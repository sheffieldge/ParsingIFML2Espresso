package espresso;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public enum ViewComponentType {
    // Form
    BUTTON("Button", 1),
    EDIT_TEXT("EditText", 1),
    TEXT_VIEW("TextView", 1),
    SPINNER("Spinner", 1),
    SUBMIT_BUTTON("SubmitButton", 10);


    private String description;
    private int priority;

    ViewComponentType(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
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
}
