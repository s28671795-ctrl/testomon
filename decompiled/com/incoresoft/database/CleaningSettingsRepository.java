/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.generated.Tables;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.TableLike;

public class CleaningSettingsRepository {
    private final DSLContext context;
    private static final int DEFAULT_RETENTION_PERIOD = 30;

    public Duration getRetentionTime(CleaningCategory cleaningCategory) {
        return this.context.select(Tables.CLEANING_SETTINGS.RETENTION_PERIOD).from((TableLike<?>)Tables.CLEANING_SETTINGS).where(Tables.CLEANING_SETTINGS.CATEGORY_ID.eq(cleaningCategory.getTypeId())).fetchOptional(Tables.CLEANING_SETTINGS.RETENTION_PERIOD).map(Duration::ofDays).orElseGet(() -> Duration.ofDays(30L));
    }

    public boolean exists(CleaningCategory cleaningCategory) {
        return this.context.fetchExists(this.context.selectFrom(Tables.CLEANING_SETTINGS).where(Tables.CLEANING_SETTINGS.CATEGORY_ID.eq(cleaningCategory.getTypeId())));
    }

    public void addCategoryIfNotExist(CleaningCategory cleaningCategory) {
        if (!this.exists(cleaningCategory)) {
            this.context.insertInto(Tables.CLEANING_SETTINGS).set(Tables.CLEANING_SETTINGS.CATEGORY_ID, cleaningCategory.getTypeId()).set(Tables.CLEANING_SETTINGS.RETENTION_PERIOD, 30).execute();
        }
    }

    public void changeRetentionTime(CleaningCategory cleaningCategory, Duration period) {
        this.addCategoryIfNotExist(cleaningCategory);
        this.context.update(Tables.CLEANING_SETTINGS).set(Tables.CLEANING_SETTINGS.RETENTION_PERIOD, Math.toIntExact(period.toDays())).where(Tables.CLEANING_SETTINGS.CATEGORY_ID.eq(cleaningCategory.getTypeId())).execute();
    }

    public Map<CleaningCategory, Duration> getMappings(Collection<CleaningCategory> categories) {
        Map<String, Integer> persistedInDb = this.context.selectFrom(Tables.CLEANING_SETTINGS).stream().collect(Collectors.toMap(record -> (String)record.get(Tables.CLEANING_SETTINGS.CATEGORY_ID), record -> (Integer)record.get(Tables.CLEANING_SETTINGS.RETENTION_PERIOD)));
        return categories.stream().map(it -> Map.entry(it, persistedInDb.getOrDefault(it.getTypeId(), 30))).collect(Collectors.toMap(Map.Entry::getKey, it -> Duration.ofDays(((Integer)it.getValue()).intValue())));
    }

    @Inject
    public CleaningSettingsRepository(DSLContext context) {
        this.context = context;
    }
}

