/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import java.util.ArrayList;
import org.codehaus.commons.nullanalysis.Nullable;

public class StringPattern {
    public static final int INCLUDE = 0;
    public static final int EXCLUDE = 1;
    private final int mode;
    @Nullable
    private final String pattern;
    public static final StringPattern[] PATTERNS_ALL = new StringPattern[]{new StringPattern("*")};
    public static final StringPattern[] PATTERNS_NONE = new StringPattern[0];

    public StringPattern(int mode, String pattern) {
        this.mode = mode;
        this.pattern = pattern;
    }

    public StringPattern(@Nullable String pattern) {
        this.mode = 0;
        this.pattern = pattern;
    }

    public int getMode() {
        return this.mode;
    }

    public boolean matches(String text) {
        return StringPattern.wildmatch(this.pattern, text);
    }

    public static StringPattern[] parseCombinedPattern(String combinedPattern) {
        ArrayList<StringPattern> al = new ArrayList<StringPattern>();
        int k = 0;
        while (k < combinedPattern.length()) {
            int l;
            int patternMode;
            char c = combinedPattern.charAt(k);
            if (c == '+') {
                patternMode = 0;
            } else if (c == '-') {
                patternMode = 1;
                ++k;
            } else {
                patternMode = 0;
            }
            for (l = ++k; l < combinedPattern.length() && (c = combinedPattern.charAt(l)) != '+' && c != '-'; ++l) {
            }
            al.add(new StringPattern(patternMode, combinedPattern.substring(k, l)));
            k = l;
        }
        return al.toArray(new StringPattern[al.size()]);
    }

    public static boolean matches(StringPattern[] patterns, String text) {
        if (patterns == null) {
            return false;
        }
        for (int i = patterns.length - 1; i >= 0; --i) {
            if (!patterns[i].matches(text)) continue;
            return patterns[i].getMode() == 0;
        }
        return false;
    }

    public String toString() {
        return (char)(this.mode == 0 ? 43 : (this.mode == 1 ? 45 : 63)) + this.pattern;
    }

    private static boolean wildmatch(@Nullable String pattern, String text) {
        int i;
        if (pattern == null) {
            return true;
        }
        block4: for (i = 0; i < pattern.length(); ++i) {
            char c = pattern.charAt(i);
            switch (c) {
                case '?': {
                    if (i != text.length()) continue block4;
                    return false;
                }
                case '*': {
                    if (pattern.length() == i + 1) {
                        return true;
                    }
                    pattern = pattern.substring(i + 1);
                    while (i <= text.length()) {
                        if (StringPattern.wildmatch(pattern, text.substring(i))) {
                            return true;
                        }
                        ++i;
                    }
                    return false;
                }
                default: {
                    if (i == text.length()) {
                        return false;
                    }
                    if (text.charAt(i) == c) continue block4;
                    return false;
                }
            }
        }
        return text.length() == i;
    }
}

