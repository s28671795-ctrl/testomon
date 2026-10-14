/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import org.jooq.Converter;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.QualifiedRecord;
import org.jooq.RecordQualifier;
import org.jooq.Row;
import org.jooq.Scope;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.DefaultBindingGetSQLInputContext;
import org.jooq.impl.DefaultBindingSetSQLOutputContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.Tools;

abstract class AbstractQualifiedRecord<R extends QualifiedRecord<R>>
extends AbstractRecord
implements QualifiedRecord<R> {
    private final RecordQualifier<R> qualifier;

    public AbstractQualifiedRecord(RecordQualifier<R> qualifier) {
        super((AbstractRow)qualifier.fieldsRow());
        this.qualifier = qualifier;
    }

    @Override
    public final RecordQualifier<R> getQualifier() {
        return this.qualifier;
    }

    @Override
    public final <T> R with(Field<T> field2, T value) {
        return (R)((QualifiedRecord)super.with(field2, value));
    }

    @Override
    public final <T, U> R with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        return (R)((QualifiedRecord)super.with(field2, value, converter));
    }

    @Override
    public Row fieldsRow() {
        return this.fields;
    }

    @Override
    public Row valuesRow() {
        return Tools.row0(Tools.fieldsArray(this.intoArray(), this.fields.fields.fields()));
    }

    @Override
    public final String getSQLTypeName() throws SQLException {
        return Tools.getMappedUDTName((Scope)DefaultExecuteContext.localExecuteContext(), this);
    }

    @Override
    public final void readSQL(SQLInput stream, String typeName) throws SQLException {
        ExecuteContext ctx = DefaultExecuteContext.localExecuteContext();
        Field<?>[] f = this.getQualifier().fields();
        for (int i = 0; i < f.length; ++i) {
            Field<?> field2 = f[i];
            DefaultBindingGetSQLInputContext out = new DefaultBindingGetSQLInputContext(ctx, stream);
            field2.getBinding().get(out);
            this.set(i, field2, out.value());
        }
    }

    @Override
    public final void writeSQL(SQLOutput stream) throws SQLException {
        ExecuteContext ctx = DefaultExecuteContext.localExecuteContext();
        Field<?>[] f = this.getQualifier().fields();
        for (int i = 0; i < f.length; ++i) {
            f[i].getBinding().set(new DefaultBindingSetSQLOutputContext<Object>(ctx, stream, this.get(i)));
        }
    }
}

