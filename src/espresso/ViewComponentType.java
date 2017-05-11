package espresso;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public enum ViewComponentType {
    // Form
    BUTTON("Button"),
    EDIT_TEXT("EditText"),
    TEXT_VIEW("TextView"),
    SPINNER("Spinner"),
    RECYCLER_VIEW("RecyclerView"),
    DRAWER("Drawer");


    private String description;

    ViewComponentType(String description) {
        this.description = description;
    }

    public static ViewComponentType fromXmlType(String xmlValue) {
        switch (xmlValue) {
            case "Button":
                return BUTTON;
            case "EditText":
                return EDIT_TEXT;
            case "TextView":
                return TEXT_VIEW;
            case "Spinner":
                return SPINNER;
            default:
                System.out.println("ViewComponentType 中未实现这种类型: " + xmlValue);
                return null;
        }
    }

    public static ViewComponentType fromConfigType(String configType) {
        switch (configType) {
            case "EditText":
                return EDIT_TEXT;
            case "TextView":
                return TEXT_VIEW;
            case "RecyclerView":
                return RECYCLER_VIEW;
            default:
                System.out.println("ViewComponentType 中未实现这种类型：" + configType);
                return null;
        }
    }

    public String getDescription() {
        return description;
    }
}
