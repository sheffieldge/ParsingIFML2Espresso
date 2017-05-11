package pattern;

import espresso.EspressoAction;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/11.
 */
public class CallPattern extends BaseTestPattern {
    @Override
    public void parseViewElements(Element viewElements) {
        Iterator m = viewElements.elementIterator();
        while (m.hasNext()) {
            Element viewElementEvents = (Element) m.next();
            addAction(new EspressoAction(ViewComponentType.BUTTON, viewElementEvents.attributeValue("name"), viewElementEvents.attributeValue("text")));
            // TODO: 2017/5/11 仅对获取到的第一个按钮处理 其他忽略
            break;
        }
    }
}
