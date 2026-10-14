/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.WebSocketConstants;
import org.eclipse.jetty.websocket.core.internal.ExtensionStack;

public class Negotiated {
    private final URI requestURI;
    private final Map<String, List<String>> parameterMap;
    private final String subProtocol;
    private final boolean secure;
    private final ExtensionStack extensions;
    private final String protocolVersion;

    public Negotiated(URI requestURI, String subProtocol, boolean secure, ExtensionStack extensions, String protocolVersion) {
        Map<String, List<String>> map;
        this.requestURI = Negotiated.toWebsocket(requestURI);
        this.subProtocol = subProtocol;
        this.secure = secure;
        this.extensions = extensions;
        this.protocolVersion = protocolVersion;
        String rawQuery = requestURI.getRawQuery();
        if (StringUtil.isBlank(rawQuery)) {
            map = Collections.emptyMap();
        } else {
            map = new HashMap();
            MultiMap<String> params = new MultiMap<String>();
            UrlEncoded.decodeUtf8To(rawQuery, params);
            for (String p : params.keySet()) {
                map.put(p, Collections.unmodifiableList(params.getValues(p)));
            }
        }
        this.parameterMap = Collections.unmodifiableMap(map);
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    public Map<String, List<String>> getParameterMap() {
        return this.parameterMap;
    }

    public String getSubProtocol() {
        return this.subProtocol;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public ExtensionStack getExtensions() {
        return this.extensions;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String toString() {
        return String.format("[%s,%s,%b.%s]", this.requestURI, this.subProtocol, this.secure, this.extensions.getNegotiatedExtensions().stream().map(ExtensionConfig::getName).collect(Collectors.toList()));
    }

    public static Negotiated from(ExtensionStack extensions) {
        try {
            return new Negotiated(new URI("/"), "", false, extensions, WebSocketConstants.SPEC_VERSION_STRING);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI toWebsocket(URI uri) {
        try {
            Objects.requireNonNull(uri, "Input URI must not be null");
            String httpScheme = uri.getScheme();
            if (httpScheme == null) {
                return uri;
            }
            if (HttpScheme.WS.is(httpScheme) || HttpScheme.WSS.is(httpScheme)) {
                return uri;
            }
            String afterScheme = uri.toString().substring(httpScheme.length());
            if (HttpScheme.HTTP.is(httpScheme)) {
                return new URI("ws" + afterScheme);
            }
            if (HttpScheme.HTTPS.is(httpScheme)) {
                return new URI("wss" + afterScheme);
            }
            throw new URISyntaxException(uri.toString(), "Unrecognized HTTP scheme");
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

