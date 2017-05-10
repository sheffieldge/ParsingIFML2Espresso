package espresso;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public enum ViewComponentType {
    // Form
    BUTTON("Button", 0),
    EDIT_TEXT("EditText", 0),
    TEXT_VIEW("TextView", 0),
    SPINNER("Spinner", 0),
    SUBMIT_BUTTON("SubmitButton", 0);


    private String description;
    private int priority;

    ViewComponentType(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public static ViewComponentType transferFromXmlType(String xmlValue) {
        switch (xmlValue) {
            case "ext:SimpleField":
                return EDIT_TEXT;
            case "ext:OnSubmitEvent":
                return SUBMIT_BUTTON;
            case "ext:SelectionField":
                return SPINNER;
            default:
                System.out.println("ViewComponentType 中未实现这种类型: " + xmlValue);
                return null;
        }
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
