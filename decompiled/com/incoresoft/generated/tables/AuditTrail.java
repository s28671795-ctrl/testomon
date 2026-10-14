/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Indexes;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.AuditTrailRecord;
import com.incoresoft.plugin.commons.db.InstantConverter;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function10;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row10;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class AuditTrail
extends TableImpl<AuditTrailRecord> {
    private static final long serialVersionUID = 1L;
    public static final AuditTrail AUDIT_TRAIL = new AuditTrail();
    public final TableField<AuditTrailRecord, Integer> ID = AuditTrail.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<AuditTrailRecord, Instant> CREATED_AT = AuditTrail.createField(DSL.name("created_at"), SQLDataType.TIMESTAMP(3).nullable(false), this, "", new InstantConverter());
    public final TableField<AuditTrailRecord, String> SESSION_ID = AuditTrail.createField(DSL.name("session_id"), SQLDataType.VARCHAR(255), this, "");
    public final TableField<AuditTrailRecord, Integer> USER_ID = AuditTrail.createField(DSL.name("user_id"), SQLDataType.INTEGER, this, "");
    public final TableField<AuditTrailRecord, String> USER_IP = AuditTrail.createField(DSL.name("user_ip"), SQLDataType.VARCHAR(45), this, "");
    public final TableField<AuditTrailRecord, String> SOURCE_ID = AuditTrail.createField(DSL.name("source_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    public final TableField<AuditTrailRecord, String> MESSAGE = AuditTrail.createField(DSL.name("message"), SQLDataType.CLOB.nullable(false), this, "");
    public final TableField<AuditTrailRecord, Integer> CLIENT_ID = AuditTrail.createField(DSL.name("client_id"), SQLDataType.INTEGER.nullable(false), this, "");
    public final TableField<AuditTrailRecord, UUID> EVENT_CATEGORY_ID = AuditTrail.createField(DSL.name("event_category_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("'${default_uuid}'"), SQLDataType.UUID)), this, "");
    public final TableField<AuditTrailRecord, UUID> EVENT_ACTION_ID = AuditTrail.createField(DSL.name("event_action_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("'${default_uuid}'"), SQLDataType.UUID)), this, "");

    @Override
    public Class<AuditTrailRecord> getRecordType() {
        return AuditTrailRecord.class;
    }

    private AuditTrail(Name alias, Table<AuditTrailRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuditTrail(Name alias, Table<AuditTrailRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public AuditTrail(String alias) {
        this(DSL.name(alias), AUDIT_TRAIL);
    }

    public AuditTrail(Name alias) {
        this(alias, AUDIT_TRAIL);
    }

    public AuditTrail() {
        this(DSL.name("audit_trail"), (Table<AuditTrailRecord>)null);
    }

    public <O extends Record> AuditTrail(Table<O> child, ForeignKey<O, AuditTrailRecord> key) {
        super(child, key, AUDIT_TRAIL);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.AUDIT_EVENT_ACTION_INDEX, Indexes.AUDIT_EVENT_CATEGORY_INDEX);
    }

    @Override
    public Identity<AuditTrailRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<AuditTrailRecord> getPrimaryKey() {
        return Keys.PK_AUDIT_TRAIL;
    }

    @Override
    public AuditTrail as(String alias) {
        return new AuditTrail(DSL.name(alias), this);
    }

    @Override
    public AuditTrail as(Name alias) {
        return new AuditTrail(alias, this);
    }

    public AuditTrail as(Table<?> alias) {
        return new AuditTrail(alias.getQualifiedName(), this);
    }

    public AuditTrail rename(String name) {
        return new AuditTrail(DSL.name(name), null);
    }

    public AuditTrail rename(Name name) {
        return new AuditTrail(name, null);
    }

    public AuditTrail rename(Table<?> name) {
        return new AuditTrail(name.getQualifiedName(), null);
    }

    @Override
    public Row10<Integer, Instant, String, Integer, String, String, String, Integer, UUID, UUID> fieldsRow() {
        return (Row10)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function10<? super Integer, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super String, ? super Integer, ? super UUID, ? super UUID, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function10<? super Integer, ? super Instant, ? super String, ? super Integer, ? super String, ? super String, ? super String, ? super Integer, ? super UUID, ? super UUID, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

