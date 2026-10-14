/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.beanutils.BeanUtilsBean
 *  org.apache.commons.beanutils.ConversionException
 *  org.apache.commons.beanutils.ConvertUtilsBean
 *  org.apache.commons.beanutils.locale.LocaleConvertUtilsBean
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.locale.LocaleConvertUtilsBean;
import org.apache.commons.lang3.StringUtils;

public class ConverterPrimitiveTypes
extends AbstractCsvConverter {
    protected final ConvertUtilsBean readConverter;
    protected final LocaleConvertUtilsBean readLocaleConverter;
    protected final ConvertUtilsBean writeConverter;
    protected final LocaleConvertUtilsBean writeLocaleConverter;

    public ConverterPrimitiveTypes(Class<?> type, String locale, String writeLocale, Locale errorLocale) {
        super(type, locale, writeLocale, errorLocale);
        if (this.locale == null) {
            this.readConverter = BeanUtilsBean.getInstance().getConvertUtils();
            this.readConverter.register(true, false, 0);
            this.readLocaleConverter = null;
        } else {
            this.readLocaleConverter = new LocaleConvertUtilsBean();
            this.readLocaleConverter.setDefaultLocale(this.locale);
            this.readConverter = null;
        }
        if (this.writeLocale == null) {
            this.writeConverter = BeanUtilsBean.getInstance().getConvertUtils();
            this.writeConverter.register(true, false, 0);
            this.writeLocaleConverter = null;
        } else {
            this.writeLocaleConverter = new LocaleConvertUtilsBean();
            this.writeLocaleConverter.setDefaultLocale(this.writeLocale);
            this.writeConverter = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        Object o;
        block10: {
            o = null;
            if (StringUtils.isNotBlank(value) || value != null && this.type.equals(String.class)) {
                try {
                    if (this.readConverter != null) {
                        ConvertUtilsBean convertUtilsBean = this.readConverter;
                        synchronized (convertUtilsBean) {
                            o = this.readConverter.convert(value, this.type);
                            break block10;
                        }
                    }
                    LocaleConvertUtilsBean localeConvertUtilsBean = this.readLocaleConverter;
                    synchronized (localeConvertUtilsBean) {
                        o = this.readLocaleConverter.convert(value, this.type);
                    }
                }
                catch (ConversionException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("conversion.impossible"), value, this.type.getCanonicalName()));
                    csve.initCause(e);
                    throw csve;
                }
            }
        }
        return o;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        String result;
        block10: {
            result = null;
            if (value != null) {
                try {
                    if (this.writeConverter != null) {
                        ConvertUtilsBean convertUtilsBean = this.writeConverter;
                        synchronized (convertUtilsBean) {
                            result = this.writeConverter.convert(value);
                            break block10;
                        }
                    }
                    LocaleConvertUtilsBean localeConvertUtilsBean = this.writeLocaleConverter;
                    synchronized (localeConvertUtilsBean) {
                        result = this.writeLocaleConverter.convert(value);
                    }
                }
                catch (ConversionException e) {
                    CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("field.not.primitive"));
                    csve.initCause(e);
                    throw csve;
                }
            }
        }
        return result;
    }
}

