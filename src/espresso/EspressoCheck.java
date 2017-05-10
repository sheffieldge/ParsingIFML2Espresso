package espresso;

import org.dom4j.Element;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class EspressoCheck extends EspressoStatement {

    public EspressoCheck(ViewComponentType componentType, String componentId, String componentText) {
        super(componentType, componentId, componentText);
        System.out.println("CHECK added: type=" + componentType.getDescription() + ", id=" + componentId + ", text=" + componentText);
        // TODO: 2017/5/10  需要保留！为了后续跳过检查非空。最后需要用配置文件填充。
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
            case TEXT_VIEW:
                return EspressoUtils.getCheckCode(null, customValue.getDisplayText(), "isDisplayed()");
            case EDIT_TEXT:
                return EspressoUtils.getCheckCode(componentId, componentText, "matches(hasErrorText(\"" + customValue.getErrorText() + "\"))");
            case DRAWER:
                return EspressoUtils.getCheckCode(componentId, componentText, "待补充");
            case RECYCLER_VIEW:
                // TODO: 2017/5/10 处理不同断言的判断
                return EspressoUtils.getCheckCode(componentId, componentText, "待补充");
            default:
                System.out.println("不能生成 Espresso 脚本：未实现的控件类型。");
                return null;
        }
    }

    @Override
    public void setCustomValueFromConfig(Element component, int priority) {
        setPriority(priority);
        switch (componentType) {
            case TEXT_VIEW:
                // default to test whether the text is displayed
                customValue.setDisplayText(component.attributeValue("displayText"));
                break;
            case EDIT_TEXT:
                customValue.setErrorText(component.attributeValue("errorText"));
                break;
            case DRAWER:
                customValue.setShouldOpen(Boolean.parseBoolean(component.attributeValue("shouldOpen")));
                break;
            case RECYCLER_VIEW:
                String position = component.attributeValue("recyclerViewItemPosition");
                String count = component.attributeValue("recyclerViewItemCount");
                if (position == null && count == null) {
                    System.out.println(componentType.getDescription() + "标签至少需要有一个断言属性！");
                } else {
                    // TODO: 2017/5/10 增加更多RecyclerView的断言以及优化此处的逻辑
                    if (position != null) {
                        customValue.setRecyclerViewItemPosition(Integer.parseInt(position));
                    }
                    if (count != null) {
                        customValue.setRecyclerViewItemCount(Integer.parseInt(count));
                    }
                }
                break;
            default:
                System.out.println("EspressoCheck setCustomValueFromConfig：" + componentType.getDescription() + "暂未实现该类型。");
                return;
        }
        System.out.println(componentType.getDescription() + "配置完成。");
    }
}
