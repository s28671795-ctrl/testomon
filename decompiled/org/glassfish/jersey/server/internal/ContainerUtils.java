/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal;

public class ContainerUtils {
    private static final String[] TOKENS = new String[]{"{", "}", "\\", "^", "|", "`"};
    private static final String[] REPLACEMENTS = new String[]{"%7B", "%7D", "%5C", "%5E", "%7C", "%60"};

    public static String encodeUnsafeCharacters(String originalQueryString) {
        if (originalQueryString == null) {
            return null;
        }
        String result = originalQueryString;
        for (int i = 0; i < TOKENS.length; ++i) {
            if (!originalQueryString.contains(TOKENS[i])) continue;
            result = result.replace(TOKENS[i], REPLACEMENTS[i]);
        }
        return result;
    }

    public static String reduceLeadingSlashes(String path) {
        int start2;
        int length;
        if (path == null || (length = path.length()) == 0) {
            return path;
        }
        for (start2 = 0; start2 != length && "/".indexOf(path.charAt(start2)) != -1; ++start2) {
        }
        return path.substring(start2 > 0 ? start2 - 1 : 0);
    }

    public static String getHandlerPath(String uri) {
        if (uri == null || uri.length() == 0 || !uri.contains("?")) {
            return uri;
        }
        return uri.substring(0, uri.indexOf("?"));
    }
}

