/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.exceptions;

import com.opencsv.exceptions.CsvRuntimeException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

public class CsvBeanIntrospectionException
extends CsvRuntimeException {
    private static final long serialVersionUID = 1L;
    private final transient Object bean;
    private final transient Field field;

    public CsvBeanIntrospectionException() {
        this.bean = null;
        this.field = null;
    }

    public CsvBeanIntrospectionException(String message) {
        super(message);
        this.bean = null;
        this.field = null;
    }

    public CsvBeanIntrospectionException(Object bean, Field field2) {
        this.bean = bean;
        this.field = field2;
    }

    public CsvBeanIntrospectionException(Object bean, Field field2, String message) {
        super(message);
        this.bean = bean;
        this.field = field2;
    }

    @Override
    public String getMessage() {
        return this.getMessageFromLocale(Locale.US);
    }

    @Override
    public String getLocalizedMessage() {
        return this.getMessageFromLocale(Locale.getDefault());
    }

    private String getMessageFromLocale(Locale locale) {
        String supermessage = super.getMessage();
        if (supermessage == null && this.getBean() != null && this.getField() != null) {
            return String.format(ResourceBundle.getBundle("opencsv", locale).getString("error.introspecting.field"), this.getField().getName(), this.getBean().getClass().getCanonicalName());
        }
        return supermessage;
    }

    public Object getBean() {
        return this.bean;
    }

    public Field getField() {
        return this.field;
    }
}

