/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.notificationspanel;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.notifications.sounds.SoundItem;
import com.incoresoft.notifications.sounds.SoundsManager;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@Schema(name="NotificationsPanelElementDTO", description="Notification panel element class structure")
public final class NotificationsPanelElementDTO {
    @JSONField(name="id")
    @Schema(name="id", description="Unique notification panel element id", implementation=String.class, example="object_in_zone")
    private final String id;
    @JSONField(name="audio_href")
    @Schema(name="audio_href", description="Audio file that should be played after receiving new notification", implementation=String.class, example="/audio/alert.mp3")
    private final String audioHref;
    @JSONField(name="audio_enabled")
    @Schema(name="audio_enabled", description="Determines whether audio should be played after receiving new notification", implementation=Boolean.class, example="true")
    private final boolean audioEnabled;
    @JSONField(name="notifications_href")
    @Schema(name="notifications_href", description="Notification href", implementation=String.class, example="/face/events/")
    private final String notificationsHref;

    public NotificationsPanelElementDTO(@NotNull NotificationsManager<?> inner, @NotNull SoundsManager soundsManager) {
        String id = Objects.requireNonNull(inner).getId();
        SoundItem soundItem = soundsManager.get(id).orElseGet(() -> SoundItem.defaultSoundItem(id));
        this.id = id;
        this.audioHref = "/" + soundItem.soundFile();
        this.audioEnabled = soundItem.enabled();
        this.notificationsHref = inner.getHref();
    }

    public String getId() {
        return this.id;
    }

    public String getAudioHref() {
        return this.audioHref;
    }

    public boolean isAudioEnabled() {
        return this.audioEnabled;
    }

    public String getNotificationsHref() {
        return this.notificationsHref;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NotificationsPanelElementDTO)) {
            return false;
        }
        NotificationsPanelElementDTO other = (NotificationsPanelElementDTO)o;
        if (this.isAudioEnabled() != other.isAudioEnabled()) {
            return false;
        }
        String this$id = this.getId();
        String other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) {
            return false;
        }
        String this$audioHref = this.getAudioHref();
        String other$audioHref = other.getAudioHref();
        if (this$audioHref == null ? other$audioHref != null : !this$audioHref.equals(other$audioHref)) {
            return false;
        }
        String this$notificationsHref = this.getNotificationsHref();
        String other$notificationsHref = other.getNotificationsHref();
        return !(this$notificationsHref == null ? other$notificationsHref != null : !this$notificationsHref.equals(other$notificationsHref));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isAudioEnabled() ? 79 : 97);
        String $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        String $audioHref = this.getAudioHref();
        result = result * 59 + ($audioHref == null ? 43 : $audioHref.hashCode());
        String $notificationsHref = this.getNotificationsHref();
        result = result * 59 + ($notificationsHref == null ? 43 : $notificationsHref.hashCode());
        return result;
    }

    public String toString() {
        return "NotificationsPanelElementDTO(id=" + this.getId() + ", audioHref=" + this.getAudioHref() + ", audioEnabled=" + this.isAudioEnabled() + ", notificationsHref=" + this.getNotificationsHref() + ")";
    }
}

