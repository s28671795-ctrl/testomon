/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHandler
extends HandlerWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(ShutdownHandler.class);
    private final String _shutdownToken;
    private boolean _sendShutdownAtStart;
    private boolean _exitJvm = false;

    public ShutdownHandler(String shutdownToken) {
        this(shutdownToken, false, false);
    }

    public ShutdownHandler(String shutdownToken, boolean exitJVM, boolean sendShutdownAtStart) {
        this._shutdownToken = shutdownToken;
        this.setExitJvm(exitJVM);
        this.setSendShutdownAtStart(sendShutdownAtStart);
    }

    public void sendShutdown() throws IOException {
        URL url2 = new URL(this.getServerUrl() + "/shutdown?token=" + this._shutdownToken);
        try {
            HttpURLConnection connection = (HttpURLConnection)url2.openConnection();
            connection.setRequestMethod("POST");
            connection.getResponseCode();
            LOG.info("Shutting down {}: {} {}", url2, connection.getResponseCode(), connection.getResponseMessage());
        }
        catch (SocketException e) {
            LOG.debug("Not running");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getServerUrl() {
        NetworkConnector connector = null;
        for (Connector c : this.getServer().getConnectors()) {
            if (!(c instanceof NetworkConnector)) continue;
            connector = (NetworkConnector)c;
            break;
        }
        if (connector == null) {
            return "http://localhost";
        }
        return "http://localhost:" + connector.getPort();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        if (this._sendShutdownAtStart) {
            this.sendShutdown();
        }
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!target.equals("/shutdown")) {
            super.handle(target, baseRequest, request, response);
            return;
        }
        if (!request.getMethod().equals("POST")) {
            response.sendError(400);
            return;
        }
        if (!this.hasCorrectSecurityToken(request)) {
            LOG.warn("Unauthorized tokenless shutdown attempt from {}", (Object)request.getRemoteAddr());
            response.sendError(401);
            return;
        }
        if (!this.requestFromLocalhost(baseRequest)) {
            LOG.warn("Unauthorized non-loopback shutdown attempt from {}", (Object)request.getRemoteAddr());
            response.sendError(401);
            return;
        }
        LOG.info("Shutting down by request from {}", (Object)request.getRemoteAddr());
        this.doShutdown(baseRequest, response);
    }

    protected void doShutdown(Request baseRequest, HttpServletResponse response) throws IOException {
        for (Connector connector : this.getServer().getConnectors()) {
            connector.shutdown();
        }
        baseRequest.setHandled(true);
        response.setStatus(200);
        response.flushBuffer();
        final Server server = this.getServer();
        new Thread(){

            @Override
            public void run() {
                try {
                    ShutdownHandler.this.shutdownServer(server);
                }
                catch (InterruptedException e) {
                    LOG.trace("IGNORED", e);
                }
                catch (Exception e) {
                    throw new RuntimeException("Shutting down server", e);
                }
            }
        }.start();
    }

    private boolean requestFromLocalhost(Request request) {
        InetSocketAddress addr = request.getRemoteInetSocketAddress();
        if (addr == null) {
            return false;
        }
        return addr.getAddress().isLoopbackAddress();
    }

    private boolean hasCorrectSecurityToken(HttpServletRequest request) {
        String tok = request.getParameter("token");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Token: {}", (Object)tok);
        }
        return this._shutdownToken.equals(tok);
    }

    private void shutdownServer(Server server) throws Exception {
        server.stop();
        if (this._exitJvm) {
            System.exit(0);
        }
    }

    public void setExitJvm(boolean exitJvm) {
        this._exitJvm = exitJvm;
    }

    public boolean isSendShutdownAtStart() {
        return this._sendShutdownAtStart;
    }

    public void setSendShutdownAtStart(boolean sendShutdownAtStart) {
        this._sendShutdownAtStart = sendShutdownAtStart;
    }

    public String getShutdownToken() {
        return this._shutdownToken;
    }

    public boolean isExitJvm() {
        return this._exitJvm;
    }
}

