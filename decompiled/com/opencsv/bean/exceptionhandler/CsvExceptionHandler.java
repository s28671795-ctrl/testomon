/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.exceptionhandler;

import com.opencsv.exceptions.CsvException;

@FunctionalInterface
public interface CsvExceptionHandler {
    public CsvException handleException(CsvException var1) throws CsvException;
}

