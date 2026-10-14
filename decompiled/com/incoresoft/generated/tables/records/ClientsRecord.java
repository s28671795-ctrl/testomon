/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.Clients;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

public class ClientsRecord
extends UpdatableRecordImpl<ClientsRecord>
implements Record9<Integer, String, String, String, String, String, String, String, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setClientName(String value) {
        this.set(1, value);
    }

    public String getClientName() {
        return (String)this.get(1);
    }

    public void setCountry(String value) {
        this.set(2, value);
    }

    public String getCountry() {
        return (String)this.get(2);
    }

    public void setCity(String value) {
        this.set(3, value);
    }

    public String getCity() {
        return (String)this.get(3);
    }

    public void setAddress(String value) {
        this.set(4, value);
    }

    public String getAddress() {
        return (String)this.get(4);
    }

    public void setZipCode(String value) {
        this.set(5, value);
    }

    public String getZipCode() {
        return (String)this.get(5);
    }

    public void setEmail(String value) {
        this.set(6, value);
    }

    public String getEmail() {
        return (String)this.get(6);
    }

    public void setPhone(String value) {
        this.set(7, value);
    }

    public String getPhone() {
        return (String)this.get(7);
    }

    public void setComment(String value) {
        this.set(8, value);
    }

    public String getComment() {
        return (String)this.get(8);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row9<Integer, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row9)super.fieldsRow();
    }

    @Override
    public Row9<Integer, String, String, String, String, String, String, String, String> valuesRow() {
        return (Row9)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Clients.CLIENTS.ID;
    }

    @Override
    public Field<String> field2() {
        return Clients.CLIENTS.CLIENT_NAME;
    }

    @Override
    public Field<String> field3() {
        return Clients.CLIENTS.COUNTRY;
    }

    @Override
    public Field<String> field4() {
        return Clients.CLIENTS.CITY;
    }

    @Override
    public Field<String> field5() {
        return Clients.CLIENTS.ADDRESS;
    }

    @Override
    public Field<String> field6() {
        return Clients.CLIENTS.ZIP_CODE;
    }

    @Override
    public Field<String> field7() {
        return Clients.CLIENTS.EMAIL;
    }

    @Override
    public Field<String> field8() {
        return Clients.CLIENTS.PHONE;
    }

    @Override
    public Field<String> field9() {
        return Clients.CLIENTS.COMMENT;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getClientName();
    }

    @Override
    public String component3() {
        return this.getCountry();
    }

    @Override
    public String component4() {
        return this.getCity();
    }

    @Override
    public String component5() {
        return this.getAddress();
    }

    @Override
    public String component6() {
        return this.getZipCode();
    }

    @Override
    public String component7() {
        return this.getEmail();
    }

    @Override
    public String component8() {
        return this.getPhone();
    }

    @Override
    public String component9() {
        return this.getComment();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getClientName();
    }

    @Override
    public String value3() {
        return this.getCountry();
    }

    @Override
    public String value4() {
        return this.getCity();
    }

    @Override
    public String value5() {
        return this.getAddress();
    }

    @Override
    public String value6() {
        return this.getZipCode();
    }

    @Override
    public String value7() {
        return this.getEmail();
    }

    @Override
    public String value8() {
        return this.getPhone();
    }

    @Override
    public String value9() {
        return this.getComment();
    }

    public ClientsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public ClientsRecord value2(String value) {
        this.setClientName(value);
        return this;
    }

    public ClientsRecord value3(String value) {
        this.setCountry(value);
        return this;
    }

    public ClientsRecord value4(String value) {
        this.setCity(value);
        return this;
    }

    public ClientsRecord value5(String value) {
        this.setAddress(value);
        return this;
    }

    public ClientsRecord value6(String value) {
        this.setZipCode(value);
        return this;
    }

    public ClientsRecord value7(String value) {
        this.setEmail(value);
        return this;
    }

    public ClientsRecord value8(String value) {
        this.setPhone(value);
        return this;
    }

    public ClientsRecord value9(String value) {
        this.setComment(value);
        return this;
    }

    public ClientsRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        this.value7(value7);
        this.value8(value8);
        this.value9(value9);
        return this;
    }

    public ClientsRecord() {
        super(Clients.CLIENTS);
    }

    public ClientsRecord(Integer id, String clientName, String country, String city, String address, String zipCode, String email, String phone, String comment) {
        super(Clients.CLIENTS);
        this.setId(id);
        this.setClientName(clientName);
        this.setCountry(country);
        this.setCity(city);
        this.setAddress(address);
        this.setZipCode(zipCode);
        this.setEmail(email);
        this.setPhone(phone);
        this.setComment(comment);
        this.resetChangedOnNotNull();
    }
}

