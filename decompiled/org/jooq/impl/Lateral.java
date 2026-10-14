/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class Lateral<R extends Record>
extends AbstractTable<R>
implements QOM.Lateral<R> {
    private final Table<R> table;

    Lateral(Table<R> table) {
        super(TableOptions.expression(), table.getQualifiedName(), table.getSchema());
        this.table = table;
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.table.getRecordType();
    }

    @Override
    public final Table<R> as(Name alias) {
        return new Lateral<R>(this.table.as(alias));
    }

    @Override
    public final Table<R> as(Name alias, Name ... fieldAliases) {
        return new Lateral<R>(this.table.as(alias, fieldAliases));
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_LATERAL).sql(' ').visit(this.table);
    }

    @Override
    final FieldsImpl<R> fields0() {
        return new FieldsImpl(this.table.fields());
    }

    @Override
    public final Table<R> $aliased() {
        return DSL.lateral((TableLike)((QOM.Aliasable)((Object)this.table)).$aliased());
    }

    @Override
    public final Name $alias() {
        return ((QOM.Aliasable)((Object)this.table)).$alias();
    }

    @Override
    public final Function1<? super Table<R>, ? extends QOM.Lateral<R>> $constructor() {
        return t2 -> new Lateral(t2);
    }

    @Override
    public final Table<R> $arg1() {
        return this.table;
    }
}

