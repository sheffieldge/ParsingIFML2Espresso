package espresso;

import org.dom4j.Element;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class EspressoAction extends EspressoStatement{

    public EspressoAction(ViewComponentType componentType, String componentId, String componentText) {
        super(componentType, componentId, componentText);
        System.out.println("ACTION added: type=" + componentType.getDescription() + ", id=" + componentId + ", priority=" + priority);
        // TODO: 2017/5/10  为了后续跳过检查非空。最后需要用配置文件填充。
        CustomValue fakedValue = new CustomValue();
        fakedValue.setPlainText("FAKED_PLAIN_TEXT");
        customValue = fakedValue;
    }

    @Override
    public String getEspressoCode() {
        if (customValue == null) {
            System.out.println("不能生成 Espresso 脚本：还未输入配置文件。");
            return null;
        }
        switch (componentType) {
            case BUTTON: case SUBMIT_BUTTON:
                return EspressoUtils.getActionCode(componentId, componentText, "click()");
            case EDIT_TEXT:
                return EspressoUtils.getActionCode(componentId, componentText, "replaceText(\"" + customValue.getPlainText() + "\")");
            case SPINNER:
                return EspressoUtils.getActionCode(componentId, componentText, "click()") + "\n" +
                        EspressoUtils.getActionCode(null, customValue.getPlainText(), "click()");
            default:
                System.out.println("不能生成 Espresso 脚本：未实现的控件类型。");
                return null;
        }
    }

    @Override
    public void setValueFromConfig(Element component, int priority) {
        // TODO: 2017/5/10 这里没有管类型是否识别，都增加优先级，并且刷新优先级队列
        setPriority(priority);
        switch (componentType) {
            case BUTTON:
            case SUBMIT_BUTTON:
                break;
            case EDIT_TEXT:
                customValue.setPlainText(component.attributeValue("plainText"));
                break;
            case SPINNER:
                customValue.setPlainText(component.attributeValue("plainText"));
                break;
            default:
                System.out.println("setValueFromConfig：" + componentType.getDescription() + "暂未实现该类型。");
                return;
        }
        System.out.println(componentType.getDescription() + "配置完成。");
    }
}
