/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.PluginConfigurationsRecord;
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

public class PluginConfigurations
extends TableImpl<PluginConfigurationsRecord> {
    private static final long serialVersionUID = 1L;
    public static final PluginConfigurations PLUGIN_CONFIGURATIONS = new PluginConfigurations();
    public final TableField<PluginConfigurationsRecord, Integer> ID = PluginConfigurations.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<PluginConfigurationsRecord, String> PLUGIN_NAME = PluginConfigurations.createField(DSL.name("plugin_name"), SQLDataType.VARCHAR(64).nullable(false), this, "");
    public final TableField<PluginConfigurationsRecord, String> BACKUP_IGNORED_TABLES = PluginConfigurations.createField(DSL.name("backup_ignored_tables"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<PluginConfigurationsRecord, String> MANAGED_TABLES = PluginConfigurations.createField(DSL.name("managed_tables"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<PluginConfigurationsRecord, Integer> CLIENT_ID = PluginConfigurations.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<PluginConfigurationsRecord, String> PLUGIN_ID = PluginConfigurations.createField(DSL.name("plugin_id"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    @Override
    public Class<PluginConfigurationsRecord> getRecordType() {
        return PluginConfigurationsRecord.class;
    }

    private PluginConfigurations(Name alias, Table<PluginConfigurationsRecord> aliased) {
        this(alias, aliased, null);
    }

    private PluginConfigurations(Name alias, Table<PluginConfigurationsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public PluginConfigurations(String alias) {
        this(DSL.name(alias), PLUGIN_CONFIGURATIONS);
    }

    public PluginConfigurations(Name alias) {
        this(alias, PLUGIN_CONFIGURATIONS);
    }

    public PluginConfigurations() {
        this(DSL.name("plugin_configurations"), (Table<PluginConfigurationsRecord>)null);
    }

    public <O extends Record> PluginConfigurations(Table<O> child, ForeignKey<O, PluginConfigurationsRecord> key) {
        super(child, key, PLUGIN_CONFIGURATIONS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<PluginConfigurationsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<PluginConfigurationsRecord> getPrimaryKey() {
        return Keys.PK_PLUGIN_CONFIGURATIONS;
    }

    @Override
    public PluginConfigurations as(String alias) {
        return new PluginConfigurations(DSL.name(alias), this);
    }

    @Override
    public PluginConfigurations as(Name alias) {
        return new PluginConfigurations(alias, this);
    }

    public PluginConfigurations as(Table<?> alias) {
        return new PluginConfigurations(alias.getQualifiedName(), this);
    }

    public PluginConfigurations rename(String name) {
        return new PluginConfigurations(DSL.name(name), null);
    }

    public PluginConfigurations rename(Name name) {
        return new PluginConfigurations(name, null);
    }

    public PluginConfigurations rename(Table<?> name) {
        return new PluginConfigurations(name.getQualifiedName(), null);
    }

    @Override
    public Row6<Integer, String, String, String, Integer, String> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function6<? super Integer, ? super String, ? super String, ? super String, ? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super String, ? super String, ? super String, ? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

