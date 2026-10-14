/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.AttachableQueryPart;
import org.jooq.CloseableQuery;
import org.jooq.Statement;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;

public interface Query
extends Statement,
AttachableQueryPart {
    @Blocking
    public int execute() throws DataAccessException;

    @NotNull
    public CompletionStage<Integer> executeAsync();

    @NotNull
    public CompletionStage<Integer> executeAsync(Executor var1);

    public boolean isExecutable();

    @NotNull
    public Query bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public Query bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @NotNull
    public Query poolable(boolean var1);

    @NotNull
    public Query queryTimeout(int var1);

    @NotNull
    public CloseableQuery keepStatement(boolean var1);

    public void cancel() throws DataAccessException;
}

