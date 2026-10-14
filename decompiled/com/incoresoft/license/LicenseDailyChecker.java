/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license;

import com.google.inject.Inject;
import com.incoresoft.license.LicenseCheckerListener;
import com.incoresoft.license.LicenseManager;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseDailyChecker {
    private static final Logger log = LoggerFactory.getLogger(LicenseDailyChecker.class);
    private static final Duration RANDOM_DELAY_BOUND = Duration.ofHours(1L);
    private final Set<LicenseCheckerListener> listeners = Collections.newSetFromMap(new ConcurrentHashMap());
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final LicenseManager licenseManager;

    @Inject
    public void init() {
        Duration initialDelay = this.getInitialDelay();
        log.info("License daily checker scheduled to start at: " + String.valueOf(ZonedDateTime.now().plus(initialDelay)));
        this.scheduler.scheduleAtFixedRate(this::checkLicense, initialDelay.toSeconds(), Duration.ofDays(1L).toSeconds(), TimeUnit.SECONDS);
    }

    public Duration getInitialDelay() {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());
        ZonedDateTime nextDay = now.plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime scheduledCheckTime = nextDay.plusSeconds((long)(Math.random() * (double)RANDOM_DELAY_BOUND.toSeconds()));
        return Duration.between(now, scheduledCheckTime);
    }

    public void addListener(LicenseCheckerListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(LicenseCheckerListener listener) {
        this.listeners.remove(listener);
    }

    public void checkLicense() {
        this.licenseManager.reactivateLicenses();
        log.info("License daily check executed.");
        this.notifyListeners();
    }

    private void notifyListeners() {
        for (LicenseCheckerListener listener : this.listeners) {
            try {
                listener.onLicenseCheck();
            }
            catch (Exception e) {
                log.error("Error while notifying listener: " + String.valueOf(listener), e);
            }
        }
    }

    @Inject
    public LicenseDailyChecker(LicenseManager licenseManager) {
        this.licenseManager = licenseManager;
    }
}

