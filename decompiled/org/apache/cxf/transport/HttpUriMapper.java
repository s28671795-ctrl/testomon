/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

public final class HttpUriMapper {
    private HttpUriMapper() {
    }

    public static String getContextName(String path) {
        String contextName = "";
        int idx = path.lastIndexOf(47);
        if (idx >= 0) {
            contextName = path.substring(0, idx);
        }
        return contextName;
    }

    public static String getResourceBase(String path) {
        String servletMap = "";
        int idx = path.lastIndexOf(47);
        if (idx >= 0) {
            servletMap = path.substring(idx);
        }
        if ("".equals(servletMap) || "".equals(path)) {
            servletMap = "/";
        }
        return servletMap;
    }
}

