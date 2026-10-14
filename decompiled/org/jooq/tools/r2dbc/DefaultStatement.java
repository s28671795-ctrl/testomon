/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.r2dbc;

import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import org.reactivestreams.Publisher;

public class DefaultStatement
implements Statement {
    private final Statement delegate;

    public DefaultStatement(Statement delegate) {
        this.delegate = delegate;
    }

    public Statement getDelegate() {
        return this.delegate;
    }

    @Override
    public Statement add() {
        return this.getDelegate().add();
    }

    @Override
    public Statement bind(int index, Object value) {
        return this.getDelegate().bind(index, value);
    }

    @Override
    public Statement bind(String name, Object value) {
        return this.getDelegate().bind(name, value);
    }

    @Override
    public Statement bindNull(int index, Class<?> type) {
        return this.getDelegate().bindNull(index, type);
    }

    @Override
    public Statement bindNull(String name, Class<?> type) {
        return this.getDelegate().bindNull(name, type);
    }

    @Override
    public Publisher<? extends Result> execute() {
        return this.getDelegate().execute();
    }

    @Override
    public Statement returnGeneratedValues(String ... columns) {
        return this.getDelegate().returnGeneratedValues(columns);
    }

    @Override
    public Statement fetchSize(int rows) {
        return this.getDelegate().fetchSize(rows);
    }
}

