/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.ApiTokens;
import java.time.Instant;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

public class ApiTokensRecord
extends UpdatableRecordImpl<ApiTokensRecord>
implements Record7<Integer, String, Integer, String, String, Instant, Instant> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setName(String value) {
        this.set(1, value);
    }

    public String getName() {
        return (String)this.get(1);
    }

    public void setUserId(Integer value) {
        this.set(2, value);
    }

    public Integer getUserId() {
        return (Integer)this.get(2);
    }

    public void setApiKey(String value) {
        this.set(3, value);
    }

    public String getApiKey() {
        return (String)this.get(3);
    }

    public void setApiKeyHash(String value) {
        this.set(4, value);
    }

    public String getApiKeyHash() {
        return (String)this.get(4);
    }

    public void setIssuedAt(Instant value) {
        this.set(5, value);
    }

    public Instant getIssuedAt() {
        return (Instant)this.get(5);
    }

    public void setExpiresAt(Instant value) {
        this.set(6, value);
    }

    public Instant getExpiresAt() {
        return (Instant)this.get(6);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row7<Integer, String, Integer, String, String, Instant, Instant> fieldsRow() {
        return (Row7)super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, Integer, String, String, Instant, Instant> valuesRow() {
        return (Row7)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return ApiTokens.API_TOKENS.ID;
    }

    @Override
    public Field<String> field2() {
        return ApiTokens.API_TOKENS.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return ApiTokens.API_TOKENS.USER_ID;
    }

    @Override
    public Field<String> field4() {
        return ApiTokens.API_TOKENS.API_KEY;
    }

    @Override
    public Field<String> field5() {
        return ApiTokens.API_TOKENS.API_KEY_HASH;
    }

    @Override
    public Field<Instant> field6() {
        return ApiTokens.API_TOKENS.ISSUED_AT;
    }

    @Override
    public Field<Instant> field7() {
        return ApiTokens.API_TOKENS.EXPIRES_AT;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getName();
    }

    @Override
    public Integer component3() {
        return this.getUserId();
    }

    @Override
    public String component4() {
        return this.getApiKey();
    }

    @Override
    public String component5() {
        return this.getApiKeyHash();
    }

    @Override
    public Instant component6() {
        return this.getIssuedAt();
    }

    @Override
    public Instant component7() {
        return this.getExpiresAt();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getName();
    }

    @Override
    public Integer value3() {
        return this.getUserId();
    }

    @Override
    public String value4() {
        return this.getApiKey();
    }

    @Override
    public String value5() {
        return this.getApiKeyHash();
    }

    @Override
    public Instant value6() {
        return this.getIssuedAt();
    }

    @Override
    public Instant value7() {
        return this.getExpiresAt();
    }

    public ApiTokensRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public ApiTokensRecord value2(String value) {
        this.setName(value);
        return this;
    }

    public ApiTokensRecord value3(Integer value) {
        this.setUserId(value);
        return this;
    }

    public ApiTokensRecord value4(String value) {
        this.setApiKey(value);
        return this;
    }

    public ApiTokensRecord value5(String value) {
        this.setApiKeyHash(value);
        return this;
    }

    public ApiTokensRecord value6(Instant value) {
        this.setIssuedAt(value);
        return this;
    }

    public ApiTokensRecord value7(Instant value) {
        this.setExpiresAt(value);
        return this;
    }

    public ApiTokensRecord values(Integer value1, String value2, Integer value3, String value4, String value5, Instant value6, Instant value7) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        return this;
    }

    public ApiTokensRecord() {
        super(ApiTokens.API_TOKENS);
    }

    public ApiTokensRecord(Integer id, String name, Integer userId, String apiKey, String apiKeyHash, Instant issuedAt, Instant expiresAt) {
        super(ApiTokens.API_TOKENS);
        this.setId(id);
        this.setName(name);
        this.setUserId(userId);
        this.setApiKey(apiKey);
        this.setApiKeyHash(apiKeyHash);
        this.setIssuedAt(issuedAt);
        this.setExpiresAt(expiresAt);
        this.resetChangedOnNotNull();
    }
}

