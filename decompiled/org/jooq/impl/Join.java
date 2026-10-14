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

final class Join
extends JoinTable<Join>
implements QOM.Join<Record> {
    Join(TableLike<?> lhs, TableLike<?> rhs) {
        super(lhs, rhs, JoinType.JOIN);
    }

    @Override
    Join construct(Table<?> table1, Collection<? extends Field<?>> partitionBy1, Collection<? extends Field<?>> partitionBy2, Table<?> table2, Condition o, Collection<? extends Field<?>> u) {
        return o != null ? (Join)new Join(table1, table2).on(o) : (Join)new Join(table1, table2).using((Collection)u);
    }
}

