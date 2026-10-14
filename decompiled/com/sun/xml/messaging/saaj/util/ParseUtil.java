/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.util;

public class ParseUtil {
    private static char unescape(String s2, int i) {
        return (char)Integer.parseInt(s2.substring(i + 1, i + 3), 16);
    }

    public static String decode(String s2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < s2.length()) {
            char c = s2.charAt(i);
            if (c != '%') {
                ++i;
            } else {
                try {
                    c = ParseUtil.unescape(s2, i);
                    i += 3;
                    if ((c & 0x80) != 0) {
                        switch (c >> 4) {
                            case 12: 
                            case 13: {
                                char c2 = ParseUtil.unescape(s2, i);
                                i += 3;
                                c = (char)((c & 0x1F) << 6 | c2 & 0x3F);
                                break;
                            }
                            case 14: {
                                char c2 = ParseUtil.unescape(s2, i);
                                char c3 = ParseUtil.unescape(s2, i += 3);
                                i += 3;
                                c = (char)((c & 0xF) << 12 | (c2 & 0x3F) << 6 | c3 & 0x3F);
                                break;
                            }
                            default: {
                                throw new IllegalArgumentException();
                            }
                        }
                    }
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}

