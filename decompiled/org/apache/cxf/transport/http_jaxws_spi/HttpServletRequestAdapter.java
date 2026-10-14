/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.AsyncContext
 *  javax.servlet.DispatcherType
 *  javax.servlet.ReadListener
 *  javax.servlet.RequestDispatcher
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletInputStream
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  javax.servlet.http.HttpUpgradeHandler
 *  javax.servlet.http.Part
 */
package org.apache.cxf.transport.http_jaxws_spi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ReadListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpExchange;

class HttpServletRequestAdapter
implements HttpServletRequest {
    private HttpExchange exchange;
    private HttpContext context;
    private String characterEncoding;
    private ServletInputStreamAdapter servletInputStreamAdapter;
    private BufferedReader reader;

    HttpServletRequestAdapter(HttpExchange exchange) {
        this.exchange = exchange;
        this.context = exchange.getHttpContext();
    }

    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String name) {
        return this.exchange.getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(this.exchange.getAttributeNames());
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return this.getHeader("Content-Type");
    }

    public DispatcherType getDispatcherType() {
        throw new UnsupportedOperationException();
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this.servletInputStreamAdapter == null) {
            this.servletInputStreamAdapter = new ServletInputStreamAdapter(this.exchange.getRequestBody());
        }
        return this.servletInputStreamAdapter;
    }

    public String getLocalAddr() {
        InetAddress ia;
        InetSocketAddress isa = this.exchange.getLocalAddress();
        if (isa != null && (ia = isa.getAddress()) != null) {
            return ia.getHostAddress();
        }
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        throw new UnsupportedOperationException();
    }

    public String getLocalName() {
        InetAddress ia;
        InetSocketAddress isa = this.exchange.getLocalAddress();
        if (isa != null && (ia = isa.getAddress()) != null) {
            return ia.getHostName();
        }
        return null;
    }

    public int getLocalPort() {
        InetSocketAddress isa = this.exchange.getLocalAddress();
        return isa != null ? isa.getPort() : 0;
    }

    public String getParameter(String name) {
        throw new UnsupportedOperationException();
    }

    public Map<String, String[]> getParameterMap() {
        throw new UnsupportedOperationException();
    }

    public Enumeration<String> getParameterNames() {
        throw new UnsupportedOperationException();
    }

    public String[] getParameterValues(String name) {
        throw new UnsupportedOperationException();
    }

    public String getProtocol() {
        return this.exchange.getProtocol();
    }

    public BufferedReader getReader() throws IOException {
        if (this.reader == null) {
            InputStreamReader isr = this.characterEncoding == null ? new InputStreamReader(this.exchange.getRequestBody()) : new InputStreamReader(this.exchange.getRequestBody(), this.characterEncoding);
            this.reader = new BufferedReader(isr);
        }
        return this.reader;
    }

    @Deprecated
    public String getRealPath(String path) {
        throw new UnsupportedOperationException();
    }

    public String getRemoteAddr() {
        InetAddress ia;
        InetSocketAddress isa = this.exchange.getRemoteAddress();
        if (isa != null && (ia = isa.getAddress()) != null) {
            return ia.getHostAddress();
        }
        return null;
    }

    public String getRemoteHost() {
        InetAddress ia;
        InetSocketAddress isa = this.exchange.getRemoteAddress();
        if (isa != null && (ia = isa.getAddress()) != null) {
            return ia.getHostName();
        }
        return null;
    }

    public int getRemotePort() {
        InetSocketAddress isa = this.exchange.getRemoteAddress();
        return isa != null ? isa.getPort() : 0;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException();
    }

    public String getScheme() {
        return this.exchange.getScheme();
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public boolean isAsyncStarted() {
        throw new UnsupportedOperationException();
    }

    public boolean isAsyncSupported() {
        throw new UnsupportedOperationException();
    }

    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    public void setAttribute(String name, Object o) {
        throw new UnsupportedOperationException();
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        this.characterEncoding = env;
    }

    public AsyncContext startAsync() {
        throw new UnsupportedOperationException();
    }

    public AsyncContext startAsync(ServletRequest request, ServletResponse response) {
        throw new UnsupportedOperationException();
    }

    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    public String getAuthType() {
        return null;
    }

    public String getContextPath() {
        return this.exchange.getContextPath();
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String name) {
        String s2 = this.getHeader(name);
        return s2 != null ? Long.parseLong(s2) : 0L;
    }

    public String getHeader(String name) {
        return this.exchange.getRequestHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.exchange.getRequestHeaders().keySet());
    }

    public Enumeration<String> getHeaders(String name) {
        List<String> list = this.exchange.getRequestHeaders().get(name);
        return list != null ? Collections.enumeration(list) : null;
    }

    public int getIntHeader(String name) {
        String s2 = this.getHeader(name);
        return s2 != null ? Integer.parseInt(s2) : 0;
    }

    public String getMethod() {
        return this.exchange.getRequestMethod();
    }

    public Part getPart(String name) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    public Collection<Part> getParts() throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    public String getPathInfo() {
        return this.exchange.getPathInfo();
    }

    public String getPathTranslated() {
        return null;
    }

    public String getQueryString() {
        return this.exchange.getQueryString();
    }

    public String getRemoteUser() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getRequestURI() {
        return this.exchange.getRequestURI();
    }

    public StringBuffer getRequestURL() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.exchange.getScheme());
        sb.append("://");
        String host2 = this.getHeader("Host");
        if (host2 != null) {
            sb.append(host2);
        } else {
            InetSocketAddress la = this.exchange.getLocalAddress();
            if (la != null) {
                sb.append(la.getHostName());
                if (la.getPort() > 0) {
                    sb.append(':');
                    sb.append(la.getPort());
                }
            } else {
                sb.append("localhost");
            }
        }
        sb.append(this.exchange.getContextPath());
        sb.append(this.context.getPath());
        return sb;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public HttpSession getSession(boolean create) {
        return null;
    }

    public Principal getUserPrincipal() {
        return this.exchange.getUserPrincipal();
    }

    public boolean isRequestedSessionIdFromCookie() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequestedSessionIdFromURL() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isUserInRole(String role) {
        return this.exchange.isUserInRole(role);
    }

    public void login(String username, String password) throws ServletException {
        throw new UnsupportedOperationException();
    }

    public void logout() throws ServletException {
        throw new UnsupportedOperationException();
    }

    public long getContentLengthLong() {
        throw new UnsupportedOperationException();
    }

    public String changeSessionId() {
        throw new UnsupportedOperationException();
    }

    public <T extends HttpUpgradeHandler> T upgrade(Class<T> cls) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    private static class ServletInputStreamAdapter
    extends ServletInputStream {
        private InputStream delegate;

        ServletInputStreamAdapter(InputStream delegate) {
            this.delegate = delegate;
        }

        public int read() throws IOException {
            return this.delegate.read();
        }

        public boolean isFinished() {
            throw new UnsupportedOperationException();
        }

        public boolean isReady() {
            throw new UnsupportedOperationException();
        }

        public void setReadListener(ReadListener arg0) {
            throw new UnsupportedOperationException();
        }
    }
}

