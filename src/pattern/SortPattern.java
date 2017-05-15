package pattern;

import espresso.EspressoAction;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/11.
 */
public class SortPattern extends BaseTestPattern {

    @Override
    public void parseViewElements(Element viewElements) {
        // 对于List类型仅需提供List控件的id
        addAction(new EspressoAction(ViewComponentType.RECYCLER_VIEW, viewElements.attributeValue("name"), viewElements.attributeValue("text")));
        Iterator m = viewElements.elementIterator();
        while (m.hasNext()) {
            // TODO: 2017/5/11 List的ViewElement暂不处理
            m.next();
        }
    }
}
