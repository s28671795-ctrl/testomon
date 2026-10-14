/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jetty.http.pathmap.MatchedResource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.util.ArrayUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Invoker
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(Invoker.class);
    private ContextHandler _contextHandler;
    private ServletHandler _servletHandler;
    private MatchedResource<ServletHandler.MappedServlet> _invokerEntry;
    private Map<String, String> _parameters;
    private boolean _nonContextServlets;
    private boolean _verbose;

    @Override
    public void init() {
        ServletContext config = this.getServletContext();
        this._contextHandler = ((ContextHandler.Context)config).getContextHandler();
        Handler handler = this._contextHandler.getHandler();
        while (handler != null && !(handler instanceof ServletHandler) && handler instanceof HandlerWrapper) {
            handler = ((HandlerWrapper)handler).getHandler();
        }
        this._servletHandler = (ServletHandler)handler;
        Enumeration<String> e = this.getInitParameterNames();
        while (e.hasMoreElements()) {
            String param2 = e.nextElement();
            String value = this.getInitParameter(param2);
            String lvalue = value.toLowerCase(Locale.ENGLISH);
            if ("nonContextServlets".equals(param2)) {
                boolean bl = this._nonContextServlets = value.length() > 0 && lvalue.startsWith("t");
            }
            if ("verbose".equals(param2)) {
                this._verbose = value.length() > 0 && lvalue.startsWith("t");
                continue;
            }
            if (this._parameters == null) {
                this._parameters = new HashMap<String, String>();
            }
            this._parameters.put(param2, value);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletHolder holder;
        String servlet;
        String pathInfo;
        String servletPath;
        boolean included;
        block27: {
            included = false;
            servletPath = (String)request.getAttribute("jakarta.servlet.include.servlet_path");
            if (servletPath == null) {
                servletPath = request.getServletPath();
            } else {
                included = true;
            }
            pathInfo = (String)request.getAttribute("jakarta.servlet.include.path_info");
            if (pathInfo == null) {
                pathInfo = request.getPathInfo();
            }
            if ((servlet = pathInfo) == null || servlet.length() <= 1) {
                response.sendError(404);
                return;
            }
            int i0 = servlet.charAt(0) == '/' ? 1 : 0;
            int i1 = servlet.indexOf(47, i0);
            servlet = i1 < 0 ? servlet.substring(i0) : servlet.substring(i0, i1);
            ServletHolder[] holders = this._servletHandler.getServlets();
            holder = this.getHolder(holders, servlet);
            if (holder != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Adding servlet mapping for named servlet: {}:{}/*", (Object)servlet, (Object)URIUtil.addPaths(servletPath, servlet));
                }
                ServletMapping mapping = new ServletMapping();
                mapping.setServletName(servlet);
                mapping.setPathSpec(URIUtil.addPaths(servletPath, servlet) + "/*");
                this._servletHandler.setServletMappings(ArrayUtil.addToArray(this._servletHandler.getServletMappings(), mapping, ServletMapping.class));
            } else {
                if (servlet.endsWith(".class")) {
                    servlet = servlet.substring(0, servlet.length() - 6);
                }
                if (servlet == null || servlet.length() == 0) {
                    response.sendError(404);
                    return;
                }
                try (AutoLock l = this._servletHandler.lock();){
                    this._invokerEntry = this._servletHandler.getMatchedServlet(servletPath);
                    String path = URIUtil.addPaths(servletPath, servlet);
                    MatchedResource<ServletHandler.MappedServlet> entry = this._servletHandler.getMatchedServlet(path);
                    if (entry != null && !entry.getResource().equals(this._invokerEntry.getResource())) {
                        holder = entry.getResource().getServletHolder();
                        break block27;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Making new servlet={}  with path={}/*", (Object)servlet, (Object)path);
                    }
                    holder = this._servletHandler.addServletWithMapping(servlet, path + "/*");
                    if (this._parameters != null) {
                        holder.setInitParameters(this._parameters);
                    }
                    try {
                        holder.start();
                    }
                    catch (Exception e) {
                        LOG.debug("Unable to start {}", (Object)holder, (Object)e);
                        throw new UnavailableException(e.toString());
                    }
                    if (!this._nonContextServlets) {
                        Servlet s2 = holder.getServlet();
                        if (this._contextHandler.getClassLoader() != s2.getClass().getClassLoader()) {
                            try {
                                holder.stop();
                            }
                            catch (Exception e) {
                                LOG.trace("IGNORED", e);
                            }
                            LOG.warn("Dynamic servlet {} not loaded from context {}", (Object)s2, (Object)request.getContextPath());
                            throw new UnavailableException("Not in context");
                        }
                    }
                    if (this._verbose && LOG.isDebugEnabled()) {
                        LOG.debug("Dynamic load '{}' at {}", (Object)servlet, (Object)path);
                    }
                }
            }
        }
        if (holder != null) {
            Request baseRequest = Request.getBaseRequest(request);
            holder.prepare(baseRequest, request, response);
            holder.handle(baseRequest, new InvokedRequest(request, included, servlet, servletPath, pathInfo), response);
        } else {
            LOG.info("Can't find holder for servlet: {}", (Object)servlet);
            response.sendError(404);
        }
    }

    private ServletHolder getHolder(ServletHolder[] holders, String servlet) {
        if (holders == null) {
            return null;
        }
        ServletHolder holder = null;
        for (int i = 0; holder == null && i < holders.length; ++i) {
            if (!holders[i].getName().equals(servlet)) continue;
            holder = holders[i];
        }
        return holder;
    }

    class InvokedRequest
    extends HttpServletRequestWrapper {
        String _servletPath;
        String _pathInfo;
        boolean _included;

        InvokedRequest(HttpServletRequest request, boolean included, String name, String servletPath, String pathInfo) {
            super(request);
            this._included = included;
            this._servletPath = URIUtil.addPaths(servletPath, name);
            this._pathInfo = pathInfo.substring(name.length() + 1);
            if (this._pathInfo.length() == 0) {
                this._pathInfo = null;
            }
        }

        @Override
        public String getServletPath() {
            if (this._included) {
                return super.getServletPath();
            }
            return this._servletPath;
        }

        @Override
        public String getPathInfo() {
            if (this._included) {
                return super.getPathInfo();
            }
            return this._pathInfo;
        }

        @Override
        public Object getAttribute(String name) {
            if (this._included) {
                if (name.equals("jakarta.servlet.include.request_uri")) {
                    return URIUtil.addPaths(URIUtil.addPaths(this.getContextPath(), this._servletPath), this._pathInfo);
                }
                if (name.equals("jakarta.servlet.include.path_info")) {
                    return this._pathInfo;
                }
                if (name.equals("jakarta.servlet.include.servlet_path")) {
                    return this._servletPath;
                }
            }
            return super.getAttribute(name);
        }
    }
}

