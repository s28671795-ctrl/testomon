/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.sounds;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.notifications.sounds.SoundItem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="SoundsDTO")
public record SoundsDTO(@JsonProperty(value="id") @JSONField(name="id") @Schema(name="id", description="Plugin notification panel ID", implementation=String.class, example="object_in_zone") String id, @JsonProperty(value="title") @JSONField(name="title") @Schema(name="title", description="Localization key for this plugin name", example="OBJECT_IN_ZONE_SIDEBAR") String title, @JsonProperty(value="sound_file") @JSONField(name="sound_file") @Schema(name="sound_file", description="Name of sound file", example="audio/alert.mp3") String soundFile, @JsonProperty(value="enabled") @JSONField(name="enabled") @Schema(name="enabled", description="Notification sound enabled / disabled", implementation=Boolean.class, example="true") boolean enabled) {
    public SoundsDTO(SoundItem item, String title) {
        this(item.id(), title, item.soundFile().replaceAll("\\\\", "/"), item.enabled());
    }
}

