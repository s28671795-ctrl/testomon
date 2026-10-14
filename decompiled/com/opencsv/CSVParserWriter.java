/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.AbstractCSVWriter;
import com.opencsv.ICSVParser;
import java.io.IOException;
import java.io.Writer;

public class CSVParserWriter
extends AbstractCSVWriter {
    protected final ICSVParser parser;

    public CSVParserWriter(Writer writer, ICSVParser parser, String lineEnd) {
        super(writer, lineEnd);
        this.parser = parser;
    }

    @Override
    protected void writeNext(String[] nextLine, boolean applyQuotesToAll, Appendable appendable) throws IOException {
        this.parser.parseToLine(nextLine, applyQuotesToAll, appendable);
        appendable.append(this.lineEnd);
        this.writer.write(appendable.toString());
    }
}

