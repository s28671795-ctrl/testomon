/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.settings;

import com.google.inject.Inject;
import com.incoresoft.database.SettingsRepository;
import com.incoresoft.dto.settings.GeneralSettingsDTO;
import com.incoresoft.middleware.settings.GlobalSettings;
import com.incoresoft.middleware.settings.OnSettingsChangeListener;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsManager
implements GlobalSettings {
    private static final Logger log = LoggerFactory.getLogger(SettingsManager.class);
    public static final String METADATA_HISTORY_DAYS = "metadata_history_days";
    public static final String IMAGE_HISTORY_DAYS = "image_history_days";
    public static final String ALERTS_HISTORY_DAYS = "alerts_history_days";
    private static final int SAVING_PERIOD_DEFAULT = 30;
    private final List<OnSettingsChangeListener> settingsChangeListeners = Collections.synchronizedList(new ArrayList());
    private final Map<String, String> settings = new ConcurrentHashMap<String, String>();
    private final SettingsRepository repository;

    @Inject
    public void init() {
        Map<String, String> loadedSettings = this.repository.getSettings();
        this.settings.putAll(loadedSettings);
        loadedSettings.forEach((key, value) -> this.settingsChangeListeners.forEach(listener -> {
            if (listener.key().equals(key) || listener.key().equals("ALL_SETTINGS")) {
                listener.onSettingsChange((String)key, value);
            }
        }));
        this.settings.computeIfAbsent(METADATA_HISTORY_DAYS, key -> String.valueOf(30));
        this.settings.computeIfAbsent(IMAGE_HISTORY_DAYS, key -> String.valueOf(30));
        this.settings.computeIfAbsent(ALERTS_HISTORY_DAYS, key -> String.valueOf(30));
    }

    @Override
    public Map<String, Object> getSettingsMap() {
        return Collections.unmodifiableMap(this.settings);
    }

    @Override
    public String getValue(String key) {
        return this.settings.get(key);
    }

    @Override
    public void addListener(OnSettingsChangeListener listener) {
        this.settingsChangeListeners.add(listener);
    }

    @Override
    public void removeListener(OnSettingsChangeListener listener) {
        this.settingsChangeListeners.remove(listener);
    }

    public GeneralSettingsDTO getGeneralSettings() {
        return this.getGeneralSettings("");
    }

    public GeneralSettingsDTO getGeneralSettings(String apiToken) {
        return new GeneralSettingsDTO(apiToken, this.settings.get("google_maps_api_key"), Double.parseDouble(this.settings.getOrDefault("user_latitude", "0.0")), Double.parseDouble(this.settings.getOrDefault("user_longitude", "0.0")), (int)this.getMetadataHistorySavingPeriod().toDays(), (int)this.getStorageHistorySavingPeriod().toDays(), (int)this.getAlertsHistorySavingPeriod().toDays());
    }

    public void saveGeneralSettings(GeneralSettingsDTO dto) {
        Map<String, String> settingsMap = dto.getSettingsMap();
        this.settings.putAll(settingsMap);
        this.repository.saveSettings(this.settings);
    }

    public void saveValue(String key, String value) {
        this.settings.put(key, value);
        this.repository.saveSettings(this.settings);
    }

    public Duration getMetadataHistorySavingPeriod() {
        return this.parseSavingPeriodDuration(METADATA_HISTORY_DAYS);
    }

    public Duration getAlertsHistorySavingPeriod() {
        return this.parseSavingPeriodDuration(ALERTS_HISTORY_DAYS);
    }

    public Duration getStorageHistorySavingPeriod() {
        return this.parseSavingPeriodDuration(IMAGE_HISTORY_DAYS);
    }

    private Duration parseSavingPeriodDuration(String settingKeyName) {
        try {
            return Duration.ofDays(Integer.parseInt(this.settings.get(settingKeyName)));
        }
        catch (Exception e) {
            return Duration.ofDays(30L);
        }
    }

    @Inject
    public SettingsManager(SettingsRepository repository) {
        this.repository = repository;
    }
}

