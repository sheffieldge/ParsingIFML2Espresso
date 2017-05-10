package pattern;

import espresso.EspressoAction;
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
    public void addTestComponent(ViewComponentType componentType, String componentId, String componentText) {
        if (componentType == null) {
            return;
        }
        switch (componentType) {
            case SUBMIT_BUTTON:
                addTestAction(new EspressoAction(ViewComponentType.SUBMIT_BUTTON, componentId, componentText));
                break;
            case SPINNER:
                addTestAction(new EspressoAction(ViewComponentType.SPINNER, componentId, componentText));
                break;
            case EDIT_TEXT:
                addTestAction(new EspressoAction(ViewComponentType.EDIT_TEXT, componentId, componentText));
                break;
            default:
                System.out.println("Form Pattern 中暂未实现该类型");
        }
    }

    @Override
    public void createFromModel(Element interactionFlowModel) {
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
                        String componentId = viewElement.attributeValue("sgComponentId");
                        String componentText = viewElement.attributeValue("sgComponentText");
                        // 根据 IFML 控件类型判断测试模型控件类型
                        addTestComponent(
                                ViewComponentType.transferFromXmlType(viewElement.attributeValue("type")),
                                componentId,
                                componentText);
                    }
                } else if (viewElements.getName().equals("actionEvents")) {

                }

            }
        }
    }
}
