/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.cleaner;

import com.incoresoft.middleware.cleaner.CleaningCategory;

public enum VmsCleaningCategory implements CleaningCategory
{
    LOGS,
    ALERTS,
    STORAGE_DATA,
    PLUGINS_DATA;


    @Override
    public String getTypeId() {
        return this.name().toLowerCase();
    }
}

