/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractFieldMapEntry;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.BeanFieldJoin;
import com.opencsv.bean.FieldMapByPositionEntry;
import com.opencsv.exceptions.CsvBadConverterException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

public class PositionToBeanField<T>
extends AbstractFieldMapEntry<String, Integer, T>
implements Iterable<FieldMapByPositionEntry<T>> {
    private final String initializer;
    private final List<Range<Integer>> ranges;

    public PositionToBeanField(String rangeDefinition, int maxIndex, BeanField<T, Integer> field2, Locale errorLocale) {
        super(field2, errorLocale);
        this.initializer = rangeDefinition;
        this.ranges = new LinkedList<Range<Integer>>();
        if (StringUtils.isBlank(rangeDefinition)) {
            throw new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.range.definition"), rangeDefinition));
        }
        String[] partialRangeDefinitions = rangeDefinition.split(",");
        try {
            for (String r : partialRangeDefinitions) {
                Range<Comparable<Integer>> range;
                if (!StringUtils.isNotEmpty(r)) continue;
                if (r.contains("-")) {
                    String[] endpoints = r.split("-", 2);
                    Integer min2 = StringUtils.isEmpty(endpoints[0]) ? Integer.valueOf(0) : Integer.valueOf(endpoints[0].trim());
                    Integer max = maxIndex;
                    if (endpoints.length == 2 && StringUtils.isNotEmpty(endpoints[1])) {
                        max = Integer.valueOf(endpoints[1].trim());
                    }
                    if (max >= maxIndex) {
                        max = min2 >= maxIndex ? min2 : Integer.valueOf(maxIndex);
                    }
                    range = Range.between(min2, max);
                } else {
                    range = Range.is(Integer.valueOf(r));
                }
                ListIterator<Range<Integer>> it = this.ranges.listIterator();
                boolean completelyContained = false;
                while (it.hasNext() && !completelyContained) {
                    Range<Integer> next = it.next();
                    if (next.containsRange(range)) {
                        completelyContained = true;
                        continue;
                    }
                    if (next.isOverlappedBy(range)) {
                        range = Range.between(Math.min(next.getMinimum(), range.getMinimum()), Math.max(next.getMaximum(), range.getMaximum()));
                        it.remove();
                        continue;
                    }
                    if (next.getMaximum() + 1 == range.getMinimum()) {
                        range = Range.between((Comparable)next.getMinimum(), (Comparable)range.getMaximum());
                        continue;
                    }
                    if (range.getMaximum() + 1 != next.getMinimum()) continue;
                    range = Range.between(range.getMinimum(), (Comparable)next.getMaximum());
                }
                if (completelyContained) continue;
                this.ranges.add(range);
            }
        }
        catch (NumberFormatException e) {
            CsvBadConverterException csve = new CsvBadConverterException(BeanFieldJoin.class, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("invalid.range.definition"), rangeDefinition));
            csve.initCause(e);
            throw csve;
        }
    }

    public void attenuateRanges(int maxIndex) {
        ListIterator<Range<Integer>> rangeIterator = this.ranges.listIterator();
        while (rangeIterator.hasNext()) {
            Range<Integer> r = rangeIterator.next();
            if (r.getMaximum() <= maxIndex) continue;
            if (r.getMinimum() > maxIndex) {
                rangeIterator.set(Range.is((Comparable)r.getMinimum()));
                continue;
            }
            rangeIterator.set(Range.between((Comparable)r.getMinimum(), maxIndex));
        }
    }

    @Override
    public boolean contains(Integer key) {
        return this.ranges.stream().anyMatch(range -> range.contains(key));
    }

    @Override
    public String getInitializer() {
        return this.initializer;
    }

    @Override
    public Iterator<FieldMapByPositionEntry<T>> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator
    implements Iterator<FieldMapByPositionEntry<T>> {
        private ListIterator<Range<Integer>> rangeIterator;
        private Range<Integer> currentRange;
        private int position;

        PositionIterator() {
            if (PositionToBeanField.this.ranges.isEmpty()) {
                this.position = -1;
            } else {
                this.rangeIterator = PositionToBeanField.this.ranges.listIterator();
                this.currentRange = this.rangeIterator.next();
                this.position = this.currentRange.getMinimum();
            }
        }

        @Override
        public boolean hasNext() {
            return this.position != -1;
        }

        @Override
        public FieldMapByPositionEntry<T> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            FieldMapByPositionEntry entry = new FieldMapByPositionEntry(this.position, PositionToBeanField.this.field);
            if (this.position == this.currentRange.getMaximum() || Integer.MAX_VALUE == this.currentRange.getMaximum()) {
                if (!this.rangeIterator.hasNext()) {
                    this.position = -1;
                } else {
                    this.currentRange = this.rangeIterator.next();
                    this.position = this.currentRange.getMinimum();
                }
            } else {
                ++this.position;
            }
            return entry;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

