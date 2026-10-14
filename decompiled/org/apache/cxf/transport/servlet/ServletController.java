/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.servlet.BaseUrlHelper;

public class ServletController {
    public static final String AUTH_SERVICE_LIST = "auth.service.list";
    public static final String AUTH_SERVICE_LIST_REALM = "auth.service.list.realm";
    protected static final String DEFAULT_LISTINGS_CLASSIFIER = "/services";
    private static final Logger LOG = LogUtils.getL7dLogger(ServletController.class);
    private static final String HTTP_PREFIX = "http";
    protected boolean isHideServiceList;
    protected boolean isAuthServiceListPage;
    protected boolean disableAddressUpdates = true;
    protected String authServiceListPageRealm;
    protected String forcedBaseAddress;
    protected String serviceListRelativePath = "/services";
    protected ServletConfig servletConfig;
    protected DestinationRegistry destinationRegistry;
    protected HttpServlet serviceListGenerator;

    public ServletController(DestinationRegistry destinationRegistry, ServletConfig config, HttpServlet serviceListGenerator) {
        this.servletConfig = config;
        this.destinationRegistry = destinationRegistry;
        this.serviceListGenerator = serviceListGenerator;
        this.init();
    }

    public void setServiceListRelativePath(String relativePath) {
        this.serviceListRelativePath = relativePath;
    }

    protected String getBaseURL(HttpServletRequest request) {
        return this.forcedBaseAddress == null ? BaseUrlHelper.getBaseURL(request) : this.forcedBaseAddress;
    }

    protected void setBaseURLAttribute(HttpServletRequest request) {
        request.setAttribute(Message.BASE_PATH, (Object)this.getBaseURL(request));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void updateDestination(HttpServletRequest request, AbstractHTTPDestination d) {
        String base = this.getBaseURL(request);
        if (this.disableAddressUpdates) {
            this.updateDestination(request, base, d);
        } else {
            AbstractHTTPDestination abstractHTTPDestination = d;
            synchronized (abstractHTTPDestination) {
                this.updateDestination(request, base, d);
            }
        }
    }

    protected void updateDestination(HttpServletRequest request, String base, AbstractHTTPDestination d) {
        String ad = d.getEndpointInfo().getAddress();
        if (ad == null && d.getAddress() != null && d.getAddress().getAddress() != null && (ad = d.getAddress().getAddress().getValue()) == null) {
            ad = "/";
        }
        if (ad != null && !ad.startsWith(HTTP_PREFIX)) {
            if (this.disableAddressUpdates) {
                request.setAttribute("org.apache.cxf.transport.endpoint.address", (Object)(base + ad));
            } else {
                BaseUrlHelper.setAddress(d, base + ad);
            }
        }
    }

    private void init() {
        String isForcedBaseAddress;
        String isDisableAddressUpdates;
        String authServiceListRealm;
        String authServiceListPage;
        if (this.servletConfig == null) {
            return;
        }
        String hideServiceList = this.servletConfig.getInitParameter("hide-service-list-page");
        if (!StringUtils.isEmpty(hideServiceList)) {
            this.isHideServiceList = Boolean.valueOf(hideServiceList);
        }
        if (!StringUtils.isEmpty(authServiceListPage = this.servletConfig.getInitParameter("service-list-page-authenticate"))) {
            this.isAuthServiceListPage = Boolean.valueOf(authServiceListPage);
        }
        if (!StringUtils.isEmpty(authServiceListRealm = this.servletConfig.getInitParameter("service-list-page-authenticate-realm"))) {
            this.authServiceListPageRealm = authServiceListRealm;
        }
        if (!StringUtils.isEmpty(isDisableAddressUpdates = this.servletConfig.getInitParameter("disable-address-updates"))) {
            this.disableAddressUpdates = Boolean.valueOf(isDisableAddressUpdates);
        }
        if (!StringUtils.isEmpty(isForcedBaseAddress = this.servletConfig.getInitParameter("base-address"))) {
            this.forcedBaseAddress = isForcedBaseAddress;
        }
        try {
            this.serviceListGenerator.init(this.servletConfig);
        }
        catch (ServletException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String serviceListPath = this.servletConfig.getInitParameter("service-list-path");
        if (!StringUtils.isEmpty(serviceListPath)) {
            this.serviceListRelativePath = serviceListPath;
        }
    }

    public boolean filter(HttpServletRequest request, HttpServletResponse res) throws ServletException {
        return this.invoke(request, res, false);
    }

    public void invoke(HttpServletRequest request, HttpServletResponse res) throws ServletException {
        this.invoke(request, res, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean invoke(HttpServletRequest request, HttpServletResponse res, boolean returnErrors) throws ServletException {
        block15: {
            try {
                String pathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
                AbstractHTTPDestination d = this.destinationRegistry.getDestinationForPath(pathInfo, true);
                if (d == null) {
                    if (!this.isHideServiceList && (request.getRequestURI().endsWith(this.serviceListRelativePath) || request.getRequestURI().endsWith(this.serviceListRelativePath + "/") || StringUtils.isEmpty(pathInfo) || "/".equals(pathInfo))) {
                        if (this.isAuthServiceListPage) {
                            this.setAuthServiceListPageAttribute(request);
                        }
                        this.setBaseURLAttribute(request);
                        this.serviceListGenerator.service((ServletRequest)request, (ServletResponse)res);
                    } else {
                        d = this.destinationRegistry.checkRestfulRequest(pathInfo);
                        if (d == null || d.getMessageObserver() == null) {
                            if (returnErrors) {
                                LOG.warning("Can't find the request for " + request.getRequestURL() + "'s Observer ");
                                this.generateNotFound(request, res);
                            }
                            return false;
                        }
                    }
                }
                if (d == null || d.getMessageObserver() == null) break block15;
                Bus bus = d.getBus();
                ClassLoaderUtils.ClassLoaderHolder orig = null;
                try {
                    if (bus != null) {
                        ResourceManager manager;
                        ClassLoader loader = bus.getExtension(ClassLoader.class);
                        if (loader == null && (manager = bus.getExtension(ResourceManager.class)) != null) {
                            loader = manager.resolveResource("", ClassLoader.class);
                        }
                        if (loader != null) {
                            orig = ClassLoaderUtils.setThreadContextClassloader(loader);
                        }
                    }
                    this.updateDestination(request, d);
                    this.invokeDestination(request, res, d);
                }
                finally {
                    if (orig != null) {
                        orig.reset();
                    }
                }
            }
            catch (IOException e) {
                throw new ServletException((Throwable)e);
            }
        }
        return true;
    }

    private void setAuthServiceListPageAttribute(HttpServletRequest request) {
        request.setAttribute(AUTH_SERVICE_LIST, (Object)this.isAuthServiceListPage);
        request.setAttribute(AUTH_SERVICE_LIST_REALM, (Object)this.authServiceListPageRealm);
    }

    public void invokeDestination(HttpServletRequest request, HttpServletResponse response, AbstractHTTPDestination d) throws ServletException {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Service http request on thread: " + Thread.currentThread());
        }
        try {
            d.invoke(this.servletConfig, this.servletConfig.getServletContext(), request, response);
        }
        catch (IOException e) {
            throw new ServletException((Throwable)e);
        }
        finally {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Finished servicing http request on thread: " + Thread.currentThread());
            }
        }
    }

    protected void generateNotFound(HttpServletRequest request, HttpServletResponse res) throws IOException {
        res.setStatus(404);
        res.setContentType("text/html");
        res.getWriter().write("<html><body>No service was found.</body></html>");
    }
}

