/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.LicensesRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Licenses
extends TableImpl<LicensesRecord> {
    private static final long serialVersionUID = 1L;
    public static final Licenses LICENSES = new Licenses();
    public final TableField<LicensesRecord, String> LICENSE_KEY = Licenses.createField(DSL.name("license_key"), SQLDataType.VARCHAR(29).nullable(false), this, "");
    public final TableField<LicensesRecord, String> LICENSE_TEXT = Licenses.createField(DSL.name("license_text"), SQLDataType.CLOB.nullable(false), this, "");

    @Override
    public Class<LicensesRecord> getRecordType() {
        return LicensesRecord.class;
    }

    private Licenses(Name alias, Table<LicensesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Licenses(Name alias, Table<LicensesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Licenses(String alias) {
        this(DSL.name(alias), LICENSES);
    }

    public Licenses(Name alias) {
        this(alias, LICENSES);
    }

    public Licenses() {
        this(DSL.name("licenses"), (Table<LicensesRecord>)null);
    }

    public <O extends Record> Licenses(Table<O> child, ForeignKey<O, LicensesRecord> key) {
        super(child, key, LICENSES);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<LicensesRecord> getPrimaryKey() {
        return Keys.PK_LICENSES;
    }

    @Override
    public Licenses as(String alias) {
        return new Licenses(DSL.name(alias), this);
    }

    @Override
    public Licenses as(Name alias) {
        return new Licenses(alias, this);
    }

    public Licenses as(Table<?> alias) {
        return new Licenses(alias.getQualifiedName(), this);
    }

    public Licenses rename(String name) {
        return new Licenses(DSL.name(name), null);
    }

    public Licenses rename(Name name) {
        return new Licenses(name, null);
    }

    public Licenses rename(Table<?> name) {
        return new Licenses(name.getQualifiedName(), null);
    }

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function2<? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

