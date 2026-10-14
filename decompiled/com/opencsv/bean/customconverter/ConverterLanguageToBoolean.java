/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.beanutils.ConversionException
 *  org.apache.commons.beanutils.converters.BooleanConverter
 */
package com.opencsv.bean.customconverter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.ResourceBundle;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.lang3.StringUtils;

public abstract class ConverterLanguageToBoolean<T, I>
extends AbstractBeanField<T, I> {
    protected abstract String getLocalizedTrue();

    protected abstract String getLocalizedFalse();

    protected abstract String[] getAllLocalizedTrueValues();

    protected abstract String[] getAllLocalizedFalseValues();

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        BooleanConverter bc = new BooleanConverter(this.getAllLocalizedTrueValues(), this.getAllLocalizedFalseValues());
        try {
            return bc.convert(Boolean.class, (Object)value.trim());
        }
        catch (ConversionException e) {
            CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.field.getType(), ResourceBundle.getBundle("convertLanguageToBoolean", this.errorLocale).getString("input.not.boolean"));
            csve.initCause(e);
            throw csve;
        }
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        String result = "";
        try {
            if (value != null) {
                Boolean b = (Boolean)value;
                result = b != false ? this.getLocalizedTrue() : this.getLocalizedFalse();
            }
        }
        catch (ClassCastException e) {
            CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(ResourceBundle.getBundle("convertLanguageToBoolean", this.errorLocale).getString("field.not.boolean"));
            csve.initCause(e);
            throw csve;
        }
        return result;
    }
}

