/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbcx;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import org.h2.jdbcx.JdbcConnectionPoolBackwardsCompat;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.message.DbException;

public final class JdbcConnectionPool
implements DataSource,
ConnectionEventListener,
JdbcConnectionPoolBackwardsCompat {
    private static final int DEFAULT_TIMEOUT = 30;
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private final ConnectionPoolDataSource dataSource;
    private final Queue<PooledConnection> recycledConnections = new ConcurrentLinkedQueue<PooledConnection>();
    private PrintWriter logWriter;
    private volatile int maxConnections = 10;
    private volatile int timeout = 30;
    private AtomicInteger activeConnections = new AtomicInteger();
    private AtomicBoolean isDisposed = new AtomicBoolean();

    private JdbcConnectionPool(ConnectionPoolDataSource connectionPoolDataSource) {
        this.dataSource = connectionPoolDataSource;
        if (connectionPoolDataSource != null) {
            try {
                this.logWriter = connectionPoolDataSource.getLogWriter();
            }
            catch (SQLException sQLException) {
                // empty catch block
            }
        }
    }

    public static JdbcConnectionPool create(ConnectionPoolDataSource connectionPoolDataSource) {
        return new JdbcConnectionPool(connectionPoolDataSource);
    }

    public static JdbcConnectionPool create(String string, String string2, String string3) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(string);
        jdbcDataSource.setUser(string2);
        jdbcDataSource.setPassword(string3);
        return new JdbcConnectionPool(jdbcDataSource);
    }

    public void setMaxConnections(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid maxConnections value: " + n);
        }
        this.maxConnections = n;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    @Override
    public int getLoginTimeout() {
        return this.timeout;
    }

    @Override
    public void setLoginTimeout(int n) {
        if (n == 0) {
            n = 30;
        }
        this.timeout = n;
    }

    public void dispose() {
        PooledConnection pooledConnection;
        this.isDisposed.set(true);
        while ((pooledConnection = this.recycledConnections.poll()) != null) {
            this.closeConnection(pooledConnection);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        long l = System.nanoTime() + (long)this.timeout * 1000000000L;
        int n = 0;
        do {
            if (this.activeConnections.incrementAndGet() <= this.maxConnections) {
                try {
                    return this.getConnectionNow();
                }
                catch (Throwable throwable) {
                    this.activeConnections.decrementAndGet();
                    throw throwable;
                }
            }
            this.activeConnections.decrementAndGet();
            if (--n >= 0) continue;
            try {
                n = 3;
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        } while (System.nanoTime() - l <= 0L);
        throw new SQLException("Login timeout", "08001", 8001);
    }

    @Override
    public Connection getConnection(String string, String string2) {
        throw new UnsupportedOperationException();
    }

    private Connection getConnectionNow() throws SQLException {
        if (this.isDisposed.get()) {
            throw new IllegalStateException("Connection pool has been disposed.");
        }
        PooledConnection pooledConnection = this.recycledConnections.poll();
        if (pooledConnection == null) {
            pooledConnection = this.dataSource.getPooledConnection();
        }
        Connection connection = pooledConnection.getConnection();
        pooledConnection.addConnectionEventListener(this);
        return connection;
    }

    private void recycleConnection(PooledConnection pooledConnection) {
        int n = this.activeConnections.decrementAndGet();
        if (n < 0) {
            this.activeConnections.incrementAndGet();
            throw new AssertionError();
        }
        if (!this.isDisposed.get() && n < this.maxConnections) {
            this.recycledConnections.add(pooledConnection);
            if (this.isDisposed.get()) {
                this.dispose();
            }
        } else {
            this.closeConnection(pooledConnection);
        }
    }

    private void closeConnection(PooledConnection pooledConnection) {
        block2: {
            try {
                pooledConnection.close();
            }
            catch (SQLException sQLException) {
                if (this.logWriter == null) break block2;
                sQLException.printStackTrace(this.logWriter);
            }
        }
    }

    @Override
    public void connectionClosed(ConnectionEvent connectionEvent) {
        PooledConnection pooledConnection = (PooledConnection)connectionEvent.getSource();
        pooledConnection.removeConnectionEventListener(this);
        this.recycleConnection(pooledConnection);
    }

    @Override
    public void connectionErrorOccurred(ConnectionEvent connectionEvent) {
    }

    public int getActiveConnections() {
        return this.activeConnections.get();
    }

    @Override
    public PrintWriter getLogWriter() {
        return this.logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) {
        this.logWriter = printWriter;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        try {
            if (this.isWrapperFor(clazz)) {
                return (T)this;
            }
            throw DbException.getInvalidValueException("iface", clazz);
        }
        catch (Exception exception) {
            throw DbException.toSQLException(exception);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return clazz != null && clazz.isAssignableFrom(this.getClass());
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }
}

