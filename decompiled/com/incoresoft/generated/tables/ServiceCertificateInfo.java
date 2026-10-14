/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables;

import com.incoresoft.generated.DefaultSchema;
import com.incoresoft.generated.Keys;
import com.incoresoft.generated.tables.records.ServiceCertificateInfoRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class ServiceCertificateInfo
extends TableImpl<ServiceCertificateInfoRecord> {
    private static final long serialVersionUID = 1L;
    public static final ServiceCertificateInfo SERVICE_CERTIFICATE_INFO = new ServiceCertificateInfo();
    public final TableField<ServiceCertificateInfoRecord, Integer> ID = ServiceCertificateInfo.createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");
    public final TableField<ServiceCertificateInfoRecord, String> CERTIFICATE_PASSWORD = ServiceCertificateInfo.createField(DSL.name("certificate_password"), SQLDataType.CLOB, this, "");

    @Override
    public Class<ServiceCertificateInfoRecord> getRecordType() {
        return ServiceCertificateInfoRecord.class;
    }

    private ServiceCertificateInfo(Name alias, Table<ServiceCertificateInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private ServiceCertificateInfo(Name alias, Table<ServiceCertificateInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public ServiceCertificateInfo(String alias) {
        this(DSL.name(alias), SERVICE_CERTIFICATE_INFO);
    }

    public ServiceCertificateInfo(Name alias) {
        this(alias, SERVICE_CERTIFICATE_INFO);
    }

    public ServiceCertificateInfo() {
        this(DSL.name("service_certificate_info"), (Table<ServiceCertificateInfoRecord>)null);
    }

    public <O extends Record> ServiceCertificateInfo(Table<O> child, ForeignKey<O, ServiceCertificateInfoRecord> key) {
        super(child, key, SERVICE_CERTIFICATE_INFO);
    }

    @Override
    public Schema getSchema() {
        return this.aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ServiceCertificateInfoRecord, Integer> getIdentity() {
        return super.getIdentity();
    }

    @Override
    public UniqueKey<ServiceCertificateInfoRecord> getPrimaryKey() {
        return Keys.PK_SERVICE_CERTIFICATE_INFO;
    }

    @Override
    public ServiceCertificateInfo as(String alias) {
        return new ServiceCertificateInfo(DSL.name(alias), this);
    }

    @Override
    public ServiceCertificateInfo as(Name alias) {
        return new ServiceCertificateInfo(alias, this);
    }

    public ServiceCertificateInfo as(Table<?> alias) {
        return new ServiceCertificateInfo(alias.getQualifiedName(), this);
    }

    public ServiceCertificateInfo rename(String name) {
        return new ServiceCertificateInfo(DSL.name(name), null);
    }

    public ServiceCertificateInfo rename(Name name) {
        return new ServiceCertificateInfo(name, null);
    }

    public ServiceCertificateInfo rename(Table<?> name) {
        return new ServiceCertificateInfo(name.getQualifiedName(), null);
    }

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    public <U> SelectField<U> mapping(Function2<? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(Records.mapping(from));
    }

    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Integer, ? super String, ? extends U> from) {
        return this.convertFrom(toType, Records.mapping(from));
    }
}

