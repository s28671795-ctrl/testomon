/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.Locale;

public interface CsvConverter {
    public Object convertToRead(String var1) throws CsvDataTypeMismatchException, CsvConstraintViolationException;

    public String convertToWrite(Object var1) throws CsvDataTypeMismatchException;

    public void setErrorLocale(Locale var1);

    public void setType(Class<?> var1);

    public void setLocale(String var1);

    public void setWriteLocale(String var1);
}

