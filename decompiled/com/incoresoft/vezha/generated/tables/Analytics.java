/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.database.converters.EventsHolderConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.AnalyticsRecord;
import java.time.Instant;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function20;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row20;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Analytics
extends TableImpl<AnalyticsRecord> {
    private static final long serialVersionUID = 1L;
    public static final Analytics ANALYTICS = new Analytics();
    public final TableField<AnalyticsRecord, Integer> ID = Analytics.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AnalyticsRecord, UUID> UUID = Analytics.createField(DSL.name("uuid"), SQLDataType.UUID.nullable(false), this, "");
    public final TableField<AnalyticsRecord, String> TYPE = Analytics.createField(DSL.name("type"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, String> PLUGIN_NAME = Analytics.createField(DSL.name("plugin_name"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, String> NAME = Analytics.createField(DSL.name("name"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, Instant> CREATED_AT = Analytics.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<AnalyticsRecord, String> STATUS = Analytics.createField(DSL.name("status"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AnalyticsRecord, Integer> CLIENT_ID = Analytics.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AnalyticsRecord, String> STREAM = Analytics.createField(DSL.name("stream"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, String> MODULE = Analytics.createField(DSL.name("module"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, Integer> LAST_GPU_ID = Analytics.createField(DSL.name("last_gpu_id"), SQLDataType.INTEGER, this, "");
    public final TableField<AnalyticsRecord, String> DESIRED_SERVER_ID = Analytics.createField(DSL.name("desired_server_id"), SQLDataType.VARCHAR(100), this, "");
    public final TableField<AnalyticsRecord, Boolean> DISABLE_BALANCING = Analytics.createField(DSL.name("disable_balancing"), SQLDataType.BOOLEAN, this, "");
    public final TableField<AnalyticsRecord, String> START_SIGNATURE = Analytics.createField(DSL.name("start_signature"), SQLDataType.VARCHAR(50), this, "");
    public final TableField<AnalyticsRecord, String> ALLOWED_SERVER_IDS = Analytics.createField(DSL.name("allowed_server_ids"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, String> RESTRICTIONS = Analytics.createField(DSL.name("restrictions"), SQLDataType.CLOB, this, "");
    public final TableField<AnalyticsRecord, EventsHolder> EVENTS_HOLDER = Analytics.createField(DSL.name("events_holder"), SQLDataType.CLOB, this, "", new EventsHolderConverter());
    public final TableField<AnalyticsRecord, Instant> START_AT = Analytics.createField(DSL.name("start_at"), SQLDataType.TIMESTAMP(3), this, "", new InstantConverter());
    public final TableField<AnalyticsRecord, UUID> STREAM_UUID = Analytics.createField(DSL.name("stream_uuid"), SQLDataType.UUID, this, "");
    public final TableField<AnalyticsRecord, Integer> GROUP_ID = Analytics.createField(DSL.name("group_id"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("'0'"), SQLDataType.INTEGER)), this, "");

    @Override
    public Class<AnalyticsRecord> getRecordType() {
        return AnalyticsRecord.class;
    }

    private Analytics(Name alias, Table<AnalyticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Analytics(Name alias, Table<AnalyticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Analytics(String alias) {
        this(DSL.name(alias), ANALYTICS);
    }

    public Analytics(Name alias) {
        this(alias, ANALYTICS);
    }

    public Analytics() {
        this(DSL.name("analytics"), (Table<AnalyticsRecord>)null);
    }

    public <O extends Record> Analytics(Table<O> child, ForeignKey<O, AnalyticsRecord> key) {
        super(child, key, ANALYTICS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<AnalyticsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AnalyticsRecord> getPrimaryKey() {
        return Keys.PK_ANALYTICS;
    }

    @Override
    public Analytics as(String alias) {
        return new Analytics(DSL.name(alias), this);
    }

    @Override
    public Analytics as(Name alias) {
        return new Analytics(alias, this);
    }

    public Analytics as(Table<?> alias) {
        return new Analytics(alias.getQualifiedName(), this);
    }

    public Analytics rename(String name) {
        return new Analytics(DSL.name(name), null);
    }

    public Analytics rename(Name name) {
        return new Analytics(name, null);
    }

    public Analytics rename(Table<?> name) {
        return new Analytics(name.getQualifiedName(), null);
    }

    @Override
    public Row20<Integer, UUID, String, String, String, Instant, String, Integer, String, String, Integer, String, Boolean, String, String, String, EventsHolder, Instant, UUID, Integer> fieldsRow() {
        return (Row20)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function20<? super Integer, ? super UUID, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super Integer, ? super String, ? super Boolean, ? super String, ? super String, ? super String, ? super EventsHolder, ? super Instant, ? super UUID, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function20<? super Integer, ? super UUID, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super Integer, ? super String, ? super Boolean, ? super String, ? super String, ? super String, ? super EventsHolder, ? super Instant, ? super UUID, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

