/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DataTypeProxy;
import org.jooq.impl.QOM;
import org.jooq.impl.ScopeMappable;

final class FieldProxy<T>
extends AbstractField<T>
implements TableField<Record, T>,
QOM.UProxy<Field<T>>,
ScopeMappable {
    private AbstractField<T> delegate;
    private final int position;
    Query scopeOwner;
    boolean resolved;

    FieldProxy(AbstractField<T> delegate, int position) {
        super(delegate.getQualifiedName(), new DataTypeProxy((AbstractDataType)delegate.getDataType()), delegate.getCommentPart(), delegate.getBinding());
        this.delegate = delegate;
        this.position = position;
    }

    final int position() {
        return this.position;
    }

    final void delegate(AbstractField<T> newDelegate) {
        this.resolve();
        this.delegate = newDelegate;
        ((DataTypeProxy)this.getDataType()).type((AbstractDataType)newDelegate.getDataType());
    }

    final FieldProxy<T> resolve() {
        this.resolved = true;
        this.scopeOwner = null;
        return this;
    }

    final void scopeOwner(Query query) {
        if (!this.resolved && this.scopeOwner == null) {
            this.scopeOwner = query;
        }
    }

    @Override
    public final Name getQualifiedName() {
        return this.delegate.getQualifiedName();
    }

    @Override
    public final int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public final boolean equals(Object that) {
        return this.delegate.equals(that);
    }

    @Override
    public final boolean declaresFields() {
        return this.delegate.declaresFields();
    }

    @Override
    public final boolean declaresTables() {
        return this.delegate.declaresTables();
    }

    @Override
    public final boolean declaresWindows() {
        return this.delegate.declaresWindows();
    }

    @Override
    public final boolean declaresCTE() {
        return this.delegate.declaresCTE();
    }

    @Override
    public final boolean generatesCast() {
        return this.delegate.generatesCast();
    }

    @Override
    public final boolean rendersContent(Context<?> ctx) {
        return this.delegate.rendersContent(ctx);
    }

    @Override
    public final void accept(Context<?> ctx) {
        this.delegate.accept(ctx);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return this.delegate.clauses(ctx);
    }

    @Override
    public final String toString() {
        return this.delegate.toString();
    }

    @Override
    public final Table<Record> getTable() {
        return this.delegate instanceof TableField ? ((TableField)((Object)this.delegate)).getTable() : null;
    }

    @Override
    public final Field<T> $delegate() {
        return this.delegate;
    }
}

