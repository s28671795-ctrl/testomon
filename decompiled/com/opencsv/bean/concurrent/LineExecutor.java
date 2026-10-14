/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.concurrent.CompleteFileReader;
import com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor;
import com.opencsv.bean.concurrent.ProcessCsvLine;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvMalformedLineException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.RejectedExecutionException;

public class LineExecutor<T>
extends IntolerantThreadPoolExecutor<T> {
    private final CompleteFileReader<T> completeFileReader;

    public LineExecutor(boolean orderedResults, Locale errorLocale, CompleteFileReader<T> completeFileReader) {
        super(orderedResults, errorLocale);
        this.completeFileReader = completeFileReader;
    }

    @Override
    public void prepare() {
        Thread readerThread = new Thread(this.completeFileReader);
        this.completeFileReader.setExecutor(this);
        super.prepare();
        readerThread.start();
    }

    @Override
    protected void checkExceptions() {
        Throwable t2 = this.completeFileReader.getTerminalException();
        if (t2 != null && !(t2 instanceof RejectedExecutionException)) {
            this.shutdownNow();
            if (t2 instanceof CsvMalformedLineException) {
                CsvMalformedLineException cmle = (CsvMalformedLineException)t2;
                throw new RuntimeException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("parsing.error.full"), cmle.getLineNumber(), cmle.getContext()), cmle);
            }
            throw new RuntimeException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("parsing.error.full"), this.completeFileReader.getLineProcessed(), Arrays.toString(this.completeFileReader.getLine())), t2);
        }
        super.checkExceptions();
    }

    public void submitLine(long lineNumber, MappingStrategy<? extends T> mapper2, CsvToBeanFilter filter2, List<BeanVerifier<T>> verifiers, String[] line, CsvExceptionHandler exceptionHandler) {
        if (this.accumulateThread != null) {
            this.expectedRecords.add(lineNumber);
        }
        try {
            this.execute(new ProcessCsvLine<T>(lineNumber, mapper2, filter2, verifiers, line, this.resultQueue, this.thrownExceptionsQueue, this.expectedRecords, exceptionHandler));
        }
        catch (Exception e) {
            if (this.accumulateThread != null) {
                this.expectedRecords.remove(lineNumber);
                this.accumulateThread.setMustStop(true);
            }
            throw e;
        }
    }
}

