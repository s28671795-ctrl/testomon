/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.AbstractMappingStrategy;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.BeanFieldJoinIntegerIndex;
import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.BeanFieldSplit;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.CsvBindAndJoinByPosition;
import com.opencsv.bean.CsvBindAndJoinByPositions;
import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindAndSplitByPositions;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvBindByPositions;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvCustomBindByPositions;
import com.opencsv.bean.FieldMap;
import com.opencsv.bean.FieldMapByPosition;
import com.opencsv.bean.FieldMapByPositionEntry;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.ArrayUtils;

public class ColumnPositionMappingStrategy<T>
extends AbstractMappingStrategy<String, Integer, ComplexFieldMapEntry<String, Integer, T>, T> {
    private boolean columnsExplicitlySet = false;
    private FieldMapByPosition<T> fieldMap;
    private Comparator<Integer> writeOrder;
    private Integer[] columnIndexForWriting = null;

    @Override
    public void captureHeader(CSVReader reader) throws IOException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("type.unset"));
        }
        String[] firstLine = ArrayUtils.nullToEmpty(reader.peek());
        this.fieldMap.setMaxIndex(firstLine.length - 1);
        if (!this.columnsExplicitlySet) {
            this.headerIndex.clear();
            for (FieldMapByPositionEntry<T> entry : this.fieldMap) {
                Field f = entry.getField().getField();
                if (f.getAnnotation(CsvCustomBindByPosition.class) == null && f.getAnnotation(CsvBindAndSplitByPosition.class) == null && f.getAnnotation(CsvBindAndJoinByPosition.class) == null && f.getAnnotation(CsvBindByPosition.class) == null) continue;
                this.headerIndex.put(entry.getPosition(), f.getName().toUpperCase().trim());
            }
        }
    }

    @Override
    protected Integer chooseMultivaluedFieldIndexFromHeaderIndex(int index) {
        return index;
    }

    @Override
    protected BeanField<T, Integer> findField(int col) {
        if (this.columnIndexForWriting != null) {
            return col < this.columnIndexForWriting.length ? this.fieldMap.get(this.columnIndexForWriting[col]) : null;
        }
        return this.fieldMap.get(col);
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        String[] h2 = super.generateHeader(bean);
        this.columnIndexForWriting = new Integer[h2.length];
        Arrays.setAll(this.columnIndexForWriting, i -> i);
        Arrays.sort(this.columnIndexForWriting, this.writeOrder);
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    @Override
    public String getColumnName(int col) {
        return this.headerIndex.getByPosition(col);
    }

    public String[] getColumnMapping() {
        return this.headerIndex.getHeaderIndex();
    }

    public void setColumnMapping(String ... columnMapping) {
        if (columnMapping != null) {
            this.headerIndex.initializeHeaderIndex(columnMapping);
        } else {
            this.headerIndex.clear();
        }
        this.columnsExplicitlySet = true;
        if (this.getType() != null) {
            this.loadFieldMap();
        }
    }

    private void registerCustomBinding(CsvCustomBindByPosition annotation, Class<?> localType, Field localField) {
        Class<? extends AbstractBeanField> converter = annotation.converter();
        BeanField bean = this.instantiateCustomConverter(converter);
        bean.setType(localType);
        bean.setField(localField);
        bean.setRequired(annotation.required());
        this.fieldMap.put(annotation.position(), bean);
    }

    private void registerSplitBinding(CsvBindAndSplitByPosition annotation, Class<?> localType, Field localField) {
        String fieldLocale = annotation.locale();
        String fieldWriteLocale = annotation.writeLocaleEqualsReadLocale() ? fieldLocale : annotation.writeLocale();
        Class<?> elementType = annotation.elementType();
        CsvConverter converter = this.determineConverter(localField, elementType, fieldLocale, fieldWriteLocale, annotation.converter());
        this.fieldMap.put(annotation.position(), new BeanFieldSplit(localType, localField, annotation.required(), this.errorLocale, converter, annotation.splitOn(), annotation.writeDelimiter(), annotation.collectionType(), elementType, annotation.capture(), annotation.format()));
    }

    private void registerJoinBinding(CsvBindAndJoinByPosition annotation, Class<?> localType, Field localField) {
        String fieldLocale = annotation.locale();
        String fieldWriteLocale = annotation.writeLocaleEqualsReadLocale() ? fieldLocale : annotation.writeLocale();
        CsvConverter converter = this.determineConverter(localField, annotation.elementType(), fieldLocale, fieldWriteLocale, annotation.converter());
        this.fieldMap.putComplex(annotation.position(), (BeanField<T, Integer>)new BeanFieldJoinIntegerIndex(localType, localField, annotation.required(), this.errorLocale, converter, annotation.mapType(), annotation.capture(), annotation.format()));
    }

    private void registerBinding(CsvBindByPosition annotation, Class<?> localType, Field localField) {
        String fieldLocale = annotation.locale();
        String fieldWriteLocale = annotation.writeLocaleEqualsReadLocale() ? fieldLocale : annotation.writeLocale();
        CsvConverter converter = this.determineConverter(localField, localField.getType(), fieldLocale, fieldWriteLocale, null);
        this.fieldMap.put(annotation.position(), new BeanFieldSingleValue(localType, localField, annotation.required(), this.errorLocale, converter, annotation.capture(), annotation.format()));
    }

    @Override
    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> fields) {
        for (Map.Entry classAndField : fields.entries()) {
            Annotation annotation;
            Class localType = (Class)classAndField.getKey();
            Field localField = (Field)classAndField.getValue();
            if (localField.isAnnotationPresent(CsvCustomBindByPosition.class) || localField.isAnnotationPresent(CsvCustomBindByPositions.class)) {
                annotation = (CsvCustomBindByPosition)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvCustomBindByPosition.class), CsvCustomBindByPosition::profiles);
                if (annotation == null) continue;
                this.registerCustomBinding((CsvCustomBindByPosition)annotation, localType, localField);
                continue;
            }
            if (localField.isAnnotationPresent(CsvBindAndSplitByPosition.class) || localField.isAnnotationPresent(CsvBindAndSplitByPositions.class)) {
                annotation = (CsvBindAndSplitByPosition)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindAndSplitByPosition.class), CsvBindAndSplitByPosition::profiles);
                if (annotation == null) continue;
                this.registerSplitBinding((CsvBindAndSplitByPosition)annotation, localType, localField);
                continue;
            }
            if (localField.isAnnotationPresent(CsvBindAndJoinByPosition.class) || localField.isAnnotationPresent(CsvBindAndJoinByPositions.class)) {
                annotation = (CsvBindAndJoinByPosition)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindAndJoinByPosition.class), CsvBindAndJoinByPosition::profiles);
                if (annotation == null) continue;
                this.registerJoinBinding((CsvBindAndJoinByPosition)annotation, localType, localField);
                continue;
            }
            annotation = (CsvBindByPosition)this.selectAnnotationForProfile(localField.getAnnotationsByType(CsvBindByPosition.class), CsvBindByPosition::profiles);
            if (annotation == null) continue;
            this.registerBinding((CsvBindByPosition)annotation, localType, localField);
        }
    }

    @Override
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> fields) {
        for (Map.Entry classAndField : fields.entries()) {
            Class localType = (Class)classAndField.getKey();
            Field localField = (Field)classAndField.getValue();
            CsvConverter converter = this.determineConverter(localField, localField.getType(), null, null, null);
            int[] indices = this.headerIndex.getByName(localField.getName());
            if (indices.length == 0) continue;
            this.fieldMap.put(indices[0], new BeanFieldSingleValue(localType, localField, false, this.errorLocale, converter, null, null));
        }
    }

    @Override
    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return new HashSet<Class<? extends Annotation>>(Arrays.asList(CsvBindByPositions.class, CsvCustomBindByPositions.class, CsvBindAndJoinByPositions.class, CsvBindAndSplitByPositions.class, CsvBindByPosition.class, CsvCustomBindByPosition.class, CsvBindAndJoinByPosition.class, CsvBindAndSplitByPosition.class));
    }

    @Override
    protected void initializeFieldMap() {
        this.fieldMap = new FieldMapByPosition(this.errorLocale);
        this.fieldMap.setColumnOrderOnWrite(this.writeOrder);
    }

    @Override
    protected void verifyLineLength(int numberOfFields) throws CsvRequiredFieldEmptyException {
        if (!this.headerIndex.isEmpty()) {
            StringBuilder sb = null;
            for (int i = numberOfFields; i <= this.headerIndex.findMaxIndex(); ++i) {
                BeanField<T, Integer> f = this.findField(i);
                if (f == null || !f.isRequired()) continue;
                if (sb == null) {
                    sb = new StringBuilder(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("multiple.required.field.empty"));
                }
                sb.append(' ');
                sb.append(f.getField().getName());
            }
            if (sb != null) {
                throw new CsvRequiredFieldEmptyException(this.type, sb.toString());
            }
        }
    }

    @Override
    public String findHeader(int col) {
        return Integer.toString(col);
    }

    @Override
    protected FieldMap<String, Integer, ? extends ComplexFieldMapEntry<String, Integer, T>, T> getFieldMap() {
        return this.fieldMap;
    }

    public void setColumnOrderOnWrite(Comparator<Integer> writeOrder) {
        this.writeOrder = writeOrder;
        if (this.fieldMap != null) {
            this.fieldMap.setColumnOrderOnWrite(this.writeOrder);
        }
    }
}

