package com.alexandravalkova.hangmangame;

import java.util.Iterator;

public class StringUtil {
    public static String join(String glue, Iterable<?> elements) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<?> iterator = elements.iterator();

        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(glue);
            }
        }

        return stringBuilder.toString();
    }

    public static String join(String glue, char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j = chars.length - 1; i <= j; i++) {
            stringBuilder.append(chars[i]);
            if (i != j) {
                stringBuilder.append(glue);
            }
        }
        return stringBuilder.toString();
    }
}
