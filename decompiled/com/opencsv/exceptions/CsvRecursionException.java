/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvRuntimeException;

public class CsvRecursionException
extends CsvRuntimeException {
    private static final long serialVersionUID = 1L;
    private final Class<?> offendingType;

    public CsvRecursionException(String message, Class<?> offendingType) {
        super(message);
        this.offendingType = offendingType;
    }

    public Class<?> getOffendingType() {
        return this.offendingType;
    }
}

