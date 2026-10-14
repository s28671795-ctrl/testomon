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
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.exception.DetachedException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.ParsingConnection;
import org.jooq.impl.R2DBC;
import org.jooq.impl.Tools;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

final class ParsingConnectionFactory
implements ConnectionFactory {
    final Configuration configuration;

    ParsingConnectionFactory(Configuration configuration) {
        if (configuration.connectionFactory() instanceof NoConnectionFactory) {
            throw new DetachedException("ConnectionProvider did not provide an R2DBC ConnectionFactory");
        }
        this.configuration = configuration.deriveSettings(s2 -> R2DBC.setParamType(configuration.dialect(), s2));
    }

    @Override
    public final Publisher<? extends Connection> create() {
        return subscriber -> this.configuration.connectionFactory().create().subscribe(new ParsingR2DBCConnectionSubscriber(subscriber));
    }

    @Override
    public final ConnectionFactoryMetadata getMetadata() {
        return this.configuration.connectionFactory().getMetadata();
    }

    private final class ParsingR2DBCConnectionSubscriber
    implements Subscriber<Connection> {
        private final Subscriber<? super Connection> subscriber;

        private ParsingR2DBCConnectionSubscriber(Subscriber<? super Connection> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public final void onSubscribe(Subscription s2) {
            this.subscriber.onSubscribe(s2);
        }

        @Override
        public final void onNext(Connection c) {
            this.subscriber.onNext(new ParsingR2DBCConnection(c));
        }

        @Override
        public final void onError(Throwable t2) {
            this.subscriber.onError(t2);
        }

        @Override
        public final void onComplete() {
            this.subscriber.onComplete();
        }
    }

    private final class ParsingR2DBCStatement
    implements Statement {
        private final Connection delegate;
        private final String input;
        private final List<List<Param<?>>> params;

        private ParsingR2DBCStatement(Connection delegate, String input) {
            this.delegate = delegate;
            this.input = input;
            this.params = new ArrayList();
            this.params.add(new ArrayList());
        }

        private final List<Param<?>> list(int index) {
            List<Param<?>> list = this.params.get(this.params.size() - 1);
            int reserve = index + 1 - list.size();
            if (reserve > 0) {
                list.addAll(Collections.nCopies(reserve, null));
            }
            return list;
        }

        @Override
        public final Statement add() {
            this.params.add(new ArrayList());
            return this;
        }

        @Override
        public final Statement bind(int index, Object value) {
            this.list(index).set(index, DSL.val(value));
            return this;
        }

        @Override
        public final Statement bind(String name, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Statement bindNull(int index, Class<?> type) {
            this.list(index).set(index, DSL.val(null, type));
            return this;
        }

        @Override
        public final Statement bindNull(String name, Class<?> type) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Publisher<? extends Result> execute() {
            Statement statement = null;
            for (List<Param<?>> p : this.params) {
                if (statement != null) {
                    statement.add();
                }
                DefaultRenderContext.Rendered rendered = ParsingConnection.translate(ParsingConnectionFactory.this.configuration, this.input, p.toArray(Tools.EMPTY_PARAM));
                if (statement == null) {
                    statement = this.delegate.createStatement(rendered.sql);
                }
                int j = 0;
                for (Param param2 : rendered.bindValues) {
                    if (param2.getValue() == null) {
                        statement.bindNull(j++, param2.getType());
                        continue;
                    }
                    statement.bind(j++, param2.getValue());
                }
            }
            return statement.execute();
        }
    }

    private final class ParsingR2DBCBatch
    implements Batch {
        private final Batch delegate;

        private ParsingR2DBCBatch(Batch b) {
            this.delegate = b;
        }

        @Override
        public final Batch add(String sql) {
            this.delegate.add(ParsingConnection.translate((Configuration)ParsingConnectionFactory.this.configuration, (String)sql, new Param[0]).sql);
            return this;
        }

        @Override
        public final Publisher<? extends Result> execute() {
            return this.delegate.execute();
        }
    }

    private final class ParsingR2DBCConnection
    implements Connection {
        private final Connection delegate;

        private ParsingR2DBCConnection(Connection delegate) {
            this.delegate = delegate;
        }

        @Override
        public final Batch createBatch() {
            return new ParsingR2DBCBatch(this.delegate.createBatch());
        }

        @Override
        public final Statement createStatement(String sql) {
            return new ParsingR2DBCStatement(this.delegate, sql);
        }

        @Override
        public final Publisher<Void> beginTransaction() {
            return this.delegate.beginTransaction();
        }

        @Override
        public final Publisher<Void> beginTransaction(TransactionDefinition definition) {
            return this.delegate.beginTransaction(definition);
        }

        @Override
        public final Publisher<Void> close() {
            return this.delegate.close();
        }

        @Override
        public final Publisher<Void> commitTransaction() {
            return this.delegate.commitTransaction();
        }

        @Override
        public final Publisher<Void> createSavepoint(String name) {
            return this.delegate.createSavepoint(name);
        }

        @Override
        public final boolean isAutoCommit() {
            return this.delegate.isAutoCommit();
        }

        @Override
        public final ConnectionMetadata getMetadata() {
            return this.delegate.getMetadata();
        }

        @Override
        public final IsolationLevel getTransactionIsolationLevel() {
            return this.delegate.getTransactionIsolationLevel();
        }

        @Override
        public final Publisher<Void> releaseSavepoint(String name) {
            return this.delegate.releaseSavepoint(name);
        }

        @Override
        public final Publisher<Void> rollbackTransaction() {
            return this.delegate.rollbackTransaction();
        }

        @Override
        public final Publisher<Void> rollbackTransactionToSavepoint(String name) {
            return this.delegate.rollbackTransactionToSavepoint(name);
        }

        @Override
        public final Publisher<Void> setAutoCommit(boolean autoCommit) {
            return this.delegate.setAutoCommit(autoCommit);
        }

        @Override
        public final Publisher<Void> setTransactionIsolationLevel(IsolationLevel isolationLevel) {
            return this.delegate.setTransactionIsolationLevel(isolationLevel);
        }

        @Override
        public final Publisher<Boolean> validate(ValidationDepth depth) {
            return this.delegate.validate(depth);
        }

        @Override
        public final Publisher<Void> setLockWaitTimeout(Duration timeout2) {
            return this.delegate.setLockWaitTimeout(timeout2);
        }

        @Override
        public final Publisher<Void> setStatementTimeout(Duration timeout2) {
            return this.delegate.setStatementTimeout(timeout2);
        }
    }
}

