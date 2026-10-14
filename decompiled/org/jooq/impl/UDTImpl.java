/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.Internal;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Package;
import org.jooq.Row;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UDTRecord;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.UDTDataType;
import org.jooq.impl.UDTFieldImpl;

@Internal
public class UDTImpl<R extends UDTRecord<R>>
extends AbstractNamed
implements UDT<R>,
FieldsTrait,
QOM.UNotYetImplemented {
    private final Schema schema;
    private final FieldsImpl<R> fields = new FieldsImpl(new SelectField[0]);
    private final Package pkg;
    private final boolean synthetic;
    private transient DataType<R> type;

    public UDTImpl(String name, Schema schema) {
        this(name, schema, null);
    }

    public UDTImpl(String name, Schema schema, Package pkg) {
        this(name, schema, pkg, false);
    }

    public UDTImpl(String name, Schema schema, Package pkg, boolean synthetic) {
        super(UDTImpl.qualify(pkg != null ? pkg : schema, DSL.name(name)), CommentImpl.NO_COMMENT);
        this.schema = schema;
        this.pkg = pkg;
        this.synthetic = synthetic;
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return this.schema;
    }

    @Override
    public Name getQualifiedName() {
        Named q = this.getPackage() != null ? this.getPackage() : this.getSchema();
        return q == null ? super.getQualifiedName() : q.getQualifiedName().append(this.getUnqualifiedName());
    }

    @Override
    public final Package getPackage() {
        return this.pkg;
    }

    @Override
    public final Row fieldsRow() {
        return Tools.row0(this.fields);
    }

    final FieldsImpl<R> fields0() {
        return this.fields;
    }

    @Override
    public Class<R> getRecordType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean isSQLUsable() {
        return this.pkg == null;
    }

    @Override
    public final boolean isSynthetic() {
        return this.synthetic;
    }

    @Override
    public final R newRecord() {
        return DSL.using(new DefaultConfiguration()).newRecord(this);
    }

    @Override
    public final DataType<R> getDataType() {
        if (this.type == null) {
            this.type = new UDTDataType(this);
        }
        return this.type;
    }

    @Override
    public final void accept(Context<?> ctx) {
        QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.getSchema());
        if (this.getPackage() != null) {
            ctx.visit(this.getPackage()).sql('.');
        }
        ctx.visit(DSL.name(this.getName()));
    }

    @Deprecated
    protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt) {
        return UDTImpl.createField(DSL.name(name), type, udt, "", null, null);
    }

    @Deprecated
    protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(String name, DataType<T> type, UDT<R> udt, String comment) {
        return UDTImpl.createField(DSL.name(name), type, udt, comment, null, null);
    }

    @Deprecated
    protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<T, U> converter) {
        return UDTImpl.createField(DSL.name(name), type, udt, comment, converter, null);
    }

    @Deprecated
    protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Binding<T, U> binding) {
        return UDTImpl.createField(DSL.name(name), type, udt, comment, null, binding);
    }

    @Deprecated
    protected static final <R extends UDTRecord<R>, T, X, U> UDTField<R, U> createField(String name, DataType<T> type, UDT<R> udt, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        return UDTImpl.createField(DSL.name(name), type, udt, comment, converter, binding);
    }

    protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(Name name, DataType<T> type, UDT<R> udt) {
        return UDTImpl.createField(name, type, udt, "", null, null);
    }

    protected static final <R extends UDTRecord<R>, T> UDTField<R, T> createField(Name name, DataType<T> type, UDT<R> udt, String comment) {
        return UDTImpl.createField(name, type, udt, comment, null, null);
    }

    protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(Name name, DataType<T> type, UDT<R> udt, String comment, Converter<T, U> converter) {
        return UDTImpl.createField(name, type, udt, comment, converter, null);
    }

    protected static final <R extends UDTRecord<R>, T, U> UDTField<R, U> createField(Name name, DataType<T> type, UDT<R> udt, String comment, Binding<T, U> binding) {
        return UDTImpl.createField(name, type, udt, comment, null, binding);
    }

    protected static final <R extends UDTRecord<R>, T, X, U> UDTField<R, U> createField(Name name, DataType<T> type, UDT<R> udt, String comment, Converter<X, U> converter, Binding<T, X> binding) {
        Binding<T, U> actualBinding = DefaultBinding.newBinding(converter, type, binding);
        DataType<Object> actualType = converter == null && binding == null ? type : type.asConvertedDataType(actualBinding);
        UDTFieldImpl<R, T> udtField = new UDTFieldImpl<R, T>(name, actualType, udt, DSL.comment(comment), actualBinding);
        return udtField;
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }
}

