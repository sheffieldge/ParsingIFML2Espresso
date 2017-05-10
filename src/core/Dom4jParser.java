package core;

import espresso.EspressoAction;
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

    public static void main(String[] args) throws DocumentException {
        GeneratorFramework generator = new GeneratorFramework();
        Dom4jParser dom4JParser = new Dom4jParser();

        System.out.println("------------- 开始处理 Pattern -------------");
        Document document = dom4JParser.parse("xmlfile/form.xml");
        BaseTestPattern pattern = dom4JParser.readDocument(document);
        if (pattern != null) {
            generator.addTestPattern(pattern);
            dom4JParser.readConfigFile("config/form.xml");
            pattern.getEspressoMethodSnippet();
        }
        System.out.println("------------- Pattern 处理结束 -------------");

    }

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
            System.out.println("readDocument：参数不能为空。");
            return null;
        }
        Element ifmlModel = document.getRootElement();
        Iterator i = ifmlModel.elementIterator();
        while (i.hasNext()) {
            Element interactionFlowModel = (Element)i.next();
            // 仅处理interactionFlowModel，忽略 Domain Model
            if (interactionFlowModel.getName().equals("interactionFlowModel")) {
                return parsePattern(interactionFlowModel.attributeValue("sgPattern"), interactionFlowModel);
            }
        }
        System.out.println("readDocument：其他错误。");
        return null;
    }

    public BaseTestPattern parsePattern(String patternName, Element interactionFlowModel) {
        if (patternName == null) {
            System.out.println("xml 中 interactionFlowModel 缺少模式类型");
            return null;
        } else {
            switch (patternName) {
                case "form":
                    BaseTestPattern formPattern = new FormPattern();
                    formPattern.createFromModel(interactionFlowModel);
                    return formPattern;
                default:
                    System.out.println("readDocument错误：请检查XML中的模式名是否有误！");
                    break;
            }
        }
        System.out.println("parsePattern: 其他错误。");
        return null;
    }

    public void readConfigFile(String fileName) throws DocumentException {
        File configXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(configXml);
        if (document == null) {
            System.out.println("配置文件读取Document有误。");
            return;
        }
        Element patternElement = document.getRootElement();
        // 处理pattern的属性
        String patternType = patternElement.attributeValue("type");
        String patternId = patternElement.attributeValue("id");
        String patternContext = patternElement.attributeValue("context");
        if (patternType == null || patternId == null || patternContext == null) {
            System.out.println("配置文件中 Pattern 属性不完整。");
            return;
        }

        BaseTestPattern testPattern = GeneratorFramework.findTestPatternById(patternId);
        testPattern.setContext(patternContext);

        int priority = 1;
        Iterator i = patternElement.elementIterator();
        while (i.hasNext()) {
            Element statementGroup = (Element) i.next();
            if (statementGroup.getName().equals("action")) {
                Iterator j = statementGroup.elementIterator();

                // for each <component> under <action>
                while (j.hasNext()) {
                    Element component = (Element) j.next();
                    if (component.attributeValue("id") == null) {
                        System.out.println("配置文件中 Component 的 id 是必填项");
                    }
                    EspressoAction action = testPattern.findEspressoActionById(component.attributeValue("id"));
                    action.setValueFromConfig(component, priority++);
                }
            } else if (statementGroup.getName().equals("check")) {

            }
        }
    }

}