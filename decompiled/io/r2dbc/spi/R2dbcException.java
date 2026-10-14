/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Nullable;

public abstract class R2dbcException
extends RuntimeException {
    private final int errorCode;
    @Nullable
    private final String sqlState;
    @Nullable
    private final String sql;

    public R2dbcException() {
        this((String)null);
    }

    public R2dbcException(@Nullable String reason) {
        this(reason, (String)null);
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState) {
        this(reason, sqlState, 0);
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState, int errorCode) {
        this(reason, sqlState, errorCode, null, null);
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql) {
        this(reason, sqlState, errorCode, sql, null);
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable String sql, @Nullable Throwable cause) {
        super(reason, cause);
        this.sqlState = sqlState;
        this.errorCode = errorCode;
        this.sql = sql;
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState, int errorCode, @Nullable Throwable cause) {
        this(reason, sqlState, errorCode, null, cause);
    }

    public R2dbcException(@Nullable String reason, @Nullable String sqlState, @Nullable Throwable cause) {
        this(reason, sqlState, 0, cause);
    }

    public R2dbcException(@Nullable String reason, @Nullable Throwable cause) {
        this(reason, null, cause);
    }

    public R2dbcException(@Nullable Throwable cause) {
        this(null, cause);
    }

    public final int getErrorCode() {
        return this.errorCode;
    }

    @Nullable
    public final String getSqlState() {
        return this.sqlState;
    }

    @Nullable
    public final String getSql() {
        return this.sql;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(32);
        builder.append(this.getClass().getName());
        if (this.getErrorCode() != 0 || this.getSqlState() != null && !this.getSqlState().isEmpty() || this.getMessage() != null) {
            builder.append(":");
        }
        if (this.getErrorCode() != 0) {
            builder.append(" [").append(this.getErrorCode()).append("]");
        }
        if (this.getSqlState() != null && !this.getSqlState().isEmpty()) {
            builder.append(" [").append(this.getSqlState()).append("]");
        }
        if (this.getMessage() != null) {
            builder.append(" ").append(this.getMessage());
        }
        return builder.toString();
    }
}

