/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.DateGenerator;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.server.AsyncContextEvent;
import org.eclipse.jetty.server.ClassLoaderDump;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.ShutdownMonitor;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.Uptime;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AttributeContainerMap;
import org.eclipse.jetty.util.component.Graceful;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ShutdownThread;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Jetty HTTP Servlet server")
public class Server
extends HandlerWrapper
implements Attributes {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final AttributeContainerMap _attributes = new AttributeContainerMap();
    private final ThreadPool _threadPool;
    private final List<Connector> _connectors = new CopyOnWriteArrayList<Connector>();
    private SessionIdManager _sessionIdManager;
    private boolean _stopAtShutdown;
    private boolean _dumpAfterStart;
    private boolean _dumpBeforeStop;
    private ErrorHandler _errorHandler;
    private RequestLog _requestLog;
    private boolean _dryRun;
    private final AutoLock _dateLock = new AutoLock();
    private volatile DateField _dateField;
    private long _stopTimeout;

    public Server() {
        this((ThreadPool)null);
    }

    public Server(@Name(value="port") int port) {
        this((ThreadPool)null);
        ServerConnector connector = new ServerConnector(this);
        connector.setPort(port);
        this.setConnectors(new Connector[]{connector});
    }

    public Server(@Name(value="address") InetSocketAddress addr) {
        this((ThreadPool)null);
        ServerConnector connector = new ServerConnector(this);
        connector.setHost(addr.getHostName());
        connector.setPort(addr.getPort());
        this.setConnectors(new Connector[]{connector});
    }

    public Server(@Name(value="threadpool") ThreadPool pool) {
        this._threadPool = pool != null ? pool : new QueuedThreadPool();
        this.addBean(this._threadPool);
        this.addBean(this._attributes);
        this.setServer(this);
    }

    public boolean isDryRun() {
        return this._dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this._dryRun = dryRun;
    }

    public RequestLog getRequestLog() {
        return this._requestLog;
    }

    public ErrorHandler getErrorHandler() {
        return this._errorHandler;
    }

    public void setRequestLog(RequestLog requestLog) {
        this.updateBean(this._requestLog, requestLog);
        this._requestLog = requestLog;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler instanceof ErrorHandler.ErrorPageMapper) {
            throw new IllegalArgumentException("ErrorPageMapper is applicable only to ContextHandler");
        }
        this.updateBean(this._errorHandler, errorHandler);
        this._errorHandler = errorHandler;
        if (errorHandler != null) {
            errorHandler.setServer(this);
        }
    }

    @ManagedAttribute(value="version of this server")
    public static String getVersion() {
        return Jetty.VERSION;
    }

    public void setStopTimeout(long stopTimeout) {
        this._stopTimeout = stopTimeout;
    }

    public long getStopTimeout() {
        return this._stopTimeout;
    }

    public boolean getStopAtShutdown() {
        return this._stopAtShutdown;
    }

    public void setStopAtShutdown(boolean stop) {
        if (stop) {
            if (!this._stopAtShutdown && this.isStarted()) {
                ShutdownThread.register(this);
            }
        } else {
            ShutdownThread.deregister(this);
        }
        this._stopAtShutdown = stop;
    }

    @ManagedAttribute(value="connectors for this server", readonly=true)
    public Connector[] getConnectors() {
        ArrayList<Connector> connectors = new ArrayList<Connector>(this._connectors);
        return connectors.toArray(new Connector[connectors.size()]);
    }

    public void addConnector(Connector connector) {
        if (connector.getServer() != this) {
            throw new IllegalArgumentException("Connector " + connector + " cannot be shared among server " + connector.getServer() + " and server " + this);
        }
        this._connectors.add(connector);
        this.addBean(connector);
    }

    public void removeConnector(Connector connector) {
        if (this._connectors.remove(connector)) {
            this.removeBean(connector);
        }
    }

    public void setConnectors(Connector[] connectors) {
        if (connectors != null) {
            for (Connector connector : connectors) {
                if (connector.getServer() == this) continue;
                throw new IllegalArgumentException("Connector " + connector + " cannot be shared among server " + connector.getServer() + " and server " + this);
            }
        }
        Object[] oldConnectors = this.getConnectors();
        this.updateBeans(oldConnectors, connectors);
        this._connectors.removeAll(Arrays.asList(oldConnectors));
        if (connectors != null) {
            this._connectors.addAll(Arrays.asList(connectors));
        }
    }

    public void addBeanToAllConnectors(Object bean) {
        for (Connector connector : this.getConnectors()) {
            connector.addBean(bean);
        }
    }

    @ManagedAttribute(value="the server thread pool")
    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    @ManagedAttribute(value="dump state to stderr after start")
    public boolean isDumpAfterStart() {
        return this._dumpAfterStart;
    }

    public void setDumpAfterStart(boolean dumpAfterStart) {
        this._dumpAfterStart = dumpAfterStart;
    }

    @ManagedAttribute(value="dump state to stderr before stop")
    public boolean isDumpBeforeStop() {
        return this._dumpBeforeStop;
    }

    public void setDumpBeforeStop(boolean dumpBeforeStop) {
        this._dumpBeforeStop = dumpBeforeStop;
    }

    public HttpField getDateField() {
        long now = System.currentTimeMillis();
        long seconds = now / 1000L;
        DateField df = this._dateField;
        if (df == null || df._seconds != seconds) {
            try (AutoLock lock = this._dateLock.lock();){
                df = this._dateField;
                if (df == null || df._seconds != seconds) {
                    PreEncodedHttpField field2 = new PreEncodedHttpField(HttpHeader.DATE, DateGenerator.formatDate(now));
                    this._dateField = new DateField(seconds, field2);
                    PreEncodedHttpField preEncodedHttpField = field2;
                    return preEncodedHttpField;
                }
            }
        }
        return df._dateField;
    }

    @Override
    protected void doStart() throws Exception {
        try {
            if (this._errorHandler == null) {
                this._errorHandler = this.getBean(ErrorHandler.class);
            }
            if (this._errorHandler == null) {
                this.setErrorHandler(new ErrorHandler());
            }
            if (this._errorHandler instanceof ErrorHandler.ErrorPageMapper) {
                LOG.warn("ErrorPageMapper not supported for Server level Error Handling");
            }
            this._errorHandler.setServer(this);
            if (this.getStopAtShutdown()) {
                ShutdownThread.register(this);
            }
            ShutdownMonitor.register(this);
            ShutdownMonitor.getInstance().start();
            String gitHash = Jetty.GIT_HASH;
            String timestamp = Jetty.BUILD_TIMESTAMP;
            LOG.info("jetty-{}; built: {}; git: {}; jvm {}", Server.getVersion(), timestamp, gitHash, System.getProperty("java.runtime.version", System.getProperty("java.version")));
            if (!Jetty.STABLE) {
                LOG.warn("THIS IS NOT A STABLE RELEASE! DO NOT USE IN PRODUCTION!");
                LOG.warn("Download a stable release from https://download.eclipse.org/jetty/");
            }
            HttpGenerator.setJettyVersion(HttpConfiguration.SERVER_VERSION);
            MultiException mex = new MultiException();
            if (!this._dryRun) {
                this._connectors.stream().filter(NetworkConnector.class::isInstance).map(NetworkConnector.class::cast).forEach(connector -> {
                    try {
                        connector.open();
                    }
                    catch (Throwable th) {
                        mex.add(th);
                    }
                });
                mex.ifExceptionThrow();
            }
            super.doStart();
            if (this._dryRun) {
                LOG.info(String.format("Started(dry run) %s @%dms", this, Uptime.getUptime()));
                throw new AbstractLifeCycle.StopException(this);
            }
            for (Connector connector2 : this._connectors) {
                try {
                    connector2.start();
                }
                catch (Throwable e) {
                    mex.add(e);
                    this._connectors.stream().filter(LifeCycle::isRunning).map(Object.class::cast).forEach(LifeCycle::stop);
                }
            }
            mex.ifExceptionThrow();
            LOG.info(String.format("Started %s @%dms", this, Uptime.getUptime()));
        }
        catch (Throwable th) {
            this._connectors.stream().filter(NetworkConnector.class::isInstance).map(NetworkConnector.class::cast).forEach(nc -> {
                block2: {
                    try {
                        nc.close();
                    }
                    catch (Throwable th2) {
                        if (th == th2) break block2;
                        th.addSuppressed(th2);
                    }
                }
            });
            throw th;
        }
        finally {
            if (!(!this.isDumpAfterStart() || this._dryRun && this.isDumpBeforeStop())) {
                this.dumpStdErr();
            }
        }
    }

    @Override
    protected void start(LifeCycle l) throws Exception {
        if (!(l instanceof Connector)) {
            super.start(l);
        }
    }

    @Override
    protected void doStop() throws Exception {
        if (this.isDumpBeforeStop()) {
            this.dumpStdErr();
        }
        LOG.info(String.format("Stopped %s", this));
        if (LOG.isDebugEnabled()) {
            LOG.debug("doStop {}", (Object)this);
        }
        MultiException mex = new MultiException();
        if (this.getStopTimeout() > 0L) {
            long end = NanoTime.now() + TimeUnit.MILLISECONDS.toNanos(this.getStopTimeout());
            try {
                Graceful.shutdown(this).get(this.getStopTimeout(), TimeUnit.MILLISECONDS);
            }
            catch (Throwable e) {
                mex.add(e);
            }
            QueuedThreadPool qtp = this.getBean(QueuedThreadPool.class);
            if (qtp != null) {
                qtp.setStopTimeout(Math.max(1000L, NanoTime.millisUntil(end)));
            }
        }
        for (Connector connector : this._connectors) {
            try {
                connector.stop();
            }
            catch (Throwable e) {
                mex.add(e);
            }
        }
        try {
            super.doStop();
        }
        catch (Throwable e) {
            mex.add(e);
        }
        if (this.getStopAtShutdown()) {
            ShutdownThread.deregister(this);
        }
        ShutdownMonitor.deregister(this);
        mex.ifExceptionThrow();
    }

    public void handle(HttpChannel channel) throws IOException, ServletException {
        String target = channel.getRequest().getPathInfo();
        Request request = channel.getRequest();
        Response response = channel.getResponse();
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} {} {} ?{} on {}", new Object[]{request.getDispatcherType(), request.getMethod(), target, request.getQueryString(), channel});
        }
        if (HttpMethod.OPTIONS.is(request.getMethod()) || "*".equals(target)) {
            if (!HttpMethod.OPTIONS.is(request.getMethod())) {
                request.setHandled(true);
                response.sendError(400);
            } else {
                this.handleOptions(request, response);
                if (!request.isHandled()) {
                    this.handle(target, request, request, response);
                }
            }
        } else {
            this.handle(target, request, request, response);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("handled={} async={} committed={} on {}", request.isHandled(), request.isAsyncStarted(), response.isCommitted(), channel);
        }
    }

    protected void handleOptions(Request request, Response response) throws IOException {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleAsync(HttpChannel channel) throws IOException, ServletException {
        HttpChannelState state = channel.getRequest().getHttpChannelState();
        AsyncContextEvent event = state.getAsyncContextEvent();
        Request baseRequest = channel.getRequest();
        HttpURI baseUri = event.getBaseURI();
        String encodedPathQuery = event.getDispatchPath();
        if (encodedPathQuery == null && baseUri == null) {
            this.handleAsync(channel, event, baseRequest);
            return;
        }
        HttpURI oldUri = baseRequest.getHttpURI();
        MultiMap<String> oldQueryParams = baseRequest.getQueryParameters();
        try {
            if (encodedPathQuery == null) {
                baseRequest.setHttpURI(baseUri);
            } else {
                HttpURI.Mutable builder;
                ServletContext servletContext = event.getServletContext();
                if (servletContext != null) {
                    String encodedContextPath;
                    String string = encodedContextPath = servletContext instanceof ContextHandler.Context ? ((ContextHandler.Context)servletContext).getContextHandler().getContextPathEncoded() : URIUtil.encodePath(servletContext.getContextPath());
                    if (!StringUtil.isEmpty(encodedContextPath) && (encodedPathQuery = URIUtil.canonicalPath(URIUtil.addEncodedPaths(encodedContextPath, encodedPathQuery))) == null) {
                        throw new BadMessageException(500, "Bad dispatch path");
                    }
                }
                if (baseUri == null) {
                    baseUri = oldUri;
                }
                if (StringUtil.isEmpty((builder = HttpURI.build(baseUri, encodedPathQuery)).getParam())) {
                    builder.param(baseUri.getParam());
                }
                if (StringUtil.isEmpty(builder.getQuery())) {
                    builder.query(baseUri.getQuery());
                }
                baseRequest.setHttpURI(builder);
                if (baseUri.getQuery() != null && baseRequest.getQueryString() != null) {
                    baseRequest.mergeQueryParameters(oldUri.getQuery(), baseRequest.getQueryString());
                }
            }
            baseRequest.setContext(null, baseRequest.getHttpURI().getDecodedPath());
            this.handleAsync(channel, event, baseRequest);
        }
        finally {
            baseRequest.setHttpURI(oldUri);
            baseRequest.setQueryParameters(oldQueryParams);
            baseRequest.resetParameters();
        }
    }

    private void handleAsync(HttpChannel channel, AsyncContextEvent event, Request baseRequest) throws IOException, ServletException {
        String target = baseRequest.getPathInfo();
        HttpServletRequest request = Request.unwrap(event.getSuppliedRequest());
        HttpServletResponse response = Response.unwrap(event.getSuppliedResponse());
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} {} {} on {}", new Object[]{request.getDispatcherType(), request.getMethod(), target, channel});
        }
        this.handle(target, baseRequest, request, response);
        if (LOG.isDebugEnabled()) {
            LOG.debug("handledAsync={} async={} committed={} on {}", channel.getRequest().isHandled(), request.isAsyncStarted(), response.isCommitted(), channel);
        }
    }

    public void join() throws InterruptedException {
        this.getThreadPool().join();
    }

    public SessionIdManager getSessionIdManager() {
        return this._sessionIdManager;
    }

    public void setSessionIdManager(SessionIdManager sessionIdManager) {
        this.updateBean(this._sessionIdManager, sessionIdManager);
        this._sessionIdManager = sessionIdManager;
    }

    @Override
    public void clearAttributes() {
        this._attributes.clearAttributes();
    }

    @Override
    public Object getAttribute(String name) {
        return this._attributes.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return this._attributes.getAttributeNames();
    }

    @Override
    public Set<String> getAttributeNameSet() {
        return this._attributes.getAttributeNameSet();
    }

    @Override
    public void removeAttribute(String name) {
        this._attributes.removeAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object attribute) {
        this._attributes.setAttribute(name, attribute);
    }

    public URI getURI() {
        NetworkConnector connector = null;
        for (Connector c : this._connectors) {
            if (!(c instanceof NetworkConnector)) continue;
            connector = (NetworkConnector)c;
            break;
        }
        if (connector == null) {
            return null;
        }
        ContextHandler context = this.getChildHandlerByClass(ContextHandler.class);
        try {
            String path;
            String protocol = connector.getDefaultConnectionFactory().getProtocol();
            String scheme2 = "http";
            if (protocol.startsWith("SSL-") || protocol.equals("SSL")) {
                scheme2 = "https";
            }
            String host2 = connector.getHost();
            if (context != null && context.getVirtualHosts() != null && context.getVirtualHosts().length > 0) {
                host2 = context.getVirtualHosts()[0];
            }
            if (host2 == null) {
                host2 = InetAddress.getLocalHost().getHostAddress();
            }
            String string = path = context == null ? null : context.getContextPath();
            if (path == null) {
                path = "/";
            }
            return new URI(scheme2, null, host2, connector.getLocalPort(), path, null, null);
        }
        catch (Exception e) {
            LOG.warn("Unable to build server URI", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("%s[%s,sto=%d]", super.toString(), Server.getVersion(), this.getStopTimeout());
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, new ClassLoaderDump(this.getClass().getClassLoader()));
    }

    public static void main(String ... args2) throws Exception {
        System.err.println(Server.getVersion());
    }

    private static class DateField {
        final long _seconds;
        final HttpField _dateField;

        public DateField(long seconds, HttpField dateField) {
            this._seconds = seconds;
            this._dateField = dateField;
        }
    }
}

