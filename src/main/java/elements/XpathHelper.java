package elements;

public class XpathHelper {

    static final String CLASS = "class";

    public static String getRoot() {
        return "//";
    }

    public static String getTextEquals(String text) {
            return "*[text() = " + convertXpathText(text) + "]";
    }

    public static String getTextContains(String text) {
            return "*[contains(text(), " + convertXpathText(text) + ")]";
    }

    public static String getTextStartsWith(String text) {
            return "*[starts-with(text(), " + convertXpathText(text) + ")]";
    }

    public static String getClassEqual(String id) {
        return getFieldEqual(CLASS, id);
    }

    public static String getClassContains(String id) {
        return getFieldContains(CLASS, id);
    }

    private static String getFieldEqual(String fieldName, String text) {
        return "*[@" + fieldName + " = " + convertXpathText(text) + "]";
    }

    private static String getFieldContains(String fieldName, String text) {
        return "*[contains(@" + fieldName + ", " + convertXpathText(text) + ")]";
    }

    private static String getFieldStartsWith(String fieldName, String text) {
        return "*[starts-with(@" + fieldName + ", " + convertXpathText(text) + ")]";
    }

    public static String getIdEqual(String id) {
        return getFieldEqual(getIdAttribute(), id);
    }

    public static String getIdContains(String id) {
        return getFieldContains(getIdAttribute(), id);
    }

    public static String getNameEqual(String name) {
        return getFieldEqual("name", name);
    }

    public static String getNameContains(String name) {
        return getFieldContains("name", name);
    }

    public static String getNameStartsWith(String name) {
        return getFieldStartsWith("name", name);
    }

    private static String getIdAttribute() {
            return "id";
    }

    private static String convertXpathText(String text) {
        if (text.contains("'")) {
            String prefix = "";
            String[] elements = text.split("'");

            StringBuilder output = new StringBuilder("concat(");

            for (String s : elements) {
                output.append(prefix).append("'").append(s).append("'");
                prefix = ",\"'\",";
            }

            if (output.toString().endsWith(","))
                output = new StringBuilder(output.substring(0, output.length() - 2));

            output.append(")");

            return output.toString();
        } else {
            return "'" + text + "'";
        }
    }

}
