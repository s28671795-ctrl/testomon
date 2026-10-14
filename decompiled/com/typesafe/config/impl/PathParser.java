/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigSyntax;
import com.typesafe.config.ConfigValueType;
import com.typesafe.config.impl.AbstractConfigValue;
import com.typesafe.config.impl.ConfigImplUtil;
import com.typesafe.config.impl.ConfigNodePath;
import com.typesafe.config.impl.Path;
import com.typesafe.config.impl.PathBuilder;
import com.typesafe.config.impl.SimpleConfigOrigin;
import com.typesafe.config.impl.Token;
import com.typesafe.config.impl.Tokenizer;
import com.typesafe.config.impl.Tokens;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class PathParser {
    static ConfigOrigin apiOrigin = SimpleConfigOrigin.newSimple("path parameter");

    PathParser() {
    }

    static ConfigNodePath parsePathNode(String path) {
        return PathParser.parsePathNode(path, ConfigSyntax.CONF);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static ConfigNodePath parsePathNode(String path, ConfigSyntax flavor) {
        try (StringReader reader = new StringReader(path);){
            Iterator<Token> tokens = Tokenizer.tokenize(apiOrigin, reader, flavor);
            tokens.next();
            ConfigNodePath configNodePath = PathParser.parsePathNodeExpression(tokens, apiOrigin, path, flavor);
            return configNodePath;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Path parsePath(String path) {
        Path speculated = PathParser.speculativeFastParsePath(path);
        if (speculated != null) {
            return speculated;
        }
        try (StringReader reader = new StringReader(path);){
            Iterator<Token> tokens = Tokenizer.tokenize(apiOrigin, reader, ConfigSyntax.CONF);
            tokens.next();
            Path path2 = PathParser.parsePathExpression(tokens, apiOrigin, path);
            return path2;
        }
    }

    protected static Path parsePathExpression(Iterator<Token> expression, ConfigOrigin origin) {
        return PathParser.parsePathExpression(expression, origin, null, null, ConfigSyntax.CONF);
    }

    protected static Path parsePathExpression(Iterator<Token> expression, ConfigOrigin origin, String originalText) {
        return PathParser.parsePathExpression(expression, origin, originalText, null, ConfigSyntax.CONF);
    }

    protected static ConfigNodePath parsePathNodeExpression(Iterator<Token> expression, ConfigOrigin origin) {
        return PathParser.parsePathNodeExpression(expression, origin, null, ConfigSyntax.CONF);
    }

    protected static ConfigNodePath parsePathNodeExpression(Iterator<Token> expression, ConfigOrigin origin, String originalText, ConfigSyntax flavor) {
        ArrayList<Token> pathTokens = new ArrayList<Token>();
        Path path = PathParser.parsePathExpression(expression, origin, originalText, pathTokens, flavor);
        return new ConfigNodePath(path, pathTokens);
    }

    protected static Path parsePathExpression(Iterator<Token> expression, ConfigOrigin origin, String originalText, ArrayList<Token> pathTokens, ConfigSyntax flavor) {
        ArrayList<Element> buf = new ArrayList<Element>();
        buf.add(new Element("", false));
        if (!expression.hasNext()) {
            throw new ConfigException.BadPath(origin, originalText, "Expecting a field name or path here, but got nothing");
        }
        while (expression.hasNext()) {
            String text;
            Token t2 = expression.next();
            if (pathTokens != null) {
                pathTokens.add(t2);
            }
            if (Tokens.isIgnoredWhitespace(t2)) continue;
            if (Tokens.isValueWithType(t2, ConfigValueType.STRING)) {
                AbstractConfigValue v = Tokens.getValue(t2);
                String s2 = v.transformToString();
                PathParser.addPathText(buf, true, s2);
                continue;
            }
            if (t2 == Tokens.END) continue;
            if (Tokens.isValue(t2)) {
                AbstractConfigValue v = Tokens.getValue(t2);
                if (pathTokens != null) {
                    pathTokens.remove(pathTokens.size() - 1);
                    pathTokens.addAll(PathParser.splitTokenOnPeriod(t2, flavor));
                }
                text = v.transformToString();
            } else if (Tokens.isUnquotedText(t2)) {
                if (pathTokens != null) {
                    pathTokens.remove(pathTokens.size() - 1);
                    pathTokens.addAll(PathParser.splitTokenOnPeriod(t2, flavor));
                }
                text = Tokens.getUnquotedText(t2);
            } else {
                throw new ConfigException.BadPath(origin, originalText, "Token not allowed in path expression: " + t2 + " (you can double-quote this token if you really want it here)");
            }
            PathParser.addPathText(buf, false, text);
        }
        PathBuilder pb = new PathBuilder();
        for (Element e : buf) {
            if (e.sb.length() == 0 && !e.canBeEmpty) {
                throw new ConfigException.BadPath(origin, originalText, "path has a leading, trailing, or two adjacent period '.' (use quoted \"\" empty string if you want an empty element)");
            }
            pb.appendKey(e.sb.toString());
        }
        return pb.result();
    }

    private static Collection<Token> splitTokenOnPeriod(Token t2, ConfigSyntax flavor) {
        String tokenText = t2.tokenText();
        if (tokenText.equals(".")) {
            return Collections.singletonList(t2);
        }
        String[] splitToken = tokenText.split("\\.");
        ArrayList<Token> splitTokens = new ArrayList<Token>();
        for (String s2 : splitToken) {
            if (flavor == ConfigSyntax.CONF) {
                splitTokens.add(Tokens.newUnquotedText(t2.origin(), s2));
            } else {
                splitTokens.add(Tokens.newString(t2.origin(), s2, "\"" + s2 + "\""));
            }
            splitTokens.add(Tokens.newUnquotedText(t2.origin(), "."));
        }
        if (tokenText.charAt(tokenText.length() - 1) != '.') {
            splitTokens.remove(splitTokens.size() - 1);
        }
        return splitTokens;
    }

    private static void addPathText(List<Element> buf, boolean wasQuoted, String newText) {
        int i = wasQuoted ? -1 : newText.indexOf(46);
        Element current = buf.get(buf.size() - 1);
        if (i < 0) {
            current.sb.append(newText);
            if (wasQuoted && current.sb.length() == 0) {
                current.canBeEmpty = true;
            }
        } else {
            current.sb.append(newText.substring(0, i));
            buf.add(new Element("", false));
            PathParser.addPathText(buf, false, newText.substring(i + 1));
        }
    }

    private static boolean looksUnsafeForFastParser(String s2) {
        boolean lastWasDot = true;
        int len = s2.length();
        if (s2.isEmpty()) {
            return true;
        }
        if (s2.charAt(0) == '.') {
            return true;
        }
        if (s2.charAt(len - 1) == '.') {
            return true;
        }
        for (int i = 0; i < len; ++i) {
            char c = s2.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_') {
                lastWasDot = false;
                continue;
            }
            if (c == '.') {
                if (lastWasDot) {
                    return true;
                }
                lastWasDot = true;
                continue;
            }
            if (c == '-') {
                if (!lastWasDot) continue;
                return true;
            }
            return true;
        }
        return lastWasDot;
    }

    private static Path fastPathBuild(Path tail, String s2, int end) {
        int splitAt = s2.lastIndexOf(46, end - 1);
        Path withOneMoreElement = new Path(s2.substring(splitAt + 1, end), tail);
        if (splitAt < 0) {
            return withOneMoreElement;
        }
        return PathParser.fastPathBuild(withOneMoreElement, s2, splitAt);
    }

    private static Path speculativeFastParsePath(String path) {
        String s2 = ConfigImplUtil.unicodeTrim(path);
        if (PathParser.looksUnsafeForFastParser(s2)) {
            return null;
        }
        return PathParser.fastPathBuild(null, s2, s2.length());
    }

    static class Element {
        StringBuilder sb;
        boolean canBeEmpty;

        Element(String initial, boolean canBeEmpty) {
            this.canBeEmpty = canBeEmpty;
            this.sb = new StringBuilder(initial);
        }

        public String toString() {
            return "Element(" + this.sb.toString() + "," + this.canBeEmpty + ")";
        }
    }
}

