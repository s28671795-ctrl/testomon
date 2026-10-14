/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.EventManagerRecord;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class EventManager
extends TableImpl<EventManagerRecord> {
    private static final long serialVersionUID = 1L;
    public static final EventManager EVENT_MANAGER = new EventManager();
    public final TableField<EventManagerRecord, Integer> ID = EventManager.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<EventManagerRecord, String> UUID = EventManager.createField(DSL.name("uuid"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<EventManagerRecord, String> TITLE = EventManager.createField(DSL.name("title"), SQLDataType.CLOB.defaultValue(DSL.field(DSL.raw("''"), SQLDataType.CLOB)), this, "");
    public final TableField<EventManagerRecord, String> DESCRIPTION = EventManager.createField(DSL.name("description"), SQLDataType.CLOB.defaultValue(DSL.field(DSL.raw("''"), SQLDataType.CLOB)), this, "");
    public final TableField<EventManagerRecord, Instant> CREATED_AT = EventManager.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3), this, "", new InstantConverter());
    public final TableField<EventManagerRecord, String> NODES = EventManager.createField(DSL.name("nodes"), SQLDataType.CLOB.defaultValue(DSL.field(DSL.raw("''"), SQLDataType.CLOB)), this, "");
    public final TableField<EventManagerRecord, Integer> CLIENT_ID = EventManager.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");

    @Override
    public Class<EventManagerRecord> getRecordType() {
        return EventManagerRecord.class;
    }

    private EventManager(Name alias, Table<EventManagerRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventManager(Name alias, Table<EventManagerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public EventManager(String alias) {
        this(DSL.name(alias), EVENT_MANAGER);
    }

    public EventManager(Name alias) {
        this(alias, EVENT_MANAGER);
    }

    public EventManager() {
        this(DSL.name("event_manager"), (Table<EventManagerRecord>)null);
    }

    public <O extends Record> EventManager(Table<O> child, ForeignKey<O, EventManagerRecord> key) {
        super(child, key, EVENT_MANAGER);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<EventManagerRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<EventManagerRecord> getPrimaryKey() {
        return Keys.PK_EVENT_MANAGER_TEMP;
    }

    @Override
    public List<UniqueKey<EventManagerRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_7);
    }

    @Override
    public EventManager as(String alias) {
        return new EventManager(DSL.name(alias), this);
    }

    @Override
    public EventManager as(Name alias) {
        return new EventManager(alias, this);
    }

    public EventManager as(Table<?> alias) {
        return new EventManager(alias.getQualifiedName(), this);
    }

    public EventManager rename(String name) {
        return new EventManager(DSL.name(name), null);
    }

    public EventManager rename(Name name) {
        return new EventManager(name, null);
    }

    public EventManager rename(Table<?> name) {
        return new EventManager(name.getQualifiedName(), null);
    }

    @Override
    public Row7<Integer, String, String, String, Instant, String, Integer> fieldsRow() {
        return (Row7)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function7<? super Integer, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Integer, ? super String, ? super String, ? super String, ? super Instant, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

