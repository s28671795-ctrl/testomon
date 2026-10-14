/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvFieldAssignmentException;

public class CsvDataTypeMismatchException
extends CsvFieldAssignmentException {
    private static final long serialVersionUID = 1L;
    private final transient Object sourceObject;
    private final Class<?> destinationClass;

    public CsvDataTypeMismatchException() {
        this.sourceObject = null;
        this.destinationClass = null;
    }

    public CsvDataTypeMismatchException(Object sourceObject, Class<?> destinationClass) {
        this.sourceObject = sourceObject;
        this.destinationClass = destinationClass;
    }

    public CsvDataTypeMismatchException(String message) {
        super(message);
        this.sourceObject = null;
        this.destinationClass = null;
    }

    public CsvDataTypeMismatchException(Object sourceObject, Class<?> destinationClass, String message) {
        super(message);
        this.sourceObject = sourceObject;
        this.destinationClass = destinationClass;
    }

    public Object getSourceObject() {
        return this.sourceObject;
    }

    public Class<?> getDestinationClass() {
        return this.destinationClass;
    }
}

