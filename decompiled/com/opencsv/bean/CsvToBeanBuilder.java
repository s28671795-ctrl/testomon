/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ObjectUtils;

public class CsvToBeanBuilder<T> {
    private MappingStrategy<? extends T> mappingStrategy = null;
    private final Reader reader;
    private final CSVReader csvReader;
    private CsvToBeanFilter filter = null;
    private CsvExceptionHandler exceptionHandler = null;
    private CSVReaderNullFieldIndicator nullFieldIndicator = null;
    private boolean keepCR;
    private Integer skipLines = null;
    private Boolean verifyReader = null;
    private Character separator = null;
    private Character quoteChar = null;
    private Character escapeChar = null;
    private Boolean strictQuotes = null;
    private Boolean ignoreLeadingWhiteSpace = null;
    private Boolean ignoreQuotations = null;
    private Boolean throwsExceptions = true;
    private Class<? extends T> type = null;
    private Integer multilineLimit = null;
    private boolean orderedResults = true;
    private boolean ignoreEmptyLines = false;
    private Locale errorLocale = Locale.getDefault();
    private final List<BeanVerifier<T>> verifiers = new LinkedList<BeanVerifier<T>>();
    private final ListValuedMap<Class<?>, Field> ignoredFields = new ArrayListValuedHashMap();
    private String profile = "";

    public CsvToBeanBuilder(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv").getString("reader.null"));
        }
        this.reader = reader;
        this.csvReader = null;
    }

    public CsvToBeanBuilder(CSVReader csvReader) {
        if (csvReader == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv").getString("reader.null"));
        }
        this.reader = null;
        this.csvReader = csvReader;
    }

    public CsvToBean<T> build() throws IllegalStateException {
        if (this.mappingStrategy == null && this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("strategy.type.missing"));
        }
        CsvToBean<Object> bean = new CsvToBean<Object>();
        if (this.csvReader != null) {
            bean.setCsvReader(this.csvReader);
        } else {
            CSVParser parser = this.buildParser();
            bean.setCsvReader(this.buildReader(parser));
        }
        if (this.exceptionHandler != null) {
            bean.setExceptionHandler(this.exceptionHandler);
        } else {
            bean.setThrowExceptions(this.throwsExceptions);
        }
        bean.setOrderedResults(this.orderedResults);
        if (this.filter != null) {
            bean.setFilter(this.filter);
        }
        bean.setVerifiers(this.verifiers);
        if (this.mappingStrategy == null) {
            this.mappingStrategy = OpencsvUtils.determineMappingStrategy(this.type, this.errorLocale, this.profile);
        }
        if (!this.ignoredFields.isEmpty()) {
            this.mappingStrategy.ignoreFields(this.ignoredFields);
        }
        bean.setMappingStrategy(this.mappingStrategy);
        bean.setErrorLocale(this.errorLocale);
        bean.setIgnoreEmptyLines(this.ignoreEmptyLines);
        return bean;
    }

    private CSVParser buildParser() {
        CSVParserBuilder csvpb = new CSVParserBuilder();
        if (this.nullFieldIndicator != null) {
            csvpb.withFieldAsNull(this.nullFieldIndicator);
        }
        if (this.separator != null) {
            csvpb.withSeparator(this.separator.charValue());
        }
        if (this.quoteChar != null) {
            csvpb.withQuoteChar(this.quoteChar.charValue());
        }
        if (this.escapeChar != null) {
            csvpb.withEscapeChar(this.escapeChar.charValue());
        }
        if (this.strictQuotes != null) {
            csvpb.withStrictQuotes(this.strictQuotes);
        }
        if (this.ignoreLeadingWhiteSpace != null) {
            csvpb.withIgnoreLeadingWhiteSpace(this.ignoreLeadingWhiteSpace);
        }
        if (this.ignoreQuotations != null) {
            csvpb.withIgnoreQuotations(this.ignoreQuotations);
        }
        csvpb.withErrorLocale(this.errorLocale);
        return csvpb.build();
    }

    private CSVReader buildReader(CSVParser parser) {
        CSVReaderBuilder csvrb = new CSVReaderBuilder(this.reader);
        csvrb.withCSVParser(parser);
        csvrb.withKeepCarriageReturn(this.keepCR);
        if (this.verifyReader != null) {
            csvrb.withVerifyReader(this.verifyReader);
        }
        if (this.skipLines != null) {
            csvrb.withSkipLines(this.skipLines);
        }
        if (this.multilineLimit != null) {
            csvrb.withMultilineLimit(this.multilineLimit);
        }
        csvrb.withErrorLocale(this.errorLocale);
        return csvrb.build();
    }

    public CsvToBeanBuilder<T> withMappingStrategy(MappingStrategy<? extends T> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
        return this;
    }

    public CsvToBeanBuilder<T> withFilter(CsvToBeanFilter filter2) {
        this.filter = filter2;
        return this;
    }

    public CsvToBeanBuilder<T> withThrowExceptions(boolean throwExceptions) {
        this.throwsExceptions = throwExceptions;
        return this;
    }

    public CsvToBeanBuilder<T> withExceptionHandler(CsvExceptionHandler exceptionHandler) {
        if (exceptionHandler != null) {
            this.exceptionHandler = exceptionHandler;
        }
        return this;
    }

    public CsvToBeanBuilder<T> withFieldAsNull(CSVReaderNullFieldIndicator indicator) {
        this.nullFieldIndicator = indicator;
        return this;
    }

    public CsvToBeanBuilder<T> withKeepCarriageReturn(boolean keepCR) {
        this.keepCR = keepCR;
        return this;
    }

    public CsvToBeanBuilder<T> withVerifyReader(boolean verifyReader) {
        this.verifyReader = verifyReader;
        return this;
    }

    public CsvToBeanBuilder<T> withSkipLines(int skipLines) {
        this.skipLines = skipLines;
        return this;
    }

    public CsvToBeanBuilder<T> withSeparator(char separator) {
        this.separator = Character.valueOf(separator);
        return this;
    }

    public CsvToBeanBuilder<T> withQuoteChar(char quoteChar) {
        this.quoteChar = Character.valueOf(quoteChar);
        return this;
    }

    public CsvToBeanBuilder<T> withEscapeChar(char escapeChar) {
        this.escapeChar = Character.valueOf(escapeChar);
        return this;
    }

    public CsvToBeanBuilder<T> withStrictQuotes(boolean strictQuotes) {
        this.strictQuotes = strictQuotes;
        return this;
    }

    public CsvToBeanBuilder<T> withIgnoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        return this;
    }

    public CsvToBeanBuilder<T> withIgnoreQuotations(boolean ignoreQuotations) {
        this.ignoreQuotations = ignoreQuotations;
        return this;
    }

    public CsvToBeanBuilder<T> withType(Class<? extends T> type) {
        this.type = type;
        return this;
    }

    public CsvToBeanBuilder<T> withMultilineLimit(int multilineLimit) {
        this.multilineLimit = multilineLimit;
        return this;
    }

    public CsvToBeanBuilder<T> withOrderedResults(boolean orderedResults) {
        this.orderedResults = orderedResults;
        return this;
    }

    public CsvToBeanBuilder<T> withErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        return this;
    }

    public CsvToBeanBuilder<T> withVerifier(BeanVerifier<T> verifier) {
        if (verifier != null) {
            this.verifiers.add(verifier);
        }
        return this;
    }

    public CsvToBeanBuilder<T> withIgnoreField(Class<?> type, Field field2) throws IllegalArgumentException {
        if (type == null || field2 == null || !field2.getDeclaringClass().isAssignableFrom(type)) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("ignore.field.inconsistent"));
        }
        this.ignoredFields.put(type, field2);
        return this;
    }

    public CsvToBeanBuilder<T> withIgnoreEmptyLine(boolean ignore) {
        this.ignoreEmptyLines = ignore;
        return this;
    }

    public CsvToBeanBuilder<T> withProfile(String profile) {
        this.profile = profile;
        return this;
    }
}

