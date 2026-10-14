/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.validators;

import com.opencsv.bean.BeanField;
import com.opencsv.exceptions.CsvValidationException;

public interface StringValidator {
    public boolean isValid(String var1);

    public void validate(String var1, BeanField var2) throws CsvValidationException;

    public void setParameterString(String var1);
}

