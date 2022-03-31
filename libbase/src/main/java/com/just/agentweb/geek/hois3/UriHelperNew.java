package com.just.agentweb.geek.hois3;

import android.content.Intent;
import android.net.Uri;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriHelperNew {
    public static final String REGEXP = "^\\{([byoilfds]{1})\\}(.+)$";

    public static final String HIOS_SCHEME = "dataability";
    public static final String CONDITION = "condition";

    public static final String CONDITION_LOGIN = "login";
    public static final String CONDITION_OR_LOGIN = "or_login";

    public static final String BOOLEAN = "b";
    public static final String BYTE = "y";
    public static final String SHORT = "o";
    public static final String INT = "i";
    public static final String LONG = "l";
    public static final String FLOAT = "f";
    public static final String DOUBLE = "d";
    public static final String STRING = "s";

    public static List<String> queryStringFillMap(Map<String, Object> map, Uri uri) {

        List<String> conditions = uri.getQueryParameters(CONDITION);

        Pattern pattern = Pattern.compile(REGEXP);
        Matcher matcher;

        Set<String> paramsNames = uri.getQueryParameterNames();
        String queryValue, tag, value;

        for (String name : paramsNames) {
            if (CONDITION.equals(name)) { continue;}

            queryValue = uri.getQueryParameter(name);
            matcher = pattern.matcher(queryValue);
            // groupCount() == allCount - 1
            if (matcher.matches() && matcher.groupCount() == 2) {
                tag = matcher.group(1);
                value = matcher.group(2);

                if (BOOLEAN.equals(tag)) {
                    MapFillerNew.fillBoolean(map, name, value);
                } else if (BYTE.equals(tag)) {
                    MapFillerNew.fillByte(map, name, value);
                } else if (SHORT.equals(tag)) {
                    MapFillerNew.fillShort(map, name, value);
                } else if (INT.equals(tag)) {
                    MapFillerNew.fillInt(map, name, value);
                } else if (LONG.equals(tag)) {
                    MapFillerNew.fillLong(map, name, value);
                } else if (FLOAT.equals(tag)) {
                    MapFillerNew.fillFloat(map, name, value);
                } else if (DOUBLE.equals(tag)) {
                    MapFillerNew.fillDouble(map, name, value);
                } else {
                    MapFillerNew.fillString(map, name, value);
                }
            }else {
                MapFillerNew.fillString(map, name, queryValue);
            }
        }

        return conditions;
    }

    /**
     * 解析uri中的querystring到intent中
     * @param intent intent
     * @param uri uri
     * @return 如果querystring有CONDITION的key， 则返回，并且不加入到intent里
     * @see #CONDITION
     * @see #CONDITION_LOGIN
     *  @see #CONDITION_OR_LOGIN
     */
    public static List<String> queryStringFillIntent(Intent intent, Uri uri) {

        List<String> conditions = uri.getQueryParameters(CONDITION);

        Pattern pattern = Pattern.compile(REGEXP);
        Matcher matcher;

        Set<String> paramsNames = uri.getQueryParameterNames();
        String queryValue, tag, value;

        for (String name : paramsNames) {
            if (CONDITION.equals(name)) { continue;}

            queryValue = uri.getQueryParameter(name);
            matcher = pattern.matcher(queryValue);
            // groupCount() == allCount - 1
            if (matcher.matches() && matcher.groupCount() == 2) {
                tag = matcher.group(1);
                value = matcher.group(2);

                if (BOOLEAN.equals(tag)) {
                    IntentFillerNew.fillBoolean(intent, name, value);
                } else if (BYTE.equals(tag)) {
                    IntentFillerNew.fillByte(intent, name, value);
                } else if (SHORT.equals(tag)) {
                    IntentFillerNew.fillShort(intent, name, value);
                } else if (INT.equals(tag)) {
                    IntentFillerNew.fillInt(intent, name, value);
                } else if (LONG.equals(tag)) {
                    IntentFillerNew.fillLong(intent, name, value);
                } else if (FLOAT.equals(tag)) {
                    IntentFillerNew.fillFloat(intent, name, value);
                } else if (DOUBLE.equals(tag)) {
                    IntentFillerNew.fillDouble(intent, name, value);
                } else {
                    IntentFillerNew.fillString(intent, name, value);
                }
            }else {
                IntentFillerNew.fillString(intent, name, queryValue);
            }
        }

        return conditions;
    }
}
