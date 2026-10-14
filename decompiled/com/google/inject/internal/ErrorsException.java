/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Errors;

public class ErrorsException
extends Exception {
    private final Errors errors;

    public ErrorsException(Errors errors2) {
        this.errors = errors2;
    }

    public Errors getErrors() {
        return this.errors;
    }
}

