/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.MappingMatch;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.PushBuilder;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public interface HttpServletRequest
extends ServletRequest {
    public static final String BASIC_AUTH = "BASIC";
    public static final String FORM_AUTH = "FORM";
    public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";
    public static final String DIGEST_AUTH = "DIGEST";

    public String getAuthType();

    public Cookie[] getCookies();

    public long getDateHeader(String var1);

    public String getHeader(String var1);

    public Enumeration<String> getHeaders(String var1);

    public Enumeration<String> getHeaderNames();

    public int getIntHeader(String var1);

    default public HttpServletMapping getHttpServletMapping() {
        return new HttpServletMapping(){

            @Override
            public String getMatchValue() {
                return "";
            }

            @Override
            public String getPattern() {
                return "";
            }

            @Override
            public String getServletName() {
                return "";
            }

            @Override
            public MappingMatch getMappingMatch() {
                return null;
            }

            public String toString() {
                return "MappingImpl{matchValue=" + this.getMatchValue() + ", pattern=" + this.getPattern() + ", servletName=" + this.getServletName() + ", mappingMatch=" + this.getMappingMatch() + "} HttpServletRequest {" + HttpServletRequest.this.toString() + "}";
            }
        };
    }

    public String getMethod();

    public String getPathInfo();

    public String getPathTranslated();

    default public PushBuilder newPushBuilder() {
        return null;
    }

    public String getContextPath();

    public String getQueryString();

    public String getRemoteUser();

    public boolean isUserInRole(String var1);

    public Principal getUserPrincipal();

    public String getRequestedSessionId();

    public String getRequestURI();

    public StringBuffer getRequestURL();

    public String getServletPath();

    public HttpSession getSession(boolean var1);

    public HttpSession getSession();

    public String changeSessionId();

    public boolean isRequestedSessionIdValid();

    public boolean isRequestedSessionIdFromCookie();

    public boolean isRequestedSessionIdFromURL();

    @Deprecated
    public boolean isRequestedSessionIdFromUrl();

    public boolean authenticate(HttpServletResponse var1) throws IOException, ServletException;

    public void login(String var1, String var2) throws ServletException;

    public void logout() throws ServletException;

    public Collection<Part> getParts() throws IOException, ServletException;

    public Part getPart(String var1) throws IOException, ServletException;

    public <T extends HttpUpgradeHandler> T upgrade(Class<T> var1) throws IOException, ServletException;

    default public Map<String, String> getTrailerFields() {
        return Collections.emptyMap();
    }

    default public boolean isTrailerFieldsReady() {
        return true;
    }
}

