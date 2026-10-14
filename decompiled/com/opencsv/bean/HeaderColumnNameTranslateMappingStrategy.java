/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.HeaderNameBaseMappingStrategy;
import java.util.HashMap;
import java.util.Map;

public class HeaderColumnNameTranslateMappingStrategy<T>
extends HeaderNameBaseMappingStrategy<T> {
    private final Map<String, String> columnMapping = new HashMap<String, String>();

    public HeaderColumnNameTranslateMappingStrategy() {
    }

    public HeaderColumnNameTranslateMappingStrategy(boolean forceCorrectRecordLength) {
        super(forceCorrectRecordLength);
    }

    @Override
    public String getColumnName(int col) {
        String name = this.headerIndex.getByPosition(col);
        if (name != null) {
            name = this.columnMapping.get(name.toUpperCase());
        }
        return name;
    }

    public Map<String, String> getColumnMapping() {
        return this.columnMapping;
    }

    public void setColumnMapping(Map<String, String> columnMapping) {
        this.columnMapping.clear();
        for (Map.Entry<String, String> entry : columnMapping.entrySet()) {
            this.columnMapping.put(entry.getKey().toUpperCase(), entry.getValue());
        }
        if (this.getType() != null) {
            this.loadFieldMap();
        }
    }
}

