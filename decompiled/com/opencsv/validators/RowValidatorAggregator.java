/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.RowValidator;
import java.util.ArrayList;
import java.util.List;

public class RowValidatorAggregator {
    private static final int CAPACITY = 512;
    private static final int MULTIPLIER = 3;
    private List<RowValidator> validators = new ArrayList<RowValidator>();

    public void addValidator(RowValidator validator) {
        if (validator != null) {
            this.validators.add(validator);
        }
    }

    public boolean isValid(String[] row) {
        return this.validators.stream().allMatch(v -> v.isValid(row));
    }

    public void validate(String[] row) throws CsvValidationException {
        if (this.validators.isEmpty()) {
            return;
        }
        StringBuilder combinedExceptionMessage = null;
        for (RowValidator validator : this.validators) {
            try {
                validator.validate(row);
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

    void setValidators(List<RowValidator> validators) {
        this.validators = validators;
    }
}

