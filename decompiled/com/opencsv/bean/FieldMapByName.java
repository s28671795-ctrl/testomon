/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractFieldMap;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.FieldMapByNameEntry;
import com.opencsv.bean.RegexToBeanField;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.ArrayUtils;

public class FieldMapByName<T>
extends AbstractFieldMap<String, String, RegexToBeanField<T>, T> {
    private Comparator<String> writeOrder = null;

    public FieldMapByName(Locale errorLocale) {
        super(errorLocale);
    }

    @Override
    public void putComplex(String key, BeanField<T, String> value) {
        this.complexMapList.add(new RegexToBeanField<T>(key, value, this.errorLocale));
    }

    public List<FieldMapByNameEntry<T>> determineMissingRequiredHeaders(String[] headersPresent) {
        List requiredStringList = this.simpleMap.entrySet().stream().filter(e -> ((BeanField)e.getValue()).isRequired()).map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedList::new));
        List requiredRegexList = this.complexMapList.stream().filter(r -> r.getBeanField().isRequired()).collect(Collectors.toList());
        for (String h2 : headersPresent) {
            if (requiredStringList.remove(h2.toUpperCase().trim())) continue;
            ListIterator requiredRegexListIterator = requiredRegexList.listIterator();
            boolean found = false;
            while (!found && requiredRegexListIterator.hasNext()) {
                ComplexFieldMapEntry r2 = (ComplexFieldMapEntry)requiredRegexListIterator.next();
                if (!r2.contains(h2)) continue;
                found = true;
                requiredRegexListIterator.remove();
            }
        }
        LinkedList<FieldMapByNameEntry<T>> missingRequiredHeaders = new LinkedList<FieldMapByNameEntry<T>>();
        for (String s2 : requiredStringList) {
            missingRequiredHeaders.add(new FieldMapByNameEntry(s2, (BeanField)this.simpleMap.get(s2), false));
        }
        for (ComplexFieldMapEntry r3 : requiredRegexList) {
            missingRequiredHeaders.add(new FieldMapByNameEntry((String)r3.getInitializer(), r3.getBeanField(), true));
        }
        return missingRequiredHeaders;
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        LinkedList<Field> missingRequiredHeaders = new LinkedList<Field>();
        ArrayList headerList = new ArrayList(this.simpleMap.keySet());
        for (ComplexFieldMapEntry r : this.complexMapList) {
            MultiValuedMap m4 = (MultiValuedMap)r.getBeanField().getFieldValue(bean);
            if (m4 != null && !m4.isEmpty()) {
                headerList.addAll(m4.entries().stream().map(Map.Entry::getKey).filter(r::contains).collect(Collectors.toList()));
                continue;
            }
            if (!r.getBeanField().isRequired()) continue;
            missingRequiredHeaders.add(r.getBeanField().getField());
        }
        if (!missingRequiredHeaders.isEmpty()) {
            String errorMessage = String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.required.field.absent"), missingRequiredHeaders.stream().map(Field::getName).collect(Collectors.joining(" ")), String.join((CharSequence)" ", headerList));
            throw new CsvRequiredFieldEmptyException(bean.getClass(), missingRequiredHeaders, errorMessage);
        }
        headerList.sort(this.writeOrder);
        return headerList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public void setColumnOrderOnWrite(Comparator<String> writeOrder) {
        this.writeOrder = writeOrder;
    }
}

