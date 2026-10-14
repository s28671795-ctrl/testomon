/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.incoresoft.cleaner.CleanerListener;
import com.incoresoft.cleaner.CleaningTask;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseCleaningTask
implements CleaningTask {
    private static final Logger log = LoggerFactory.getLogger(DatabaseCleaningTask.class);
    private final DSLContext context;
    private final String tableName;
    private final String comparisonFieldName;
    private final CleaningCategory cleaningCategory;
    private final List<CleanerListener> listeners;
    private final Supplier<Duration> savingArchivePeriod;
    private long actualSavingArchivePeriodInDays;

    public DatabaseCleaningTask(@NotNull DSLContext context, @NotNull String tableName, @NotNull String comparisonFieldName, @NotNull CleaningCategory cleaningCategory, List<CleanerListener> listeners, Supplier<Duration> savingArchivePeriod) {
        this.context = Objects.requireNonNull(context);
        this.tableName = Objects.requireNonNull(tableName);
        this.comparisonFieldName = Objects.requireNonNull(comparisonFieldName);
        this.cleaningCategory = Objects.requireNonNull(cleaningCategory);
        this.listeners = Objects.requireNonNull(listeners);
        this.savingArchivePeriod = savingArchivePeriod;
    }

    @Override
    public void run() {
        this.actualSavingArchivePeriodInDays = this.savingArchivePeriod.get().toDays();
        LocalDate cutoffDate = LocalDate.now().minusDays(this.actualSavingArchivePeriodInDays - 1L);
        Instant cutoffTime = cutoffDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        int count = 1000;
        int limit = 1000;
        int removed = 0;
        while (count > 0) {
            try {
                count = this.context.deleteFrom(DSL.table(DSL.quotedName(this.tableName))).where(DSL.field(DSL.quotedName(this.comparisonFieldName), Instant.class).lessThan(cutoffTime)).limit(limit).execute();
                removed += count;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(100L);
            }
            catch (Exception e) {
                log.error("Error while sleeping", e);
            }
        }
        for (CleanerListener listener : this.listeners) {
            try {
                listener.onDatabaseTaskExecuted(this.cleaningCategory, this.tableName, removed != 0);
            }
            catch (Exception e) {
                log.info("Listener failed");
            }
        }
    }

    public String toString() {
        return "DatabaseCleaningTask(tableName=" + this.tableName + ", comparisonFieldName=" + this.comparisonFieldName + ", cleaningCategory=" + String.valueOf(this.cleaningCategory) + ", actualSavingArchivePeriodInDays=" + this.actualSavingArchivePeriodInDays + ")";
    }
}

