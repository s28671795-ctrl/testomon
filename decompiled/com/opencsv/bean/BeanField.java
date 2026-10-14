/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.lang.reflect.Field;
import java.util.Locale;

public interface BeanField<T, I> {
    public Class<?> getType();

    public void setType(Class<?> var1);

    public void setField(Field var1);

    public Field getField();

    public boolean isRequired();

    public void setRequired(boolean var1);

    public void setFieldValue(Object var1, String var2, String var3) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvValidationException;

    public Object getFieldValue(Object var1);

    public Object[] indexAndSplitMultivaluedField(Object var1, I var2) throws CsvDataTypeMismatchException;

    public String[] write(Object var1, I var2) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

    public void setErrorLocale(Locale var1);

    public Locale getErrorLocale();
}

