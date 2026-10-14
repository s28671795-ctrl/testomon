/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterContext;
import org.jooq.ExecuteType;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Routine;
import org.jooq.Scope;

public interface ExecuteContext
extends Scope {
    public ConverterContext converterContext();

    public Connection connection();

    @NotNull
    public ExecuteType type();

    @Nullable
    public Query query();

    @Nullable
    @Pro
    public Query originalQuery();

    @Nullable
    @Pro
    public Query transformedQuery();

    @Pro
    public void query(Query var1);

    @NotNull
    public BatchMode batchMode();

    @NotNull
    public @NotNull Query @NotNull [] batchQueries();

    @Pro
    @NotNull
    public @NotNull Query @NotNull [] originalBatchQueries();

    @Pro
    @NotNull
    public @NotNull Query @NotNull [] transformedBatchQueries();

    @Pro
    public void batchQueries(Query ... var1);

    @Nullable
    public Routine<?> routine();

    @Nullable
    public String sql();

    public void sql(String var1);

    @NotNull
    public @NotNull String @NotNull [] batchSQL();

    public void connectionProvider(ConnectionProvider var1);

    @Nullable
    public PreparedStatement statement();

    public void statement(PreparedStatement var1);

    public int statementExecutionCount();

    @Nullable
    public ResultSet resultSet();

    public void resultSet(ResultSet var1);

    public int recordLevel();

    @Nullable
    public Record record();

    public void record(Record var1);

    public int rows();

    public void rows(int var1);

    public int @NotNull [] batchRows();

    public int resultLevel();

    @Nullable
    public Result<?> result();

    public void result(Result<?> var1);

    @Nullable
    public RuntimeException exception();

    public void exception(RuntimeException var1);

    @Nullable
    public SQLException sqlException();

    public void sqlException(SQLException var1);

    @Nullable
    public SQLWarning sqlWarning();

    public void sqlWarning(SQLWarning var1);

    @NotNull
    public @NotNull String @NotNull [] serverOutput();

    public void serverOutput(String[] var1);

    public static enum BatchMode {
        NONE,
        SINGLE,
        MULTIPLE;

    }
}

