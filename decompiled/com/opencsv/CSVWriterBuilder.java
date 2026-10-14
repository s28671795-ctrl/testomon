/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVParserWriter;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVParser;
import com.opencsv.ICSVWriter;
import com.opencsv.ResultSetHelper;
import java.io.Writer;

public class CSVWriterBuilder {
    private final Writer writer;
    private ICSVParser parser;
    private Character separator;
    private Character quotechar;
    private Character escapechar;
    private ResultSetHelper resultSetHelper;
    private String lineEnd = "\n";

    public CSVWriterBuilder(Writer writer) {
        this.writer = writer;
    }

    public CSVWriterBuilder withParser(ICSVParser parser) {
        if (this.separator != null || this.quotechar != null || this.escapechar != null) {
            throw new IllegalArgumentException("You cannot set the parser in the builder if you have set the separator, quote, or escape character");
        }
        this.parser = parser;
        return this;
    }

    public CSVWriterBuilder withSeparator(char separator) {
        if (this.parser != null) {
            throw new IllegalArgumentException("You cannot set the separator in the builder if you have a ICSVParser set.  Set the separator in the parser instead.");
        }
        this.separator = Character.valueOf(separator);
        return this;
    }

    public CSVWriterBuilder withQuoteChar(char quoteChar) {
        if (this.parser != null) {
            throw new IllegalArgumentException("You cannot set the quote character in the builder if you have a ICSVParser set.  Set the quote character in the parser instead.");
        }
        this.quotechar = Character.valueOf(quoteChar);
        return this;
    }

    public CSVWriterBuilder withEscapeChar(char escapeChar) {
        if (this.parser != null) {
            throw new IllegalArgumentException("You cannot set the escape character in the builder if you have a ICSVParser set.  Set the escape character in the parser instead.");
        }
        this.escapechar = Character.valueOf(escapeChar);
        return this;
    }

    public CSVWriterBuilder withLineEnd(String lineEnd) {
        this.lineEnd = lineEnd;
        return this;
    }

    public ICSVWriter build() {
        if (this.parser != null) {
            return this.createCSVParserWriter();
        }
        return this.createCSVWriter();
    }

    private ICSVWriter createCSVParserWriter() {
        return new CSVParserWriter(this.writer, this.parser, this.lineEnd);
    }

    private ICSVWriter createCSVWriter() {
        if (this.separator == null) {
            this.separator = Character.valueOf(',');
        }
        if (this.quotechar == null) {
            this.quotechar = Character.valueOf('\"');
        }
        if (this.escapechar == null) {
            this.escapechar = Character.valueOf('\"');
        }
        CSVWriter icsvWriter = new CSVWriter(this.writer, this.separator.charValue(), this.quotechar.charValue(), this.escapechar.charValue(), this.lineEnd);
        if (this.resultSetHelper != null) {
            icsvWriter.setResultService(this.resultSetHelper);
        }
        return icsvWriter;
    }

    public CSVWriterBuilder withResultSetHelper(ResultSetHelper helper) {
        this.resultSetHelper = helper;
        return this;
    }
}

