/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.util.OpencsvUtils;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.SortedBag;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BeanFieldSplit<T, I>
extends AbstractBeanField<T, I> {
    private final Pattern splitOn;
    private final Pattern capture;
    private final String writeDelimiter;
    private final String writeFormat;
    private final Class<? extends Collection> collectionType;
    private final Class<?> elementType;

    public BeanFieldSplit(Class<?> type, Field field2, boolean required, Locale errorLocale, CsvConverter converter, String splitOn, String writeDelimiter, Class<? extends Collection> collectionType, Class<?> elementType, String capture, String format) {
        super(type, field2, required, errorLocale, converter);
        this.writeDelimiter = writeDelimiter;
        this.writeFormat = format;
        this.elementType = elementType;
        if (!Collection.class.isAssignableFrom(field2.getType())) {
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.collection.type"), field2.getType().toString()));
        }
        this.splitOn = OpencsvUtils.compilePattern(splitOn, 0, BeanFieldSplit.class, this.errorLocale);
        this.capture = OpencsvUtils.compilePatternAtLeastOneGroup(capture, 0, BeanFieldSplit.class, this.errorLocale);
        OpencsvUtils.verifyFormatString(this.writeFormat, BeanFieldSplit.class, this.errorLocale);
        Class<?> fieldType = field2.getType();
        if (!fieldType.isInterface()) {
            this.collectionType = field2.getType();
        } else if (!collectionType.isInterface()) {
            this.collectionType = collectionType;
        } else if (Collection.class.equals(fieldType) || List.class.equals(fieldType)) {
            this.collectionType = ArrayList.class;
        } else if (Set.class.equals(fieldType)) {
            this.collectionType = fieldType.isEnum() ? EnumSet.class : HashSet.class;
        } else if (SortedSet.class.equals(fieldType) || NavigableSet.class.equals(fieldType)) {
            this.collectionType = TreeSet.class;
        } else if (Queue.class.equals(fieldType) || Deque.class.equals(fieldType)) {
            this.collectionType = ArrayDeque.class;
        } else if (Bag.class.equals(fieldType)) {
            this.collectionType = HashBag.class;
        } else if (SortedBag.class.equals(fieldType)) {
            this.collectionType = TreeBag.class;
        } else {
            this.collectionType = null;
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.collection.type"), collectionType.toString()));
        }
        if (!field2.getType().isAssignableFrom(this.collectionType)) {
            throw new CsvBadConverterException(BeanFieldSplit.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("unassignable.collection.type"), collectionType.getName(), field2.getType().getName()));
        }
    }

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] splitValues;
        Collection<Object> collection;
        try {
            collection = this.collectionType.equals(EnumSet.class) ? EnumSet.noneOf(this.elementType) : this.collectionType.newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("collection.cannot.be.instantiated"), this.collectionType.getCanonicalName()));
            csve.initCause(e);
            throw csve;
        }
        for (String s2 : splitValues = value == null ? ArrayUtils.EMPTY_STRING_ARRAY : this.splitOn.split(value)) {
            Matcher m4;
            if (this.capture != null && (m4 = this.capture.matcher(s2)).matches()) {
                s2 = m4.group(1);
            }
            collection.add(this.converter.convertToRead(s2));
        }
        return collection;
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException {
        String retval = "";
        if (value != null) {
            Collection collection = (Collection)value;
            Object[] convertedValue = new String[collection.size()];
            int i = 0;
            for (Object o : collection) {
                convertedValue[i] = this.converter.convertToWrite(o);
                if (StringUtils.isNotEmpty(this.writeFormat) && StringUtils.isNotEmpty((CharSequence)convertedValue[i])) {
                    convertedValue[i] = String.format(this.writeFormat, convertedValue[i]);
                }
                ++i;
            }
            retval = StringUtils.join(convertedValue, this.writeDelimiter);
        }
        return retval;
    }

    @Override
    protected boolean isFieldEmptyForWrite(Object value) {
        return super.isFieldEmptyForWrite(value) || ((Collection)value).isEmpty();
    }
}

