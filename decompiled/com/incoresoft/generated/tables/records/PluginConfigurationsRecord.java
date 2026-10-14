/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.PluginConfigurations;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

public class PluginConfigurationsRecord
extends UpdatableRecordImpl<PluginConfigurationsRecord>
implements Record6<Integer, String, String, String, Integer, String> {
    private static final long serialVersionUID = 1L;

    public void setId(Integer value) {
        this.set(0, value);
    }

    public Integer getId() {
        return (Integer)this.get(0);
    }

    public void setPluginName(String value) {
        this.set(1, value);
    }

    public String getPluginName() {
        return (String)this.get(1);
    }

    public void setBackupIgnoredTables(String value) {
        this.set(2, value);
    }

    public String getBackupIgnoredTables() {
        return (String)this.get(2);
    }

    public void setManagedTables(String value) {
        this.set(3, value);
    }

    public String getManagedTables() {
        return (String)this.get(3);
    }

    public void setClientId(Integer value) {
        this.set(4, value);
    }

    public Integer getClientId() {
        return (Integer)this.get(4);
    }

    public void setPluginId(String value) {
        this.set(5, value);
    }

    public String getPluginId() {
        return (String)this.get(5);
    }

    @Override
    public Record1<Integer> key() {
        return (Record1)super.key();
    }

    @Override
    public Row6<Integer, String, String, String, Integer, String> fieldsRow() {
        return (Row6)super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, Integer, String> valuesRow() {
        return (Row6)super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.ID;
    }

    @Override
    public Field<String> field2() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.PLUGIN_NAME;
    }

    @Override
    public Field<String> field3() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.BACKUP_IGNORED_TABLES;
    }

    @Override
    public Field<String> field4() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.MANAGED_TABLES;
    }

    @Override
    public Field<Integer> field5() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.CLIENT_ID;
    }

    @Override
    public Field<String> field6() {
        return PluginConfigurations.PLUGIN_CONFIGURATIONS.PLUGIN_ID;
    }

    @Override
    public Integer component1() {
        return this.getId();
    }

    @Override
    public String component2() {
        return this.getPluginName();
    }

    @Override
    public String component3() {
        return this.getBackupIgnoredTables();
    }

    @Override
    public String component4() {
        return this.getManagedTables();
    }

    @Override
    public Integer component5() {
        return this.getClientId();
    }

    @Override
    public String component6() {
        return this.getPluginId();
    }

    @Override
    public Integer value1() {
        return this.getId();
    }

    @Override
    public String value2() {
        return this.getPluginName();
    }

    @Override
    public String value3() {
        return this.getBackupIgnoredTables();
    }

    @Override
    public String value4() {
        return this.getManagedTables();
    }

    @Override
    public Integer value5() {
        return this.getClientId();
    }

    @Override
    public String value6() {
        return this.getPluginId();
    }

    public PluginConfigurationsRecord value1(Integer value) {
        this.setId(value);
        return this;
    }

    public PluginConfigurationsRecord value2(String value) {
        this.setPluginName(value);
        return this;
    }

    public PluginConfigurationsRecord value3(String value) {
        this.setBackupIgnoredTables(value);
        return this;
    }

    public PluginConfigurationsRecord value4(String value) {
        this.setManagedTables(value);
        return this;
    }

    public PluginConfigurationsRecord value5(Integer value) {
        this.setClientId(value);
        return this;
    }

    public PluginConfigurationsRecord value6(String value) {
        this.setPluginId(value);
        return this;
    }

    public PluginConfigurationsRecord values(Integer value1, String value2, String value3, String value4, Integer value5, String value6) {
        this.value1(value1);
        this.value2(value2);
        this.value3(value3);
        this.value4(value4);
        this.value5(value5);
        this.value6(value6);
        return this;
    }

    public PluginConfigurationsRecord() {
        super(PluginConfigurations.PLUGIN_CONFIGURATIONS);
    }

    public PluginConfigurationsRecord(Integer id, String pluginName, String backupIgnoredTables, String managedTables, Integer clientId, String pluginId) {
        super(PluginConfigurations.PLUGIN_CONFIGURATIONS);
        this.setId(id);
        this.setPluginName(pluginName);
        this.setBackupIgnoredTables(backupIgnoredTables);
        this.setManagedTables(managedTables);
        this.setClientId(clientId);
        this.setPluginId(pluginId);
        this.resetChangedOnNotNull();
    }
}

