/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServers;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

public class AggregatedMetricsAnalyticsServersRecord
extends UpdatableRecordImpl<AggregatedMetricsAnalyticsServersRecord>
implements Record6<Integer, Instant, String, String, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setStartTime(Instant value) {
        this.set(1, value);
    }

    public Instant getStartTime() {
        return (Instant)this.get(1);
    }

    public void setServerId(String value) {
        this.set(2, value);
    }

    public String getServerId() {
        return (String)this.get(2);
    }

    public void setIpAddress(String value) {
        this.set(3, value);
    }

    public String getIpAddress() {
        return (String)this.get(3);
    }

    public void setCpuLoad(String value) {
        this.set(4, value);
    }

    public String getCpuLoad() {
        return (String)this.get(4);
    }

    public void setClientId(Integer value) {
        this.set(5, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(5);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row6<Integer, Instant, String, String, String, Integer> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    @Override
    public Row6<Integer, Instant, String, String, String, Integer> valuesRow() {
        return (Row6)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.ID;
    }

    @Override
    public Field<Instant> field2() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.START_TIME;
    }

    @Override
    public Field<String> field3() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.SERVER_ID;
    }

    @Override
    public Field<String> field4() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.IP_ADDRESS;
    }

    @Override
    public Field<String> field5() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.CPU_LOAD;
    }

    @Override
    public Field<Integer> field6() {
        return AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.CLIENT_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public Instant component2() {
        return this.getStartTime();
    }

    @Override
    public String component3() {
        return this.getServerId();
    }

    @Override
    public String component4() {
        return this.getIpAddress();
    }

    @Override
    public String component5() {
        return this.getCpuLoad();
    }

    @Override
    public Integer component6() {
        return this.getClientId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public Instant value2() {
        return this.getStartTime();
    }

    @Override
    public String value3() {
        return this.getServerId();
    }

    @Override
    public String value4() {
        return this.getIpAddress();
    }

    @Override
    public String value5() {
        return this.getCpuLoad();
    }

    @Override
    public Integer value6() {
        return this.getClientId();
    }

    public AggregatedMetricsAnalyticsServersRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord value2(Instant value) {
        this.setStartTime(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord value3(String value) {
        this.setServerId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord value4(String value) {
        this.setIpAddress(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord value5(String value) {
        this.setCpuLoad(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord value6(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord values(Integer value1, Instant value2, String value3, String value4, String value5, Integer value6) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        return this;
    }

    public AggregatedMetricsAnalyticsServersRecord() {
        super(AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS);
    }

    public AggregatedMetricsAnalyticsServersRecord(Integer id, Instant startTime, String serverId, String ipAddress, String cpuLoad, Integer clientId) {
        super(AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS);
        this.setId(id);
        this.setStartTime(startTime);
        this.setServerId(serverId);
        this.setIpAddress(ipAddress);
        this.setCpuLoad(cpuLoad);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

