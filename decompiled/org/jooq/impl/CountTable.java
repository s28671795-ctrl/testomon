/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultAggregateFunction;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class CountTable
extends AbstractAggregateFunction<Integer>
implements QOM.CountTable {
    private final Table<?> table;

    CountTable(Table<?> table, boolean distinct2) {
        super(distinct2, "count", SQLDataType.INTEGER, DSL.field(DSL.name(table.getName())));
        this.table = table;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                super.accept(ctx);
                break;
            }
            default: {
                UniqueKey<?> pk = this.table.getPrimaryKey();
                if (pk != null) {
                    ctx.visit(new DefaultAggregateFunction<Integer>(this.distinct, "count", SQLDataType.INTEGER, this.table.fields(pk.getFieldsArray())));
                    break;
                }
                super.accept(ctx);
                break;
            }
        }
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$table());
        return super.$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table(), this.distinct, CountTable::new, replacer);
    }
}

