/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.QOM;

final class NoAutoAlias<R extends Record>
extends AbstractDelegatingTable<R>
implements QOM.UTransient {
    NoAutoAlias(AbstractTable<R> delegate) {
        super(delegate);
    }

    static final <R extends Record> Table<R> noAutoAlias(Table<R> table) {
        return table instanceof AutoAlias ? new NoAutoAlias<R>(table) : table;
    }

    @Override
    final <O extends Record> NoAutoAlias<O> construct(AbstractTable<O> newDelegate) {
        return new NoAutoAlias<O>(newDelegate);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.delegate);
    }
}

