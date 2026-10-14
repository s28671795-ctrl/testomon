/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.annotations.Beta;
import com.google.common.net.MediaType;
import java.net.URI;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.net.MediaTypeSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@Beta
public final class HttpServletSupport {
    private HttpServletSupport() {
    }

    public static void addNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
    }

    public static void setUTF8Encoding(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
    }

    public static void setContentType(HttpServletResponse response, String contentType) {
        response.setHeader("Content-Type", contentType);
    }

    public static String getRequestPathWithoutContext(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (request.getPathInfo() == null) {
            return servletPath;
        }
        return servletPath + request.getPathInfo();
    }

    public static URI getFullRequestURI(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String encodedQuery = StringSupport.trimOrNull(request.getQueryString());
        if (encodedQuery != null) {
            requestUrl.append("?").append(encodedQuery);
        }
        return URI.create(requestUrl.toString());
    }

    public static boolean validateContentType(HttpServletRequest request, Set<MediaType> validTypes, boolean noContentTypeIsValid, boolean isOneOfStrategy) {
        return MediaTypeSupport.validateContentType(request.getContentType(), validTypes, noContentTypeIsValid, isOneOfStrategy);
    }
}

