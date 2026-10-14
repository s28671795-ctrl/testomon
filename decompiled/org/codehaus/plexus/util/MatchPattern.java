/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.codehaus.plexus.util.SelectorUtils;

public class MatchPattern {
    private final String source;
    private final String regexPattern;
    private final String separator;
    private final String[] tokenized;
    private final char[][] tokenizedChar;

    private MatchPattern(String source2, String separator) {
        this.regexPattern = SelectorUtils.isRegexPrefixedPattern(source2) ? source2.substring("%regex[".length(), source2.length() - "]".length()) : null;
        this.source = SelectorUtils.isAntPrefixedPattern(source2) ? source2.substring("%ant[".length(), source2.length() - "]".length()) : source2;
        this.separator = separator;
        this.tokenized = MatchPattern.tokenizePathToString(this.source, separator);
        this.tokenizedChar = new char[this.tokenized.length][];
        for (int i = 0; i < this.tokenized.length; ++i) {
            this.tokenizedChar[i] = this.tokenized[i].toCharArray();
        }
    }

    public boolean matchPath(String str, boolean isCaseSensitive) {
        if (this.regexPattern != null) {
            return str.matches(this.regexPattern);
        }
        return SelectorUtils.matchAntPathPattern(this, str, this.separator, isCaseSensitive);
    }

    boolean matchPath(String str, char[][] strDirs, boolean isCaseSensitive) {
        if (this.regexPattern != null) {
            return str.matches(this.regexPattern);
        }
        return SelectorUtils.matchAntPathPattern(this.getTokenizedPathChars(), strDirs, isCaseSensitive);
    }

    public boolean matchPatternStart(String str, boolean isCaseSensitive) {
        if (this.regexPattern != null) {
            return true;
        }
        String altStr = str.replace('\\', '/');
        return SelectorUtils.matchAntPathPatternStart(this, str, File.separator, isCaseSensitive) || SelectorUtils.matchAntPathPatternStart(this, altStr, "/", isCaseSensitive);
    }

    public String[] getTokenizedPathString() {
        return this.tokenized;
    }

    public char[][] getTokenizedPathChars() {
        return this.tokenizedChar;
    }

    public boolean startsWith(String string) {
        return this.source.startsWith(string);
    }

    static String[] tokenizePathToString(String path, String separator) {
        ArrayList<String> ret = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(path, separator);
        while (st.hasMoreTokens()) {
            ret.add(st.nextToken());
        }
        return ret.toArray(new String[0]);
    }

    static char[][] tokenizePathToCharArray(String path, String separator) {
        String[] tokenizedName = MatchPattern.tokenizePathToString(path, separator);
        char[][] tokenizedNameChar = new char[tokenizedName.length][];
        for (int i = 0; i < tokenizedName.length; ++i) {
            tokenizedNameChar[i] = tokenizedName[i].toCharArray();
        }
        return tokenizedNameChar;
    }

    public static MatchPattern fromString(String source2) {
        return new MatchPattern(source2, File.separator);
    }
}

