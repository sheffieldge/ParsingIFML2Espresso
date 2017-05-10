package espresso;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class EspressoAction extends EspressoStatement{

    public EspressoAction(ViewComponentType componentType, String componentId, String componentText) {
        super(componentType, componentId, componentText);
        System.out.println("ACTION added: type=" + componentType.getDescription() + ", id=" + componentId + ", priority=" + priority);
        // TODO: 2017/5/10  为了后续跳过检查非空。最后需要用配置文件填充。
        customValue = new CustomValue("FAKED_TEXT", 0);
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
}
