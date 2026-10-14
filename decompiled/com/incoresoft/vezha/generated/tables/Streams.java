/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables;

import com.incoresoft.plugin.commons.db.InstantConverter;
import com.incoresoft.vezha.generated.DefaultSchema;
import com.incoresoft.vezha.generated.Keys;
import com.incoresoft.vezha.generated.tables.records.StreamsRecord;
import java.time.Instant;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function22;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row22;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class Streams
extends TableImpl<StreamsRecord> {
    private static final long serialVersionUID = 1L;
    public static final Streams STREAMS = new Streams();
    public final TableField<StreamsRecord, Integer> ID = Streams.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<StreamsRecord, String> NAME = Streams.createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<StreamsRecord, String> PATH = Streams.createField(DSL.name("path"), SQLDataType.CLOB, this, "");
    public final TableField<StreamsRecord, Integer> WIDTH = Streams.createField(DSL.name("width"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<StreamsRecord, Integer> HEIGHT = Streams.createField(DSL.name("height"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<StreamsRecord, String> FILE_NAME = Streams.createField(DSL.name("file_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<StreamsRecord, Byte> STATUS = Streams.createField(DSL.name("status"), SQLDataType.TINYINT.nullable(false), this, "");
    public final TableField<StreamsRecord, Instant> CREATED_AT = Streams.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3).nullable(false).defaultValue(DSL.field(DSL.raw("LOCALTIMESTAMP"), SQLDataType.TIMESTAMP)), this, "", new InstantConverter());
    public final TableField<StreamsRecord, Double> LAT = Streams.createField(DSL.name("lat"), SQLDataType.DOUBLE.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.DOUBLE)), this, "");
    public final TableField<StreamsRecord, Double> LNG = Streams.createField(DSL.name("lng"), SQLDataType.DOUBLE.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.DOUBLE)), this, "");
    public final TableField<StreamsRecord, String> TYPE = Streams.createField(DSL.name("type"), SQLDataType.VARCHAR(45).nullable(false).defaultValue(DSL.field(DSL.raw("'rtsp'"), SQLDataType.VARCHAR)), this, "");
    public final TableField<StreamsRecord, UUID> UUID = Streams.createField(DSL.name("uuid"), SQLDataType.UUID.nullable(false), this, "");
    public final TableField<StreamsRecord, String> ADDRESS = Streams.createField(DSL.name("address"), SQLDataType.VARCHAR(255).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "");
    public final TableField<StreamsRecord, String> PARAMS = Streams.createField(DSL.name("params"), SQLDataType.CLOB, this, "");
    public final TableField<StreamsRecord, String> AUTH = Streams.createField(DSL.name("auth"), SQLDataType.CLOB, this, "");
    public final TableField<StreamsRecord, Integer> DIRECTION = Streams.createField(DSL.name("direction"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.INTEGER)), this, "");
    public final TableField<StreamsRecord, Integer> CLIENT_ID = Streams.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<StreamsRecord, String> CODEC = Streams.createField(DSL.name("codec"), SQLDataType.VARCHAR(10), this, "");
    public final TableField<StreamsRecord, String> TIMEZONE = Streams.createField(DSL.name("timezone"), SQLDataType.VARCHAR(100), this, "");
    public final TableField<StreamsRecord, Long> DURATION = Streams.createField(DSL.name("duration"), SQLDataType.BIGINT, this, "");
    public final TableField<StreamsRecord, String> RESTRICTIONS = Streams.createField(DSL.name("restrictions"), SQLDataType.CLOB, this, "");
    public final TableField<StreamsRecord, Integer> PARENT_ID = Streams.createField(DSL.name("parent_id"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("'0'"), SQLDataType.INTEGER)), this, "");

    @Override
    public Class<StreamsRecord> getRecordType() {
        return StreamsRecord.class;
    }

    private Streams(Name alias, Table<StreamsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Streams(Name alias, Table<StreamsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public Streams(String alias) {
        this(DSL.name(alias), STREAMS);
    }

    public Streams(Name alias) {
        this(alias, STREAMS);
    }

    public Streams() {
        this(DSL.name("streams"), (Table<StreamsRecord>)null);
    }

    public <O extends Record> Streams(Table<O> child, ForeignKey<O, StreamsRecord> key) {
        super(child, key, STREAMS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<StreamsRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<StreamsRecord> getPrimaryKey() {
        return Keys.PK_STREAMS;
    }

    @Override
    public Streams as(String alias) {
        return new Streams(DSL.name(alias), this);
    }

    @Override
    public Streams as(Name alias) {
        return new Streams(alias, this);
    }

    public Streams as(Table<?> alias) {
        return new Streams(alias.getQualifiedName(), this);
    }

    public Streams rename(String name) {
        return new Streams(DSL.name(name), null);
    }

    public Streams rename(Name name) {
        return new Streams(name, null);
    }

    public Streams rename(Table<?> name) {
        return new Streams(name.getQualifiedName(), null);
    }

    @Override
    public Row22<Integer, String, String, Integer, Integer, String, Byte, Instant, Double, Double, String, UUID, String, String, String, Integer, Integer, String, String, Long, String, Integer> fieldsRow() {
        return (Row22)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function22<? super Integer, ? super String, ? super String, ? super Integer, ? super Integer, ? super String, ? super Byte, ? super Instant, ? super Double, ? super Double, ? super String, ? super UUID, ? super String, ? super String, ? super String, ? super Integer, ? super Integer, ? super String, ? super String, ? super Long, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function22<? super Integer, ? super String, ? super String, ? super Integer, ? super Integer, ? super String, ? super Byte, ? super Instant, ? super Double, ? super Double, ? super String, ? super UUID, ? super String, ? super String, ? super String, ? super Integer, ? super Integer, ? super String, ? super String, ? super Long, ? super String, ? super Integer, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

