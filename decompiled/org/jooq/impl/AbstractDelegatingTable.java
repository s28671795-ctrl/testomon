/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.TableAlias;

abstract class AbstractDelegatingTable<R extends Record>
extends AbstractTable<R> {
    final AbstractTable<R> delegate;

    AbstractDelegatingTable(AbstractTable<R> delegate) {
        super(delegate.getOptions(), delegate.getQualifiedName(), delegate.getSchema());
        this.delegate = delegate;
    }

    abstract <O extends Record> AbstractDelegatingTable<O> construct(AbstractTable<O> var1);

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.delegate.getRecordType();
    }

    @Override
    public final Table<R> as(Name alias) {
        return this.construct(new TableAlias<R>(this.delegate, alias));
    }

    @Override
    public final Table<R> as(Name alias, Name ... fieldAliases) {
        return this.construct(new TableAlias<R>(this.delegate, alias, fieldAliases));
    }

    @Override
    public final List<ForeignKey<R, ?>> getReferences() {
        return this.delegate.getReferences();
    }

    @Override
    final FieldsImpl<R> fields0() {
        return this.delegate.fields0();
    }

    @Override
    public final Table<R> $aliased() {
        return this.construct((AbstractTable)this.delegate.$aliased());
    }

    @Override
    public final Name $alias() {
        return this.delegate.$alias();
    }
}

