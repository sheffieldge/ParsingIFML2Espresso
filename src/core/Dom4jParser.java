package core;

import espresso.ViewComponentType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pattern.BaseTestPattern;
import pattern.FormPattern;

import java.io.File;
import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class Dom4jParser {

    /**
     * @param fileName XML文件路径
     * @return
     * @throws DocumentException
     */
    public Document parse(String fileName) throws DocumentException{
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        return saxReader.read(inputXml);

    }

    public BaseTestPattern readDocument(Document document) {
        if (document == null) {
            System.out.println("document 不能为空。");
            return null;
        }
        Element ifmlModel = document.getRootElement();
        Iterator i = ifmlModel.elementIterator();
        while (i.hasNext()) {
            // 获取 IFM，忽略 Domain Model
            Element interactionFlowModel = (Element)i.next();
            if (interactionFlowModel.getName().equals("interactionFlowModel")) {
                String testPattern = interactionFlowModel.attributeValue("sgPattern");
                if (testPattern == null) {
                    System.out.println("xml 中 interactionFlowModel 缺少模式类型");
                    return null;
                }
                if (testPattern.equals("form")) {
                    System.out.println("------------- 开始处理 Form Pattern -------------");
                    BaseTestPattern result = createFormPattern(interactionFlowModel);
                    System.out.println("------------- Form Pattern 处理结束 -------------");
                    return result;
                }
            }
        }
        System.out.println("readDocument错误：请检查XML中的模式名是否有误！");
        return null;
    }

    public FormPattern createFormPattern(Element interactionFlowModel) {
        FormPattern formPattern = new FormPattern();
        Iterator j = interactionFlowModel.elementIterator();
        while (j.hasNext()) {

            // 依次获取 interactionFlowModelElements 同级
            Element interactionFlowModelElements = (Element) j.next();
            Iterator k = interactionFlowModelElements.elementIterator();
            while (k.hasNext()) {

                //依次获取 viewElements 同级
                Element viewElements = (Element) k.next();
                if (viewElements.getName().equals("viewElements")) {
                    Iterator m = viewElements.elementIterator();
                    while (m.hasNext()) {
                        // 依次获取单个 View Element
                        Element viewElement = (Element) m.next();
                        String componentId = viewElement.attributeValue("sgComponentId");
                        String componentText = viewElement.attributeValue("sgComponentText");
                        // 根据 IFML 控件类型判断测试模型控件类型
                        formPattern.addTestComponent(
                                ViewComponentType.transferFromXmlType(viewElement.attributeValue("type")),
                                componentId,
                                componentText);
                    }
                }
                else if (viewElements.getName().equals("actionEvents")) {

                }

            }
        }
        formPattern.getEspressoMethod();
        return formPattern;
    }

    public static void main(String[] args) {
        Dom4jParser dom4JParser = new Dom4jParser();
        try {
            Document document = dom4JParser.parse("xmlfile/form.xml");
            dom4JParser.readDocument(document);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}