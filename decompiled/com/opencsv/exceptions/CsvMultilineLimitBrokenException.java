/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import java.io.IOException;

public class CsvMultilineLimitBrokenException
extends IOException {
    private static final long serialVersionUID = 1L;
    private long row;
    private String context;
    private int multilineLimit;

    public int getMultilineLimit() {
        return this.multilineLimit;
    }

    public long getRow() {
        return this.row;
    }

    public String getContext() {
        return this.context;
    }

    public CsvMultilineLimitBrokenException() {
    }

    public CsvMultilineLimitBrokenException(String message, long row, String context, int multilineLimit) {
        super(message);
        this.row = row;
        this.context = context;
        this.multilineLimit = multilineLimit;
    }
}

