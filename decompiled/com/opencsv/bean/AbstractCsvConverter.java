/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.CsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Locale;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractCsvConverter
implements CsvConverter {
    protected Class<?> type;
    protected Locale locale;
    protected Locale writeLocale;
    protected Locale errorLocale;

    protected AbstractCsvConverter() {
        this.type = null;
        this.locale = null;
        this.writeLocale = null;
        this.errorLocale = Locale.getDefault();
    }

    protected AbstractCsvConverter(Class<?> type, String locale, String writeLocale, Locale errorLocale) {
        this.type = type;
        this.locale = StringUtils.isNotEmpty(locale) ? Locale.forLanguageTag(locale) : null;
        this.writeLocale = StringUtils.isNotEmpty(writeLocale) ? Locale.forLanguageTag(writeLocale) : null;
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        return Objects.toString(value, "");
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    @Override
    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public void setLocale(String locale) {
        this.locale = StringUtils.isNotEmpty(locale) ? Locale.forLanguageTag(locale) : null;
    }

    @Override
    public void setWriteLocale(String writeLocale) {
        this.writeLocale = StringUtils.isNotEmpty(writeLocale) ? Locale.forLanguageTag(writeLocale) : null;
    }
}

