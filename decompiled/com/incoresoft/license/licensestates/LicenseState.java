/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.licensestates;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public interface LicenseState {
    public Instant generationDate();

    public Instant activationDate();

    public Instant maintenanceExpireDate();

    public Instant expireDate();

    public Map<String, String> features();

    public boolean isDemo();

    public boolean isValid();

    public boolean isActivated();

    public Optional<Long> licenseTextGenerationTimestamp();
}

