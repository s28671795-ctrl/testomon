/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.cleaner;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
import com.incoresoft.cleaner.CleaningTask;
import com.incoresoft.cleaner.StorageDirectoryEnumerator;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.dependencyinjection.jooq.LockingDSLContext;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.cleaner.AnalyticsCleaningTask;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class VezhaCleanerTasksRepositoryImpl
extends CleanerTasksRepositoryImpl {
    private final AnalyticsCleaningTask analyticsCleaningTask;

    @Inject
    public VezhaCleanerTasksRepositoryImpl(@NotNull StorageDirectoryEnumerator storageDirectoryEnumerator, @NotNull CleaningSettingsRepository cleaningSettingsRepository, @LockingDSLContext @NotNull DSLContext context, @NotNull AnalyticsCleaningTask analyticsCleaningTask) {
        super(storageDirectoryEnumerator, cleaningSettingsRepository, context);
        this.analyticsCleaningTask = analyticsCleaningTask;
    }

    @Override
    public void addAnalyticsCleaningTask(AnalyticsTypes analyticsTypes, Function<AnalyticsTypes, Collection<Integer>> function) {
        this.analyticsCleaningTask.addCleaningTask(analyticsTypes, function);
    }

    @Override
    public Collection<CleaningTask> getTasks() {
        ArrayList<CleaningTask> tasks2 = new ArrayList<CleaningTask>(super.getTasks());
        tasks2.add(this.analyticsCleaningTask);
        return Collections.unmodifiableCollection(tasks2);
    }
}

