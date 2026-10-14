/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.pac4j.core.exception.TechnicalException;

public final class CommonHelper {
    private static final int INDEX_NOT_FOUND = -1;
    private static final Map<String, Constructor> constructorsCache = new HashMap<String, Constructor>();

    public static boolean isNotBlank(String s2) {
        return s2 != null && !s2.isBlank();
    }

    public static boolean isBlank(String s2) {
        return !CommonHelper.isNotBlank(s2);
    }

    public static boolean areEquals(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    public static boolean areEqualsIgnoreCaseAndTrim(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        return s1 != null && s2 != null && s1.trim().equalsIgnoreCase(s2.trim());
    }

    public static boolean areNotEquals(String s1, String s2) {
        return !CommonHelper.areEquals(s1, s2);
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !CommonHelper.isEmpty(coll);
    }

    public static void assertTrue(boolean value, String message) {
        if (!value) {
            throw new TechnicalException(message);
        }
    }

    public static void assertNotBlank(String name, String value, String msg) {
        CommonHelper.assertTrue(!CommonHelper.isBlank(value), name + " cannot be blank" + (String)(msg != null ? ": " + msg : ""));
    }

    public static void assertNotBlank(String name, String value) {
        CommonHelper.assertNotBlank(name, value, null);
    }

    public static void assertNotNull(String name, Object obj) {
        CommonHelper.assertTrue(obj != null, name + " cannot be null");
    }

    public static void assertNull(String name, Object obj) {
        CommonHelper.assertTrue(obj == null, name + " must be null");
    }

    public static String addParameter(String url2, String name, String value) {
        if (url2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(url2);
            if (name != null) {
                if (url2.indexOf("?") >= 0) {
                    sb.append("&");
                } else {
                    sb.append("?");
                }
                sb.append(name);
                sb.append("=");
                if (value != null) {
                    sb.append(CommonHelper.urlEncode(value));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            String message = "Unable to encode text : " + text;
            throw new TechnicalException(message, e);
        }
    }

    public static String toNiceString(Class<?> clazz, Object ... args2) {
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(clazz.getSimpleName());
        sb.append("# |");
        boolean b = true;
        for (Object arg : args2) {
            if (b) {
                sb.append(" ");
                sb.append(arg);
                sb.append(":");
            } else {
                sb.append(" ");
                sb.append(arg);
                sb.append(" |");
            }
            b = !b;
        }
        return sb.toString();
    }

    public static String randomString(int size) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < size) {
            String suffix = UUID.randomUUID().toString().replace("-", "");
            builder.append(suffix);
        }
        return builder.substring(0, size);
    }

    public static Date newDate(Date original) {
        return original != null ? new Date(original.getTime()) : null;
    }

    public static URI asURI(String s2) {
        try {
            return new URI(s2);
        }
        catch (URISyntaxException e) {
            throw new TechnicalException("Cannot make an URI from: " + s2, e);
        }
    }

    public static String substringBetween(String str, String open, String close) {
        int end;
        if (str == null || open == null || close == null) {
            return null;
        }
        int start2 = str.indexOf(open);
        if (start2 != -1 && (end = str.indexOf(close, start2 + open.length())) != -1) {
            return str.substring(start2 + open.length(), end);
        }
        return null;
    }

    public static String substringAfter(String str, String separator) {
        if (CommonHelper.isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    public static String substringBefore(String str, String separator) {
        if (CommonHelper.isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Constructor getConstructor(String name) throws ClassNotFoundException, NoSuchMethodException {
        Constructor<?> constructor = constructorsCache.get(name);
        if (constructor == null) {
            Map<String, Constructor> map = constructorsCache;
            synchronized (map) {
                constructor = constructorsCache.get(name);
                if (constructor == null) {
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(name, true, CommonHelper.class.getClassLoader());
                    }
                    catch (ClassNotFoundException e) {
                        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
                        clazz = tccl == null ? Class.forName(name) : Class.forName(name, true, tccl);
                    }
                    constructor = clazz.getDeclaredConstructor(new Class[0]);
                    constructorsCache.put(name, constructor);
                }
            }
        }
        return constructor;
    }

    public static String ifBlank(String value, String defaultValue) {
        return CommonHelper.isBlank(value) ? defaultValue : value;
    }
}

