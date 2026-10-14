/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServers;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersGpuRecord;
import java.util.Arrays;
import java.util.List;
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

public class AggregatedMetricsAnalyticsServersGpu
extends TableImpl<AggregatedMetricsAnalyticsServersGpuRecord> {
    private static final long serialVersionUID = 1L;
    public static final AggregatedMetricsAnalyticsServersGpu AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU = new AggregatedMetricsAnalyticsServersGpu();
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, Integer> ID = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, Integer> SERVER_METRICS_ID = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("server_metrics_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, Integer> GPU_ID = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("gpu_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, String> GPU_NAME = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("gpu_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, String> LOAD = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("load"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersGpuRecord, Integer> CLIENT_ID = AggregatedMetricsAnalyticsServersGpu.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    private transient AggregatedMetricsAnalyticsServers _aggregatedMetricsAnalyticsServers;

    @Override
    public Class<AggregatedMetricsAnalyticsServersGpuRecord> getRecordType() {
        return AggregatedMetricsAnalyticsServersGpuRecord.class;
    }

    private AggregatedMetricsAnalyticsServersGpu(Name alias, Table<AggregatedMetricsAnalyticsServersGpuRecord> aliased) {
        this(alias, aliased, null);
    }

    private AggregatedMetricsAnalyticsServersGpu(Name alias, Table<AggregatedMetricsAnalyticsServersGpuRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AggregatedMetricsAnalyticsServersGpu(String alias) {
        this(DSL.name(alias), AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU);
    }

    public AggregatedMetricsAnalyticsServersGpu(Name alias) {
        this(alias, AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU);
    }

    public AggregatedMetricsAnalyticsServersGpu() {
        this(DSL.name("aggregated_metrics_analytics_servers_gpu"), (Table<AggregatedMetricsAnalyticsServersGpuRecord>)null);
    }

    public <O extends Record> AggregatedMetricsAnalyticsServersGpu(Table<O> child, ForeignKey<O, AggregatedMetricsAnalyticsServersGpuRecord> key) {
        super(child, key, AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<AggregatedMetricsAnalyticsServersGpuRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AggregatedMetricsAnalyticsServersGpuRecord> getPrimaryKey() {
        return Keys.PK_AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU;
    }

    @Override
    public List<ForeignKey<AggregatedMetricsAnalyticsServersGpuRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK__METRICS_ANALYTICS_SERVERS_GPU__METRICS_ANALYTICS_SERVERS);
    }

    public AggregatedMetricsAnalyticsServers aggregatedMetricsAnalyticsServers() {
        if (this._aggregatedMetricsAnalyticsServers == null) {
            this._aggregatedMetricsAnalyticsServers = new AggregatedMetricsAnalyticsServers(this, Keys.FK__METRICS_ANALYTICS_SERVERS_GPU__METRICS_ANALYTICS_SERVERS);
        }
        return this._aggregatedMetricsAnalyticsServers;
    }

    @Override
    public AggregatedMetricsAnalyticsServersGpu as(String alias) {
        return new AggregatedMetricsAnalyticsServersGpu(DSL.name(alias), this);
    }

    @Override
    public AggregatedMetricsAnalyticsServersGpu as(Name alias) {
        return new AggregatedMetricsAnalyticsServersGpu(alias, this);
    }

    public AggregatedMetricsAnalyticsServersGpu as(Table<?> alias) {
        return new AggregatedMetricsAnalyticsServersGpu(alias.getQualifiedName(), this);
    }

    public AggregatedMetricsAnalyticsServersGpu rename(String name) {
        return new AggregatedMetricsAnalyticsServersGpu(DSL.name(name), null);
    }

    public AggregatedMetricsAnalyticsServersGpu rename(Name name) {
        return new AggregatedMetricsAnalyticsServersGpu(name, null);
    }

    public AggregatedMetricsAnalyticsServersGpu rename(Table<?> name) {
        return new AggregatedMetricsAnalyticsServersGpu(name.getQualifiedName(), null);
    }

    @Override
    public Row6<Integer, Integer, Integer, String, String, Integer> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function6<? super Integer, ? super Integer, ? super Integer, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super Integer, ? super Integer, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

