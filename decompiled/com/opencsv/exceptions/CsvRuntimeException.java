/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

public class CsvRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CsvRuntimeException() {
    }

    public CsvRuntimeException(String message) {
        super(message);
    }

    public CsvRuntimeException(String message, Throwable e) {
        super(message, e);
    }
}

