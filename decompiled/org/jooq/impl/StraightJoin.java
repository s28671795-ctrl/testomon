/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.JoinTable;
import org.jooq.impl.QOM;

final class StraightJoin
extends JoinTable<StraightJoin>
implements QOM.StraightJoin<Record> {
    StraightJoin(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.STRAIGHT_JOIN);
    }

    @Override
    StraightJoin construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return o != null ? (StraightJoin)new StraightJoin(table1, table2).on(o) : (StraightJoin)new StraightJoin(table1, table2).using((Collection)u);
    }
}

