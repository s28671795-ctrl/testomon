/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils.pattern;

import java.util.regex.Pattern;

public class PatternMatchUtils {
    static final char ANY_DIGIT_WILDCARD = '$';
    static final char ANY_LETTER_WILDCARD = '@';
    static final char ANY_SYMBOL_WILDCARD = '_';
    static final char ANY_NUMBER_OF_SYMBOLS_WILDCARD = '%';
    static final String ANY_DIGIT_REGEX = "[0-9]";
    static final String ANY_LETTER_REGEX = "[A-Z]";
    static final String ANY_SYMBOL_REGEX = "[0-9A-Z]";
    static final String ANY_NUMBER_OF_SYMBOLS_REGEX = "[0-9A-Z]*";

    public static Pattern getWildcardsPattern(String valueWithWildcards) {
        return Pattern.compile(PatternMatchUtils.getWildcardsPatternString(valueWithWildcards));
    }

    public static String getWildcardsPatternString(String valueWithWildcards) {
        return "^" + valueWithWildcards.replace(String.valueOf('$'), ANY_DIGIT_REGEX).replace(String.valueOf('@'), ANY_LETTER_REGEX).replace(String.valueOf('_'), ANY_SYMBOL_REGEX).replace(String.valueOf('%'), ANY_NUMBER_OF_SYMBOLS_REGEX) + "$";
    }
}

