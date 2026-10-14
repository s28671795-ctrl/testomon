/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.policy.impl;

public final class StringUtils {
    private StringUtils() {
    }

    static String combine(String s1, String s2) {
        return s1 == null ? s2 : s1;
    }

    static boolean equals(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    static boolean compatible(String s1, String s2) {
        return s1 == null || s2 == null || s1.equals(s2);
    }
}

