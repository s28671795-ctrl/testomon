/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.bean.AbstractMappingStrategy;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvRecurse;
import com.opencsv.bean.FieldMap;
import com.opencsv.bean.FieldMapByName;
import com.opencsv.bean.FieldMapByNameEntry;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.ArrayUtils;

public abstract class HeaderNameBaseMappingStrategy<T>
extends AbstractMappingStrategy<String, String, ComplexFieldMapEntry<String, String, T>, T> {
    protected FieldMapByName<T> fieldMap = null;
    protected Comparator<String> writeOrder = null;
    protected final boolean forceCorrectRecordLength;

    public HeaderNameBaseMappingStrategy() {
        this.forceCorrectRecordLength = false;
    }

    public HeaderNameBaseMappingStrategy(boolean forceCorrectRecordLength) {
        this.forceCorrectRecordLength = forceCorrectRecordLength;
    }

    @Override
    public void captureHeader(CSVReader reader) throws IOException, CsvRequiredFieldEmptyException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("type.unset"));
        }
        CharSequence[] header = ArrayUtils.nullToEmpty(reader.readNextSilently());
        for (int i = 0; i < header.length; ++i) {
            if (header[i] != null) continue;
            header[i] = "";
        }
        this.headerIndex.initializeHeaderIndex((String[])header);
        List<FieldMapByNameEntry<T>> missingRequiredHeaders = this.fieldMap.determineMissingRequiredHeaders((String[])header);
        if (!missingRequiredHeaders.isEmpty()) {
            CharSequence[] requiredHeaderNames = new String[missingRequiredHeaders.size()];
            ArrayList<Field> requiredFields = new ArrayList<Field>(missingRequiredHeaders.size());
            for (int i = 0; i < missingRequiredHeaders.size(); ++i) {
                FieldMapByNameEntry<T> fme = missingRequiredHeaders.get(i);
                requiredHeaderNames[i] = fme.isRegexPattern() ? String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("matching"), fme.getName()) : fme.getName();
                requiredFields.add(fme.getField().getField());
            }
            String missingRequiredFields = String.join((CharSequence)", ", requiredHeaderNames);
            String allHeaders = String.join((CharSequence)",", header);
            CsvRequiredFieldEmptyException e = new CsvRequiredFieldEmptyException(this.type, requiredFields, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.required.field.absent"), missingRequiredFields, allHeaders));
            e.setLine((String[])header);
            throw e;
        }
    }

    @Override
    protected String chooseMultivaluedFieldIndexFromHeaderIndex(int index) {
        String[] s2 = this.headerIndex.getHeaderIndex();
        return index >= s2.length ? null : s2[index];
    }

    @Override
    public void verifyLineLength(int numberOfFields) throws CsvRequiredFieldEmptyException {
        if (!this.headerIndex.isEmpty() && numberOfFields != this.headerIndex.getHeaderIndexLength() && !this.forceCorrectRecordLength) {
            throw new CsvRequiredFieldEmptyException(this.type, ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.data.mismatch"));
        }
    }

    @Override
    protected BeanField<T, String> findField(int col) throws CsvBadConverterException {
        BeanField beanField = null;
        String columnName = this.getColumnName(col);
        if (columnName == null) {
            return null;
        }
        if (!(columnName = columnName.trim()).isEmpty()) {
            beanField = this.fieldMap.get(columnName.toUpperCase());
        }
        return beanField;
    }

    @Override
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> fields) {
        fields.entries().stream().filter(entry -> !Serializable.class.isAssignableFrom((Class)entry.getKey()) || !"serialVersionUID".equals(((Field)entry.getValue()).getName())).filter(entry -> !((Field)entry.getValue()).isAnnotationPresent(CsvRecurse.class)).forEach(entry -> {
            CsvConverter converter = this.determineConverter((Field)entry.getValue(), ((Field)entry.getValue()).getType(), null, null, null);
            this.fieldMap.put(((Field)entry.getValue()).getName().toUpperCase(), new BeanFieldSingleValue((Class)entry.getKey(), (Field)entry.getValue(), false, this.errorLocale, converter, null, null));
        });
    }

    @Override
    protected void initializeFieldMap() {
        this.fieldMap = new FieldMapByName(this.errorLocale);
        this.fieldMap.setColumnOrderOnWrite(this.writeOrder);
    }

    @Override
    protected FieldMap<String, String, ? extends ComplexFieldMapEntry<String, String, T>, T> getFieldMap() {
        return this.fieldMap;
    }

    @Override
    public String findHeader(int col) {
        return this.headerIndex.getByPosition(col);
    }

    public void setColumnOrderOnWrite(Comparator<String> writeOrder) {
        this.writeOrder = writeOrder;
        if (this.fieldMap != null) {
            this.fieldMap.setColumnOrderOnWrite(this.writeOrder);
        }
    }
}

