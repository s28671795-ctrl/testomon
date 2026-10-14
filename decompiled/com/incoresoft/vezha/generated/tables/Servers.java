/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.ServersRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Servers
extends TableImpl<ServersRecord> {
    private static final long serialVersionUID = 1L;
    public static final Servers SERVERS = new Servers();
    public final TableField<ServersRecord, String> ID = Servers.createField(DSL.name("id"), SQLDataType.VARCHAR(36).nullable(false), this, "");
    public final TableField<ServersRecord, String> NAME = Servers.createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<ServersRecord, Boolean> IS_EXTERNAL_ADDRESS_ENABLED = Servers.createField(DSL.name("is_external_address_enabled"), SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("FALSE"), SQLDataType.BOOLEAN)), this, "");
    public final TableField<ServersRecord, String> ADDRESS = Servers.createField(DSL.name("address"), SQLDataType.CLOB, this, "");
    public final TableField<ServersRecord, Integer> PORT = Servers.createField(DSL.name("port"), SQLDataType.INTEGER, this, "");

    @Override
    public Class<ServersRecord> getRecordType() {
        return ServersRecord.class;
    }

    private Servers(Name alias, Table<ServersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Servers(Name alias, Table<ServersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Servers(String alias) {
        this(DSL.name(alias), SERVERS);
    }

    public Servers(Name alias) {
        this(alias, SERVERS);
    }

    public Servers() {
        this(DSL.name("servers"), (Table<ServersRecord>)null);
    }

    public <O extends Record> Servers(Table<O> child, ForeignKey<O, ServersRecord> key) {
        super(child, key, SERVERS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<ServersRecord> getPrimaryKey() {
        return Keys.PK_SERVERS;
    }

    @Override
    public Servers as(String alias) {
        return new Servers(DSL.name(alias), this);
    }

    @Override
    public Servers as(Name alias) {
        return new Servers(alias, this);
    }

    public Servers as(Table<?> alias) {
        return new Servers(alias.getQualifiedName(), this);
    }

    public Servers rename(String name) {
        return new Servers(DSL.name(name), null);
    }

    public Servers rename(Name name) {
        return new Servers(name, null);
    }

    public Servers rename(Table<?> name) {
        return new Servers(name.getQualifiedName(), null);
    }

    @Override
    public Row5<String, String, Boolean, String, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function5<? super String, ? super String, ? super Boolean, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super String, ? super String, ? super Boolean, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

