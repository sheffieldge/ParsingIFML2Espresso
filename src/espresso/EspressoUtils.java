package espresso;

/**
 * Espresso语句生成
 */
public class EspressoUtils {

    public static String getActionCode(String id, String text, String performMethod) {
        if (id != null && text != null) {
            return "onView(allOf(withId(R.id." + id + "), withText(\"" + text + "\"))).perform(" + performMethod + ");";
        }
        else if (id == null && text == null) {
            System.out.println("in getActionCode: 控件的id和text不能均为空。");
            return null;
        }
        else if (id != null) {
            return "onView(withId(R.id." + id + ")).perform(" + performMethod + ");";
        }
        else {
            return "onView(withText(\"" + text + "\")).perform(" + performMethod + ");";
        }
    }

    public static String getCheckCode(String id, String text, String checkMethod) {
        return null;
    }
}
