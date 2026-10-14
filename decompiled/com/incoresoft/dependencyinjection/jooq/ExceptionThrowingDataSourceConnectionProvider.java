/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dependencyinjection.jooq;

import com.incoresoft.dependencyinjection.exceptions.UnavailabilityReason;
import java.sql.Connection;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.jooq.impl.DataSourceConnectionProvider;

public class ExceptionThrowingDataSourceConnectionProvider
extends DataSourceConnectionProvider {
    private volatile boolean isAvailable = true;
    private volatile UnavailabilityReason reason;

    public ExceptionThrowingDataSourceConnectionProvider(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @NotNull
    public Connection acquire() {
        if (!this.isAvailable) {
            throw this.reason.getExceptionSupplier().get();
        }
        return super.acquire();
    }

    public synchronized void setUnavailable(UnavailabilityReason unavailabilityReason) {
        this.isAvailable = false;
        this.reason = unavailabilityReason;
    }

    public synchronized void setAvailable() {
        this.isAvailable = true;
        this.reason = null;
    }
}

