/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.LogarithmicArrayByteBufferPool;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelListeners;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ProcessorUtils;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.Container;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.Graceful;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.util.thread.Scheduler;
import org.eclipse.jetty.util.thread.ThreadPoolBudget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Abstract implementation of the Connector Interface")
public abstract class AbstractConnector
extends ContainerLifeCycle
implements Connector,
Dumpable {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractConnector.class);
    private final AutoLock _lock = new AutoLock();
    private final Condition _setAccepting = this._lock.newCondition();
    private final Map<String, ConnectionFactory> _factories = new LinkedHashMap<String, ConnectionFactory>();
    private final Server _server;
    private final Executor _executor;
    private final Scheduler _scheduler;
    private final ByteBufferPool _byteBufferPool;
    private final Thread[] _acceptors;
    private final Set<EndPoint> _endpoints = Collections.newSetFromMap(new ConcurrentHashMap());
    private final Set<EndPoint> _immutableEndPoints = Collections.unmodifiableSet(this._endpoints);
    private Graceful.Shutdown _shutdown;
    private HttpChannel.Listener _httpChannelListeners = HttpChannel.NOOP_LISTENER;
    private long _idleTimeout = 30000L;
    private long _shutdownIdleTimeout = 1000L;
    private String _defaultProtocol;
    private ConnectionFactory _defaultConnectionFactory;
    private String _name;
    private int _acceptorPriorityDelta = -2;
    private boolean _accepting = true;
    private ThreadPoolBudget.Lease _lease;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AbstractConnector(Server server, Executor executor2, Scheduler scheduler, ByteBufferPool pool, int acceptors, ConnectionFactory ... factories) {
        this._server = server;
        this._executor = executor2 != null ? executor2 : this._server.getThreadPool();
        this.addBean(this._executor);
        if (executor2 == null) {
            this.unmanage(this._executor);
        }
        if (scheduler == null) {
            scheduler = this._server.getBean(Scheduler.class);
        }
        this._scheduler = scheduler != null ? scheduler : new ScheduledExecutorScheduler(String.format("Connector-Scheduler-%x", this.hashCode()), false);
        this.addBean(this._scheduler);
        ConnectionFactory[] connectionFactoryArray = server;
        synchronized (server) {
            if (pool == null) {
                pool = server.getBean(ByteBufferPool.class);
                if (pool == null) {
                    pool = new LogarithmicArrayByteBufferPool();
                    server.addBean((Object)pool, true);
                }
                this.addBean(pool, false);
            } else {
                this.addBean(pool, true);
            }
            // ** MonitorExit[var7_7] (shouldn't be in output)
            this._byteBufferPool = pool;
            this.addBean(pool.asRetainableByteBufferPool());
            this.addEventListener(new Container.Listener(){

                @Override
                public void beanAdded(Container parent, Object bean) {
                    if (bean instanceof HttpChannel.Listener) {
                        AbstractConnector.this._httpChannelListeners = new HttpChannelListeners(AbstractConnector.this.getBeans(HttpChannel.Listener.class));
                    }
                }

                @Override
                public void beanRemoved(Container parent, Object bean) {
                    if (bean instanceof HttpChannel.Listener) {
                        AbstractConnector.this._httpChannelListeners = new HttpChannelListeners(AbstractConnector.this.getBeans(HttpChannel.Listener.class));
                    }
                }
            });
            for (ConnectionFactory factory2 : factories) {
                this.addConnectionFactory(factory2);
            }
            int cores = ProcessorUtils.availableProcessors();
            if (acceptors < 0) {
                acceptors = Math.max(1, Math.min(4, cores / 8));
            }
            if (acceptors > cores) {
                LOG.warn("Acceptors should be <= availableProcessors: {} ", (Object)this);
            }
            this._acceptors = new Thread[acceptors];
            return;
        }
    }

    public HttpChannel.Listener getHttpChannelListeners() {
        return this._httpChannelListeners;
    }

    @Override
    public Server getServer() {
        return this._server;
    }

    @Override
    public Executor getExecutor() {
        return this._executor;
    }

    @Override
    public ByteBufferPool getByteBufferPool() {
        return this._byteBufferPool;
    }

    @Override
    @ManagedAttribute(value="The connection idle timeout in milliseconds")
    public long getIdleTimeout() {
        return this._idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this._idleTimeout = idleTimeout;
        if (this._idleTimeout == 0L) {
            this._shutdownIdleTimeout = 0L;
        } else if (this._idleTimeout < this._shutdownIdleTimeout) {
            this._shutdownIdleTimeout = Math.min(1000L, this._idleTimeout);
        }
    }

    public void setShutdownIdleTimeout(long idle) {
        this._shutdownIdleTimeout = idle;
    }

    public long getShutdownIdleTimeout() {
        return this._shutdownIdleTimeout;
    }

    @ManagedAttribute(value="number of acceptor threads")
    public int getAcceptors() {
        return this._acceptors.length;
    }

    @Override
    protected void doStart() throws Exception {
        String next;
        ConnectionFactory cf;
        this.getConnectionFactories().stream().filter(ConnectionFactory.Configuring.class::isInstance).map(ConnectionFactory.Configuring.class::cast).forEach(configuring -> configuring.configure(this));
        this._shutdown = new Graceful.Shutdown(this){

            @Override
            public boolean isShutdownDone() {
                if (!AbstractConnector.this._endpoints.isEmpty()) {
                    return false;
                }
                for (Thread a : AbstractConnector.this._acceptors) {
                    if (a == null) continue;
                    return false;
                }
                return true;
            }
        };
        if (this._defaultProtocol == null) {
            throw new IllegalStateException("No default protocol for " + this);
        }
        this._defaultConnectionFactory = this.getConnectionFactory(this._defaultProtocol);
        if (this._defaultConnectionFactory == null) {
            throw new IllegalStateException("No protocol factory for default protocol '" + this._defaultProtocol + "' in " + this);
        }
        SslConnectionFactory ssl = this.getConnectionFactory(SslConnectionFactory.class);
        if (ssl != null && (cf = this.getConnectionFactory(next = ssl.getNextProtocol())) == null) {
            throw new IllegalStateException("No protocol factory for SSL next protocol: '" + next + "' in " + this);
        }
        this._lease = ThreadPoolBudget.leaseFrom(this.getExecutor(), this, this._acceptors.length);
        super.doStart();
        for (int i = 0; i < this._acceptors.length; ++i) {
            Acceptor a = new Acceptor(i);
            this.addBean(a);
            this.getExecutor().execute(a);
        }
        LOG.info("Started {}", (Object)this);
    }

    protected void interruptAcceptors() {
        try (AutoLock lock = this._lock.lock();){
            for (Thread thread2 : this._acceptors) {
                if (thread2 == null) continue;
                thread2.interrupt();
            }
        }
    }

    @Override
    public CompletableFuture<Void> shutdown() {
        Graceful.Shutdown shutdown = this._shutdown;
        if (shutdown == null) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<Void> done = shutdown.shutdown();
        this.interruptAcceptors();
        for (EndPoint ep : this._endpoints) {
            ep.setIdleTimeout(this.getShutdownIdleTimeout());
        }
        return done;
    }

    @Override
    public boolean isShutdown() {
        Graceful.Shutdown shutdown = this._shutdown;
        return shutdown == null || shutdown.isShutdown();
    }

    @Override
    protected void doStop() throws Exception {
        if (this._lease != null) {
            this._lease.close();
        }
        this.interruptAcceptors();
        super.doStop();
        for (Acceptor a : this.getBeans(Acceptor.class)) {
            this.removeBean(a);
        }
        this._shutdown = null;
        LOG.info("Stopped {}", (Object)this);
    }

    public void join() throws InterruptedException {
        this.join(0L);
    }

    public void join(long timeout2) throws InterruptedException {
        try (AutoLock lock = this._lock.lock();){
            for (Thread thread2 : this._acceptors) {
                if (thread2 == null) continue;
                thread2.join(timeout2);
            }
        }
    }

    protected abstract void accept(int var1) throws IOException, InterruptedException;

    public boolean isAccepting() {
        try (AutoLock lock = this._lock.lock();){
            boolean bl = this._accepting;
            return bl;
        }
    }

    public void setAccepting(boolean accepting) {
        try (AutoLock l = this._lock.lock();){
            this._accepting = accepting;
            this._setAccepting.signalAll();
        }
    }

    @Override
    public ConnectionFactory getConnectionFactory(String protocol) {
        try (AutoLock lock = this._lock.lock();){
            ConnectionFactory connectionFactory = this._factories.get(StringUtil.asciiToLowerCase(protocol));
            return connectionFactory;
        }
    }

    @Override
    public <T> T getConnectionFactory(Class<T> factoryType) {
        try (AutoLock lock = this._lock.lock();){
            for (ConnectionFactory f : this._factories.values()) {
                if (!factoryType.isAssignableFrom(f.getClass())) continue;
                ConnectionFactory connectionFactory = f;
                return (T)connectionFactory;
            }
            Iterator<ConnectionFactory> iterator2 = null;
            return (T)iterator2;
        }
    }

    public void addConnectionFactory(ConnectionFactory factory2) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        HashSet<ConnectionFactory> toRemove = new HashSet<ConnectionFactory>();
        for (String key : factory2.getProtocols()) {
            ConnectionFactory old = this._factories.remove(key = StringUtil.asciiToLowerCase(key));
            if (old != null) {
                if (old.getProtocol().equals(this._defaultProtocol)) {
                    this._defaultProtocol = null;
                }
                toRemove.add(old);
            }
            this._factories.put(key, factory2);
        }
        for (ConnectionFactory f : this._factories.values()) {
            toRemove.remove(f);
        }
        for (ConnectionFactory old : toRemove) {
            this.removeBean(old);
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug("{} removed {}", (Object)this, (Object)old);
        }
        this.addBean(factory2);
        if (this._defaultProtocol == null) {
            this._defaultProtocol = factory2.getProtocol();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} added {}", (Object)this, (Object)factory2);
        }
    }

    public void addFirstConnectionFactory(ConnectionFactory factory2) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        ArrayList<ConnectionFactory> existings = new ArrayList<ConnectionFactory>(this._factories.values());
        this.clearConnectionFactories();
        this.addConnectionFactory(factory2);
        for (ConnectionFactory existing : existings) {
            this.addConnectionFactory(existing);
        }
    }

    public void addIfAbsentConnectionFactory(ConnectionFactory factory2) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        String key = StringUtil.asciiToLowerCase(factory2.getProtocol());
        if (!this._factories.containsKey(key)) {
            this.addConnectionFactory(factory2);
        }
    }

    public ConnectionFactory removeConnectionFactory(String protocol) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        ConnectionFactory factory2 = this._factories.remove(StringUtil.asciiToLowerCase(protocol));
        if (this._factories.isEmpty()) {
            this._defaultProtocol = null;
        }
        this.removeBean(factory2);
        return factory2;
    }

    @Override
    public Collection<ConnectionFactory> getConnectionFactories() {
        return this._factories.values();
    }

    public void setConnectionFactories(Collection<ConnectionFactory> factories) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        ArrayList<ConnectionFactory> existing = new ArrayList<ConnectionFactory>(this._factories.values());
        for (ConnectionFactory factory2 : existing) {
            this.removeConnectionFactory(factory2.getProtocol());
        }
        for (ConnectionFactory factory2 : factories) {
            if (factory2 == null) continue;
            this.addConnectionFactory(factory2);
        }
    }

    public void clearConnectionFactories() {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        this._factories.clear();
        this._defaultProtocol = null;
    }

    @ManagedAttribute(value="The priority delta to apply to acceptor threads")
    public int getAcceptorPriorityDelta() {
        return this._acceptorPriorityDelta;
    }

    public void setAcceptorPriorityDelta(int acceptorPriorityDelta) {
        int old = this._acceptorPriorityDelta;
        this._acceptorPriorityDelta = acceptorPriorityDelta;
        if (old != acceptorPriorityDelta && this.isStarted()) {
            for (Thread thread2 : this._acceptors) {
                thread2.setPriority(Math.max(1, Math.min(10, thread2.getPriority() - old + acceptorPriorityDelta)));
            }
        }
    }

    @Override
    @ManagedAttribute(value="Protocols supported by this connector")
    public List<String> getProtocols() {
        return new ArrayList<String>(this._factories.keySet());
    }

    @ManagedAttribute(value="This connector's default protocol")
    public String getDefaultProtocol() {
        return this._defaultProtocol;
    }

    public void setDefaultProtocol(String defaultProtocol) {
        this._defaultProtocol = StringUtil.asciiToLowerCase(defaultProtocol);
        if (this.isRunning()) {
            this._defaultConnectionFactory = this.getConnectionFactory(this._defaultProtocol);
        }
    }

    @Override
    public ConnectionFactory getDefaultConnectionFactory() {
        if (this.isStarted()) {
            return this._defaultConnectionFactory;
        }
        return this.getConnectionFactory(this._defaultProtocol);
    }

    protected boolean handleAcceptFailure(Throwable ex) {
        if (this.isRunning()) {
            if (ex instanceof InterruptedException) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Accept Interrupted", ex);
                }
                return true;
            }
            if (ex instanceof ClosedByInterruptException) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Accept Closed by Interrupt", ex);
                }
                return false;
            }
            LOG.warn("Accept Failure", ex);
            try {
                Thread.sleep(1000L);
                return true;
            }
            catch (Throwable x) {
                LOG.trace("IGNORED", x);
                return false;
            }
        }
        LOG.trace("IGNORED", ex);
        return false;
    }

    @Override
    public Collection<EndPoint> getConnectedEndPoints() {
        return this._immutableEndPoints;
    }

    protected void onEndPointOpened(EndPoint endp) {
        this._endpoints.add(endp);
    }

    protected void onEndPointClosed(EndPoint endp) {
        this._endpoints.remove(endp);
        Graceful.Shutdown shutdown = this._shutdown;
        if (shutdown != null) {
            shutdown.check();
        }
    }

    @Override
    public Scheduler getScheduler() {
        return this._scheduler;
    }

    @Override
    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    @Override
    public String toString() {
        return String.format("%s@%x{%s, %s}", this._name == null ? this.getClass().getSimpleName() : this._name, this.hashCode(), this.getDefaultProtocol(), this.getProtocols().stream().collect(Collectors.joining(", ", "(", ")")));
    }

    private class Acceptor
    implements Runnable {
        private final int _id;
        private String _name;

        private Acceptor(int id) {
            this._id = id;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Thread thread2 = Thread.currentThread();
            String name = thread2.getName();
            this._name = String.format("%s-acceptor-%d@%x-%s", name, this._id, this.hashCode(), AbstractConnector.this.toString());
            thread2.setName(this._name);
            int priority = thread2.getPriority();
            if (AbstractConnector.this._acceptorPriorityDelta != 0) {
                thread2.setPriority(Math.max(1, Math.min(10, priority + AbstractConnector.this._acceptorPriorityDelta)));
            }
            try (AutoLock l = AbstractConnector.this._lock.lock();){
                AbstractConnector.this._acceptors[this._id] = thread2;
            }
            try {
                while (AbstractConnector.this.isRunning()) {
                    block35: {
                        if (AbstractConnector.this._shutdown.isShutdown()) return;
                        try {
                            l = AbstractConnector.this._lock.lock();
                            try {
                                if (!AbstractConnector.this._accepting && AbstractConnector.this.isRunning()) {
                                    AbstractConnector.this._setAccepting.await();
                                    continue;
                                }
                                break block35;
                            }
                            finally {
                                if (l == null) continue;
                                l.close();
                            }
                        }
                        catch (InterruptedException e) {}
                        continue;
                    }
                    try {
                        AbstractConnector.this.accept(this._id);
                    }
                    catch (Throwable x) {
                        if (!AbstractConnector.this.handleAcceptFailure(x)) return;
                    }
                }
                return;
            }
            finally {
                thread2.setName(name);
                if (AbstractConnector.this._acceptorPriorityDelta != 0) {
                    thread2.setPriority(priority);
                }
                l = AbstractConnector.this._lock.lock();
                try {
                    AbstractConnector.this._acceptors[this._id] = null;
                }
                finally {
                    if (l != null) {
                        l.close();
                    }
                }
                Graceful.Shutdown shutdown = AbstractConnector.this._shutdown;
                if (shutdown != null) {
                    shutdown.check();
                }
            }
        }

        public String toString() {
            String name = this._name;
            if (name == null) {
                return String.format("acceptor-%d@%x", this._id, this.hashCode());
            }
            return name;
        }
    }
}

