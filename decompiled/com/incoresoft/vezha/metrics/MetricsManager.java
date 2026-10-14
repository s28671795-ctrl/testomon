/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics;

import com.google.inject.Inject;
import com.incoresoft.system.compute.ComputeResourcesManager;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.log.LogFilesManager;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetricsManager;
import com.incoresoft.vezha.metrics.client.MetricsClient;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetrics;
import com.incoresoft.vezha.metrics.snapshot.SnapshotMetricsManager;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetricsManager {
    private static final Logger log = LoggerFactory.getLogger(MetricsManager.class);
    private static final int MAX_DAILY_PROCESSING_OFFSET_SECONDS = 900;
    private final ComputeResourcesManager computeResourcesManager;
    private final AggregatedMetricsManager aggregatedMetricsManager;
    private final SnapshotMetricsManager snapshotMetricsManager;
    private final MetricsClient metricsClient;
    private final LogFilesManager logFilesManager;
    private final Random random = new Random();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, Thread.ofVirtual().factory());

    public void init(ServersMonitor serversMonitor) {
        Instant previousStartTime = this.shiftAggregatedMetricsStartTime();
        this.aggregatedMetricsManager.onCoreStartup();
        this.ensurePreviousMetricsProcessed(previousStartTime);
        this.scheduleDailyMetricsProcessing();
        this.scheduleCoreCpuLoadMeasurements();
        serversMonitor.addServerActionListener(this.aggregatedMetricsManager);
    }

    private void scheduleCoreCpuLoadMeasurements() {
        this.scheduler.scheduleAtFixedRate(() -> {
            try {
                double cpuUsage = this.computeResourcesManager.getCpuUsage();
                this.aggregatedMetricsManager.onCoreCpuLoadMeasurement(cpuUsage);
            }
            catch (RuntimeException e) {
                log.error("Error while measuring CPU usage", e);
            }
        }, 0L, 5L, TimeUnit.SECONDS);
    }

    private void scheduleDailyMetricsProcessing() {
        this.scheduler.scheduleAtFixedRate(() -> {
            try {
                Instant previousStartTime = this.shiftAggregatedMetricsStartTime();
                this.scheduler.schedule(() -> this.ensurePreviousMetricsProcessed(previousStartTime), this.getDailyProcessingOffsetSeconds(), TimeUnit.SECONDS);
            }
            catch (RuntimeException e) {
                log.error("Error while processing previous metrics", e);
            }
        }, this.getSecondsToNextDay(), 1L, TimeUnit.DAYS);
    }

    private Instant shiftAggregatedMetricsStartTime() {
        Instant newStartTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant previousStartTime = LocalDate.now().minusDays(1L).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant deletingStartTime = LocalDate.now().minusDays(2L).atStartOfDay(ZoneId.systemDefault()).toInstant();
        this.aggregatedMetricsManager.updateCurrentStartTime(newStartTime);
        this.aggregatedMetricsManager.deleteByStartTimeLessOrEqual(deletingStartTime);
        return previousStartTime;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GuardedBy(value="this")
    private synchronized void ensurePreviousMetricsProcessed(Instant previousStartTime) {
        Optional<AggregatedMetrics> aggregatedMetricsOptional = this.aggregatedMetricsManager.getByStartTime(previousStartTime);
        if (aggregatedMetricsOptional.isPresent()) {
            File logFile = null;
            try {
                AggregatedMetrics aggregatedMetrics = aggregatedMetricsOptional.get();
                SnapshotMetrics snapshotMetrics = this.snapshotMetricsManager.getMetrics();
                logFile = this.logFilesManager.getArchivedLogFile(previousStartTime);
                this.metricsClient.send(aggregatedMetrics, snapshotMetrics, logFile);
                log.info("Metrics for start time {} processed and sent to IncoreSoft server", (Object)previousStartTime);
                this.aggregatedMetricsManager.deleteByStartTimeLessOrEqual(previousStartTime);
            }
            catch (Exception e) {
                log.error("Error while sending metrics for start time {}: {}", previousStartTime, e.getMessage(), e);
            }
            finally {
                if (logFile != null) {
                    logFile.delete();
                }
            }
        }
    }

    private long getSecondsToNextDay() {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());
        ZonedDateTime nextDay = now.plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return Duration.between(now, nextDay).getSeconds();
    }

    private long getDailyProcessingOffsetSeconds() {
        return this.random.nextInt(900);
    }

    @Inject
    public MetricsManager(ComputeResourcesManager computeResourcesManager, AggregatedMetricsManager aggregatedMetricsManager, SnapshotMetricsManager snapshotMetricsManager, MetricsClient metricsClient, LogFilesManager logFilesManager) {
        this.computeResourcesManager = computeResourcesManager;
        this.aggregatedMetricsManager = aggregatedMetricsManager;
        this.snapshotMetricsManager = snapshotMetricsManager;
        this.metricsClient = metricsClient;
        this.logFilesManager = logFilesManager;
    }
}

