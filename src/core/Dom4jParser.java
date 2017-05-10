package core;

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
            pattern.parseConfigFile(dom4JParser.readConfigFile("config/form.xml"));
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
                    formPattern.parseModel(interactionFlowModel);
                    return formPattern;
                default:
                    System.out.println("readDocument错误：请检查XML中的模式名是否有误！");
                    break;
            }
        }
        System.out.println("parsePattern: 其他错误。");
        return null;
    }

    public Element readConfigFile(String fileName) throws DocumentException {
        Document document = parse(fileName);
        if (document == null) {
            System.out.println("配置文件读取Document有误。");
            return null;
        }
        Element patternElement = document.getRootElement();
        // 处理pattern的属性
        String patternType = patternElement.attributeValue("type");
        String patternId = patternElement.attributeValue("id");
        String patternContext = patternElement.attributeValue("context");
        if (patternType == null || patternId == null || patternContext == null) {
            System.out.println("配置文件中 Pattern 属性不完整。");
            return null;
        } else {
            if (GeneratorFramework.findTestPatternById(patternId) == null) {
                System.out.println("找不到对应的IFML信息：" + patternId);
                return null;
            } else {
                return patternElement;
            }
        }
    }

}