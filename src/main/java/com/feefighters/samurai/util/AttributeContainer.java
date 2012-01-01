package com.feefighters.samurai.util;

import java.math.BigDecimal;
import java.util.Map;

public class AttributeContainer {

    protected void setUnlessNull(String variable, Object value) {
        if (value == null) return;
        try {
            this.getClass().getDeclaredField(variable).set(this, value);
        } catch(NoSuchFieldException ex) {
            // do nothing
        } catch(IllegalAccessException ex) {
            // do nothing
        }
    }

    protected static Integer getIntegerValue(Map<String, String> map, String key) {
        return Integer.parseInt(getValue(map, key));
    }

    protected static BigDecimal getDecimalValue(Map<String, String> map, String key) {
        return new BigDecimal(getValue(map, key));
    }

    protected static String getValue(Map<String, String> map, String key) {
        return map.get(key) != null ? map.get(key) : map.get(underscore(key));
    }

    private static String toCamelCase(String s){
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String underscore(String camelCaseWord, char... delimiterChars) {
        if (camelCaseWord == null) return null;
        String result = camelCaseWord.trim();
        if (result.length() == 0) return "";
        result = result.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2");
        result = result.replaceAll("([a-z\\d])([A-Z])", "$1_$2");
        result = result.replace('-', '_');
        if (delimiterChars != null) {
            for (char delimiterChar : delimiterChars) {
                result = result.replace(delimiterChar, '_');
            }
        }
        return result.toLowerCase();
    }
}
