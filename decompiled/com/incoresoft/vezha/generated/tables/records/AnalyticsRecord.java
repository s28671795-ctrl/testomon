/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.vezha.generated.tables.Analytics;
import java.time.Instant;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record20;
import org.jooq.Row20;
import org.jooq.impl.UpdatableRecordImpl;

public class AnalyticsRecord
extends UpdatableRecordImpl<AnalyticsRecord>
implements Record20<Integer, UUID, String, String, String, Instant, String, Integer, String, String, Integer, String, Boolean, String, String, String, EventsHolder, Instant, UUID, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setUuid(UUID value) {
        this.set(1, value);
    }

    public UUID getUuid() {
        return (UUID)this.get(1);
    }

    public void setType(String value) {
        this.set(2, value);
    }

    public String getType() {
        return (String)this.get(2);
    }

    public void setPluginName(String value) {
        this.set(3, value);
    }

    public String getPluginName() {
        return (String)this.get(3);
    }

    public void setName(String value) {
        this.set(4, value);
    }

    public String getName() {
        return (String)this.get(4);
    }

    public void setCreatedAt(Instant value) {
        this.set(5, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(5);
    }

    public void setStatus(String value) {
        this.set(6, value);
    }

    public String getStatus() {
        return (String)this.get(6);
    }

    public void setClientId(Integer value) {
        this.set(7, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(7);
    }

    public void setStream(String value) {
        this.set(8, value);
    }

    public String getStream() {
        return (String)this.get(8);
    }

    public void setModule(String value) {
        this.set(9, value);
    }

    public String getModule() {
        return (String)this.get(9);
    }

    public void setLastGpuId(Integer value) {
        this.set(10, value);
    }

    public Integer getLastGpuId() {
        return (Integer)this.get(10);
    }

    public void setDesiredServerId(String value) {
        this.set(11, value);
    }

    public String getDesiredServerId() {
        return (String)this.get(11);
    }

    public void setDisableBalancing(Boolean value) {
        this.set(12, value);
    }

    public Boolean getDisableBalancing() {
        return (Boolean)this.get(12);
    }

    public void setStartSignature(String value) {
        this.set(13, value);
    }

    public String getStartSignature() {
        return (String)this.get(13);
    }

    public void setAllowedServerIds(String value) {
        this.set(14, value);
    }

    public String getAllowedServerIds() {
        return (String)this.get(14);
    }

    public void setRestrictions(String value) {
        this.set(15, value);
    }

    public String getRestrictions() {
        return (String)this.get(15);
    }

    public void setEventsHolder(EventsHolder value) {
        this.set(16, value);
    }

    public EventsHolder getEventsHolder() {
        return (EventsHolder)this.get(16);
    }

    public void setStartAt(Instant value) {
        this.set(17, value);
    }

    public Instant getStartAt() {
        return (Instant)this.get(17);
    }

    public void setStreamUuid(UUID value) {
        this.set(18, value);
    }

    public UUID getStreamUuid() {
        return (UUID)this.get(18);
    }

    public void setGroupId(Integer value) {
        this.set(19, value);
    }

    public Integer getGroupId() {
        return (Integer)this.get(19);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row20<Integer, UUID, String, String, String, Instant, String, Integer, String, String, Integer, String, Boolean, String, String, String, EventsHolder, Instant, UUID, Integer> fieldsRow() {
        return (Row20)super.fieldsRow();
    }

    @Override
    public Row20<Integer, UUID, String, String, String, Instant, String, Integer, String, String, Integer, String, Boolean, String, String, String, EventsHolder, Instant, UUID, Integer> valuesRow() {
        return (Row20)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Analytics.ANALYTICS.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Analytics.ANALYTICS.UUID;
    }

    @Override
    public Field<String> field3() {
        return Analytics.ANALYTICS.TYPE;
    }

    @Override
    public Field<String> field4() {
        return Analytics.ANALYTICS.PLUGIN_NAME;
    }

    @Override
    public Field<String> field5() {
        return Analytics.ANALYTICS.NAME;
    }

    @Override
    public Field<Instant> field6() {
        return Analytics.ANALYTICS.CREATED_AT;
    }

    @Override
    public Field<String> field7() {
        return Analytics.ANALYTICS.STATUS;
    }

    @Override
    public Field<Integer> field8() {
        return Analytics.ANALYTICS.CLIENT_ID;
    }

    @Override
    public Field<String> field9() {
        return Analytics.ANALYTICS.STREAM;
    }

    @Override
    public Field<String> field10() {
        return Analytics.ANALYTICS.MODULE;
    }

    @Override
    public Field<Integer> field11() {
        return Analytics.ANALYTICS.LAST_GPU_ID;
    }

    @Override
    public Field<String> field12() {
        return Analytics.ANALYTICS.DESIRED_SERVER_ID;
    }

    @Override
    public Field<Boolean> field13() {
        return Analytics.ANALYTICS.DISABLE_BALANCING;
    }

    @Override
    public Field<String> field14() {
        return Analytics.ANALYTICS.START_SIGNATURE;
    }

    @Override
    public Field<String> field15() {
        return Analytics.ANALYTICS.ALLOWED_SERVER_IDS;
    }

    @Override
    public Field<String> field16() {
        return Analytics.ANALYTICS.RESTRICTIONS;
    }

    @Override
    public Field<EventsHolder> field17() {
        return Analytics.ANALYTICS.EVENTS_HOLDER;
    }

    @Override
    public Field<Instant> field18() {
        return Analytics.ANALYTICS.START_AT;
    }

    @Override
    public Field<UUID> field19() {
        return Analytics.ANALYTICS.STREAM_UUID;
    }

    @Override
    public Field<Integer> field20() {
        return Analytics.ANALYTICS.GROUP_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public UUID component2() {
        return this.getUuid();
    }

    @Override
    public String component3() {
        return this.getType();
    }

    @Override
    public String component4() {
        return this.getPluginName();
    }

    @Override
    public String component5() {
        return this.getName();
    }

    @Override
    public Instant component6() {
        return this.getCreatedAt();
    }

    @Override
    public String component7() {
        return this.getStatus();
    }

    @Override
    public Integer component8() {
        return this.getClientId();
    }

    @Override
    public String component9() {
        return this.getStream();
    }

    @Override
    public String component10() {
        return this.getModule();
    }

    @Override
    public Integer component11() {
        return this.getLastGpuId();
    }

    @Override
    public String component12() {
        return this.getDesiredServerId();
    }

    @Override
    public Boolean component13() {
        return this.getDisableBalancing();
    }

    @Override
    public String component14() {
        return this.getStartSignature();
    }

    @Override
    public String component15() {
        return this.getAllowedServerIds();
    }

    @Override
    public String component16() {
        return this.getRestrictions();
    }

    @Override
    public EventsHolder component17() {
        return this.getEventsHolder();
    }

    @Override
    public Instant component18() {
        return this.getStartAt();
    }

    @Override
    public UUID component19() {
        return this.getStreamUuid();
    }

    @Override
    public Integer component20() {
        return this.getGroupId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public UUID value2() {
        return this.getUuid();
    }

    @Override
    public String value3() {
        return this.getType();
    }

    @Override
    public String value4() {
        return this.getPluginName();
    }

    @Override
    public String value5() {
        return this.getName();
    }

    @Override
    public Instant value6() {
        return this.getCreatedAt();
    }

    @Override
    public String value7() {
        return this.getStatus();
    }

    @Override
    public Integer value8() {
        return this.getClientId();
    }

    @Override
    public String value9() {
        return this.getStream();
    }

    @Override
    public String value10() {
        return this.getModule();
    }

    @Override
    public Integer value11() {
        return this.getLastGpuId();
    }

    @Override
    public String value12() {
        return this.getDesiredServerId();
    }

    @Override
    public Boolean value13() {
        return this.getDisableBalancing();
    }

    @Override
    public String value14() {
        return this.getStartSignature();
    }

    @Override
    public String value15() {
        return this.getAllowedServerIds();
    }

    @Override
    public String value16() {
        return this.getRestrictions();
    }

    @Override
    public EventsHolder value17() {
        return this.getEventsHolder();
    }

    @Override
    public Instant value18() {
        return this.getStartAt();
    }

    @Override
    public UUID value19() {
        return this.getStreamUuid();
    }

    @Override
    public Integer value20() {
        return this.getGroupId();
    }

    public AnalyticsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AnalyticsRecord value2(UUID value) {
        this.setUuid(value);
        return this;
    }

    public AnalyticsRecord value3(String value) {
        this.setType(value);
        return this;
    }

    public AnalyticsRecord value4(String value) {
        this.setPluginName(value);
        return this;
    }

    public AnalyticsRecord value5(String value) {
        this.setName(value);
        return this;
    }

    public AnalyticsRecord value6(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public AnalyticsRecord value7(String value) {
        this.setStatus(value);
        return this;
    }

    public AnalyticsRecord value8(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AnalyticsRecord value9(String value) {
        this.setStream(value);
        return this;
    }

    public AnalyticsRecord value10(String value) {
        this.setModule(value);
        return this;
    }

    public AnalyticsRecord value11(Integer value) {
        this.setLastGpuId(value);
        return this;
    }

    public AnalyticsRecord value12(String value) {
        this.setDesiredServerId(value);
        return this;
    }

    public AnalyticsRecord value13(Boolean value) {
        this.setDisableBalancing(value);
        return this;
    }

    public AnalyticsRecord value14(String value) {
        this.setStartSignature(value);
        return this;
    }

    public AnalyticsRecord value15(String value) {
        this.setAllowedServerIds(value);
        return this;
    }

    public AnalyticsRecord value16(String value) {
        this.setRestrictions(value);
        return this;
    }

    public AnalyticsRecord value17(EventsHolder value) {
        this.setEventsHolder(value);
        return this;
    }

    public AnalyticsRecord value18(Instant value) {
        this.setStartAt(value);
        return this;
    }

    public AnalyticsRecord value19(UUID value) {
        this.setStreamUuid(value);
        return this;
    }

    public AnalyticsRecord value20(Integer value) {
        this.setGroupId(value);
        return this;
    }

    public AnalyticsRecord values(Integer value1, UUID value2, String value3, String value4, String value5, Instant value6, String value7, Integer value8, String value9, String value10, Integer value11, String value12, Boolean value13, String value14, String value15, String value16, EventsHolder value17, Instant value18, UUID value19, Integer value20) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        this.value8(value8);
        this.value9(value9);
        this.value10(value10);
        this.value11(value11);
        this.value12(value12);
        this.value13(value13);
        this.value14(value14);
        this.value15(value15);
        this.value16(value16);
        this.value17(value17);
        this.value18(value18);
        this.value19(value19);
        this.value20(value20);
        return this;
    }

    public AnalyticsRecord() {
        super(Analytics.ANALYTICS);
    }

    public AnalyticsRecord(Integer id, UUID uuid, String type, String pluginName, String name, Instant createdAt, String status, Integer clientId, String stream, String module, Integer lastGpuId, String desiredServerId, Boolean disableBalancing, String startSignature, String allowedServerIds, String restrictions, EventsHolder eventsHolder, Instant startAt, UUID streamUuid, Integer groupId) {
        super(Analytics.ANALYTICS);
        this.setId(id);
        this.setUuid(uuid);
        this.setType(type);
        this.setPluginName(pluginName);
        this.setName(name);
        this.setCreatedAt(createdAt);
        this.setStatus(status);
        this.setClientId(clientId);
        this.setStream(stream);
        this.setModule(module);
        this.setLastGpuId(lastGpuId);
        this.setDesiredServerId(desiredServerId);
        this.setDisableBalancing(disableBalancing);
        this.setStartSignature(startSignature);
        this.setAllowedServerIds(allowedServerIds);
        this.setRestrictions(restrictions);
        this.setEventsHolder(eventsHolder);
        this.setStartAt(startAt);
        this.setStreamUuid(streamUuid);
        this.setGroupId(groupId);
        this.resetChangedOnNotNull();
    }
}

