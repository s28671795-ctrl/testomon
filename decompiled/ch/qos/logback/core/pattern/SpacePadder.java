/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.pattern;

public class SpacePadder {
    static final String[] SPACES = new String[]{" ", "  ", "    ", "        ", "                ", "                                "};

    public static final void leftPad(StringBuilder buf, String s2, int desiredLength) {
        int actualLen = 0;
        if (s2 != null) {
            actualLen = s2.length();
        }
        if (actualLen < desiredLength) {
            SpacePadder.spacePad(buf, desiredLength - actualLen);
        }
        if (s2 != null) {
            buf.append(s2);
        }
    }

    public static final void rightPad(StringBuilder buf, String s2, int desiredLength) {
        int actualLen = 0;
        if (s2 != null) {
            actualLen = s2.length();
        }
        if (s2 != null) {
            buf.append(s2);
        }
        if (actualLen < desiredLength) {
            SpacePadder.spacePad(buf, desiredLength - actualLen);
        }
    }

    public static final void spacePad(StringBuilder sbuf, int length) {
        while (length >= 32) {
            sbuf.append(SPACES[5]);
            length -= 32;
        }
        for (int i = 4; i >= 0; --i) {
            if ((length & 1 << i) == 0) continue;
            sbuf.append(SPACES[i]);
        }
    }
}

