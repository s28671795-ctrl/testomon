/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(PathUtils.class);
    private static final char COLON = ':';
    private static final char OPEN = '{';
    private static final char CLOSE = '}';
    private static final char SLASH = '/';
    private static final Pattern TRIM_PATTERN = Pattern.compile("^/*(.*?)/*$");

    public static String parsePath(String uri, Map<String, String> patterns) {
        if (uri == null) {
            return null;
        }
        if (StringUtils.isBlank(uri)) {
            return String.valueOf('/');
        }
        StringCharacterIterator ci = new StringCharacterIterator(uri);
        StringBuilder pathBuffer = new StringBuilder();
        char c = ci.first();
        if (c == '\uffff') {
            return String.valueOf('/');
        }
        do {
            if (c == '{') {
                String regexBuffer = PathUtils.cutParameter(ci, patterns);
                if (regexBuffer == null) {
                    LOGGER.warn("Operation path \"{}\" contains syntax error.", (Object)uri);
                    return null;
                }
                pathBuffer.append(regexBuffer);
                continue;
            }
            int length = pathBuffer.length();
            if (c == '/' && length != 0 && pathBuffer.charAt(length - 1) == '/') continue;
            pathBuffer.append(c);
        } while ((c = ci.next()) != '\uffff');
        return pathBuffer.toString();
    }

    public static String collectPath(String ... pathParts) {
        StringBuilder sb = new StringBuilder();
        for (String item : pathParts) {
            String path;
            if (StringUtils.isBlank(item) || !StringUtils.isNotBlank(path = PathUtils.trimPath(item))) continue;
            sb.append('/').append(path);
        }
        return sb.length() > 0 ? sb.toString() : String.valueOf('/');
    }

    private static String trimPath(String value) {
        Matcher matcher = TRIM_PATTERN.matcher(value);
        return matcher.find() && StringUtils.isNotBlank(matcher.group(1)) ? matcher.group(1) : null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String cutParameter(CharacterIterator ci, Map<String, String> patterns) {
        StringBuilder regexBuffer = new StringBuilder();
        int braceCount = 1;
        char regexChar = ci.next();
        while (regexChar != '\uffff') {
            if (regexChar == '{') {
                ++braceCount;
            } else if (regexChar == '}' && --braceCount == 0) break;
            regexBuffer.append(regexChar);
            regexChar = ci.next();
        }
        if (braceCount != 0) {
            return null;
        }
        String regex2 = StringUtils.trimToNull(regexBuffer.toString());
        if (regex2 == null) {
            return null;
        }
        StringBuilder pathBuffer = new StringBuilder();
        pathBuffer.append('{');
        int index = regex2.indexOf(58);
        if (index != -1) {
            String name = StringUtils.trimToNull(regex2.substring(0, index));
            String value = StringUtils.trimToNull(regex2.substring(index + 1, regex2.length()));
            if (name == null) return null;
            pathBuffer.append(name);
            if (value != null) {
                patterns.put(name, value);
            }
        } else {
            pathBuffer.append(regex2);
        }
        pathBuffer.append('}');
        return pathBuffer.toString();
    }
}

