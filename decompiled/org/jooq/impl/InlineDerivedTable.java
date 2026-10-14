/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.DSL;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.QOM;

final class InlineDerivedTable<R extends Record>
extends DerivedTable<R> {
    final Table<R> table;
    final Condition condition;

    InlineDerivedTable(Table<R> table, Condition condition) {
        super(DSL.selectFrom(table).where(condition), table.getUnqualifiedName());
        this.table = table;
        this.condition = condition;
    }

    @Override
    final FieldsImpl<R> fields0() {
        return new FieldsImpl(this.table.as(this.table).fields());
    }

    @Override
    public final <X> X $traverse(Traverser<?, X> traverser) {
        return QOM.traverse(traverser, this, this.table, this.condition);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.table, this.condition, InlineDerivedTable::new, replacer);
    }
}

