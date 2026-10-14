/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license;

import com.incoresoft.license.model.LicenseData;
import com.incoresoft.license.model.LicenseStatus;
import com.license4j.exceptions.LicenseSecurityException;
import java.security.SignatureException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface License {
    public LicenseStatus getStatus();

    public LicenseStatus activateLicense() throws LicenseSecurityException, SignatureException;

    public LicenseStatus deactivateLicense();

    public void updateLicense();

    public String getLicenseKey();

    public String getLicenseText();

    public Map<String, String> getFeatures();

    public boolean isDemo();

    public boolean isValid();

    public boolean isActivated();

    public LicenseData getLicenseData();

    public LocalDate generationDate();

    public LocalDate activationDate();

    public LocalDate expireDate();

    public LocalDate maintenanceExpireDate();

    public Optional<Long> licenseTextGenerationTimestamp();

    public void setDemoState();
}

