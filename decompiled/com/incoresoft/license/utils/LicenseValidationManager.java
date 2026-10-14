/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.utils;

import com.google.inject.Inject;
import com.incoresoft.license.LicenseImpl;
import com.incoresoft.license.LicenseProperties;
import com.incoresoft.license.model.LicenseStatus;
import com.license4j.ActivationStatus;
import com.license4j.License;
import com.license4j.ValidationStatus;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.validator.LicenseValidator;
import java.security.SignatureException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseValidationManager {
    private static final Logger log = LoggerFactory.getLogger(LicenseValidationManager.class);
    private final LicenseProperties licenseProperties;

    public LicenseImpl fromKey(@NotNull String licenseKey) {
        LicenseImpl licenseFromKey = new LicenseImpl(licenseKey, null, this);
        try {
            licenseFromKey.activateLicense();
        }
        catch (LicenseSecurityException | SignatureException e) {
            throw new RuntimeException(e);
        }
        return licenseFromKey;
    }

    public LicenseImpl fromText(@NotNull String activatedLicenseText) {
        return new LicenseImpl(null, activatedLicenseText, this);
    }

    public License validateLicenseText(String activatedLicenseText) {
        return LicenseValidator.validate(activatedLicenseText, this.licenseProperties.getPublicKey(), this.licenseProperties.getProductId(), null, null, null, null);
    }

    public License validateLicenseKey(String licenseKey) {
        return LicenseValidator.validate(licenseKey, this.licenseProperties.getPublicKey(), this.licenseProperties.getProductId(), null, null, 0);
    }

    public static LicenseStatus getByStatus(ValidationStatus status) {
        return switch (status) {
            case ValidationStatus.LICENSE_VALID -> LicenseStatus.LICENSE_VALID;
            case ValidationStatus.LICENSE_INVALID -> LicenseStatus.LICENSE_INVALID;
            case ValidationStatus.LICENSE_EXPIRED -> LicenseStatus.LICENSE_EXPIRED;
            case ValidationStatus.LICENSE_MAINTENANCE_EXPIRED -> LicenseStatus.LICENSE_MAINTENANCE_EXPIRED;
            case ValidationStatus.INCORRECT_SYSTEM_TIME -> LicenseStatus.INCORRECT_SYSTEM_TIME;
            case ValidationStatus.MISMATCH_HARDWARE_ID -> LicenseStatus.HARDWARE_ID_MISMATCH;
            case ValidationStatus.MISMATCH_PRODUCT_ID -> LicenseStatus.PRODUCT_ID_MISMATCH;
            default -> LicenseStatus.UNKNOWN;
        };
    }

    public static LicenseStatus getByStatus(ActivationStatus status) {
        return switch (status) {
            case ActivationStatus.ACTIVATION_SERVER_CONNECTION_ERROR -> LicenseStatus.LICENSE_SERVER_CONNECTION_FAILED;
            case ActivationStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER -> LicenseStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER;
            case ActivationStatus.ALREADY_ACTIVATED_ON_ANOTHER_COMPUTER -> LicenseStatus.ALREADY_ACTIVATED_ON_ANOTHER_COMPUTER;
            case ActivationStatus.MULTIPLE_ACTIVATION_LIMIT_REACHED -> LicenseStatus.MULTIPLE_ACTIVATION_LIMIT_REACHED;
            case ActivationStatus.MULTIPLE_DEACTIVATION_LIMIT_REACHED -> LicenseStatus.MULTIPLE_DEACTIVATION_LIMIT_REACHED;
            case ActivationStatus.ACTIVATION_COMPLETED -> LicenseStatus.ACTIVATION_COMPLETED;
            case ActivationStatus.ACTIVATION_REQUIRED -> LicenseStatus.ACTIVATION_REQUIRED;
            case ActivationStatus.ACTIVATION_NOT_FOUND_ON_SERVER -> LicenseStatus.ACTIVATION_NOT_FOUND_ON_SERVER;
            case ActivationStatus.DEACTIVATION_COMPLETED -> LicenseStatus.DEACTIVATION_COMPLETED;
            default -> LicenseStatus.UNKNOWN;
        };
    }

    public String getManualActivationString(String key) {
        return this.validateLicenseKey(key).getManualActivationRequestString();
    }

    public LicenseStatus checkLicenseKey(String key) {
        License tempLicense = this.validateLicenseKey(key);
        if (tempLicense.getValidationStatus() == ValidationStatus.LICENSE_VALID) {
            return LicenseStatus.LICENSE_VALID;
        }
        log.info("License key validation failed - " + String.valueOf((Object)tempLicense.getValidationStatus()));
        return LicenseValidationManager.getByStatus(tempLicense.getValidationStatus());
    }

    @Inject
    public LicenseValidationManager(LicenseProperties licenseProperties) {
        this.licenseProperties = licenseProperties;
    }

    public LicenseProperties getLicenseProperties() {
        return this.licenseProperties;
    }
}

