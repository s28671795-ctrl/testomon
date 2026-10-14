/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.NetworkSettings;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

public class NetworkSettingsRecord
extends UpdatableRecordImpl<NetworkSettingsRecord>
implements Record4<Integer, String, String, String> {
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

    public void setLocalMask(String value) {
        this.set(2, value);
    }

    public String getLocalMask() {
        return (String)this.get(2);
    }

    public void setPublicAddress(String value) {
        this.set(3, value);
    }

    public String getPublicAddress() {
        return (String)this.get(3);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row4<Integer, String, String, String> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    @Override
    public Row4<Integer, String, String, String> valuesRow() {
        return (Row4)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return NetworkSettings.NETWORK_SETTINGS.ID;
    }

    @Override
    public Field<String> field2() {
        return NetworkSettings.NETWORK_SETTINGS.NAME;
    }

    @Override
    public Field<String> field3() {
        return NetworkSettings.NETWORK_SETTINGS.LOCAL_MASK;
    }

    @Override
    public Field<String> field4() {
        return NetworkSettings.NETWORK_SETTINGS.PUBLIC_ADDRESS;
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
    public String component3() {
        return this.getLocalMask();
    }

    @Override
    public String component4() {
        return this.getPublicAddress();
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
    public String value3() {
        return this.getLocalMask();
    }

    @Override
    public String value4() {
        return this.getPublicAddress();
    }

    public NetworkSettingsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public NetworkSettingsRecord value2(String value) {
        this.setName(value);
        return this;
    }

    public NetworkSettingsRecord value3(String value) {
        this.setLocalMask(value);
        return this;
    }

    public NetworkSettingsRecord value4(String value) {
        this.setPublicAddress(value);
        return this;
    }

    public NetworkSettingsRecord values(Integer value1, String value2, String value3, String value4) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        return this;
    }

    public NetworkSettingsRecord() {
        super(NetworkSettings.NETWORK_SETTINGS);
    }

    public NetworkSettingsRecord(Integer id, String name, String localMask, String publicAddress) {
        super(NetworkSettings.NETWORK_SETTINGS);
        this.setId(id);
        this.setName(name);
        this.setLocalMask(localMask);
        this.setPublicAddress(publicAddress);
        this.resetChangedOnNotNull();
    }
}

