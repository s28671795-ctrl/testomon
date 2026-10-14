/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.api.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public final class WSURI {
    public static URI toHttp(URI inputUri) throws URISyntaxException {
        Objects.requireNonNull(inputUri, "Input URI must not be null");
        String wsScheme = inputUri.getScheme();
        if ("http".equalsIgnoreCase(wsScheme) || "https".equalsIgnoreCase(wsScheme)) {
            return inputUri;
        }
        if ("ws".equalsIgnoreCase(wsScheme)) {
            return new URI("http" + inputUri.toString().substring(wsScheme.length()));
        }
        if ("wss".equalsIgnoreCase(wsScheme)) {
            return new URI("https" + inputUri.toString().substring(wsScheme.length()));
        }
        throw new URISyntaxException(inputUri.toString(), "Unrecognized WebSocket scheme");
    }

    public static URI toWebsocket(CharSequence inputUrl) throws URISyntaxException {
        return WSURI.toWebsocket(new URI(inputUrl.toString()));
    }

    public static URI toWebsocket(CharSequence inputUrl, String query) throws URISyntaxException {
        if (query == null) {
            return WSURI.toWebsocket(new URI(inputUrl.toString()));
        }
        return WSURI.toWebsocket(new URI(inputUrl.toString() + "?" + query));
    }

    public static URI toWebsocket(URI inputUri) throws URISyntaxException {
        Objects.requireNonNull(inputUri, "Input URI must not be null");
        String httpScheme = inputUri.getScheme();
        if (httpScheme == null) {
            throw new URISyntaxException(inputUri.toString(), "Undefined HTTP scheme");
        }
        if ("ws".equalsIgnoreCase(httpScheme) || "wss".equalsIgnoreCase(httpScheme)) {
            return inputUri;
        }
        String afterScheme = inputUri.toString().substring(httpScheme.length());
        if ("http".equalsIgnoreCase(httpScheme)) {
            return new URI("ws" + afterScheme);
        }
        if ("https".equalsIgnoreCase(httpScheme)) {
            return new URI("wss" + afterScheme);
        }
        throw new URISyntaxException(inputUri.toString(), "Unrecognized HTTP scheme");
    }
}

