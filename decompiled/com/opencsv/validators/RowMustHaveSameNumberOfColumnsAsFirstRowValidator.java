/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.RowValidator;

public class RowMustHaveSameNumberOfColumnsAsFirstRowValidator
implements RowValidator {
    private static final int NO_ROWS = 0;
    private int numRows = 0;

    @Override
    public boolean isValid(String[] row) {
        if (row == null || row.length == 0) {
            return false;
        }
        if (this.firstRowNotSetYet()) {
            this.numRows = row.length;
        }
        return row.length == this.numRows;
    }

    @Override
    public void validate(String[] row) throws CsvValidationException {
        if (!this.isValid(row)) {
            if (this.firstRowNotSetYet()) {
                throw new CsvValidationException("First row should not be empty or null");
            }
            if (row == null || row.length == 0) {
                throw new CsvValidationException("Row should not be empty or null");
            }
            throw new CsvValidationException(String.format("Row was expected to have %d elements but had %d instead", this.numRows, row.length));
        }
    }

    private boolean firstRowNotSetYet() {
        return this.numRows == 0;
    }
}

