package utils;

/**
 * Created by andy on 4/11/15.
 */
public class StringUtils {

    public static String getAngleBracketString(String str) {
        return "<" + str + ">";
    }

    public static String getLastSubUrl(String fullUrl) {
        return fullUrl.substring(fullUrl.lastIndexOf('/') + 1);
    }

}
