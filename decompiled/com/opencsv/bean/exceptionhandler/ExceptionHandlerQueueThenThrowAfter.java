/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.exceptionhandler;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;
import java.util.concurrent.atomic.AtomicInteger;

public final class ExceptionHandlerQueueThenThrowAfter
implements CsvExceptionHandler {
    private final AtomicInteger count = new AtomicInteger();
    private final int maxExceptions;

    public ExceptionHandlerQueueThenThrowAfter(int maxExceptions) {
        this.maxExceptions = maxExceptions;
    }

    @Override
    public CsvException handleException(CsvException e) throws CsvException {
        if (this.count.incrementAndGet() > this.maxExceptions) {
            throw e;
        }
        return e;
    }
}

