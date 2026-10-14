/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.SystemSettingsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class SystemSettings
extends TableImpl<SystemSettingsRecord> {
    private static final long serialVersionUID = 1L;
    public static final SystemSettings SYSTEM_SETTINGS = new SystemSettings();
    public final TableField<SystemSettingsRecord, Integer> ID = SystemSettings.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<SystemSettingsRecord, String> VARIABLE_NAME = SystemSettings.createField(DSL.name("variable_name"), SQLDataType.VARCHAR(45).nullable(false), this, "");
    public final TableField<SystemSettingsRecord, String> VALUE = SystemSettings.createField(DSL.name("value"), SQLDataType.CLOB, this, "");

    @Override
    public Class<SystemSettingsRecord> getRecordType() {
        return SystemSettingsRecord.class;
    }

    private SystemSettings(Name alias, Table<SystemSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private SystemSettings(Name alias, Table<SystemSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public SystemSettings(String alias) {
        this(DSL.name(alias), SYSTEM_SETTINGS);
    }

    public SystemSettings(Name alias) {
        this(alias, SYSTEM_SETTINGS);
    }

    public SystemSettings() {
        this(DSL.name("system_settings"), (Table<SystemSettingsRecord>)null);
    }

    public <O extends Record> SystemSettings(Table<O> child, ForeignKey<O, SystemSettingsRecord> key) {
        super(child, key, SYSTEM_SETTINGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<SystemSettingsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<SystemSettingsRecord> getPrimaryKey() {
        return Keys.PK_SYSTEM_SETTINGS;
    }

    @Override
    public SystemSettings as(String alias) {
        return new SystemSettings(DSL.name(alias), this);
    }

    @Override
    public SystemSettings as(Name alias) {
        return new SystemSettings(alias, this);
    }

    public SystemSettings as(Table<?> alias) {
        return new SystemSettings(alias.getQualifiedName(), this);
    }

    public SystemSettings rename(String name) {
        return new SystemSettings(DSL.name(name), null);
    }

    public SystemSettings rename(Name name) {
        return new SystemSettings(name, null);
    }

    public SystemSettings rename(Table<?> name) {
        return new SystemSettings(name.getQualifiedName(), null);
    }

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function3<? super Integer, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super Integer, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

