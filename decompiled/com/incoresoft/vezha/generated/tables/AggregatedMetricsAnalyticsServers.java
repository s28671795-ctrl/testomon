/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsCore;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersRecord;
import java.time.Instant;
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

public class AggregatedMetricsAnalyticsServers
extends TableImpl<AggregatedMetricsAnalyticsServersRecord> {
    private static final long serialVersionUID = 1L;
    public static final AggregatedMetricsAnalyticsServers AGGREGATED_METRICS_ANALYTICS_SERVERS = new AggregatedMetricsAnalyticsServers();
    public final TableField<AggregatedMetricsAnalyticsServersRecord, Integer> ID = AggregatedMetricsAnalyticsServers.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersRecord, Instant> START_TIME = AggregatedMetricsAnalyticsServers.createField(DSL.name("start_time"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<AggregatedMetricsAnalyticsServersRecord, String> SERVER_ID = AggregatedMetricsAnalyticsServers.createField(DSL.name("server_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersRecord, String> IP_ADDRESS = AggregatedMetricsAnalyticsServers.createField(DSL.name("ip_address"), SQLDataType.VARCHAR(64).nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersRecord, String> CPU_LOAD = AggregatedMetricsAnalyticsServers.createField(DSL.name("cpu_load"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AggregatedMetricsAnalyticsServersRecord, Integer> CLIENT_ID = AggregatedMetricsAnalyticsServers.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    private transient AggregatedMetricsCore _aggregatedMetricsCore;

    @Override
    public Class<AggregatedMetricsAnalyticsServersRecord> getRecordType() {
        return AggregatedMetricsAnalyticsServersRecord.class;
    }

    private AggregatedMetricsAnalyticsServers(Name alias, Table<AggregatedMetricsAnalyticsServersRecord> aliased) {
        this(alias, aliased, null);
    }

    private AggregatedMetricsAnalyticsServers(Name alias, Table<AggregatedMetricsAnalyticsServersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AggregatedMetricsAnalyticsServers(String alias) {
        this(DSL.name(alias), AGGREGATED_METRICS_ANALYTICS_SERVERS);
    }

    public AggregatedMetricsAnalyticsServers(Name alias) {
        this(alias, AGGREGATED_METRICS_ANALYTICS_SERVERS);
    }

    public AggregatedMetricsAnalyticsServers() {
        this(DSL.name("aggregated_metrics_analytics_servers"), (Table<AggregatedMetricsAnalyticsServersRecord>)null);
    }

    public <O extends Record> AggregatedMetricsAnalyticsServers(Table<O> child, ForeignKey<O, AggregatedMetricsAnalyticsServersRecord> key) {
        super(child, key, AGGREGATED_METRICS_ANALYTICS_SERVERS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<AggregatedMetricsAnalyticsServersRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AggregatedMetricsAnalyticsServersRecord> getPrimaryKey() {
        return Keys.PK_AGGREGATED_METRICS_ANALYTICS_SERVERS;
    }

    @Override
    public List<ForeignKey<AggregatedMetricsAnalyticsServersRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK__METRICS_ANALYTICS_SERVERS__METRICS_CORE);
    }

    public AggregatedMetricsCore aggregatedMetricsCore() {
        if (this._aggregatedMetricsCore == null) {
            this._aggregatedMetricsCore = new AggregatedMetricsCore(this, Keys.FK__METRICS_ANALYTICS_SERVERS__METRICS_CORE);
        }
        return this._aggregatedMetricsCore;
    }

    @Override
    public AggregatedMetricsAnalyticsServers as(String alias) {
        return new AggregatedMetricsAnalyticsServers(DSL.name(alias), this);
    }

    @Override
    public AggregatedMetricsAnalyticsServers as(Name alias) {
        return new AggregatedMetricsAnalyticsServers(alias, this);
    }

    public AggregatedMetricsAnalyticsServers as(Table<?> alias) {
        return new AggregatedMetricsAnalyticsServers(alias.getQualifiedName(), this);
    }

    public AggregatedMetricsAnalyticsServers rename(String name) {
        return new AggregatedMetricsAnalyticsServers(DSL.name(name), null);
    }

    public AggregatedMetricsAnalyticsServers rename(Name name) {
        return new AggregatedMetricsAnalyticsServers(name, null);
    }

    public AggregatedMetricsAnalyticsServers rename(Table<?> name) {
        return new AggregatedMetricsAnalyticsServers(name.getQualifiedName(), null);
    }

    @Override
    public Row6<Integer, Instant, String, String, String, Integer> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function6<? super Integer, ? super Instant, ? super String, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super Instant, ? super String, ? super String, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

