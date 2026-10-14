/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.r2dbc;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionMetadata;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import org.reactivestreams.Publisher;

public class DefaultConnection
implements Connection {
    private final Connection delegate;

    public DefaultConnection(Connection delegate) {
        this.delegate = delegate;
    }

    public Connection getDelegate() {
        return this.delegate;
    }

    @Override
    public Publisher<Void> beginTransaction() {
        return this.getDelegate().beginTransaction();
    }

    @Override
    public Publisher<Void> beginTransaction(TransactionDefinition definition) {
        return this.getDelegate().beginTransaction(definition);
    }

    @Override
    public Publisher<Void> close() {
        return this.getDelegate().close();
    }

    @Override
    public Publisher<Void> commitTransaction() {
        return this.getDelegate().commitTransaction();
    }

    @Override
    public Batch createBatch() {
        return this.getDelegate().createBatch();
    }

    @Override
    public Publisher<Void> createSavepoint(String name) {
        return this.getDelegate().createSavepoint(name);
    }

    @Override
    public Statement createStatement(String sql) {
        return this.getDelegate().createStatement(sql);
    }

    @Override
    public boolean isAutoCommit() {
        return this.getDelegate().isAutoCommit();
    }

    @Override
    public ConnectionMetadata getMetadata() {
        return this.getDelegate().getMetadata();
    }

    @Override
    public IsolationLevel getTransactionIsolationLevel() {
        return this.getDelegate().getTransactionIsolationLevel();
    }

    @Override
    public Publisher<Void> releaseSavepoint(String name) {
        return this.getDelegate().releaseSavepoint(name);
    }

    @Override
    public Publisher<Void> rollbackTransaction() {
        return this.getDelegate().rollbackTransaction();
    }

    @Override
    public Publisher<Void> rollbackTransactionToSavepoint(String name) {
        return this.getDelegate().rollbackTransactionToSavepoint(name);
    }

    @Override
    public Publisher<Void> setAutoCommit(boolean autoCommit) {
        return this.getDelegate().setAutoCommit(autoCommit);
    }

    @Override
    public Publisher<Void> setLockWaitTimeout(Duration timeout2) {
        return this.getDelegate().setLockWaitTimeout(timeout2);
    }

    @Override
    public Publisher<Void> setStatementTimeout(Duration timeout2) {
        return this.getDelegate().setStatementTimeout(timeout2);
    }

    @Override
    public Publisher<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel) {
        return this.getDelegate().setTransactionIsolationLevel(isolationLevel);
    }

    @Override
    public Publisher<Boolean> validate(ValidationDepth depth) {
        return this.getDelegate().validate(depth);
    }
}

