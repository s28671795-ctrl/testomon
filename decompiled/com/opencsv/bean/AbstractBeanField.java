/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.FieldAccess;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import com.opencsv.bean.processor.StringProcessor;
import com.opencsv.bean.validators.PreAssignmentValidator;
import com.opencsv.bean.validators.StringValidator;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractBeanField<T, I>
implements BeanField<T, I> {
    protected Class<?> type;
    protected Field field;
    protected boolean required;
    protected Locale errorLocale;
    protected CsvConverter converter;
    protected FieldAccess<Object> fieldAccess;

    public AbstractBeanField() {
        this.required = false;
        this.errorLocale = Locale.getDefault();
    }

    public AbstractBeanField(Class<?> type, Field field2, boolean required, Locale errorLocale, CsvConverter converter) {
        this.type = type;
        this.field = field2;
        this.required = required;
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        this.converter = converter;
        this.fieldAccess = new FieldAccess(this.field);
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public void setField(Field field2) {
        this.field = field2;
        this.fieldAccess = new FieldAccess(this.field);
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        if (this.converter != null) {
            this.converter.setErrorLocale(this.errorLocale);
        }
    }

    @Override
    public Locale getErrorLocale() {
        return this.errorLocale;
    }

    @Override
    public final void setFieldValue(Object bean, String value, String header) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvValidationException {
        PreAssignmentValidator[] validators;
        if (this.required && StringUtils.isBlank(value)) {
            throw new CsvRequiredFieldEmptyException(bean.getClass(), this.field, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("required.field.empty"), this.field.getName()));
        }
        PreAssignmentProcessor[] processors = (PreAssignmentProcessor[])this.field.getAnnotationsByType(PreAssignmentProcessor.class);
        String fieldValue = value;
        for (PreAssignmentProcessor processor : processors) {
            fieldValue = this.preProcessValue(processor, fieldValue);
        }
        for (PreAssignmentValidator validator : validators = (PreAssignmentValidator[])this.field.getAnnotationsByType(PreAssignmentValidator.class)) {
            this.validateValue(validator, fieldValue);
        }
        this.assignValueToField(bean, this.convert(fieldValue), header);
    }

    private String preProcessValue(PreAssignmentProcessor processor, String value) throws CsvValidationException {
        try {
            StringProcessor stringProcessor = processor.processor().newInstance();
            stringProcessor.setParameterString(processor.paramString());
            return stringProcessor.processString(value);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new CsvValidationException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("validator.instantiation.impossible"), processor.processor().getName(), this.field.getName()));
        }
    }

    private void validateValue(PreAssignmentValidator validator, String value) throws CsvValidationException {
        try {
            StringValidator stringValidator = validator.validator().newInstance();
            stringValidator.setParameterString(validator.paramString());
            stringValidator.validate(value, this);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new CsvValidationException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("validator.instantiation.impossible"), validator.validator().getName(), this.field.getName()));
        }
    }

    @Override
    public Object getFieldValue(Object bean) {
        Object o = null;
        try {
            o = this.fieldAccess.getField(bean);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(bean, this.field, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("error.introspecting.field"), this.field.getName(), bean.getClass().toString()));
            csve.initCause(e);
            throw csve;
        }
        return o;
    }

    @Override
    public Object[] indexAndSplitMultivaluedField(Object value, I index) throws CsvDataTypeMismatchException {
        return new Object[]{value};
    }

    protected boolean isFieldEmptyForWrite(Object value) {
        return value == null;
    }

    protected void assignValueToField(Object bean, Object obj, String header) throws CsvDataTypeMismatchException {
        if (obj != null) {
            try {
                this.fieldAccess.setField(bean, obj);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(bean, this.field, e.getLocalizedMessage());
                csve.initCause(e);
                throw csve;
            }
            catch (IllegalArgumentException e2) {
                CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(obj, this.field.getType());
                csve.initCause(e2);
                throw csve;
            }
        }
    }

    protected abstract Object convert(String var1) throws CsvDataTypeMismatchException, CsvConstraintViolationException;

    @Override
    public final String[] write(Object bean, I index) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String[] result;
        Object value;
        Object object = value = bean != null ? this.getFieldValue(bean) : null;
        if (this.required && (bean == null || this.isFieldEmptyForWrite(value))) {
            throw new CsvRequiredFieldEmptyException(this.type, this.field, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("required.field.empty"), this.field.getName()));
        }
        Object[] multivalues = this.indexAndSplitMultivaluedField(value, index);
        String[] intermediateResult = new String[multivalues.length];
        try {
            for (int i = 0; i < multivalues.length; ++i) {
                intermediateResult[i] = this.convertToWrite(multivalues[i]);
            }
            result = intermediateResult;
        }
        catch (CsvDataTypeMismatchException e) {
            CsvDataTypeMismatchException csve = new CsvDataTypeMismatchException(bean, this.field.getType(), e.getMessage());
            csve.initCause(e.getCause());
            throw csve;
        }
        catch (CsvRequiredFieldEmptyException e) {
            Class<?> beanClass = bean == null ? null : bean.getClass();
            CsvRequiredFieldEmptyException csve = new CsvRequiredFieldEmptyException(beanClass, this.field, e.getMessage());
            csve.initCause(e.getCause());
            throw csve;
        }
        return result;
    }

    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return Objects.toString(value, "");
    }
}

