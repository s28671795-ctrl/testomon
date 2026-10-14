/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

public class AsyncDelayHandler
extends HandlerWrapper {
    public static final String AHW_ATTR = "o.e.j.s.h.AsyncHandlerWrapper";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!this.isStarted() || this._handler == null) {
            return;
        }
        DispatcherType ctype = baseRequest.getDispatcherType();
        DispatcherType dtype = (DispatcherType)((Object)baseRequest.getAttribute(AHW_ATTR));
        Object asyncContextPath = null;
        Object asyncPathInfo = null;
        Object asyncQueryString = null;
        Object asyncRequestUri = null;
        Object asyncServletPath = null;
        Object asyncHttpServletMapping = null;
        boolean restart = false;
        if (dtype != null) {
            baseRequest.setAttribute(AHW_ATTR, null);
            baseRequest.setDispatcherType(dtype);
            restart = true;
            asyncContextPath = baseRequest.getAttribute("jakarta.servlet.async.context_path");
            baseRequest.setAttribute("jakarta.servlet.async.context_path", null);
            asyncPathInfo = baseRequest.getAttribute("jakarta.servlet.async.path_info");
            baseRequest.setAttribute("jakarta.servlet.async.path_info", null);
            asyncQueryString = baseRequest.getAttribute("jakarta.servlet.async.query_string");
            baseRequest.setAttribute("jakarta.servlet.async.query_string", null);
            asyncRequestUri = baseRequest.getAttribute("jakarta.servlet.async.request_uri");
            baseRequest.setAttribute("jakarta.servlet.async.request_uri", null);
            asyncServletPath = baseRequest.getAttribute("jakarta.servlet.async.servlet_path");
            baseRequest.setAttribute("jakarta.servlet.async.servlet_path", null);
            asyncHttpServletMapping = baseRequest.getAttribute("jakarta.servlet.async.mapping");
            baseRequest.setAttribute("jakarta.servlet.async.mapping", null);
        }
        if (!this.startHandling(baseRequest, restart)) {
            AsyncContext context = baseRequest.startAsync();
            baseRequest.setAttribute(AHW_ATTR, (Object)ctype);
            this.delayHandling(baseRequest, context);
            return;
        }
        try {
            this._handler.handle(target, baseRequest, request, response);
            if (restart) {
                baseRequest.setDispatcherType(ctype);
                baseRequest.setAttribute("jakarta.servlet.async.context_path", asyncContextPath);
                baseRequest.setAttribute("jakarta.servlet.async.path_info", asyncPathInfo);
                baseRequest.setAttribute("jakarta.servlet.async.query_string", asyncQueryString);
                baseRequest.setAttribute("jakarta.servlet.async.request_uri", asyncRequestUri);
                baseRequest.setAttribute("jakarta.servlet.async.servlet_path", asyncServletPath);
                baseRequest.setAttribute("jakarta.servlet.async.mapping", asyncHttpServletMapping);
            }
            this.endHandling(baseRequest);
        }
        catch (Throwable throwable) {
            if (restart) {
                baseRequest.setDispatcherType(ctype);
                baseRequest.setAttribute("jakarta.servlet.async.context_path", asyncContextPath);
                baseRequest.setAttribute("jakarta.servlet.async.path_info", asyncPathInfo);
                baseRequest.setAttribute("jakarta.servlet.async.query_string", asyncQueryString);
                baseRequest.setAttribute("jakarta.servlet.async.request_uri", asyncRequestUri);
                baseRequest.setAttribute("jakarta.servlet.async.servlet_path", asyncServletPath);
                baseRequest.setAttribute("jakarta.servlet.async.mapping", asyncHttpServletMapping);
            }
            this.endHandling(baseRequest);
            throw throwable;
        }
    }

    protected boolean startHandling(Request request, boolean restart) {
        return true;
    }

    protected void delayHandling(Request request, AsyncContext context) {
        context.dispatch();
    }

    protected void endHandling(Request request) {
    }
}

