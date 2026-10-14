/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.database.converters.SoundsSettingsConverter;
import com.incoresoft.generated.Tables;
import com.incoresoft.notifications.sounds.SoundItem;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.impl.DSL;

public class SoundsSettingsRepository {
    private final DSLContext context;
    private final SoundsSettingsConverter converter;

    public boolean noSettingsSet() {
        return this.context.fetchCount(Tables.SOUNDS_SETTINGS) == 0;
    }

    public List<SoundItem> getAll() {
        return this.context.selectFrom(Tables.SOUNDS_SETTINGS).fetch(this.converter::from);
    }

    @NotNull
    public Optional<SoundItem> get(String id) {
        return this.context.selectFrom(Tables.SOUNDS_SETTINGS).where(Tables.SOUNDS_SETTINGS.ID.eq(id)).fetchOptional(this.converter::from);
    }

    public void add(SoundItem item) {
        this.converter.to(item).insert();
    }

    public void addAll(@NotNull List<SoundItem> settings) {
        this.context.batchStore(settings.stream().map(this.converter::to).toList()).execute();
    }

    public void resetSoundFile(String id) {
        this.setSoundFile(id, "sounds/alert.mp3");
    }

    public void setSoundFile(String id, String soundFile) {
        this.context.transaction(context -> {
            DSLContext transactionContext = context.dsl();
            if (!transactionContext.fetchExists((Table<?>)Tables.SOUNDS_SETTINGS, Tables.SOUNDS_SETTINGS.ID.eq(id))) {
                throw new IllegalArgumentException(String.format("Sound item \"%s\" not found", id));
            }
            transactionContext.update(Tables.SOUNDS_SETTINGS).set(Tables.SOUNDS_SETTINGS.SOUND_FILE, soundFile).where(Tables.SOUNDS_SETTINGS.ID.eq(id)).execute();
        });
    }

    public void toggle(String id) {
        this.context.transaction(context -> {
            DSLContext transactionContext = context.dsl();
            if (!transactionContext.fetchExists((Table<?>)Tables.SOUNDS_SETTINGS, Tables.SOUNDS_SETTINGS.ID.eq(id))) {
                throw new IllegalArgumentException(String.format("Sound item \"%s\" not found", id));
            }
            transactionContext.update(Tables.SOUNDS_SETTINGS).set(Tables.SOUNDS_SETTINGS.ENABLED, DSL.not(Tables.SOUNDS_SETTINGS.ENABLED)).where(Tables.SOUNDS_SETTINGS.ID.eq(id)).execute();
        });
    }

    public void updateAll(List<SoundItem> items) {
        this.context.batchUpdate(items.stream().map(this.converter::to).toList()).execute();
    }

    @Inject
    public SoundsSettingsRepository(DSLContext context, SoundsSettingsConverter converter) {
        this.context = context;
        this.converter = converter;
    }
}

