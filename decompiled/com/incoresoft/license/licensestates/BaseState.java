/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licensestates;

import com.incoresoft.license.licensestates.LicenseState;
import com.license4j.License;
import com.license4j.ValidationStatus;
import com.license4j.licensetext.LicenseText;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public abstract class BaseState
implements LicenseState {
    protected final License license;

    public BaseState(@NotNull License license) {
        this.license = Objects.requireNonNull(license, "license text must be not null");
    }

    @Override
    public Instant generationDate() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return Instant.ofEpochMilli(licenseText.getLicenseGenerationDateTime());
        }
        return Instant.ofEpochMilli(0L);
    }

    @Override
    public Instant activationDate() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return Instant.ofEpochMilli(licenseText.getActivationCompletedDateTime());
        }
        return Instant.ofEpochMilli(0L);
    }

    @Override
    public Instant maintenanceExpireDate() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return licenseText.getLicenseMaintenanceExpireDate();
        }
        return Instant.ofEpochMilli(0L);
    }

    @Override
    public Instant expireDate() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return licenseText.getLicenseExpireDate();
        }
        return Instant.ofEpochMilli(0L);
    }

    @Override
    public Map<String, String> features() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return licenseText.getCustomUnsignedFeatures();
        }
        return Collections.emptyMap();
    }

    @Override
    public boolean isDemo() {
        return false;
    }

    @Override
    public boolean isValid() {
        return this.license.getValidationStatus() == ValidationStatus.LICENSE_VALID;
    }

    @Override
    public boolean isActivated() {
        return this.license.isActivationCompleted();
    }

    @Override
    public Optional<Long> licenseTextGenerationTimestamp() {
        LicenseText licenseText = this.license.getLicenseText();
        if (licenseText != null) {
            return Optional.of(licenseText.getTimestamp());
        }
        return Optional.empty();
    }
}

