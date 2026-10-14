/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.metrics.aggregated;

import com.google.inject.Inject;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.listeners.ServerHeartBeatListener;
import com.incoresoft.vezha.analytics.servers.models.GpuCard;
import com.incoresoft.vezha.database.AnalyticsServerGpuMetricsRepository;
import com.incoresoft.vezha.database.AnalyticsServersMetricsRepository;
import com.incoresoft.vezha.database.CoreAggregatedMetricsRepository;
import com.incoresoft.vezha.metrics.aggregated.AggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerGpuAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.AnalyticsServerWithGpusAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.CoreAggregatedMetrics;
import com.incoresoft.vezha.metrics.aggregated.StatisticalMetric;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregatedMetricsManager
implements ServerHeartBeatListener {
    private static final Logger log = LoggerFactory.getLogger(AggregatedMetricsManager.class);
    private final CoreAggregatedMetricsRepository coreMetricsRepository;
    private final AnalyticsServersMetricsRepository serverMetricsRepository;
    private final AnalyticsServerGpuMetricsRepository serverGpuMetricsRepository;
    private volatile Instant currentStartTime;

    public Optional<AggregatedMetrics> getByStartTime(Instant startTime) {
        Optional<CoreAggregatedMetrics> coreMetricsOptional = this.coreMetricsRepository.getByStartTime(startTime);
        return coreMetricsOptional.map(coreAggregatedMetrics -> new AggregatedMetrics((CoreAggregatedMetrics)coreAggregatedMetrics, this.serverMetricsRepository.getByStartTime(startTime).stream().map(metrics -> new AnalyticsServerWithGpusAggregatedMetrics((AnalyticsServerAggregatedMetrics)metrics, this.serverGpuMetricsRepository.getByServerMetricsId(metrics.getId()))).toList()));
    }

    @Override
    public void onServerHeartBeat(Server server) {
    }

    public void onCoreCpuLoadMeasurement(double cpuLoad) {
        CoreAggregatedMetrics metrics = this.getCoreCurrentMetrics();
        CoreAggregatedMetrics updating = metrics.withCpuLoad(metrics.cpuLoad().withNewMeasurement(cpuLoad));
        this.coreMetricsRepository.update(updating);
    }

    public void onCoreStartup() {
        CoreAggregatedMetrics metrics = this.getCoreCurrentMetrics();
        CoreAggregatedMetrics updating = metrics.withStartupsNumber(metrics.startupsNumber() + 1);
        this.coreMetricsRepository.update(updating);
    }

    public void updateCurrentStartTime(Instant newStatTime) {
        if (this.coreMetricsRepository.getByStartTime(newStatTime).isEmpty()) {
            this.coreMetricsRepository.add(new CoreAggregatedMetrics(newStatTime, new StatisticalMetric(0.0)));
            log.info("Created new core aggregated metrics for start time: {}", (Object)newStatTime);
        }
        this.currentStartTime = newStatTime;
    }

    public void deleteByStartTimeLessOrEqual(Instant startTime) {
        int deletedRecordsNumber = this.coreMetricsRepository.deleteByStartTimeLessOrEqual(startTime);
        if (deletedRecordsNumber > 0) {
            log.info("Deleted {} metrics total for start time less or equal to: {}", (Object)deletedRecordsNumber, (Object)startTime);
        }
    }

    private CoreAggregatedMetrics getCoreCurrentMetrics() {
        Optional<CoreAggregatedMetrics> metricsOptional = this.coreMetricsRepository.getByStartTime(this.currentStartTime);
        if (metricsOptional.isPresent()) {
            return metricsOptional.get();
        }
        log.warn("No core aggregating metrics found for start time: {}, creating new...", (Object)this.currentStartTime);
        return this.coreMetricsRepository.add(new CoreAggregatedMetrics(this.currentStartTime, new StatisticalMetric(0.0)));
    }

    private AnalyticsServerAggregatedMetrics createOrUpdateServerMetrics(String serverId, String ipAddress, double cpuLoad) {
        Optional<AnalyticsServerAggregatedMetrics> metricsOptional = this.serverMetricsRepository.getByServerIdAndStartTime(serverId, this.currentStartTime);
        if (metricsOptional.isEmpty()) {
            return this.createServerMetrics(serverId, ipAddress, cpuLoad);
        }
        return this.updateServerMetrics(metricsOptional.get(), ipAddress, cpuLoad);
    }

    private AnalyticsServerAggregatedMetrics createServerMetrics(String serverId, String ipAddress, double cpuLoad) {
        AnalyticsServerAggregatedMetrics metrics = new AnalyticsServerAggregatedMetrics(this.currentStartTime, serverId, ipAddress, new StatisticalMetric(cpuLoad));
        return this.serverMetricsRepository.add(metrics);
    }

    private AnalyticsServerAggregatedMetrics updateServerMetrics(AnalyticsServerAggregatedMetrics existing, String ipAddress, double cpuLoad) {
        AnalyticsServerAggregatedMetrics metrics = new AnalyticsServerAggregatedMetrics(existing.getId(), existing.startTime(), existing.serverId(), ipAddress, existing.cpuLoad().withNewMeasurement(cpuLoad), existing.getClientId());
        return this.serverMetricsRepository.update(metrics);
    }

    private void createOrUpdateServerGpuMetrics(int serverMetricsId, GpuCard gpuCard) {
        Optional<AnalyticsServerGpuAggregatedMetrics> gpuMetricsOptional = this.serverGpuMetricsRepository.getByServerMetricsIdAnsGpuId(serverMetricsId, gpuCard.getId());
        if (gpuMetricsOptional.isEmpty()) {
            this.createServerGpuMetrics(serverMetricsId, gpuCard);
        } else {
            this.updateServerGpuMetrics(gpuMetricsOptional.get(), gpuCard);
        }
    }

    private void createServerGpuMetrics(int serverMetricsId, GpuCard gpuCard) {
        AnalyticsServerGpuAggregatedMetrics gpuMetrics = new AnalyticsServerGpuAggregatedMetrics(serverMetricsId, gpuCard.getId(), gpuCard.getName(), new StatisticalMetric(gpuCard.getGpuUsage()));
        this.serverGpuMetricsRepository.add(gpuMetrics);
    }

    private void updateServerGpuMetrics(AnalyticsServerGpuAggregatedMetrics existing, GpuCard gpuCard) {
        AnalyticsServerGpuAggregatedMetrics updatingGpu = new AnalyticsServerGpuAggregatedMetrics(existing.id(), existing.serverMetricsId(), existing.gpuId(), gpuCard.getName(), existing.load().withNewMeasurement(gpuCard.getGpuUsage()), existing.clientId());
        this.serverGpuMetricsRepository.update(updatingGpu);
    }

    @Inject
    public AggregatedMetricsManager(CoreAggregatedMetricsRepository coreMetricsRepository, AnalyticsServersMetricsRepository serverMetricsRepository, AnalyticsServerGpuMetricsRepository serverGpuMetricsRepository) {
        this.coreMetricsRepository = coreMetricsRepository;
        this.serverMetricsRepository = serverMetricsRepository;
        this.serverGpuMetricsRepository = serverGpuMetricsRepository;
    }
}

