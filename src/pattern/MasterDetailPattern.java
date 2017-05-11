package pattern;

import espresso.EspressoAction;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/12.
 */
public class MasterDetailPattern extends BaseTestPattern {
    @Override
    public void parseViewElements(Element viewElements) {
        Iterator m = viewElements.elementIterator();
        while (m.hasNext()) {
            // 依次获取单个 View Element
            Element viewElementEvents = (Element) m.next();
            if (viewElementEvents.element("outInteractionFlows") != null) {
                addAction(new EspressoAction(ViewComponentType.fromXmlType(viewElementEvents.attributeValue("type")),
                        viewElementEvents.attributeValue("id"),
                        viewElementEvents.attributeValue("text")));
            }
        }
    }
}
