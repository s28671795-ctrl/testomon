/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import org.apache.commons.lang3.ArrayUtils;

public class CsvException
extends Exception {
    private static final long serialVersionUID = 1L;
    private long lineNumber = -1L;
    private String[] line;

    public CsvException() {
    }

    public CsvException(String message) {
        super(message);
    }

    public long getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String[] getLine() {
        return ArrayUtils.clone(this.line);
    }

    public void setLine(String[] line) {
        this.line = ArrayUtils.clone(line);
    }
}

