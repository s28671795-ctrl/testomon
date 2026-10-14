/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import java.util.LinkedList;
import java.util.List;

public class CsvChainedException
extends CsvException {
    private final List<CsvFieldAssignmentException> exceptionChain = new LinkedList<CsvFieldAssignmentException>();

    public CsvChainedException(CsvFieldAssignmentException csve) {
        this.exceptionChain.add(csve);
    }

    public void add(CsvFieldAssignmentException csve) {
        this.exceptionChain.add(csve);
    }

    public List<CsvFieldAssignmentException> getExceptionChain() {
        return this.exceptionChain;
    }

    @Override
    public void setLine(String[] line) {
        super.setLine(line);
        this.exceptionChain.forEach(e -> e.setLine(line));
    }

    @Override
    public void setLineNumber(long lineNumber) {
        super.setLineNumber(lineNumber);
        this.exceptionChain.forEach(e -> e.setLineNumber(lineNumber));
    }

    public boolean hasOnlyOneException() {
        return this.exceptionChain.size() == 1;
    }

    public CsvFieldAssignmentException getFirstException() {
        return this.exceptionChain.isEmpty() ? null : this.exceptionChain.get(0);
    }
}

