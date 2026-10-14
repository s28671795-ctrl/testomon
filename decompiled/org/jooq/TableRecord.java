/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.QualifiedRecord;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.exception.DataAccessException;

public interface TableRecord<R extends TableRecord<R>>
extends QualifiedRecord<R> {
    @NotNull
    public Table<R> getTable();

    @NotNull
    public R original();

    @Support
    public int insert() throws DataAccessException;

    @Support
    public int insert(Field<?> ... var1) throws DataAccessException;

    @Support
    public int insert(Collection<? extends Field<?>> var1) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> var1) throws DataAccessException;

    @Support
    @NotNull
    public <O extends UpdatableRecord<O>> Table<O> parent(ForeignKey<R, O> var1);
}

