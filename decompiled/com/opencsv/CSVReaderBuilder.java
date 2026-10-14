/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBaseBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidator;
import com.opencsv.validators.RowValidator;
import java.io.Reader;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ObjectUtils;

public class CSVReaderBuilder
extends CSVReaderBaseBuilder<CSVReader> {
    public CSVReaderBuilder(Reader reader) {
        super(reader);
        if (reader == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv").getString("reader.null"));
        }
    }

    public CSVReaderBuilder withSkipLines(int skipLines) {
        this.skipLines = Math.max(skipLines, 0);
        return this;
    }

    public CSVReaderBuilder withCSVParser(ICSVParser icsvParser) {
        this.icsvParser = icsvParser;
        return this;
    }

    @Override
    public CSVReader build() {
        ICSVParser parser = this.getOrCreateCsvParser();
        return new CSVReader(this.reader, this.skipLines, parser, this.keepCR, this.verifyReader, this.multilineLimit, this.errorLocale, this.lineValidatorAggregator, this.rowValidatorAggregator, this.rowProcessor);
    }

    public CSVReaderBuilder withKeepCarriageReturn(boolean keepCR) {
        this.keepCR = keepCR;
        return this;
    }

    public CSVReaderBuilder withVerifyReader(boolean verifyReader) {
        this.verifyReader = verifyReader;
        return this;
    }

    public CSVReaderBuilder withFieldAsNull(CSVReaderNullFieldIndicator indicator) {
        this.nullFieldIndicator = indicator;
        return this;
    }

    public CSVReaderBuilder withMultilineLimit(int multilineLimit) {
        this.multilineLimit = multilineLimit;
        return this;
    }

    public CSVReaderBuilder withErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        return this;
    }

    public CSVReaderBuilder withLineValidator(LineValidator lineValidator) {
        this.lineValidatorAggregator.addValidator(lineValidator);
        return this;
    }

    public CSVReaderBuilder withRowValidator(RowValidator rowValidator) {
        this.rowValidatorAggregator.addValidator(rowValidator);
        return this;
    }

    public CSVReaderBuilder withRowProcessor(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
        return this;
    }
}

