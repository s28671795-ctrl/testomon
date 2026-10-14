/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.ClientsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function9;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row9;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Clients
extends TableImpl<ClientsRecord> {
    private static final long serialVersionUID = 1L;
    public static final Clients CLIENTS = new Clients();
    public final TableField<ClientsRecord, Integer> ID = Clients.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<ClientsRecord, String> CLIENT_NAME = Clients.createField(DSL.name("client_name"), SQLDataType.VARCHAR(250).nullable(false), this, "");
    public final TableField<ClientsRecord, String> COUNTRY = Clients.createField(DSL.name("country"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> CITY = Clients.createField(DSL.name("city"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> ADDRESS = Clients.createField(DSL.name("address"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> ZIP_CODE = Clients.createField(DSL.name("zip_code"), SQLDataType.VARCHAR(15).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> EMAIL = Clients.createField(DSL.name("email"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> PHONE = Clients.createField(DSL.name("phone"), SQLDataType.VARCHAR(20).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<ClientsRecord, String> COMMENT = Clients.createField(DSL.name("comment"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.CLOB)), this, "");

    @Override
    public Class<ClientsRecord> getRecordType() {
        return ClientsRecord.class;
    }

    private Clients(Name alias, Table<ClientsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Clients(Name alias, Table<ClientsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Clients(String alias) {
        this(DSL.name(alias), CLIENTS);
    }

    public Clients(Name alias) {
        this(alias, CLIENTS);
    }

    public Clients() {
        this(DSL.name("clients"), (Table<ClientsRecord>)null);
    }

    public <O extends Record> Clients(Table<O> child, ForeignKey<O, ClientsRecord> key) {
        super(child, key, CLIENTS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ClientsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<ClientsRecord> getPrimaryKey() {
        return Keys.PK_CLIENTS;
    }

    @Override
    public Clients as(String alias) {
        return new Clients(DSL.name(alias), this);
    }

    @Override
    public Clients as(Name alias) {
        return new Clients(alias, this);
    }

    public Clients as(Table<?> alias) {
        return new Clients(alias.getQualifiedName(), this);
    }

    public Clients rename(String name) {
        return new Clients(DSL.name(name), null);
    }

    public Clients rename(Name name) {
        return new Clients(name, null);
    }

    public Clients rename(Table<?> name) {
        return new Clients(name.getQualifiedName(), null);
    }

    @Override
    public Row9<Integer, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row9)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

