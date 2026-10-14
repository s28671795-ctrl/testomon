/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.Traverser;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.TableList;
import org.jooq.impl.Tools;

final class RowsFrom
extends AbstractTable<Record>
implements QOM.RowsFrom {
    private final TableList tables;

    RowsFrom(Table<?> ... tables) {
        super(TableOptions.expression(), Names.N_ROWSFROM);
        this.tables = new TableList((List<? extends Table<?>>)Arrays.asList(tables));
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<Record> fields0() {
        ArrayList fields = new ArrayList();
        for (Table table : this.tables) {
            for (Field<?> field2 : table.fields()) {
                fields.add(DSL.field(DSL.name(field2.getName()), field2.getDataType()));
            }
        }
        return new FieldsImpl<Record>(fields);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_ROWS_FROM).sql(" (").declareTables(true, c -> c.visit(this.tables)).sql(')');
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $tables() {
        return QOM.unmodifiable(this.tables);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.tables.toArray((E[])Tools.EMPTY_TABLE), RowsFrom::new, replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$tables());
    }
}

