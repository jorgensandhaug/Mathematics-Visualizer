package org.utils;

import java.util.regex.Pattern;

/**
 * Handles validation of input
 */
public abstract class RegexUtils {
    private static Pattern namePattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    /**
     * Returns true if a name is valid, else false
     */
    public static boolean isValidName(String name){
        return namePattern.matcher(name).matches();
    }

}
