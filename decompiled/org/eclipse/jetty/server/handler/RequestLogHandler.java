/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.handler.HandlerWrapper;

public class RequestLogHandler
extends HandlerWrapper {
    private RequestLog _requestLog;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (baseRequest.getDispatcherType() == DispatcherType.REQUEST) {
            baseRequest.getHttpChannel().addRequestLog(this._requestLog);
        }
        if (this._handler != null) {
            this._handler.handle(target, baseRequest, request, response);
        }
    }

    public void setRequestLog(RequestLog requestLog) {
        this.updateBean(this._requestLog, requestLog);
        this._requestLog = requestLog;
    }

    public RequestLog getRequestLog() {
        return this._requestLog;
    }
}

