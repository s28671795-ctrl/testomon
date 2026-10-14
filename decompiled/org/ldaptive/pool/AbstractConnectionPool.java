/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionValidator;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapUtils;
import org.ldaptive.SearchConnectionValidator;
import org.ldaptive.pool.ActivationException;
import org.ldaptive.pool.ConnectionActivator;
import org.ldaptive.pool.ConnectionPassivator;
import org.ldaptive.pool.ConnectionPool;
import org.ldaptive.pool.IdlePruneStrategy;
import org.ldaptive.pool.PoolException;
import org.ldaptive.pool.PooledConnectionProxy;
import org.ldaptive.pool.PooledConnectionStatistics;
import org.ldaptive.pool.PruneStrategy;
import org.ldaptive.pool.Queue;
import org.ldaptive.pool.QueueType;
import org.ldaptive.pool.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConnectionPool
implements ConnectionPool {
    public static final int DEFAULT_MIN_POOL_SIZE = 3;
    public static final int DEFAULT_MAX_POOL_SIZE = 10;
    private static final AtomicInteger POOL_ID = new AtomicInteger();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final ReentrantLock poolLock = new ReentrantLock();
    protected final Condition poolNotEmpty = this.poolLock.newCondition();
    protected final ReentrantLock checkOutLock = new ReentrantLock();
    protected Queue<PooledConnectionProxy> available;
    protected Queue<PooledConnectionProxy> active;
    private String name = "ldaptive-pool-" + POOL_ID.incrementAndGet();
    private int minPoolSize = 3;
    private int maxPoolSize = 10;
    private boolean validateOnCheckIn;
    private boolean validateOnCheckOut;
    private boolean validatePeriodically;
    private ConnectionActivator activator = connection -> true;
    private ConnectionPassivator passivator = connection -> true;
    private ConnectionValidator validator = new SearchConnectionValidator();
    private PruneStrategy pruneStrategy = new IdlePruneStrategy();
    private DefaultConnectionFactory connectionFactory;
    private boolean connectOnCreate = true;
    private QueueType queueType = QueueType.LIFO;
    private ScheduledExecutorService poolExecutor;
    private boolean initialized;
    private boolean failFastInitialize = true;

    public String getName() {
        return this.name;
    }

    public void setName(String s2) {
        this.logger.trace("setting name: {}", (Object)s2);
        this.name = s2;
    }

    public int getMinPoolSize() {
        return this.minPoolSize;
    }

    public void setMinPoolSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Minimum pool size must be greater than or equal to 0 for pool " + this.getName());
        }
        this.logger.trace("setting minPoolSize: {}", (Object)size);
        this.minPoolSize = size;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Maximum pool size must be greater than or equal to 0 for pool " + this.getName());
        }
        this.logger.trace("setting maxPoolSize: {}", (Object)size);
        this.maxPoolSize = size;
    }

    public boolean isValidateOnCheckIn() {
        return this.validateOnCheckIn;
    }

    public void setValidateOnCheckIn(boolean b) {
        this.logger.trace("setting validateOnCheckIn: {}", (Object)b);
        this.validateOnCheckIn = b;
    }

    public boolean isValidateOnCheckOut() {
        return this.validateOnCheckOut;
    }

    public void setValidateOnCheckOut(boolean b) {
        this.logger.trace("setting validateOnCheckOut: {}", (Object)b);
        this.validateOnCheckOut = b;
    }

    public boolean isValidatePeriodically() {
        return this.validatePeriodically;
    }

    public void setValidatePeriodically(boolean b) {
        this.logger.trace("setting validatePeriodically: {}", (Object)b);
        this.validatePeriodically = b;
    }

    @Override
    public ConnectionActivator getActivator() {
        return this.activator;
    }

    @Override
    public void setActivator(ConnectionActivator a) {
        this.logger.trace("setting activator: {}", (Object)a);
        this.activator = a;
    }

    @Override
    public ConnectionPassivator getPassivator() {
        return this.passivator;
    }

    @Override
    public void setPassivator(ConnectionPassivator p) {
        this.logger.trace("setting passivator: {}", (Object)p);
        this.passivator = p;
    }

    public ConnectionValidator getValidator() {
        return this.validator;
    }

    public void setValidator(ConnectionValidator cv) {
        this.logger.trace("setting validator: {}", (Object)cv);
        this.validator = cv;
    }

    public PruneStrategy getPruneStrategy() {
        return this.pruneStrategy;
    }

    public void setPruneStrategy(PruneStrategy ps) {
        this.logger.trace("setting pruneStrategy: {}", (Object)ps);
        this.pruneStrategy = ps;
    }

    public DefaultConnectionFactory getDefaultConnectionFactory() {
        return this.connectionFactory;
    }

    public void setDefaultConnectionFactory(DefaultConnectionFactory cf) {
        this.logger.trace("setting defaultConnectionFactory: {}", (Object)cf);
        this.connectionFactory = cf;
    }

    public boolean getConnectOnCreate() {
        return this.connectOnCreate;
    }

    public void setConnectOnCreate(boolean b) {
        this.logger.trace("setting connectOnCreate: {}", (Object)b);
        this.connectOnCreate = b;
    }

    public QueueType getQueueType() {
        return this.queueType;
    }

    public void setQueueType(QueueType type) {
        this.logger.trace("setting queueType: {}", (Object)type);
        this.queueType = type;
    }

    public boolean getFailFastInitialize() {
        return this.failFastInitialize;
    }

    public void setFailFastInitialize(boolean b) {
        this.logger.trace("setting failFastInitialize: {}", (Object)b);
        this.failFastInitialize = b;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    protected void throwIfNotInitialized() {
        if (!this.initialized) {
            throw new IllegalStateException("Pool " + this.getName() + " is not initialized");
        }
    }

    @Override
    public synchronized void initialize() {
        if (this.initialized) {
            throw new IllegalStateException("Pool " + this.getName() + " has already been initialized");
        }
        this.logger.debug("Beginning pool initialization for {}", (Object)this);
        if (this.pruneStrategy == null) {
            throw new IllegalStateException("No prune strategy configured for pool " + this.getName());
        }
        if (this.activator == null) {
            throw new IllegalStateException("No activator configured for pool " + this.getName());
        }
        if (this.passivator == null) {
            throw new IllegalStateException("No passivator configured for pool " + this.getName());
        }
        this.available = new Queue(this.queueType);
        this.active = new Queue(this.queueType);
        IllegalStateException growException = null;
        try {
            this.grow(this.minPoolSize, true);
        }
        catch (IllegalStateException e) {
            growException = e;
        }
        if (this.available.isEmpty() && this.minPoolSize > 0) {
            if (this.failFastInitialize) {
                throw new IllegalStateException("Could not initialize pool size for pool " + this.getName(), growException != null ? growException.getCause() : null);
            }
            this.logger.warn("Could not initialize pool size (pool is empty) for {}", (Object)this);
        }
        this.logger.debug("Initialized available queue {} for {}", (Object)this.available, (Object)this);
        this.poolExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t2 = new Thread(r, this.getClass().getSimpleName() + "@" + this.hashCode());
            t2.setDaemon(true);
            return t2;
        });
        this.poolExecutor.scheduleAtFixedRate(() -> {
            this.logger.debug("Begin prune task for {}", (Object)this);
            try {
                this.prune();
            }
            catch (Exception e) {
                this.logger.error("Prune task failed for {}", (Object)this);
            }
            this.logger.debug("End prune task for {}", (Object)this);
        }, this.pruneStrategy.getPrunePeriod().toMillis(), this.pruneStrategy.getPrunePeriod().toMillis(), TimeUnit.MILLISECONDS);
        this.logger.debug("Prune pool task scheduled for {}", (Object)this);
        if (this.validatePeriodically) {
            this.poolExecutor.scheduleAtFixedRate(() -> {
                this.logger.debug("Begin validate task for {}", (Object)this);
                try {
                    this.validate();
                }
                catch (Exception e) {
                    this.logger.error("Validation task failed for {}", (Object)this);
                }
                this.logger.debug("End validate task for {}", (Object)this);
            }, this.validator.getValidatePeriod().toMillis(), this.validator.getValidatePeriod().toMillis(), TimeUnit.MILLISECONDS);
            this.logger.debug("Validate pool task scheduled for {}", (Object)this);
        }
        this.initialized = true;
        this.logger.info("Pool initialized for {}", (Object)this);
    }

    protected void grow(int size) {
        this.grow(size, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void grow(int size, boolean throwOnFailure) {
        this.logger.trace("waiting for pool lock to initialize pool {}", (Object)this.poolLock.getQueueLength());
        this.poolLock.lock();
        try {
            IllegalStateException lastThrown = null;
            int currentPoolSize = this.active.size() + this.available.size();
            this.logger.debug("Checking connection pool size >= {} for {}", (Object)size, (Object)this);
            for (int count = 0; currentPoolSize < size && count < size * 2; ++count) {
                try {
                    PooledConnectionProxy pc = this.createAvailableConnection(throwOnFailure);
                    if (pc != null && this.connectOnCreate && !this.passivateAndValidateConnection(pc)) {
                        this.removeAvailableConnection(pc);
                    }
                }
                catch (IllegalStateException e) {
                    lastThrown = e;
                }
                currentPoolSize = this.active.size() + this.available.size();
            }
            if (lastThrown != null && currentPoolSize < size) {
                throw lastThrown;
            }
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public synchronized void close() {
        this.throwIfNotInitialized();
        this.logger.debug("Closing {} of size {} for {}", (Object)this, (Object)(this.available.size() + this.active.size()));
        this.poolLock.lock();
        try {
            PooledConnectionProxy pc;
            while (!this.available.isEmpty()) {
                pc = this.available.remove();
                pc.getConnection().close();
                this.logger.trace("removed {} from {}", (Object)pc, (Object)this);
            }
            while (!this.active.isEmpty()) {
                pc = this.active.remove();
                pc.getConnection().close();
                this.logger.trace("removed {} from {}", (Object)pc, (Object)this);
            }
        }
        finally {
            this.poolLock.unlock();
        }
        this.poolExecutor.shutdown();
        this.logger.info("Pool {} closed", (Object)this);
        this.initialized = false;
    }

    @Override
    public abstract Connection getConnection() throws PoolException;

    public abstract void putConnection(Connection var1);

    protected PooledConnectionProxy createConnection(boolean throwOnFailure) {
        Connection c;
        block4: {
            c = this.connectionFactory.getConnection();
            if (this.connectOnCreate) {
                try {
                    c.open();
                }
                catch (Exception e) {
                    this.logger.error("Unable to open connection for {}}", (Object)this, (Object)e);
                    c.close();
                    c = null;
                    if (!throwOnFailure) break block4;
                    throw new IllegalStateException("Unable to open connection for pool " + this.getName(), e);
                }
            }
        }
        if (c != null) {
            return new DefaultPooledConnectionProxy(c);
        }
        return null;
    }

    protected PooledConnectionProxy createAvailableConnection(boolean throwOnFailure) {
        PooledConnectionProxy pc = this.createConnection(throwOnFailure);
        if (pc != null) {
            this.poolLock.lock();
            try {
                this.available.add(pc);
                pc.getPooledConnectionStatistics().addAvailableStat();
                this.logger.info("Added available connection {} for {}", (Object)pc.getConnection(), (Object)this);
            }
            finally {
                this.poolLock.unlock();
            }
        } else {
            this.logger.warn("Unable to create available connection for {}", (Object)this);
        }
        return pc;
    }

    protected PooledConnectionProxy createActiveConnection(boolean throwOnFailure) {
        PooledConnectionProxy pc = this.createConnection(throwOnFailure);
        if (pc != null) {
            this.poolLock.lock();
            try {
                this.active.add(pc);
                pc.getPooledConnectionStatistics().addActiveStat();
                this.logger.info("Added active connection {} for {}", (Object)pc.getConnection(), (Object)this);
            }
            finally {
                this.poolLock.unlock();
            }
        } else {
            this.logger.warn("Unable to create active connection for {}", (Object)this);
        }
        return pc;
    }

    protected void removeAvailableConnection(PooledConnectionProxy pc) {
        boolean destroy = false;
        this.poolLock.lock();
        try {
            if (this.available.remove(pc)) {
                destroy = true;
            } else {
                this.logger.warn("Attempt to remove unknown available connection {} from {}", (Object)pc.getConnection(), (Object)this);
            }
        }
        finally {
            this.poolLock.unlock();
        }
        if (destroy) {
            pc.getConnection().close();
            this.logger.info("Removed {} from {}", (Object)pc.getConnection(), (Object)this);
        }
    }

    protected void removeActiveConnection(PooledConnectionProxy pc) {
        boolean destroy = false;
        this.poolLock.lock();
        try {
            if (this.active.remove(pc)) {
                destroy = true;
            } else {
                this.logger.warn("Attempt to remove unknown active connection {} from {}", (Object)pc.getConnection(), (Object)this);
            }
        }
        finally {
            this.poolLock.unlock();
        }
        if (destroy) {
            pc.getConnection().close();
            this.logger.info("Removed {} from {}", (Object)pc.getConnection(), (Object)this);
        }
    }

    protected void removeAvailableAndActiveConnection(PooledConnectionProxy pc) {
        boolean destroy = false;
        this.poolLock.lock();
        try {
            if (this.available.remove(pc)) {
                destroy = true;
            } else {
                this.logger.trace("attempt to remove unknown available connection {} from {}", (Object)pc, (Object)this);
            }
            if (this.active.remove(pc)) {
                destroy = true;
            } else {
                this.logger.trace("attempt to remove unknown active connection {} from {}", (Object)pc, (Object)this);
            }
        }
        finally {
            this.poolLock.unlock();
        }
        if (destroy) {
            pc.getConnection().close();
            this.logger.info("Removed {} from {}", (Object)pc.getConnection(), (Object)this);
        }
    }

    protected void activateAndValidateConnection(PooledConnectionProxy pc) throws PoolException {
        if (!((Boolean)this.activator.apply(pc.getConnection())).booleanValue()) {
            this.logger.warn("Failed activation on {} with {} for {}", pc.getConnection(), this.activator, this);
            this.removeAvailableAndActiveConnection(pc);
            throw new ActivationException("Activation of connection failed for pool " + this.getName());
        }
        if (this.validateOnCheckOut && !((Boolean)this.validator.apply(pc.getConnection())).booleanValue()) {
            this.logger.warn("Failed check out validation on {} with {} for {}", pc.getConnection(), this.validator, this);
            this.removeAvailableAndActiveConnection(pc);
            throw new ValidationException("Validation of connection failed for pool " + this.getName());
        }
    }

    @Deprecated
    protected boolean validateAndPassivateConnection(PooledConnectionProxy pc) {
        return this.passivateAndValidateConnection(pc);
    }

    protected boolean passivateAndValidateConnection(PooledConnectionProxy pc) {
        if (!pc.getConnection().isOpen()) {
            this.logger.warn("Failed validation on {} for {}, not open", (Object)pc.getConnection(), (Object)this);
            return false;
        }
        boolean valid = false;
        if (((Boolean)this.passivator.apply(pc.getConnection())).booleanValue()) {
            if (this.validateOnCheckIn) {
                if (((Boolean)this.validator.apply(pc.getConnection())).booleanValue()) {
                    this.logger.trace("connection {} passed initialize validation", (Object)pc);
                    valid = true;
                } else {
                    this.logger.warn("Failed check in validation on {} with {} for {}", pc.getConnection(), this.validator, this);
                }
            } else {
                valid = true;
            }
        } else {
            this.logger.warn("Failed passivation on {} with {} for {}", pc.getConnection(), this.passivator, this);
        }
        return valid;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void prune() {
        this.throwIfNotInitialized();
        this.logger.trace("waiting for pool lock to prune {} for {}", (Object)this.poolLock.getQueueLength(), (Object)this);
        this.poolLock.lock();
        try {
            if (!this.available.isEmpty()) {
                int currentPoolSize = this.active.size() + this.available.size();
                if (currentPoolSize > this.minPoolSize) {
                    this.logger.debug("Pruning available pool of size {} for {}", (Object)this.available.size(), (Object)this);
                    int numConnToPrune = this.available.size();
                    Iterator<PooledConnectionProxy> connIter = this.available.iterator();
                    for (int i = 0; i < numConnToPrune && currentPoolSize > this.minPoolSize; ++i) {
                        PooledConnectionProxy pc = connIter.next();
                        if (!((Boolean)this.pruneStrategy.apply(pc)).booleanValue()) continue;
                        connIter.remove();
                        pc.getConnection().close();
                        this.logger.trace("prune removed {} from {}", (Object)pc, (Object)this);
                        --currentPoolSize;
                    }
                    if (numConnToPrune == this.available.size()) {
                        this.logger.debug("Prune strategy did not remove any connections for {}", (Object)this);
                    } else {
                        this.logger.info("Available pool size pruned to {} for {}", (Object)this.available.size(), (Object)this);
                    }
                } else {
                    this.logger.debug("Pool size is {}, no connections pruned for {}", (Object)currentPoolSize, (Object)this);
                }
            } else {
                this.logger.debug("No available connections, no connections pruned for {}", (Object)this);
            }
        }
        finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void validate() {
        this.throwIfNotInitialized();
        this.poolLock.lock();
        try {
            if (!this.available.isEmpty()) {
                this.logger.debug("Validate available pool of size {} for {}", (Object)this.available.size(), (Object)this);
                ArrayList<PooledConnectionProxy> remove = new ArrayList<PooledConnectionProxy>();
                HashMap<PooledConnectionProxy, Supplier<Boolean>> results = new HashMap<PooledConnectionProxy, Supplier<Boolean>>(this.available.size());
                for (PooledConnectionProxy pooledConnectionProxy : this.available) {
                    this.logger.trace("validating {} for {}", (Object)pooledConnectionProxy, (Object)this);
                    results.put(pooledConnectionProxy, this.validator.applyAsync(pooledConnectionProxy.getConnection()));
                }
                for (Map.Entry entry : results.entrySet()) {
                    Boolean validateResult = (Boolean)((Supplier)entry.getValue()).get();
                    if (validateResult != null && validateResult.booleanValue()) {
                        this.logger.trace("passed validation on {} with {} for {}", entry.getKey(), this.validator, this);
                        continue;
                    }
                    this.logger.warn("Failed validation on {} with {} for {}, {}", ((PooledConnectionProxy)entry.getKey()).getConnection(), this.validator, this, validateResult == null ? "validator timeout exceeded" : "validator returned false");
                    remove.add((PooledConnectionProxy)entry.getKey());
                }
                for (PooledConnectionProxy pooledConnectionProxy : remove) {
                    this.logger.trace("validate removing {} from {}", (Object)pooledConnectionProxy, (Object)this);
                    this.available.remove(pooledConnectionProxy);
                    pooledConnectionProxy.getConnection().close();
                    this.logger.trace("validate removed {} from {}", (Object)pooledConnectionProxy, (Object)this);
                }
            } else {
                this.logger.debug("No available connections, no validation performed for {}", (Object)this);
            }
            this.grow(this.minPoolSize);
            this.logger.debug("Pool size after validation is {} for {}", (Object)(this.available.size() + this.active.size()), (Object)this);
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public int availableCount() {
        if (this.available == null) {
            return 0;
        }
        return this.available.size();
    }

    @Override
    public int activeCount() {
        if (this.active == null) {
            return 0;
        }
        return this.active.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<PooledConnectionStatistics> getPooledConnectionStatistics() {
        this.throwIfNotInitialized();
        HashSet<PooledConnectionStatistics> stats = new HashSet<PooledConnectionStatistics>();
        this.poolLock.lock();
        try {
            for (PooledConnectionProxy cp : this.available) {
                stats.add(cp.getPooledConnectionStatistics());
            }
            for (PooledConnectionProxy cp : this.active) {
                stats.add(cp.getPooledConnectionStatistics());
            }
        }
        finally {
            this.poolLock.unlock();
        }
        return Collections.unmodifiableSet(stats);
    }

    protected Connection createConnectionProxy(PooledConnectionProxy pc) {
        return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, pc);
    }

    protected PooledConnectionProxy retrieveConnectionProxy(Connection proxy) {
        return (PooledConnectionProxy)Proxy.getInvocationHandler(proxy);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "name=" + this.getName() + ", " + "minPoolSize=" + this.minPoolSize + ", " + "maxPoolSize=" + this.maxPoolSize + ", " + "validateOnCheckIn=" + this.validateOnCheckIn + ", " + "validateOnCheckOut=" + this.validateOnCheckOut + ", " + "validatePeriodically=" + this.validatePeriodically + ", " + "activator=" + this.activator + ", " + "passivator=" + this.passivator + ", " + "validator=" + this.validator + ", " + "pruneStrategy=" + this.pruneStrategy + ", " + "connectOnCreate=" + this.connectOnCreate + ", " + "connectionFactory=" + this.connectionFactory + ", " + "failFastInitialize=" + this.failFastInitialize + ", " + "initialized=" + this.initialized + ", " + "availableCount=" + this.availableCount() + ", " + "activeCount=" + this.activeCount();
    }

    protected class DefaultPooledConnectionProxy
    implements PooledConnectionProxy {
        private static final int HASH_CODE_SEED = 503;
        private final Connection conn;
        private final long createdTime = System.currentTimeMillis();
        private final PooledConnectionStatistics statistics;

        public DefaultPooledConnectionProxy(Connection c) {
            this.statistics = new PooledConnectionStatistics(AbstractConnectionPool.this.pruneStrategy.getStatisticsSize());
            this.conn = c;
        }

        @Override
        public ConnectionPool getConnectionPool() {
            return AbstractConnectionPool.this;
        }

        @Override
        public Connection getConnection() {
            return this.conn;
        }

        @Override
        public long getCreatedTime() {
            return this.createdTime;
        }

        @Override
        public PooledConnectionStatistics getPooledConnectionStatistics() {
            return this.statistics;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof DefaultPooledConnectionProxy) {
                DefaultPooledConnectionProxy v = (DefaultPooledConnectionProxy)o;
                return LdapUtils.areEqual(this.conn, v.conn);
            }
            return false;
        }

        public int hashCode() {
            return LdapUtils.computeHashCode(503, this.conn);
        }

        public String toString() {
            return this.getClass().getName() + "@" + this.hashCode() + "::" + "conn=" + this.conn + ", " + "createdTime=" + this.createdTime + ", " + "statistics=" + this.statistics;
        }

        @Override
        public Object invoke(Object proxy, Method method2, Object[] args2) throws Throwable {
            Object retValue = null;
            if ("open".equals(method2.getName())) {
                if (!this.conn.isOpen()) {
                    try {
                        retValue = method2.invoke((Object)this.conn, args2);
                    }
                    catch (InvocationTargetException e) {
                        throw e.getTargetException();
                    }
                }
            } else if ("close".equals(method2.getName())) {
                AbstractConnectionPool.this.putConnection((Connection)proxy);
            } else {
                try {
                    retValue = method2.invoke((Object)this.conn, args2);
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
            return retValue;
        }
    }
}

