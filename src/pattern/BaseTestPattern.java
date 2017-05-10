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

    public void addAction(EspressoAction espressoAction) {
        espressoActions.add(espressoAction);
    }

    public void addAction(ViewComponentType componentType, String componentId, String componentText) {
        if (componentType == null) {
            System.out.println("addAction：参数不能为空");
            return;
        }
        switch (componentType) {
            case SUBMIT_BUTTON:
                addAction(new EspressoAction(ViewComponentType.SUBMIT_BUTTON, componentId, componentText));
                break;
            case SPINNER:
                addAction(new EspressoAction(ViewComponentType.SPINNER, componentId, componentText));
                break;
            case EDIT_TEXT:
                addAction(new EspressoAction(ViewComponentType.EDIT_TEXT, componentId, componentText));
                break;
            default:
                System.out.println("BasePattern 中暂未实现该类型，需要子类继续处理。");
        }
    }

    public void addCheck(EspressoCheck espressoCheck) {
        espressoChecks.add(espressoCheck);
    }

    public void addCheck(ViewComponentType componentType, String componentId, String componentText) {
        // TODO: 2017/5/10
        return;
    }

    public EspressoAction findEspressoActionById(String id) {
        for (EspressoAction action : espressoActions) {
            if (action.getComponentId().equals(id)) {
                return action;
            }
        }
        System.out.println("findEspressoActionById: 配置文件中存在未识别的 Component ID：" + id);
        return null;
    }

    public EspressoCheck findEspressoCheckById(String id) {
        for (EspressoCheck check : espressoChecks) {
            if (check.getComponentId().equals(id)) {
                return check;
            }
        }
        System.out.println("findEspressoCheckById: 配置文件中存在未识别的 Component ID：" + id);
        return null;
    }

    public void getEspressoMethodSnippet() {

        // 首先对语句按照优先级重新排序
        espressoStatements.addAll(espressoActions);
        espressoStatements.addAll(espressoChecks);

        System.out.println("------- Espresso scripts begin here! -------");
        while (espressoStatements.size() != 0) {
            EspressoStatement statement = espressoStatements.poll();
            System.out.println(statement.getPriority() + ": " + statement.getComponentType().getDescription());
            System.out.println(statement.getEspressoCode());
        }
        System.out.println("------- Espresso scripts end! -------");
    }

    public abstract void parseModel(Element interactionFlowModel);

    public abstract void parseConfigFile(Element rootElement);

    private static class ActionPriorityComparator implements Comparator<EspressoStatement> {
        @Override
        public int compare(EspressoStatement o1, EspressoStatement o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }


}
