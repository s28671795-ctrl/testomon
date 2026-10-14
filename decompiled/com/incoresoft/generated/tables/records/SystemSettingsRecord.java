/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.SystemSettings;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

public class SystemSettingsRecord
extends UpdatableRecordImpl<SystemSettingsRecord>
implements Record3<Integer, String, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setVariableName(String value) {
        this.set(1, value);
    }

    public String getVariableName() {
        return (String)this.get(1);
    }

    public void setValue(String value) {
        this.set(2, value);
    }

    public String getValue() {
        return (String)this.get(2);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3)super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return SystemSettings.SYSTEM_SETTINGS.ID;
    }

    @Override
    public Field<String> field2() {
        return SystemSettings.SYSTEM_SETTINGS.VARIABLE_NAME;
    }

    @Override
    public Field<String> field3() {
        return SystemSettings.SYSTEM_SETTINGS.VALUE;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getVariableName();
    }

    @Override
    public String component3() {
        return this.getValue();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getVariableName();
    }

    @Override
    public String value3() {
        return this.getValue();
    }

    public SystemSettingsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public SystemSettingsRecord value2(String value) {
        this.setVariableName(value);
        return this;
    }

    public SystemSettingsRecord value3(String value) {
        this.setValue(value);
        return this;
    }

    public SystemSettingsRecord values(Integer value1, String value2, String value3) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        return this;
    }

    public SystemSettingsRecord() {
        super(SystemSettings.SYSTEM_SETTINGS);
    }

    public SystemSettingsRecord(Integer id, String variableName, String value) {
        super(SystemSettings.SYSTEM_SETTINGS);
        this.setId(id);
        this.setVariableName(variableName);
        this.setValue(value);
        this.resetChangedOnNotNull();
    }
}

