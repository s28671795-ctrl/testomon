/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.ApiTokensRecord;
import com.incoresoft.plugin.commons.db.InstantConverter;
import java.time.Instant;
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

public class ApiTokens
extends TableImpl<ApiTokensRecord> {
    private static final long serialVersionUID = 1L;
    public static final ApiTokens API_TOKENS = new ApiTokens();
    public final TableField<ApiTokensRecord, Integer> ID = ApiTokens.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<ApiTokensRecord, String> NAME = ApiTokens.createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<ApiTokensRecord, Integer> USER_ID = ApiTokens.createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<ApiTokensRecord, String> API_KEY = ApiTokens.createField(DSL.name("api_key"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<ApiTokensRecord, String> API_KEY_HASH = ApiTokens.createField(DSL.name("api_key_hash"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<ApiTokensRecord, Instant> ISSUED_AT = ApiTokens.createField(DSL.name("issued_at"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<ApiTokensRecord, Instant> EXPIRES_AT = ApiTokens.createField(DSL.name("expires_at"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());

    @Override
    public Class<ApiTokensRecord> getRecordType() {
        return ApiTokensRecord.class;
    }

    private ApiTokens(Name alias, Table<ApiTokensRecord> aliased) {
        this(alias, aliased, null);
    }

    private ApiTokens(Name alias, Table<ApiTokensRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public ApiTokens(String alias) {
        this(DSL.name(alias), API_TOKENS);
    }

    public ApiTokens(Name alias) {
        this(alias, API_TOKENS);
    }

    public ApiTokens() {
        this(DSL.name("api_tokens"), (Table<ApiTokensRecord>)null);
    }

    public <O extends Record> ApiTokens(Table<O> child, ForeignKey<O, ApiTokensRecord> key) {
        super(child, key, API_TOKENS);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ApiTokensRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<ApiTokensRecord> getPrimaryKey() {
        return Keys.PK_API_TOKENS;
    }

    @Override
    public ApiTokens as(String alias) {
        return new ApiTokens(DSL.name(alias), this);
    }

    @Override
    public ApiTokens as(Name alias) {
        return new ApiTokens(alias, this);
    }

    public ApiTokens as(Table<?> alias) {
        return new ApiTokens(alias.getQualifiedName(), this);
    }

    public ApiTokens rename(String name) {
        return new ApiTokens(DSL.name(name), null);
    }

    public ApiTokens rename(Name name) {
        return new ApiTokens(name, null);
    }

    public ApiTokens rename(Table<?> name) {
        return new ApiTokens(name.getQualifiedName(), null);
    }

    @Override
    public Row7<Integer, String, Integer, String, String, Instant, Instant> fieldsRow() {
        return (Row7)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function7<? super Integer, ? super String, ? super Integer, ? super String, ? super String, ? super Instant, ? super Instant, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Integer, ? super String, ? super Integer, ? super String, ? super String, ? super Instant, ? super Instant, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

