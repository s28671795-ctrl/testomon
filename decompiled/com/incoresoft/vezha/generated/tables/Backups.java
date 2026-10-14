/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.BackupsRecord;
import java.time.Instant;
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

public class Backups
extends TableImpl<BackupsRecord> {
    private static final long serialVersionUID = 1L;
    public static final Backups BACKUPS = new Backups();
    public final TableField<BackupsRecord, Integer> ID = Backups.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<BackupsRecord, String> FILE_NAME = Backups.createField(DSL.name("file_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<BackupsRecord, String> DESCRIPTION = Backups.createField(DSL.name("description"), SQLDataType.CLOB, this, "");
    public final TableField<BackupsRecord, String> CORE_VERSION = Backups.createField(DSL.name("core_version"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<BackupsRecord, String> TYPE = Backups.createField(DSL.name("type"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<BackupsRecord, Instant> CREATED_AT = Backups.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<BackupsRecord, Long> SIZE = Backups.createField(DSL.name("size"), SQLDataType.BIGINT.nullable(false), this, "");
    public final TableField<BackupsRecord, Integer> CLIENT_ID = Backups.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<BackupsRecord, Boolean> IS_VMS_INTEGRATION_ENABLED = Backups.createField(DSL.name("is_vms_integration_enabled"), SQLDataType.BOOLEAN.nullable(false), this, "");

    @Override
    public Class<BackupsRecord> getRecordType() {
        return BackupsRecord.class;
    }

    private Backups(Name alias, Table<BackupsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Backups(Name alias, Table<BackupsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Backups(String alias) {
        this(DSL.name(alias), BACKUPS);
    }

    public Backups(Name alias) {
        this(alias, BACKUPS);
    }

    public Backups() {
        this(DSL.name("backups"), (Table<BackupsRecord>)null);
    }

    public <O extends Record> Backups(Table<O> child, ForeignKey<O, BackupsRecord> key) {
        super(child, key, BACKUPS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<BackupsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<BackupsRecord> getPrimaryKey() {
        return Keys.PK_BACKUPS;
    }

    @Override
    public Backups as(String alias) {
        return new Backups(DSL.name(alias), this);
    }

    @Override
    public Backups as(Name alias) {
        return new Backups(alias, this);
    }

    public Backups as(Table<?> alias) {
        return new Backups(alias.getQualifiedName(), this);
    }

    public Backups rename(String name) {
        return new Backups(DSL.name(name), null);
    }

    public Backups rename(Name name) {
        return new Backups(name, null);
    }

    public Backups rename(Table<?> name) {
        return new Backups(name.getQualifiedName(), null);
    }

    @Override
    public Row9<Integer, String, String, String, String, Instant, Long, Integer, Boolean> fieldsRow() {
        return (Row9)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super Instant, ? super Long, ? super Integer, ? super Boolean, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super Instant, ? super Long, ? super Integer, ? super Boolean, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

