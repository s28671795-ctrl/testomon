/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.FilterChain
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletException
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletRequestWrapper
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transport.servlet.AbstractHTTPServlet;
import org.apache.cxf.transport.servlet.ServletConfigAware;
import org.apache.cxf.transport.servlet.ServletContextResourceResolver;
import org.apache.cxf.transport.servlet.ServletController;
import org.apache.cxf.transport.servlet.servicelist.ServiceListGeneratorServlet;

public class CXFNonSpringServlet
extends AbstractHTTPServlet {
    public static final String TRANSPORT_ID = "transportId";
    private static final long serialVersionUID = -2437897227486327166L;
    private static final String IGNORE_SERVLET_CONTEXT_RESOLVER = "ignore.servlet.context.resolver";
    private static final String DEFAULT_TRANSPORT_ID = "http://cxf.apache.org/transports/http/configuration";
    protected Bus bus;
    private DestinationRegistry destinationRegistry;
    private boolean globalRegistry;
    private ServletController controller;
    private ClassLoader loader;
    private boolean loadBus = true;

    public CXFNonSpringServlet() {
    }

    public CXFNonSpringServlet(DestinationRegistry destinationRegistry) {
        this(destinationRegistry, true);
    }

    public CXFNonSpringServlet(DestinationRegistry destinationRegistry, boolean loadBus) {
        this.destinationRegistry = destinationRegistry;
        this.globalRegistry = destinationRegistry != null;
        this.loadBus = loadBus;
    }

    public CXFNonSpringServlet(DestinationRegistry destinationRegistry, Bus bus) {
        this.destinationRegistry = destinationRegistry;
        this.globalRegistry = destinationRegistry != null;
        this.bus = bus;
        this.loadBus = false;
    }

    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        if (this.bus == null && this.loadBus) {
            this.loadBus(sc);
        }
        if (this.bus != null) {
            this.loader = this.initClassLoader();
            this.registerServletContextResolver(sc);
            if (this.destinationRegistry == null) {
                this.destinationRegistry = this.getDestinationRegistryFromBusOrDefault(sc.getInitParameter(TRANSPORT_ID));
            }
        }
        this.controller = this.createServletController(sc);
        this.finalizeServletInit(sc);
    }

    @Override
    protected void finalizeServletInit(ServletConfig servletConfig) throws ServletException {
        super.finalizeServletInit(servletConfig);
        if (this.destinationRegistry instanceof ServletConfigAware) {
            ((ServletConfigAware)((Object)this.destinationRegistry)).onServletConfigAvailable(servletConfig);
        }
    }

    protected void registerServletContextResolver(ServletConfig sc) {
        if (Boolean.valueOf(sc.getInitParameter(IGNORE_SERVLET_CONTEXT_RESOLVER)).booleanValue()) {
            return;
        }
        ResourceManager resourceManager = this.bus.getExtension(ResourceManager.class);
        resourceManager.addResourceResolver(new ServletContextResourceResolver(sc.getServletContext()));
    }

    protected ClassLoader initClassLoader() {
        return this.bus.getExtension(ClassLoader.class);
    }

    protected DestinationRegistry getDestinationRegistryFromBusOrDefault(String transportId) {
        DestinationFactoryManager dfm = this.bus.getExtension(DestinationFactoryManager.class);
        try {
            DestinationFactory df;
            Set<String> candidates;
            String peferredTransportId = transportId;
            if (StringUtils.isEmpty(peferredTransportId) && this.getBus() != null) {
                peferredTransportId = (String)this.getBus().getProperty("org.apache.cxf.preferred.transport.id");
            }
            if (StringUtils.isEmpty(peferredTransportId) && !(candidates = dfm.getRegisteredDestinationFactoryNames()).contains(DEFAULT_TRANSPORT_ID)) {
                peferredTransportId = candidates.stream().filter(name -> name.endsWith("/configuration")).findAny().orElse(DEFAULT_TRANSPORT_ID);
            }
            DestinationFactory destinationFactory = df = StringUtils.isEmpty(peferredTransportId) ? dfm.getDestinationFactory(DEFAULT_TRANSPORT_ID) : dfm.getDestinationFactory(peferredTransportId);
            if (df instanceof HTTPTransportFactory) {
                HTTPTransportFactory transportFactory = (HTTPTransportFactory)df;
                return transportFactory.getRegistry();
            }
        }
        catch (BusException busException) {
            // empty catch block
        }
        return null;
    }

    protected void loadBus(ServletConfig sc) {
        this.bus = BusFactory.newInstance().createBus();
    }

    protected ServletController createServletController(ServletConfig servletConfig) {
        ServiceListGeneratorServlet serviceListGeneratorServlet = new ServiceListGeneratorServlet(this.destinationRegistry, this.bus);
        return new ServletController(this.destinationRegistry, servletConfig, serviceListGeneratorServlet);
    }

    @Override
    public Bus getBus() {
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ClassLoaderUtils.ClassLoaderHolder origLoader = null;
        Bus origBus = null;
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            block10: {
                block11: {
                    try {
                        HttpServletRequest httpRequest;
                        if (this.loader != null) {
                            origLoader = ClassLoaderUtils.setThreadContextClassloader(this.loader);
                        }
                        if (this.bus != null) {
                            origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
                        }
                        if (!this.controller.filter((HttpServletRequest)new HttpServletRequestFilter(httpRequest = (HttpServletRequest)request, super.getServletName()), (HttpServletResponse)response)) break block10;
                        if (origBus == this.bus) break block11;
                    }
                    catch (Throwable throwable) {
                        if (origBus != this.bus) {
                            BusFactory.setThreadDefaultBus(origBus);
                        }
                        if (origLoader != null) {
                            origLoader.reset();
                        }
                        throw throwable;
                    }
                    BusFactory.setThreadDefaultBus(origBus);
                }
                if (origLoader != null) {
                    origLoader.reset();
                }
                return;
            }
            if (origBus != this.bus) {
                BusFactory.setThreadDefaultBus(origBus);
            }
            if (origLoader != null) {
                origLoader.reset();
            }
        }
        chain.doFilter(request, response);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ClassLoaderUtils.ClassLoaderHolder origLoader;
        block7: {
            origLoader = null;
            Bus origBus = null;
            try {
                if (this.loader != null) {
                    origLoader = ClassLoaderUtils.setThreadContextClassloader(this.loader);
                }
                if (this.bus != null) {
                    origBus = BusFactory.getAndSetThreadDefaultBus(this.bus);
                }
                this.controller.invoke(request, response);
                if (origBus == this.bus) break block7;
            }
            catch (Throwable throwable) {
                if (origBus != this.bus) {
                    BusFactory.setThreadDefaultBus(origBus);
                }
                if (origLoader != null) {
                    origLoader.reset();
                }
                throw throwable;
            }
            BusFactory.setThreadDefaultBus(origBus);
        }
        if (origLoader != null) {
            origLoader.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void destroy() {
        if (!this.globalRegistry) {
            for (String path : this.destinationRegistry.getDestinationsPaths()) {
                AbstractHTTPDestination dest;
                AbstractHTTPDestination abstractHTTPDestination = dest = this.destinationRegistry.getDestinationForPath(path);
                synchronized (abstractHTTPDestination) {
                    this.destinationRegistry.removeDestination(path);
                    dest.releaseRegistry();
                }
            }
            this.destinationRegistry = null;
        }
        this.destroyBus();
        super.destroy();
    }

    public void destroyBus() {
        if (this.bus != null) {
            this.bus.shutdown(true);
            this.bus = null;
        }
    }

    private static class HttpServletRequestFilter
    extends HttpServletRequestWrapper {
        private String filterName;
        private String servletPath;
        private String pathInfo;

        HttpServletRequestFilter(HttpServletRequest request, String filterName) {
            super(request);
            this.filterName = filterName;
        }

        public String getServletPath() {
            if (this.servletPath == null) {
                try {
                    String mapping;
                    Collection mappings;
                    Method m4 = ServletContext.class.getMethod("getFilterRegistration", String.class);
                    Object registration = m4.invoke((Object)super.getServletContext(), this.filterName);
                    if (registration != null && !(mappings = CastUtils.cast((Collection)(m4 = registration.getClass().getMethod("getUrlPatternMappings", new Class[0])).invoke(registration, new Object[0]))).isEmpty() && (mapping = (String)mappings.iterator().next()).endsWith("/*")) {
                        this.servletPath = mapping.substring(0, mapping.length() - 2);
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                if (this.servletPath == null) {
                    this.servletPath = "";
                }
            }
            return this.servletPath;
        }

        public String getPathInfo() {
            if (this.pathInfo == null) {
                String prefix;
                this.pathInfo = super.getPathInfo();
                if (this.pathInfo == null) {
                    this.pathInfo = this.getRequestURI();
                }
                if (this.pathInfo.startsWith(prefix = super.getContextPath() + this.getServletPath())) {
                    this.pathInfo = this.pathInfo.substring(prefix.length());
                }
            }
            return this.pathInfo;
        }
    }
}

