/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandlerContainer;

public class HotSwapHandler
extends AbstractHandlerContainer {
    private volatile Handler _handler;

    public Handler getHandler() {
        return this._handler;
    }

    @Override
    public Handler[] getHandlers() {
        Handler handler = this._handler;
        if (handler == null) {
            return new Handler[0];
        }
        return new Handler[]{handler};
    }

    public void setHandler(Handler handler) {
        try {
            Server server = this.getServer();
            if (handler == this._handler) {
                return;
            }
            Handler oldHandler = this._handler;
            if (handler != null) {
                handler.setServer(server);
                this.addBean(handler, true);
                if (oldHandler != null && oldHandler.isStarted()) {
                    handler.start();
                }
            }
            this._handler = handler;
            if (oldHandler != null) {
                this.removeBean(oldHandler);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Handler handler = this._handler;
        if (handler != null && this.isStarted() && handler.isStarted()) {
            handler.handle(target, baseRequest, request, response);
        }
    }

    @Override
    protected void expandChildren(List<Handler> list, Class<?> byClass) {
        Handler handler = this._handler;
        if (handler != null) {
            this.expandHandler(handler, list, byClass);
        }
    }

    @Override
    public void destroy() {
        if (!this.isStopped()) {
            throw new IllegalStateException("!STOPPED");
        }
        Handler child = this.getHandler();
        if (child != null) {
            this.setHandler(null);
            child.destroy();
        }
        super.destroy();
    }
}

