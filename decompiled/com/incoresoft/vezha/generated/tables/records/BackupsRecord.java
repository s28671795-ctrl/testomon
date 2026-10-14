/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.Backups;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

public class BackupsRecord
extends UpdatableRecordImpl<BackupsRecord>
implements Record9<Integer, String, String, String, String, Instant, Long, Integer, Boolean> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setFileName(String value) {
        this.set(1, value);
    }

    public String getFileName() {
        return (String)this.get(1);
    }

    public void setDescription(String value) {
        this.set(2, value);
    }

    public String getDescription() {
        return (String)this.get(2);
    }

    public void setCoreVersion(String value) {
        this.set(3, value);
    }

    public String getCoreVersion() {
        return (String)this.get(3);
    }

    public void setType(String value) {
        this.set(4, value);
    }

    public String getType() {
        return (String)this.get(4);
    }

    public void setCreatedAt(Instant value) {
        this.set(5, value);
    }

    public Instant getCreatedAt() {
        return (Instant)this.get(5);
    }

    public void setSize(Long value) {
        this.set(6, value);
    }

    public Long getSize() {
        return (Long)this.get(6);
    }

    public void setClientId(Integer value) {
        this.set(7, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(7);
    }

    public void setIsVmsIntegrationEnabled(Boolean value) {
        this.set(8, value);
    }

    public Boolean getIsVmsIntegrationEnabled() {
        return (Boolean)this.get(8);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row9<Integer, String, String, String, String, Instant, Long, Integer, Boolean> fieldsRow() {
        return (Row9)super.fieldsRow();
    }

    @Override
    public Row9<Integer, String, String, String, String, Instant, Long, Integer, Boolean> valuesRow() {
        return (Row9)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Backups.BACKUPS.ID;
    }

    @Override
    public Field<String> field2() {
        return Backups.BACKUPS.FILE_NAME;
    }

    @Override
    public Field<String> field3() {
        return Backups.BACKUPS.DESCRIPTION;
    }

    @Override
    public Field<String> field4() {
        return Backups.BACKUPS.CORE_VERSION;
    }

    @Override
    public Field<String> field5() {
        return Backups.BACKUPS.TYPE;
    }

    @Override
    public Field<Instant> field6() {
        return Backups.BACKUPS.CREATED_AT;
    }

    @Override
    public Field<Long> field7() {
        return Backups.BACKUPS.SIZE;
    }

    @Override
    public Field<Integer> field8() {
        return Backups.BACKUPS.CLIENT_ID;
    }

    @Override
    public Field<Boolean> field9() {
        return Backups.BACKUPS.IS_VMS_INTEGRATION_ENABLED;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getFileName();
    }

    @Override
    public String component3() {
        return this.getDescription();
    }

    @Override
    public String component4() {
        return this.getCoreVersion();
    }

    @Override
    public String component5() {
        return this.getType();
    }

    @Override
    public Instant component6() {
        return this.getCreatedAt();
    }

    @Override
    public Long component7() {
        return this.getSize();
    }

    @Override
    public Integer component8() {
        return this.getClientId();
    }

    @Override
    public Boolean component9() {
        return this.getIsVmsIntegrationEnabled();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getFileName();
    }

    @Override
    public String value3() {
        return this.getDescription();
    }

    @Override
    public String value4() {
        return this.getCoreVersion();
    }

    @Override
    public String value5() {
        return this.getType();
    }

    @Override
    public Instant value6() {
        return this.getCreatedAt();
    }

    @Override
    public Long value7() {
        return this.getSize();
    }

    @Override
    public Integer value8() {
        return this.getClientId();
    }

    @Override
    public Boolean value9() {
        return this.getIsVmsIntegrationEnabled();
    }

    public BackupsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public BackupsRecord value2(String value) {
        this.setFileName(value);
        return this;
    }

    public BackupsRecord value3(String value) {
        this.setDescription(value);
        return this;
    }

    public BackupsRecord value4(String value) {
        this.setCoreVersion(value);
        return this;
    }

    public BackupsRecord value5(String value) {
        this.setType(value);
        return this;
    }

    public BackupsRecord value6(Instant value) {
        this.setCreatedAt(value);
        return this;
    }

    public BackupsRecord value7(Long value) {
        this.setSize(value);
        return this;
    }

    public BackupsRecord value8(Integer value) {
        this.setClientId(value);
        return this;
    }

    public BackupsRecord value9(Boolean value) {
        this.setIsVmsIntegrationEnabled(value);
        return this;
    }

    public BackupsRecord values(Integer value1, String value2, String value3, String value4, String value5, Instant value6, Long value7, Integer value8, Boolean value9) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        this.value8(value8);
        this.value9(value9);
        return this;
    }

    public BackupsRecord() {
        super(Backups.BACKUPS);
    }

    public BackupsRecord(Integer id, String fileName, String description, String coreVersion, String type, Instant createdAt, Long size, Integer clientId, Boolean isVmsIntegrationEnabled) {
        super(Backups.BACKUPS);
        this.setId(id);
        this.setFileName(fileName);
        this.setDescription(description);
        this.setCoreVersion(coreVersion);
        this.setType(type);
        this.setCreatedAt(createdAt);
        this.setSize(size);
        this.setClientId(clientId);
        this.setIsVmsIntegrationEnabled(isVmsIntegrationEnabled);
        this.resetChangedOnNotNull();
    }
}

