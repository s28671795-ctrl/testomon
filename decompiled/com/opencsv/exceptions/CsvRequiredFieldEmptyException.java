/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvFieldAssignmentException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.list.UnmodifiableList;

public class CsvRequiredFieldEmptyException
extends CsvFieldAssignmentException {
    private static final long serialVersionUID = 1L;
    private final Class<?> beanClass;
    private final transient List<Field> destinationFields;

    public CsvRequiredFieldEmptyException() {
        this.beanClass = null;
        this.destinationFields = Collections.emptyList();
    }

    public CsvRequiredFieldEmptyException(String message) {
        super(message);
        this.beanClass = null;
        this.destinationFields = Collections.emptyList();
    }

    public CsvRequiredFieldEmptyException(Class<?> beanClass, Field destinationField) {
        this.beanClass = beanClass;
        this.destinationFields = Collections.singletonList(destinationField);
    }

    public CsvRequiredFieldEmptyException(Class<?> beanClass, List<Field> destinationFields) {
        this.beanClass = beanClass;
        this.destinationFields = new UnmodifiableList<Field>(destinationFields);
    }

    public CsvRequiredFieldEmptyException(Class<?> beanClass, String message) {
        super(message);
        this.beanClass = beanClass;
        this.destinationFields = Collections.emptyList();
    }

    public CsvRequiredFieldEmptyException(Class<?> beanClass, Field destinationField, String message) {
        super(message);
        this.beanClass = beanClass;
        this.destinationFields = Collections.singletonList(destinationField);
    }

    public CsvRequiredFieldEmptyException(Class<?> beanClass, List<Field> destinationFields, String message) {
        super(message);
        this.beanClass = beanClass;
        this.destinationFields = new UnmodifiableList<Field>(destinationFields);
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public Field getDestinationField() {
        return CollectionUtils.isEmpty(this.destinationFields) ? null : this.destinationFields.get(0);
    }

    public List<Field> getDestinationFields() {
        return this.destinationFields;
    }
}

