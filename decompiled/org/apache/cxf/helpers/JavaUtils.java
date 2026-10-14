/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.cxf.common.util.SystemPropertyAction;

public final class JavaUtils {
    static final char KEYWORD_PREFIX = '_';
    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"));
    private static boolean isJava11Compatible;
    private static boolean isJava9Compatible;
    private static boolean isJava8Before161;

    private JavaUtils() {
    }

    public static boolean isJavaKeyword(String keyword) {
        return KEYWORDS.contains(keyword);
    }

    public static String makeNonJavaKeyword(String keyword) {
        return '_' + keyword;
    }

    public static boolean isJava9Compatible() {
        return isJava9Compatible;
    }

    public static boolean isJava11Compatible() {
        return isJava11Compatible;
    }

    private static void setJava9Compatible(boolean java9Compatible) {
        isJava9Compatible = java9Compatible;
    }

    private static void setJava11Compatible(boolean java11Compatible) {
        isJava11Compatible = java11Compatible;
    }

    public static boolean isJava8Before161() {
        return isJava8Before161;
    }

    static {
        String version = SystemPropertyAction.getProperty("java.version");
        try {
            isJava8Before161 = version != null && version.startsWith("1.8.0_") && Integer.parseInt(version.substring(6)) < 161;
        }
        catch (NumberFormatException ex) {
            isJava8Before161 = false;
        }
        if (version.indexOf(46) > 0) {
            version = version.substring(0, version.indexOf(46));
        }
        if (version.indexOf(45) > 0) {
            version = version.substring(0, version.indexOf(45));
        }
        JavaUtils.setJava9Compatible(Integer.valueOf(version) >= 9);
        JavaUtils.setJava11Compatible(Integer.valueOf(version) >= 11);
    }
}

