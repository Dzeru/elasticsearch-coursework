package com.dzeru.elasticsearchcoursework.util;

public class ExtractorUtils {

    public static int getStartPositionWithOffset(String string, String point) {
        return string.indexOf(point) + point.length();
    }
}
