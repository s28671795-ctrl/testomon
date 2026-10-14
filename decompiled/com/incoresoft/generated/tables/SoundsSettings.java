/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.SoundsSettingsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class SoundsSettings
extends TableImpl<SoundsSettingsRecord> {
    private static final long serialVersionUID = 1L;
    public static final SoundsSettings SOUNDS_SETTINGS = new SoundsSettings();
    public final TableField<SoundsSettingsRecord, String> ID = SoundsSettings.createField(DSL.name("id"), SQLDataType.VARCHAR(100).nullable(false), this, "");
    public final TableField<SoundsSettingsRecord, String> SOUND_FILE = SoundsSettings.createField(DSL.name("sound_file"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<SoundsSettingsRecord, Boolean> ENABLED = SoundsSettings.createField(DSL.name("enabled"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("TRUE"), SQLDataType.BOOLEAN)), this, "");

    @Override
    public Class<SoundsSettingsRecord> getRecordType() {
        return SoundsSettingsRecord.class;
    }

    private SoundsSettings(Name alias, Table<SoundsSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private SoundsSettings(Name alias, Table<SoundsSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public SoundsSettings(String alias) {
        this(DSL.name(alias), SOUNDS_SETTINGS);
    }

    public SoundsSettings(Name alias) {
        this(alias, SOUNDS_SETTINGS);
    }

    public SoundsSettings() {
        this(DSL.name("sounds_settings"), (Table<SoundsSettingsRecord>)null);
    }

    public <O extends Record> SoundsSettings(Table<O> child, ForeignKey<O, SoundsSettingsRecord> key) {
        super(child, key, SOUNDS_SETTINGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<SoundsSettingsRecord> getPrimaryKey() {
        return Keys.PK_SOUNDS_SETTINGS;
    }

    @Override
    public SoundsSettings as(String alias) {
        return new SoundsSettings(DSL.name(alias), this);
    }

    @Override
    public SoundsSettings as(Name alias) {
        return new SoundsSettings(alias, this);
    }

    public SoundsSettings as(Table<?> alias) {
        return new SoundsSettings(alias.getQualifiedName(), this);
    }

    public SoundsSettings rename(String name) {
        return new SoundsSettings(DSL.name(name), null);
    }

    public SoundsSettings rename(Name name) {
        return new SoundsSettings(name, null);
    }

    public SoundsSettings rename(Table<?> name) {
        return new SoundsSettings(name.getQualifiedName(), null);
    }

    @Override
    public Row3<String, String, Boolean> fieldsRow() {
        return (Row3)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function3<? super String, ? super String, ? super Boolean, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super String, ? super String, ? super Boolean, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

