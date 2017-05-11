package pattern;

import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/11.
 */
public class ListPattern extends BaseTestPattern {


    @Override
    public void parseViewElements(Element viewElements) {
        if (viewElements.getName().equals("viewElements")) {
            if (viewElements.attributeValue("type").equals("ext:List")) {
                // 对于List类型仅需提供List控件的id
                addAction(ViewComponentType.RECYCLER_VIEW,
                        viewElements.attributeValue("name"),
                        viewElements.attributeValue("text"));
                Iterator m = viewElements.elementIterator();
                while (m.hasNext()) {
                    // TODO: 2017/5/11 List的ViewElement暂不处理
                    m.next();
                }
            } else {
                System.out.println("请检查IFML：不支持的ViewElements类型。（不属于List Pattern）");
            }
        } else if (viewElements.getName().equals("actionEvents")) {

        }
    }
}
