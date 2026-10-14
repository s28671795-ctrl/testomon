/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.BeanFieldJoinStringIndex;
import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.BeanFieldSplit;
import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindAndJoinByNames;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindAndSplitByNames;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByNames;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvCustomBindByNames;
import com.opencsv.bean.HeaderNameBaseMappingStrategy;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.StringUtils;

public class HeaderColumnNameMappingStrategy<T>
extends HeaderNameBaseMappingStrategy<T> {
    public HeaderColumnNameMappingStrategy() {
    }

    public HeaderColumnNameMappingStrategy(boolean forceCorrectRecordLength) {
        super(forceCorrectRecordLength);
    }

    private void registerCustomBinding(CsvCustomBindByName annotation, Class<?> localType, Field localField) {
        String columnName = annotation.column().toUpperCase().trim();
        if (StringUtils.isEmpty(columnName)) {
            columnName = localField.getName().toUpperCase();
        }
        Class<? extends AbstractBeanField> converter = annotation.converter();
        BeanField bean = this.instantiateCustomConverter(converter);
        bean.setType(localType);
        bean.setField(localField);
        bean.setRequired(annotation.required());
        this.fieldMap.put(columnName, bean);
    }

    private void registerSplitBinding(CsvBindAndSplitByName annotation, Class<?> localType, Field localField) {
        String columnName = annotation.column().toUpperCase().trim();
        String locale = annotation.locale();
        String writeLocale = annotation.writeLocaleEqualsReadLocale() ? locale : annotation.writeLocale();
        Class<?> elementType = annotation.elementType();
        CsvConverter converter = this.determineConverter(localField, elementType, locale, writeLocale, annotation.converter());
        if (StringUtils.isEmpty(columnName)) {
            this.fieldMap.put(localField.getName().toUpperCase(), new BeanFieldSplit(localType, localField, annotation.required(), this.errorLocale, converter, annotation.splitOn(), annotation.writeDelimiter(), annotation.collectionType(), elementType, annotation.capture(), annotation.format()));
        } else {
            this.fieldMap.put(columnName, new BeanFieldSplit(localType, localField, annotation.required(), this.errorLocale, converter, annotation.splitOn(), annotation.writeDelimiter(), annotation.collectionType(), elementType, annotation.capture(), annotation.format()));
        }
    }

    private void registerJoinBinding(CsvBindAndJoinByName annotation, Class<?> localType, Field localField) {
        String columnRegex = annotation.column();
        String locale = annotation.locale();
        String writeLocale = annotation.writeLocaleEqualsReadLocale() ? locale : annotation.writeLocale();
        CsvConverter converter = this.determineConverter(localField, annotation.elementType(), locale, writeLocale, annotation.converter());
        if (StringUtils.isEmpty(columnRegex)) {
            this.fieldMap.putComplex(localField.getName(), new BeanFieldJoinStringIndex(localType, localField, annotation.required(), this.errorLocale, converter, annotation.mapType(), annotation.capture(), annotation.format()));
        } else {
            this.fieldMap.putComplex(columnRegex, new BeanFieldJoinStringIndex(localType, localField, annotation.required(), this.errorLocale, converter, annotation.mapType(), annotation.capture(), annotation.format()));
        }
    }

    private void registerBinding(CsvBindByName annotation, Class<?> localType, Field localField) {
        String columnName = annotation.column().toUpperCase().trim();
        String locale = annotation.locale();
        String writeLocale = annotation.writeLocaleEqualsReadLocale() ? locale : annotation.writeLocale();
        CsvConverter converter = this.determineConverter(localField, localField.getType(), locale, writeLocale, null);
        if (StringUtils.isEmpty(columnName)) {
            this.fieldMap.put(localField.getName().toUpperCase(), new BeanFieldSingleValue(localType, localField, annotation.required(), this.errorLocale, converter, annotation.capture(), annotation.format()));
        } else {
            this.fieldMap.put(columnName, new BeanFieldSingleValue(localType, localField, annotation.required(), this.errorLocale, converter, annotation.capture(), annotation.format()));
        }
    }

    @Override
    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> fields) {
        for (Map.Entry classField : fields.entries()) {
            Annotation annotation;
            Class localType = (Class)classField.getKey();
            Field localField = (Field)classField.getValue();
            if (localField.isAnnotationPresent(CsvCustomBindByName.class) || localField.isAnnotationPresent(CsvCustomBindByNames.class)) {
                annotation = (CsvCustomBindByName)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvCustomBindByName.class), CsvCustomBindByName::profiles);
                if (annotation == null) continue;
                this.registerCustomBinding((CsvCustomBindByName)annotation, localType, localField);
                continue;
            }
            if (localField.isAnnotationPresent(CsvBindAndSplitByName.class) || localField.isAnnotationPresent(CsvBindAndSplitByNames.class)) {
                annotation = (CsvBindAndSplitByName)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindAndSplitByName.class), CsvBindAndSplitByName::profiles);
                if (annotation == null) continue;
                this.registerSplitBinding((CsvBindAndSplitByName)annotation, localType, localField);
                continue;
            }
            if (localField.isAnnotationPresent(CsvBindAndJoinByName.class) || localField.isAnnotationPresent(CsvBindAndJoinByNames.class)) {
                annotation = (CsvBindAndJoinByName)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindAndJoinByName.class), CsvBindAndJoinByName::profiles);
                if (annotation == null) continue;
                this.registerJoinBinding((CsvBindAndJoinByName)annotation, localType, localField);
                continue;
            }
            annotation = (CsvBindByName)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindByName.class), CsvBindByName::profiles);
            if (annotation == null) continue;
            this.registerBinding((CsvBindByName)annotation, localType, localField);
        }
    }

    @Override
    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return new HashSet<Class<? extends Annotation>>(Arrays.asList(CsvBindByNames.class, CsvCustomBindByNames.class, CsvBindAndSplitByNames.class, CsvBindAndJoinByNames.class, CsvBindByName.class, CsvCustomBindByName.class, CsvBindAndSplitByName.class, CsvBindAndJoinByName.class));
    }
}

