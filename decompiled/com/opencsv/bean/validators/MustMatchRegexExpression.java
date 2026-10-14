/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.validators;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.validators.StringValidator;
import com.opencsv.exceptions.CsvValidationException;
import java.util.ResourceBundle;

public class MustMatchRegexExpression
implements StringValidator {
    private String regex = "";

    @Override
    public boolean isValid(String value) {
        if (this.regex.isEmpty()) {
            return true;
        }
        return value.matches(this.regex);
    }

    @Override
    public void validate(String value, BeanField field2) throws CsvValidationException {
        if (!this.isValid(value)) {
            throw new CsvValidationException(String.format(ResourceBundle.getBundle("mustMatchRegex", field2.getErrorLocale()).getString("validator.regex.mismatch"), field2.getField().getName(), value, this.regex));
        }
    }

    @Override
    public void setParameterString(String value) {
        if (value != null && !value.isEmpty()) {
            this.regex = value;
        }
    }
}

