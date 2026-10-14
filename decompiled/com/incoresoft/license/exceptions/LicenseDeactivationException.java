/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.exceptions;

import com.incoresoft.license.model.LicenseStatus;

public class LicenseDeactivationException
extends RuntimeException {
    private final LicenseStatus licenseStatus;

    public LicenseDeactivationException(LicenseStatus licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public LicenseStatus getLicenseStatus() {
        return this.licenseStatus;
    }
}

