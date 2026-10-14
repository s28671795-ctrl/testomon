/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;

public class ConverterCurrency
extends AbstractCsvConverter {
    public ConverterCurrency(Locale errorLocale) {
        super(Currency.class, null, null, errorLocale);
    }

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        Currency c = null;
        if (StringUtils.isNotEmpty(value)) {
            try {
                c = Currency.getInstance(value);
            }
            catch (IllegalArgumentException e) {
                CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.currency.value"), value, this.type.getName()));
                csve.initCause(e);
                throw csve;
            }
        }
        return c;
    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        String result = null;
        if (value != null) {
            Currency c = (Currency)value;
            result = c.getCurrencyCode();
        }
        return result;
    }
}

