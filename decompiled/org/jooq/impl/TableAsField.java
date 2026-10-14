/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordDataType;
import org.jooq.impl.RowAsField;
import org.jooq.impl.ScopeMappableWrapper;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.Tools;

final class TableAsField<R extends Record>
extends AbstractRowAsField<R>
implements QOM.TableAsField<R>,
ScopeMappableWrapper<TableAsField<R>, Table<R>> {
    final Table<R> table;

    TableAsField(Table<R> table) {
        this(table, table.getQualifiedName());
    }

    TableAsField(Table<R> table, Name as) {
        super(as, new RecordDataType(((AbstractTable)table).fieldsRow(), table.getRecordType(), table.getName()));
        this.table = table;
    }

    @Override
    final Table<R> fields0() {
        return this.table;
    }

    @Override
    final Class<R> getRecordType() {
        return this.table.getRecordType();
    }

    @Override
    final void acceptDefault(Context<?> ctx) {
        if (RowAsField.NO_NATIVE_SUPPORT.contains((Object)ctx.dialect())) {
            ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c -> c.visit(new SelectFieldList((SelectFieldOrAsterisk[])this.emulatedFields((Configuration)ctx.configuration()).fields.fields)));
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit(this.table);
        } else {
            ctx.visit(new RowAsField(this.table.fieldsRow(), this.getQualifiedName()));
        }
    }

    @Override
    public Field<R> as(Name alias) {
        return new TableAsField<R>(this.table, alias);
    }

    @Override
    public final Field<?> $aliased() {
        return new TableAsField<R>(this.table);
    }

    @Override
    public final Name $alias() {
        return this.getQualifiedName();
    }

    @Override
    public final Table<R> $table() {
        return this.table;
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.table);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.table, t2 -> new TableAsField<R>(t2, this.getQualifiedName()), replacer);
    }

    @Override
    public final TableAsField<R> wrap(Table<R> wrapped) {
        return new TableAsField<R>(wrapped, this.getQualifiedName());
    }
}

