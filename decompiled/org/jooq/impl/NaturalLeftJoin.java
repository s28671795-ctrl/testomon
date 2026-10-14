/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Collections;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.JoinTable;
import org.jooq.impl.QOM;

final class NaturalLeftJoin
extends JoinTable<NaturalLeftJoin>
implements QOM.NaturalLeftJoin<Record> {
    NaturalLeftJoin(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.NATURAL_LEFT_OUTER_JOIN, Collections.emptyList());
    }

    NaturalLeftJoin(TableLike<?> lhs, TableLike<?> rhs, Collection<? extends Field<?>> lhsPartitionBy) {
        super(lhs, rhs, JoinType.NATURAL_LEFT_OUTER_JOIN, lhsPartitionBy);
    }

    @Override
    NaturalLeftJoin construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return (NaturalLeftJoin)new NaturalLeftJoin(table1, table2, partitionBy1).partitionBy0(partitionBy2);
    }
}

