/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import io.r2dbc.spi.R2dbcException;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.exception.ExceptionTools;
import org.jooq.exception.SQLStateClass;
import org.jooq.exception.SQLStateSubclass;
import org.jooq.tools.StringUtils;

public class DataAccessException
extends RuntimeException {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    @NotNull
    public String sqlState() {
        SQLException s2 = this.getCause(SQLException.class);
        if (s2 != null) {
            return StringUtils.defaultIfNull(s2.getSQLState(), "00000");
        }
        R2dbcException r = this.getCause(R2dbcException.class);
        if (r != null) {
            return StringUtils.defaultIfNull(r.getSqlState(), "00000");
        }
        return "00000";
    }

    @NotNull
    public SQLStateClass sqlStateClass() {
        SQLException s2 = this.getCause(SQLException.class);
        if (s2 != null) {
            return DataAccessException.sqlStateClass(s2);
        }
        R2dbcException r = this.getCause(R2dbcException.class);
        if (r != null) {
            return DataAccessException.sqlStateClass(r);
        }
        return SQLStateClass.NONE;
    }

    @NotNull
    public static SQLStateClass sqlStateClass(SQLException e) {
        if (e.getClass().getName().startsWith("com.microsoft.sqlserver")) {
            SQLStateClass c = SQLStateClass.fromCode(e.getSQLState());
            if (c != SQLStateClass.OTHER) {
                return c;
            }
            return SQLStateClass.fromSQLServerVendorCode(e.getErrorCode());
        }
        if (e.getSQLState() != null) {
            return SQLStateClass.fromCode(e.getSQLState());
        }
        if (e.getSQLState() == null && "org.sqlite.SQLiteException".equals(e.getClass().getName())) {
            return SQLStateClass.fromSQLiteVendorCode(e.getErrorCode());
        }
        return SQLStateClass.NONE;
    }

    @NotNull
    public static SQLStateClass sqlStateClass(R2dbcException e) {
        if (e.getSqlState() != null) {
            return SQLStateClass.fromCode(e.getSqlState());
        }
        return SQLStateClass.NONE;
    }

    @NotNull
    public SQLStateSubclass sqlStateSubclass() {
        return SQLStateSubclass.fromCode(this.sqlState());
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Nullable
    public <T extends Throwable> T getCause(Class<? extends T> type) {
        return ExceptionTools.getCause(this, type);
    }
}

