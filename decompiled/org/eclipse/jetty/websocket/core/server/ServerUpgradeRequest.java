/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;

public class ServerUpgradeRequest {
    private final URI requestURI;
    private final String queryString;
    private final HttpServletRequest request;
    private final boolean secure;
    private final WebSocketNegotiation negotiation;
    private List<HttpCookie> cookies;
    private Map<String, List<String>> parameterMap;

    public ServerUpgradeRequest(WebSocketNegotiation negotiation) throws BadMessageException {
        this.negotiation = negotiation;
        this.request = negotiation.getRequest();
        this.queryString = this.request.getQueryString();
        this.secure = this.request.isSecure();
        try {
            StringBuffer uri = this.request.getRequestURL();
            if (this.queryString != null) {
                uri.append("?").append(this.queryString);
            }
            uri.replace(0, uri.indexOf(":"), this.secure ? "wss" : "ws");
            this.requestURI = new URI(uri.toString());
        }
        catch (Throwable t2) {
            throw new BadMessageException("Bad WebSocket UpgradeRequest", t2);
        }
    }

    public X509Certificate[] getCertificates() {
        return (X509Certificate[])this.request.getAttribute("jakarta.servlet.request.X509Certificate");
    }

    public List<HttpCookie> getCookies() {
        if (this.cookies == null) {
            Cookie[] reqCookies = this.request.getCookies();
            this.cookies = reqCookies != null ? Arrays.stream(reqCookies).map(c -> new HttpCookie(c.getName(), c.getValue())).collect(Collectors.toList()) : Collections.emptyList();
        }
        return this.cookies;
    }

    public List<ExtensionConfig> getExtensions() {
        return this.negotiation.getOfferedExtensions();
    }

    public String getHeader(String name) {
        return this.request.getHeader(name);
    }

    public int getHeaderInt(String name) {
        return this.request.getIntHeader(name);
    }

    public Map<String, List<String>> getHeadersMap() {
        TreeMap<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
        Enumeration<String> headerNames = this.request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, Collections.list(this.request.getHeaders(name)));
            }
        }
        return headers;
    }

    public List<String> getHeaders(String name) {
        Enumeration<String> headers = this.request.getHeaders(name);
        if (headers == null || !headers.hasMoreElements()) {
            return null;
        }
        return Collections.list(headers);
    }

    public String getHost() {
        return this.requestURI.getHost();
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

    public String getHttpVersion() {
        return this.request.getProtocol();
    }

    public Locale getLocale() {
        return this.request.getLocale();
    }

    public Enumeration<Locale> getLocales() {
        return this.request.getLocales();
    }

    public SocketAddress getLocalSocketAddress() {
        return new InetSocketAddress(this.request.getLocalAddr(), this.request.getLocalPort());
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    public String getOrigin() {
        return this.getHeader("Origin");
    }

    public Map<String, List<String>> getParameterMap() {
        Map<String, String[]> requestParams;
        if (this.parameterMap == null && (requestParams = this.request.getParameterMap()) != null) {
            this.parameterMap = new HashMap<String, List<String>>(requestParams.size());
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                this.parameterMap.put(entry.getKey(), Arrays.asList(entry.getValue()));
            }
        }
        return this.parameterMap;
    }

    public String getProtocolVersion() {
        String version = this.request.getHeader(HttpHeader.SEC_WEBSOCKET_VERSION.asString());
        if (version == null) {
            return Integer.toString(13);
        }
        return version;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public SocketAddress getRemoteSocketAddress() {
        return new InetSocketAddress(this.request.getRemoteAddr(), this.request.getRemotePort());
    }

    public String getRequestPath() {
        String contextPath = this.request.getContextPath();
        String requestPath = this.request.getRequestURI();
        if (requestPath.startsWith(contextPath)) {
            requestPath = requestPath.substring(contextPath.length());
        }
        return requestPath;
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    public String getPathInContext() {
        return URIUtil.addPaths(this.request.getServletPath(), this.request.getPathInfo());
    }

    public Object getServletAttribute(String name) {
        return this.request.getAttribute(name);
    }

    public Map<String, Object> getServletAttributes() {
        HashMap<String, Object> attributes = new HashMap<String, Object>(2);
        Enumeration<String> attributeNames = this.request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            attributes.put(name, this.request.getAttribute(name));
        }
        return attributes;
    }

    public Map<String, List<String>> getServletParameters() {
        return this.getParameterMap();
    }

    public HttpSession getSession() {
        return this.request.getSession(false);
    }

    public List<String> getSubProtocols() {
        return this.negotiation.getOfferedSubprotocols();
    }

    public Principal getUserPrincipal() {
        return this.request.getUserPrincipal();
    }

    public boolean hasSubProtocol(String subprotocol) {
        for (String protocol : this.getSubProtocols()) {
            if (!protocol.equalsIgnoreCase(subprotocol)) continue;
            return true;
        }
        return false;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public boolean isUserInRole(String role) {
        return this.request.isUserInRole(role);
    }

    public void setServletAttribute(String name, Object value) {
        this.request.setAttribute(name, value);
    }
}

