/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractFieldMap;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.FieldMapByPositionEntry;
import com.opencsv.bean.PositionToBeanField;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.iterators.LazyIteratorChain;
import org.apache.commons.collections4.iterators.TransformIterator;

public class FieldMapByPosition<T>
extends AbstractFieldMap<String, Integer, PositionToBeanField<T>, T>
implements Iterable<FieldMapByPositionEntry<T>> {
    private int maxIndex = Integer.MAX_VALUE;
    private Comparator<Integer> writeOrder = null;

    public FieldMapByPosition(Locale errorLocale) {
        super(errorLocale);
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        LinkedList<Field> missingRequiredHeaders = new LinkedList<Field>();
        TreeMap<Integer, String> headerMap = new TreeMap<Integer, String>(this.writeOrder);
        for (Map.Entry entry : this.simpleMap.entrySet()) {
            headerMap.put((Integer)entry.getKey(), ((BeanField)entry.getValue()).getField().getName());
        }
        for (ComplexFieldMapEntry r : this.complexMapList) {
            MultiValuedMap m4 = (MultiValuedMap)r.getBeanField().getFieldValue(bean);
            boolean oneEntryMatched = false;
            if (m4 != null && !m4.isEmpty()) {
                for (Map.Entry entry : m4.entries()) {
                    Integer key = (Integer)entry.getKey();
                    if (!r.contains(key)) continue;
                    headerMap.put((Integer)entry.getKey(), r.getBeanField().getField().getName());
                    oneEntryMatched = true;
                }
            }
            if (m4 != null && !m4.isEmpty() && oneEntryMatched || !r.getBeanField().isRequired()) continue;
            missingRequiredHeaders.add(r.getBeanField().getField());
        }
        TreeSet headerSet = new TreeSet(headerMap.keySet());
        int arraySize = headerSet.isEmpty() ? 0 : (Integer)headerSet.last() + 1;
        CharSequence[] headers = new String[arraySize];
        int previousIndex = headerSet.isEmpty() ? 0 : (Integer)headerSet.first();
        for (Integer i : headerSet) {
            for (int j = previousIndex + 1; j < i; ++j) {
                headerMap.put(j, null);
            }
            previousIndex = i;
        }
        previousIndex = 0;
        for (String value : headerMap.values()) {
            headers[previousIndex++] = value;
        }
        if (!missingRequiredHeaders.isEmpty()) {
            String errorMessage = String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("header.required.field.absent"), missingRequiredHeaders.stream().map(Field::getName).collect(Collectors.joining(" ")), String.join((CharSequence)" ", headers));
            throw new CsvRequiredFieldEmptyException(bean.getClass(), missingRequiredHeaders, errorMessage);
        }
        return headers;
    }

    @Override
    public void putComplex(String rangeDefinition, BeanField<T, Integer> field2) {
        this.complexMapList.add(new PositionToBeanField<T>(rangeDefinition, this.maxIndex, field2, this.errorLocale));
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
        this.complexMapList.forEach(p -> p.attenuateRanges(maxIndex));
    }

    @Override
    public Iterator<FieldMapByPositionEntry<T>> iterator() {
        return new LazyIteratorChain<FieldMapByPositionEntry<T>>(){

            @Override
            protected Iterator<FieldMapByPositionEntry<T>> nextIterator(int count) {
                if (count <= FieldMapByPosition.this.complexMapList.size()) {
                    return ((PositionToBeanField)FieldMapByPosition.this.complexMapList.get(count - 1)).iterator();
                }
                if (count == FieldMapByPosition.this.complexMapList.size() + 1) {
                    return new TransformIterator<Map.Entry, FieldMapByPositionEntry>(FieldMapByPosition.this.simpleMap.entrySet().iterator(), input -> new FieldMapByPositionEntry((Integer)input.getKey(), (BeanField)input.getValue()));
                }
                return null;
            }
        };
    }

    public void setColumnOrderOnWrite(Comparator<Integer> writeOrder) {
        this.writeOrder = writeOrder;
    }
}

