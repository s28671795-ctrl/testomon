/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licensestates;

import com.incoresoft.license.licensestates.LicenseState;
import com.license4j.License;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DemoState
implements LicenseState {
    private final Instant generationDate;
    private final Instant activationDate;
    private final Instant maintenanceExpireDate;
    private final Instant expireDate;
    private final long licenseTextGenerationTimestamp;

    public DemoState(License license) {
        if (license.getLicenseText() != null) {
            this.generationDate = Instant.ofEpochMilli(license.getLicenseText().getLicenseGenerationDateTime());
            this.activationDate = Instant.ofEpochMilli(license.getLicenseText().getActivationCompletedDateTime());
            this.maintenanceExpireDate = license.getLicenseText().getLicenseMaintenanceExpireDate();
            this.expireDate = license.getLicenseText().getLicenseExpireDate();
            this.licenseTextGenerationTimestamp = license.getLicenseText().getTimestamp();
        } else {
            this.generationDate = Instant.ofEpochMilli(0L);
            this.activationDate = Instant.ofEpochMilli(0L);
            this.maintenanceExpireDate = Instant.ofEpochMilli(0L);
            this.expireDate = Instant.ofEpochMilli(0L);
            this.licenseTextGenerationTimestamp = 0L;
        }
    }

    @Override
    public Instant generationDate() {
        return this.generationDate;
    }

    @Override
    public Instant activationDate() {
        return this.activationDate;
    }

    @Override
    public Instant maintenanceExpireDate() {
        return this.maintenanceExpireDate;
    }

    @Override
    public Instant expireDate() {
        return this.expireDate;
    }

    @Override
    public Map<String, String> features() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isDemo() {
        return true;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isActivated() {
        return false;
    }

    @Override
    public Optional<Long> licenseTextGenerationTimestamp() {
        return Optional.of(this.licenseTextGenerationTimestamp);
    }
}

