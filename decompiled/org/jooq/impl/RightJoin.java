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

final class RightJoin
extends JoinTable<RightJoin>
implements QOM.RightJoin<Record> {
    RightJoin(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.RIGHT_OUTER_JOIN);
    }

    RightJoin(TableLike<?> lhs, TableLike<?> rhs, Collection<? extends Field<?>> lhsPartitionBy) {
        super(lhs, rhs, JoinType.RIGHT_OUTER_JOIN, lhsPartitionBy);
    }

    @Override
    RightJoin construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return o != null ? (RightJoin)((RightJoin)new RightJoin(table1, table2, partitionBy1).partitionBy0(partitionBy2)).on(o) : (RightJoin)((RightJoin)new RightJoin(table1, table2, partitionBy1).partitionBy0(partitionBy2)).using((Collection)u);
    }
}

