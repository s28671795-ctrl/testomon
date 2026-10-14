/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.errors;

import com.incoresoft.license.model.LicenseStatus;
import com.incoresoft.middleware.http.errors.Error;

public class LicenseError
implements Error {
    private final LicenseStatus status;

    public LicenseError(LicenseStatus status) {
        this.status = status;
    }

    @Override
    public String getLocaleKey() {
        return "ERROR_" + this.status.name();
    }
}

