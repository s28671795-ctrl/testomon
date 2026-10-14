/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

class RenamedTable<R extends Record>
extends TableImpl<R> {
    RenamedTable(Schema schema, Table<R> delegate, String rename) {
        super(DSL.name(rename), schema);
        for (Field<?> field2 : delegate.fields()) {
            RenamedTable.createField(field2.getUnqualifiedName(), field2.getDataType(), this);
        }
    }
}

