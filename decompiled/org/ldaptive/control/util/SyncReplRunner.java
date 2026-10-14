/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control.util;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.InitialRetryMetadata;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.Result;
import org.ldaptive.SearchRequest;
import org.ldaptive.SingleConnectionFactory;
import org.ldaptive.control.util.CookieManager;
import org.ldaptive.control.util.SyncReplClient;
import org.ldaptive.extended.SyncInfoMessage;
import org.ldaptive.transport.Transport;
import org.ldaptive.transport.netty.ConnectionFactoryTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncReplRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncReplRunner.class);
    private static final int IO_WORKER_THREADS = 1;
    private static final int MESSAGE_WORKER_THREADS = 4;
    private final Transport connectionTransport;
    private final ConnectionConfig connectionConfig;
    private final SearchRequest searchRequest;
    private final CookieManager cookieManager;
    private final Consumer<Exception> onException = new Consumer<Exception>(){

        @Override
        public void accept(Exception e) {
            if (SyncReplRunner.this.started) {
                if (SyncReplRunner.this.handlingException.compareAndSet(false, true)) {
                    try {
                        LOGGER.warn("Received exception '{}' for {}", (Object)e.getMessage(), (Object)this);
                        SyncReplRunner.this.stop();
                        SyncReplRunner.this.start();
                    }
                    finally {
                        SyncReplRunner.this.handlingException.set(false);
                    }
                } else {
                    LOGGER.debug("Ignoring exception, restart already in progress for {}", (Object)this);
                }
            } else {
                LOGGER.debug("Ignoring exception, runner not started for {}", (Object)this);
            }
        }
    };
    private SyncReplClient syncReplClient;
    private Supplier<Boolean> onStart;
    private Consumer<LdapEntry> onEntry;
    private Consumer<Result> onResult;
    private Consumer<SyncInfoMessage> onMessage;
    private boolean started;
    private AtomicBoolean handlingException = new AtomicBoolean();

    public SyncReplRunner(ConnectionConfig config, SearchRequest request, CookieManager manager) {
        this(SyncReplRunner.createTransport(), config, request, manager);
    }

    public SyncReplRunner(Transport transport, ConnectionConfig config, SearchRequest request, CookieManager manager) {
        this.connectionTransport = transport;
        this.connectionConfig = config;
        this.searchRequest = request;
        this.cookieManager = manager;
    }

    private static Transport createTransport() {
        ConnectionFactoryTransport transport = new ConnectionFactoryTransport(SyncReplRunner.class.getSimpleName(), 1, 4);
        transport.setShutdownOnClose(false);
        return transport;
    }

    public void setOnStart(Supplier<Boolean> supplier) {
        this.onStart = supplier;
    }

    public void setOnEntry(Consumer<LdapEntry> consumer) {
        this.onEntry = consumer;
    }

    public void setOnResult(Consumer<Result> consumer) {
        this.onResult = consumer;
    }

    public void setOnMessage(Consumer<SyncInfoMessage> consumer) {
        this.onMessage = consumer;
    }

    public void initialize(boolean refreshAndPersist, Duration reconnectWait) {
        if (this.started) {
            throw new IllegalStateException("Runner has already been started");
        }
        SingleConnectionFactory connectionFactory = SyncReplRunner.reconnectFactory(this.connectionTransport, this.connectionConfig, reconnectWait);
        this.syncReplClient = new SyncReplClient(connectionFactory, refreshAndPersist);
        this.syncReplClient.setOnEntry(this.onEntry);
        this.syncReplClient.setOnResult(this.onResult);
        this.syncReplClient.setOnMessage(this.onMessage);
        this.syncReplClient.setOnException(this.onException);
    }

    public synchronized void start() {
        if (this.started) {
            throw new IllegalStateException("Runner has already been started");
        }
        try {
            if (this.onStart != null && !this.onStart.get().booleanValue()) {
                throw new RuntimeException("Start aborted from " + this.onStart);
            }
            LOGGER.debug("Starting runner {}", (Object)this);
            ((SingleConnectionFactory)this.syncReplClient.getConnectionFactory()).initialize();
            this.syncReplClient.send(this.searchRequest, this.cookieManager);
            this.started = true;
            LOGGER.info("Runner {} started", (Object)this);
        }
        catch (Exception e) {
            LOGGER.error("Could not start the runner", e);
        }
    }

    public synchronized void stop() {
        if (!this.started) {
            return;
        }
        LOGGER.debug("Stopping runner {}", (Object)this);
        this.started = false;
        if (this.syncReplClient != null) {
            try {
                if (!this.syncReplClient.isComplete()) {
                    this.syncReplClient.cancel();
                }
            }
            catch (Exception e) {
                LOGGER.warn("Could not cancel sync repl request", e);
            }
            finally {
                this.syncReplClient.close();
            }
        }
        LOGGER.info("Runner {} stopped", (Object)this);
    }

    public boolean isStarted() {
        return this.started;
    }

    public synchronized void restartSearch() {
        if (!this.started) {
            throw new IllegalStateException("Runner is stopped");
        }
        try {
            if (!this.syncReplClient.isComplete()) {
                this.syncReplClient.cancel();
            }
        }
        catch (Exception e) {
            LOGGER.warn("Could not cancel sync repl request", e);
        }
        try {
            this.syncReplClient.send(this.searchRequest, this.cookieManager);
        }
        catch (LdapException e) {
            throw new IllegalStateException("Could not send sync repl request", e);
        }
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "syncReplClient=" + this.syncReplClient + ", " + "searchRequest=" + this.searchRequest + ", " + "cookieManager=" + this.cookieManager + ", " + "onStart=" + this.onStart + ", " + "onEntry=" + this.onEntry + ", " + "onResult=" + this.onResult + ", " + "onMessage=" + this.onMessage + ", " + "started=" + this.started;
    }

    protected static SingleConnectionFactory reconnectFactory(Transport transport, ConnectionConfig cc, Duration wait) {
        ConnectionConfig newConfig = ConnectionConfig.copy(cc);
        newConfig.setTransportOption("AUTO_READ", false);
        newConfig.setAutoReconnect(false);
        newConfig.setAutoReconnectCondition(metadata -> {
            if (metadata instanceof InitialRetryMetadata) {
                try {
                    LOGGER.debug("Waiting {}ms to reconnect", (Object)wait.toMillis());
                    Thread.sleep(wait.toMillis());
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return true;
            }
            return false;
        });
        newConfig.setAutoReplay(false);
        SingleConnectionFactory factory2 = new SingleConnectionFactory(newConfig, transport);
        factory2.setFailFastInitialize(true);
        factory2.setNonBlockingInitialize(false);
        return factory2;
    }
}

