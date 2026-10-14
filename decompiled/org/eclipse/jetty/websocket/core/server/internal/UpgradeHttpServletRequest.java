/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;

public class UpgradeHttpServletRequest
implements HttpServletRequest {
    private static final String UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE = "Feature unsupported after Upgraded to WebSocket";
    private HttpServletRequest request;
    private final ServletContext context;
    private final DispatcherType dispatcher;
    private final String method;
    private final String protocol;
    private final String scheme;
    private final boolean secure;
    private final String requestURI;
    private final StringBuffer requestURL;
    private final String pathInfo;
    private final String pathTranslated;
    private final String servletPath;
    private final String query;
    private final String authType;
    private final Cookie[] cookies;
    private final String remoteUser;
    private final Principal principal;
    private final Authentication authentication;
    private final UserIdentity.Scope scope;
    private final Map<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, String[]> parameters = new TreeMap<String, String[]>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, Object> attributes = new HashMap<String, Object>(2);
    private final List<Locale> locales = new ArrayList<Locale>(2);
    private final HttpSession session;
    private final InetSocketAddress localAddress;
    private final String localName;
    private final InetSocketAddress remoteAddress;
    private final String remoteName;
    private final InetSocketAddress serverAddress;
    private boolean isAsyncStarted;
    private boolean isAsyncSupported;

    public UpgradeHttpServletRequest(HttpServletRequest httpRequest) {
        this.request = httpRequest;
        this.context = httpRequest.getServletContext();
        this.dispatcher = httpRequest.getDispatcherType();
        this.method = httpRequest.getMethod();
        this.protocol = httpRequest.getProtocol();
        this.scheme = httpRequest.getScheme();
        this.secure = httpRequest.isSecure();
        this.requestURI = httpRequest.getRequestURI();
        this.requestURL = httpRequest.getRequestURL();
        this.pathInfo = httpRequest.getPathInfo();
        this.pathTranslated = httpRequest.getPathTranslated();
        this.servletPath = httpRequest.getServletPath();
        this.query = httpRequest.getQueryString();
        this.authType = httpRequest.getAuthType();
        this.cookies = httpRequest.getCookies();
        this.session = httpRequest.getSession(false);
        this.remoteUser = httpRequest.getRemoteUser();
        this.principal = httpRequest.getUserPrincipal();
        Request baseRequest = Objects.requireNonNull(Request.getBaseRequest(httpRequest));
        this.authentication = baseRequest.getAuthentication();
        this.scope = baseRequest.getUserIdentityScope();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            this.headers.put(name, Collections.list(httpRequest.getHeaders(name)));
        }
        this.parameters.putAll(httpRequest.getParameterMap());
        Enumeration<Locale> localeElements = httpRequest.getLocales();
        while (localeElements.hasMoreElements()) {
            this.locales.add(localeElements.nextElement());
        }
        this.localAddress = InetSocketAddress.createUnresolved(httpRequest.getLocalAddr(), httpRequest.getLocalPort());
        this.localName = httpRequest.getLocalName();
        this.remoteAddress = InetSocketAddress.createUnresolved(httpRequest.getRemoteAddr(), httpRequest.getRemotePort());
        this.remoteName = httpRequest.getRemoteHost();
        this.serverAddress = InetSocketAddress.createUnresolved(httpRequest.getServerName(), httpRequest.getServerPort());
    }

    public void upgrade() {
        Enumeration<String> attributeNames = this.request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            this.attributes.put(name, this.request.getAttribute(name));
        }
        this.isAsyncStarted = this.request.isAsyncStarted();
        this.isAsyncSupported = this.request.isAsyncSupported();
        this.request = null;
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

    @Override
    public String getAuthType() {
        return this.authType;
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies;
    }

    @Override
    public String getHeader(String name) {
        List<String> values2 = this.headers.get(name);
        if (values2 == null || values2.isEmpty()) {
            return null;
        }
        return values2.get(0);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values2 = this.headers.get(name);
        if (values2 == null) {
            return Collections.emptyEnumeration();
        }
        return Collections.enumeration(values2);
    }

    public Map<String, List<String>> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

    @Override
    public long getDateHeader(String name) {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getDateHeader(name);
    }

    @Override
    public int getIntHeader(String name) {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getIntHeader(name);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return this.pathInfo;
    }

    @Override
    public String getPathTranslated() {
        return this.pathTranslated;
    }

    @Override
    public String getContextPath() {
        return this.context.getContextPath();
    }

    @Override
    public String getQueryString() {
        return this.query;
    }

    @Override
    public String getRemoteUser() {
        return this.remoteUser;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.authentication instanceof Authentication.User) {
            return ((Authentication.User)this.authentication).isUserInRole(this.scope, role);
        }
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        return this.requestURL;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (create && this.session == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.session;
    }

    @Override
    public HttpSession getSession() {
        return this.session;
    }

    @Override
    public String getRequestedSessionId() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getRequestedSessionId();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.isRequestedSessionIdValid();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.isRequestedSessionIdFromUrl();
    }

    @Override
    public Object getAttribute(String name) {
        if (this.request == null) {
            return this.attributes.get(name);
        }
        return this.request.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        if (this.request == null) {
            return Collections.enumeration(this.attributes.keySet());
        }
        return this.request.getAttributeNames();
    }

    @Override
    public String getParameter(String name) {
        String[] values2 = this.parameters.get(name);
        if (values2 == null || values2.length == 0) {
            return null;
        }
        return values2[0];
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parameters;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public String getServerName() {
        return this.serverAddress.getHostString();
    }

    @Override
    public int getServerPort() {
        return this.serverAddress.getPort();
    }

    @Override
    public String getRemoteAddr() {
        return this.remoteAddress.getHostString();
    }

    @Override
    public int getRemotePort() {
        return this.remoteAddress.getPort();
    }

    @Override
    public String getRemoteHost() {
        return this.remoteName;
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (this.request == null) {
            this.attributes.put(name, value);
        } else {
            this.request.setAttribute(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
        if (this.request == null) {
            this.attributes.remove(name);
        } else {
            this.request.removeAttribute(name);
        }
    }

    @Override
    public Locale getLocale() {
        if (this.locales.isEmpty()) {
            return Locale.getDefault();
        }
        return this.locales.get(0);
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(this.locales);
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public String getRealPath(String path) {
        return this.context.getRealPath(path);
    }

    @Override
    public String getLocalName() {
        return this.localName;
    }

    @Override
    public String getLocalAddr() {
        return this.localAddress.getHostString();
    }

    @Override
    public int getLocalPort() {
        return this.localAddress.getPort();
    }

    @Override
    public ServletContext getServletContext() {
        return this.context;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return this.dispatcher;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.authenticate(response);
    }

    @Override
    public String changeSessionId() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.changeSessionId();
    }

    @Override
    public AsyncContext getAsyncContext() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getAsyncContext();
    }

    @Override
    public String getCharacterEncoding() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getInputStream();
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getPart(name);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getParts();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getReader();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.getRequestDispatcher(path);
    }

    @Override
    public boolean isAsyncStarted() {
        if (this.request == null) {
            return this.isAsyncStarted;
        }
        return this.request.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        if (this.request == null) {
            return this.isAsyncSupported;
        }
        return this.request.isAsyncSupported();
    }

    @Override
    public void login(String username, String password) throws ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this.request.login(username, password);
    }

    @Override
    public void logout() throws ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this.request.logout();
    }

    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        this.request.setCharacterEncoding(enc);
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.startAsync(servletRequest, servletResponse);
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        if (this.request == null) {
            throw new UnsupportedOperationException(UNSUPPORTED_AFTER_WEBSOCKET_UPGRADE);
        }
        return this.request.upgrade(handlerClass);
    }
}

