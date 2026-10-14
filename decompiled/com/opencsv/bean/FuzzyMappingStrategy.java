/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.CSVReader;
import com.opencsv.bean.BeanFieldSingleValue;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class FuzzyMappingStrategy<T>
extends HeaderColumnNameMappingStrategy<T> {
    public FuzzyMappingStrategy() {
    }

    public FuzzyMappingStrategy(boolean forceCorrectRecordLength) {
        super(forceCorrectRecordLength);
    }

    @Override
    protected void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> fields) {
    }

    @Override
    public void captureHeader(CSVReader reader) throws IOException, CsvRequiredFieldEmptyException {
        super.captureHeader(reader);
        Set<String> unusedHeaders = Stream.of(this.headerIndex.getHeaderIndex()).filter(Objects::nonNull).filter(k -> this.fieldMap.get(k.toUpperCase()) == null).collect(Collectors.toSet());
        ListValuedMap<Class<?>, Field> unusedFields = this.partitionFields().get(Boolean.FALSE);
        LevenshteinDistance levenshtein = LevenshteinDistance.getDefaultInstance();
        LinkedList<Object> comparisons = new LinkedList<Object>();
        unusedHeaders.forEach(h2 -> unusedFields.entries().forEach(f -> comparisons.add(new FuzzyComparison(levenshtein.apply(h2.toUpperCase(), ((Field)f.getValue()).getName().toUpperCase()), (String)h2, (Class)f.getKey(), (Field)f.getValue()))));
        comparisons.sort(null);
        while (!comparisons.isEmpty()) {
            FuzzyComparison fc = (FuzzyComparison)comparisons.get(0);
            CsvConverter converter = this.determineConverter(fc.field, fc.field.getType(), null, null, null);
            this.fieldMap.put(fc.header.toUpperCase(), new BeanFieldSingleValue(fc.type, fc.field, false, this.errorLocale, converter, null, null));
            comparisons.removeIf(e -> StringUtils.equals(e.header, fc.header) || Objects.equals(e.field, fc.field));
        }
    }

    private static class FuzzyComparison
    implements Comparable<FuzzyComparison> {
        final Integer distance;
        final String header;
        final Class<?> type;
        final Field field;

        FuzzyComparison(Integer distance, String header, Class<?> type, Field field2) {
            this.distance = distance;
            this.header = header;
            this.type = type;
            this.field = field2;
        }

        @Override
        public int compareTo(FuzzyComparison o) {
            return Integer.compare(this.distance, o.distance);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FuzzyComparison)) {
                return false;
            }
            FuzzyComparison that = (FuzzyComparison)o;
            return Objects.equals(this.distance, that.distance);
        }

        public int hashCode() {
            return Objects.hash(this.distance);
        }
    }
}

