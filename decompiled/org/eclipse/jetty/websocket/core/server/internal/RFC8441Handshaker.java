/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.eclipse.jetty.websocket.core.internal.WebSocketCoreSession;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;
import org.eclipse.jetty.websocket.core.server.internal.AbstractHandshaker;
import org.eclipse.jetty.websocket.core.server.internal.RFC8441Negotiation;

public class RFC8441Handshaker
extends AbstractHandshaker {
    @Override
    protected boolean validateRequest(HttpServletRequest request) {
        if (!HttpMethod.CONNECT.is(request.getMethod())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded method!=GET {}", (Object)request);
            }
            return false;
        }
        if (!HttpVersion.HTTP_2.is(request.getProtocol())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded HttpVersion!=2 {}", (Object)request);
            }
            return false;
        }
        return true;
    }

    @Override
    protected WebSocketNegotiation newNegotiation(HttpServletRequest request, HttpServletResponse response, WebSocketComponents webSocketComponents) {
        return new RFC8441Negotiation(Request.getBaseRequest(request), request, response, webSocketComponents);
    }

    @Override
    protected boolean validateFrameHandler(FrameHandler frameHandler, HttpServletResponse response) {
        if (frameHandler == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: no frame handler provided");
            }
            response.setStatus(503);
        }
        return true;
    }

    @Override
    protected WebSocketConnection createWebSocketConnection(Request baseRequest, WebSocketCoreSession coreSession) {
        HttpChannel httpChannel = baseRequest.getHttpChannel();
        Connector connector = httpChannel.getConnector();
        EndPoint endPoint = httpChannel.getTunnellingEndPoint();
        ByteBufferPool byteBufferPool = connector.getByteBufferPool();
        RetainableByteBufferPool retainableByteBufferPool = byteBufferPool.asRetainableByteBufferPool();
        return this.newWebSocketConnection(endPoint, connector.getExecutor(), connector.getScheduler(), byteBufferPool, retainableByteBufferPool, coreSession);
    }

    @Override
    protected void prepareResponse(Response response, WebSocketNegotiation negotiation) {
        response.setStatus(200);
    }
}

