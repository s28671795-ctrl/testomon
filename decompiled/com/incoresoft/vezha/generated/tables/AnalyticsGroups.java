/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.AnalyticsGroupsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class AnalyticsGroups
extends TableImpl<AnalyticsGroupsRecord> {
    private static final long serialVersionUID = 1L;
    public static final AnalyticsGroups ANALYTICS_GROUPS = new AnalyticsGroups();
    public final TableField<AnalyticsGroupsRecord, Integer> ID = AnalyticsGroups.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AnalyticsGroupsRecord, String> NAME = AnalyticsGroups.createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AnalyticsGroupsRecord, Integer> PARENT_ID = AnalyticsGroups.createField(DSL.name("parent_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AnalyticsGroupsRecord, String> PLUGIN_NAME = AnalyticsGroups.createField(DSL.name("plugin_name"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AnalyticsGroupsRecord, Integer> CLIENT_ID = AnalyticsGroups.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");

    @Override
    public Class<AnalyticsGroupsRecord> getRecordType() {
        return AnalyticsGroupsRecord.class;
    }

    private AnalyticsGroups(Name alias, Table<AnalyticsGroupsRecord> aliased) {
        this(alias, aliased, null);
    }

    private AnalyticsGroups(Name alias, Table<AnalyticsGroupsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AnalyticsGroups(String alias) {
        this(DSL.name(alias), ANALYTICS_GROUPS);
    }

    public AnalyticsGroups(Name alias) {
        this(alias, ANALYTICS_GROUPS);
    }

    public AnalyticsGroups() {
        this(DSL.name("analytics_groups"), (Table<AnalyticsGroupsRecord>)null);
    }

    public <O extends Record> AnalyticsGroups(Table<O> child, ForeignKey<O, AnalyticsGroupsRecord> key) {
        super(child, key, ANALYTICS_GROUPS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<AnalyticsGroupsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AnalyticsGroupsRecord> getPrimaryKey() {
        return Keys.PK_ANALYTICS_GROUPS;
    }

    @Override
    public AnalyticsGroups as(String alias) {
        return new AnalyticsGroups(DSL.name(alias), this);
    }

    @Override
    public AnalyticsGroups as(Name alias) {
        return new AnalyticsGroups(alias, this);
    }

    public AnalyticsGroups as(Table<?> alias) {
        return new AnalyticsGroups(alias.getQualifiedName(), this);
    }

    public AnalyticsGroups rename(String name) {
        return new AnalyticsGroups(DSL.name(name), null);
    }

    public AnalyticsGroups rename(Name name) {
        return new AnalyticsGroups(name, null);
    }

    public AnalyticsGroups rename(Table<?> name) {
        return new AnalyticsGroups(name.getQualifiedName(), null);
    }

    @Override
    public Row5<Integer, String, Integer, String, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function5<? super Integer, ? super String, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Integer, ? super String, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

