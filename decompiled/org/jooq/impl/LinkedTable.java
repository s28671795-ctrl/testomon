/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Link;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.QOM;

@Pro
final class LinkedTable<R extends Record>
extends AbstractDelegatingTable<R>
implements QOM.LinkedTable<R> {
    private final Link link;

    LinkedTable(AbstractTable<R> delegate, Link link) {
        super(delegate);
        this.link = link;
    }

    @Override
    final <O extends Record> LinkedTable<O> construct(AbstractTable<O> newDelegate) {
        return new LinkedTable<O>(newDelegate, this.link);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.delegate).sql('@').visit(this.link);
    }

    @Override
    public final Table<R> $table() {
        return this.delegate;
    }

    @Override
    public final <O extends Record> LinkedTable<O> $table(Table<O> newTable) {
        return this.construct((AbstractTable)newTable);
    }

    @Override
    public final Link $link() {
        return this.link;
    }

    @Override
    public final LinkedTable<R> $link(Link newLink) {
        return new LinkedTable<R>(this.delegate, newLink);
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.delegate, this.link);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.delegate, this.link, LinkedTable::new, replacer);
    }
}

