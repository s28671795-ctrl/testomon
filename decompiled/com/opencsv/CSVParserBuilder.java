/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVParser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import java.util.Locale;
import org.apache.commons.lang3.ObjectUtils;

public class CSVParserBuilder {
    private char separator = (char)44;
    private char quoteChar = (char)34;
    private char escapeChar = (char)92;
    private boolean strictQuotes = false;
    private boolean ignoreLeadingWhiteSpace = true;
    private boolean ignoreQuotations = false;
    private CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;
    private Locale errorLocale = Locale.getDefault();

    public CSVParserBuilder withSeparator(char separator) {
        this.separator = separator;
        return this;
    }

    public CSVParserBuilder withQuoteChar(char quoteChar) {
        this.quoteChar = quoteChar;
        return this;
    }

    public CSVParserBuilder withEscapeChar(char escapeChar) {
        this.escapeChar = escapeChar;
        return this;
    }

    public CSVParserBuilder withStrictQuotes(boolean strictQuotes) {
        this.strictQuotes = strictQuotes;
        return this;
    }

    public CSVParserBuilder withIgnoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        return this;
    }

    public CSVParserBuilder withIgnoreQuotations(boolean ignoreQuotations) {
        this.ignoreQuotations = ignoreQuotations;
        return this;
    }

    public CSVParser build() {
        return new CSVParser(this.separator, this.quoteChar, this.escapeChar, this.strictQuotes, this.ignoreLeadingWhiteSpace, this.ignoreQuotations, this.nullFieldIndicator, this.errorLocale);
    }

    public char getSeparator() {
        return this.separator;
    }

    public char getQuoteChar() {
        return this.quoteChar;
    }

    public char getEscapeChar() {
        return this.escapeChar;
    }

    public boolean isStrictQuotes() {
        return this.strictQuotes;
    }

    public boolean isIgnoreLeadingWhiteSpace() {
        return this.ignoreLeadingWhiteSpace;
    }

    public boolean isIgnoreQuotations() {
        return this.ignoreQuotations;
    }

    public CSVParserBuilder withFieldAsNull(CSVReaderNullFieldIndicator fieldIndicator) {
        this.nullFieldIndicator = fieldIndicator;
        return this;
    }

    public CSVParserBuilder withErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        return this;
    }

    public CSVReaderNullFieldIndicator nullFieldIndicator() {
        return this.nullFieldIndicator;
    }
}

