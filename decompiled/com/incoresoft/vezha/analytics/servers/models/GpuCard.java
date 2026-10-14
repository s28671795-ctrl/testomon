/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.servers.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.analytics.Gpu;
import com.incoresoft.vezha.analytics.servers.dto.GpuCardDTO;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GpuCard
implements Gpu {
    private static final Logger log = LoggerFactory.getLogger(GpuCard.class);
    public static final int DEFAULT_START_DEBOUNCE_SECONDS = 3;
    public static final int MAX_START_DEBOUNCE_SECONDS = 60;
    private static volatile int startDebounceSeconds = 3;
    private final int id;
    private final String name;
    private final double gpuUsage;
    private final double nvDec;
    private final double nvEnc;
    private final double totalMem;
    private final AtomicReference<Double> freeMemory = new AtomicReference<Double>(0.0);
    private final AtomicInteger runnedAnalytics = new AtomicInteger(0);
    private final AtomicReference<Instant> lastAnalyticsRun = new AtomicReference<Instant>(Instant.EPOCH);

    public static void setStartDebounceSeconds(int seconds) {
        int clamped = Math.clamp((long)seconds, 0, 60);
        if (clamped != seconds) {
            log.warn("gpu_start_debounce_seconds={} is outside [0, {}], using {}", seconds, 60, clamped);
        }
        startDebounceSeconds = clamped;
    }

    public static int getStartDebounceSeconds() {
        return startDebounceSeconds;
    }

    public GpuCard(GpuCardDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.gpuUsage = dto.getGpu();
        this.nvDec = dto.getNvDec();
        this.nvEnc = dto.getNvEnc();
        this.totalMem = dto.getTotalMem();
        this.freeMemory.set(dto.getFreeMem());
    }

    @JSONField(serialize=false)
    public int getWeight() {
        return (int)(this.gpuUsage + this.nvDec * 2.0) / 5;
    }

    @JSONField(serialize=false)
    public int getRunnedAnalyticsWeight() {
        return this.runnedAnalytics.get() / 3;
    }

    public void minusFreeMemory(double v) {
        this.freeMemory.getAndUpdate(mem -> mem - v);
    }

    public void setRunnedAnalytics(int value) {
        this.runnedAnalytics.set(value);
    }

    public void updateLastAnalyticsRun() {
        this.lastAnalyticsRun.set(Instant.now());
    }

    public boolean isGpuAvailable() {
        int debounce = startDebounceSeconds;
        if (debounce <= 0) {
            return true;
        }
        return this.lastAnalyticsRun.get().plusSeconds(debounce).isBefore(Instant.now());
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getGpuUsage() {
        return this.gpuUsage;
    }

    public double getNvDec() {
        return this.nvDec;
    }

    public double getNvEnc() {
        return this.nvEnc;
    }

    public double getTotalMem() {
        return this.totalMem;
    }

    public AtomicReference<Double> getFreeMemory() {
        return this.freeMemory;
    }

    public AtomicInteger getRunnedAnalytics() {
        return this.runnedAnalytics;
    }

    public AtomicReference<Instant> getLastAnalyticsRun() {
        return this.lastAnalyticsRun;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GpuCard)) {
            return false;
        }
        GpuCard other = (GpuCard)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (Double.compare(this.getGpuUsage(), other.getGpuUsage()) != 0) {
            return false;
        }
        if (Double.compare(this.getNvDec(), other.getNvDec()) != 0) {
            return false;
        }
        if (Double.compare(this.getNvEnc(), other.getNvEnc()) != 0) {
            return false;
        }
        if (Double.compare(this.getTotalMem(), other.getTotalMem()) != 0) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        AtomicReference<Double> this$freeMemory = this.getFreeMemory();
        AtomicReference<Double> other$freeMemory = other.getFreeMemory();
        if (this$freeMemory == null ? other$freeMemory != null : !this$freeMemory.equals(other$freeMemory)) {
            return false;
        }
        AtomicInteger this$runnedAnalytics = this.getRunnedAnalytics();
        AtomicInteger other$runnedAnalytics = other.getRunnedAnalytics();
        if (this$runnedAnalytics == null ? other$runnedAnalytics != null : !this$runnedAnalytics.equals(other$runnedAnalytics)) {
            return false;
        }
        AtomicReference<Instant> this$lastAnalyticsRun = this.getLastAnalyticsRun();
        AtomicReference<Instant> other$lastAnalyticsRun = other.getLastAnalyticsRun();
        return !(this$lastAnalyticsRun == null ? other$lastAnalyticsRun != null : !this$lastAnalyticsRun.equals(other$lastAnalyticsRun));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $gpuUsage = Double.doubleToLongBits(this.getGpuUsage());
        result = result * 59 + (int)($gpuUsage >>> 32 ^ $gpuUsage);
        long $nvDec = Double.doubleToLongBits(this.getNvDec());
        result = result * 59 + (int)($nvDec >>> 32 ^ $nvDec);
        long $nvEnc = Double.doubleToLongBits(this.getNvEnc());
        result = result * 59 + (int)($nvEnc >>> 32 ^ $nvEnc);
        long $totalMem = Double.doubleToLongBits(this.getTotalMem());
        result = result * 59 + (int)($totalMem >>> 32 ^ $totalMem);
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        AtomicReference<Double> $freeMemory = this.getFreeMemory();
        result = result * 59 + ($freeMemory == null ? 43 : $freeMemory.hashCode());
        AtomicInteger $runnedAnalytics = this.getRunnedAnalytics();
        result = result * 59 + ($runnedAnalytics == null ? 43 : $runnedAnalytics.hashCode());
        AtomicReference<Instant> $lastAnalyticsRun = this.getLastAnalyticsRun();
        result = result * 59 + ($lastAnalyticsRun == null ? 43 : $lastAnalyticsRun.hashCode());
        return result;
    }

    public GpuCard(int id, String name, double gpuUsage, double nvDec, double nvEnc, double totalMem) {
        this.id = id;
        this.name = name;
        this.gpuUsage = gpuUsage;
        this.nvDec = nvDec;
        this.nvEnc = nvEnc;
        this.totalMem = totalMem;
    }

    public String toString() {
        return "GpuCard(id=" + this.getId() + ", gpuUsage=" + this.getGpuUsage() + ", nvDec=" + this.getNvDec() + ", runnedAnalytics=" + String.valueOf(this.getRunnedAnalytics()) + ")";
    }
}

