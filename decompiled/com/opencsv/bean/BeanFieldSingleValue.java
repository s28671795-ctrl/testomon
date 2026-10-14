/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class BeanFieldSingleValue<T, I>
extends AbstractBeanField<T, I> {
    protected final Pattern capture;
    protected final String writeFormat;

    public BeanFieldSingleValue(Class<?> type, Field field2, boolean required, Locale errorLocale, CsvConverter converter, String capture, String format) {
        super(type, field2, required, errorLocale, converter);
        this.capture = OpencsvUtils.compilePatternAtLeastOneGroup(capture, 0, BeanFieldSingleValue.class, this.errorLocale);
        this.writeFormat = format;
        OpencsvUtils.verifyFormatString(this.writeFormat, BeanFieldSingleValue.class, this.errorLocale);
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Matcher m4;
        String convertValue = value;
        if (this.capture != null && value != null && (m4 = this.capture.matcher(value)).matches()) {
            convertValue = m4.group(1);
        }
        if (this.converter != null) {
            return this.converter.convertToRead(convertValue);
        }
        throw new CsvBadConverterException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("no.converter.specified"));
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        if (this.converter != null) {
            String s2 = this.converter.convertToWrite(value);
            if (StringUtils.isNotEmpty(this.writeFormat) && StringUtils.isNotEmpty(s2)) {
                s2 = String.format(this.writeFormat, s2);
            }
            return s2;
        }
        throw new CsvBadConverterException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("no.converter.specified"));
    }
}

