/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.LazyName;

final class TableDataType<R extends Record>
extends DefaultDataType<R> {
    final Table<R> table;

    TableDataType(Table<R> table) {
        super(SQLDialect.DEFAULT, table.getRecordType(), new LazyName(() -> TableDataType.lazyName(table::getSchema, table::getUnqualifiedName)));
        this.table = table;
    }
}

