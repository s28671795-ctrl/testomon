/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.ConstantPool;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.TransactionDefinition;

public final class IsolationLevel
implements TransactionDefinition {
    private static final ConstantPool<IsolationLevel> CONSTANTS = new ConstantPool<IsolationLevel>(){

        @Override
        IsolationLevel createConstant(String name, boolean sensitive) {
            return new IsolationLevel(name);
        }
    };
    public static final IsolationLevel READ_COMMITTED = IsolationLevel.valueOf("READ COMMITTED");
    public static final IsolationLevel READ_UNCOMMITTED = IsolationLevel.valueOf("READ UNCOMMITTED");
    public static final IsolationLevel REPEATABLE_READ = IsolationLevel.valueOf("REPEATABLE READ");
    public static final IsolationLevel SERIALIZABLE = IsolationLevel.valueOf("SERIALIZABLE");
    private final String sql;

    private IsolationLevel(String sql) {
        this.sql = Assert.requireNonNull(sql, "sql must not be null");
    }

    public static IsolationLevel valueOf(String sql) {
        Assert.requireNonNull(sql, "sql must not be null");
        Assert.requireNonEmpty(sql, "sql must not be empty");
        return CONSTANTS.valueOf(sql, false);
    }

    @Override
    public <T> T getAttribute(Option<T> option) {
        Assert.requireNonNull(option, "option must not be null");
        if (option.equals(TransactionDefinition.ISOLATION_LEVEL)) {
            return option.cast(this);
        }
        return null;
    }

    public String asSql() {
        return this.sql;
    }

    public String toString() {
        return "IsolationLevel{sql='" + this.sql + '\'' + '}';
    }
}

