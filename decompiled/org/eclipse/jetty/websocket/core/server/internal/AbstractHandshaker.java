/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server.internal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.RetainableByteBufferPool;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpTransport;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.thread.Scheduler;
import org.eclipse.jetty.websocket.core.Behavior;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.WebSocketComponents;
import org.eclipse.jetty.websocket.core.WebSocketConstants;
import org.eclipse.jetty.websocket.core.exception.WebSocketException;
import org.eclipse.jetty.websocket.core.internal.ExtensionStack;
import org.eclipse.jetty.websocket.core.internal.Negotiated;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.eclipse.jetty.websocket.core.internal.WebSocketCoreSession;
import org.eclipse.jetty.websocket.core.server.Handshaker;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiator;
import org.eclipse.jetty.websocket.core.server.internal.UpgradeHttpServletRequest;
import org.eclipse.jetty.websocket.core.server.internal.UpgradeHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHandshaker
implements Handshaker {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractHandshaker.class);
    private static final HttpField SERVER_VERSION = new PreEncodedHttpField(HttpHeader.SERVER, HttpConfiguration.SERVER_VERSION);

    @Override
    public boolean upgradeRequest(WebSocketNegotiator negotiator, HttpServletRequest request, HttpServletResponse response, WebSocketComponents components, Configuration.Customizer defaultCustomizer) throws IOException {
        if (!this.validateRequest(request)) {
            return false;
        }
        UpgradeHttpServletRequest upgradeRequest = new UpgradeHttpServletRequest(request);
        UpgradeHttpServletResponse upgradeResponse = new UpgradeHttpServletResponse(response);
        WebSocketNegotiation negotiation = this.newNegotiation(upgradeRequest, upgradeResponse, components);
        if (LOG.isDebugEnabled()) {
            LOG.debug("negotiation {}", (Object)negotiation);
        }
        negotiation.negotiate();
        if (!this.validateNegotiation(negotiation)) {
            return false;
        }
        FrameHandler handler = negotiator.negotiate(negotiation);
        if (!this.validateFrameHandler(handler, response)) {
            return false;
        }
        Request baseRequest = negotiation.getBaseRequest();
        if (response.isCommitted()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: response committed {}", (Object)request);
            }
            baseRequest.setHandled(true);
            return false;
        }
        int httpStatus = response.getStatus();
        if (httpStatus > 200) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: invalid http code {} {}", (Object)httpStatus, (Object)request);
            }
            response.flushBuffer();
            baseRequest.setHandled(true);
            return false;
        }
        String protocol = negotiation.getSubprotocol();
        List<String> offeredProtocols = negotiation.getOfferedSubprotocols();
        if (protocol != null && !offeredProtocols.contains(protocol)) {
            throw new WebSocketException("not upgraded: selected a protocol not present in offered protocols");
        }
        for (ExtensionConfig config : negotiation.getNegotiatedExtensions()) {
            if (config.getName().startsWith("@")) continue;
            long matches = negotiation.getOfferedExtensions().stream().filter(c -> config.getName().equalsIgnoreCase(c.getName())).count();
            if (matches < 1L) {
                throw new WebSocketException("Upgrade failed: negotiated extension not requested");
            }
            matches = negotiation.getNegotiatedExtensions().stream().filter(c -> config.getName().equalsIgnoreCase(c.getName())).count();
            if (matches <= 1L) continue;
            throw new WebSocketException("Upgrade failed: multiple negotiated extensions of the same name");
        }
        ExtensionStack extensionStack = new ExtensionStack(components, Behavior.SERVER);
        extensionStack.negotiate(negotiation.getOfferedExtensions(), negotiation.getNegotiatedExtensions());
        negotiation.setNegotiatedExtensions(extensionStack.getNegotiatedExtensions());
        if (extensionStack.hasNegotiatedExtensions()) {
            baseRequest.getResponse().setHeader(HttpHeader.SEC_WEBSOCKET_EXTENSIONS, ExtensionConfig.toHeaderValue(negotiation.getNegotiatedExtensions()));
        } else {
            baseRequest.getResponse().setHeader(HttpHeader.SEC_WEBSOCKET_EXTENSIONS, null);
        }
        Negotiated negotiated = new Negotiated(baseRequest.getHttpURI().toURI(), protocol, baseRequest.isSecure(), extensionStack, WebSocketConstants.SPEC_VERSION_STRING);
        WebSocketCoreSession coreSession = this.newWebSocketCoreSession(upgradeRequest, handler, negotiated, components);
        if (defaultCustomizer != null) {
            defaultCustomizer.customize(coreSession);
        }
        negotiator.customize(coreSession);
        if (LOG.isDebugEnabled()) {
            LOG.debug("session {}", (Object)coreSession);
        }
        WebSocketConnection connection = this.createWebSocketConnection(baseRequest, coreSession);
        if (LOG.isDebugEnabled()) {
            LOG.debug("connection {}", (Object)connection);
        }
        if (connection == null) {
            throw new WebSocketException("not upgraded: no connection");
        }
        HttpChannel httpChannel = baseRequest.getHttpChannel();
        HttpConfiguration httpConfig = httpChannel.getHttpConfiguration();
        connection.setUseInputDirectByteBuffers(httpConfig.isUseInputDirectByteBuffers());
        connection.setUseOutputDirectByteBuffers(httpChannel.isUseOutputDirectByteBuffers());
        httpChannel.getConnector().getEventListeners().forEach(connection::addEventListener);
        coreSession.setWebSocketConnection(connection);
        baseRequest.setHandled(true);
        Response baseResponse = baseRequest.getResponse();
        this.prepareResponse(baseResponse, negotiation);
        if (httpConfig.getSendServerVersion()) {
            baseResponse.getHttpFields().put(SERVER_VERSION);
        }
        baseResponse.flushBuffer();
        baseRequest.setAttribute(HttpTransport.UPGRADE_CONNECTION_ATTRIBUTE, connection);
        upgradeRequest.upgrade();
        upgradeResponse.upgrade();
        negotiation.upgrade();
        if (LOG.isDebugEnabled()) {
            LOG.debug("upgrade connection={} session={} framehandler={}", connection, coreSession, handler);
        }
        return true;
    }

    protected abstract boolean validateRequest(HttpServletRequest var1);

    protected abstract WebSocketNegotiation newNegotiation(HttpServletRequest var1, HttpServletResponse var2, WebSocketComponents var3);

    protected abstract boolean validateFrameHandler(FrameHandler var1, HttpServletResponse var2);

    protected boolean validateNegotiation(WebSocketNegotiation negotiation) {
        if (!negotiation.validateHeaders()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: no upgrade header or connection upgrade {}", (Object)negotiation.getBaseRequest());
            }
            return false;
        }
        if (!WebSocketConstants.SPEC_VERSION_STRING.equals(negotiation.getVersion())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("not upgraded: unsupported version {} {}", (Object)negotiation.getVersion(), (Object)negotiation.getBaseRequest());
            }
            return false;
        }
        return true;
    }

    protected WebSocketCoreSession newWebSocketCoreSession(HttpServletRequest request, FrameHandler handler, Negotiated negotiated, WebSocketComponents components) {
        final ContextHandler contextHandler = ContextHandler.getContextHandler(request.getServletContext());
        return new WebSocketCoreSession(handler, Behavior.SERVER, negotiated, components){

            @Override
            protected void handle(Runnable runnable2) {
                if (contextHandler != null) {
                    contextHandler.handle(runnable2);
                } else {
                    super.handle(runnable2);
                }
            }
        };
    }

    protected abstract WebSocketConnection createWebSocketConnection(Request var1, WebSocketCoreSession var2);

    protected WebSocketConnection newWebSocketConnection(EndPoint endPoint, Executor executor2, Scheduler scheduler, ByteBufferPool byteBufferPool, RetainableByteBufferPool retainableByteBufferPool, WebSocketCoreSession coreSession) {
        return new WebSocketConnection(endPoint, executor2, scheduler, byteBufferPool, retainableByteBufferPool, coreSession);
    }

    protected abstract void prepareResponse(Response var1, WebSocketNegotiation var2);
}

