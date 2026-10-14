/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.StreamGroups;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

public class StreamGroupsRecord
extends UpdatableRecordImpl<StreamGroupsRecord>
implements Record4<Integer, Integer, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setParentId(Integer value) {
        this.set(1, value);
    }

    public Integer getParentId() {
        return (Integer)this.get(1);
    }

    public void setName(String value) {
        this.set(2, value);
    }

    public String getName() {
        return (String)this.get(2);
    }

    public void setClientId(Integer value) {
        this.set(3, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(3);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, String, Integer> valuesRow() {
        return (Row4)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return StreamGroups.STREAM_GROUPS.ID;
    }

    @Override
    public Field<Integer> field2() {
        return StreamGroups.STREAM_GROUPS.PARENT_ID;
    }

    @Override
    public Field<String> field3() {
        return StreamGroups.STREAM_GROUPS.NAME;
    }

    @Override
    public Field<Integer> field4() {
        return StreamGroups.STREAM_GROUPS.CLIENT_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public Integer component2() {
        return this.getParentId();
    }

    @Override
    public String component3() {
        return this.getName();
    }

    @Override
    public Integer component4() {
        return this.getClientId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public Integer value2() {
        return this.getParentId();
    }

    @Override
    public String value3() {
        return this.getName();
    }

    @Override
    public Integer value4() {
        return this.getClientId();
    }

    public StreamGroupsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public StreamGroupsRecord value2(Integer value) {
        this.setParentId(value);
        return this;
    }

    public StreamGroupsRecord value3(String value) {
        this.setName(value);
        return this;
    }

    public StreamGroupsRecord value4(Integer value) {
        this.setClientId(value);
        return this;
    }

    public StreamGroupsRecord values(Integer value1, Integer value2, String value3, Integer value4) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        return this;
    }

    public StreamGroupsRecord() {
        super(StreamGroups.STREAM_GROUPS);
    }

    public StreamGroupsRecord(Integer id, Integer parentId, String name, Integer clientId) {
        super(StreamGroups.STREAM_GROUPS);
        this.setId(id);
        this.setParentId(parentId);
        this.setName(name);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

