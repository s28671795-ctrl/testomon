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

final class LeftJoin
extends JoinTable<LeftJoin>
implements QOM.LeftJoin<Record> {
    LeftJoin(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.LEFT_OUTER_JOIN);
    }

    LeftJoin(TableLike<?> lhs, TableLike<?> rhs, Collection<? extends Field<?>> lhsPartitionBy) {
        super(lhs, rhs, JoinType.LEFT_OUTER_JOIN, lhsPartitionBy);
    }

    @Override
    LeftJoin construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return o != null ? (LeftJoin)((LeftJoin)new LeftJoin(table1, table2, partitionBy1).partitionBy0(partitionBy2)).on(o) : (LeftJoin)((LeftJoin)new LeftJoin(table1, table2, partitionBy1).partitionBy0(partitionBy2)).using((Collection)u);
    }
}

