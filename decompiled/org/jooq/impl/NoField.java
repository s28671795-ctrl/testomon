/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class NoField<T>
extends AbstractField<T>
implements SortField<T>,
QOM.UEmptyField<T> {
    static final NoField<?> INSTANCE = new NoField<Object>(SQLDataType.OTHER);

    NoField(DataType<T> type) {
        super(Names.N_NULL, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(DSL.inline(null, this.getDataType()));
    }

    @Override
    public final SortOrder getOrder() {
        return SortOrder.DEFAULT;
    }

    @Override
    public final Field<T> $field() {
        return this;
    }

    @Override
    public final <U> SortField<U> $field(Field<U> newField) {
        return newField.sortDefault();
    }

    @Override
    public final SortOrder $sortOrder() {
        return SortOrder.DEFAULT;
    }

    @Override
    public final SortField<T> $sortOrder(SortOrder newOrder) {
        return this;
    }

    @Override
    public final QOM.NullOrdering $nullOrdering() {
        return null;
    }

    @Override
    public final SortField<T> $nullOrdering(QOM.NullOrdering newOrdering) {
        return this;
    }
}

