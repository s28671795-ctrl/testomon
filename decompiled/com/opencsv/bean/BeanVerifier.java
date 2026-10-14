/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.exceptions.CsvConstraintViolationException;

public interface BeanVerifier<T> {
    public boolean verifyBean(T var1) throws CsvConstraintViolationException;
}

