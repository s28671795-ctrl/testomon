/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.ldaptive.Connection;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.pool.AbstractConnectionPool;
import org.ldaptive.pool.BlockingTimeoutException;
import org.ldaptive.pool.PoolException;
import org.ldaptive.pool.PoolExhaustedException;
import org.ldaptive.pool.PooledConnectionProxy;

public class BlockingConnectionPool
extends AbstractConnectionPool {
    private Duration blockWaitTime = Duration.ofMinutes(1L);

    public BlockingConnectionPool() {
    }

    public BlockingConnectionPool(DefaultConnectionFactory cf) {
        this.setDefaultConnectionFactory(cf);
    }

    public Duration getBlockWaitTime() {
        return this.blockWaitTime;
    }

    public void setBlockWaitTime(Duration time) {
        if (time == null || time.isNegative()) {
            throw new IllegalArgumentException("Block wait time cannot be null or negative");
        }
        this.blockWaitTime = time;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Connection getConnection() throws PoolException {
        boolean create;
        PooledConnectionProxy pc;
        block21: {
            this.throwIfNotInitialized();
            pc = null;
            create = false;
            this.logger.trace("waiting on pool lock for check out {}", (Object)this.poolLock.getQueueLength());
            this.poolLock.lock();
            try {
                if (!this.available.isEmpty()) {
                    try {
                        this.logger.trace("retrieve available connection from pool of size {}", (Object)this.available.size());
                        pc = this.retrieveAvailableConnection();
                        break block21;
                    }
                    catch (NoSuchElementException e) {
                        this.logger.error("Could not retrieve available connection", e);
                        throw new IllegalStateException("Pool is empty", e);
                    }
                }
                if (this.active.size() < this.getMaxPoolSize()) {
                    this.logger.trace("pool can grow, attempt to create active connection in pool of size {}", (Object)this.active.size());
                    create = true;
                } else {
                    this.logger.trace("pool is full, block until connection is available");
                    pc = this.blockAvailableConnection();
                }
            }
            finally {
                this.poolLock.unlock();
            }
        }
        if (create) {
            this.checkOutLock.lock();
            try {
                boolean b = true;
                this.poolLock.lock();
                try {
                    this.logger.trace("create connection in pool of size {}", (Object)(this.available.size() + this.active.size()));
                    if (this.available.size() + this.active.size() == this.getMaxPoolSize()) {
                        this.logger.trace("pool at maximum size, create not allowed");
                        b = false;
                    }
                }
                finally {
                    this.poolLock.unlock();
                }
                if (b) {
                    pc = this.createActiveConnection(false);
                }
            }
            finally {
                this.checkOutLock.unlock();
            }
            if (pc == null) {
                if (this.available.isEmpty() && this.active.isEmpty()) {
                    this.logger.error("Could not service check out request");
                    throw new PoolExhaustedException("Pool is empty and connection creation failed");
                }
                this.logger.debug("Create failed, block until connection is available");
                pc = this.blockAvailableConnection();
            } else {
                this.logger.trace("created new active connection: {}", (Object)pc);
            }
        }
        if (pc == null) {
            this.logger.error("Could not service check out request");
            throw new PoolExhaustedException("Pool is empty and connection creation failed");
        }
        this.activateAndValidateConnection(pc);
        return this.createConnectionProxy(pc);
    }

    protected PooledConnectionProxy retrieveAvailableConnection() {
        PooledConnectionProxy pc;
        this.logger.trace("waiting on pool lock for retrieve available {}", (Object)this.poolLock.getQueueLength());
        this.poolLock.lock();
        try {
            pc = (PooledConnectionProxy)this.available.remove();
            this.active.add(pc);
            pc.getPooledConnectionStatistics().addActiveStat();
            this.logger.trace("retrieved available connection: {}", (Object)pc);
        }
        finally {
            this.poolLock.unlock();
        }
        return pc;
    }

    protected PooledConnectionProxy blockAvailableConnection() throws PoolException {
        PooledConnectionProxy pc = null;
        this.logger.trace("waiting on pool lock for block available {}", (Object)this.poolLock.getQueueLength());
        this.poolLock.lock();
        try {
            while (pc == null) {
                this.logger.trace("available pool is empty, waiting for pool not empty");
                if (Duration.ZERO.equals(this.blockWaitTime)) {
                    this.poolNotEmpty.await();
                } else if (!this.poolNotEmpty.await(this.blockWaitTime.toMillis(), TimeUnit.MILLISECONDS)) {
                    this.logger.debug("Block time of {} exceeded, throwing exception", (Object)this.blockWaitTime);
                    throw new BlockingTimeoutException("Block time of " + this.blockWaitTime + " exceeded for pool " + this.getName() + " with max size of " + this.getMaxPoolSize());
                }
                this.logger.trace("notified to continue for pool not empty");
                try {
                    pc = this.retrieveAvailableConnection();
                }
                catch (NoSuchElementException e) {
                    this.logger.trace("notified to continue for pool not empty but pool was empty");
                }
            }
        }
        catch (InterruptedException e) {
            this.logger.error("Waiting for available connection interrupted", e);
            throw new PoolException("Interrupted while waiting for an available connection", e);
        }
        finally {
            this.poolLock.unlock();
        }
        return pc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void putConnection(Connection c) {
        this.throwIfNotInitialized();
        PooledConnectionProxy pc = this.retrieveConnectionProxy(c);
        boolean valid = this.passivateAndValidateConnection(pc);
        this.logger.trace("waiting on pool lock for check in {}", (Object)this.poolLock.getQueueLength());
        this.poolLock.lock();
        try {
            if (!valid) {
                this.removeAvailableAndActiveConnection(pc);
            } else if (this.active.remove(pc)) {
                this.available.add(pc);
                pc.getPooledConnectionStatistics().addAvailableStat();
                this.logger.trace("returned active connection: {}", (Object)pc);
                this.poolNotEmpty.signal();
            } else if (this.available.contains(pc)) {
                this.logger.warn("returned available connection: {}", (Object)pc);
            } else {
                this.logger.warn("attempt to return unknown connection: {}", (Object)pc);
            }
        }
        finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "blockWaitTime=" + this.blockWaitTime;
    }
}

