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

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.configuration.jsse.TLSServerParameters;
import org.apache.cxf.configuration.security.ClientAuthentication;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.transport.HttpUriMapper;
import org.apache.cxf.transport.http_jetty.JettyHTTPHandler;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;
import org.apache.cxf.transport.http_jetty.ServerEngine;
import org.apache.cxf.transport.http_jetty.ThreadingParameters;
import org.apache.cxf.transport.https.SSLUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.component.Container;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

public class JettyHTTPServerEngine
implements ServerEngine {
    public static final String DO_NOT_CHECK_URL_PROP = "org.apache.cxf.transports.http_jetty.DontCheckUrl";
    private static final Logger LOG = LogUtils.getL7dLogger(JettyHTTPServerEngine.class);
    private int port;
    private String host;
    private String protocol = "http";
    private Boolean isSessionSupport = false;
    private int sessionTimeout = -1;
    private Boolean isReuseAddress = true;
    private Boolean continuationsEnabled = true;
    private int maxIdleTime = 200000;
    private Boolean sendServerVersion = true;
    private int servantCount;
    private Server server;
    private Connector connector;
    private List<Handler> handlers;
    private ContextHandlerCollection contexts;
    private Container.Listener mBeanContainer;
    private SessionHandler sessionHandler;
    private ThreadPool threadPool;
    private TLSServerParameters tlsServerParameters;
    private ThreadingParameters threadingParameters;
    private boolean configFinalized;
    private List<String> registedPaths = new CopyOnWriteArrayList<String>();

    public JettyHTTPServerEngine(Container.Listener mBeanContainer, String host2, int port) {
        this.host = host2;
        this.port = port;
        this.mBeanContainer = mBeanContainer;
    }

    public JettyHTTPServerEngine() {
    }

    public void setThreadPool(ThreadPool p) {
        this.threadPool = p;
    }

    public void setPort(int p) {
        this.port = p;
    }

    public void setHost(String host2) {
        this.host = host2;
    }

    public void setContinuationsEnabled(boolean enabled) {
        this.continuationsEnabled = enabled;
    }

    public boolean getContinuationsEnabled() {
        return this.continuationsEnabled;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }

    public void shutdown() {
        this.registedPaths.clear();
        if (this.shouldDestroyPort()) {
            if (this.servantCount == 0) {
                JettyHTTPServerEngineFactory.destroyForPort(this.port);
            } else {
                LOG.log(Level.WARNING, "FAILED_TO_SHUTDOWN_ENGINE_MSG", this.port);
            }
        }
    }

    private boolean shouldDestroyPort() {
        String s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.transports.http_jetty.DontClosePort." + this.port);
        if (s2 == null) {
            s2 = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.transports.http_jetty.DontClosePort");
        }
        return Boolean.valueOf(s2) == false;
    }

    private boolean shouldCheckUrl(Bus bus) {
        Object prop = null;
        if (bus != null) {
            prop = bus.getProperty(DO_NOT_CHECK_URL_PROP);
        }
        if (prop == null) {
            prop = SystemPropertyAction.getPropertyOrNull(DO_NOT_CHECK_URL_PROP);
        }
        return !PropertyUtils.isTrue(prop);
    }

    public Server getServer() {
        return this.server;
    }

    public void setServer(Server s2) {
        this.server = s2;
    }

    public void setConnector(Connector c) {
        this.connector = c;
    }

    public void setHandlers(List<Handler> h2) {
        this.handlers = h2;
    }

    public void setSessionSupport(boolean support) {
        this.isSessionSupport = support;
    }

    public boolean isSessionSupport() {
        return this.isSessionSupport;
    }

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    public Connector getConnector() {
        return this.connector;
    }

    public boolean isReuseAddress() {
        return this.isReuseAddress;
    }

    public void setReuseAddress(boolean reuse) {
        this.isReuseAddress = reuse;
    }

    public int getMaxIdleTime() {
        return this.maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdle) {
        this.maxIdleTime = maxIdle;
    }

    protected void checkRegistedContext(URL url2) {
        String path = url2.getPath();
        for (String registedPath : this.registedPaths) {
            if (path.equals(registedPath)) {
                throw new Fault(new Message("ADD_HANDLER_CONTEXT_IS_USED_MSG", LOG, url2, registedPath));
            }
            if (path.equals(HttpUriMapper.getContextName(registedPath))) {
                throw new Fault(new Message("ADD_HANDLER_CONTEXT_IS_USED_MSG", LOG, url2, registedPath));
            }
            if (!registedPath.equals(HttpUriMapper.getContextName(path))) continue;
            throw new Fault(new Message("ADD_HANDLER_CONTEXT_CONFILICT_MSG", LOG, url2, registedPath));
        }
    }

    private Server createServer() {
        Server s2 = null;
        if (this.connector != null && this.connector.getServer() != null) {
            s2 = this.connector.getServer();
        }
        if (this.threadPool != null) {
            try {
                if (s2 == null) {
                    s2 = new Server(this.threadPool);
                } else {
                    s2.addBean(this.threadPool);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (s2 == null) {
            s2 = new Server();
        }
        ErrorHandler eh = new ErrorHandler(){

            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                String msg = (String)request.getAttribute("javax.servlet.error.message");
                if (StringUtils.isEmpty(msg) || msg.contains("org.apache.cxf.interceptor.Fault")) {
                    msg = HttpStatus.getMessage(response.getStatus());
                    request.setAttribute("javax.servlet.error.message", (Object)msg);
                }
                if (response instanceof Response) {
                    ((Response)response).setStatusWithReason(response.getStatus(), msg);
                }
                super.handle(target, baseRequest, request, response);
            }

            protected void writeErrorPage(HttpServletRequest request, Writer writer, int code, String message, boolean showStacks) throws IOException {
                super.writeErrorPage(request, writer, code, message, false);
            }
        };
        s2.addBean(eh);
        return s2;
    }

    @Override
    public synchronized void addServant(URL url2, JettyHTTPHandler handler) {
        if (this.shouldCheckUrl(handler.getBus())) {
            this.checkRegistedContext(url2);
        }
        this.initializeContexts();
        SecurityHandler securityHandler = null;
        if (this.server == null) {
            DefaultHandler defaultHandler = null;
            this.server = this.createServer();
            this.addServerMBean();
            if (this.connector == null) {
                this.connector = this.createConnector(this.getHost(), this.getPort());
                if (LOG.isLoggable(Level.FINER)) {
                    JettyHTTPServerEngine.logConnector((ServerConnector)this.connector);
                }
            }
            this.server.addConnector(this.connector);
            this.setupThreadPool();
            int numberOfHandlers = 1;
            if (this.handlers != null) {
                numberOfHandlers += this.handlers.size();
            }
            Handler existingHandler = this.server.getHandler();
            HandlerCollection handlerCollection = null;
            boolean existingHandlerCollection = existingHandler instanceof HandlerCollection;
            if (existingHandlerCollection) {
                handlerCollection = (HandlerCollection)existingHandler;
            }
            if (!(existingHandlerCollection || existingHandler == null && numberOfHandlers <= 1)) {
                handlerCollection = new HandlerCollection();
                if (existingHandler != null) {
                    handlerCollection.addHandler(existingHandler);
                }
                this.server.setHandler(handlerCollection);
            }
            if (this.handlers != null && !this.handlers.isEmpty()) {
                for (Handler h2 : this.handlers) {
                    if (h2 instanceof DefaultHandler) {
                        defaultHandler = (DefaultHandler)h2;
                        continue;
                    }
                    if (h2 instanceof SecurityHandler && ((SecurityHandler)h2).getHandler() == null) {
                        ((SecurityHandler)h2).setHandler(handler);
                        securityHandler = (SecurityHandler)h2;
                        continue;
                    }
                    handlerCollection.addHandler(h2);
                }
            }
            if (handlerCollection != null) {
                handlerCollection.addHandler(this.contexts);
                if (defaultHandler != null) {
                    handlerCollection.addHandler(defaultHandler);
                }
            } else {
                this.server.setHandler(this.contexts);
            }
            try {
                this.server.start();
            }
            catch (Exception e) {
                LOG.log(Level.SEVERE, "START_UP_SERVER_FAILED_MSG", new Object[]{e.getMessage(), this.port});
                try {
                    this.server.stop();
                    this.server.destroy();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.server = null;
                throw new Fault(new Message("START_UP_SERVER_FAILED_MSG", LOG, e.getMessage(), this.port), (Throwable)e);
            }
        }
        String contextName = HttpUriMapper.getContextName(url2.getPath());
        ContextHandler context = new ContextHandler();
        context.setContextPath(contextName);
        if (this.isSessionSupport.booleanValue()) {
            SessionHandler sh = this.configureSession();
            if (securityHandler != null) {
                sh.setHandler(securityHandler);
            } else {
                sh.setHandler(handler);
            }
            context.setHandler(sh);
        } else if (securityHandler != null) {
            context.setHandler(securityHandler);
        } else {
            context.setHandler(handler);
        }
        this.contexts.addHandler(context);
        ContextHandler.Context sc = context.getServletContext();
        handler.setServletContext((ServletContext)sc);
        String smap = this.getHandlerName(url2, context);
        handler.setName(smap);
        if (this.contexts.isStarted()) {
            try {
                context.start();
            }
            catch (Exception ex) {
                LOG.log(Level.WARNING, "ADD_HANDLER_FAILED_MSG", new Object[]{ex.getMessage()});
            }
        }
        this.registedPaths.add(url2.getPath());
        ++this.servantCount;
    }

    private SessionHandler configureSession() {
        SessionHandler sh = null;
        try {
            if (Server.getVersion().startsWith("9.2") || Server.getVersion().startsWith("9.3")) {
                if (this.sessionHandler == null) {
                    this.sessionHandler = new SessionHandler();
                }
                sh = new SessionHandler();
                Method get2 = ReflectionUtil.getDeclaredMethod(SessionHandler.class, "getSessionManager", new Class[0]);
                Method set = ReflectionUtil.getDeclaredMethod(SessionHandler.class, "setSessionManager", get2.getReturnType());
                if (this.getSessionTimeout() >= 0) {
                    Method setMaxInactiveInterval = ReflectionUtil.getDeclaredMethod(get2.getReturnType(), "setMaxInactiveInterval", Integer.TYPE);
                    ReflectionUtil.setAccessible(setMaxInactiveInterval).invoke(ReflectionUtil.setAccessible(get2).invoke((Object)this.sessionHandler, new Object[0]), 20);
                }
                ReflectionUtil.setAccessible(set).invoke((Object)sh, ReflectionUtil.setAccessible(get2).invoke((Object)this.sessionHandler, new Object[0]));
            } else {
                sh = new SessionHandler();
                if (this.getSessionTimeout() >= 0) {
                    Method setMaxInactiveInterval = ReflectionUtil.getDeclaredMethod(SessionHandler.class, "setMaxInactiveInterval", Integer.TYPE);
                    ReflectionUtil.setAccessible(setMaxInactiveInterval).invoke((Object)sh, 20);
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return sh;
    }

    private String getHandlerName(URL url2, ContextHandler context) {
        String contextPath = context.getContextPath();
        String path = url2.getPath();
        if (path.startsWith(contextPath)) {
            if ("/".equals(contextPath)) {
                return path;
            }
            return path.substring(contextPath.length());
        }
        return HttpUriMapper.getResourceBase(url2.getPath());
    }

    private void initializeContexts() {
        if (this.contexts == null) {
            this.contexts = new ContextHandlerCollection();
            if (this.server != null) {
                if (this.server.getHandler() instanceof ContextHandlerCollection) {
                    this.contexts = (ContextHandlerCollection)this.server.getHandler();
                } else {
                    this.server.setHandler(this.contexts);
                }
            }
        }
    }

    private void addServerMBean() {
        if (this.mBeanContainer == null) {
            return;
        }
        try {
            Container container = JettyHTTPServerEngine.getContainer(this.server);
            container.addEventListener(this.mBeanContainer);
            this.mBeanContainer.beanAdded(null, this.server);
        }
        catch (RuntimeException rex) {
            throw rex;
        }
        catch (Exception r) {
            throw new RuntimeException(r);
        }
    }

    private void removeServerMBean() {
        try {
            this.mBeanContainer.beanRemoved(null, this.server);
        }
        catch (RuntimeException rex) {
            throw rex;
        }
        catch (Exception r) {
            throw new RuntimeException(r);
        }
    }

    private Connector createConnector(String hosto, int porto) {
        SslContextFactory sslcf = null;
        if (this.tlsServerParameters != null) {
            sslcf = new SslContextFactory(){

                @Override
                protected void doStart() throws Exception {
                    this.setSslContext(JettyHTTPServerEngine.this.createSSLContext(this));
                    super.doStart();
                    this.checkKeyStore();
                }

                public void checkKeyStore() {
                }
            };
            this.decorateCXFJettySslSocketConnector(sslcf);
        }
        ServerConnector result = null;
        int major = 9;
        int minor = 0;
        try {
            String[] version = Server.getVersion().split("\\.");
            major = Integer.parseInt(version[0]);
            minor = Integer.parseInt(version[1]);
        }
        catch (Exception version) {
            // empty catch block
        }
        result = (ServerConnector)this.createConnectorJetty(sslcf, hosto, porto, major, minor);
        try {
            result.setPort(porto);
            if (hosto != null) {
                result.setHost(hosto);
            }
            result.setReuseAddress(this.isReuseAddress());
        }
        catch (RuntimeException rex) {
            throw rex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    AbstractConnector createConnectorJetty(SslContextFactory sslcf, String hosto, int porto, int major, int minor) {
        ServerConnector result = null;
        try {
            HttpConfiguration httpConfig = new HttpConfiguration();
            httpConfig.setSendServerVersion(this.getSendServerVersion());
            HttpConnectionFactory httpFactory = new HttpConnectionFactory(httpConfig);
            ArrayList<ConnectionFactory> connectionFactories = new ArrayList<ConnectionFactory>();
            result = new ServerConnector(this.server);
            if (this.tlsServerParameters != null) {
                httpConfig.addCustomizer(new SecureRequestCustomizer());
                SslConnectionFactory scf = new SslConnectionFactory(sslcf, "HTTP/1.1");
                connectionFactories.add(scf);
                String proto = major > 9 || major == 9 && minor >= 3 ? "SSL" : "SSL-HTTP/1.1";
                result.setDefaultProtocol(proto);
            }
            connectionFactories.add(httpFactory);
            result.setConnectionFactories(connectionFactories);
            if (this.getMaxIdleTime() > 0) {
                result.setIdleTimeout(this.getMaxIdleTime());
            }
        }
        catch (RuntimeException rex) {
            throw rex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    protected SSLContext createSSLContext(SslContextFactory scf) throws Exception {
        String proto = this.tlsServerParameters.getSecureSocketProtocol() == null ? "TLS" : this.tlsServerParameters.getSecureSocketProtocol();
        boolean allowSSLv3 = "SSLv3".equals(proto);
        if (allowSSLv3 || !this.tlsServerParameters.getIncludeProtocols().isEmpty()) {
            ArrayList excludedProtocols = new ArrayList();
            for (String excludedProtocol : scf.getExcludeProtocols()) {
                if (this.tlsServerParameters.getIncludeProtocols().contains(excludedProtocol) || allowSSLv3 && ("SSLv3".equals(excludedProtocol) || "SSLv2Hello".equals(excludedProtocol))) continue;
                excludedProtocols.add(excludedProtocol);
            }
            String[] revisedProtocols = new String[excludedProtocols.size()];
            excludedProtocols.toArray(revisedProtocols);
            scf.setExcludeProtocols(revisedProtocols);
        }
        for (String p : this.tlsServerParameters.getExcludeProtocols()) {
            scf.addExcludeProtocols(p);
        }
        SSLContext context = this.tlsServerParameters.getJsseProvider() == null ? SSLContext.getInstance(proto) : SSLContext.getInstance(proto, this.tlsServerParameters.getJsseProvider());
        KeyManager[] keyManagers = this.tlsServerParameters.getKeyManagers();
        KeyManager[] configuredKeyManagers = SSLUtils.configureKeyManagersWithCertAlias(this.tlsServerParameters, keyManagers);
        context.init(configuredKeyManagers, this.tlsServerParameters.getTrustManagers(), this.tlsServerParameters.getSecureRandom());
        String[] supportedCipherSuites = org.apache.cxf.configuration.jsse.SSLUtils.getServerSupportedCipherSuites(context);
        if (this.tlsServerParameters.getCipherSuitesFilter() != null && this.tlsServerParameters.getCipherSuitesFilter().isSetExclude()) {
            String[] excludedCipherSuites = org.apache.cxf.configuration.jsse.SSLUtils.getFilteredCiphersuites(this.tlsServerParameters.getCipherSuitesFilter(), supportedCipherSuites, LOG, true);
            scf.setExcludeCipherSuites(excludedCipherSuites);
        }
        String[] includedCipherSuites = org.apache.cxf.configuration.jsse.SSLUtils.getCiphersuitesToInclude(this.tlsServerParameters.getCipherSuites(), this.tlsServerParameters.getCipherSuitesFilter(), context.getServerSocketFactory().getDefaultCipherSuites(), supportedCipherSuites, LOG);
        scf.setIncludeCipherSuites(includedCipherSuites);
        return context;
    }

    protected void setClientAuthentication(SslContextFactory con, ClientAuthentication clientAuth) {
        con.setWantClientAuth(true);
        if (clientAuth != null) {
            if (clientAuth.isSetWant()) {
                con.setWantClientAuth(clientAuth.isWant());
            }
            if (clientAuth.isSetRequired()) {
                con.setNeedClientAuth(clientAuth.isRequired());
            }
        }
    }

    private void decorateCXFJettySslSocketConnector(SslContextFactory con) {
        this.setClientAuthentication(con, this.tlsServerParameters.getClientAuthentication());
        con.setCertAlias(this.tlsServerParameters.getCertAlias());
        con.setEndpointIdentificationAlgorithm(null);
    }

    private static Container getContainer(Object server) {
        if (server instanceof Container) {
            return (Container)server;
        }
        try {
            return (Container)server.getClass().getMethod("getContainer", new Class[0]).invoke(server, new Object[0]);
        }
        catch (RuntimeException t2) {
            throw t2;
        }
        catch (Throwable t3) {
            throw new RuntimeException(t3);
        }
    }

    private static void logConnector(ServerConnector connector) {
        try {
            String h2 = connector.getHost();
            int port = connector.getPort();
            LOG.finer("connector.host: " + h2 == null ? "null" : "\"" + h2 + "\"");
            LOG.finer("connector.port: " + port);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    protected void setupThreadPool() {
        if (this.isSetThreadingParameters()) {
            ThreadPool pl = this.getThreadPool();
            AbstractConnector aconn = (AbstractConnector)this.connector;
            int acc = aconn.getAcceptors() * 2;
            if (this.getThreadingParameters().isSetMaxThreads() && this.getThreadingParameters().getMaxThreads() <= acc) {
                throw new Fault(new Message("NOT_ENOUGH_THREADS", LOG, this.port, acc + 1, this.getThreadingParameters().getMaxThreads(), acc));
            }
            if (!(pl instanceof QueuedThreadPool)) {
                throw new Fault(new Message("NOT_A_QUEUED_THREAD_POOL", LOG, pl.getClass()));
            }
            if (this.getThreadingParameters().isThreadNamePrefixSet()) {
                ((QueuedThreadPool)pl).setName(this.getThreadingParameters().getThreadNamePrefix());
            }
            if (this.getThreadingParameters().isSetMinThreads()) {
                ((QueuedThreadPool)pl).setMinThreads(this.getThreadingParameters().getMinThreads());
            }
            if (this.getThreadingParameters().isSetMaxThreads()) {
                ((QueuedThreadPool)pl).setMaxThreads(this.getThreadingParameters().getMaxThreads());
            }
        }
    }

    private ThreadPool getThreadPool() {
        ThreadPool pool = this.server.getThreadPool();
        if (pool == null) {
            pool = new QueuedThreadPool();
            try {
                this.server.getClass().getMethod("setThreadPool", ThreadPool.class).invoke((Object)this.server, pool);
            }
            catch (RuntimeException t2) {
                throw t2;
            }
            catch (Throwable t3) {
                throw new RuntimeException(t3);
            }
        }
        return pool;
    }

    @Override
    public synchronized void removeServant(URL url2) {
        String contextName = HttpUriMapper.getContextName(url2.getPath());
        String smap = HttpUriMapper.getResourceBase(url2.getPath());
        boolean found = false;
        if (this.server != null && this.server.isRunning()) {
            for (Handler handler : this.contexts.getChildHandlersByClass(ContextHandler.class)) {
                Handler jh;
                ContextHandler contextHandler = null;
                if (!(handler instanceof ContextHandler) || !((jh = (contextHandler = (ContextHandler)handler).getHandler()) instanceof JettyHTTPHandler) || !contextName.equals(contextHandler.getContextPath()) && (!StringUtils.isEmpty(contextName) || !"/".equals(contextHandler.getContextPath())) || !((JettyHTTPHandler)jh).getName().equals(smap)) continue;
                try {
                    this.contexts.removeHandler(handler);
                    handler.stop();
                    handler.destroy();
                }
                catch (Exception ex) {
                    LOG.log(Level.WARNING, "REMOVE_HANDLER_FAILED_MSG", new Object[]{ex.getMessage()});
                }
                found = true;
                break;
            }
        }
        if (!found) {
            LOG.log(Level.WARNING, "CAN_NOT_FIND_HANDLER_MSG", new Object[]{url2});
        }
        this.registedPaths.remove(url2.getPath());
        --this.servantCount;
    }

    @Override
    public synchronized Handler getServant(URL url2) {
        String contextName = HttpUriMapper.getContextName(url2.getPath());
        Handler ret = null;
        if (this.server != null) {
            for (Handler handler : this.server.getChildHandlersByClass(ContextHandler.class)) {
                ContextHandler contextHandler = null;
                if (!(handler instanceof ContextHandler) || !contextName.equals((contextHandler = (ContextHandler)handler).getContextPath())) continue;
                ret = contextHandler.getHandler();
                break;
            }
        }
        return ret;
    }

    public synchronized ContextHandler getContextHandler(URL url2) {
        String contextName = HttpUriMapper.getContextName(url2.getPath());
        ContextHandler ret = null;
        if (this.server != null) {
            for (Handler handler : this.server.getChildHandlersByClass(ContextHandler.class)) {
                ContextHandler contextHandler = null;
                if (!(handler instanceof ContextHandler) || !contextName.equals((contextHandler = (ContextHandler)handler).getContextPath())) continue;
                ret = contextHandler;
                break;
            }
        }
        return ret;
    }

    private boolean isSsl() {
        if (this.connector == null) {
            return false;
        }
        try {
            return "https".equalsIgnoreCase(this.connector.getDefaultConnectionFactory().getProtocol());
        }
        catch (Exception exception) {
            return false;
        }
    }

    protected void retrieveListenerFactory() {
        if (this.tlsServerParameters != null) {
            if (this.connector != null && !this.isSsl()) {
                LOG.warning("Connector " + this.connector + " for JettyServerEngine Port " + this.port + " does not support SSL connections.");
                return;
            }
            this.protocol = "https";
        } else {
            if (this.isSsl()) {
                throw new RuntimeException("Connector " + this.connector + " for JettyServerEngine Port " + this.port + " does not support non-SSL connections.");
            }
            this.protocol = "http";
        }
        LOG.fine("Configured port " + this.port + " for \"" + this.protocol + "\".");
    }

    @PostConstruct
    public void finalizeConfig() throws GeneralSecurityException, IOException {
        this.retrieveListenerFactory();
        this.checkConnectorPort();
        this.configFinalized = true;
    }

    private void checkConnectorPort() throws IOException {
        try {
            int cp;
            if (null != this.connector && this.port != (cp = ((ServerConnector)this.connector).getPort())) {
                throw new IOException("Error: Connector port " + cp + " does not match with the server engine port " + this.port);
            }
        }
        catch (IOException ioe) {
            throw ioe;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void stop() throws Exception {
        this.registedPaths.clear();
        if (this.server != null) {
            try {
                if (this.connector != null) {
                    this.connector.stop();
                    if (this.connector instanceof Closeable) {
                        ((Closeable)((Object)this.connector)).close();
                    } else {
                        ((ServerConnector)this.connector).close();
                    }
                }
            }
            finally {
                if (this.contexts != null && this.contexts.getHandlers() != null) {
                    for (Handler h2 : this.contexts.getHandlers()) {
                        h2.stop();
                    }
                    this.contexts.stop();
                }
                this.contexts = null;
                this.server.stop();
                if (this.mBeanContainer != null) {
                    this.removeServerMBean();
                }
                this.server.destroy();
                this.server = null;
            }
        }
    }

    public void setTlsServerParameters(TLSServerParameters params) {
        this.tlsServerParameters = params;
        if (this.configFinalized) {
            this.retrieveListenerFactory();
        }
    }

    public TLSServerParameters getTlsServerParameters() {
        return this.tlsServerParameters;
    }

    public void setThreadingParameters(ThreadingParameters params) {
        this.threadingParameters = params;
    }

    public boolean isSetThreadingParameters() {
        return this.threadingParameters != null;
    }

    public ThreadingParameters getThreadingParameters() {
        return this.threadingParameters;
    }

    public void setSendServerVersion(Boolean sendServerVersion) {
        this.sendServerVersion = sendServerVersion;
    }

    public Boolean getSendServerVersion() {
        return this.sendServerVersion;
    }

    public int getSessionTimeout() {
        return this.sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}

