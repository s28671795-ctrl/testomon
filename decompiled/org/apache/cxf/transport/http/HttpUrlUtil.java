/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

public final class HttpUrlUtil {
    private HttpUrlUtil() {
    }

    public static boolean checkContextPath(String urlName, String target) {
        String pathString = urlName;
        if (!pathString.endsWith("/")) {
            pathString = pathString + "/";
        }
        return target.startsWith(pathString);
    }
}

