/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleanerListener;
import com.incoresoft.cleaner.CleaningTask;
import com.incoresoft.cleaner.DatabaseCleaningTask;
import com.incoresoft.cleaner.FolderCleaningTask;
import com.incoresoft.cleaner.StorageDirectoryEnumerator;
import com.incoresoft.database.CleaningSettingsRepository;
import com.incoresoft.dependencyinjection.jooq.LockingDSLContext;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.cleaner.Cleaner;
import com.incoresoft.middleware.cleaner.CleaningCategory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanerTasksRepositoryImpl
implements Cleaner {
    private static final Logger log = LoggerFactory.getLogger(CleanerTasksRepositoryImpl.class);
    private final Collection<CleaningTask> customTasks = new ConcurrentLinkedQueue<CleaningTask>();
    private final Collection<DatabaseCleaningTask> databaseTasks = new ConcurrentLinkedQueue<DatabaseCleaningTask>();
    private final Map<CleaningCategory, List<String>> dirsToClean = new ConcurrentHashMap<CleaningCategory, List<String>>();
    private final List<CleanerListener> listeners = Collections.synchronizedList(new ArrayList());
    private final StorageDirectoryEnumerator storageDirectoryEnumerator;
    private final CleaningSettingsRepository cleaningSettingsRepository;
    private final DSLContext dslContext;

    @Inject
    public CleanerTasksRepositoryImpl(@NotNull StorageDirectoryEnumerator storageDirectoryEnumerator, @NotNull CleaningSettingsRepository cleaningSettingsRepository, @LockingDSLContext @NotNull DSLContext context) {
        this.storageDirectoryEnumerator = Objects.requireNonNull(storageDirectoryEnumerator);
        this.cleaningSettingsRepository = Objects.requireNonNull(cleaningSettingsRepository);
        this.dslContext = Objects.requireNonNull(context);
    }

    public void addListener(CleanerListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void addDatabaseCleaningTask(CleaningCategory cleaningCategory, String tableName, String comparisonFieldName) {
        this.cleaningSettingsRepository.addCategoryIfNotExist(cleaningCategory);
        this.databaseTasks.add(new DatabaseCleaningTask(this.dslContext, tableName, comparisonFieldName, cleaningCategory, this.listeners, () -> this.cleaningSettingsRepository.getRetentionTime(cleaningCategory)));
    }

    @Override
    public void addStorageCleaningTask(CleaningCategory cleaningCategory, String dir) {
        this.cleaningSettingsRepository.addCategoryIfNotExist(cleaningCategory);
        List<String> dirs = this.dirsToClean.get(cleaningCategory);
        if (dirs == null) {
            this.dirsToClean.put(cleaningCategory, Collections.singletonList(dir));
        } else {
            dirs.add(dir);
            this.dirsToClean.put(cleaningCategory, dirs);
        }
    }

    @Override
    public void addAnalyticsCleaningTask(AnalyticsTypes analyticsTypes, Function<AnalyticsTypes, Collection<Integer>> function) {
    }

    @Override
    public void addCustomTask(Runnable task) {
        this.customTasks.add(task::run);
    }

    @Override
    public Optional<Duration> getRetentionTime(CleaningCategory cleaningCategory) {
        return Optional.of(this.cleaningSettingsRepository.getRetentionTime(cleaningCategory));
    }

    public Collection<CleaningTask> getTasks() {
        return Stream.of(this.getDatabaseCleaningTasks(), this.getStorageCleaningTasks(), this.getCustomTasks()).flatMap(Collection::stream).collect(Collectors.toUnmodifiableList());
    }

    private Collection<DatabaseCleaningTask> getDatabaseCleaningTasks() {
        return this.databaseTasks;
    }

    private Collection<CleaningTask> getCustomTasks() {
        return this.customTasks;
    }

    private Collection<FolderCleaningTask> getStorageCleaningTasks() {
        try {
            ArrayList<FolderCleaningTask> folderCleaningTasks = new ArrayList<FolderCleaningTask>();
            for (Map.Entry<CleaningCategory, List<String>> entry : this.dirsToClean.entrySet()) {
                Duration duration = this.cleaningSettingsRepository.getRetentionTime(entry.getKey());
                folderCleaningTasks.addAll(entry.getValue().stream().map(it -> this.storageDirectoryEnumerator.enumerateSubDirsEligibleForRemoval((String)it, duration)).flatMap(Collection::stream).map(path -> new FolderCleaningTask(path.toString(), this.listeners)).toList());
            }
            return folderCleaningTasks;
        }
        catch (Exception e) {
            log.error("Couldn't initialize folder cleaning tasks.", e);
            return Collections.emptyList();
        }
    }

    public List<CleanerListener> getListeners() {
        return this.listeners;
    }
}

