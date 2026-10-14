/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.R2dbcNonTransientException;

public class R2dbcPermissionDeniedException
extends R2dbcNonTransientException {
    public R2dbcPermissionDeniedException() {
    }

    public R2dbcPermissionDeniedException(@Nullable String reason) {
        super(reason);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState) {
        super(reason, sqlState);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState, int errorCode) {
        super(reason, sqlState, errorCode);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql) {
        super(reason, sqlState, errorCode, sql);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql, @Nullable Throwable cause) {
        super(reason, sqlState, errorCode, sql, cause);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable Throwable cause) {
        super(reason, sqlState, errorCode, cause);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable String sqlState, @Nullable Throwable cause) {
        super(reason, sqlState, cause);
    }

    public R2dbcPermissionDeniedException(@Nullable String reason, @Nullable Throwable cause) {
        super(reason, cause);
    }

    public R2dbcPermissionDeniedException(@Nullable Throwable cause) {
        super(cause);
    }
}

