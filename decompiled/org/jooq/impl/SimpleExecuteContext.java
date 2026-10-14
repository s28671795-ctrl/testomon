/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Routine;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.Tools;

final class SimpleExecuteContext
extends AbstractScope
implements ExecuteContext {
    SimpleExecuteContext(Configuration configuration, Map<Object, Object> data) {
        super(configuration, data);
    }

    @Override
    public final ConverterContext converterContext() {
        return Tools.converterContext(this.configuration);
    }

    @Override
    public final Connection connection() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final ExecuteType type() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query query() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void query(Query query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query originalQuery() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query transformedQuery() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final ExecuteContext.BatchMode batchMode() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query[] batchQueries() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void batchQueries(Query ... query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query[] originalBatchQueries() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Query[] transformedBatchQueries() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Routine<?> routine() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String sql() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void sql(String sql) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String[] batchSQL() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void connectionProvider(ConnectionProvider connectionProvider) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final PreparedStatement statement() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void statement(PreparedStatement statement) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final int statementExecutionCount() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final ResultSet resultSet() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void resultSet(ResultSet resultSet) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final int recordLevel() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Record record() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void record(Record record) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final int rows() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void rows(int rows) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final int[] batchRows() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final int resultLevel() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final Result<?> result() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void result(Result<?> result) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final RuntimeException exception() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void exception(RuntimeException e) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final SQLException sqlException() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void sqlException(SQLException e) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final SQLWarning sqlWarning() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void sqlWarning(SQLWarning e) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String[] serverOutput() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final void serverOutput(String[] output) {
        throw new UnsupportedOperationException("Not implemented");
    }
}

