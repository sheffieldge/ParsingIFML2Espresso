package core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pattern.*;

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
//        Document document = dom4JParser.parse("file/ifml/find.xml");
//        Document document = dom4JParser.parse("file/ifml/sort.xml");
//        Document document = dom4JParser.parse("file/ifml/login.xml");
//        Document document = dom4JParser.parse("file/ifml/call.xml");
//        Document document = dom4JParser.parse("file/ifml/input.xml");
        Document document = dom4JParser.parse("file/ifml/master_detail.xml");
        BaseTestPattern pattern = dom4JParser.readDocument(document);
        if (pattern != null) {
            generator.addTestPattern(pattern);
//            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/find.xml"));
//            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/sort.xml"));
//            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/login.xml"));
//            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/call.xml"));
//            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/input.xml"));
            pattern.parseConfigFile(dom4JParser.readConfigFile("file/config/master_detail.xml"));

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
                return parsePattern(interactionFlowModel.attributeValue("pattern"), interactionFlowModel);
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
                case "find":
                    BaseTestPattern formPattern = new FindPattern();
                    formPattern.parseModel(interactionFlowModel);
                    return formPattern;
                case "list":
                    BaseTestPattern listPattern = new SortPattern();
                    listPattern.parseModel(interactionFlowModel);
                    return listPattern;
                case "login":
                    BaseTestPattern loginPattern = new FindPattern();
                    loginPattern.parseModel(interactionFlowModel);
                    return loginPattern;
                case "call":
                    BaseTestPattern callPattern = new CallPattern();
                    callPattern.parseModel(interactionFlowModel);
                    return callPattern;
                case "input":
                    BaseTestPattern inputPattern = new InputPattern();
                    inputPattern.parseModel(interactionFlowModel);
                    return inputPattern;
                case "master_detail":
                    BaseTestPattern masterDetailPattern = new MasterDetailPattern();
                    masterDetailPattern.parseModel(interactionFlowModel);
                    return masterDetailPattern;
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
        if (patternType == null || patternId == null) {
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