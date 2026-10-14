/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletSecurityElement;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;
import jakarta.servlet.descriptor.JspPropertyGroupDescriptor;
import jakarta.servlet.descriptor.TaglibDescriptor;
import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletContainerInitializerHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.DecoratedObjectFactory;
import org.eclipse.jetty.util.DeprecationWarning;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Servlet Context Handler")
public class ServletContextHandler
extends ContextHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ServletContextHandler.class);
    public static final int SESSIONS = 1;
    public static final int SECURITY = 2;
    public static final int NO_SESSIONS = 0;
    public static final int NO_SECURITY = 0;
    protected final DecoratedObjectFactory _objFactory;
    protected Class<? extends SecurityHandler> _defaultSecurityHandlerClass = ConstraintSecurityHandler.class;
    protected SessionHandler _sessionHandler;
    protected SecurityHandler _securityHandler;
    protected ServletHandler _servletHandler;
    protected int _options;
    protected JspConfigDescriptor _jspConfig;
    private boolean _startListeners;

    public static ServletContextHandler getServletContextHandler(ServletContext servletContext, String purpose) {
        ContextHandler contextHandler = ContextHandler.getContextHandler(servletContext);
        if (contextHandler == null) {
            throw new IllegalStateException("No Jetty ContextHandler, " + purpose + " unavailable");
        }
        if (!(contextHandler instanceof ServletContextHandler)) {
            throw new IllegalStateException("No Jetty ServletContextHandler, " + purpose + " unavailable");
        }
        return (ServletContextHandler)contextHandler;
    }

    public static ServletContextHandler getServletContextHandler(ServletContext context) {
        ContextHandler handler = ServletContextHandler.getContextHandler(context);
        if (handler == null) {
            return null;
        }
        if (handler instanceof ServletContextHandler) {
            return (ServletContextHandler)handler;
        }
        return null;
    }

    public ServletContextHandler() {
        this(null, null, null, null, null);
    }

    public ServletContextHandler(int options) {
        this(null, null, options);
    }

    public ServletContextHandler(HandlerContainer parent, String contextPath) {
        this(parent, contextPath, null, null, null, null);
    }

    public ServletContextHandler(HandlerContainer parent, String contextPath, int options) {
        this(parent, contextPath, null, null, null, null, options);
    }

    public ServletContextHandler(HandlerContainer parent, String contextPath, boolean sessions, boolean security) {
        this(parent, contextPath, (sessions ? 1 : 0) | (security ? 2 : 0));
    }

    public ServletContextHandler(HandlerContainer parent, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        this(parent, null, sessionHandler, securityHandler, servletHandler, errorHandler);
    }

    public ServletContextHandler(HandlerContainer parent, String contextPath, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        this(parent, contextPath, sessionHandler, securityHandler, servletHandler, errorHandler, 0);
    }

    public ServletContextHandler(HandlerContainer parent, String contextPath, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler, int options) {
        super(null, parent, contextPath);
        this._options = options;
        this._scontext = new Context();
        this._sessionHandler = sessionHandler;
        this._securityHandler = securityHandler;
        this._servletHandler = servletHandler;
        this._objFactory = new DecoratedObjectFactory();
        this.relinkHandlers();
        if (errorHandler != null) {
            this.setErrorHandler(errorHandler);
        }
    }

    protected void setParent(HandlerContainer parent) {
        if (parent instanceof HandlerWrapper) {
            ((HandlerWrapper)parent).setHandler(this);
        } else if (parent instanceof HandlerCollection) {
            ((HandlerCollection)parent).addHandler(this);
        }
    }

    @Override
    public boolean addEventListener(EventListener listener) {
        if (super.addEventListener(listener)) {
            if ((listener instanceof HttpSessionActivationListener || listener instanceof HttpSessionAttributeListener || listener instanceof HttpSessionBindingListener || listener instanceof HttpSessionListener || listener instanceof HttpSessionIdListener) && this._sessionHandler != null) {
                this._sessionHandler.addEventListener(listener);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setHandler(Handler handler) {
        if (handler instanceof SessionHandler) {
            this.setSessionHandler((SessionHandler)handler);
        } else if (handler instanceof SecurityHandler) {
            this.setSecurityHandler((SecurityHandler)handler);
        } else if (handler instanceof ServletHandler) {
            this.setServletHandler((ServletHandler)handler);
        } else if (handler instanceof GzipHandler) {
            this.setGzipHandler((GzipHandler)handler);
        } else {
            if (handler != null) {
                LOG.warn("ServletContextHandler.setHandler should not be called directly. Use insertHandler or setSessionHandler etc.");
            }
            super.setHandler(handler);
        }
    }

    private void doSetHandler(HandlerWrapper wrapper, Handler handler) {
        if (wrapper == this) {
            super.setHandler(handler);
        } else {
            wrapper.setHandler(handler);
        }
    }

    private void relinkHandlers() {
        HandlerWrapper handler = this;
        if (this.getSessionHandler() != null) {
            while (!(handler.getHandler() instanceof SessionHandler) && !(handler.getHandler() instanceof SecurityHandler) && !(handler.getHandler() instanceof ServletHandler) && handler.getHandler() instanceof HandlerWrapper) {
                handler = (HandlerWrapper)handler.getHandler();
            }
            if (handler.getHandler() != this._sessionHandler) {
                this.doSetHandler(handler, this._sessionHandler);
            }
            handler = this._sessionHandler;
        }
        if (this.getSecurityHandler() != null) {
            while (!(handler.getHandler() instanceof SecurityHandler) && !(handler.getHandler() instanceof ServletHandler) && handler.getHandler() instanceof HandlerWrapper) {
                handler = (HandlerWrapper)handler.getHandler();
            }
            if (handler.getHandler() != this._securityHandler) {
                this.doSetHandler(handler, this._securityHandler);
            }
            handler = this._securityHandler;
        }
        if (this.getServletHandler() != null) {
            while (!(handler.getHandler() instanceof ServletHandler) && handler.getHandler() instanceof HandlerWrapper) {
                handler = (HandlerWrapper)handler.getHandler();
            }
            if (handler.getHandler() != this._servletHandler) {
                this.doSetHandler(handler, this._servletHandler);
            }
        }
    }

    @Override
    protected void doStart() throws Exception {
        this._objFactory.addDecorator(new DeprecationWarning());
        this.getServletContext().setAttribute(DecoratedObjectFactory.ATTR, this._objFactory);
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        this._objFactory.clear();
        this.getServletContext().removeAttribute(DecoratedObjectFactory.ATTR);
    }

    public Class<? extends SecurityHandler> getDefaultSecurityHandlerClass() {
        return this._defaultSecurityHandlerClass;
    }

    public void setDefaultSecurityHandlerClass(Class<? extends SecurityHandler> defaultSecurityHandlerClass) {
        this._defaultSecurityHandlerClass = defaultSecurityHandlerClass;
    }

    protected SessionHandler newSessionHandler() {
        return new SessionHandler();
    }

    protected SecurityHandler newSecurityHandler() {
        try {
            return this.getDefaultSecurityHandlerClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected ServletHandler newServletHandler() {
        return new ServletHandler();
    }

    @Override
    protected void startContext() throws Exception {
        for (ServletContainerInitializerCaller sci : this.getBeans(ServletContainerInitializerCaller.class)) {
            if (!sci.isStopped()) continue;
            sci.start();
            if (!this.isAuto(sci)) continue;
            this.manage(sci);
        }
        if (this._servletHandler != null && this._servletHandler.getListeners() != null) {
            for (ListenerHolder holder : this._servletHandler.getListeners()) {
                holder.start();
            }
        }
        this._startListeners = true;
        super.startContext();
        if (this._servletHandler != null) {
            this._servletHandler.initialize();
        }
    }

    @Override
    protected void stopContext() throws Exception {
        this._startListeners = false;
        super.stopContext();
    }

    @ManagedAttribute(value="context security handler", readonly=true)
    public SecurityHandler getSecurityHandler() {
        if (this._securityHandler == null && (this._options & 2) != 0 && !this.isStarted()) {
            this._securityHandler = this.newSecurityHandler();
        }
        return this._securityHandler;
    }

    @ManagedAttribute(value="context servlet handler", readonly=true)
    public ServletHandler getServletHandler() {
        if (this._servletHandler == null && !this.isStarted()) {
            this._servletHandler = this.newServletHandler();
        }
        return this._servletHandler;
    }

    @ManagedAttribute(value="context session handler", readonly=true)
    public SessionHandler getSessionHandler() {
        if (this._sessionHandler == null && (this._options & 1) != 0 && !this.isStarted()) {
            this._sessionHandler = this.newSessionHandler();
        }
        return this._sessionHandler;
    }

    public ServletHolder addServlet(String className, String pathSpec) {
        return this.getServletHandler().addServletWithMapping(className, pathSpec);
    }

    public ServletHolder addServlet(Class<? extends Servlet> servlet, String pathSpec) {
        return this.getServletHandler().addServletWithMapping(servlet, pathSpec);
    }

    public void addServlet(ServletHolder servlet, String pathSpec) {
        this.getServletHandler().addServletWithMapping(servlet, pathSpec);
    }

    public void addFilter(FilterHolder holder, String pathSpec, EnumSet<DispatcherType> dispatches) {
        this.getServletHandler().addFilterWithMapping(holder, pathSpec, dispatches);
    }

    public FilterHolder addFilter(Class<? extends Filter> filterClass, String pathSpec, EnumSet<DispatcherType> dispatches) {
        return this.getServletHandler().addFilterWithMapping(filterClass, pathSpec, dispatches);
    }

    public FilterHolder addFilter(String filterClass, String pathSpec, EnumSet<DispatcherType> dispatches) {
        return this.getServletHandler().addFilterWithMapping(filterClass, pathSpec, dispatches);
    }

    public ServletContainerInitializerHolder addServletContainerInitializer(ServletContainerInitializer sci) {
        if (!this.isStopped()) {
            throw new IllegalStateException("ServletContainerInitializers should be added before starting");
        }
        ServletContainerInitializerHolder holder = new ServletContainerInitializerHolder(sci, new Class[0]);
        this.addServletContainerInitializer(holder);
        return holder;
    }

    public ServletContainerInitializerHolder addServletContainerInitializer(ServletContainerInitializer sci, Class<?> ... classes) {
        if (!this.isStopped()) {
            throw new IllegalStateException("ServletContainerInitializers should be added before starting");
        }
        ServletContainerInitializerHolder holder = new ServletContainerInitializerHolder(sci, classes);
        this.addServletContainerInitializer(holder);
        return holder;
    }

    public void addServletContainerInitializer(ServletContainerInitializerHolder ... sciHolders) {
        ServletContainerInitializerStarter starter = this.getBean(ServletContainerInitializerStarter.class);
        if (starter == null) {
            starter = new ServletContainerInitializerStarter();
            this.addBean(starter, true);
        }
        starter.addServletContainerInitializerHolders(sciHolders);
    }

    protected ServletRegistration.Dynamic dynamicHolderAdded(ServletHolder holder) {
        return holder.getRegistration();
    }

    protected void addRoles(String ... roleNames) {
        if (this._securityHandler != null && this._securityHandler instanceof ConstraintAware) {
            HashSet<String> union = new HashSet<String>();
            Set<String> existing = ((ConstraintAware)((Object)this._securityHandler)).getRoles();
            if (existing != null) {
                union.addAll(existing);
            }
            union.addAll(Arrays.asList(roleNames));
            ((ConstraintSecurityHandler)this._securityHandler).setRoles(union);
        }
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic registration, ServletSecurityElement servletSecurityElement) {
        Collection<String> pathSpecs = registration.getMappings();
        if (pathSpecs != null) {
            for (String pathSpec : pathSpecs) {
                List<ConstraintMapping> mappings = ConstraintSecurityHandler.createConstraintsWithMappingsForPath(registration.getName(), pathSpec, servletSecurityElement);
                for (ConstraintMapping m4 : mappings) {
                    ((ConstraintAware)((Object)this.getSecurityHandler())).addConstraintMapping(m4);
                }
            }
        }
        return Collections.emptySet();
    }

    @Override
    public void callContextInitialized(ServletContextListener l, ServletContextEvent e) {
        try {
            if (this.isProgrammaticListener(l)) {
                this.getServletContext().setEnabled(false);
            }
            super.callContextInitialized(l, e);
        }
        finally {
            this.getServletContext().setEnabled(true);
        }
    }

    @Override
    public void callContextDestroyed(ServletContextListener l, ServletContextEvent e) {
        super.callContextDestroyed(l, e);
    }

    private void replaceHandler(HandlerWrapper handler, HandlerWrapper replacement) {
        if (this.isStarted()) {
            throw new IllegalStateException("STARTED");
        }
        Handler next = null;
        if (handler != null) {
            next = handler.getHandler();
            handler.setHandler(null);
            ServletContextHandler wrapper = this;
            while (wrapper != null) {
                if (wrapper.getHandler() == handler) {
                    this.doSetHandler(wrapper, replacement);
                    break;
                }
                wrapper = wrapper.getHandler() instanceof HandlerWrapper ? (HandlerWrapper)wrapper.getHandler() : null;
            }
        }
        if (next != null && replacement.getHandler() == null) {
            replacement.setHandler(next);
        }
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.replaceHandler(this._sessionHandler, sessionHandler);
        this._sessionHandler = sessionHandler;
        this.relinkHandlers();
    }

    public void setSecurityHandler(SecurityHandler securityHandler) {
        this.replaceHandler(this._securityHandler, securityHandler);
        this._securityHandler = securityHandler;
        this.relinkHandlers();
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this.replaceHandler(this._servletHandler, servletHandler);
        this._servletHandler = servletHandler;
        this.relinkHandlers();
    }

    @Deprecated
    public void setGzipHandler(GzipHandler gzipHandler) {
        this.insertHandler(gzipHandler);
        LOG.warn("ServletContextHandler.setGzipHandler(GzipHandler) is deprecated, use insertHandler(HandlerWrapper) instead.");
    }

    @Override
    public void insertHandler(HandlerWrapper handler) {
        if (handler instanceof SessionHandler) {
            this.setSessionHandler((SessionHandler)handler);
        } else if (handler instanceof SecurityHandler) {
            this.setSecurityHandler((SecurityHandler)handler);
        } else if (handler instanceof ServletHandler) {
            this.setServletHandler((ServletHandler)handler);
        } else {
            HandlerWrapper wrapper;
            HandlerWrapper tail = handler;
            while (tail.getHandler() instanceof HandlerWrapper) {
                tail = (HandlerWrapper)tail.getHandler();
            }
            if (tail.getHandler() != null) {
                throw new IllegalArgumentException("bad tail of inserted wrapper chain");
            }
            HandlerWrapper h2 = this;
            while (h2.getHandler() instanceof HandlerWrapper && !((wrapper = (HandlerWrapper)h2.getHandler()) instanceof SessionHandler) && !(wrapper instanceof SecurityHandler) && !(wrapper instanceof ServletHandler)) {
                h2 = wrapper;
            }
            Handler next = h2.getHandler();
            this.doSetHandler(h2, handler);
            this.doSetHandler(tail, next);
        }
        this.relinkHandlers();
    }

    public DecoratedObjectFactory getObjectFactory() {
        return this._objFactory;
    }

    void destroyServlet(Servlet servlet) {
        this._objFactory.destroy(servlet);
    }

    void destroyFilter(Filter filter2) {
        this._objFactory.destroy(filter2);
    }

    void destroyListener(EventListener listener) {
        this._objFactory.destroy(listener);
    }

    public class Context
    extends ContextHandler.Context {
        @Override
        public RequestDispatcher getNamedDispatcher(String name) {
            ServletContextHandler context = ServletContextHandler.this;
            if (ServletContextHandler.this._servletHandler == null) {
                return null;
            }
            ServletHolder holder = ServletContextHandler.this._servletHandler.getServlet(name);
            if (holder == null || !holder.isEnabled()) {
                return null;
            }
            return new Dispatcher(context, name);
        }

        private void checkDynamic(String name) {
            if (ServletContextHandler.this.isStarted()) {
                throw new IllegalStateException();
            }
            if (ServletContextHandler.this.getServletHandler().isInitialized()) {
                throw new IllegalStateException();
            }
            if (StringUtil.isBlank(name)) {
                throw new IllegalArgumentException("Missing name");
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
        }

        @Override
        public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
            this.checkDynamic(filterName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            FilterHolder holder = handler.getFilter(filterName);
            if (holder == null) {
                holder = handler.newFilterHolder(Source.JAVAX_API);
                holder.setName(filterName);
                holder.setHeldClass(filterClass);
                handler.addFilter(holder);
                return holder.getRegistration();
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setHeldClass(filterClass);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public FilterRegistration.Dynamic addFilter(String filterName, String className) {
            this.checkDynamic(filterName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            FilterHolder holder = handler.getFilter(filterName);
            if (holder == null) {
                holder = handler.newFilterHolder(Source.JAVAX_API);
                holder.setName(filterName);
                holder.setClassName(className);
                handler.addFilter(holder);
                return holder.getRegistration();
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setClassName(className);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public FilterRegistration.Dynamic addFilter(String filterName, Filter filter2) {
            this.checkDynamic(filterName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            FilterHolder holder = handler.getFilter(filterName);
            if (holder == null) {
                holder = handler.newFilterHolder(Source.JAVAX_API);
                holder.setName(filterName);
                holder.setFilter(filter2);
                handler.addFilter(holder);
                return holder.getRegistration();
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setFilter(filter2);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
            this.checkDynamic(servletName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            ServletHolder holder = handler.getServlet(servletName);
            if (holder == null) {
                holder = handler.newServletHolder(Source.JAVAX_API);
                holder.setName(servletName);
                holder.setHeldClass(servletClass);
                handler.addServlet(holder);
                return ServletContextHandler.this.dynamicHolderAdded(holder);
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setHeldClass(servletClass);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public ServletRegistration.Dynamic addServlet(String servletName, String className) {
            this.checkDynamic(servletName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            ServletHolder holder = handler.getServlet(servletName);
            if (holder == null) {
                holder = handler.newServletHolder(Source.JAVAX_API);
                holder.setName(servletName);
                holder.setClassName(className);
                handler.addServlet(holder);
                return ServletContextHandler.this.dynamicHolderAdded(holder);
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setClassName(className);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
            this.checkDynamic(servletName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            ServletHolder holder = handler.getServlet(servletName);
            if (holder == null) {
                holder = handler.newServletHolder(Source.JAVAX_API);
                holder.setName(servletName);
                holder.setServlet(servlet);
                handler.addServlet(holder);
                return ServletContextHandler.this.dynamicHolderAdded(holder);
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null) {
                holder.setServlet(servlet);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
            this.checkDynamic(servletName);
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            ServletHolder holder = handler.getServlet(servletName);
            if (holder == null) {
                holder = handler.newServletHolder(Source.JAVAX_API);
                holder.setName(servletName);
                holder.setForcedPath(jspFile);
                handler.addServlet(holder);
                return ServletContextHandler.this.dynamicHolderAdded(holder);
            }
            if (holder.getClassName() == null && holder.getHeldClass() == null && holder.getForcedPath() == null) {
                holder.setForcedPath(jspFile);
                return holder.getRegistration();
            }
            return null;
        }

        @Override
        public String getInitParameter(String name) {
            Objects.requireNonNull(name);
            return super.getInitParameter(name);
        }

        @Override
        public boolean setInitParameter(String name, String value) {
            Objects.requireNonNull(name);
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            return super.setInitParameter(name, value);
        }

        @Override
        public <T> T createInstance(Class<T> clazz) throws ServletException {
            return ServletContextHandler.this._objFactory.decorate(super.createInstance(clazz));
        }

        public <T> T createInstance(BaseHolder<T> holder) throws ServletException {
            try {
                DecoratedObjectFactory.associateInfo(holder);
                T t2 = this.createInstance(holder.getHeldClass());
                return t2;
            }
            finally {
                DecoratedObjectFactory.disassociateInfo();
            }
        }

        public <T extends Filter> void destroyFilter(T f) {
            ServletContextHandler.this._objFactory.destroy(f);
        }

        public <T extends Servlet> void destroyServlet(T s2) {
            ServletContextHandler.this._objFactory.destroy(s2);
        }

        @Override
        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getDefaultSessionTrackingModes();
            }
            return null;
        }

        @Override
        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getEffectiveSessionTrackingModes();
            }
            return null;
        }

        @Override
        public FilterRegistration getFilterRegistration(String filterName) {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            FilterHolder holder = ServletContextHandler.this.getServletHandler().getFilter(filterName);
            return holder == null ? null : holder.getRegistration();
        }

        @Override
        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            HashMap<String, FilterRegistration.Dynamic> registrations = new HashMap<String, FilterRegistration.Dynamic>();
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            FilterHolder[] holders = handler.getFilters();
            if (holders != null) {
                for (FilterHolder holder : holders) {
                    registrations.put(holder.getName(), holder.getRegistration());
                }
            }
            return registrations;
        }

        @Override
        public ServletRegistration getServletRegistration(String servletName) {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            ServletHolder holder = ServletContextHandler.this.getServletHandler().getServlet(servletName);
            return holder == null ? null : holder.getRegistration();
        }

        @Override
        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            HashMap<String, ServletRegistration.Dynamic> registrations = new HashMap<String, ServletRegistration.Dynamic>();
            ServletHandler handler = ServletContextHandler.this.getServletHandler();
            ServletHolder[] holders = handler.getServlets();
            if (holders != null) {
                for (ServletHolder holder : holders) {
                    registrations.put(holder.getName(), holder.getRegistration());
                }
            }
            return registrations;
        }

        @Override
        public SessionCookieConfig getSessionCookieConfig() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getSessionCookieConfig();
            }
            return null;
        }

        @Override
        public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            if (ServletContextHandler.this._sessionHandler != null) {
                ServletContextHandler.this._sessionHandler.setSessionTrackingModes(sessionTrackingModes);
            }
        }

        @Override
        public int getSessionTimeout() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            int timeout2 = -1;
            if (ServletContextHandler.this._sessionHandler != null) {
                timeout2 = ServletContextHandler.this._sessionHandler.getMaxInactiveInterval();
            }
            return (int)TimeUnit.SECONDS.toMinutes(timeout2);
        }

        @Override
        public void setSessionTimeout(int sessionTimeout) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            if (ServletContextHandler.this._sessionHandler != null) {
                long tmp = TimeUnit.MINUTES.toSeconds(sessionTimeout);
                if (tmp > Integer.MAX_VALUE) {
                    tmp = Integer.MAX_VALUE;
                }
                if (tmp < Integer.MIN_VALUE) {
                    tmp = Integer.MIN_VALUE;
                }
                ServletContextHandler.this._sessionHandler.setMaxInactiveInterval((int)tmp);
            }
        }

        @Override
        public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            return super.createServlet(clazz);
        }

        @Override
        public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            return super.createFilter(clazz);
        }

        @Override
        public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
            block3: {
                if (!this._enabled) {
                    throw new UnsupportedOperationException();
                }
                try {
                    this.checkListener(clazz);
                }
                catch (IllegalArgumentException e) {
                    if (ServletContextListener.class.isAssignableFrom(clazz)) break block3;
                    throw e;
                }
            }
            return super.createListener(clazz);
        }

        @Override
        public void addListener(String className) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            super.addListener(className);
        }

        @Override
        public <T extends EventListener> void addListener(T t2) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            this.checkListener(t2.getClass());
            ListenerHolder holder = ServletContextHandler.this.getServletHandler().newListenerHolder(Source.JAVAX_API);
            holder.setListener(t2);
            ServletContextHandler.this.addProgrammaticListener(t2);
            ServletContextHandler.this.getServletHandler().addListener(holder);
            if (ServletContextHandler.this._startListeners) {
                try {
                    holder.start();
                }
                catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        @Override
        public void addListener(Class<? extends EventListener> listenerClass) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            super.addListener(listenerClass);
        }

        @Override
        public JspConfigDescriptor getJspConfigDescriptor() {
            return ServletContextHandler.this._jspConfig;
        }

        @Override
        public void setJspConfigDescriptor(JspConfigDescriptor d) {
            ServletContextHandler.this._jspConfig = d;
        }

        @Override
        public void declareRoles(String ... roleNames) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            ServletContextHandler.this.addRoles(roleNames);
        }

        @Override
        public String getRequestCharacterEncoding() {
            return ServletContextHandler.this.getDefaultRequestCharacterEncoding();
        }

        @Override
        public void setRequestCharacterEncoding(String encoding) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            ServletContextHandler.this.setDefaultRequestCharacterEncoding(encoding);
        }

        @Override
        public String getResponseCharacterEncoding() {
            return ServletContextHandler.this.getDefaultResponseCharacterEncoding();
        }

        @Override
        public void setResponseCharacterEncoding(String encoding) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            ServletContextHandler.this.setDefaultResponseCharacterEncoding(encoding);
        }
    }

    public static interface ServletContainerInitializerCaller
    extends LifeCycle {
    }

    public static class ServletContainerInitializerStarter
    extends ContainerLifeCycle
    implements ServletContainerInitializerCaller {
        public void addServletContainerInitializerHolders(ServletContainerInitializerHolder ... holders) {
            for (ServletContainerInitializerHolder holder : holders) {
                this.addBean(holder, true);
            }
        }

        public Collection<ServletContainerInitializerHolder> getServletContainerInitializerHolders() {
            return this.getContainedBeans(ServletContainerInitializerHolder.class);
        }

        @Override
        protected void doStart() throws Exception {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Starting SCIs");
            }
            super.doStart();
        }

        @Override
        protected void doStop() throws Exception {
            Collection<ServletContainerInitializerHolder> holders = this.getServletContainerInitializerHolders();
            for (ServletContainerInitializerHolder h2 : holders) {
                if (h2.getSource().getOrigin() == Source.Origin.EMBEDDED) continue;
                this.removeBean(h2);
            }
            super.doStop();
        }
    }

    @Deprecated
    public static class Initializer
    extends AbstractLifeCycle
    implements ServletContainerInitializerCaller {
        private final ServletContextHandler _context;
        private final ServletContainerInitializer _sci;
        private final Set<Class<?>> _classes;

        public Initializer(ServletContextHandler context, ServletContainerInitializer sci, Set<Class<?>> classes) {
            this._context = context;
            this._sci = sci;
            this._classes = classes;
        }

        public Initializer(ServletContextHandler context, ServletContainerInitializer sci) {
            this(context, sci, Collections.emptySet());
        }

        @Override
        protected void doStart() throws Exception {
            boolean oldExtended = this._context.getServletContext().isExtendedListenerTypes();
            try {
                this._context.getServletContext().setExtendedListenerTypes(true);
                this._sci.onStartup(this._classes, this._context.getServletContext());
            }
            finally {
                this._context.getServletContext().setExtendedListenerTypes(oldExtended);
            }
        }
    }

    public static class JspConfig
    implements JspConfigDescriptor {
        private final List<TaglibDescriptor> _taglibs = new ArrayList<TaglibDescriptor>();
        private final List<JspPropertyGroupDescriptor> _jspPropertyGroups = new ArrayList<JspPropertyGroupDescriptor>();

        @Override
        public Collection<TaglibDescriptor> getTaglibs() {
            return new ArrayList<TaglibDescriptor>(this._taglibs);
        }

        public void addTaglibDescriptor(TaglibDescriptor d) {
            this._taglibs.add(d);
        }

        @Override
        public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
            return new ArrayList<JspPropertyGroupDescriptor>(this._jspPropertyGroups);
        }

        public void addJspPropertyGroup(JspPropertyGroupDescriptor g2) {
            this._jspPropertyGroups.add(g2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("JspConfigDescriptor: \n");
            for (TaglibDescriptor taglib : this._taglibs) {
                sb.append(taglib).append("\n");
            }
            for (JspPropertyGroupDescriptor jpg : this._jspPropertyGroups) {
                sb.append(jpg).append("\n");
            }
            return sb.toString();
        }
    }

    public static class TagLib
    implements TaglibDescriptor {
        private String _uri;
        private String _location;

        @Override
        public String getTaglibURI() {
            return this._uri;
        }

        public void setTaglibURI(String uri) {
            this._uri = uri;
        }

        @Override
        public String getTaglibLocation() {
            return this._location;
        }

        public void setTaglibLocation(String location) {
            this._location = location;
        }

        public String toString() {
            return "TagLibDescriptor: taglib-uri=" + this._uri + " location=" + this._location;
        }
    }

    public static class JspPropertyGroup
    implements JspPropertyGroupDescriptor {
        private final List<String> _urlPatterns = new ArrayList<String>();
        private String _elIgnored;
        private String _pageEncoding;
        private String _scriptingInvalid;
        private String _isXml;
        private final List<String> _includePreludes = new ArrayList<String>();
        private final List<String> _includeCodas = new ArrayList<String>();
        private String _deferredSyntaxAllowedAsLiteral;
        private String _trimDirectiveWhitespaces;
        private String _defaultContentType;
        private String _buffer;
        private String _errorOnUndeclaredNamespace;

        @Override
        public Collection<String> getUrlPatterns() {
            return new ArrayList<String>(this._urlPatterns);
        }

        public void addUrlPattern(String s2) {
            if (!this._urlPatterns.contains(s2)) {
                this._urlPatterns.add(s2);
            }
        }

        @Override
        public String getElIgnored() {
            return this._elIgnored;
        }

        public void setElIgnored(String s2) {
            this._elIgnored = s2;
        }

        @Override
        public String getPageEncoding() {
            return this._pageEncoding;
        }

        public void setPageEncoding(String pageEncoding) {
            this._pageEncoding = pageEncoding;
        }

        public void setScriptingInvalid(String scriptingInvalid) {
            this._scriptingInvalid = scriptingInvalid;
        }

        public void setIsXml(String isXml) {
            this._isXml = isXml;
        }

        public void setDeferredSyntaxAllowedAsLiteral(String deferredSyntaxAllowedAsLiteral) {
            this._deferredSyntaxAllowedAsLiteral = deferredSyntaxAllowedAsLiteral;
        }

        public void setTrimDirectiveWhitespaces(String trimDirectiveWhitespaces) {
            this._trimDirectiveWhitespaces = trimDirectiveWhitespaces;
        }

        public void setDefaultContentType(String defaultContentType) {
            this._defaultContentType = defaultContentType;
        }

        public void setBuffer(String buffer) {
            this._buffer = buffer;
        }

        public void setErrorOnUndeclaredNamespace(String errorOnUndeclaredNamespace) {
            this._errorOnUndeclaredNamespace = errorOnUndeclaredNamespace;
        }

        @Override
        public String getScriptingInvalid() {
            return this._scriptingInvalid;
        }

        @Override
        public String getIsXml() {
            return this._isXml;
        }

        @Override
        public Collection<String> getIncludePreludes() {
            return new ArrayList<String>(this._includePreludes);
        }

        public void addIncludePrelude(String prelude) {
            if (!this._includePreludes.contains(prelude)) {
                this._includePreludes.add(prelude);
            }
        }

        @Override
        public Collection<String> getIncludeCodas() {
            return new ArrayList<String>(this._includeCodas);
        }

        public void addIncludeCoda(String coda) {
            if (!this._includeCodas.contains(coda)) {
                this._includeCodas.add(coda);
            }
        }

        @Override
        public String getDeferredSyntaxAllowedAsLiteral() {
            return this._deferredSyntaxAllowedAsLiteral;
        }

        @Override
        public String getTrimDirectiveWhitespaces() {
            return this._trimDirectiveWhitespaces;
        }

        @Override
        public String getDefaultContentType() {
            return this._defaultContentType;
        }

        @Override
        public String getBuffer() {
            return this._buffer;
        }

        @Override
        public String getErrorOnUndeclaredNamespace() {
            return this._errorOnUndeclaredNamespace;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("JspPropertyGroupDescriptor:");
            sb.append(" el-ignored=").append(this._elIgnored);
            sb.append(" is-xml=").append(this._isXml);
            sb.append(" page-encoding=").append(this._pageEncoding);
            sb.append(" scripting-invalid=").append(this._scriptingInvalid);
            sb.append(" deferred-syntax-allowed-as-literal=").append(this._deferredSyntaxAllowedAsLiteral);
            sb.append(" trim-directive-whitespaces").append(this._trimDirectiveWhitespaces);
            sb.append(" default-content-type=").append(this._defaultContentType);
            sb.append(" buffer=").append(this._buffer);
            sb.append(" error-on-undeclared-namespace=").append(this._errorOnUndeclaredNamespace);
            for (String prelude : this._includePreludes) {
                sb.append(" include-prelude=").append(prelude);
            }
            for (String coda : this._includeCodas) {
                sb.append(" include-coda=").append(coda);
            }
            return sb.toString();
        }
    }
}

