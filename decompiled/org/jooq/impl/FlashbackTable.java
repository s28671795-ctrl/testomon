/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.VersionsBetweenAndStep;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Alias;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class FlashbackTable<R extends Record, T>
extends AbstractDelegatingTable<R>
implements VersionsBetweenAndStep<R, T>,
QOM.UNotYetImplemented {
    private final FlashbackType type;
    private final Field<?> asOf;
    private final QueryPart minvalue;
    private QueryPart maxvalue;

    FlashbackTable(AbstractTable<R> table, Field<?> asOf, QueryPart minvalue, FlashbackType type) {
        super(table);
        this.asOf = asOf;
        this.minvalue = minvalue != null ? minvalue : Keywords.K_MINVALUE;
        this.type = type;
    }

    private FlashbackTable(Table<R> table, FlashbackTable<R, T> o) {
        this((AbstractTable)table, o.asOf, o.minvalue, o.type);
        this.maxvalue = o.maxvalue;
    }

    final <O extends Record> FlashbackTable<O, T> construct(AbstractTable<O> newDelegate) {
        FlashbackTable<O, T> r = new FlashbackTable<O, T>(newDelegate, this.asOf, this.minvalue, this.type);
        r.maxvalue = this.maxvalue;
        return r;
    }

    @Override
    public final Table<R> and(T value) {
        return this.and((Field<? extends T>)DSL.val(value));
    }

    @Override
    public final Table<R> and(Field<? extends T> field2) {
        this.maxvalue = field2;
        return this;
    }

    @Override
    public final Table<R> andMaxvalue() {
        this.maxvalue = Keywords.K_MAXVALUE;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        Table aliased = Tools.aliased(this.delegate);
        if (aliased != null) {
            Alias alias = Tools.alias(this.delegate);
            ctx.visit(new Alias<FlashbackTable>(new FlashbackTable(aliased, this), this, alias.alias, alias.fieldAliases, alias.wrapInParentheses));
        } else {
            ctx.visit(this.delegate);
            if (this.asOf != null) {
                ctx.sql(' ').visit(Keywords.K_AS_OF).sql(' ').visit(this.type.keyword()).sql(' ').visit(this.asOf);
            } else {
                ctx.sql(' ').visit(Keywords.K_VERSIONS).sql(' ').visit(Keywords.K_BETWEEN).sql(' ').visit(this.type.keyword()).sql(' ').visit(this.minvalue).sql(' ').visit(Keywords.K_AND).sql(' ').visit(this.maxvalue);
            }
        }
    }

    @Pro
    static enum FlashbackType {
        SCN,
        TIMESTAMP;


        Keyword keyword() {
            if (this == SCN) {
                return Keywords.K_SCN;
            }
            if (this == TIMESTAMP) {
                return Keywords.K_TIMESTAMP;
            }
            throw new IllegalStateException("Unsupported FlashbackType: " + String.valueOf((Object)this));
        }
    }
}

