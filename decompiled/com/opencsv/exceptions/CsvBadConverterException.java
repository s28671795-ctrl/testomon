/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvRuntimeException;

public class CsvBadConverterException
extends CsvRuntimeException {
    private static final long serialVersionUID = 1L;
    private final Class<?> converterClass;

    public CsvBadConverterException() {
        this.converterClass = null;
    }

    public CsvBadConverterException(Class<?> converterClass) {
        this.converterClass = converterClass;
    }

    public CsvBadConverterException(String message) {
        super(message);
        this.converterClass = null;
    }

    public CsvBadConverterException(Class<?> converterClass, String message) {
        super(message);
        this.converterClass = converterClass;
    }

    public Class<?> getConverterClass() {
        return this.converterClass;
    }
}

