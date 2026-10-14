/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.vezha.axisstationpro.AxisStationProSettings;
import com.incoresoft.vezha.generated.Tables;
import org.jooq.DSLContext;

public class AxisStationProSettingsRepository {
    private final DSLContext context;

    public AxisStationProSettings getSettings() {
        return this.context.selectFrom(Tables.AXIS_STATION_PRO_SETTINGS).fetchOptional(it -> new AxisStationProSettings(it.getHost(), it.getPort(), it.getUsername(), it.getPassword())).orElseGet(() -> new AxisStationProSettings("", null, "", ""));
    }

    public void saveSettings(AxisStationProSettings settings) {
        this.context.transaction(configuration -> {
            DSLContext transaction = configuration.dsl();
            transaction.truncate(Tables.AXIS_STATION_PRO_SETTINGS).execute();
            transaction.insertInto(Tables.AXIS_STATION_PRO_SETTINGS).set(Tables.AXIS_STATION_PRO_SETTINGS.HOST, settings.host()).set(Tables.AXIS_STATION_PRO_SETTINGS.PORT, settings.port()).set(Tables.AXIS_STATION_PRO_SETTINGS.USERNAME, settings.username()).set(Tables.AXIS_STATION_PRO_SETTINGS.PASSWORD, settings.password()).execute();
        });
    }

    @Inject
    public AxisStationProSettingsRepository(DSLContext context) {
        this.context = context;
    }
}

