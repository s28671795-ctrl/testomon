/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.exceptionhandler;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;

public final class ExceptionHandlerIgnore
implements CsvExceptionHandler {
    @Override
    public CsvException handleException(CsvException e) {
        return null;
    }
}

