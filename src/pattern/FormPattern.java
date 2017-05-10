package pattern;

import espresso.EspressoAction;
import espresso.ViewComponentType;

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
}
