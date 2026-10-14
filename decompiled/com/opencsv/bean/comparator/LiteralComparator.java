/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean.comparator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.collections4.comparators.ComparableComparator;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.comparators.NullComparator;

@Deprecated
public class LiteralComparator<T extends Comparable<T>>
implements Comparator<T>,
Serializable {
    private static final long serialVersionUID = 1L;
    private Comparator<T> c;

    public LiteralComparator(T[] predefinedOrder) {
        List predefinedList = predefinedOrder == null ? Collections.emptyList() : Arrays.asList(predefinedOrder);
        FixedOrderComparator fixedComparator = new FixedOrderComparator(predefinedList);
        fixedComparator.setUnknownObjectBehavior(FixedOrderComparator.UnknownObjectBehavior.AFTER);
        this.c = new ComparatorChain<T>(Arrays.asList(fixedComparator, new NullComparator(false), new ComparableComparator()));
    }

    @Override
    public int compare(T o1, T o2) {
        return this.c.compare(o1, o2);
    }
}

