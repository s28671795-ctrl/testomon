/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.ConnectionMetadata;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import org.jooq.exception.DetachedException;
import org.jooq.impl.R2DBC;
import org.reactivestreams.Publisher;

final class DefaultConnectionFactory
implements ConnectionFactory {
    Connection connection;
    final boolean finalize;
    final boolean nested;
    final AtomicInteger savepoints = new AtomicInteger();

    DefaultConnectionFactory(Connection connection) {
        this(connection, false, true);
    }

    DefaultConnectionFactory(Connection connection, boolean finalize, boolean nested) {
        this.connection = connection;
        this.finalize = finalize;
        this.nested = nested;
    }

    final Connection connectionOrThrow() {
        if (this.connection == null) {
            throw new DetachedException("R2DBC Connection not available or already closed");
        }
        return this.connection;
    }

    @Override
    public final Publisher<? extends Connection> create() {
        return s2 -> s2.onSubscribe(R2DBC.AbstractSubscription.onRequest(s2, x -> {
            x.onNext(new NonClosingConnection());
            x.onComplete();
        }));
    }

    @Override
    public final ConnectionFactoryMetadata getMetadata() {
        return () -> this.connectionOrThrow().getMetadata().getDatabaseProductName();
    }

    final class NonClosingConnection
    implements Connection {
        NonClosingConnection() {
        }

        private <T> T nest(IntSupplier level, Supplier<T> toplevelAction, IntFunction<T> nestedAction) {
            if (DefaultConnectionFactory.this.nested) {
                return nestedAction.apply(level.getAsInt());
            }
            return toplevelAction.get();
        }

        private String savepoint(int i) {
            return "S" + i;
        }

        @Override
        public Publisher<Void> beginTransaction() {
            return this.nest(DefaultConnectionFactory.this.savepoints::getAndIncrement, () -> DefaultConnectionFactory.this.connectionOrThrow().beginTransaction(), i -> DefaultConnectionFactory.this.connectionOrThrow().createSavepoint(this.savepoint(i)));
        }

        @Override
        public Publisher<Void> beginTransaction(TransactionDefinition definition) {
            return this.nest(DefaultConnectionFactory.this.savepoints::getAndIncrement, () -> DefaultConnectionFactory.this.connectionOrThrow().beginTransaction(definition), i -> DefaultConnectionFactory.this.connectionOrThrow().createSavepoint(this.savepoint(i)));
        }

        @Override
        public Publisher<Void> close() {
            return s2 -> s2.onSubscribe(R2DBC.AbstractSubscription.onRequest(s2, x -> x.onComplete()));
        }

        @Override
        public Publisher<Void> commitTransaction() {
            return this.nest(DefaultConnectionFactory.this.savepoints::decrementAndGet, () -> DefaultConnectionFactory.this.connectionOrThrow().commitTransaction(), i -> DefaultConnectionFactory.this.connectionOrThrow().releaseSavepoint(this.savepoint(i)));
        }

        @Override
        public Batch createBatch() {
            return DefaultConnectionFactory.this.connectionOrThrow().createBatch();
        }

        @Override
        public Publisher<Void> createSavepoint(String name) {
            return DefaultConnectionFactory.this.connectionOrThrow().createSavepoint(name);
        }

        @Override
        public Statement createStatement(String sql) {
            return DefaultConnectionFactory.this.connectionOrThrow().createStatement(sql);
        }

        @Override
        public boolean isAutoCommit() {
            return DefaultConnectionFactory.this.connectionOrThrow().isAutoCommit();
        }

        @Override
        public ConnectionMetadata getMetadata() {
            return DefaultConnectionFactory.this.connectionOrThrow().getMetadata();
        }

        @Override
        public IsolationLevel getTransactionIsolationLevel() {
            return DefaultConnectionFactory.this.connectionOrThrow().getTransactionIsolationLevel();
        }

        @Override
        public Publisher<Void> releaseSavepoint(String name) {
            return DefaultConnectionFactory.this.connectionOrThrow().releaseSavepoint(name);
        }

        @Override
        public Publisher<Void> rollbackTransaction() {
            return this.nest(DefaultConnectionFactory.this.savepoints::decrementAndGet, () -> DefaultConnectionFactory.this.connectionOrThrow().rollbackTransaction(), i -> DefaultConnectionFactory.this.connectionOrThrow().rollbackTransactionToSavepoint(this.savepoint(i)));
        }

        @Override
        public Publisher<Void> rollbackTransactionToSavepoint(String name) {
            return DefaultConnectionFactory.this.connectionOrThrow().rollbackTransactionToSavepoint(name);
        }

        @Override
        public Publisher<Void> setAutoCommit(boolean autoCommit) {
            return DefaultConnectionFactory.this.connectionOrThrow().setAutoCommit(autoCommit);
        }

        @Override
        public Publisher<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel) {
            return DefaultConnectionFactory.this.connectionOrThrow().setTransactionIsolationLevel(isolationLevel);
        }

        @Override
        public Publisher<Boolean> validate(ValidationDepth depth) {
            return DefaultConnectionFactory.this.connectionOrThrow().validate(depth);
        }

        @Override
        public Publisher<Void> setLockWaitTimeout(Duration timeout2) {
            return DefaultConnectionFactory.this.connectionOrThrow().setLockWaitTimeout(timeout2);
        }

        @Override
        public Publisher<Void> setStatementTimeout(Duration timeout2) {
            return DefaultConnectionFactory.this.connectionOrThrow().setStatementTimeout(timeout2);
        }
    }
}

