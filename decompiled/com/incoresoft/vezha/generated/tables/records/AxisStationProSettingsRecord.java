/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.AxisStationProSettings;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;

public class AxisStationProSettingsRecord
extends TableRecordImpl<AxisStationProSettingsRecord>
implements Record4<String, Integer, String, String> {
    private static final long serialVersionUID = 1L;

    public void setHost(String value) {
        this.set(0, value);
    }

    public String getHost() {
        return (String)this.get(0);
    }

    public void setPort(Integer value) {
        this.set(1, value);
    }

    public Integer getPort() {
        return (Integer)this.get(1);
    }

    public void setUsername(String value) {
        this.set(2, value);
    }

    public String getUsername() {
        return (String)this.get(2);
    }

    public void setPassword(String value) {
        this.set(3, value);
    }

    public String getPassword() {
        return (String)this.get(3);
    }

    @Override
    public Row4<String, Integer, String, String> fieldsRow() {
        return (Row4)super.fieldsRow();
    }

    @Override
    public Row4<String, Integer, String, String> valuesRow() {
        return (Row4)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return AxisStationProSettings.AXIS_STATION_PRO_SETTINGS.HOST;
    }

    @Override
    public Field<Integer> field2() {
        return AxisStationProSettings.AXIS_STATION_PRO_SETTINGS.PORT;
    }

    @Override
    public Field<String> field3() {
        return AxisStationProSettings.AXIS_STATION_PRO_SETTINGS.USERNAME;
    }

    @Override
    public Field<String> field4() {
        return AxisStationProSettings.AXIS_STATION_PRO_SETTINGS.PASSWORD;
    }

    @Override
    public String component1() {
        return this.getHost();
    }

    @Override
    public Integer component2() {
        return this.getPort();
    }

    @Override
    public String component3() {
        return this.getUsername();
    }

    @Override
    public String component4() {
        return this.getPassword();
    }

    @Override
    public String value1() {
        return this.getHost();
    }

    @Override
    public Integer value2() {
        return this.getPort();
    }

    @Override
    public String value3() {
        return this.getUsername();
    }

    @Override
    public String value4() {
        return this.getPassword();
    }

    public AxisStationProSettingsRecord value1(String value) {
        this.setHost(value);
        return this;
    }

    public AxisStationProSettingsRecord value2(Integer value) {
        this.setPort(value);
        return this;
    }

    public AxisStationProSettingsRecord value3(String value) {
        this.setUsername(value);
        return this;
    }

    public AxisStationProSettingsRecord value4(String value) {
        this.setPassword(value);
        return this;
    }

    public AxisStationProSettingsRecord values(String value1, Integer value2, String value3, String value4) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        return this;
    }

    public AxisStationProSettingsRecord() {
        super(AxisStationProSettings.AXIS_STATION_PRO_SETTINGS);
    }

    public AxisStationProSettingsRecord(String host2, Integer port, String username, String password) {
        super(AxisStationProSettings.AXIS_STATION_PRO_SETTINGS);
        this.setHost(host2);
        this.setPort(port);
        this.setUsername(username);
        this.setPassword(password);
        this.resetChangedOnNotNull();
    }
}

