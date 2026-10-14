/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.converters;

import com.incoresoft.generated.tables.records.SoundsSettingsRecord;
import com.incoresoft.notifications.sounds.SoundItem;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class SoundsSettingsConverter
implements Converter<SoundsSettingsRecord, SoundItem> {
    @Override
    public SoundItem from(SoundsSettingsRecord databaseObject) {
        return new SoundItem(databaseObject.getId(), databaseObject.getSoundFile(), databaseObject.getEnabled());
    }

    @Override
    public SoundsSettingsRecord to(SoundItem userObject) {
        return new SoundsSettingsRecord(userObject.id(), userObject.soundFile(), userObject.enabled());
    }

    @Override
    @NotNull
    public Class<SoundsSettingsRecord> fromType() {
        return SoundsSettingsRecord.class;
    }

    @Override
    @NotNull
    public Class<SoundItem> toType() {
        return SoundItem.class;
    }
}

