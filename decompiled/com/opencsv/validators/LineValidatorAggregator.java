/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.LineValidator;
import java.util.ArrayList;
import java.util.List;

public class LineValidatorAggregator {
    private static final int CAPACITY = 512;
    private static final int MULTIPLIER = 3;
    private List<LineValidator> validators = new ArrayList<LineValidator>();

    public void addValidator(LineValidator validator) {
        if (validator != null) {
            this.validators.add(validator);
        }
    }

    public boolean isValid(String line) {
        return this.validators.stream().allMatch(v -> v.isValid(line));
    }

    public void validate(String line) throws CsvValidationException {
        if (this.validators.isEmpty()) {
            return;
        }
        StringBuilder combinedExceptionMessage = null;
        for (LineValidator validator : this.validators) {
            try {
                validator.validate(line);
            }
            catch (CsvValidationException ex) {
                if (combinedExceptionMessage == null) {
                    int length = (ex.getMessage().length() + 2) * 3;
                    combinedExceptionMessage = new StringBuilder(Math.max(length, 512));
                }
                combinedExceptionMessage.append(ex.getMessage()).append("\n");
            }
        }
        if (combinedExceptionMessage != null && combinedExceptionMessage.length() > 0) {
            throw new CsvValidationException(combinedExceptionMessage.toString());
        }
    }

    void setValidators(List<LineValidator> validators) {
        this.validators = validators;
    }
}

