/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public class ConverterNumber
extends AbstractCsvConverter {
    private final DecimalFormat readFormatter;
    private final DecimalFormat writeFormatter;
    private final UnaryOperator<Number> readConversionFunction;

    public ConverterNumber(Class<?> type, String locale, String writeLocale, Locale errorLocale, String readFormat, String writeFormat, RoundingMode roundingMode) throws CsvBadConverterException {
        super(type, locale, writeLocale, errorLocale);
        if (!Number.class.isAssignableFrom(this.type.isPrimitive() ? ClassUtils.primitiveToWrapper(this.type) : this.type)) {
            throw new CsvBadConverterException(ConverterNumber.class, ResourceBundle.getBundle("opencsv", this.errorLocale).getString("csvnumber.not.number"));
        }
        this.readFormatter = this.createDecimalFormat(readFormat, this.locale, roundingMode);
        if (this.type == BigInteger.class || this.type == BigDecimal.class) {
            this.readFormatter.setParseBigDecimal(true);
        }
        this.readConversionFunction = this.type == Byte.class || this.type == Byte.TYPE ? Number::byteValue : (this.type == Short.class || this.type == Short.TYPE ? Number::shortValue : (this.type == Integer.class || this.type == Integer.TYPE ? Number::intValue : (this.type == Long.class || this.type == Long.TYPE ? Number::longValue : (this.type == Float.class || this.type == Float.TYPE ? Number::floatValue : (this.type == Double.class || this.type == Double.TYPE ? Number::doubleValue : (this.type == BigInteger.class ? n -> ((BigDecimal)n).toBigInteger() : n -> n))))));
        this.writeFormatter = this.createDecimalFormat(writeFormat, this.writeLocale, roundingMode);
    }

    private DecimalFormat createDecimalFormat(String format, Locale locale, RoundingMode roundingMode) {
        NumberFormat nf = NumberFormat.getInstance(ObjectUtils.defaultIfNull(locale, Locale.getDefault(Locale.Category.FORMAT)));
        if (!(nf instanceof DecimalFormat)) {
            throw new CsvBadConverterException(ConverterNumber.class, ResourceBundle.getBundle("opencsv", this.errorLocale).getString("numberformat.not.decimalformat"));
        }
        DecimalFormat formatter = (DecimalFormat)nf;
        try {
            formatter.applyLocalizedPattern(format);
        }
        catch (IllegalArgumentException e) {
            CsvBadConverterException csve = new CsvBadConverterException(ConverterNumber.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.number.pattern"), format));
            csve.initCause(e);
            throw csve;
        }
        formatter.setRoundingMode(roundingMode);
        return formatter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        Number n = null;
        if (StringUtils.isNotEmpty(value)) {
            try {
                DecimalFormat decimalFormat = this.readFormatter;
                synchronized (decimalFormat) {
                    n = this.readFormatter.parse(value);
                }
            }
            catch (ArithmeticException | ParseException e) {
                CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("unparsable.number"), value, this.readFormatter.toPattern()));
                csve.initCause(e);
                throw csve;
            }
            n = (Number)this.readConversionFunction.apply(n);
        }
        return n;
    }

    @Override
    public String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        DecimalFormat decimalFormat = this.writeFormatter;
        synchronized (decimalFormat) {
            try {
                return value != null ? this.writeFormatter.format(value) : null;
            }
            catch (ArithmeticException e) {
                CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(value, this.type, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("unparsable.number"), value, this.writeFormatter.toPattern()));
                csve.initCause(e);
                throw csve;
            }
        }
    }
}

