package com.tempest.moonlight.server.util;

import com.tempest.moonlight.server.domain.HasValue;

import java.util.Random;

/**
 * Created by Yurii on 2015-06-16.
 */
public class RandomStringUtil {
    public enum SymbolsSet implements HasValue<String> {
        NUMERIC("0123456789"),
        ALPHABETICAL("abcdefghijklmnopqrstuvwxyz"),
        NUMERIC_ALPHABETICAL("0123456789abcdefghijklmnopqrstuvwxyz")
        ;

        private final String value;

        SymbolsSet(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    private static final Random RANDOM = new Random();

    public static String getRandomString(HasValue<String> symbolsSet, int length) {
        char[] charArray = symbolsSet.getValue().toCharArray();
        int charsCount = charArray.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(charArray[RANDOM.nextInt(charsCount)]);
        }
        return stringBuilder.toString();
    }

    public static String getRandomString(int length) {
        return getRandomString(SymbolsSet.NUMERIC_ALPHABETICAL, length);
    }
}
