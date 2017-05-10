package pattern;

import espresso.EspressoAction;
import espresso.EspressoCheck;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public abstract class BaseTestPattern {
    private static Comparator<EspressoAction> comparator = new ActionPriorityComparator();
    String id;
    Queue<EspressoAction> espressoActions;
    List<EspressoCheck> espressoChecks;

    public BaseTestPattern() {
        espressoActions = new PriorityQueue<>(20, comparator);
        espressoChecks = new ArrayList<>();
    }

    public void addTestAction(EspressoAction espressoAction) {
        espressoActions.add(espressoAction);
    }

    public void addTestCheck(EspressoCheck espressoCheck) {
        espressoChecks.add(espressoCheck);
    }

    public void getEspressoMethod() {
        System.out.println("------- Espresso scripts begin here! -------");
        int i = 1;
        for (EspressoAction espressoAction : espressoActions) {
            System.out.println(i++ + ": " + espressoAction.getComponentType().getDescription());
            System.out.println(espressoAction.getEspressoCode());
        }
        System.out.println("------- Espresso scripts end! -------");
    }

    /**
     * @param componentType 控件类型
     * @param componentId 控件id（可能为空）
     * @param componentText 控件文字（可能为空）
     */
    public abstract void addTestComponent(ViewComponentType componentType, String componentId, String componentText);

    public abstract void createFromModel(Element interactionFlowModel);

    private static class ActionPriorityComparator implements Comparator<EspressoAction> {
        @Override
        public int compare(EspressoAction o1, EspressoAction o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }


}
