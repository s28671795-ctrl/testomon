/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.Result;
import org.reactivestreams.Publisher;

public interface Statement {
    public Statement add();

    public Statement bind(int var1, Object var2);

    public Statement bind(String var1, Object var2);

    public Statement bindNull(int var1, Class<?> var2);

    public Statement bindNull(String var1, Class<?> var2);

    public Publisher<? extends Result> execute();

    default public Statement returnGeneratedValues(String ... columns) {
        Assert.requireNonNull(columns, "columns must not be null");
        return this;
    }

    default public Statement fetchSize(int rows) {
        return this;
    }
}

