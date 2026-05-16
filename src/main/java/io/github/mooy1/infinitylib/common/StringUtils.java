package io.github.mooy1.infinitylib.common;

public final class StringUtils {

    private StringUtils() {}

    public static boolean isBlank(String string) {
        // Is default blank if null or length = 0
        if (string == null || string.length() == 0) {
            return true;
        }

        // Otherwise loop through the chars to see if any are NOT whitespace
        for (char chr : string.toCharArray()) {
            if (!Character.isWhitespace(chr)) {
                return false;
            }
        }

        // No non-whitespace chars, is blank
        return true;
    }
}
