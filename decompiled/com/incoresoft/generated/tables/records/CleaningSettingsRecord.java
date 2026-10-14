/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.CleaningSettings;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

public class CleaningSettingsRecord
extends TableRecordImpl<CleaningSettingsRecord>
implements Record2<String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setCategoryId(String value) {
        this.set(0, value);
    }

    public String getCategoryId() {
        return (String)this.get(0);
    }

    public void setRetentionPeriod(Integer value) {
        this.set(1, value);
    }

    public Integer getRetentionPeriod() {
        return (Integer)this.get(1);
    }

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    @Override
    public Row2<String, Integer> valuesRow() {
        return (Row2)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return CleaningSettings.CLEANING_SETTINGS.CATEGORY_ID;
    }

    @Override
    public Field<Integer> field2() {
        return CleaningSettings.CLEANING_SETTINGS.RETENTION_PERIOD;
    }

    @Override
    public String component1() {
        return this.getCategoryId();
    }

    @Override
    public Integer component2() {
        return this.getRetentionPeriod();
    }

    @Override
    public String value1() {
        return this.getCategoryId();
    }

    @Override
    public Integer value2() {
        return this.getRetentionPeriod();
    }

    public CleaningSettingsRecord value1(String value) {
        this.setCategoryId(value);
        return this;
    }

    public CleaningSettingsRecord value2(Integer value) {
        this.setRetentionPeriod(value);
        return this;
    }

    public CleaningSettingsRecord values(String value1, Integer value2) {
        this.value1(value1);
        this.value2(value2);
        return this;
    }

    public CleaningSettingsRecord() {
        super(CleaningSettings.CLEANING_SETTINGS);
    }

    public CleaningSettingsRecord(String categoryId, Integer retentionPeriod) {
        super(CleaningSettings.CLEANING_SETTINGS);
        this.setCategoryId(categoryId);
        this.setRetentionPeriod(retentionPeriod);
        this.resetChangedOnNotNull();
    }
}

