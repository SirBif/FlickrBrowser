package com.flickrbrowser.util;

public class Utils {

    /**
     * Shortcut version for {@link #niceString(String, int) niceString} with lenght=100
     * @param input
     * @return
     */
    public static String niceString(String input) {
        return niceString(input, 100);
    }

    /**
     * Returns a more "presentable" version of the string (no "null" and no excessively long strings)
     * @param input the string
     * @param lenght the amount of characters after which the string will be cut
     * @return
     */
    public static String niceString(String input, int lenght) {
        if(input == null) {
            return "";
        }
        if(input.length() > lenght) {
            return input.substring(0, lenght) + "...";
        }
        return input;
    }
}
