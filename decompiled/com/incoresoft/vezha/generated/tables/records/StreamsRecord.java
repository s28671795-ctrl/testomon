/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.Streams;
import java.time.Instant;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;

public class StreamsRecord
extends UpdatableRecordImpl<StreamsRecord>
implements Record22<Integer, String, String, Integer, Integer, String, Byte, Instant, Double, Double, String, UUID, String, String, String, Integer, Integer, String, String, Long, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setName(String value) {
        this.set(1, value);
    }

    public String getName() {
        return (String)this.get(1);
    }

    public void setPath(String value) {
        this.set(2, value);
    }

    public String getPath() {
        return (String)this.get(2);
    }

    public void setWidth(Integer value) {
        this.set(3, value);
    }

    public Integer getWidth() {
        return (Integer)this.get(3);
    }

    public void setHeight(Integer value) {
        this.set(4, value);
    }

    public Integer getHeight() {
        return (Integer)this.get(4);
    }

    public void setFileName(String value) {
        this.set(5, value);
    }

    public String getFileName() {
        return (String)this.get(5);
    }

    public void setStatus(Byte value) {
        this.set(6, value);
    }

    public Byte getStatus() {
        return (Byte)this.get(6);
    }

    public void setCreatedAt(Instant value) {
        this.set(7, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(7);
    }

    public void setLat(Double value) {
        this.set(8, value);
    }

    public Double getLat() {
        return (Double)this.get(8);
    }

    public void setLng(Double value) {
        this.set(9, value);
    }

    public Double getLng() {
        return (Double)this.get(9);
    }

    public void setType(String value) {
        this.set(10, value);
    }

    public String getType() {
        return (String)this.get(10);
    }

    public void setUuid(UUID value) {
        this.set(11, value);
    }

    public UUID getUuid() {
        return (UUID)this.get(11);
    }

    public void setAddress(String value) {
        this.set(12, value);
    }

    public String getAddress() {
        return (String)this.get(12);
    }

    public void setParams(String value) {
        this.set(13, value);
    }

    public String getParams() {
        return (String)this.get(13);
    }

    public void setAuth(String value) {
        this.set(14, value);
    }

    public String getAuth() {
        return (String)this.get(14);
    }

    public void setDirection(Integer value) {
        this.set(15, value);
    }

    public Integer getDirection() {
        return (Integer)this.get(15);
    }

    public void setClientId(Integer value) {
        this.set(16, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(16);
    }

    public void setCodec(String value) {
        this.set(17, value);
    }

    public String getCodec() {
        return (String)this.get(17);
    }

    public void setTimezone(String value) {
        this.set(18, value);
    }

    public String getTimezone() {
        return (String)this.get(18);
    }

    public void setDuration(Long value) {
        this.set(19, value);
    }

    public Long getDuration() {
        return (Long)this.get(19);
    }

    public void setRestrictions(String value) {
        this.set(20, value);
    }

    public String getRestrictions() {
        return (String)this.get(20);
    }

    public void setParentId(Integer value) {
        this.set(21, value);
    }

    public Integer getParentId() {
        return (Integer)this.get(21);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row22<Integer, String, String, Integer, Integer, String, Byte, Instant, Double, Double, String, UUID, String, String, String, Integer, Integer, String, String, Long, String, Integer> fieldsRow() {
        return (Row22)super.fieldsRow();
    }

    @Override
    public Row22<Integer, String, String, Integer, Integer, String, Byte, Instant, Double, Double, String, UUID, String, String, String, Integer, Integer, String, String, Long, String, Integer> valuesRow() {
        return (Row22)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Streams.STREAMS.ID;
    }

    @Override
    public Field<String> field2() {
        return Streams.STREAMS.NAME;
    }

    @Override
    public Field<String> field3() {
        return Streams.STREAMS.PATH;
    }

    @Override
    public Field<Integer> field4() {
        return Streams.STREAMS.WIDTH;
    }

    @Override
    public Field<Integer> field5() {
        return Streams.STREAMS.HEIGHT;
    }

    @Override
    public Field<String> field6() {
        return Streams.STREAMS.FILE_NAME;
    }

    @Override
    public Field<Byte> field7() {
        return Streams.STREAMS.STATUS;
    }

    @Override
    public Field<Instant> field8() {
        return Streams.STREAMS.CREATED_AT;
    }

    @Override
    public Field<Double> field9() {
        return Streams.STREAMS.LAT;
    }

    @Override
    public Field<Double> field10() {
        return Streams.STREAMS.LNG;
    }

    @Override
    public Field<String> field11() {
        return Streams.STREAMS.TYPE;
    }

    @Override
    public Field<UUID> field12() {
        return Streams.STREAMS.UUID;
    }

    @Override
    public Field<String> field13() {
        return Streams.STREAMS.ADDRESS;
    }

    @Override
    public Field<String> field14() {
        return Streams.STREAMS.PARAMS;
    }

    @Override
    public Field<String> field15() {
        return Streams.STREAMS.AUTH;
    }

    @Override
    public Field<Integer> field16() {
        return Streams.STREAMS.DIRECTION;
    }

    @Override
    public Field<Integer> field17() {
        return Streams.STREAMS.CLIENT_ID;
    }

    @Override
    public Field<String> field18() {
        return Streams.STREAMS.CODEC;
    }

    @Override
    public Field<String> field19() {
        return Streams.STREAMS.TIMEZONE;
    }

    @Override
    public Field<Long> field20() {
        return Streams.STREAMS.DURATION;
    }

    @Override
    public Field<String> field21() {
        return Streams.STREAMS.RESTRICTIONS;
    }

    @Override
    public Field<Integer> field22() {
        return Streams.STREAMS.PARENT_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getName();
    }

    @Override
    public String component3() {
        return this.getPath();
    }

    @Override
    public Integer component4() {
        return this.getWidth();
    }

    @Override
    public Integer component5() {
        return this.getHeight();
    }

    @Override
    public String component6() {
        return this.getFileName();
    }

    @Override
    public Byte component7() {
        return this.getStatus();
    }

    @Override
    public Instant component8() {
        return this.getCreatedAt();
    }

    @Override
    public Double component9() {
        return this.getLat();
    }

    @Override
    public Double component10() {
        return this.getLng();
    }

    @Override
    public String component11() {
        return this.getType();
    }

    @Override
    public UUID component12() {
        return this.getUuid();
    }

    @Override
    public String component13() {
        return this.getAddress();
    }

    @Override
    public String component14() {
        return this.getParams();
    }

    @Override
    public String component15() {
        return this.getAuth();
    }

    @Override
    public Integer component16() {
        return this.getDirection();
    }

    @Override
    public Integer component17() {
        return this.getClientId();
    }

    @Override
    public String component18() {
        return this.getCodec();
    }

    @Override
    public String component19() {
        return this.getTimezone();
    }

    @Override
    public Long component20() {
        return this.getDuration();
    }

    @Override
    public String component21() {
        return this.getRestrictions();
    }

    @Override
    public Integer component22() {
        return this.getParentId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getName();
    }

    @Override
    public String value3() {
        return this.getPath();
    }

    @Override
    public Integer value4() {
        return this.getWidth();
    }

    @Override
    public Integer value5() {
        return this.getHeight();
    }

    @Override
    public String value6() {
        return this.getFileName();
    }

    @Override
    public Byte value7() {
        return this.getStatus();
    }

    @Override
    public Instant value8() {
        return this.getCreatedAt();
    }

    @Override
    public Double value9() {
        return this.getLat();
    }

    @Override
    public Double value10() {
        return this.getLng();
    }

    @Override
    public String value11() {
        return this.getType();
    }

    @Override
    public UUID value12() {
        return this.getUuid();
    }

    @Override
    public String value13() {
        return this.getAddress();
    }

    @Override
    public String value14() {
        return this.getParams();
    }

    @Override
    public String value15() {
        return this.getAuth();
    }

    @Override
    public Integer value16() {
        return this.getDirection();
    }

    @Override
    public Integer value17() {
        return this.getClientId();
    }

    @Override
    public String value18() {
        return this.getCodec();
    }

    @Override
    public String value19() {
        return this.getTimezone();
    }

    @Override
    public Long value20() {
        return this.getDuration();
    }

    @Override
    public String value21() {
        return this.getRestrictions();
    }

    @Override
    public Integer value22() {
        return this.getParentId();
    }

    public StreamsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public StreamsRecord value2(String value) {
        this.setName(value);
        return this;
    }

    public StreamsRecord value3(String value) {
        this.setPath(value);
        return this;
    }

    public StreamsRecord value4(Integer value) {
        this.setWidth(value);
        return this;
    }

    public StreamsRecord value5(Integer value) {
        this.setHeight(value);
        return this;
    }

    public StreamsRecord value6(String value) {
        this.setFileName(value);
        return this;
    }

    public StreamsRecord value7(Byte value) {
        this.setStatus(value);
        return this;
    }

    public StreamsRecord value8(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public StreamsRecord value9(Double value) {
        this.setLat(value);
        return this;
    }

    public StreamsRecord value10(Double value) {
        this.setLng(value);
        return this;
    }

    public StreamsRecord value11(String value) {
        this.setType(value);
        return this;
    }

    public StreamsRecord value12(UUID value) {
        this.setUuid(value);
        return this;
    }

    public StreamsRecord value13(String value) {
        this.setAddress(value);
        return this;
    }

    public StreamsRecord value14(String value) {
        this.setParams(value);
        return this;
    }

    public StreamsRecord value15(String value) {
        this.setAuth(value);
        return this;
    }

    public StreamsRecord value16(Integer value) {
        this.setDirection(value);
        return this;
    }

    public StreamsRecord value17(Integer value) {
        this.setClientId(value);
        return this;
    }

    public StreamsRecord value18(String value) {
        this.setCodec(value);
        return this;
    }

    public StreamsRecord value19(String value) {
        this.setTimezone(value);
        return this;
    }

    public StreamsRecord value20(Long value) {
        this.setDuration(value);
        return this;
    }

    public StreamsRecord value21(String value) {
        this.setRestrictions(value);
        return this;
    }

    public StreamsRecord value22(Integer value) {
        this.setParentId(value);
        return this;
    }

    public StreamsRecord values(Integer value1, String value2, String value3, Integer value4, Integer value5, String value6, Byte value7, Instant value8, Double value9, Double value10, String value11, UUID value12, String value13, String value14, String value15, Integer value16, Integer value17, String value18, String value19, Long value20, String value21, Integer value22) {
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
        this.value21(value21);
        this.value22(value22);
        return this;
    }

    public StreamsRecord() {
        super(Streams.STREAMS);
    }

    public StreamsRecord(Integer id, String name, String path, Integer width, Integer height, String fileName, Byte status, Instant createdAt, Double lat, Double lng, String type, UUID uuid, String address, String params, String auth, Integer direction, Integer clientId, String codec, String timezone, Long duration, String restrictions, Integer parentId) {
        super(Streams.STREAMS);
        this.setId(id);
        this.setName(name);
        this.setPath(path);
        this.setWidth(width);
        this.setHeight(height);
        this.setFileName(fileName);
        this.setStatus(status);
        this.setCreatedAt(createdAt);
        this.setLat(lat);
        this.setLng(lng);
        this.setType(type);
        this.setUuid(uuid);
        this.setAddress(address);
        this.setParams(params);
        this.setAuth(auth);
        this.setDirection(direction);
        this.setClientId(clientId);
        this.setCodec(codec);
        this.setTimezone(timezone);
        this.setDuration(duration);
        this.setRestrictions(restrictions);
        this.setParentId(parentId);
        this.resetChangedOnNotNull();
    }
}

