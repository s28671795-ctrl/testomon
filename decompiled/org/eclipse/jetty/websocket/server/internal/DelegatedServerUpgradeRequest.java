/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.net.SocketAddress;
import java.net.URI;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.jetty.websocket.api.ExtensionConfig;
import org.eclipse.jetty.websocket.common.JettyExtensionConfig;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeRequest;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeRequest;

public class DelegatedServerUpgradeRequest
implements JettyServerUpgradeRequest {
    private final ServerUpgradeRequest upgradeRequest;

    public DelegatedServerUpgradeRequest(ServerUpgradeRequest request) {
        this.upgradeRequest = request;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return this.upgradeRequest.getCookies();
    }

    @Override
    public List<ExtensionConfig> getExtensions() {
        return this.upgradeRequest.getExtensions().stream().map(JettyExtensionConfig::new).collect(Collectors.toList());
    }

    @Override
    public String getHeader(String name) {
        return this.upgradeRequest.getHeader(name);
    }

    @Override
    public int getHeaderInt(String name) {
        return this.upgradeRequest.getHeaderInt(name);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.upgradeRequest.getHeadersMap();
    }

    @Override
    public List<String> getHeaders(String name) {
        return this.upgradeRequest.getHeaders(name);
    }

    @Override
    public String getHost() {
        return this.upgradeRequest.getHost();
    }

    @Override
    public String getHttpVersion() {
        return this.upgradeRequest.getHttpVersion();
    }

    @Override
    public String getMethod() {
        return this.upgradeRequest.getMethod();
    }

    @Override
    public String getOrigin() {
        return this.upgradeRequest.getOrigin();
    }

    @Override
    public Map<String, List<String>> getParameterMap() {
        return this.upgradeRequest.getParameterMap();
    }

    @Override
    public String getProtocolVersion() {
        return this.upgradeRequest.getProtocolVersion();
    }

    @Override
    public String getQueryString() {
        return this.upgradeRequest.getQueryString();
    }

    @Override
    public URI getRequestURI() {
        return this.upgradeRequest.getRequestURI();
    }

    @Override
    public HttpSession getSession() {
        return this.upgradeRequest.getSession();
    }

    @Override
    public List<String> getSubProtocols() {
        return this.upgradeRequest.getSubProtocols();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.upgradeRequest.getUserPrincipal();
    }

    @Override
    public boolean hasSubProtocol(String subprotocol) {
        return this.upgradeRequest.hasSubProtocol(subprotocol);
    }

    @Override
    public boolean isSecure() {
        return this.upgradeRequest.isSecure();
    }

    @Override
    public X509Certificate[] getCertificates() {
        return this.upgradeRequest.getCertificates();
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return this.upgradeRequest.getHttpServletRequest();
    }

    @Override
    public Locale getLocale() {
        return this.upgradeRequest.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return this.upgradeRequest.getLocales();
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return this.upgradeRequest.getLocalSocketAddress();
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return this.upgradeRequest.getRemoteSocketAddress();
    }

    @Override
    public String getRequestPath() {
        return this.upgradeRequest.getRequestPath();
    }

    @Override
    public Object getServletAttribute(String name) {
        return this.upgradeRequest.getServletAttribute(name);
    }

    @Override
    public Map<String, Object> getServletAttributes() {
        return this.upgradeRequest.getServletAttributes();
    }

    @Override
    public Map<String, List<String>> getServletParameters() {
        return this.upgradeRequest.getServletParameters();
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.upgradeRequest.isUserInRole(role);
    }

    @Override
    public void setServletAttribute(String name, Object value) {
        this.upgradeRequest.setServletAttribute(name, value);
    }
}

