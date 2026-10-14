/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareSettings;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.GpuMemoryReserve;
import com.incoresoft.vezha.analytics.servers.InFlightGpuReservations;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.models.RunningAnalytics;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.AnalyticsVramConfig;
import com.incoresoft.vezha.config.models.VezhaConfig;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class GpuMemoryReserveCalculator {
    private static final Logger log = LoggerFactory.getLogger(GpuMemoryReserveCalculator.class);
    static final long GRACE_SECONDS = 30L;
    private final Provider<AnalyticsManager> analyticsManager;
    private final VezhaConfigManager configManager;
    private final StreamsManager streamsManager;
    private final InFlightGpuReservations inFlight;
    private static final Method NO_IS_RESIZE = Object.class.getMethods()[0];
    private static final ConcurrentHashMap<Class<?>, Method> IS_RESIZE_BY_CLASS = new ConcurrentHashMap();

    @Inject
    public GpuMemoryReserveCalculator(@NotNull Provider<AnalyticsManager> analyticsManager, @NotNull VezhaConfigManager configManager, @NotNull StreamsManager streamsManager, @NotNull InFlightGpuReservations inFlight) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.configManager = Objects.requireNonNull(configManager);
        this.streamsManager = Objects.requireNonNull(streamsManager);
        this.inFlight = Objects.requireNonNull(inFlight);
    }

    public GpuMemoryReserve forServer(Server server) {
        if (server == null) {
            return GpuMemoryReserve.EMPTY;
        }
        AnalyticsVramConfig vram = ((VezhaConfig)this.configManager.getConfig()).getAnalyticsVram();
        HashMap<Integer, Double> byGpu = new HashMap<Integer, Double>();
        for (RunningAnalytics running : server.getRunningAnalytics()) {
            if (running.isConsumingGpuMemory()) continue;
            byGpu.merge(running.getGpuId(), this.estimateFor(running.getId(), vram), Double::sum);
        }
        Instant now = Instant.now();
        for (InFlightGpuReservations.Reservation reservation : this.inFlight.reservationsFor(server.getId())) {
            RunningAnalytics reported = server.getRunningAnalyticsMap().get(reservation.analyticsId());
            if (reported != null && !reported.isConsumingGpuMemory() || reported != null && Duration.between(reservation.placedAt(), now).getSeconds() >= 30L) continue;
            byGpu.merge(reservation.gpuId(), reservation.gb(), Double::sum);
        }
        if (!byGpu.isEmpty() && log.isDebugEnabled()) {
            log.debug("Server {}: GPU memory promised to analytics that are not consuming yet: {}", (Object)server.getId(), (Object)byGpu);
        }
        return new GpuMemoryReserve(byGpu);
    }

    public void recordPlacement(BaseAnalytics analytics, String serverId, int gpuId) {
        AnalyticsVramConfig vram = ((VezhaConfig)this.configManager.getConfig()).getAnalyticsVram();
        double gb = vram.estimateGb(analytics.getPluginName(), this.megapixelsOf(analytics), this.isResizedToFullHd(analytics));
        this.inFlight.record(analytics.getId(), serverId, gpuId, gb);
        log.debug("Reserved {} GB on server {} GPU {} for just-started analytics {}", gb, serverId, gpuId, analytics.getId());
    }

    public void clearPlacement(int analyticsId) {
        this.inFlight.clear(analyticsId);
    }

    private double estimateFor(int analyticsId, AnalyticsVramConfig vram) {
        Analytics analytics = (Analytics)this.analyticsManager.get().get(analyticsId);
        if (analytics == null) {
            return vram.estimateGb(null, null);
        }
        return vram.estimateGb(analytics.getPluginName(), this.megapixelsOf(analytics), this.isResizedToFullHd(analytics));
    }

    private Double megapixelsOf(Analytics analytics) {
        try {
            Stream stream = (Stream)this.streamsManager.get(analytics.getStreamId());
            if (stream == null) {
                return null;
            }
            Resolution resolution = stream.getResolution();
            if (resolution == null || resolution.getWidth() <= 0 || resolution.getHeight() <= 0) {
                return null;
            }
            return (double)resolution.getWidth() * (double)resolution.getHeight() / 1000000.0;
        }
        catch (Exception e) {
            return null;
        }
    }

    private boolean isResizedToFullHd(Analytics analytics) {
        try {
            return GpuMemoryReserveCalculator.isResizedToFullHd(analytics.getModule().getHardwareSettings());
        }
        catch (Exception e) {
            return false;
        }
    }

    static boolean isResizedToFullHd(HardwareSettings settings) {
        if (settings == null) {
            return false;
        }
        Method isResize = IS_RESIZE_BY_CLASS.computeIfAbsent(settings.getClass(), type -> {
            try {
                return type.getMethod("isResize", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                return NO_IS_RESIZE;
            }
        });
        if (isResize == NO_IS_RESIZE) {
            return false;
        }
        try {
            return Boolean.TRUE.equals(isResize.invoke((Object)settings, new Object[0]));
        }
        catch (ReflectiveOperationException e) {
            return false;
        }
    }
}

