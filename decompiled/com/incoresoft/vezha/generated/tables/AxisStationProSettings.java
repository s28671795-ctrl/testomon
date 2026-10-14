/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.tables.records.AxisStationProSettingsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class AxisStationProSettings
extends TableImpl<AxisStationProSettingsRecord> {
    private static final long serialVersionUID = 1L;
    public static final AxisStationProSettings AXIS_STATION_PRO_SETTINGS = new AxisStationProSettings();
    public final TableField<AxisStationProSettingsRecord, String> HOST = AxisStationProSettings.createField(DSL.name("host"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AxisStationProSettingsRecord, Integer> PORT = AxisStationProSettings.createField(DSL.name("port"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AxisStationProSettingsRecord, String> USERNAME = AxisStationProSettings.createField(DSL.name("username"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AxisStationProSettingsRecord, String> PASSWORD = AxisStationProSettings.createField(DSL.name("password"), SQLDataType.CLOB.nullable(false), this, "");

    @Override
    public Class<AxisStationProSettingsRecord> getRecordType() {
        return AxisStationProSettingsRecord.class;
    }

    private AxisStationProSettings(Name alias, Table<AxisStationProSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private AxisStationProSettings(Name alias, Table<AxisStationProSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AxisStationProSettings(String alias) {
        this(DSL.name(alias), AXIS_STATION_PRO_SETTINGS);
    }

    public AxisStationProSettings(Name alias) {
        this(alias, AXIS_STATION_PRO_SETTINGS);
    }

    public AxisStationProSettings() {
        this(DSL.name("axis_station_pro_settings"), (Table<AxisStationProSettingsRecord>)null);
    }

    public <O extends Record> AxisStationProSettings(Table<O> child, ForeignKey<O, AxisStationProSettingsRecord> key) {
        super(child, key, AXIS_STATION_PRO_SETTINGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public AxisStationProSettings as(String alias) {
        return new AxisStationProSettings(DSL.name(alias), this);
    }

    @Override
    public AxisStationProSettings as(Name alias) {
        return new AxisStationProSettings(alias, this);
    }

    public AxisStationProSettings as(Table<?> alias) {
        return new AxisStationProSettings(alias.getQualifiedName(), this);
    }

    public AxisStationProSettings rename(String name) {
        return new AxisStationProSettings(DSL.name(name), null);
    }

    public AxisStationProSettings rename(Name name) {
        return new AxisStationProSettings(name, null);
    }

    public AxisStationProSettings rename(Table<?> name) {
        return new AxisStationProSettings(name.getQualifiedName(), null);
    }

    @Override
    public Row4<String, Integer, String, String> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function4<? super String, ? super Integer, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super String, ? super Integer, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

