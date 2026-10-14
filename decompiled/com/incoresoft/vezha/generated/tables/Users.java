/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.UsersRecord;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function15;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row15;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Users
extends TableImpl<UsersRecord> {
    private static final long serialVersionUID = 1L;
    public static final Users USERS = new Users();
    public final TableField<UsersRecord, Integer> ID = Users.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<UsersRecord, String> EMAIL = Users.createField(DSL.name("email"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<UsersRecord, String> FULLNAME = Users.createField(DSL.name("fullname"), SQLDataType.VARCHAR(250).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<UsersRecord, String> PASSWORD = Users.createField(DSL.name("password"), SQLDataType.VARCHAR(64).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<UsersRecord, String> LAST_IP = Users.createField(DSL.name("last_ip"), SQLDataType.VARCHAR(64), this, "");
    public final TableField<UsersRecord, Instant> LAST_LOGIN = Users.createField(DSL.name("last_login"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<UsersRecord, String> IP_PARAMS = Users.createField(DSL.name("ip_params"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<UsersRecord, Integer> STATUS = Users.createField(DSL.name("status"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.INTEGER)), this, "");
    public final TableField<UsersRecord, Instant> CREATED_AT = Users.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3).nullable(false).defaultValue(DSL.field(DSL.raw("LOCALTIMESTAMP"), SQLDataType.TIMESTAMP)), this, "", new InstantConverter());
    public final TableField<UsersRecord, String> SETTINGS = Users.createField(DSL.name("settings"), SQLDataType.CLOB, this, "");
    public final TableField<UsersRecord, Integer> CLIENT_ID = Users.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<UsersRecord, String> CLIENT_INFO = Users.createField(DSL.name("client_info"), SQLDataType.CLOB, this, "");
    public final TableField<UsersRecord, String> TYPE = Users.createField(DSL.name("type"), SQLDataType.VARCHAR(45).nullable(false).defaultValue(DSL.field(DSL.raw("'basic'"), SQLDataType.VARCHAR)), this, "");
    public final TableField<UsersRecord, String> TIMEZONE = Users.createField(DSL.name("timezone"), SQLDataType.VARCHAR(100), this, "");
    public final TableField<UsersRecord, String> ROLE_IDS = Users.createField(DSL.name("role_ids"), SQLDataType.CLOB.nullable(false), this, "");

    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
    }

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    public Users(Name alias) {
        this(alias, USERS);
    }

    public Users() {
        this(DSL.name("users"), (Table<UsersRecord>)null);
    }

    public <O extends Record> Users(Table<O> child, ForeignKey<O, UsersRecord> key) {
        super(child, key, USERS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<UsersRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<UsersRecord> getPrimaryKey() {
        return Keys.PK_USERS;
    }

    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    public Users as(Table<?> alias) {
        return new Users(alias.getQualifiedName(), this);
    }

    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    public Users rename(Name name) {
        return new Users(name, null);
    }

    public Users rename(Table<?> name) {
        return new Users(name.getQualifiedName(), null);
    }

    @Override
    public Row15<Integer, String, String, String, String, Instant, String, Integer, Instant, String, Integer, String, String, String, String> fieldsRow() {
        return (Row15)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function15<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super String, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

