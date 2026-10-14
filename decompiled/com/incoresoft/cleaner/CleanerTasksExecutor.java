/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.google.inject.Inject;
import com.incoresoft.cleaner.CleanerListener;
import com.incoresoft.cleaner.CleanerTasksRepositoryImpl;
import com.incoresoft.cleaner.CleaningTask;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanerTasksExecutor
implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(CleanerTasksExecutor.class);
    private static final Duration SCHEDULER_RUNNING_PERIOD = Duration.ofDays(1L);
    private static final Duration NEXT_TASK_EXECUTION_DELAY = Duration.ofMillis(200L);
    private final ScheduledExecutorService cleaningStartScheduler = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
    private final ScheduledExecutorService tasksExecutor = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final CleanerTasksRepositoryImpl cleanerTasksRepository;

    @Inject
    public CleanerTasksExecutor(@NotNull CleanerTasksRepositoryImpl cleanerTasksRepository) {
        this.cleanerTasksRepository = Objects.requireNonNull(cleanerTasksRepository);
    }

    public boolean isRunning() {
        return this.isRunning.get();
    }

    public void cleanNow() {
        log.info("Cleaner will be started at {}", (Object)LocalDateTime.now());
        this.cleaningStartScheduler.submit(this::executeCleanUp);
    }

    public void schedule() {
        this.scheduleAt(LocalTime.MIDNIGHT.plusMinutes(10L));
    }

    public void scheduleAt(LocalTime cleanerStartTime) {
        Duration schedulerStartInitialDelay = CleanerTasksExecutor.calculateInitialDelay(cleanerStartTime);
        log.info("Cleaner will be started at {}", (Object)LocalDateTime.now().plusSeconds(schedulerStartInitialDelay.toSeconds()));
        this.cleaningStartScheduler.scheduleAtFixedRate(this::executeCleanUp, schedulerStartInitialDelay.toSeconds(), SCHEDULER_RUNNING_PERIOD.toSeconds(), TimeUnit.SECONDS);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeCleanUp() {
        if (this.isRunning.getAndSet(true)) {
            log.warn("Cleaner is already running. It will not be started again");
            return;
        }
        try {
            Collection<CleaningTask> tasks2 = this.cleanerTasksRepository.getTasks();
            int tasksCount = tasks2.size();
            if (tasksCount == 0) {
                log.info("Cleaner will not be started - no tasks");
                return;
            }
            this.onStarted();
            log.info("Cleaner is starting. {} tasks will be executed", (Object)tasksCount);
            CountDownLatch latch = new CountDownLatch(tasksCount);
            int taskPositionInQueue = 1;
            for (CleaningTask task : tasks2) {
                int currentTaskPositionInQueue = taskPositionInQueue;
                this.tasksExecutor.schedule(() -> {
                    try {
                        task.run();
                        log.info("Task {} finished successfully ({})", (Object)currentTaskPositionInQueue, (Object)task);
                    }
                    catch (Throwable e) {
                        log.error("Task {} finished with error: {}({})", currentTaskPositionInQueue, e.getMessage(), task, e);
                    }
                    finally {
                        latch.countDown();
                    }
                }, (long)taskPositionInQueue * NEXT_TASK_EXECUTION_DELAY.toMillis(), TimeUnit.MILLISECONDS);
                ++taskPositionInQueue;
            }
            try {
                latch.await();
                this.onFinished();
                log.info("Cleaner has finished current run.");
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Cleaner was interrupted while waiting for tasks to complete");
            }
        }
        finally {
            this.isRunning.set(false);
        }
    }

    private static Duration calculateInitialDelay(LocalTime cleanerStartTime) {
        LocalTime currentTime = LocalTime.now();
        Duration between = Duration.between(currentTime, cleanerStartTime);
        if (!between.isNegative()) {
            return between;
        }
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1L);
        return Duration.between(LocalDateTime.of(today, currentTime), LocalDateTime.of(tomorrow, cleanerStartTime));
    }

    @Override
    public void close() {
        this.cleaningStartScheduler.shutdown();
        this.tasksExecutor.shutdown();
    }

    private void onStarted() {
        for (CleanerListener listener : this.cleanerTasksRepository.getListeners()) {
            try {
                listener.onCleanStarted();
            }
            catch (Exception e) {
                log.info("Cleaner listener onStarted failed");
            }
        }
    }

    private void onFinished() {
        for (CleanerListener listener : this.cleanerTasksRepository.getListeners()) {
            try {
                listener.onCleanFinished();
            }
            catch (Exception e) {
                log.info("Listener failed");
            }
        }
    }
}

