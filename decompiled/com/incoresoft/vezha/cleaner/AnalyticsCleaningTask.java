/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.cleaner;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleaningTask;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsCleaningTask
implements CleaningTask {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsCleaningTask.class);
    private final Map<AnalyticsTypes, List<Function<AnalyticsTypes, Collection<Integer>>>> cleaningTasks = new ConcurrentHashMap<AnalyticsTypes, List<Function<AnalyticsTypes, Collection<Integer>>>>();
    private final AnalyticsManagerImpl analyticsManager;

    @Inject
    public AnalyticsCleaningTask(AnalyticsManagerImpl analyticsManager) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
    }

    public void addCleaningTask(AnalyticsTypes analyticsType, Function<AnalyticsTypes, Collection<Integer>> cleaningTask) {
        Objects.requireNonNull(analyticsType);
        Objects.requireNonNull(cleaningTask);
        this.cleaningTasks.computeIfAbsent(analyticsType, k -> new CopyOnWriteArrayList()).add(cleaningTask);
    }

    @Override
    public void run() {
        Set<Integer> analyticsToDelete = this.cleaningTasks.entrySet().parallelStream().map(it -> {
            Set archivedAnalyticsIds = this.analyticsManager.getAnalyticsArchive().getAll(a -> a.getModule().getAnalyticsTypes().contains(it.getKey())).stream().map(Analytics::getId).collect(Collectors.toUnmodifiableSet());
            HashSet archivedAnalyticsToKeep = new HashSet();
            for (Function task : (List)it.getValue()) {
                Collection idsToKeep;
                try {
                    idsToKeep = (Collection)task.apply((AnalyticsTypes)it.getKey());
                }
                catch (Exception e) {
                    log.error("Error executing cleaning task for analytics type {}: {}", it.getKey(), (Object)e.getMessage());
                    return archivedAnalyticsIds;
                }
                archivedAnalyticsToKeep.addAll(idsToKeep);
            }
            return CollectionUtils.subtract(archivedAnalyticsIds, archivedAnalyticsToKeep);
        }).flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
        Set<Integer> deletedAnalytics = this.analyticsManager.removeArchivedAnalytics(analyticsToDelete);
        log.info("Analytics cleaning task removed archived analytics with IDs: {}", (Object)deletedAnalytics);
    }
}

