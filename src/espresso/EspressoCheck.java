package espresso;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class EspressoCheck extends EspressoStatement {

    public EspressoCheck(ViewComponentType componentType, String componentId, String componentText) {
        super(componentType, componentId, componentText);
        System.out.println("CHECK added: type=" + componentType.getDescription() + ", id=" + componentId);
        // TODO: 2017/5/10  为了后续跳过检查非空。最后需要用配置文件填充。
        customValue = new CustomValue("FAKED_TEXT", 0);
    }

    @Override
    public String getEspressoCode() {
        // TODO: 2017/5/10
        return null;
    }
}
