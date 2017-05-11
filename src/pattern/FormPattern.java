package pattern;

import espresso.ViewComponentType;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by gexiaofei on 2017/5/9.
 */
public class FormPattern extends BaseTestPattern {

    @Override
    public void parseViewElements(Element viewElements) {
        if (viewElements.attributeValue("type").equals("ext:Form")) {
            Iterator m = viewElements.elementIterator();
            while (m.hasNext()) {
                // 依次获取单个 View Element
                Element viewElement = (Element) m.next();
                // FIXME: 2017/5/10 利用 IFML 控件的 name 属性
                String componentId = viewElement.attributeValue("name");
                String componentText = viewElement.attributeValue("text");
                // 根据 IFML 控件类型判断测试模型控件类型
                addAction(
                        ViewComponentType.fromXmlType(viewElement.attributeValue("type")),
                        componentId,
                        componentText);
            }
        } else {
            System.out.println("请检查IFML：不支持的ViewElements类型。（不属于Form Pattern）");
        }
    }

}
