/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.jooq;

import java.sql.Connection;
import javax.annotation.concurrent.GuardedBy;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.jooq.impl.DataSourceConnectionProvider;

public class LockingDataSourceConnectionProvider
extends DataSourceConnectionProvider {
    @GuardedBy(value="this")
    private boolean isAvailable = true;

    public LockingDataSourceConnectionProvider(DataSource dataSource) {
        super(dataSource);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @NotNull
    public Connection acquire() {
        LockingDataSourceConnectionProvider lockingDataSourceConnectionProvider = this;
        synchronized (lockingDataSourceConnectionProvider) {
            while (!this.isAvailable) {
                try {
                    this.wait();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return super.acquire();
    }

    public synchronized void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        if (isAvailable) {
            this.notifyAll();
        }
    }
}

