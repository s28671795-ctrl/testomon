/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVReaderBaseBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.ICSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidator;
import com.opencsv.validators.RowValidator;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ObjectUtils;

public class CSVReaderHeaderAwareBuilder
extends CSVReaderBaseBuilder<CSVReaderHeaderAware> {
    public CSVReaderHeaderAwareBuilder(Reader reader) {
        super(reader);
    }

    public CSVReaderHeaderAwareBuilder withSkipLines(int skipLines) {
        this.skipLines = Math.max(skipLines, 0);
        return this;
    }

    public CSVReaderHeaderAwareBuilder withCSVParser(ICSVParser icsvParser) {
        this.icsvParser = icsvParser;
        return this;
    }

    public CSVReaderHeaderAwareBuilder withKeepCarriageReturn(boolean keepCR) {
        this.keepCR = keepCR;
        return this;
    }

    public CSVReaderHeaderAwareBuilder withVerifyReader(boolean verifyReader) {
        this.verifyReader = verifyReader;
        return this;
    }

    public CSVReaderHeaderAwareBuilder withFieldAsNull(CSVReaderNullFieldIndicator indicator) {
        this.nullFieldIndicator = indicator;
        return this;
    }

    public CSVReaderHeaderAwareBuilder withMultilineLimit(int multilineLimit) {
        this.multilineLimit = multilineLimit;
        return this;
    }

    public CSVReaderHeaderAwareBuilder withErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        return this;
    }

    public CSVReaderHeaderAwareBuilder withLineValidator(LineValidator lineValidator) {
        this.lineValidatorAggregator.addValidator(lineValidator);
        return this;
    }

    public CSVReaderHeaderAwareBuilder withRowValidator(RowValidator rowValidator) {
        this.rowValidatorAggregator.addValidator(rowValidator);
        return this;
    }

    public CSVReaderHeaderAwareBuilder withRowProcessor(RowProcessor rowProcessor) {
        this.rowProcessor = rowProcessor;
        return this;
    }

    @Override
    public CSVReaderHeaderAware build() throws RuntimeException {
        ICSVParser parser = this.getOrCreateCsvParser();
        try {
            return new CSVReaderHeaderAware(this.reader, this.skipLines, parser, this.keepCR, this.verifyReader, this.multilineLimit, this.errorLocale, this.lineValidatorAggregator, this.rowValidatorAggregator, this.rowProcessor);
        }
        catch (IOException e) {
            throw new RuntimeException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("csvreaderheaderaware.impossible"), e);
        }
    }
}

