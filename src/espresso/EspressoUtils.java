package espresso;

/**
 * Espresso语句生成
 */
public class EspressoUtils {

    public static String getViewMatchersStatement(String id, String text) {
        assert (id != null || text != null);
        if (id != null && text != null) {
            return "onView(allOf(withId(R.id." + id + "), withText(\"" + text + "\")))";
        }
        else if (id != null) {
            return "onView(withId(R.id." + id + "))";
        }
        else {
            return "onView(withText(\"" + text + "\"))";
        }
    }

    public static String getViewActionStatement(String actionMethod, String... actionMethods) {
        if (actionMethods.length == 0) {
            return ".perform(" + actionMethod + ");";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(".perform(").append(actionMethod).append(", ");
            for (int i = 0; i < actionMethods.length - 1; i++) {
                sb.append(actionMethods[i]).append(", ");
            }
            sb.append(actionMethods[actionMethods.length - 1]).append(");");
            return sb.toString();
        }
    }

    public static String getViewAssertions(String assertionString) {
        return ".check(" + assertionString + ");";
    }

    public static String getActionCode(String id, String text, String actionMethod, String... actionMethods) {
        if (id == null && text == null) {
            System.out.println("EspressoUtils getActionCode: 控件的id和text不能均为空。");
            return null;
        } else {
            return getViewMatchersStatement(id, text) + getViewActionStatement(actionMethod, actionMethods);
        }
    }

    public static String getCheckCode(String id, String text, String checkMethod) {
        if (id == null && text == null) {
            System.out.println("EspressoUtils getActionCode: 控件的id和text不能均为空。");
            return null;
        } else {
            return getViewMatchersStatement(id, text) + getViewAssertions(checkMethod);
        }
    }
}
