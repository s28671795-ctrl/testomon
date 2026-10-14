/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.RowValidator;
import java.util.function.Function;

public class RowFunctionValidator
implements RowValidator {
    private Function<String[], Boolean> testFunction;
    private String failureMessage;

    public RowFunctionValidator(Function<String[], Boolean> testFunction, String failureMessage) {
        this.testFunction = testFunction;
        this.failureMessage = failureMessage;
    }

    @Override
    public boolean isValid(String[] row) {
        if (row == null || row.length == 0) {
            return false;
        }
        return this.testFunction.apply(row);
    }

    @Override
    public void validate(String[] row) throws CsvValidationException {
        if (!this.isValid(row)) {
            throw new CsvValidationException(this.failureMessage);
        }
    }
}

