/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.axisstationpro;

import com.google.inject.Inject;
import com.incoresoft.vezha.axisstationpro.AxisStationProSettings;
import com.incoresoft.vezha.database.AxisStationProSettingsRepository;

public class AxisStationProSettingsManager {
    private final AxisStationProSettingsRepository repository;

    public AxisStationProSettings getSettings() {
        return this.repository.getSettings();
    }

    public void saveSettings(AxisStationProSettings settings) {
        this.repository.saveSettings(settings);
    }

    @Inject
    public AxisStationProSettingsManager(AxisStationProSettingsRepository repository) {
        this.repository = repository;
    }
}

