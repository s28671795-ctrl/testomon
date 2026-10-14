/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.notifications.sounds;

import com.google.inject.Inject;
import com.incoresoft.database.SoundsSettingsRepository;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.notifications.sounds.SoundItem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundsManager {
    private static final Logger log = LoggerFactory.getLogger(SoundsManager.class);
    private static final File SETTINGS_FILE = new File("sounds/settings.json");
    private static final Path SOUND_ALERT_DIR = Path.of("sounds", new String[0]);
    private final NotificationsPanel notificationsPanel;
    private final SoundsSettingsRepository repository;

    public void init() {
        if (SETTINGS_FILE.exists() && this.repository.noSettingsSet()) {
            Map<Object, Object> savedSettings = Collections.emptyMap();
            try {
                savedSettings = ((List)Serialization.objectMapper().readerForListOf(SoundItem.class).readValue(Files.readString(SETTINGS_FILE.toPath()))).stream().collect(Collectors.toMap(SoundItem::id, Function.identity()));
            }
            catch (IOException e) {
                log.error("Failed to read sound settings file", e);
            }
            Map finalSavedSettings = savedSettings;
            List<SoundItem> settings = this.notificationsPanel.getNotificationsManagers().stream().map(it -> finalSavedSettings.getOrDefault(it.getId(), SoundItem.defaultSoundItem(it.getId()))).filter(it -> it.soundFile() != null).toList();
            this.repository.addAll(settings);
        }
    }

    public List<SoundItem> getAll() {
        List<SoundItem> all = this.repository.getAll();
        Set savedInDatabase = all.stream().map(SoundItem::id).collect(Collectors.toSet());
        Set allNotificationPanelItems = this.notificationsPanel.getNotificationsManagers().stream().map(NotificationsManager::getId).collect(Collectors.toSet());
        Set existingButNotSaved = allNotificationPanelItems.stream().filter(it -> !savedInDatabase.contains(it)).collect(Collectors.toSet());
        if (!existingButNotSaved.isEmpty()) {
            List<SoundItem> newItems = existingButNotSaved.stream().map(SoundItem::defaultSoundItem).toList();
            this.repository.addAll(newItems);
            all = this.repository.getAll();
        }
        return all.stream().filter(it -> allNotificationPanelItems.contains(it.id())).toList();
    }

    public Optional<SoundItem> get(String id) {
        return this.repository.get(id);
    }

    private void add(SoundItem item) {
        this.repository.add(item);
    }

    public void updateAll(List<SoundItem> items) {
        this.repository.updateAll(items);
    }

    public void toggle(String id) {
        this.repository.toggle(id);
    }

    public void uploadFile(String id, InputStream fileStream) throws IOException {
        Path filePath = SOUND_ALERT_DIR.resolve(id + ".mp3");
        Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        this.repository.setSoundFile(id, filePath.toString());
    }

    public void resetSoundFile(String id) {
        this.repository.resetSoundFile(id);
    }

    @Inject
    public SoundsManager(NotificationsPanel notificationsPanel, SoundsSettingsRepository repository) {
        this.notificationsPanel = notificationsPanel;
        this.repository = repository;
    }
}

