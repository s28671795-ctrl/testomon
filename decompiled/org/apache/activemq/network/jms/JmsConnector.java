/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network.jms;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.jms.Connection;
import javax.jms.Destination;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.network.jms.DestinationBridge;
import org.apache.activemq.network.jms.JmsMesageConvertor;
import org.apache.activemq.network.jms.JndiLookupFactory;
import org.apache.activemq.network.jms.ReconnectionPolicy;
import org.apache.activemq.network.jms.SimpleJmsMessageConvertor;
import org.apache.activemq.util.LRUCache;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JmsConnector
implements Service {
    private static int nextId;
    private static final Logger LOG;
    protected boolean preferJndiDestinationLookup = false;
    protected JndiLookupFactory jndiLocalTemplate;
    protected JndiLookupFactory jndiOutboundTemplate;
    protected JmsMesageConvertor inboundMessageConvertor;
    protected JmsMesageConvertor outboundMessageConvertor;
    protected AtomicBoolean initialized = new AtomicBoolean(false);
    protected AtomicBoolean localSideInitialized = new AtomicBoolean(false);
    protected AtomicBoolean foreignSideInitialized = new AtomicBoolean(false);
    protected AtomicBoolean started = new AtomicBoolean(false);
    protected AtomicBoolean failed = new AtomicBoolean();
    protected AtomicReference<Connection> foreignConnection = new AtomicReference();
    protected AtomicReference<Connection> localConnection = new AtomicReference();
    protected ActiveMQConnectionFactory embeddedConnectionFactory;
    protected int replyToDestinationCacheSize = 10000;
    protected String outboundUsername;
    protected String outboundPassword;
    protected String localUsername;
    protected String localPassword;
    protected String outboundClientId;
    protected String localClientId;
    protected LRUCache<Destination, DestinationBridge> replyToBridges = JmsConnector.createLRUCache();
    private ReconnectionPolicy policy = new ReconnectionPolicy();
    protected ThreadPoolExecutor connectionService;
    private final List<DestinationBridge> inboundBridges = new CopyOnWriteArrayList<DestinationBridge>();
    private final List<DestinationBridge> outboundBridges = new CopyOnWriteArrayList<DestinationBridge>();
    private String name;
    private final ThreadFactory factory = new ThreadFactory(){

        @Override
        public Thread newThread(Runnable runnable2) {
            Thread thread2 = new Thread(runnable2, "JmsConnector Async Connection Task: ");
            thread2.setDaemon(true);
            return thread2;
        }
    };

    private static LRUCache<Destination, DestinationBridge> createLRUCache() {
        return new LRUCache<Destination, DestinationBridge>(){
            private static final long serialVersionUID = -7446792754185879286L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<Destination, DestinationBridge> enty) {
                if (this.size() > this.maxCacheSize) {
                    Iterator iter = this.entrySet().iterator();
                    Map.Entry lru = iter.next();
                    this.remove(lru.getKey());
                    DestinationBridge bridge = (DestinationBridge)lru.getValue();
                    try {
                        bridge.stop();
                        LOG.info("Expired bridge: {}", (Object)bridge);
                    }
                    catch (Exception e) {
                        LOG.warn("Stopping expired bridge {} caused an exception", (Object)bridge, (Object)e);
                    }
                }
                return false;
            }
        };
    }

    public boolean init() {
        boolean result = this.initialized.compareAndSet(false, true);
        if (result) {
            if (this.jndiLocalTemplate == null) {
                this.jndiLocalTemplate = new JndiLookupFactory();
            }
            if (this.jndiOutboundTemplate == null) {
                this.jndiOutboundTemplate = new JndiLookupFactory();
            }
            if (this.inboundMessageConvertor == null) {
                this.inboundMessageConvertor = new SimpleJmsMessageConvertor();
            }
            if (this.outboundMessageConvertor == null) {
                this.outboundMessageConvertor = new SimpleJmsMessageConvertor();
            }
            this.replyToBridges.setMaxCacheSize(this.getReplyToDestinationCacheSize());
            this.connectionService = this.createExecutor();
            result = this.doConnectorInit();
        }
        return result;
    }

    protected boolean doConnectorInit() {
        try {
            this.initializeLocalConnection();
            this.localSideInitialized.set(true);
        }
        catch (Exception e) {
            this.scheduleAsyncLocalConnectionReconnect();
        }
        try {
            this.initializeForeignConnection();
            this.foreignSideInitialized.set(true);
        }
        catch (Exception e) {
            this.scheduleAsyncForeignConnectionReconnect();
        }
        return true;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            this.init();
            for (DestinationBridge bridge : this.inboundBridges) {
                bridge.start();
            }
            for (DestinationBridge bridge : this.outboundBridges) {
                bridge.start();
            }
            LOG.info("JMS Connector {} started", (Object)this.getName());
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            ThreadPoolUtils.shutdown(this.connectionService);
            this.connectionService = null;
            if (this.foreignConnection.get() != null) {
                try {
                    this.foreignConnection.get().close();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (this.localConnection.get() != null) {
                try {
                    this.localConnection.get().close();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            for (DestinationBridge bridge : this.inboundBridges) {
                bridge.stop();
            }
            for (DestinationBridge bridge : this.outboundBridges) {
                bridge.stop();
            }
            LOG.info("JMS Connector {} stopped", (Object)this.getName());
        }
    }

    public void clearBridges() {
        this.inboundBridges.clear();
        this.outboundBridges.clear();
        this.replyToBridges.clear();
    }

    protected abstract Destination createReplyToBridge(Destination var1, Connection var2, Connection var3);

    public void setBrokerService(BrokerService service2) {
        this.embeddedConnectionFactory = new ActiveMQConnectionFactory(service2.getVmConnectorURI());
    }

    public Connection getLocalConnection() {
        return this.localConnection.get();
    }

    public Connection getForeignConnection() {
        return this.foreignConnection.get();
    }

    public JndiLookupFactory getJndiLocalTemplate() {
        return this.jndiLocalTemplate;
    }

    public void setJndiLocalTemplate(JndiLookupFactory jndiTemplate) {
        this.jndiLocalTemplate = jndiTemplate;
    }

    public JndiLookupFactory getJndiOutboundTemplate() {
        return this.jndiOutboundTemplate;
    }

    public void setJndiOutboundTemplate(JndiLookupFactory jndiOutboundTemplate) {
        this.jndiOutboundTemplate = jndiOutboundTemplate;
    }

    public JmsMesageConvertor getInboundMessageConvertor() {
        return this.inboundMessageConvertor;
    }

    public void setInboundMessageConvertor(JmsMesageConvertor jmsMessageConvertor) {
        this.inboundMessageConvertor = jmsMessageConvertor;
    }

    public JmsMesageConvertor getOutboundMessageConvertor() {
        return this.outboundMessageConvertor;
    }

    public void setOutboundMessageConvertor(JmsMesageConvertor outboundMessageConvertor) {
        this.outboundMessageConvertor = outboundMessageConvertor;
    }

    public int getReplyToDestinationCacheSize() {
        return this.replyToDestinationCacheSize;
    }

    public void setReplyToDestinationCacheSize(int replyToDestinationCacheSize) {
        this.replyToDestinationCacheSize = replyToDestinationCacheSize;
    }

    public String getLocalPassword() {
        return this.localPassword;
    }

    public void setLocalPassword(String localPassword) {
        this.localPassword = localPassword;
    }

    public String getLocalUsername() {
        return this.localUsername;
    }

    public void setLocalUsername(String localUsername) {
        this.localUsername = localUsername;
    }

    public String getOutboundPassword() {
        return this.outboundPassword;
    }

    public void setOutboundPassword(String outboundPassword) {
        this.outboundPassword = outboundPassword;
    }

    public String getOutboundUsername() {
        return this.outboundUsername;
    }

    public void setOutboundUsername(String outboundUsername) {
        this.outboundUsername = outboundUsername;
    }

    public String getOutboundClientId() {
        return this.outboundClientId;
    }

    public void setOutboundClientId(String outboundClientId) {
        this.outboundClientId = outboundClientId;
    }

    public String getLocalClientId() {
        return this.localClientId;
    }

    public void setLocalClientId(String localClientId) {
        this.localClientId = localClientId;
    }

    public ReconnectionPolicy getReconnectionPolicy() {
        return this.policy;
    }

    public void setReconnectionPolicy(ReconnectionPolicy policy) {
        this.policy = policy;
    }

    public boolean isPreferJndiDestinationLookup() {
        return this.preferJndiDestinationLookup;
    }

    public void setPreferJndiDestinationLookup(boolean preferJndiDestinationLookup) {
        this.preferJndiDestinationLookup = preferJndiDestinationLookup;
    }

    public boolean isConnected() {
        return this.localConnection.get() != null && this.foreignConnection.get() != null;
    }

    protected void addInboundBridge(DestinationBridge bridge) {
        if (!this.inboundBridges.contains(bridge)) {
            this.inboundBridges.add(bridge);
        }
    }

    protected void addOutboundBridge(DestinationBridge bridge) {
        if (!this.outboundBridges.contains(bridge)) {
            this.outboundBridges.add(bridge);
        }
    }

    protected void removeInboundBridge(DestinationBridge bridge) {
        this.inboundBridges.remove(bridge);
    }

    protected void removeOutboundBridge(DestinationBridge bridge) {
        this.outboundBridges.remove(bridge);
    }

    public String getName() {
        if (this.name == null) {
            this.name = "Connector:" + JmsConnector.getNextId();
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static synchronized int getNextId() {
        return nextId++;
    }

    public boolean isFailed() {
        return this.failed.get();
    }

    protected abstract void initializeLocalConnection() throws Exception;

    protected abstract void initializeForeignConnection() throws Exception;

    void handleConnectionFailure(Connection connection) {
        if (connection == null || !this.started.get()) {
            return;
        }
        LOG.info("JmsConnector handling loss of connection [{}]", (Object)connection.toString());
        this.replyToBridges.clear();
        if (this.foreignConnection.compareAndSet(connection, null)) {
            for (DestinationBridge bridge : this.inboundBridges) {
                try {
                    bridge.stop();
                }
                catch (Exception exception) {}
            }
            this.connectionService.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        JmsConnector.this.doInitializeConnection(false);
                    }
                    catch (Exception e) {
                        LOG.error("Failed to initialize foreign connection for the JMSConnector", e);
                    }
                }
            });
        } else if (this.localConnection.compareAndSet(connection, null)) {
            for (DestinationBridge bridge : this.outboundBridges) {
                try {
                    bridge.stop();
                }
                catch (Exception exception) {}
            }
            this.connectionService.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        JmsConnector.this.doInitializeConnection(true);
                    }
                    catch (Exception e) {
                        LOG.error("Failed to initialize local connection for the JMSConnector", e);
                    }
                }
            });
        }
    }

    private void scheduleAsyncLocalConnectionReconnect() {
        this.connectionService.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    JmsConnector.this.doInitializeConnection(true);
                }
                catch (Exception e) {
                    LOG.error("Failed to initialize local connection for the JMSConnector", e);
                }
            }
        });
    }

    private void scheduleAsyncForeignConnectionReconnect() {
        this.connectionService.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    JmsConnector.this.doInitializeConnection(false);
                }
                catch (Exception e) {
                    LOG.error("Failed to initialize foreign connection for the JMSConnector", e);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doInitializeConnection(boolean local) throws Exception {
        int maxRetries;
        ThreadPoolExecutor connectionService = this.connectionService;
        int attempt = 0;
        if (local) {
            maxRetries = !this.localSideInitialized.get() ? this.policy.getMaxInitialConnectAttempts() : this.policy.getMaxReconnectAttempts();
        } else {
            int n = maxRetries = !this.foreignSideInitialized.get() ? this.policy.getMaxInitialConnectAttempts() : this.policy.getMaxReconnectAttempts();
        }
        do {
            if (attempt > 0) {
                try {
                    long nextDelay = this.policy.getNextDelay(attempt);
                    LOG.debug("Bridge reconnect attempt {} waiting {}ms before next attempt.", (Object)attempt, (Object)nextDelay);
                    Thread.sleep(nextDelay);
                }
                catch (InterruptedException nextDelay) {
                    // empty catch block
                }
            }
            if (connectionService.isTerminating()) {
                return;
            }
            try {
                if (local) {
                    this.initializeLocalConnection();
                    this.localSideInitialized.set(true);
                } else {
                    this.initializeForeignConnection();
                    this.foreignSideInitialized.set(true);
                }
                if (this.localConnection.get() != null && this.foreignConnection.get() != null) {
                    for (DestinationBridge bridge : this.inboundBridges) {
                        bridge.start();
                    }
                    for (DestinationBridge bridge : this.outboundBridges) {
                        bridge.start();
                    }
                }
                return;
            }
            catch (Exception e) {
                LOG.debug("Failed to establish initial {} connection for JmsConnector [{}]", (Object)new Object[]{local ? "local" : "foreign", attempt}, (Object)e);
            }
            finally {
                ++attempt;
            }
        } while ((maxRetries == -1 || maxRetries > attempt) && !connectionService.isShutdown());
        this.failed.set(true);
    }

    private ThreadPoolExecutor createExecutor() {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(0, 2, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), this.factory);
        exec.allowCoreThreadTimeOut(true);
        return exec;
    }

    static {
        LOG = LoggerFactory.getLogger(JmsConnector.class);
    }
}

