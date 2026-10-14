/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvFieldAssignmentException;

public class CsvValidationException
extends CsvFieldAssignmentException {
    private static final long serialVersionUID = 1L;

    public CsvValidationException() {
    }

    public CsvValidationException(String message) {
        super(message);
    }
}

