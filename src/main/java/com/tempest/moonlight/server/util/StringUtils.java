package com.tempest.moonlight.server.util;

/**
 * Created by Yurii on 2015-06-20.
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }
}
