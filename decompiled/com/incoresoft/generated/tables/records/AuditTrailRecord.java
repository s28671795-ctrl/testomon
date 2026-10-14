/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.AuditTrail;
import java.time.Instant;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;

public class AuditTrailRecord
extends UpdatableRecordImpl<AuditTrailRecord>
implements Record10<Integer, Instant, String, Integer, String, String, String, Integer, UUID, UUID> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setCreatedAt(Instant value) {
        this.set(1, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(1);
    }

    public void setSessionId(String value) {
        this.set(2, value);
    }

    public String getSessionId() {
        return (String)this.get(2);
    }

    public void setUserId(Integer value) {
        this.set(3, value);
    }

    public Integer getUserId() {
        return (Integer)this.get(3);
    }

    public void setUserIp(String value) {
        this.set(4, value);
    }

    public String getUserIp() {
        return (String)this.get(4);
    }

    public void setSourceId(String value) {
        this.set(5, value);
    }

    public String getSourceId() {
        return (String)this.get(5);
    }

    public void setMessage(String value) {
        this.set(6, value);
    }

    public String getMessage() {
        return (String)this.get(6);
    }

    public void setClientId(Integer value) {
        this.set(7, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(7);
    }

    public void setEventCategoryId(UUID value) {
        this.set(8, value);
    }

    public UUID getEventCategoryId() {
        return (UUID)this.get(8);
    }

    public void setEventActionId(UUID value) {
        this.set(9, value);
    }

    public UUID getEventActionId() {
        return (UUID)this.get(9);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row10<Integer, Instant, String, Integer, String, String, String, Integer, UUID, UUID> fieldsRow() {
        return (Row10)super.fieldsRow();
    }

    @Override
    public Row10<Integer, Instant, String, Integer, String, String, String, Integer, UUID, UUID> valuesRow() {
        return (Row10)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AuditTrail.AUDIT_TRAIL.ID;
    }

    @Override
    public Field<Instant> field2() {
        return AuditTrail.AUDIT_TRAIL.CREATED_AT;
    }

    @Override
    public Field<String> field3() {
        return AuditTrail.AUDIT_TRAIL.SESSION_ID;
    }

    @Override
    public Field<Integer> field4() {
        return AuditTrail.AUDIT_TRAIL.USER_ID;
    }

    @Override
    public Field<String> field5() {
        return AuditTrail.AUDIT_TRAIL.USER_IP;
    }

    @Override
    public Field<String> field6() {
        return AuditTrail.AUDIT_TRAIL.SOURCE_ID;
    }

    @Override
    public Field<String> field7() {
        return AuditTrail.AUDIT_TRAIL.MESSAGE;
    }

    @Override
    public Field<Integer> field8() {
        return AuditTrail.AUDIT_TRAIL.CLIENT_ID;
    }

    @Override
    public Field<UUID> field9() {
        return AuditTrail.AUDIT_TRAIL.EVENT_CATEGORY_ID;
    }

    @Override
    public Field<UUID> field10() {
        return AuditTrail.AUDIT_TRAIL.EVENT_ACTION_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public Instant component2() {
        return this.getCreatedAt();
    }

    @Override
    public String component3() {
        return this.getSessionId();
    }

    @Override
    public Integer component4() {
        return this.getUserId();
    }

    @Override
    public String component5() {
        return this.getUserIp();
    }

    @Override
    public String component6() {
        return this.getSourceId();
    }

    @Override
    public String component7() {
        return this.getMessage();
    }

    @Override
    public Integer component8() {
        return this.getClientId();
    }

    @Override
    public UUID component9() {
        return this.getEventCategoryId();
    }

    @Override
    public UUID component10() {
        return this.getEventActionId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public Instant value2() {
        return this.getCreatedAt();
    }

    @Override
    public String value3() {
        return this.getSessionId();
    }

    @Override
    public Integer value4() {
        return this.getUserId();
    }

    @Override
    public String value5() {
        return this.getUserIp();
    }

    @Override
    public String value6() {
        return this.getSourceId();
    }

    @Override
    public String value7() {
        return this.getMessage();
    }

    @Override
    public Integer value8() {
        return this.getClientId();
    }

    @Override
    public UUID value9() {
        return this.getEventCategoryId();
    }

    @Override
    public UUID value10() {
        return this.getEventActionId();
    }

    public AuditTrailRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AuditTrailRecord value2(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public AuditTrailRecord value3(String value) {
        this.setSessionId(value);
        return this;
    }

    public AuditTrailRecord value4(Integer value) {
        this.setUserId(value);
        return this;
    }

    public AuditTrailRecord value5(String value) {
        this.setUserIp(value);
        return this;
    }

    public AuditTrailRecord value6(String value) {
        this.setSourceId(value);
        return this;
    }

    public AuditTrailRecord value7(String value) {
        this.setMessage(value);
        return this;
    }

    public AuditTrailRecord value8(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AuditTrailRecord value9(UUID value) {
        this.setEventCategoryId(value);
        return this;
    }

    public AuditTrailRecord value10(UUID value) {
        this.setEventActionId(value);
        return this;
    }

    public AuditTrailRecord values(Integer value1, Instant value2, String value3, Integer value4, String value5, String value6, String value7, Integer value8, UUID value9, UUID value10) {
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
        return this;
    }

    public AuditTrailRecord() {
        super(AuditTrail.AUDIT_TRAIL);
    }

    public AuditTrailRecord(Integer id, Instant createdAt, String sessionId, Integer userId, String userIp, String sourceId, String message, Integer clientId, UUID eventCategoryId, UUID eventActionId) {
        super(AuditTrail.AUDIT_TRAIL);
        this.setId(id);
        this.setCreatedAt(createdAt);
        this.setSessionId(sessionId);
        this.setUserId(userId);
        this.setUserIp(userIp);
        this.setSourceId(sourceId);
        this.setMessage(message);
        this.setClientId(clientId);
        this.setEventCategoryId(eventCategoryId);
        this.setEventActionId(eventActionId);
        this.resetChangedOnNotNull();
    }
}

