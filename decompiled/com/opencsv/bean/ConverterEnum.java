/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public class ConverterEnum
extends AbstractCsvConverter {
    public ConverterEnum(Class<?> type, String locale, String writeLocale, Locale errorLocale) {
        super(type, locale, writeLocale, errorLocale);
    }

    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        Object o = null;
        if (StringUtils.isNotEmpty(value) && (o = EnumUtils.getEnumIgnoreCase(this.type, value)) == null) {
            throw new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("illegal.enum.value"), value, this.type.getName()));
        }
        return o;
    }

    @Override
    public String convertToWrite(Object value) {
        String s2 = "";
        if (value != null) {
            Enum e = (Enum)value;
            s2 = e.name();
        }
        return s2;
    }
}

