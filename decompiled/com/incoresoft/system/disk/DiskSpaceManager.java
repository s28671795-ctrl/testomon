/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.disk;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.config.models.StorageConfig;
import com.incoresoft.system.disk.CombinedDiskSpaceInfo;
import com.incoresoft.system.disk.DiskSpaceInfo;
import com.incoresoft.system.disk.DiskSpaceListener;
import io.minio.admin.MinioAdminClient;
import io.minio.admin.messages.info.Disk;
import io.minio.admin.messages.info.ServerProperties;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiskSpaceManager {
    private static final Logger log = LoggerFactory.getLogger(DiskSpaceManager.class);
    private final ConfigManager<?> configManager;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Set<DiskSpaceListener> listeners = new HashSet<DiskSpaceListener>();
    private static final Duration EXECUTION_PERIOD = Duration.ofMinutes(5L);
    private static final Duration NOTIFICATION_COOLDOWN = Duration.ofHours(1L);
    private Instant lastNotificationTime = Instant.MIN;

    public void scheduleDiskSpaceCheck() {
        this.executorService.scheduleAtFixedRate(this::checkSpace, EXECUTION_PERIOD.toSeconds(), EXECUTION_PERIOD.toSeconds(), TimeUnit.SECONDS);
    }

    public void addListener(DiskSpaceListener listener) {
        this.listeners.add(listener);
    }

    public DiskSpaceInfo getStorageDiskSpace() {
        Optional<String> storageSettingsOptional = this.configManager.getConfig().getStorage().localDirectory();
        if (storageSettingsOptional.isPresent()) {
            return this.getDiskSpace(new File(storageSettingsOptional.get()).getAbsolutePath());
        }
        return new DiskSpaceInfo("", 0L, 0L, 0L);
    }

    public DiskSpaceInfo getDiskSpace(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid path");
        }
        File root = directory.toPath().getRoot().toFile();
        long totalSpace = root.getTotalSpace();
        long freeSpace = root.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;
        return new DiskSpaceInfo(root.getAbsolutePath(), totalSpace, freeSpace, usedSpace);
    }

    private void checkSpace() {
        StorageConfig config = this.configManager.getConfig().getStorage();
        DiskSpaceInfo mainDiskSpace = this.getDiskSpace(System.getProperty("user.dir"));
        DiskSpaceInfo storageDiskSpace = switch (config.getProperties().get("type")) {
            case "local" -> this.getDiskSpace(this.getStoragePath().orElseThrow());
            case "s3" -> this.getS3Space(config);
            default -> throw new IllegalStateException("Unexpected storage type: " + config.getProperties().get("type"));
        };
        this.notifyListenersIfNecessary(mainDiskSpace, storageDiskSpace);
        this.logFreeSpace(storageDiskSpace);
    }

    private Optional<String> getStoragePath() {
        return this.configManager.getStorage().localDirectory().map(File::new).map(File::getAbsolutePath);
    }

    private void notifyListenersIfNecessary(DiskSpaceInfo mainDiskSpace, DiskSpaceInfo storageDiskSpace) {
        CombinedDiskSpaceInfo combinedDiskSpaceInfo;
        if (this.isNotificationCooldownOver() && !(combinedDiskSpaceInfo = this.createCombinedDiskSpaceInfo(mainDiskSpace, storageDiskSpace)).isEmpty()) {
            this.notifyListeners(combinedDiskSpaceInfo);
            this.updateLastNotificationTime();
        }
    }

    private boolean isNotificationCooldownOver() {
        return Duration.between(this.lastNotificationTime, Instant.now()).compareTo(NOTIFICATION_COOLDOWN) > 0;
    }

    private CombinedDiskSpaceInfo createCombinedDiskSpaceInfo(DiskSpaceInfo mainDiskSpace, DiskSpaceInfo storageDiskSpace) {
        CombinedDiskSpaceInfo info = new CombinedDiskSpaceInfo();
        if (this.isDiskSpaceLow(mainDiskSpace)) {
            info.setSystem(mainDiskSpace);
        }
        if (this.isDiskSpaceLow(storageDiskSpace)) {
            info.setStorage(storageDiskSpace);
        }
        return info;
    }

    private void notifyListeners(CombinedDiskSpaceInfo info) {
        this.listeners.forEach(listener -> listener.onLowDiskSpaceRecognized(info));
    }

    private void updateLastNotificationTime() {
        this.lastNotificationTime = Instant.now();
    }

    private void logFreeSpace(DiskSpaceInfo diskSpaceInfo) {
        log.info("Storage check completed. Free space: {}%", (Object)this.calculateFreeSpacePercentage(diskSpaceInfo));
    }

    private double calculateFreeSpacePercentage(DiskSpaceInfo diskSpaceInfo) {
        return (double)diskSpaceInfo.freeSpace() / (double)diskSpaceInfo.totalSpace() * 100.0;
    }

    public boolean isDiskSpaceLow(DiskSpaceInfo diskSpace) {
        long totalSpace = diskSpace.totalSpace();
        long freeSpace = diskSpace.freeSpace();
        if (totalSpace == 0L) {
            return false;
        }
        double freeSpacePercentage = (double)freeSpace / (double)totalSpace * 100.0;
        return freeSpacePercentage < 10.0;
    }

    public boolean isStorageDiskSpaceLow() {
        StorageConfig config = this.configManager.getConfig().getStorage();
        DiskSpaceInfo storageDiskSpace = switch (config.getProperties().get("type")) {
            case "local" -> this.getDiskSpace(this.getStoragePath().orElseThrow());
            case "s3" -> this.getS3Space(config);
            default -> throw new IllegalStateException("Unexpected storage type: " + config.getProperties().get("type"));
        };
        return this.isDiskSpaceLow(storageDiskSpace);
    }

    private DiskSpaceInfo getS3Space(StorageConfig config) {
        String url2 = config.getProperties().get("url");
        String login = config.getProperties().get("access_key");
        String password = config.getProperties().get("secret_key");
        MinioAdminClient minioAdminClient = MinioAdminClient.builder().endpoint(url2).credentials(login, password).build();
        BigDecimal totalSpace = BigDecimal.ZERO;
        BigDecimal usedSpace = BigDecimal.ZERO;
        BigDecimal freeSpace = BigDecimal.ZERO;
        try {
            for (ServerProperties server : minioAdminClient.getServerInfo().servers()) {
                for (Disk disk : server.disks()) {
                    totalSpace = totalSpace.add(disk.totalspace());
                    usedSpace = usedSpace.add(disk.usedspace());
                    freeSpace = freeSpace.add(disk.availspace());
                }
            }
            return new DiskSpaceInfo("", totalSpace.longValue(), freeSpace.longValue(), usedSpace.longValue());
        }
        catch (IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    public DiskSpaceManager(ConfigManager<?> configManager) {
        this.configManager = configManager;
    }
}

