/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.URIUtil;

public class SecuredRedirectHandler
extends HandlerWrapper {
    private final int _redirectCode;

    public SecuredRedirectHandler() {
        this(302);
    }

    public SecuredRedirectHandler(int code) {
        if (!HttpStatus.isRedirection(code)) {
            throw new IllegalArgumentException("Not a 3xx redirect code");
        }
        this._redirectCode = code;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpChannel channel = baseRequest.getHttpChannel();
        if (baseRequest.isSecure() || channel == null) {
            super.handle(target, baseRequest, request, response);
            return;
        }
        baseRequest.setHandled(true);
        HttpConfiguration httpConfig = channel.getHttpConfiguration();
        if (httpConfig == null) {
            response.sendError(403, "Missing HttpConfiguration");
            return;
        }
        int securePort = httpConfig.getSecurePort();
        if (securePort > 0) {
            String secureScheme = httpConfig.getSecureScheme();
            String url2 = URIUtil.newURI(secureScheme, baseRequest.getServerName(), securePort, baseRequest.getRequestURI(), baseRequest.getQueryString());
            response.setContentLength(0);
            baseRequest.getResponse().sendRedirect(this._redirectCode, url2, true);
        } else {
            response.sendError(403, "HttpConfiguration.securePort not configured");
        }
    }
}

