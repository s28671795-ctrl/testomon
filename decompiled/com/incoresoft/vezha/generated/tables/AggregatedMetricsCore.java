/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsCoreRecord;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
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

public class AggregatedMetricsCore
extends TableImpl<AggregatedMetricsCoreRecord> {
    private static final long serialVersionUID = 1L;
    public static final AggregatedMetricsCore AGGREGATED_METRICS_CORE = new AggregatedMetricsCore();
    public final TableField<AggregatedMetricsCoreRecord, Integer> ID = AggregatedMetricsCore.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AggregatedMetricsCoreRecord, Instant> START_TIME = AggregatedMetricsCore.createField(DSL.name("start_time"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<AggregatedMetricsCoreRecord, String> CPU_LOAD = AggregatedMetricsCore.createField(DSL.name("cpu_load"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AggregatedMetricsCoreRecord, Integer> STARTUPS_NUMBER = AggregatedMetricsCore.createField(DSL.name("startups_number"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AggregatedMetricsCoreRecord, Integer> CLIENT_ID = AggregatedMetricsCore.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");

    @Override
    public Class<AggregatedMetricsCoreRecord> getRecordType() {
        return AggregatedMetricsCoreRecord.class;
    }

    private AggregatedMetricsCore(Name alias, Table<AggregatedMetricsCoreRecord> aliased) {
        this(alias, aliased, null);
    }

    private AggregatedMetricsCore(Name alias, Table<AggregatedMetricsCoreRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AggregatedMetricsCore(String alias) {
        this(DSL.name(alias), AGGREGATED_METRICS_CORE);
    }

    public AggregatedMetricsCore(Name alias) {
        this(alias, AGGREGATED_METRICS_CORE);
    }

    public AggregatedMetricsCore() {
        this(DSL.name("aggregated_metrics_core"), (Table<AggregatedMetricsCoreRecord>)null);
    }

    public <O extends Record> AggregatedMetricsCore(Table<O> child, ForeignKey<O, AggregatedMetricsCoreRecord> key) {
        super(child, key, AGGREGATED_METRICS_CORE);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<AggregatedMetricsCoreRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AggregatedMetricsCoreRecord> getPrimaryKey() {
        return Keys.PK_AGGREGATED_METRICS_CORE;
    }

    @Override
    public List<UniqueKey<AggregatedMetricsCoreRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_3);
    }

    @Override
    public AggregatedMetricsCore as(String alias) {
        return new AggregatedMetricsCore(DSL.name(alias), this);
    }

    @Override
    public AggregatedMetricsCore as(Name alias) {
        return new AggregatedMetricsCore(alias, this);
    }

    public AggregatedMetricsCore as(Table<?> alias) {
        return new AggregatedMetricsCore(alias.getQualifiedName(), this);
    }

    public AggregatedMetricsCore rename(String name) {
        return new AggregatedMetricsCore(DSL.name(name), null);
    }

    public AggregatedMetricsCore rename(Name name) {
        return new AggregatedMetricsCore(name, null);
    }

    public AggregatedMetricsCore rename(Table<?> name) {
        return new AggregatedMetricsCore(name.getQualifiedName(), null);
    }

    @Override
    public Row5<Integer, Instant, String, Integer, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function5<? super Integer, ? super Instant, ? super String, ? super Integer, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Integer, ? super Instant, ? super String, ? super Integer, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

