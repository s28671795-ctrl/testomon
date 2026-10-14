/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licensestates;

import com.incoresoft.license.licensestates.BaseState;
import com.license4j.License;
import com.license4j.ValidationStatus;
import com.license4j.licensetext.LicenseText;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class NormalState
extends BaseState {
    private final Instant generationDate;
    private final Instant activationDate;
    private final Instant maintenanceExpireDate;
    private final Instant expireDate;
    private final Long licenseTextGenerationTimestamp;
    private final Map<String, String> features;
    private final boolean valid;
    private final boolean activated;

    public NormalState(@NotNull License license) {
        super(license);
        LicenseText licenseText = license.getLicenseText();
        this.generationDate = licenseText != null ? Instant.ofEpochMilli(licenseText.getLicenseGenerationDateTime()) : Instant.ofEpochMilli(0L);
        this.activationDate = licenseText != null ? Instant.ofEpochMilli(licenseText.getActivationCompletedDateTime()) : Instant.ofEpochMilli(0L);
        this.maintenanceExpireDate = licenseText != null ? licenseText.getLicenseMaintenanceExpireDate() : Instant.ofEpochMilli(0L);
        this.expireDate = licenseText != null ? licenseText.getLicenseExpireDate() : Instant.ofEpochMilli(0L);
        this.licenseTextGenerationTimestamp = licenseText != null ? Long.valueOf(licenseText.getTimestamp()) : null;
        this.features = licenseText != null ? NormalState.unmodifiableCopy(licenseText.getCustomUnsignedFeatures()) : Collections.emptyMap();
        this.valid = license.getValidationStatus() == ValidationStatus.LICENSE_VALID;
        this.activated = license.isActivationCompleted();
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
        return this.features;
    }

    @Override
    public boolean isValid() {
        return this.valid;
    }

    @Override
    public boolean isActivated() {
        return this.activated;
    }

    @Override
    public Optional<Long> licenseTextGenerationTimestamp() {
        return Optional.ofNullable(this.licenseTextGenerationTimestamp);
    }

    private static Map<String, String> unmodifiableCopy(Map<String, String> features) {
        return features != null ? Collections.unmodifiableMap(new LinkedHashMap<String, String>(features)) : Collections.emptyMap();
    }
}

