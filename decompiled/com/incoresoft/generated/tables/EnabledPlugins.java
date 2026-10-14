/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.EnabledPluginsRecord;
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

public class EnabledPlugins
extends TableImpl<EnabledPluginsRecord> {
    private static final long serialVersionUID = 1L;
    public static final EnabledPlugins ENABLED_PLUGINS = new EnabledPlugins();
    public final TableField<EnabledPluginsRecord, String> PLUGIN_ID = EnabledPlugins.createField(DSL.name("plugin_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<EnabledPluginsRecord, Boolean> ENABLED = EnabledPlugins.createField(DSL.name("enabled"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("TRUE"), SQLDataType.BOOLEAN)), this, "");

    @Override
    public Class<EnabledPluginsRecord> getRecordType() {
        return EnabledPluginsRecord.class;
    }

    private EnabledPlugins(Name alias, Table<EnabledPluginsRecord> aliased) {
        this(alias, aliased, null);
    }

    private EnabledPlugins(Name alias, Table<EnabledPluginsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public EnabledPlugins(String alias) {
        this(DSL.name(alias), ENABLED_PLUGINS);
    }

    public EnabledPlugins(Name alias) {
        this(alias, ENABLED_PLUGINS);
    }

    public EnabledPlugins() {
        this(DSL.name("enabled_plugins"), (Table<EnabledPluginsRecord>)null);
    }

    public <O extends Record> EnabledPlugins(Table<O> child, ForeignKey<O, EnabledPluginsRecord> key) {
        super(child, key, ENABLED_PLUGINS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<EnabledPluginsRecord> getPrimaryKey() {
        return Keys.PK_ENABLED_PLUGINS;
    }

    @Override
    public EnabledPlugins as(String alias) {
        return new EnabledPlugins(DSL.name(alias), this);
    }

    @Override
    public EnabledPlugins as(Name alias) {
        return new EnabledPlugins(alias, this);
    }

    public EnabledPlugins as(Table<?> alias) {
        return new EnabledPlugins(alias.getQualifiedName(), this);
    }

    public EnabledPlugins rename(String name) {
        return new EnabledPlugins(DSL.name(name), null);
    }

    public EnabledPlugins rename(Name name) {
        return new EnabledPlugins(name, null);
    }

    public EnabledPlugins rename(Table<?> name) {
        return new EnabledPlugins(name.getQualifiedName(), null);
    }

    @Override
    public Row2<String, Boolean> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function2<? super String, ? super Boolean, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super String, ? super Boolean, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

