/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.SoundsSettings;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

public class SoundsSettingsRecord
extends UpdatableRecordImpl<SoundsSettingsRecord>
implements Record3<String, String, Boolean> {
    private static final long serialVersionUID = 1L;

    public void setId(String value) {
        this.set(0, value);
    }

    public String getId() {
        return (String)this.get(0);
    }

    public void setSoundFile(String value) {
        this.set(1, value);
    }

    public String getSoundFile() {
        return (String)this.get(1);
    }

    public void setEnabled(Boolean value) {
        this.set(2, value);
    }

    public Boolean getEnabled() {
        return (Boolean)this.get(2);
    }

    @Override
    public Record1<String> key() {
        return (Record1)super.key();
    }

    @Override
    public Row3<String, String, Boolean> fieldsRow() {
        return (Row3)super.fieldsRow();
    }

    @Override
    public Row3<String, String, Boolean> valuesRow() {
        return (Row3)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return SoundsSettings.SOUNDS_SETTINGS.ID;
    }

    @Override
    public Field<String> field2() {
        return SoundsSettings.SOUNDS_SETTINGS.SOUND_FILE;
    }

    @Override
    public Field<Boolean> field3() {
        return SoundsSettings.SOUNDS_SETTINGS.ENABLED;
    }

    @Override
    public String component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getSoundFile();
    }

    @Override
    public Boolean component3() {
        return this.getEnabled();
    }

    @Override
    public String value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getSoundFile();
    }

    @Override
    public Boolean value3() {
        return this.getEnabled();
    }

    public SoundsSettingsRecord value1(String value) {
        this.setId(value);
        return this;
    }

    public SoundsSettingsRecord value2(String value) {
        this.setSoundFile(value);
        return this;
    }

    public SoundsSettingsRecord value3(Boolean value) {
        this.setEnabled(value);
        return this;
    }

    public SoundsSettingsRecord values(String value1, String value2, Boolean value3) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        return this;
    }

    public SoundsSettingsRecord() {
        super(SoundsSettings.SOUNDS_SETTINGS);
    }

    public SoundsSettingsRecord(String id, String soundFile, Boolean enabled) {
        super(SoundsSettings.SOUNDS_SETTINGS);
        this.setId(id);
        this.setSoundFile(soundFile);
        this.setEnabled(enabled);
        this.resetChangedOnNotNull();
    }
}

