/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated.tables.records;

import com.incoresoft.vezha.generated.tables.AnalyticsGroups;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

public class AnalyticsGroupsRecord
extends UpdatableRecordImpl<AnalyticsGroupsRecord>
implements Record5<Integer, String, Integer, String, Integer> {
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

    public void setParentId(Integer value) {
        this.set(2, value);
    }

    public Integer getParentId() {
        return (Integer)this.get(2);
    }

    public void setPluginName(String value) {
        this.set(3, value);
    }

    public String getPluginName() {
        return (String)this.get(3);
    }

    public void setClientId(Integer value) {
        this.set(4, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(4);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row5<Integer, String, Integer, String, Integer> fieldsRow() {
        return (Row5)super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, Integer, String, Integer> valuesRow() {
        return (Row5)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AnalyticsGroups.ANALYTICS_GROUPS.ID;
    }

    @Override
    public Field<String> field2() {
        return AnalyticsGroups.ANALYTICS_GROUPS.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return AnalyticsGroups.ANALYTICS_GROUPS.PARENT_ID;
    }

    @Override
    public Field<String> field4() {
        return AnalyticsGroups.ANALYTICS_GROUPS.PLUGIN_NAME;
    }

    @Override
    public Field<Integer> field5() {
        return AnalyticsGroups.ANALYTICS_GROUPS.CLIENT_ID;
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
        return this.getParentId();
    }

    @Override
    public String component4() {
        return this.getPluginName();
    }

    @Override
    public Integer component5() {
        return this.getClientId();
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
        return this.getParentId();
    }

    @Override
    public String value4() {
        return this.getPluginName();
    }

    @Override
    public Integer value5() {
        return this.getClientId();
    }

    public AnalyticsGroupsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public AnalyticsGroupsRecord value2(String value) {
        this.setName(value);
        return this;
    }

    public AnalyticsGroupsRecord value3(Integer value) {
        this.setParentId(value);
        return this;
    }

    public AnalyticsGroupsRecord value4(String value) {
        this.setPluginName(value);
        return this;
    }

    public AnalyticsGroupsRecord value5(Integer value) {
        this.setClientId(value);
        return this;
    }

    public AnalyticsGroupsRecord values(Integer value1, String value2, Integer value3, String value4, Integer value5) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        return this;
    }

    public AnalyticsGroupsRecord() {
        super(AnalyticsGroups.ANALYTICS_GROUPS);
    }

    public AnalyticsGroupsRecord(Integer id, String name, Integer parentId, String pluginName, Integer clientId) {
        super(AnalyticsGroups.ANALYTICS_GROUPS);
        this.setId(id);
        this.setName(name);
        this.setParentId(parentId);
        this.setPluginName(pluginName);
        this.setClientId(clientId);
        this.resetChangedOnNotNull();
    }
}

