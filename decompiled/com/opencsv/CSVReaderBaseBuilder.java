/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVParserBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.Reader;
import java.util.Locale;
import org.apache.commons.lang3.ObjectUtils;

public abstract class CSVReaderBaseBuilder<T> {
    protected final Reader reader;
    protected final LineValidatorAggregator lineValidatorAggregator = new LineValidatorAggregator();
    protected final RowValidatorAggregator rowValidatorAggregator = new RowValidatorAggregator();
    private final CSVParserBuilder parserBuilder = new CSVParserBuilder();
    protected int skipLines = 0;
    protected ICSVParser icsvParser = null;
    protected boolean keepCR;
    protected boolean verifyReader = true;
    protected CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;
    protected int multilineLimit = 0;
    protected Locale errorLocale = Locale.getDefault();
    protected RowProcessor rowProcessor = null;

    protected CSVReaderBaseBuilder(Reader reader) {
        this.reader = reader;
    }

    protected Reader getReader() {
        return this.reader;
    }

    protected int getSkipLines() {
        return this.skipLines;
    }

    protected ICSVParser getCsvParser() {
        return this.icsvParser;
    }

    protected int getMultilineLimit() {
        return this.multilineLimit;
    }

    protected boolean keepCarriageReturn() {
        return this.keepCR;
    }

    protected ICSVParser getOrCreateCsvParser() {
        return ObjectUtils.defaultIfNull(this.icsvParser, this.parserBuilder.withFieldAsNull(this.nullFieldIndicator).withErrorLocale(this.errorLocale).build());
    }

    public boolean isVerifyReader() {
        return this.verifyReader;
    }

    public Locale getErrorLocale() {
        return this.errorLocale;
    }

    public LineValidatorAggregator getLineValidatorAggregator() {
        return this.lineValidatorAggregator;
    }

    public RowValidatorAggregator getRowValidatorAggregator() {
        return this.rowValidatorAggregator;
    }

    public abstract T build();
}

