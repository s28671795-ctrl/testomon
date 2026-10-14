/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.CloseableQuery;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

abstract class AbstractDelegatingQuery<R extends Record, Q extends CloseableQuery>
extends AbstractQueryPart
implements CloseableQuery,
QOM.UProxy<Q> {
    private final Q delegate;

    AbstractDelegatingQuery(Q delegate) {
        this.delegate = delegate;
    }

    @Override
    public final Configuration configuration() {
        return this.delegate.configuration();
    }

    @Override
    public final List<Object> getBindValues() {
        return this.delegate.getBindValues();
    }

    @Override
    public final Map<String, Param<?>> getParams() {
        return this.delegate.getParams();
    }

    @Override
    public final Param<?> getParam(String name) {
        return this.delegate.getParam(name);
    }

    @Override
    public final void accept(Context<?> context) {
        context.visit((QueryPart)this.delegate);
    }

    @Override
    public final String getSQL() {
        return this.delegate.getSQL();
    }

    @Override
    public final String getSQL(ParamType paramType) {
        return this.delegate.getSQL(paramType);
    }

    @Override
    public final void attach(Configuration configuration) {
        this.delegate.attach(configuration);
    }

    @Override
    public final void detach() {
        this.delegate.detach();
    }

    @Override
    public final int execute() {
        return this.delegate.execute();
    }

    @Override
    public final CompletionStage<Integer> executeAsync() {
        return this.delegate.executeAsync();
    }

    @Override
    public final CompletionStage<Integer> executeAsync(Executor executor2) {
        return this.delegate.executeAsync(executor2);
    }

    @Override
    public final boolean isExecutable() {
        return this.delegate.isExecutable();
    }

    public final Q bind(String param2, Object value) {
        return (Q)this.delegate.bind(param2, value);
    }

    public final Q bind(int index, Object value) {
        return (Q)this.delegate.bind(index, value);
    }

    public final Q poolable(boolean poolable) {
        return (Q)this.delegate.poolable(poolable);
    }

    public final Q queryTimeout(int timeout2) {
        return (Q)this.delegate.queryTimeout(timeout2);
    }

    public final Q keepStatement(boolean keepStatement) {
        return (Q)this.delegate.keepStatement(keepStatement);
    }

    @Override
    public final void close() {
        this.delegate.close();
    }

    @Override
    public final void cancel() {
        this.delegate.cancel();
    }

    final Q getDelegate() {
        return this.delegate;
    }

    @Override
    public final Q $delegate() {
        return this.getDelegate();
    }
}

