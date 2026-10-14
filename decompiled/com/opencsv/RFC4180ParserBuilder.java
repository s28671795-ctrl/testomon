/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.RFC4180Parser;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

public class RFC4180ParserBuilder {
    private char separator = (char)44;
    private char quoteChar = (char)34;
    private CSVReaderNullFieldIndicator nullFieldIndicator = CSVReaderNullFieldIndicator.NEITHER;

    public char getSeparator() {
        return this.separator;
    }

    public char getQuoteChar() {
        return this.quoteChar;
    }

    public CSVReaderNullFieldIndicator nullFieldIndicator() {
        return this.nullFieldIndicator;
    }

    public RFC4180Parser build() {
        return new RFC4180Parser(this.quoteChar, this.separator, this.nullFieldIndicator);
    }

    public RFC4180ParserBuilder withSeparator(char separator) {
        this.separator = separator;
        return this;
    }

    public RFC4180ParserBuilder withQuoteChar(char quoteChar) {
        this.quoteChar = quoteChar;
        return this;
    }

    public RFC4180ParserBuilder withFieldAsNull(CSVReaderNullFieldIndicator fieldIndicator) {
        this.nullFieldIndicator = fieldIndicator;
        return this;
    }
}

