/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.R2dbcTransientException;

public class R2dbcTransientResourceException
extends R2dbcTransientException {
    public R2dbcTransientResourceException() {
    }

    public R2dbcTransientResourceException(@Nullable String reason) {
        super(reason);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState) {
        super(reason, sqlState);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState, int errorCode) {
        super(reason, sqlState, errorCode);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql) {
        super(reason, sqlState, errorCode, sql);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql, @Nullable Throwable cause) {
        super(reason, sqlState, errorCode, sql, cause);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable Throwable cause) {
        super(reason, sqlState, errorCode, cause);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable String sqlState, @Nullable Throwable cause) {
        super(reason, sqlState, cause);
    }

    public R2dbcTransientResourceException(@Nullable String reason, @Nullable Throwable cause) {
        super(reason, cause);
    }

    public R2dbcTransientResourceException(@Nullable Throwable cause) {
        super(cause);
    }
}

