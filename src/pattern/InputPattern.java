package pattern;

import espresso.EspressoAction;
import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/12.
 */
public class InputPattern extends BaseTestPattern {
    @Override
    public void parseViewElements(Element viewElements) {
        Iterator m = viewElements.elementIterator();
        while (m.hasNext()) {
            Element parameters = (Element) m.next();
            // 仅处理标签名 parameters
            if (parameters.getName().equals("parameters")) {
                String componentId = parameters.attributeValue("name");
                String componentText = parameters.attributeValue("text");
                // 根据 IFML 控件类型判断测试模型控件类型
                addAction(new EspressoAction(ViewComponentType.fromXmlType(parameters.attributeValue("type")), componentId, componentText));
            }
        }
    }
}
