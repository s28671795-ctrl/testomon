/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {
    private static final Predicate<String> NOT_EMPTY = s2 -> !s2.isEmpty();

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        if (str != null) {
            int len = str.length();
            for (int x = 0; x < len; ++x) {
                if (str.charAt(x) <= ' ') continue;
                return false;
            }
        }
        return true;
    }

    public static Predicate<String> notEmpty() {
        return NOT_EMPTY;
    }

    public static boolean isEmpty(List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return list.size() == 1 && StringUtils.isEmpty(list.get(0));
    }

    public static String diff(String str1, String str2) {
        int index = str1.lastIndexOf(str2);
        if (index > -1) {
            return str1.substring(str2.length());
        }
        return str1;
    }

    public static String getFirstFound(String contents, String regex2) {
        if (StringUtils.isEmpty(regex2) || StringUtils.isEmpty(contents)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex2, 64);
        Matcher matcher = pattern.matcher(contents);
        if (matcher.find()) {
            if (matcher.groupCount() > 0) {
                return matcher.group(1);
            }
            return matcher.group();
        }
        return null;
    }

    public static String addDefaultPortIfMissing(String urlString) {
        return StringUtils.addDefaultPortIfMissing(urlString, "80");
    }

    public static String addDefaultPortIfMissing(String urlString, String defaultPort) {
        try {
            if (new URL(urlString).getPort() != -1) {
                return urlString;
            }
        }
        catch (MalformedURLException e) {
            return urlString;
        }
        String regex2 = "http://([^/]+)";
        String found = StringUtils.getFirstFound(urlString, regex2);
        if (!StringUtils.isEmpty(found)) {
            String replacer = "http://" + found + ':' + defaultPort;
            return urlString.replaceFirst(regex2, replacer);
        }
        return urlString;
    }

    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public static String uncapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return new StringBuilder(str.length()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static byte[] toBytesUTF8(String str) {
        return StringUtils.toBytes(str, StandardCharsets.UTF_8.name());
    }

    public static byte[] toBytesASCII(String str) {
        return StringUtils.toBytes(str, "US-ASCII");
    }

    public static byte[] toBytes(String str, String enc) {
        try {
            return str.getBytes(enc);
        }
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            hexString.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return hexString.toString();
    }
}

