/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import java.io.IOException;

public class CsvMalformedLineException
extends IOException {
    private static final long serialVersionUID = 1L;
    private long lineNumber;
    private String context;

    public long getLineNumber() {
        return this.lineNumber;
    }

    public String getContext() {
        return this.context;
    }

    public CsvMalformedLineException() {
    }

    public CsvMalformedLineException(String message, long lineNumber, String context) {
        super(message);
        this.lineNumber = lineNumber;
        this.context = context;
    }
}

