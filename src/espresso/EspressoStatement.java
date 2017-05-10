package espresso;

/**
 * Created by gexiaofei on 2017/5/10.
 */
public abstract class EspressoStatement {
    int priority;
    ViewComponentType componentType;
    String componentId;
    String componentText;
    CustomValue customValue;


    public EspressoStatement(ViewComponentType componentType, String componentId, String componentText) {
        this.componentType = componentType;
        this.componentId = componentId;
        this.componentText = componentText;
        // TODO: 2017/5/10 这里priority的来源应该是Espresso代码中的执行顺序
        priority = componentType.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public CustomValue getCustomValue() {
        return customValue;
    }

    public void setCustomValue(CustomValue customValue) {
        this.customValue = customValue;
    }

    public ViewComponentType getComponentType() {
        return componentType;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getComponentText() {
        return componentText;
    }

    public abstract String getEspressoCode();
}
