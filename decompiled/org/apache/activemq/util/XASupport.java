/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

public class XASupport {
    public static String toString(int flags) {
        int nonStandardFlags;
        if (flags == 0) {
            return "TMNOFLAGS";
        }
        StringBuilder result = new StringBuilder();
        if (XASupport.hasFlag(flags, 0x800000)) {
            XASupport.add(result, "TMENDRSCAN");
        }
        if (XASupport.hasFlag(flags, 0x20000000)) {
            XASupport.add(result, "TMFAIL");
        }
        if (XASupport.hasFlag(flags, 0x200000)) {
            XASupport.add(result, "TMJOIN");
        }
        if (XASupport.hasFlag(flags, 0x40000000)) {
            XASupport.add(result, "TMONEPHASE");
        }
        if (XASupport.hasFlag(flags, 0x8000000)) {
            XASupport.add(result, "TMRESUME");
        }
        if (XASupport.hasFlag(flags, 0x1000000)) {
            XASupport.add(result, "TMSTARTRSCAN");
        }
        if (XASupport.hasFlag(flags, 0x4000000)) {
            XASupport.add(result, "TMSUCCESS");
        }
        if (XASupport.hasFlag(flags, 0x2000000)) {
            XASupport.add(result, "TMSUSPEND");
        }
        if ((nonStandardFlags = flags & 0xFF7FFFFF & 0xDFFFFFFF & 0xFFDFFFFF & 0xBFFFFFFF & 0xF7FFFFFF & 0xFEFFFFFF & 0xFBFFFFFF & 0xFDFFFFFF) != 0) {
            XASupport.add(result, String.format("0x%08x", nonStandardFlags));
        }
        return result.toString();
    }

    private static boolean hasFlag(int flags, int flag) {
        return (flags & flag) == flag;
    }

    private static void add(StringBuilder result, String string) {
        if (result.length() > 0) {
            result.append(" | ");
        }
        result.append(string);
    }
}

