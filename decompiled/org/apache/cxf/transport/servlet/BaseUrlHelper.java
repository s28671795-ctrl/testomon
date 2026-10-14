/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package org.apache.cxf.transport.servlet;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.wsdl.http.AddressType;

public final class BaseUrlHelper {
    private BaseUrlHelper() {
    }

    public static String getBaseURL(HttpServletRequest request) {
        String reqPrefix = request.getRequestURL().toString();
        String pathInfo = request.getPathInfo();
        if (!"/".equals(pathInfo) || reqPrefix.contains(";")) {
            String servletPath;
            StringBuilder sb = new StringBuilder();
            URI uri = URI.create(reqPrefix);
            sb.append(uri.getScheme()).append("://").append(uri.getRawAuthority());
            String contextPath = request.getContextPath();
            if (contextPath != null) {
                sb.append(contextPath);
            }
            if ((servletPath = request.getServletPath()) != null) {
                sb.append(servletPath);
            }
            reqPrefix = sb.toString();
        }
        return reqPrefix;
    }

    public static void setAddress(AbstractDestination dest, String absAddress) {
        dest.getEndpointInfo().setAddress(absAddress);
        if (dest.getEndpointInfo().getExtensor(AddressType.class) != null) {
            dest.getEndpointInfo().getExtensor(AddressType.class).setLocation(absAddress);
        }
    }
}

