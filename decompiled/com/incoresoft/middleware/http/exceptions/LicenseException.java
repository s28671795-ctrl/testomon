/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http.exceptions;

public class LicenseException
extends RuntimeException {
    public LicenseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return String.format("License error %s", super.getMessage());
    }
}

