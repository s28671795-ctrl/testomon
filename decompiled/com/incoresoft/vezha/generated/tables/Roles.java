/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.RolesRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
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

public class Roles
extends TableImpl<RolesRecord> {
    private static final long serialVersionUID = 1L;
    public static final Roles ROLES = new Roles();
    public final TableField<RolesRecord, Integer> ID = Roles.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<RolesRecord, String> ROLE_NAME = Roles.createField(DSL.name("role_name"), SQLDataType.VARCHAR(60).nullable(false), this, "");
    public final TableField<RolesRecord, String> PERMISSIONS = Roles.createField(DSL.name("permissions"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<RolesRecord, Integer> CLIENT_ID = Roles.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<RolesRecord, String> DESCRIPTION = Roles.createField(DSL.name("description"), SQLDataType.CLOB, this, "");

    @Override
    public Class<RolesRecord> getRecordType() {
        return RolesRecord.class;
    }

    private Roles(Name alias, Table<RolesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Roles(Name alias, Table<RolesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Roles(String alias) {
        this(DSL.name(alias), ROLES);
    }

    public Roles(Name alias) {
        this(alias, ROLES);
    }

    public Roles() {
        this(DSL.name("roles"), (Table<RolesRecord>)null);
    }

    public <O extends Record> Roles(Table<O> child, ForeignKey<O, RolesRecord> key) {
        super(child, key, ROLES);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<RolesRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<RolesRecord> getPrimaryKey() {
        return Keys.PK_ROLES;
    }

    @Override
    public Roles as(String alias) {
        return new Roles(DSL.name(alias), this);
    }

    @Override
    public Roles as(Name alias) {
        return new Roles(alias, this);
    }

    public Roles as(Table<?> alias) {
        return new Roles(alias.getQualifiedName(), this);
    }

    public Roles rename(String name) {
        return new Roles(DSL.name(name), null);
    }

    public Roles rename(Name name) {
        return new Roles(name, null);
    }

    public Roles rename(Table<?> name) {
        return new Roles(name.getQualifiedName(), null);
    }

    @Override
    public Row5<Integer, String, String, Integer, String> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function5<? super Integer, ? super String, ? super String, ? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function5<? super Integer, ? super String, ? super String, ? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

