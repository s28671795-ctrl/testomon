/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServersGpu;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

public class AggregatedMetricsAnalyticsServersGpuRecord
extends UpdatableRecordImpl<AggregatedMetricsAnalyticsServersGpuRecord>
implements Record6<Integer, Integer, Integer, String, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setServerMetricsId(Integer value) {
        this.set(1, value);
    }

    public Integer getServerMetricsId() {
        return (Integer)this.get(1);
    }

    public void setGpuId(Integer value) {
        this.set(2, value);
    }

    public Integer getGpuId() {
        return (Integer)this.get(2);
    }

    public void setGpuName(String value) {
        this.set(3, value);
    }

    public String getGpuName() {
        return (String)this.get(3);
    }

    public void setLoad(String value) {
        this.set(4, value);
    }

    public String getLoad() {
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
    public Row6<Integer, Integer, Integer, String, String, Integer> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    @Override
    public Row6<Integer, Integer, Integer, String, String, Integer> valuesRow() {
        return (Row6)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.ID;
    }

    @Override
    public Field<Integer> field2() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.SERVER_METRICS_ID;
    }

    @Override
    public Field<Integer> field3() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.GPU_ID;
    }

    @Override
    public Field<String> field4() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.GPU_NAME;
    }

    @Override
    public Field<String> field5() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.LOAD;
    }

    @Override
    public Field<Integer> field6() {
        return AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.CLIENT_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public Integer component2() {
        return this.getServerMetricsId();
    }

    @Override
    public Integer component3() {
        return this.getGpuId();
    }

    @Override
    public String component4() {
        return this.getGpuName();
    }

    @Override
    public String component5() {
        return this.getLoad();
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
    public Integer value2() {
        return this.getServerMetricsId();
    }

    @Override
    public Integer value3() {
        return this.getGpuId();
    }

    @Override
    public String value4() {
        return this.getGpuName();
    }

    @Override
    public String value5() {
        return this.getLoad();
    }

    @Override
    public Integer value6() {
        return this.getClientId();
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value2(Integer value) {
        this.setServerMetricsId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value3(Integer value) {
        this.setGpuId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value4(String value) {
        this.setGpuName(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value5(String value) {
        this.setLoad(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord value6(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, Integer value6) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        return this;
    }

    public AggregatedMetricsAnalyticsServersGpuRecord() {
        super(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU);
    }

    public AggregatedMetricsAnalyticsServersGpuRecord(Integer id, Integer serverMetricsId, Integer gpuId, String gpuName, String load, Integer clientId) {
        super(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU);
        this.setId(id);
        this.setServerMetricsId(serverMetricsId);
        this.setGpuId(gpuId);
        this.setGpuName(gpuName);
        this.setLoad(load);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

