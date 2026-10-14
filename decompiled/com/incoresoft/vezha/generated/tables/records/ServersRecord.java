/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.Servers;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

public class ServersRecord
extends UpdatableRecordImpl<ServersRecord>
implements Record5<String, String, Boolean, String, Integer> {
    private static final long serialVersionUID = 1L;

    public void setId(String value) {
        this.set(0, value);
    }

    public String getId() {
        return (String)this.get(0);
    }

    public void setName(String value) {
        this.set(1, value);
    }

    public String getName() {
        return (String)this.get(1);
    }

    public void setIsExternalAddressEnabled(Boolean value) {
        this.set(2, value);
    }

    public Boolean getIsExternalAddressEnabled() {
        return (Boolean)this.get(2);
    }

    public void setAddress(String value) {
        this.set(3, value);
    }

    public String getAddress() {
        return (String)this.get(3);
    }

    public void setPort(Integer value) {
        this.set(4, value);
    }

    public Integer getPort() {
        return (Integer)this.get(4);
    }

    @Override
    public Record1<String> key() {
        return (Record1)super.key();
    }

    @Override
    public Row5<String, String, Boolean, String, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    @Override
    public Row5<String, String, Boolean, String, Integer> valuesRow() {
        return (Row5)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Servers.SERVERS.ID;
    }

    @Override
    public Field<String> field2() {
        return Servers.SERVERS.NAME;
    }

    @Override
    public Field<Boolean> field3() {
        return Servers.SERVERS.IS_EXTERNAL_ADDRESS_ENABLED;
    }

    @Override
    public Field<String> field4() {
        return Servers.SERVERS.ADDRESS;
    }

    @Override
    public Field<Integer> field5() {
        return Servers.SERVERS.PORT;
    }

    @Override
    public String component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getName();
    }

    @Override
    public Boolean component3() {
        return this.getIsExternalAddressEnabled();
    }

    @Override
    public String component4() {
        return this.getAddress();
    }

    @Override
    public Integer component5() {
        return this.getPort();
    }

    @Override
    public String value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getName();
    }

    @Override
    public Boolean value3() {
        return this.getIsExternalAddressEnabled();
    }

    @Override
    public String value4() {
        return this.getAddress();
    }

    @Override
    public Integer value5() {
        return this.getPort();
    }

    public ServersRecord value1(String value) {
        this.setId(value);
        return this;
    }

    public ServersRecord value2(String value) {
        this.setName(value);
        return this;
    }

    public ServersRecord value3(Boolean value) {
        this.setIsExternalAddressEnabled(value);
        return this;
    }

    public ServersRecord value4(String value) {
        this.setAddress(value);
        return this;
    }

    public ServersRecord value5(Integer value) {
        this.setPort(value);
        return this;
    }

    public ServersRecord values(String value1, String value2, Boolean value3, String value4, Integer value5) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        return this;
    }

    public ServersRecord() {
        super(Servers.SERVERS);
    }

    public ServersRecord(String id, String name, Boolean isExternalAddressEnabled, String address, Integer port) {
        super(Servers.SERVERS);
        this.setId(id);
        this.setName(name);
        this.setIsExternalAddressEnabled(isExternalAddressEnabled);
        this.setAddress(address);
        this.setPort(port);
        this.resetChangedOnNotNull();
    }
}

