/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.notifications.sounds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.dto.sounds.SoundsDTO;

public record SoundItem(@JsonProperty(value="id") String id, @JsonProperty(value="sound_file") String soundFile, @JsonProperty(value="enabled") boolean enabled) {
    public static final String DEFAULT_SOUND_FILE = "sounds/alert.mp3";

    public SoundItem(SoundsDTO dto) {
        this(dto.id(), dto.soundFile(), dto.enabled());
    }

    public static SoundItem defaultSoundItem(String id) {
        return new SoundItem(id, DEFAULT_SOUND_FILE, true);
    }
}

