/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.ServiceCertificateInfo;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

public class ServiceCertificateInfoRecord
extends UpdatableRecordImpl<ServiceCertificateInfoRecord>
implements Record2<Integer, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setCertificatePassword(String value) {
        this.set(1, value);
    }

    public String getCertificatePassword() {
        return (String)this.get(1);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO.ID;
    }

    @Override
    public Field<String> field2() {
        return ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO.CERTIFICATE_PASSWORD;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getCertificatePassword();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getCertificatePassword();
    }

    public ServiceCertificateInfoRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public ServiceCertificateInfoRecord value2(String value) {
        this.setCertificatePassword(value);
        return this;
    }

    public ServiceCertificateInfoRecord values(Integer value1, String value2) {
        this.value1(value1);
        this.value2(value2);
        return this;
    }

    public ServiceCertificateInfoRecord() {
        super(ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO);
    }

    public ServiceCertificateInfoRecord(Integer id, String certificatePassword) {
        super(ServiceCertificateInfo.SERVICE_CERTIFICATE_INFO);
        this.setId(id);
        this.setCertificatePassword(certificatePassword);
        this.resetChangedOnNotNull();
    }
}

