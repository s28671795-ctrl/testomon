/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.generated.Tables;
import java.util.HashMap;
import java.util.Map;
import org.jooq.DSLContext;

public class SettingsRepository {
    private final DSLContext create;

    public Map<String, String> getSettings() {
        HashMap<String, String> result = new HashMap<String, String>();
        this.create.selectFrom(Tables.SYSTEM_SETTINGS).stream().forEach(it -> result.put(it.getVariableName(), it.getValue()));
        return result;
    }

    public void saveSettings(Map<String, String> settings) {
        this.create.deleteFrom(Tables.SYSTEM_SETTINGS).execute();
        this.create.batch(settings.entrySet().stream().map(it -> this.create.insertInto(Tables.SYSTEM_SETTINGS, Tables.SYSTEM_SETTINGS.VALUE, Tables.SYSTEM_SETTINGS.VARIABLE_NAME).values((String)it.getValue(), (String)it.getKey()).onDuplicateKeyUpdate().set(Tables.SYSTEM_SETTINGS.VALUE, (String)it.getValue())).toList()).execute();
    }

    @Inject
    public SettingsRepository(DSLContext create) {
        this.create = create;
    }
}

