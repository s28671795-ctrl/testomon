/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class HintedTable<R extends Record>
extends AbstractDelegatingTable<R>
implements QOM.HintedTable<R> {
    private final Keyword keywords;
    private final QueryPartList<Name> arguments;

    HintedTable(AbstractTable<R> delegate, String keywords, String ... arguments) {
        this(delegate, keywords, (QueryPartList<Name>)new QueryPartList((QueryPart[])Tools.names(arguments)));
    }

    HintedTable(AbstractTable<R> delegate, String keywords, QueryPartList<Name> arguments) {
        this(delegate, DSL.keyword(keywords), arguments);
    }

    HintedTable(AbstractTable<R> delegate, Keyword keywords, String ... arguments) {
        this(delegate, keywords, (QueryPartList<Name>)new QueryPartList((QueryPart[])Tools.names(arguments)));
    }

    HintedTable(AbstractTable<R> delegate, Keyword keywords, QueryPartList<Name> arguments) {
        super(delegate);
        this.keywords = keywords;
        this.arguments = arguments;
    }

    @Override
    final <O extends Record> HintedTable<O> construct(AbstractTable<O> newDelegate) {
        return new HintedTable<O>(newDelegate, this.keywords, this.arguments);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.delegate).sql(' ').visit(this.keywords).sql(" (").visit(this.arguments).sql(')');
    }

    @Override
    public final Table<R> $table() {
        return this.delegate;
    }

    @Override
    public final <O extends Record> HintedTable<O> $table(Table<O> newTable) {
        return this.construct((AbstractTable)newTable);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.delegate);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.delegate, abstractTable -> this.construct((AbstractTable)abstractTable), replacer);
    }
}

