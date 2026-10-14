/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.WindowsShareFolderConfigsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function6;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class WindowsShareFolderConfigs
extends TableImpl<WindowsShareFolderConfigsRecord> {
    private static final long serialVersionUID = 1L;
    public static final WindowsShareFolderConfigs WINDOWS_SHARE_FOLDER_CONFIGS = new WindowsShareFolderConfigs();
    public final TableField<WindowsShareFolderConfigsRecord, Integer> ID = WindowsShareFolderConfigs.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<WindowsShareFolderConfigsRecord, String> HOST = WindowsShareFolderConfigs.createField(DSL.name("host"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<WindowsShareFolderConfigsRecord, String> SHARE_NAME = WindowsShareFolderConfigs.createField(DSL.name("share_name"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<WindowsShareFolderConfigsRecord, String> USERNAME = WindowsShareFolderConfigs.createField(DSL.name("username"), SQLDataType.CLOB, this, "");
    public final TableField<WindowsShareFolderConfigsRecord, String> PASSWORD = WindowsShareFolderConfigs.createField(DSL.name("password"), SQLDataType.CLOB, this, "");
    public final TableField<WindowsShareFolderConfigsRecord, String> DOMAIN = WindowsShareFolderConfigs.createField(DSL.name("domain"), SQLDataType.CLOB, this, "");

    @Override
    public Class<WindowsShareFolderConfigsRecord> getRecordType() {
        return WindowsShareFolderConfigsRecord.class;
    }

    private WindowsShareFolderConfigs(Name alias, Table<WindowsShareFolderConfigsRecord> aliased) {
        this(alias, aliased, null);
    }

    private WindowsShareFolderConfigs(Name alias, Table<WindowsShareFolderConfigsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public WindowsShareFolderConfigs(String alias) {
        this(DSL.name(alias), WINDOWS_SHARE_FOLDER_CONFIGS);
    }

    public WindowsShareFolderConfigs(Name alias) {
        this(alias, WINDOWS_SHARE_FOLDER_CONFIGS);
    }

    public WindowsShareFolderConfigs() {
        this(DSL.name("windows_share_folder_configs"), (Table<WindowsShareFolderConfigsRecord>)null);
    }

    public <O extends Record> WindowsShareFolderConfigs(Table<O> child, ForeignKey<O, WindowsShareFolderConfigsRecord> key) {
        super(child, key, WINDOWS_SHARE_FOLDER_CONFIGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<WindowsShareFolderConfigsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<WindowsShareFolderConfigsRecord> getPrimaryKey() {
        return Keys.PK_WINDOWS_SHARE_FOLDER_CONFIGS;
    }

    @Override
    public WindowsShareFolderConfigs as(String alias) {
        return new WindowsShareFolderConfigs(DSL.name(alias), this);
    }

    @Override
    public WindowsShareFolderConfigs as(Name alias) {
        return new WindowsShareFolderConfigs(alias, this);
    }

    public WindowsShareFolderConfigs as(Table<?> alias) {
        return new WindowsShareFolderConfigs(alias.getQualifiedName(), this);
    }

    public WindowsShareFolderConfigs rename(String name) {
        return new WindowsShareFolderConfigs(DSL.name(name), null);
    }

    public WindowsShareFolderConfigs rename(Name name) {
        return new WindowsShareFolderConfigs(name, null);
    }

    public WindowsShareFolderConfigs rename(Table<?> name) {
        return new WindowsShareFolderConfigs(name.getQualifiedName(), null);
    }

    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function6<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

