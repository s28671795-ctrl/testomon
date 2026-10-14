/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.AggregatedMetricsCore;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

public class AggregatedMetricsCoreRecord
extends UpdatableRecordImpl<AggregatedMetricsCoreRecord>
implements Record5<Integer, Instant, String, Integer, Integer> {
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

    public void setCpuLoad(String value) {
        this.set(2, value);
    }

    public String getCpuLoad() {
        return (String)this.get(2);
    }

    public void setStartupsNumber(Integer value) {
        this.set(3, value);
    }

    public Integer getStartupsNumber() {
        return (Integer)this.get(3);
    }

    public void setClientId(Integer value) {
        this.set(4, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(4);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row5<Integer, Instant, String, Integer, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    @Override
    public Row5<Integer, Instant, String, Integer, Integer> valuesRow() {
        return (Row5)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AggregatedMetricsCore.AGGREGATED_METRICS_CORE.ID;
    }

    @Override
    public Field<Instant> field2() {
        return AggregatedMetricsCore.AGGREGATED_METRICS_CORE.START_TIME;
    }

    @Override
    public Field<String> field3() {
        return AggregatedMetricsCore.AGGREGATED_METRICS_CORE.CPU_LOAD;
    }

    @Override
    public Field<Integer> field4() {
        return AggregatedMetricsCore.AGGREGATED_METRICS_CORE.STARTUPS_NUMBER;
    }

    @Override
    public Field<Integer> field5() {
        return AggregatedMetricsCore.AGGREGATED_METRICS_CORE.CLIENT_ID;
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
        return this.getCpuLoad();
    }

    @Override
    public Integer component4() {
        return this.getStartupsNumber();
    }

    @Override
    public Integer component5() {
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
        return this.getCpuLoad();
    }

    @Override
    public Integer value4() {
        return this.getStartupsNumber();
    }

    @Override
    public Integer value5() {
        return this.getClientId();
    }

    public AggregatedMetricsCoreRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AggregatedMetricsCoreRecord value2(Instant value) {
        this.setStartTime(value);
        return this;
    }

    public AggregatedMetricsCoreRecord value3(String value) {
        this.setCpuLoad(value);
        return this;
    }

    public AggregatedMetricsCoreRecord value4(Integer value) {
        this.setStartupsNumber(value);
        return this;
    }

    public AggregatedMetricsCoreRecord value5(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AggregatedMetricsCoreRecord values(Integer value1, Instant value2, String value3, Integer value4, Integer value5) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        return this;
    }

    public AggregatedMetricsCoreRecord() {
        super(AggregatedMetricsCore.AGGREGATED_METRICS_CORE);
    }

    public AggregatedMetricsCoreRecord(Integer id, Instant startTime, String cpuLoad, Integer startupsNumber, Integer clientId) {
        super(AggregatedMetricsCore.AGGREGATED_METRICS_CORE);
        this.setId(id);
        this.setStartTime(startTime);
        this.setCpuLoad(cpuLoad);
        this.setStartupsNumber(startupsNumber);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

