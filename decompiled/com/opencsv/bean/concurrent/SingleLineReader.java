/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class SingleLineReader {
    protected final CSVReader csvReader;
    protected final boolean ignoreEmptyLines;
    protected String[] line;

    public SingleLineReader(CSVReader csvReader, boolean ignoreEmptyLines) {
        this.csvReader = csvReader;
        this.ignoreEmptyLines = ignoreEmptyLines;
    }

    private boolean isCurrentLineEmpty() {
        return this.line.length == 0 || this.line.length == 1 && StringUtils.isEmpty(this.line[0]);
    }

    public String[] readNextLine() throws IOException, CsvValidationException {
        do {
            this.line = this.csvReader.readNext();
        } while (this.line != null && this.isCurrentLineEmpty() && this.ignoreEmptyLines);
        return this.getLine();
    }

    public long getLinesRead() {
        return this.csvReader.getLinesRead();
    }

    public String[] getLine() {
        String[] lineCopy = this.line;
        if (this.line != null) {
            lineCopy = new String[this.line.length];
            System.arraycopy(this.line, 0, lineCopy, 0, this.line.length);
        }
        return lineCopy;
    }
}

