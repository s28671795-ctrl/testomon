/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.CsvConverter;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;

public abstract class BeanFieldJoin<T, I>
extends BeanFieldSingleValue<T, I> {
    private final Class<? extends MultiValuedMap> mapType;

    public BeanFieldJoin(Class<?> type, Field field2, boolean required, Locale errorLocale, CsvConverter converter, Class<? extends MultiValuedMap> mapType, String capture, String format) {
        super(type, field2, required, errorLocale, converter, capture, format);
        if (!MultiValuedMap.class.isAssignableFrom(field2.getType())) {
            throw new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", errorLocale).getString("invalid.multivaluedmap.type"), field2.getType().toString()));
        }
        Class<?> fieldType = field2.getType();
        if (!fieldType.isInterface()) {
            this.mapType = field2.getType();
        } else if (!mapType.isInterface()) {
            this.mapType = mapType;
        } else if (MultiValuedMap.class.equals(fieldType) || ListValuedMap.class.equals(fieldType)) {
            this.mapType = ArrayListValuedHashMap.class;
        } else if (SetValuedMap.class.equals(fieldType)) {
            this.mapType = HashSetValuedHashMap.class;
        } else {
            this.mapType = null;
            throw new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", errorLocale).getString("invalid.multivaluedmap.type"), mapType.toString()));
        }
        if (!field2.getType().isAssignableFrom(this.mapType)) {
            throw new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", errorLocale).getString("unassignable.multivaluedmap.type"), mapType.getName(), field2.getType().getName()));
        }
    }

    protected abstract Object putNewValue(MultiValuedMap<I, Object> var1, String var2, Object var3);

    @Override
    protected void assignValueToField(Object bean, Object obj, String header) throws CsvDataTypeMismatchException {
        MultiValuedMap currentValue = (MultiValuedMap)this.getFieldValue(bean);
        try {
            if (currentValue == null) {
                Constructor<? extends MultiValuedMap> c = this.mapType.getConstructor(new Class[0]);
                currentValue = c.newInstance(new Object[0]);
            }
            this.putNewValue(currentValue, header, obj);
            super.assignValueToField(bean, currentValue, header);
        }
        catch (ClassCastException | IllegalAccessException | InvocationTargetException e) {
            CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(bean, this.field, e.getLocalizedMessage());
            csve.initCause(e);
            throw csve;
        }
        catch (InstantiationException | NoSuchMethodException e) {
            CsvBadConverterException csve = new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("map.cannot.be.instantiated"), this.mapType.getName()));
            csve.initCause(e);
            throw csve;
        }
    }

    @Override
    public Object[] indexAndSplitMultivaluedField(Object value, I index) throws CsvDataTypeMismatchException {
        Object[] splitObjects = ArrayUtils.EMPTY_OBJECT_ARRAY;
        if (value != null) {
            if (MultiValuedMap.class.isAssignableFrom(value.getClass())) {
                MultiValuedMap map = (MultiValuedMap)value;
                Collection splitCollection = map.get(index);
                splitObjects = splitCollection.toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
            } else {
                throw new CsvDataTypeMismatchException(value, String.class, ResourceBundle.getBundle("opencsv", this.errorLocale).getString("field.not.multivaluedmap"));
            }
        }
        return splitObjects;
    }

    @Override
    protected boolean isFieldEmptyForWrite(Object value) {
        return super.isFieldEmptyForWrite(value) || ((MultiValuedMap)value).isEmpty();
    }
}

