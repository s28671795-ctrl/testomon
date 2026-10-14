/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.cleaner;

import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public interface Cleaner {
    public void addDatabaseCleaningTask(CleaningCategory var1, String var2, String var3);

    public void addStorageCleaningTask(CleaningCategory var1, String var2);

    public void addAnalyticsCleaningTask(AnalyticsTypes var1, Function<AnalyticsTypes, Collection<Integer>> var2);

    public void addCustomTask(Runnable var1);

    public Optional<Duration> getRetentionTime(CleaningCategory var1);
}

