/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Key;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;

public interface ForeignKey<R extends Record, O extends Record>
extends Key<R> {
    @NotNull
    public UniqueKey<O> getKey();

    @NotNull
    public List<TableField<O, ?>> getKeyFields();

    @NotNull
    public @NotNull TableField<O, ?> @NotNull [] getKeyFieldsArray();

    @Nullable
    @Blocking
    public O fetchParent(R var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Result<O> fetchParents(R ... var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Result<O> fetchParents(Collection<? extends R> var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Result<R> fetchChildren(O var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Result<R> fetchChildren(O ... var1) throws DataAccessException;

    @NotNull
    @Blocking
    public Result<R> fetchChildren(Collection<? extends O> var1) throws DataAccessException;

    @NotNull
    public Table<O> parent(R var1);

    @NotNull
    public Table<O> parents(R ... var1);

    @NotNull
    public Table<O> parents(Collection<? extends R> var1);

    @NotNull
    public Table<R> children(O var1);

    @NotNull
    public Table<R> children(O ... var1);

    @NotNull
    public Table<R> children(Collection<? extends O> var1);
}

