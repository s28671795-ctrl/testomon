/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.ConfigSyntax;
import com.typesafe.config.impl.Path;
import com.typesafe.config.impl.SerializedConfigValue;
import com.typesafe.config.impl.SimpleConfigOrigin;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class ConfigImplUtil {
    static boolean equalsHandlingNull(Object a, Object b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == b) {
            return true;
        }
        return a.equals(b);
    }

    static boolean isC0Control(int codepoint) {
        return codepoint >= 0 && codepoint <= 31;
    }

    public static String renderJsonString(String s2) {
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        block9: for (int i = 0; i < s2.length(); ++i) {
            char c = s2.charAt(i);
            switch (c) {
                case '\"': {
                    sb.append("\\\"");
                    continue block9;
                }
                case '\\': {
                    sb.append("\\\\");
                    continue block9;
                }
                case '\n': {
                    sb.append("\\n");
                    continue block9;
                }
                case '\b': {
                    sb.append("\\b");
                    continue block9;
                }
                case '\f': {
                    sb.append("\\f");
                    continue block9;
                }
                case '\r': {
                    sb.append("\\r");
                    continue block9;
                }
                case '\t': {
                    sb.append("\\t");
                    continue block9;
                }
                default: {
                    if (ConfigImplUtil.isC0Control(c)) {
                        sb.append(String.format("\\u%04x", c));
                        continue block9;
                    }
                    sb.append(c);
                }
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    static String renderStringUnquotedIfPossible(String s2) {
        if (s2.length() == 0) {
            return ConfigImplUtil.renderJsonString(s2);
        }
        int first = s2.codePointAt(0);
        if (Character.isDigit(first) || first == 45) {
            return ConfigImplUtil.renderJsonString(s2);
        }
        if (s2.startsWith("include") || s2.startsWith("true") || s2.startsWith("false") || s2.startsWith("null") || s2.contains("//")) {
            return ConfigImplUtil.renderJsonString(s2);
        }
        for (int i = 0; i < s2.length(); ++i) {
            char c = s2.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || c == '-') continue;
            return ConfigImplUtil.renderJsonString(s2);
        }
        return s2;
    }

    static boolean isWhitespace(int codepoint) {
        switch (codepoint) {
            case 10: 
            case 32: 
            case 160: 
            case 8199: 
            case 8239: 
            case 65279: {
                return true;
            }
        }
        return Character.isWhitespace(codepoint);
    }

    public static String unicodeTrim(String s2) {
        int length = s2.length();
        if (length == 0) {
            return s2;
        }
        int start2 = 0;
        while (start2 < length) {
            char c = s2.charAt(start2);
            if (c == ' ' || c == '\n') {
                ++start2;
                continue;
            }
            int cp = s2.codePointAt(start2);
            if (!ConfigImplUtil.isWhitespace(cp)) break;
            start2 += Character.charCount(cp);
        }
        int end = length;
        while (end > start2) {
            int delta;
            int cp;
            char c = s2.charAt(end - 1);
            if (c == ' ' || c == '\n') {
                --end;
                continue;
            }
            if (Character.isLowSurrogate(c)) {
                cp = s2.codePointAt(end - 2);
                delta = 2;
            } else {
                cp = s2.codePointAt(end - 1);
                delta = 1;
            }
            if (!ConfigImplUtil.isWhitespace(cp)) break;
            end -= delta;
        }
        return s2.substring(start2, end);
    }

    public static ConfigException extractInitializerError(ExceptionInInitializerError e) {
        Throwable cause = e.getCause();
        if (cause != null && cause instanceof ConfigException) {
            return (ConfigException)cause;
        }
        throw e;
    }

    static File urlToFile(URL url2) {
        try {
            return new File(url2.toURI());
        }
        catch (URISyntaxException e) {
            return new File(url2.getPath());
        }
        catch (IllegalArgumentException e) {
            return new File(url2.getPath());
        }
    }

    public static String joinPath(String ... elements) {
        return new Path(elements).render();
    }

    public static String joinPath(List<String> elements) {
        return ConfigImplUtil.joinPath(elements.toArray(new String[0]));
    }

    public static List<String> splitPath(String path) {
        ArrayList<String> elements = new ArrayList<String>();
        for (Path p = Path.newPath(path); p != null; p = p.remainder()) {
            elements.add(p.first());
        }
        return elements;
    }

    public static ConfigOrigin readOrigin(ObjectInputStream in) throws IOException {
        return SerializedConfigValue.readOrigin(in, null);
    }

    public static void writeOrigin(ObjectOutputStream out, ConfigOrigin origin) throws IOException {
        SerializedConfigValue.writeOrigin(new DataOutputStream(out), (SimpleConfigOrigin)origin, null);
    }

    static String toCamelCase(String originalName) {
        String[] words = originalName.split("-+");
        StringBuilder nameBuilder = new StringBuilder(originalName.length());
        for (String word : words) {
            if (nameBuilder.length() == 0) {
                nameBuilder.append(word);
                continue;
            }
            nameBuilder.append(word.substring(0, 1).toUpperCase());
            nameBuilder.append(word.substring(1));
        }
        return nameBuilder.toString();
    }

    private static char underscoreMappings(int num) {
        switch (num) {
            case 1: {
                return '.';
            }
            case 2: {
                return '-';
            }
            case 3: {
                return '_';
            }
        }
        return '\u0000';
    }

    static String envVariableAsProperty(String variable, String prefix) throws ConfigException {
        StringBuilder builder = new StringBuilder();
        String strippedPrefix = variable.substring(prefix.length(), variable.length());
        int underscores = 0;
        for (char c : strippedPrefix.toCharArray()) {
            if (c == '_') {
                ++underscores;
                continue;
            }
            if (underscores > 0 && underscores < 4) {
                builder.append(ConfigImplUtil.underscoreMappings(underscores));
            } else if (underscores > 3) {
                throw new ConfigException.BadPath(variable, "Environment variable contains an un-mapped number of underscores.");
            }
            underscores = 0;
            builder.append(c);
        }
        if (underscores > 0 && underscores < 4) {
            builder.append(ConfigImplUtil.underscoreMappings(underscores));
        } else if (underscores > 3) {
            throw new ConfigException.BadPath(variable, "Environment variable contains an un-mapped number of underscores.");
        }
        return builder.toString();
    }

    public static ConfigSyntax syntaxFromExtension(String filename) {
        if (filename == null) {
            return null;
        }
        if (filename.endsWith(".json")) {
            return ConfigSyntax.JSON;
        }
        if (filename.endsWith(".conf")) {
            return ConfigSyntax.CONF;
        }
        if (filename.endsWith(".properties")) {
            return ConfigSyntax.PROPERTIES;
        }
        return null;
    }
}

