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

final class LeftAntiJoin
extends JoinTable<LeftAntiJoin>
implements QOM.LeftAntiJoin<Record> {
    LeftAntiJoin(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.LEFT_ANTI_JOIN);
    }

    @Override
    LeftAntiJoin construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return o != null ? (LeftAntiJoin)new LeftAntiJoin(table1, table2).on(o) : (LeftAntiJoin)new LeftAntiJoin(table1, table2).using((Collection)u);
    }
}

