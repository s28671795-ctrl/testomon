/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerQueue;
import com.opencsv.bean.exceptionhandler.ExceptionHandlerThrow;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ObjectUtils;

public class StatefulBeanToCsvBuilder<T> {
    private char separator = (char)44;
    private char quotechar = (char)34;
    private char escapechar = (char)34;
    private String lineEnd = "\n";
    private MappingStrategy<T> mappingStrategy = null;
    private final Writer writer;
    private final ICSVWriter csvWriter;
    private CsvExceptionHandler exceptionHandler = new ExceptionHandlerThrow();
    private boolean orderedResults = true;
    private Locale errorLocale = Locale.getDefault();
    private boolean applyQuotesToAll = true;
    private final ListValuedMap<Class<?>, Field> ignoredFields = new ArrayListValuedHashMap();
    private String profile = "";

    public StatefulBeanToCsvBuilder(Writer writer) {
        this.writer = writer;
        this.csvWriter = null;
    }

    public StatefulBeanToCsvBuilder(ICSVWriter icsvWriter) {
        this.writer = null;
        this.csvWriter = icsvWriter;
    }

    public StatefulBeanToCsvBuilder<T> withMappingStrategy(MappingStrategy<T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withSeparator(char separator) {
        this.separator = separator;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withQuotechar(char quotechar) {
        this.quotechar = quotechar;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withEscapechar(char escapechar) {
        this.escapechar = escapechar;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withLineEnd(String lineEnd) {
        this.lineEnd = lineEnd;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withThrowExceptions(boolean throwExceptions) {
        this.exceptionHandler = throwExceptions ? new ExceptionHandlerThrow() : new ExceptionHandlerQueue();
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withExceptionHandler(CsvExceptionHandler exceptionHandler) {
        if (exceptionHandler != null) {
            this.exceptionHandler = exceptionHandler;
        }
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withOrderedResults(boolean orderedResults) {
        this.orderedResults = orderedResults;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withApplyQuotesToAll(boolean applyQuotesToAll) {
        this.applyQuotesToAll = applyQuotesToAll;
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withIgnoreField(Class<?> type, Field field2) throws IllegalArgumentException {
        if (type == null || field2 == null || !field2.getDeclaringClass().isAssignableFrom(type)) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("ignore.field.inconsistent"));
        }
        this.ignoredFields.put(type, field2);
        return this;
    }

    public StatefulBeanToCsvBuilder<T> withProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public StatefulBeanToCsv<T> build() {
        StatefulBeanToCsv<T> sbtcsv = this.writer != null ? new StatefulBeanToCsv<T>(this.escapechar, this.lineEnd, this.mappingStrategy, this.quotechar, this.separator, this.exceptionHandler, this.writer, this.applyQuotesToAll, this.ignoredFields, this.profile) : new StatefulBeanToCsv<T>(this.mappingStrategy, this.exceptionHandler, this.applyQuotesToAll, this.csvWriter, this.ignoredFields, this.profile);
        sbtcsv.setOrderedResults(this.orderedResults);
        sbtcsv.setErrorLocale(this.errorLocale);
        return sbtcsv;
    }
}

