/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.concurrent;

import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.concurrent.IntolerantThreadPoolExecutor;
import com.opencsv.bean.concurrent.ProcessCsvBean;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import java.util.Locale;

public class BeanExecutor<T>
extends IntolerantThreadPoolExecutor<String[]> {
    public BeanExecutor(boolean orderedResults, Locale errorLocale) {
        super(orderedResults, errorLocale);
    }

    public void submitBean(long lineNumber, MappingStrategy<T> mappingStrategy, T bean, CsvExceptionHandler exceptionHandler) {
        if (this.accumulateThread != null) {
            this.expectedRecords.add(lineNumber);
        }
        try {
            this.execute(new ProcessCsvBean<T>(lineNumber, mappingStrategy, bean, this.resultQueue, this.thrownExceptionsQueue, this.expectedRecords, exceptionHandler));
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

