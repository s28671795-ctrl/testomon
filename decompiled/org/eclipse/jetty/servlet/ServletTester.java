/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.LocalConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletTester
extends ContainerLifeCycle {
    private static final Logger LOG = LoggerFactory.getLogger(ServletTester.class);
    private final Server _server = new Server();
    private final LocalConnector _connector = new LocalConnector(this._server);
    private final ServletContextHandler _context;

    public Server getServer() {
        return this._server;
    }

    public LocalConnector getConnector() {
        return this._connector;
    }

    public void setVirtualHosts(String[] vhosts) {
        this._context.setVirtualHosts(vhosts);
    }

    public void addVirtualHosts(String[] virtualHosts) {
        this._context.addVirtualHosts(virtualHosts);
    }

    public ServletHolder addServlet(String className, String pathSpec) {
        return this._context.addServlet(className, pathSpec);
    }

    public ServletHolder addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        return this._context.addServlet(servlet, pathSpec);
    }

    public void addServlet(ServletHolder servlet, String pathSpec) {
        this._context.addServlet(servlet, pathSpec);
    }

    public void addFilter(FilterHolder holder, String pathSpec, EnumSet<DispatcherType> dispatches) {
        this._context.addFilter(holder, pathSpec, dispatches);
    }

    public FilterHolder addFilter(Class<? extends Filter> filterClass, String pathSpec, EnumSet<DispatcherType> dispatches) {
        return this._context.addFilter(filterClass, pathSpec, dispatches);
    }

    public FilterHolder addFilter(String filterClass, String pathSpec, EnumSet<DispatcherType> dispatches) {
        return this._context.addFilter(filterClass, pathSpec, dispatches);
    }

    public Object getAttribute(String name) {
        return this._context.getAttribute(name);
    }

    public Enumeration<String> getAttributeNames() {
        return this._context.getAttributeNames();
    }

    public Attributes getAttributes() {
        return this._context.getAttributes();
    }

    public String getContextPath() {
        return this._context.getContextPath();
    }

    public String getInitParameter(String name) {
        return this._context.getInitParameter(name);
    }

    public String setInitParameter(String name, String value) {
        return this._context.setInitParameter(name, value);
    }

    public Enumeration<String> getInitParameterNames() {
        return this._context.getInitParameterNames();
    }

    public Map<String, String> getInitParams() {
        return this._context.getInitParams();
    }

    public void removeAttribute(String name) {
        this._context.removeAttribute(name);
    }

    public void setAttribute(String name, Object value) {
        this._context.setAttribute(name, value);
    }

    public void setContextPath(String contextPath) {
        this._context.setContextPath(contextPath);
    }

    public Resource getBaseResource() {
        return this._context.getBaseResource();
    }

    public void setBaseResource(Resource resource) {
        this._context.setBaseResource(resource);
    }

    public String getResourceBase() {
        return this._context.getResourceBase();
    }

    public void setResourceBase(String resourceBase) {
        this._context.setResourceBase(resourceBase);
    }

    public ServletTester() {
        this("/", 3);
    }

    public ServletTester(String ctxPath) {
        this(ctxPath, 3);
    }

    public ServletTester(String contextPath, int options) {
        this._context = new ServletContextHandler(this._server, contextPath, options);
        this._server.setConnectors(new Connector[]{this._connector});
        this.addBean(this._server);
    }

    public ServletContextHandler getContext() {
        return this._context;
    }

    public String getResponses(String request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request: {}", (Object)request);
        }
        return this._connector.getResponse(request);
    }

    public String getResponses(String request, long idleFor, TimeUnit units) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request: {}", (Object)request);
        }
        return this._connector.getResponse(request, idleFor, units);
    }

    public ByteBuffer getResponses(ByteBuffer request) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request (Buffer): {}", (Object)BufferUtil.toUTF8String(request));
        }
        return this._connector.getResponse(request);
    }

    public ByteBuffer getResponses(ByteBuffer requestsBuffer, long idleFor, TimeUnit units) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Requests (Buffer): {}", (Object)BufferUtil.toUTF8String(requestsBuffer));
        }
        return this._connector.getResponse(requestsBuffer, idleFor, units);
    }

    public String createConnector(boolean localhost) throws Exception {
        ServerConnector connector = new ServerConnector(this._server);
        if (localhost) {
            connector.setHost("127.0.0.1");
        }
        this._server.addConnector(connector);
        if (this._server.isStarted()) {
            connector.start();
        } else {
            connector.open();
        }
        return "http://" + (localhost ? "127.0.0.1" : InetAddress.getLocalHost().getHostAddress()) + ":" + connector.getLocalPort();
    }

    public LocalConnector createLocalConnector() {
        LocalConnector connector = new LocalConnector(this._server);
        this._server.addConnector(connector);
        return connector;
    }
}

