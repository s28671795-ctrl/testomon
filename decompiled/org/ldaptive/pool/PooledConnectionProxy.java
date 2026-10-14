/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.lang.reflect.InvocationHandler;
import org.ldaptive.Connection;
import org.ldaptive.pool.ConnectionPool;
import org.ldaptive.pool.PooledConnectionStatistics;

public interface PooledConnectionProxy
extends InvocationHandler {
    public ConnectionPool getConnectionPool();

    public Connection getConnection();

    public long getCreatedTime();

    public PooledConnectionStatistics getPooledConnectionStatistics();
}

