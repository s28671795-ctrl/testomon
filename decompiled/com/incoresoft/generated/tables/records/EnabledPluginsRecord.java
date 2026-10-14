/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated.tables.records;

import com.incoresoft.generated.tables.EnabledPlugins;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

public class EnabledPluginsRecord
extends UpdatableRecordImpl<EnabledPluginsRecord>
implements Record2<String, Boolean> {
    private static final long serialVersionUID = 1L;

    public void setPluginId(String value) {
        this.set(0, value);
    }

    public String getPluginId() {
        return (String)this.get(0);
    }

    public void setEnabled(Boolean value) {
        this.set(1, value);
    }

    public Boolean getEnabled() {
        return (Boolean)this.get(1);
    }

    @Override
    public Record1<String> key() {
        return (Record1)super.key();
    }

    @Override
    public Row2<String, Boolean> fieldsRow() {
        return (Row2)super.fieldsRow();
    }

    @Override
    public Row2<String, Boolean> valuesRow() {
        return (Row2)super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return EnabledPlugins.ENABLED_PLUGINS.PLUGIN_ID;
    }

    @Override
    public Field<Boolean> field2() {
        return EnabledPlugins.ENABLED_PLUGINS.ENABLED;
    }

    @Override
    public String component1() {
        return this.getPluginId();
    }

    @Override
    public Boolean component2() {
        return this.getEnabled();
    }

    @Override
    public String value1() {
        return this.getPluginId();
    }

    @Override
    public Boolean value2() {
        return this.getEnabled();
    }

    public EnabledPluginsRecord value1(String value) {
        this.setPluginId(value);
        return this;
    }

    public EnabledPluginsRecord value2(Boolean value) {
        this.setEnabled(value);
        return this;
    }

    public EnabledPluginsRecord values(String value1, Boolean value2) {
        this.value1(value1);
        this.value2(value2);
        return this;
    }

    public EnabledPluginsRecord() {
        super(EnabledPlugins.ENABLED_PLUGINS);
    }

    public EnabledPluginsRecord(String pluginId, Boolean enabled) {
        super(EnabledPlugins.ENABLED_PLUGINS);
        this.setPluginId(pluginId);
        this.setEnabled(enabled);
        this.resetChangedOnNotNull();
    }
}

