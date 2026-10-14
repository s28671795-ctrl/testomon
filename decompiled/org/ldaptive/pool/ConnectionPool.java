/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.util.Set;
import org.ldaptive.Connection;
import org.ldaptive.pool.ConnectionActivator;
import org.ldaptive.pool.ConnectionPassivator;
import org.ldaptive.pool.PoolException;
import org.ldaptive.pool.PooledConnectionStatistics;

public interface ConnectionPool {
    public ConnectionActivator getActivator();

    public void setActivator(ConnectionActivator var1);

    public ConnectionPassivator getPassivator();

    public void setPassivator(ConnectionPassivator var1);

    public void initialize();

    public Connection getConnection() throws PoolException;

    public int availableCount();

    public int activeCount();

    public Set<PooledConnectionStatistics> getPooledConnectionStatistics();

    public void close();
}

