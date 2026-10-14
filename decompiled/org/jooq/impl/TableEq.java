/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.tools.StringUtils;

final class TableEq<R extends Record>
extends AbstractCondition
implements QOM.TableEq<R> {
    final Table<R> arg1;
    final Table<R> arg2;

    TableEq(Table<R> arg1, Table<R> arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.sql('(').visit(this.arg1).sql(" = ").visit(this.arg2).sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.row(this.arg1.fields()).eq(DSL.row(this.arg2.fields())));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.arg1).sql(".eq(").visit(this.arg2).sql(')');
    }

    @Override
    public final Table<R> $arg1() {
        return this.arg1;
    }

    @Override
    public final Table<R> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.TableEq<R> $arg1(Table<R> newValue) {
        return this.$constructor().apply(newValue, (Table<R>)this.$arg2());
    }

    @Override
    public final QOM.TableEq<R> $arg2(Table<R> newValue) {
        return this.$constructor().apply((Table<R>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Table<R>, ? super Table<R>, ? extends QOM.TableEq<R>> $constructor() {
        return (a1, a2) -> new TableEq(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.TableEq) {
            QOM.TableEq o = (QOM.TableEq)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

