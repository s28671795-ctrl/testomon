/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.exceptions;

import com.incoresoft.license.model.LicenseStatus;
import com.license4j.ValidationStatus;

public class LicenseActivationException
extends RuntimeException {
    private final LicenseStatus licenseStatus;
    private final ValidationStatus validationStatus;

    public LicenseActivationException(LicenseStatus licenseStatus, ValidationStatus validationStatus) {
        this.licenseStatus = licenseStatus;
        this.validationStatus = validationStatus;
    }

    public LicenseStatus getLicenseStatus() {
        return this.licenseStatus;
    }

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }
}

