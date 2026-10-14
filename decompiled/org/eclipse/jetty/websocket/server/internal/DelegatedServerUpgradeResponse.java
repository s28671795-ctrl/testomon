/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jetty.websocket.api.ExtensionConfig;
import org.eclipse.jetty.websocket.common.JettyExtensionConfig;
import org.eclipse.jetty.websocket.core.server.ServerUpgradeResponse;
import org.eclipse.jetty.websocket.server.JettyServerUpgradeResponse;

public class DelegatedServerUpgradeResponse
implements JettyServerUpgradeResponse {
    private final ServerUpgradeResponse upgradeResponse;

    public DelegatedServerUpgradeResponse(ServerUpgradeResponse response) {
        this.upgradeResponse = response;
    }

    @Override
    public void addHeader(String name, String value) {
        this.upgradeResponse.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        this.upgradeResponse.setHeader(name, value);
    }

    @Override
    public void setHeader(String name, List<String> values2) {
        this.upgradeResponse.setHeader(name, values2);
    }

    @Override
    public String getAcceptedSubProtocol() {
        return this.upgradeResponse.getAcceptedSubProtocol();
    }

    @Override
    public List<ExtensionConfig> getExtensions() {
        return this.upgradeResponse.getExtensions().stream().map(JettyExtensionConfig::new).collect(Collectors.toList());
    }

    @Override
    public String getHeader(String name) {
        return this.upgradeResponse.getHeader(name);
    }

    @Override
    public Set<String> getHeaderNames() {
        return this.upgradeResponse.getHeaderNames();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return this.upgradeResponse.getHeadersMap();
    }

    @Override
    public List<String> getHeaders(String name) {
        return this.upgradeResponse.getHeaders(name);
    }

    @Override
    public int getStatusCode() {
        return this.upgradeResponse.getStatusCode();
    }

    @Override
    public void sendForbidden(String message) throws IOException {
        this.upgradeResponse.sendForbidden(message);
    }

    @Override
    public void setAcceptedSubProtocol(String protocol) {
        this.upgradeResponse.setAcceptedSubProtocol(protocol);
    }

    @Override
    public void setExtensions(List<ExtensionConfig> configs) {
        this.upgradeResponse.setExtensions(configs.stream().map(c -> new org.eclipse.jetty.websocket.core.ExtensionConfig(c.getName(), c.getParameters())).collect(Collectors.toList()));
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.upgradeResponse.setStatusCode(statusCode);
    }

    @Override
    public boolean isCommitted() {
        return this.upgradeResponse.isCommitted();
    }

    @Override
    public void sendError(int statusCode, String message) throws IOException {
        this.upgradeResponse.sendError(statusCode, message);
    }
}

