/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics.listeners;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.incoresoft.license.OnLicenseListener;
import com.incoresoft.license.model.LicenseGrabStatus;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.errors.CoreAnalyticsErrors;
import com.incoresoft.vezha.license.LicenseUsageManager;
import com.incoresoft.vezha.protocol.ws.AnalyticsWSBroadcaster;
import com.incoresoft.vezha.ws.analytics.events.AnalyticsServerErrorsEvent;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseAndDiskChangesListener
implements OnLicenseListener {
    private static final Logger log = LoggerFactory.getLogger(LicenseAndDiskChangesListener.class);
    private static final Duration CHECK_PERIOD = Duration.ofMinutes(30L);
    private final ScheduledExecutorService DISK_SPACE_CHECKER = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
    private final Provider<AnalyticsManagerImpl> analyticsManager;
    private final LicenseUsageManager licenseUsageManager;
    private final AnalyticsWSBroadcaster analyticsWSBroadcaster;

    @Inject
    public LicenseAndDiskChangesListener(@NotNull Provider<AnalyticsManagerImpl> analyticsManager, @NotNull LicenseUsageManager licenseUsageManager, @NotNull AnalyticsWSBroadcaster analyticsWSBroadcaster) {
        this.analyticsManager = Objects.requireNonNull(analyticsManager);
        this.licenseUsageManager = Objects.requireNonNull(licenseUsageManager);
        this.analyticsWSBroadcaster = Objects.requireNonNull(analyticsWSBroadcaster);
    }

    @Inject
    public void init() {
        this.DISK_SPACE_CHECKER.scheduleAtFixedRate(this::pauseOrUnpauseAccordingToResourceAvailability, Duration.ofMinutes(5L).toMillis(), CHECK_PERIOD.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void onLicenseChanged() {
        this.pauseOrUnpauseAccordingToResourceAvailability();
    }

    private void pauseOrUnpauseAccordingToResourceAvailability() {
        log.info("Pausing or unpausing analytics according to resource availability...");
        List<Analytics> runningAnalytics = this.analyticsManager.get().getAll().stream().filter(analytics -> analytics.getStatus() != AnalyticsStatus.stopped).toList();
        for (Analytics analytics2 : runningAnalytics) {
            BaseAnalytics baseAnalytics = (BaseAnalytics)analytics2;
            boolean errorsFound = false;
            if (this.analyticsManager.get().getAnalyticsQueue().blockedByLowDiskSpace()) {
                log.warn("Analytics with id {} will be paused due to low free disk space", (Object)baseAnalytics.getId());
                baseAnalytics.addErrorIfAbsent(CoreAnalyticsErrors.NO_SPACE_ON_DISK);
                errorsFound = true;
            }
            if (this.licenseUsageManager.grabLicense(analytics2) != LicenseGrabStatus.LICENSED) {
                log.warn("Analytics with id {} will be paused due to license absence", (Object)baseAnalytics.getId());
                baseAnalytics.addErrorIfAbsent(CoreAnalyticsErrors.NO_AVAILABLE_LICENSE);
                errorsFound = true;
            }
            if (errorsFound && analytics2.getStatus() != AnalyticsStatus.paused) {
                this.analyticsManager.get().pause(analytics2.getId());
            } else if (!errorsFound) {
                if (analytics2.getStatus() == AnalyticsStatus.paused) {
                    log.warn("Analytics with id {} will be unpaused", (Object)baseAnalytics.getId());
                    this.analyticsManager.get().unpause(analytics2.getId());
                }
                baseAnalytics.removeErrorIfPresent(CoreAnalyticsErrors.NO_SPACE_ON_DISK);
                baseAnalytics.removeErrorIfPresent(CoreAnalyticsErrors.NO_AVAILABLE_LICENSE);
            }
            this.analyticsWSBroadcaster.broadcast(new AnalyticsServerErrorsEvent(baseAnalytics), user -> user.hasAccessTo(baseAnalytics));
        }
        log.info("Done with pausing or unpausing analytics.");
    }
}

