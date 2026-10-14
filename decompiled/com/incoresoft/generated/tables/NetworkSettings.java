/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.NetworkSettingsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class NetworkSettings
extends TableImpl<NetworkSettingsRecord> {
    private static final long serialVersionUID = 1L;
    public static final NetworkSettings NETWORK_SETTINGS = new NetworkSettings();
    public final TableField<NetworkSettingsRecord, Integer> ID = NetworkSettings.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<NetworkSettingsRecord, String> NAME = NetworkSettings.createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<NetworkSettingsRecord, String> LOCAL_MASK = NetworkSettings.createField(DSL.name("local_mask"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<NetworkSettingsRecord, String> PUBLIC_ADDRESS = NetworkSettings.createField(DSL.name("public_address"), SQLDataType.CLOB, this, "");

    @Override
    public Class<NetworkSettingsRecord> getRecordType() {
        return NetworkSettingsRecord.class;
    }

    private NetworkSettings(Name alias, Table<NetworkSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private NetworkSettings(Name alias, Table<NetworkSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public NetworkSettings(String alias) {
        this(DSL.name(alias), NETWORK_SETTINGS);
    }

    public NetworkSettings(Name alias) {
        this(alias, NETWORK_SETTINGS);
    }

    public NetworkSettings() {
        this(DSL.name("network_settings"), (Table<NetworkSettingsRecord>)null);
    }

    public <O extends Record> NetworkSettings(Table<O> child, ForeignKey<O, NetworkSettingsRecord> key) {
        super(child, key, NETWORK_SETTINGS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<NetworkSettingsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<NetworkSettingsRecord> getPrimaryKey() {
        return Keys.PK_NETWORK_SETTINGS;
    }

    @Override
    public NetworkSettings as(String alias) {
        return new NetworkSettings(DSL.name(alias), this);
    }

    @Override
    public NetworkSettings as(Name alias) {
        return new NetworkSettings(alias, this);
    }

    public NetworkSettings as(Table<?> alias) {
        return new NetworkSettings(alias.getQualifiedName(), this);
    }

    public NetworkSettings rename(String name) {
        return new NetworkSettings(DSL.name(name), null);
    }

    public NetworkSettings rename(Name name) {
        return new NetworkSettings(name, null);
    }

    public NetworkSettings rename(Table<?> name) {
        return new NetworkSettings(name.getQualifiedName(), null);
    }

    @Override
    public Row4<Integer, String, String, String> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function4<? super Integer, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

