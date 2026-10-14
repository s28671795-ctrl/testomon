/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.eclipse.jetty.websocket.core.internal.WebSocketCore;
import org.eclipse.jetty.websocket.core.internal.WebSocketCoreSession;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;
import org.eclipse.jetty.websocket.core.server.internal.AbstractHandshaker;
import org.eclipse.jetty.websocket.core.server.internal.RFC6455Negotiation;

public final class RFC6455Handshaker
extends AbstractHandshaker {
    private static final HttpField UPGRADE_WEBSOCKET = new PreEncodedHttpField(HttpHeader.UPGRADE, "websocket");
    private static final HttpField CONNECTION_UPGRADE = new PreEncodedHttpField(HttpHeader.CONNECTION, HttpHeader.UPGRADE.asString());

    @Override
    protected boolean validateRequest(HttpServletRequest request) {
        if (!HttpMethod.GET.is(request.getMethod())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded method!=GET {}", (Object)request);
            }
            return false;
        }
        if (!HttpVersion.HTTP_1_1.is(request.getProtocol())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded version!=1.1 {}", (Object)request);
            }
            return false;
        }
        return true;
    }

    @Override
    protected WebSocketNegotiation newNegotiation(HttpServletRequest request, HttpServletResponse response, WebSocketComponents webSocketComponents) {
        return new RFC6455Negotiation(Request.getBaseRequest(request), request, response, webSocketComponents);
    }

    @Override
    protected boolean validateNegotiation(WebSocketNegotiation negotiation) {
        boolean result = super.validateNegotiation(negotiation);
        if (!result) {
            return false;
        }
        if (((RFC6455Negotiation)negotiation).getKey() == null) {
            throw new BadMessageException("Missing request header 'Sec-WebSocket-Key'");
        }
        return true;
    }

    @Override
    protected boolean validateFrameHandler(FrameHandler frameHandler, HttpServletResponse response) {
        if (frameHandler == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: no frame handler provided");
            }
            return false;
        }
        return true;
    }

    @Override
    protected WebSocketConnection createWebSocketConnection(Request baseRequest, WebSocketCoreSession coreSession) {
        HttpChannel httpChannel = baseRequest.getHttpChannel();
        Connector connector = httpChannel.getConnector();
        ByteBufferPool byteBufferPool = connector.getByteBufferPool();
        RetainableByteBufferPool retainableByteBufferPool = byteBufferPool.asRetainableByteBufferPool();
        return this.newWebSocketConnection(httpChannel.getEndPoint(), connector.getExecutor(), connector.getScheduler(), byteBufferPool, retainableByteBufferPool, coreSession);
    }

    @Override
    protected void prepareResponse(Response response, WebSocketNegotiation negotiation) {
        response.setStatus(101);
        HttpFields.Mutable responseFields = response.getHttpFields();
        responseFields.put(UPGRADE_WEBSOCKET);
        responseFields.put(CONNECTION_UPGRADE);
        responseFields.put(HttpHeader.SEC_WEBSOCKET_ACCEPT, WebSocketCore.hashKey(((RFC6455Negotiation)negotiation).getKey()));
    }
}

