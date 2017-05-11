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

    public void addCheck(EspressoCheck espressoCheck) {
        espressoChecks.add(espressoCheck);
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
            System.out.println("Line " + statement.getPriority() + ": (" + statement.getComponentType().getDescription() + ")");
            System.out.println(statement.getEspressoCode());
        }
        System.out.println("------- Espresso scripts end! -------");
    }

    public void parseModel(Element interactionFlowModel) {
        id = interactionFlowModel.attributeValue("patternId");
        if (id == null) {
            System.out.println("xml 中 interactionFlowModel 的属性 sgPatternId 是必填项");
            return;
        }
        Iterator j = interactionFlowModel.elementIterator();
        while (j.hasNext()) {

            // 依次获取 interactionFlowModelElements 同级
            Element interactionFlowModelElements = (Element) j.next();
            if (interactionFlowModelElements.attributeValue("type").equals("core:ViewContainer")) {
                // FIXME: 2017/5/11 利用 IFML 控件的 name 属性为 context
                context = interactionFlowModelElements.attributeValue("name");
                Iterator k = interactionFlowModelElements.elementIterator();
                while (k.hasNext()) {
                    //依次获取 viewElements 同级
                    Element viewElements = (Element) k.next();
                    if (viewElements.getName().equals("viewElements")) {
                        // 根据子类不同而转到相应的实现方法
                        parseViewElements(viewElements);
                    } else {
                        // TODO: 2017/5/11 其他情况暂不考虑
                    }
                }
                // TODO: 2017/5/11 对于每个模型，设置第一个Window为默认场景
                break;
            } else {

            }
        }
    }

    public abstract void parseViewElements(Element viewElements);

    public void parseConfigFile(Element patternElement) {
        int priority = 1;
        Iterator i = patternElement.elementIterator();
        while (i.hasNext()) {
            Element statementGroup = (Element) i.next();
            if (statementGroup.getName().equals("action")) {
                Iterator j = statementGroup.elementIterator();

                // for each <component> under <action>
                while (j.hasNext()) {
                    Element component = (Element) j.next();
                    if (component.attributeValue("id") == null && component.attributeValue("text") == null) {
                        System.out.println("parseConfigFile：配置文件中 <action>/<component> 不能没有标识符id或text！");
                    } else if (component.attributeValue("id") != null) {
                        findEspressoActionById(component.attributeValue("id"))
                                .setCustomValueFromConfig(component, priority++);
                    } else {
                        addAction(new EspressoAction(ViewComponentType.TEXT_VIEW, null, component.attributeValue("name")));
                    }
                }
            } else if (statementGroup.getName().equals("check")) {
                Iterator j = statementGroup.elementIterator();

                // for each <component> under <check>
                while (j.hasNext()) {
                    Element component = (Element) j.next();
                    if (component.attributeValue("type") == null) {
                        System.out.println("配置文件中 <check>/<component> 的 type 是必填项");
                    }
                    EspressoCheck check = new EspressoCheck(
                            ViewComponentType.fromConfigType(component.attributeValue("type")),
                            component.attributeValue("id"),
                            component.attributeValue("text"));
                    check.setCustomValueFromConfig(component, priority++);
                    addCheck(check);
                }
            }
        }
    }

    private static class ActionPriorityComparator implements Comparator<EspressoStatement> {
        @Override
        public int compare(EspressoStatement o1, EspressoStatement o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }

}
