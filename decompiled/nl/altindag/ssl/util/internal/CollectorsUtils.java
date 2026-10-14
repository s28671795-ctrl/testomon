/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class CollectorsUtils {
    private CollectorsUtils() {
    }

    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList);
    }

    public static <T> Collector<T, ?, List<T>> toModifiableList() {
        return Collectors.toCollection(ArrayList::new);
    }

    public static <T, U> Collector<T, ?, U> toListAndThen(Function<List<T>, U> finisher) {
        return Collectors.collectingAndThen(Collectors.toList(), finisher);
    }

    public static <T> Collector<T, ?, T[]> toArray(T[] template) {
        return Collectors.collectingAndThen(Collectors.toList(), list -> list.toArray(template));
    }

    public static Collector<String, ?, String[]> toStringArray() {
        return CollectorsUtils.toArray(new String[0]);
    }
}

