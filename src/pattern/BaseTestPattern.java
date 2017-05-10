package pattern;

import espresso.EspressoAction;
import espresso.EspressoCheck;
import espresso.EspressoStatement;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public abstract class BaseTestPattern {
    private static Comparator<EspressoStatement> comparator = new ActionPriorityComparator();

    String id;
    String context;
    List<EspressoAction> espressoActions;
    List<EspressoCheck> espressoChecks;
    Queue<EspressoStatement> espressoStatements;    //仅用于维护最终Espresso语句执行顺序，打印后将为空

    public BaseTestPattern() {
        espressoActions = new ArrayList<>();
        espressoChecks = new ArrayList<>();
        espressoStatements = new PriorityQueue<>(20, comparator);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void addTestAction(EspressoAction espressoAction) {
        espressoActions.add(espressoAction);
    }

    public void addTestCheck(EspressoCheck espressoCheck) {
        espressoChecks.add(espressoCheck);
    }

    public EspressoAction findEspressoActionById(String id) {
        for (EspressoAction action : espressoActions) {
            if (action.getComponentId().equals(id)) {
                return action;
            }
        }
        System.out.println("findEspressoActionById: 配置文件中的 Component ID 有误！");
        return null;
    }

    public EspressoCheck findEspressoCheckById(String id) {
        for (EspressoCheck check : espressoChecks) {
            if (check.getComponentId().equals(id)) {
                return check;
            }
        }
        System.out.println("findEspressoCheckById: 配置文件中的 Component ID 有误！");
        return null;
    }

    public void getEspressoMethodSnippet() {

        // 首先对语句按照优先级重新排序
        espressoStatements.addAll(espressoActions);
        //espressoStatements.addAll(espressoChecks);

        System.out.println("------- Espresso scripts begin here! -------");
        while (espressoStatements.size() != 0) {
            EspressoStatement statement = espressoStatements.poll();
            System.out.println(statement.getPriority() + ": " + statement.getComponentType().getDescription());
            System.out.println(statement.getEspressoCode());
        }
//        Iterator<EspressoStatement> iter = espressoStatements.iterator();
//        while (iter.hasNext()) {
//            EspressoStatement currentStatement = iter.next();
//            System.out.println(currentStatement.getPriority() + ": " + currentStatement.getComponentType().getDescription());
//            System.out.println(currentStatement.getEspressoCode());
//        }
        System.out.println("------- Espresso scripts end! -------");
    }

    /**
     * @param componentType 控件类型
     * @param componentId   控件id（可能为空）
     * @param componentText 控件文字（可能为空）
     */
    public abstract void addTestComponent(ViewComponentType componentType, String componentId, String componentText);

    public abstract void createFromModel(Element interactionFlowModel);

    private static class ActionPriorityComparator implements Comparator<EspressoStatement> {
        @Override
        public int compare(EspressoStatement o1, EspressoStatement o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }


}
