package pattern;

import espresso.EspressoAction;
import espresso.EspressoCheck;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class FormPattern extends BaseTestPattern implements CodeGenerator {

    @Override
    public String getEspressoStatement() {
        return null;
    }

    @Override
    public void parseModel(Element interactionFlowModel) {
        id = interactionFlowModel.attributeValue("sgPatternId");
        if (id == null) {
            System.out.println("xml 中 interactionFlowModel 缺少模式 ID");
            return;
        }
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
                        // FIXME: 2017/5/10 利用 IFML 控件的 name 属性
                        String componentId = viewElement.attributeValue("name");
                        String componentText = viewElement.attributeValue("sgText");
                        // 根据 IFML 控件类型判断测试模型控件类型
                        addAction(
                                ViewComponentType.fromXmlType(viewElement.attributeValue("type")),
                                componentId,
                                componentText);
                    }
                } else if (viewElements.getName().equals("actionEvents")) {

                }
            }
        }
    }

    @Override
    public void parseConfigFile(Element patternElement) {
        String patternContext = patternElement.attributeValue("context");
        setContext(patternContext);

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
                        System.out.println("配置文件中 <action>/<component> 的 id 是必填项");
                    }
                    EspressoAction action = findEspressoActionById(component.attributeValue("id"));
                    action.setCustomValueFromConfig(component, priority++);
                }
            } else if (statementGroup.getName().equals("check")) {
                Iterator j = statementGroup.elementIterator();

                // for each <component> under <check>
                while (j.hasNext()) {
                    Element component = (Element) j.next();
                    if (component.attributeValue("type") == null) {
                        System.out.println("配置文件中 <check>/<component> 的 type 是必填项");
                    }
                    EspressoCheck check = new EspressoCheck(
                            ViewComponentType.fromConfigType(component.attributeValue("type")),
                            component.attributeValue("id"),
                            component.attributeValue("text"));
                    check.setCustomValueFromConfig(component, priority++);
                    addCheck(check);
                }
            }
        }
    }
}
