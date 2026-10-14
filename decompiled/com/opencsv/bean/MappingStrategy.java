/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import org.apache.commons.collections4.MultiValuedMap;

public interface MappingStrategy<T> {
    public void captureHeader(CSVReader var1) throws IOException, CsvRequiredFieldEmptyException;

    public String[] generateHeader(T var1) throws CsvRequiredFieldEmptyException;

    @Deprecated
    default public boolean isAnnotationDriven() {
        return false;
    }

    public T populateNewBean(String[] var1) throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException;

    default public void setErrorLocale(Locale errorLocale) {
    }

    public void setType(Class<? extends T> var1) throws CsvBadConverterException;

    default public void setProfile(String profile) {
        throw new UnsupportedOperationException();
    }

    default public void ignoreFields(MultiValuedMap<Class<?>, Field> fields) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public String[] transmuteBean(T var1) throws CsvFieldAssignmentException, CsvChainedException;
}

