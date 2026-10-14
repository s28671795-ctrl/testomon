/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpSession
 */
package org.apache.cxf.transport.http;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

public class HttpServletRequestSnapshot
extends HttpServletRequestWrapper {
    private String authType;
    private String characterEncoding;
    private int contentLength;
    private String contentType;
    private String contextPath;
    private Cookie[] cookies;
    private String localAddr;
    private Locale local;
    private Enumeration locals;
    private String localName;
    private int localPort = -1;
    private String method;
    private String pathInfo;
    private String pathTranslated;
    private String protocol;
    private String queryString;
    private String remoteAddr;
    private String remoteHost;
    private int remotePort = -1;
    private String remoteUser;
    private String requestURI;
    private StringBuffer requestURL;
    private String schema;
    private String serverName;
    private int serverPort = -1;
    private String servletPath;
    private HttpSession session;
    private Principal principal;
    private Enumeration<String> requestHeaderNames;
    private Map<String, Enumeration<String>> headersMap = new ConcurrentHashMap<String, Enumeration<String>>();
    private String requestedSessionId;

    public HttpServletRequestSnapshot(HttpServletRequest request) {
        super(request);
        this.authType = request.getAuthType();
        this.characterEncoding = request.getCharacterEncoding();
        this.contentLength = request.getContentLength();
        this.contentType = request.getContentType();
        this.contextPath = request.getContextPath();
        this.cookies = request.getCookies();
        this.requestHeaderNames = request.getHeaderNames();
        Enumeration tmp = request.getHeaderNames();
        while (tmp.hasMoreElements()) {
            String key = (String)tmp.nextElement();
            this.headersMap.put(key, request.getHeaders(key));
        }
        this.localAddr = request.getLocalAddr();
        this.local = request.getLocale();
        this.localName = request.getLocalName();
        this.localPort = request.getLocalPort();
        this.method = request.getMethod();
        this.pathInfo = request.getPathInfo();
        this.pathTranslated = request.getPathTranslated();
        this.protocol = request.getProtocol();
        this.queryString = request.getQueryString();
        this.remoteAddr = request.getRemoteAddr();
        this.remoteHost = request.getRemoteHost();
        this.remotePort = request.getRemotePort();
        this.remoteUser = request.getRemoteUser();
        this.requestURI = request.getRequestURI();
        this.requestURL = request.getRequestURL();
        this.requestedSessionId = request.getRequestedSessionId();
        this.schema = request.getScheme();
        this.serverName = request.getServerName();
        this.serverPort = request.getServerPort();
        this.servletPath = request.getServletPath();
        if (request.isRequestedSessionIdValid()) {
            this.session = request.getSession();
        }
        this.principal = request.getUserPrincipal();
    }

    public String getAuthType() {
        return this.authType;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public Cookie[] getCookies() {
        return this.cookies;
    }

    public String getHeader(String name) {
        if (this.headersMap.get(name) != null && this.headersMap.get(name).hasMoreElements()) {
            return this.headersMap.get(name).nextElement();
        }
        return null;
    }

    public Enumeration getHeaderNames() {
        return this.requestHeaderNames;
    }

    public Enumeration getHeaders(String name) {
        return this.headersMap.get(name);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getPathTranslated() {
        return this.pathTranslated;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRemoteUser() {
        return this.remoteUser;
    }

    public String getRequestURI() {
        return this.requestURI;
    }

    public StringBuffer getRequestURL() {
        return this.requestURL;
    }

    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    public String getServletPath() {
        return this.servletPath;
    }

    public HttpSession getSession() {
        return this.session;
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getLocalAddr() {
        return this.localAddr;
    }

    public String getLocalName() {
        return this.localName;
    }

    public int getLocalPort() {
        return this.localPort;
    }

    public Locale getLocale() {
        return this.local;
    }

    public Enumeration getLocales() {
        return this.locals;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public String getScheme() {
        return this.schema;
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getServerPort() {
        return this.serverPort;
    }
}

