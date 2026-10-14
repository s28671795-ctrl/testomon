/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.CloseableResultQuery;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractAttachableQueryPart;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.ResultQueryTrait;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;

abstract class AbstractDMLQueryAsResultQuery<R extends Record, Q extends AbstractDMLQuery<R>>
extends AbstractQueryPart
implements ResultQueryTrait<R> {
    final Q delegate;
    final boolean returningResult;
    Table<?> coerceTable;
    Collection<? extends Field<?>> coerceFields;

    AbstractDMLQueryAsResultQuery(Q delegate, boolean returningResult) {
        this.delegate = delegate;
        this.returningResult = returningResult;
    }

    final Q getDelegate() {
        return this.delegate;
    }

    @Override
    public final Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        Field<?>[] f = this.getFields();
        return f != null ? f : ((AbstractDMLQuery)this.delegate).getFields(rs);
    }

    @Override
    public final Field<?>[] getFields() {
        if (this.coerceFields != null && !this.coerceFields.isEmpty()) {
            return this.coerceFields.toArray(Tools.EMPTY_FIELD);
        }
        return ((AbstractDMLQuery)this.delegate).returningResolvedAsterisks.toArray(Tools.EMPTY_FIELD);
    }

    @Override
    public final <X extends Record> CloseableResultQuery<X> coerce(Table<X> table) {
        this.coerceTable = table;
        return this.coerce(Arrays.asList(table.fields()));
    }

    @Override
    public final CloseableResultQuery<Record> coerce(Collection<? extends Field<?>> fields) {
        this.coerceFields = fields;
        return this;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        if (this.coerceTable != null) {
            return this.coerceTable.getRecordType();
        }
        if (this.returningResult) {
            return ((AbstractDMLQuery)this.delegate).getRecordType();
        }
        return ((AbstractDMLQuery)this.delegate).table().getRecordType();
    }

    @Override
    public final Result<R> fetch() {
        ((AbstractQuery)this.delegate).execute();
        return this.getResult();
    }

    @Override
    public final int execute() throws DataAccessException {
        return ((AbstractQuery)this.delegate).execute();
    }

    @Override
    public final CompletionStage<Integer> executeAsync() {
        return ((AbstractQuery)this.delegate).executeAsync();
    }

    @Override
    public final CompletionStage<Integer> executeAsync(Executor executor2) {
        return ((AbstractQuery)this.delegate).executeAsync(executor2);
    }

    @Override
    public final boolean isExecutable() {
        return ((AbstractQuery)this.delegate).isExecutable();
    }

    @Override
    public final String getSQL() {
        return ((AbstractAttachableQueryPart)this.delegate).getSQL();
    }

    @Override
    public final String getSQL(ParamType paramType) {
        return ((AbstractAttachableQueryPart)this.delegate).getSQL(paramType);
    }

    @Override
    public final List<Object> getBindValues() {
        return ((AbstractAttachableQueryPart)this.delegate).getBindValues();
    }

    @Override
    public final Map<String, Param<?>> getParams() {
        return ((AbstractAttachableQueryPart)this.delegate).getParams();
    }

    @Override
    public final Param<?> getParam(String name) {
        return ((AbstractAttachableQueryPart)this.delegate).getParam(name);
    }

    @Override
    public final void close() throws DataAccessException {
        ((AbstractQuery)this.delegate).close();
    }

    @Override
    public final void cancel() throws DataAccessException {
        ((AbstractQuery)this.delegate).cancel();
    }

    @Override
    public final void attach(Configuration configuration) {
        ((AbstractAttachableQueryPart)this.delegate).attach(configuration);
    }

    @Override
    public final void detach() {
        ((AbstractAttachableQueryPart)this.delegate).detach();
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit((QueryPart)this.delegate);
    }

    @Override
    public final Configuration configuration() {
        return ((AbstractAttachableQueryPart)this.delegate).configuration();
    }

    @Override
    public final Result<R> getResult() {
        return this.returningResult ? ((AbstractDMLQuery)this.delegate).getResult() : ((AbstractDMLQuery)this.delegate).getReturnedRecords();
    }

    @Override
    public final CloseableResultQuery<R> bind(String param2, Object value) throws IllegalArgumentException, DataTypeException {
        ((AbstractQuery)this.delegate).bind(param2, value);
        return this;
    }

    @Override
    public final CloseableResultQuery<R> bind(int index, Object value) throws IllegalArgumentException, DataTypeException {
        ((AbstractQuery)this.delegate).bind(index, value);
        return this;
    }

    @Override
    public final CloseableResultQuery<R> poolable(boolean poolable) {
        ((AbstractQuery)this.delegate).poolable(poolable);
        return this;
    }

    @Override
    public final CloseableResultQuery<R> queryTimeout(int timeout2) {
        ((AbstractQuery)this.delegate).queryTimeout(timeout2);
        return this;
    }

    @Override
    public final CloseableResultQuery<R> keepStatement(boolean keepStatement) {
        ((AbstractQuery)this.delegate).keepStatement(keepStatement);
        return this;
    }

    @Override
    public final CloseableResultQuery<R> maxRows(int rows) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> fetchSize(int rows) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> resultSetConcurrency(int resultSetConcurrency) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> resultSetType(int resultSetType) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> resultSetHoldability(int resultSetHoldability) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(Field<?> ... fields) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(int ... fieldIndexes) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(String ... fieldNames) {
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(Name ... fieldNames) {
        return this;
    }
}

