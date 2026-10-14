/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jetty;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.transport.http.HttpUrlUtil;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JettyHTTPHandler
extends AbstractHandler {
    private static final String METHOD_TRACE = "TRACE";
    protected JettyHTTPDestination jettyHTTPDestination;
    protected ServletContext servletContext;
    private String urlName;
    private boolean contextMatchExact;
    private Bus bus;

    public JettyHTTPHandler(JettyHTTPDestination jhd, boolean cmExact) {
        this.contextMatchExact = cmExact;
        this.jettyHTTPDestination = jhd;
    }

    public JettyHTTPHandler(Bus bus) {
        this.bus = bus;
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
        if (this.jettyHTTPDestination != null) {
            this.jettyHTTPDestination.setServletContext(sc);
        }
    }

    public void setName(String name) {
        this.urlName = name;
    }

    public String getName() {
        return this.urlName;
    }

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getMethod().equals(METHOD_TRACE)) {
            baseRequest.setHandled(true);
            response.setStatus(405);
        } else if (this.contextMatchExact) {
            if (target.equals(this.urlName)) {
                this.jettyHTTPDestination.doService(this.servletContext, request, response);
            }
        } else if (target.equals(this.urlName) || HttpUrlUtil.checkContextPath(this.urlName, target)) {
            this.jettyHTTPDestination.doService(this.servletContext, request, response);
        }
    }

    public Bus getBus() {
        return this.jettyHTTPDestination != null ? this.jettyHTTPDestination.getBus() : this.bus;
    }
}

