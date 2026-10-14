/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.CSVReader;
import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.concurrent.LineExecutor;
import com.opencsv.bean.concurrent.SingleLineReader;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;

public class CompleteFileReader<T>
extends SingleLineReader
implements Runnable {
    private final CsvToBeanFilter filter;
    private final MappingStrategy<? extends T> mappingStrategy;
    private final CsvExceptionHandler exceptionHandler;
    private final List<BeanVerifier<T>> verifiers;
    private long lineProcessed;
    private Throwable terminalException;
    private LineExecutor<T> executor;

    public CompleteFileReader(CSVReader csvReader, CsvToBeanFilter filter2, boolean ignoreEmptyLines, MappingStrategy<? extends T> mappingStrategy, CsvExceptionHandler exceptionHandler, List<BeanVerifier<T>> verifiers) {
        super(csvReader, ignoreEmptyLines);
        this.filter = filter2;
        this.mappingStrategy = mappingStrategy;
        this.exceptionHandler = exceptionHandler;
        this.verifiers = ObjectUtils.defaultIfNull(verifiers, Collections.emptyList());
    }

    public Throwable getTerminalException() {
        return this.terminalException;
    }

    public long getLineProcessed() {
        return this.lineProcessed;
    }

    public void setExecutor(LineExecutor<T> executor2) {
        if (this.executor == null) {
            this.executor = executor2;
        }
    }

    @Override
    public void run() {
        try {
            while (null != this.readNextLine()) {
                this.lineProcessed = this.csvReader.getLinesRead();
                this.executor.submitLine(this.lineProcessed, this.mappingStrategy, this.filter, this.verifiers, this.line, this.exceptionHandler);
            }
            this.executor.complete();
        }
        catch (Exception e) {
            this.terminalException = e;
        }
    }
}

