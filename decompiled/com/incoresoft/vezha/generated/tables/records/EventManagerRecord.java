/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.EventManager;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

public class EventManagerRecord
extends UpdatableRecordImpl<EventManagerRecord>
implements Record7<Integer, String, String, String, Instant, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setUuid(String value) {
        this.set(1, value);
    }

    public String getUuid() {
        return (String)this.get(1);
    }

    public void setTitle(String value) {
        this.set(2, value);
    }

    public String getTitle() {
        return (String)this.get(2);
    }

    public void setDescription(String value) {
        this.set(3, value);
    }

    public String getDescription() {
        return (String)this.get(3);
    }

    public void setCreatedAt(Instant value) {
        this.set(4, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(4);
    }

    public void setNodes(String value) {
        this.set(5, value);
    }

    public String getNodes() {
        return (String)this.get(5);
    }

    public void setClientId(Integer value) {
        this.set(6, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(6);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row7<Integer, String, String, String, Instant, String, Integer> fieldsRow() {
        return (Row7)super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, String, String, Instant, String, Integer> valuesRow() {
        return (Row7)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return EventManager.EVENT_MANAGER.ID;
    }

    @Override
    public Field<String> field2() {
        return EventManager.EVENT_MANAGER.UUID;
    }

    @Override
    public Field<String> field3() {
        return EventManager.EVENT_MANAGER.TITLE;
    }

    @Override
    public Field<String> field4() {
        return EventManager.EVENT_MANAGER.DESCRIPTION;
    }

    @Override
    public Field<Instant> field5() {
        return EventManager.EVENT_MANAGER.CREATED_AT;
    }

    @Override
    public Field<String> field6() {
        return EventManager.EVENT_MANAGER.NODES;
    }

    @Override
    public Field<Integer> field7() {
        return EventManager.EVENT_MANAGER.CLIENT_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getUuid();
    }

    @Override
    public String component3() {
        return this.getTitle();
    }

    @Override
    public String component4() {
        return this.getDescription();
    }

    @Override
    public Instant component5() {
        return this.getCreatedAt();
    }

    @Override
    public String component6() {
        return this.getNodes();
    }

    @Override
    public Integer component7() {
        return this.getClientId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getUuid();
    }

    @Override
    public String value3() {
        return this.getTitle();
    }

    @Override
    public String value4() {
        return this.getDescription();
    }

    @Override
    public Instant value5() {
        return this.getCreatedAt();
    }

    @Override
    public String value6() {
        return this.getNodes();
    }

    @Override
    public Integer value7() {
        return this.getClientId();
    }

    public EventManagerRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public EventManagerRecord value2(String value) {
        this.setUuid(value);
        return this;
    }

    public EventManagerRecord value3(String value) {
        this.setTitle(value);
        return this;
    }

    public EventManagerRecord value4(String value) {
        this.setDescription(value);
        return this;
    }

    public EventManagerRecord value5(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public EventManagerRecord value6(String value) {
        this.setNodes(value);
        return this;
    }

    public EventManagerRecord value7(Integer value) {
        this.setClientId(value);
        return this;
    }

    public EventManagerRecord values(Integer value1, String value2, String value3, String value4, Instant value5, String value6, Integer value7) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        return this;
    }

    public EventManagerRecord() {
        super(EventManager.EVENT_MANAGER);
    }

    public EventManagerRecord(Integer id, String uuid, String title, String description, Instant createdAt, String nodes, Integer clientId) {
        super(EventManager.EVENT_MANAGER);
        this.setId(id);
        this.setUuid(uuid);
        this.setTitle(title);
        this.setDescription(description);
        this.setCreatedAt(createdAt);
        this.setNodes(nodes);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

