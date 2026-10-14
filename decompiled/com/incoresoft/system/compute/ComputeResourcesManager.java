/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.compute;

import com.incoresoft.plugin.commons.utils.OsUtils;
import com.incoresoft.system.compute.ComputeResources;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputeResourcesManager {
    private static final Logger log = LoggerFactory.getLogger(ComputeResourcesManager.class);
    private static final int BYTES_IN_GB = 0x40000000;
    private static final int KB_IN_GB = 0x100000;
    private static final String MEM_AVAILABLE_PREFIX = "MemAvailable:";
    Path PROC_MEMINFO_PATH = Path.of("/proc/meminfo", new String[0]);
    private static Instant startedAt;

    public ComputeResources getComputeResources() {
        return new ComputeResources(this.getCpuUsage() * 100.0, this.getTotalMem(), this.getFreeMem(), Duration.between(startedAt, Instant.now()));
    }

    public double getCpuUsage() {
        return ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getCpuLoad();
    }

    private double getTotalMem() {
        return (double)ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getTotalMemorySize() / 1.073741824E9;
    }

    private double getFreeMem() {
        if (OsUtils.getOperatingSystemType() == OsUtils.OSType.Linux) {
            return this.getLinuxAvailableMem();
        }
        return this.getMXBeanFreeMem();
    }

    double getLinuxAvailableMem() {
        try {
            return Files.readAllLines(this.PROC_MEMINFO_PATH).stream().filter(line -> line.startsWith(MEM_AVAILABLE_PREFIX)).findFirst().map(this::parseMemInfoLineKb).map(kb -> (double)kb.longValue() / 1048576.0).orElseGet(() -> {
                log.warn("MemAvailable not found in {}, falling back to MXBean", (Object)this.PROC_MEMINFO_PATH);
                return this.getMXBeanFreeMem();
            });
        }
        catch (Exception e) {
            log.warn("Failed to read {}, falling back to MXBean", (Object)this.PROC_MEMINFO_PATH, (Object)e);
            return this.getMXBeanFreeMem();
        }
    }

    private long parseMemInfoLineKb(String line) {
        String[] parts = line.split("\\s+");
        return Long.parseLong(parts[1]);
    }

    private double getMXBeanFreeMem() {
        return (double)ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getFreeMemorySize() / 1.073741824E9;
    }

    public static void setStartedAt(Instant startedAt) {
        ComputeResourcesManager.startedAt = startedAt;
    }
}

