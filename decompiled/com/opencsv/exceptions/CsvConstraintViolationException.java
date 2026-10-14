/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvFieldAssignmentException;

public class CsvConstraintViolationException
extends CsvFieldAssignmentException {
    private static final long serialVersionUID = 1L;
    private final transient Object sourceObject;

    public CsvConstraintViolationException() {
        this.sourceObject = null;
    }

    public CsvConstraintViolationException(Object sourceObject) {
        this.sourceObject = sourceObject;
    }

    public CsvConstraintViolationException(String message) {
        super(message);
        this.sourceObject = null;
    }

    public CsvConstraintViolationException(Object sourceObject, String message) {
        super(message);
        this.sourceObject = sourceObject;
    }

    public Object getSourceObject() {
        return this.sourceObject;
    }
}

