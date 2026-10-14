/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Query;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;

public interface CloseableQuery
extends Query,
AutoCloseable {
    @Override
    @NotNull
    public CloseableQuery bind(String var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public CloseableQuery bind(int var1, Object var2) throws IllegalArgumentException, DataTypeException;

    @Override
    @NotNull
    public CloseableQuery poolable(boolean var1);

    @Override
    @NotNull
    public CloseableQuery queryTimeout(int var1);

    @Override
    @NotNull
    public CloseableQuery keepStatement(boolean var1);

    @Override
    public void close() throws DataAccessException;
}

