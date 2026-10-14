/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;

public class ServerUpgradeResponse {
    private final HttpServletResponse response;
    private final WebSocketNegotiation negotiation;

    public ServerUpgradeResponse(WebSocketNegotiation negotiation) {
        this.negotiation = negotiation;
        this.response = negotiation.getResponse();
        Objects.requireNonNull(this.response, "HttpServletResponse must not be null");
    }

    public void addHeader(String name, String value) {
        if (HttpHeader.SEC_WEBSOCKET_SUBPROTOCOL.is(name)) {
            this.setAcceptedSubProtocol(value);
            return;
        }
        if (HttpHeader.SEC_WEBSOCKET_EXTENSIONS.is(name)) {
            this.addExtensions(ExtensionConfig.parseList(value));
            return;
        }
        this.response.addHeader(name, value);
    }

    public void setHeader(String name, String value) {
        if (HttpHeader.SEC_WEBSOCKET_SUBPROTOCOL.is(name)) {
            this.setAcceptedSubProtocol(value);
            return;
        }
        if (HttpHeader.SEC_WEBSOCKET_EXTENSIONS.is(name)) {
            this.setExtensions(ExtensionConfig.parseList(value));
            return;
        }
        this.response.setHeader(name, value);
    }

    public void setHeader(String name, List<String> values2) {
        if (HttpHeader.SEC_WEBSOCKET_SUBPROTOCOL.is(name)) {
            if (values2 == null || values2.isEmpty()) {
                this.setAcceptedSubProtocol(null);
            } else if (values2.size() == 1) {
                this.setAcceptedSubProtocol(values2.get(0));
            } else {
                throw new IllegalArgumentException("multiple subprotocols");
            }
        }
        if (HttpHeader.SEC_WEBSOCKET_EXTENSIONS.is(name)) {
            List<ExtensionConfig> extensions = Collections.emptyList();
            if (values2 != null) {
                extensions = values2.stream().flatMap(s2 -> ExtensionConfig.parseList(s2).stream()).collect(Collectors.toList());
            }
            this.setExtensions(extensions);
            return;
        }
        this.response.setHeader(name, null);
        if (values2 != null) {
            values2.forEach(value -> this.response.addHeader(name, (String)value));
        }
    }

    public String getAcceptedSubProtocol() {
        return this.getHeader(HttpHeader.SEC_WEBSOCKET_SUBPROTOCOL.asString());
    }

    public List<ExtensionConfig> getExtensions() {
        return this.negotiation.getNegotiatedExtensions();
    }

    public String getHeader(String name) {
        return this.response.getHeader(name);
    }

    public Set<String> getHeaderNames() {
        return Set.copyOf(this.response.getHeaderNames());
    }

    public Map<String, List<String>> getHeadersMap() {
        Map<String, List> headers = this.response.getHeaderNames().stream().collect(Collectors.toMap(name -> name, name -> new ArrayList<String>(this.response.getHeaders((String)name))));
        return Collections.unmodifiableMap(headers);
    }

    public List<String> getHeaders(String name) {
        return List.copyOf(this.response.getHeaders(name));
    }

    public int getStatusCode() {
        return this.response.getStatus();
    }

    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    public void sendError(int statusCode, String message) throws IOException {
        this.response.sendError(statusCode, message);
        this.response.flushBuffer();
    }

    public void sendForbidden(String message) throws IOException {
        this.sendError(403, message);
    }

    public void setAcceptedSubProtocol(String protocol) {
        this.negotiation.setSubprotocol(protocol);
    }

    public void addExtensions(List<ExtensionConfig> configs) {
        ArrayList<ExtensionConfig> combinedConfig = new ArrayList<ExtensionConfig>();
        combinedConfig.addAll(this.getExtensions());
        combinedConfig.addAll(configs);
        this.setExtensions(combinedConfig);
    }

    public void setExtensions(List<ExtensionConfig> configs) {
        for (ExtensionConfig config : configs) {
            if (config.getName().startsWith("@")) continue;
            long matches = this.negotiation.getOfferedExtensions().stream().filter(e -> e.getName().equals(config.getName())).count();
            if (matches < 1L) {
                throw new IllegalArgumentException("Extension not a requested extension");
            }
            matches = configs.stream().filter(e -> e.getName().equals(config.getName())).count();
            if (matches <= 1L) continue;
            throw new IllegalArgumentException("Multiple extensions of the same name");
        }
        this.negotiation.setNegotiatedExtensions(configs);
    }

    public void setStatusCode(int statusCode) {
        this.response.setStatus(statusCode);
    }

    public String toString() {
        return String.format("UpgradeResponse=%s", this.response);
    }
}

