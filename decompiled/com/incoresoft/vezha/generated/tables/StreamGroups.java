/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.StreamGroupsRecord;
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

public class StreamGroups
extends TableImpl<StreamGroupsRecord> {
    private static final long serialVersionUID = 1L;
    public static final StreamGroups STREAM_GROUPS = new StreamGroups();
    public final TableField<StreamGroupsRecord, Integer> ID = StreamGroups.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<StreamGroupsRecord, Integer> PARENT_ID = StreamGroups.createField(DSL.name("parent_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<StreamGroupsRecord, String> NAME = StreamGroups.createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<StreamGroupsRecord, Integer> CLIENT_ID = StreamGroups.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");

    @Override
    public Class<StreamGroupsRecord> getRecordType() {
        return StreamGroupsRecord.class;
    }

    private StreamGroups(Name alias, Table<StreamGroupsRecord> aliased) {
        this(alias, aliased, null);
    }

    private StreamGroups(Name alias, Table<StreamGroupsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public StreamGroups(String alias) {
        this(DSL.name(alias), STREAM_GROUPS);
    }

    public StreamGroups(Name alias) {
        this(alias, STREAM_GROUPS);
    }

    public StreamGroups() {
        this(DSL.name("stream_groups"), (Table<StreamGroupsRecord>)null);
    }

    public <O extends Record> StreamGroups(Table<O> child, ForeignKey<O, StreamGroupsRecord> key) {
        super(child, key, STREAM_GROUPS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<StreamGroupsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<StreamGroupsRecord> getPrimaryKey() {
        return Keys.PK_STREAM_GROUPS;
    }

    @Override
    public StreamGroups as(String alias) {
        return new StreamGroups(DSL.name(alias), this);
    }

    @Override
    public StreamGroups as(Name alias) {
        return new StreamGroups(alias, this);
    }

    public StreamGroups as(Table<?> alias) {
        return new StreamGroups(alias.getQualifiedName(), this);
    }

    public StreamGroups rename(String name) {
        return new StreamGroups(DSL.name(name), null);
    }

    public StreamGroups rename(Name name) {
        return new StreamGroups(name, null);
    }

    public StreamGroups rename(Table<?> name) {
        return new StreamGroups(name.getQualifiedName(), null);
    }

    @Override
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function4<? super Integer, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

