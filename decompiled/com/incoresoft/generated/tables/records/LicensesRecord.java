/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.Licenses;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

public class LicensesRecord
extends UpdatableRecordImpl<LicensesRecord>
implements Record2<String, String> {
    private static final long serialVersionUID = 1L;

    public void setLicenseKey(String value) {
        this.set(0, value);
    }

    public String getLicenseKey() {
        return (String)this.get(0);
    }

    public void setLicenseText(String value) {
        this.set(1, value);
    }

    public String getLicenseText() {
        return (String)this.get(1);
    }

    @Override
    public Record1<String> key() {
        return (Record1)super.key();
    }

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Licenses.LICENSES.LICENSE_KEY;
    }

    @Override
    public Field<String> field2() {
        return Licenses.LICENSES.LICENSE_TEXT;
    }

    @Override
    public String component1() {
        return this.getLicenseKey();
    }

    @Override
    public String component2() {
        return this.getLicenseText();
    }

    @Override
    public String value1() {
        return this.getLicenseKey();
    }

    @Override
    public String value2() {
        return this.getLicenseText();
    }

    public LicensesRecord value1(String value) {
        this.setLicenseKey(value);
        return this;
    }

    public LicensesRecord value2(String value) {
        this.setLicenseText(value);
        return this;
    }

    public LicensesRecord values(String value1, String value2) {
        this.value1(value1);
        this.value2(value2);
        return this;
    }

    public LicensesRecord() {
        super(Licenses.LICENSES);
    }

    public LicensesRecord(String licenseKey, String licenseText) {
        super(Licenses.LICENSES);
        this.setLicenseKey(licenseKey);
        this.setLicenseText(licenseText);
        this.resetChangedOnNotNull();
    }
}

