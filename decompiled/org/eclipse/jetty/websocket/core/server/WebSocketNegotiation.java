/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.QuotedCSV;
import org.eclipse.jetty.http.QuotedCSVParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

public abstract class WebSocketNegotiation {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final WebSocketComponents components;
    private Request baseRequest;
    private String version;
    private List<ExtensionConfig> offeredExtensions;
    private List<ExtensionConfig> negotiatedExtensions;
    private List<String> offeredProtocols;
    private String protocol;

    public WebSocketNegotiation(Request baseRequest, HttpServletRequest request, HttpServletResponse response, WebSocketComponents webSocketComponents) {
        this.baseRequest = baseRequest;
        this.request = request;
        this.response = response;
        this.components = webSocketComponents;
    }

    public Request getBaseRequest() {
        return this.baseRequest;
    }

    public void upgrade() {
        this.baseRequest = null;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public void negotiate() throws BadMessageException {
        try {
            this.negotiateHeaders(this.getBaseRequest());
        }
        catch (Throwable x) {
            throw new BadMessageException("Invalid upgrade request", x);
        }
    }

    protected void negotiateHeaders(Request baseRequest) {
        QuotedCSV extensions = null;
        QuotedCSVParser protocols = null;
        for (HttpField field2 : baseRequest.getHttpFields()) {
            if (field2.getHeader() == null) continue;
            switch (field2.getHeader()) {
                case SEC_WEBSOCKET_VERSION: {
                    this.version = field2.getValue();
                    break;
                }
                case SEC_WEBSOCKET_EXTENSIONS: {
                    if (extensions == null) {
                        extensions = new QuotedCSV(field2.getValue());
                        break;
                    }
                    extensions.addValue(field2.getValue());
                    break;
                }
                case SEC_WEBSOCKET_SUBPROTOCOL: {
                    if (protocols == null) {
                        protocols = new QuotedCSV(field2.getValue());
                        break;
                    }
                    protocols.addValue(field2.getValue());
                    break;
                }
            }
        }
        Set<String> available = this.components.getExtensionRegistry().getAvailableExtensionNames();
        this.offeredExtensions = extensions == null ? Collections.emptyList() : extensions.getValues().stream().map(ExtensionConfig::parse).filter(ec -> available.contains(ec.getName()) && !ec.getName().startsWith("@")).collect(Collectors.toList());
        this.offeredExtensions.forEach(ExtensionConfig::removeInternalParameters);
        this.offeredProtocols = protocols == null ? Collections.emptyList() : ((QuotedCSV)protocols).getValues();
        this.negotiatedExtensions = new ArrayList<ExtensionConfig>();
        for (ExtensionConfig config : this.offeredExtensions) {
            long matches = this.negotiatedExtensions.stream().filter(negotiatedConfig -> negotiatedConfig.getName().equals(config.getName())).count();
            if (matches != 0L) continue;
            this.negotiatedExtensions.add(new ExtensionConfig(config));
        }
    }

    public abstract boolean validateHeaders();

    public String getVersion() {
        return this.version;
    }

    public String getSubprotocol() {
        return this.protocol;
    }

    public void setSubprotocol(String protocol) {
        this.protocol = protocol;
        this.response.setHeader(HttpHeader.SEC_WEBSOCKET_SUBPROTOCOL.asString(), protocol);
    }

    public List<String> getOfferedSubprotocols() {
        return this.offeredProtocols;
    }

    public List<ExtensionConfig> getOfferedExtensions() {
        return this.offeredExtensions;
    }

    public List<ExtensionConfig> getNegotiatedExtensions() {
        return this.negotiatedExtensions;
    }

    public void setNegotiatedExtensions(List<ExtensionConfig> extensions) {
        if (extensions == this.offeredExtensions) {
            return;
        }
        this.negotiatedExtensions = extensions;
    }

    public String toString() {
        return String.format("%s@%x{uri=%s,oe=%s,op=%s}", this.getClass().getSimpleName(), this.hashCode(), this.getRequest().getRequestURI(), this.getOfferedExtensions(), this.getOfferedSubprotocols());
    }
}

