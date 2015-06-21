package com.tempest.moonlight.server.util;

/**
 * Created by Yurii on 2015-06-20.
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean equalIgnoreCase(String first, String second) {
        if(first == null || second == null) {
            return first == second;
        }
        return first.compareToIgnoreCase(second) == 0;
    }
}
