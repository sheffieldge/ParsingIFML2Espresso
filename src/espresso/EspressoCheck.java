package espresso;

import org.dom4j.Element;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class EspressoCheck extends EspressoStatement {

    public EspressoCheck(ViewComponentType componentType, String componentId, String componentText) {
        super(componentType, componentId, componentText);
        System.out.println("CHECK added: type=" + componentType.getDescription() + ", id=" + componentId);
        // TODO: 2017/5/10  为了后续跳过检查非空。最后需要用配置文件填充。
        CustomValue fakedValue = new CustomValue();
        fakedValue.setPlainText("FAKED_PLAIN_TEXT");
        customValue = fakedValue;
    }

    @Override
    public String getEspressoCode() {
        // TODO: 2017/5/10
        return null;
    }

    @Override
    public void setValueFromConfig(Element component, int priority) {
        // TODO: 2017/5/10 未实现
    }
}
