/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.processor.RowProcessor;
import com.opencsv.validators.LineValidatorAggregator;
import com.opencsv.validators.RowValidatorAggregator;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class CSVReaderHeaderAware
extends CSVReader {
    private final Map<String, Integer> headerIndex = new HashMap<String, Integer>();

    public CSVReaderHeaderAware(Reader reader) throws IOException {
        super(reader);
        this.initializeHeader();
    }

    CSVReaderHeaderAware(Reader reader, int skipLines, ICSVParser parser, boolean keepCR, boolean verifyReader, int multilineLimit, Locale errorLocale, LineValidatorAggregator lineValidatorAggregator, RowValidatorAggregator rowValidatorAggregator, RowProcessor rowProcessor) throws IOException {
        super(reader, skipLines, parser, keepCR, verifyReader, multilineLimit, errorLocale, lineValidatorAggregator, rowValidatorAggregator, rowProcessor);
        this.initializeHeader();
    }

    public String[] readNext(String ... headerNames) throws IOException, CsvValidationException {
        if (headerNames == null) {
            return super.readNextSilently();
        }
        String[] strings = this.readNext();
        if (strings == null) {
            return null;
        }
        if (strings.length != this.headerIndex.size()) {
            throw new IOException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.data.mismatch.with.line.number"), this.getRecordsRead(), this.headerIndex.size(), strings.length));
        }
        String[] response = new String[headerNames.length];
        for (int i = 0; i < headerNames.length; ++i) {
            String headerName = headerNames[i];
            Integer index = this.headerIndex.get(headerName);
            if (index == null) {
                throw new IllegalArgumentException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.nonexistant"), headerName));
            }
            response[i] = strings[index];
        }
        return response;
    }

    public Map<String, String> readMap() throws IOException, CsvValidationException {
        String[] strings = this.readNext();
        if (strings == null) {
            return null;
        }
        if (strings.length != this.headerIndex.size()) {
            throw new IOException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.data.mismatch.with.line.number"), this.getRecordsRead(), this.headerIndex.size(), strings.length));
        }
        HashMap<String, String> resultMap = new HashMap<String, String>(this.headerIndex.size() * 2);
        for (Map.Entry<String, Integer> entry : this.headerIndex.entrySet()) {
            if (entry.getValue() >= strings.length) continue;
            resultMap.put(entry.getKey(), strings[entry.getValue()]);
        }
        return resultMap;
    }

    private void initializeHeader() throws IOException {
        String[] headers = super.readNextSilently();
        for (int i = 0; i < headers.length; ++i) {
            this.headerIndex.put(headers[i], i);
        }
    }
}

