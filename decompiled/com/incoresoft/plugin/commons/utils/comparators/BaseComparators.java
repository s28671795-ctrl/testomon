/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils.comparators;

import java.util.Comparator;
import java.util.function.Function;

public class BaseComparators {
    public static <T, R extends Comparable<? super R>> Comparator<? super T> getComparator(Function<? super T, ? extends R> comparingFunction, boolean reverse) {
        Comparator<T> comparator = Comparator.comparing(comparingFunction);
        return !reverse ? comparator : comparator.reversed();
    }
}

