/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;
import org.ldaptive.ConnectException;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionStrategy;
import org.ldaptive.InitialRetryMetadata;
import org.ldaptive.LdapException;
import org.ldaptive.LdapURL;
import org.ldaptive.ResultCode;
import org.ldaptive.RetryMetadata;
import org.ldaptive.UnbindRequest;
import org.ldaptive.transport.DefaultOperationHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransportConnection
implements Connection {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportConnection.class);
    protected final ReentrantLock openLock = new ReentrantLock();
    protected final ReentrantLock closeLock = new ReentrantLock();
    protected final ConnectionConfig connectionConfig;
    protected Instant lastSuccessfulOpen;
    private final ConnectionStrategy connectionStrategy;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TransportConnection(ConnectionConfig config) {
        if (config == null) {
            throw new NullPointerException("Connection config cannot be null");
        }
        this.connectionConfig = config;
        ConnectionStrategy connectionStrategy = this.connectionStrategy = this.connectionConfig.getConnectionStrategy();
        synchronized (connectionStrategy) {
            if (!this.connectionStrategy.isInitialized()) {
                this.connectionStrategy.initialize(this.connectionConfig.getLdapUrl(), this::test);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void open() throws LdapException {
        LOGGER.debug("Strategy {} opening connection {}", (Object)this.connectionStrategy, (Object)this);
        if (this.openLock.tryLock()) {
            try {
                LdapException lastThrown;
                if (this.isOpen()) {
                    throw new ConnectException(ResultCode.CONNECT_ERROR, "Connection is already open");
                }
                InitialRetryMetadata metadata = new InitialRetryMetadata(this.lastSuccessfulOpen);
                while (true) {
                    try {
                        this.strategyOpen(metadata);
                        lastThrown = null;
                    }
                    catch (LdapException e) {
                        lastThrown = e;
                        LOGGER.debug("Error opening connection for strategy {} with metadata {}", this.connectionStrategy, metadata, e);
                        if (lastThrown != null && this.connectionConfig.getAutoReconnectCondition().test(metadata)) continue;
                    }
                    break;
                }
                if (lastThrown != null) {
                    throw lastThrown;
                }
                if (this.isOpen()) {
                    this.lastSuccessfulOpen = Instant.now();
                }
                LOGGER.debug("Strategy {} finished open for connection {}", (Object)this.connectionStrategy, (Object)this);
            }
            finally {
                this.openLock.unlock();
            }
        } else {
            LOGGER.warn("Open lock {} could not be acquired by {}", (Object)this.openLock, (Object)Thread.currentThread());
            throw new LdapException(ResultCode.CONNECT_ERROR, "Open in progress");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void reopen(RetryMetadata metadata) throws LdapException {
        LOGGER.debug("Strategy {} reopening connection {}", (Object)this.connectionStrategy, (Object)this);
        if (this.openLock.tryLock()) {
            try {
                if (this.isOpen()) {
                    throw new ConnectException(ResultCode.CONNECT_ERROR, "Connection is already open");
                }
                LdapException lastThrown = null;
                while (this.connectionConfig.getAutoReconnectCondition().test(metadata)) {
                    try {
                        this.strategyOpen(metadata);
                        lastThrown = null;
                        break;
                    }
                    catch (LdapException e) {
                        lastThrown = e;
                        LOGGER.debug("Error reopening connection {} using strategy {}", this, this.connectionStrategy, e);
                    }
                }
                if (lastThrown != null) {
                    throw lastThrown;
                }
                if (this.isOpen()) {
                    this.lastSuccessfulOpen = Instant.now();
                }
                LOGGER.debug("Finished reopen for connection {} using strategy {}", (Object)this, (Object)this.connectionStrategy);
            }
            finally {
                this.openLock.unlock();
            }
        } else {
            LOGGER.warn("Open lock {} could not be acquired by {}", (Object)this.openLock, (Object)Thread.currentThread());
            throw new LdapException(ResultCode.CONNECT_ERROR, "Open in progress");
        }
    }

    protected void strategyOpen(RetryMetadata metadata) throws LdapException {
        boolean strategyProducedUrls = false;
        ConnectException lastThrown = null;
        for (LdapURL url2 : this.connectionStrategy) {
            strategyProducedUrls = true;
            try {
                LOGGER.trace("attempting connection to {} for strategy {}", (Object)url2.getHostnameWithSchemeAndPort(), (Object)this.connectionStrategy);
                this.open(url2);
                this.connectionStrategy.success(url2);
                metadata.recordSuccess(Instant.now());
                lastThrown = null;
                break;
            }
            catch (ConnectException e) {
                this.connectionStrategy.failure(url2);
                lastThrown = e;
                LOGGER.debug("Error connecting to {} for strategy {}", url2.getHostnameWithSchemeAndPort(), this.connectionStrategy, e);
            }
        }
        if (!strategyProducedUrls) {
            throw new IllegalStateException("Connection strategy did not produce any LDAP URLs");
        }
        if (lastThrown != null) {
            metadata.recordFailure(Instant.now());
            throw lastThrown;
        }
    }

    protected abstract boolean test(LdapURL var1);

    protected abstract void open(LdapURL var1) throws LdapException;

    protected abstract void operation(UnbindRequest var1);

    protected abstract void write(DefaultOperationHandle var1);

    protected abstract void complete(DefaultOperationHandle var1);
}

