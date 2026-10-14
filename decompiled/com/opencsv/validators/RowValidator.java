/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.validators;

import com.opencsv.exceptions.CsvValidationException;

public interface RowValidator {
    public boolean isValid(String[] var1);

    public void validate(String[] var1) throws CsvValidationException;
}

