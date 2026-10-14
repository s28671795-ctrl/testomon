/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.CleaningSettingsRecord;
import java.util.Arrays;
import java.util.List;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class CleaningSettings
extends TableImpl<CleaningSettingsRecord> {
    private static final long serialVersionUID = 1L;
    public static final CleaningSettings CLEANING_SETTINGS = new CleaningSettings();
    public final TableField<CleaningSettingsRecord, String> CATEGORY_ID = CleaningSettings.createField(DSL.name("category_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<CleaningSettingsRecord, Integer> RETENTION_PERIOD = CleaningSettings.createField(DSL.name("retention_period"), SQLDataType.INTEGER.nullable(false), this, "");

    @Override
    public Class<CleaningSettingsRecord> getRecordType() {
        return CleaningSettingsRecord.class;
    }

    private CleaningSettings(Name alias, Table<CleaningSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CleaningSettings(Name alias, Table<CleaningSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public CleaningSettings(String alias) {
        this(DSL.name(alias), CLEANING_SETTINGS);
    }

    public CleaningSettings(Name alias) {
        this(alias, CLEANING_SETTINGS);
    }

    public CleaningSettings() {
        this(DSL.name("cleaning_settings"), (Table<CleaningSettingsRecord>)null);
    }

    public <O extends Record> CleaningSettings(Table<O> child, ForeignKey<O, CleaningSettingsRecord> key) {
        super(child, key, CLEANING_SETTINGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<UniqueKey<CleaningSettingsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_A);
    }

    @Override
    public CleaningSettings as(String alias) {
        return new CleaningSettings(DSL.name(alias), this);
    }

    @Override
    public CleaningSettings as(Name alias) {
        return new CleaningSettings(alias, this);
    }

    public CleaningSettings as(Table<?> alias) {
        return new CleaningSettings(alias.getQualifiedName(), this);
    }

    public CleaningSettings rename(String name) {
        return new CleaningSettings(DSL.name(name), null);
    }

    public CleaningSettings rename(Name name) {
        return new CleaningSettings(name, null);
    }

    public CleaningSettings rename(Table<?> name) {
        return new CleaningSettings(name.getQualifiedName(), null);
    }

    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function2<? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

