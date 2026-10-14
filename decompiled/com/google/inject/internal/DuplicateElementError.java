/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.ErrorDetail;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class DuplicateElementError<T>
extends InternalErrorDetail<DuplicateElementError<T>> {
    private final Key<Set<T>> setKey;
    private final ImmutableMultimap<T, Element<T>> elements;

    DuplicateElementError(Key<Set<T>> setKey, List<Binding<T>> bindings, T[] values2, List<Object> sources) {
        this(setKey, DuplicateElementError.indexElements(bindings, values2), sources);
    }

    private DuplicateElementError(Key<Set<T>> setKey, ImmutableMultimap<T, Element<T>> elements, List<Object> sources) {
        super(ErrorId.DUPLICATE_ELEMENT, String.format("Duplicate elements found in Multibinder %s.", Messages.convert(setKey)), sources, null);
        this.setKey = setKey;
        this.elements = elements;
    }

    @Override
    protected void formatDetail(List<ErrorDetail<?>> others, Formatter formatter) {
        formatter.format("\n%s\n", Messages.bold("Duplicates:"));
        int duplicateIndex = 1;
        for (Map.Entry entry : ((ImmutableMap)this.elements.asMap()).entrySet()) {
            formatter.format("%-2s: ", duplicateIndex++);
            if (((Collection)entry.getValue()).size() <= 1) continue;
            Set valuesAsString = ((Collection)entry.getValue()).stream().map(element -> element.value.toString()).collect(Collectors.toSet());
            if (valuesAsString.size() == 1) {
                formatter.format("Element: %s\n", Messages.redBold((String)valuesAsString.iterator().next()));
                formatter.format("    Bound at:\n", new Object[0]);
                int index = 1;
                for (Element element2 : (Collection)entry.getValue()) {
                    formatter.format("    %-2s: ", index++);
                    this.formatElement(element2, formatter);
                }
                continue;
            }
            boolean indent = false;
            for (Element element2 : (Collection)entry.getValue()) {
                if (indent) {
                    formatter.format("    ", new Object[0]);
                } else {
                    indent = true;
                }
                formatter.format("Element: %s\n", Messages.redBold(element2.value.toString()));
                formatter.format("    Bound at: ", new Object[0]);
                this.formatElement(element2, formatter);
            }
        }
        formatter.format("\n%s\n", Messages.bold("Multibinder declared at:"));
        List<Object> filteredSource = this.getSources().stream().filter(source2 -> {
            if (source2 instanceof Dependency) {
                return !((Dependency)source2).getKey().equals(this.setKey);
            }
            return true;
        }).collect(Collectors.toList());
        ErrorFormatter.formatSources(filteredSource, formatter);
    }

    private void formatElement(Element<T> element, Formatter formatter) {
        Object source2 = element.binding.getSource();
        new SourceFormatter(source2, formatter, true).format();
    }

    @Override
    public DuplicateElementError<T> withSources(List<Object> newSources) {
        return new DuplicateElementError<T>(this.setKey, this.elements, newSources);
    }

    static <T> ImmutableMultimap<T, Element<T>> indexElements(List<Binding<T>> bindings, T[] values2) {
        ImmutableMultimap.Builder<T, Element<T>> map = ImmutableMultimap.builder();
        for (int i = 0; i < values2.length; ++i) {
            map.put(values2[i], new Element<T>(values2[i], bindings.get(i)));
        }
        return map.build();
    }

    static class Element<T> {
        T value;
        Binding<T> binding;

        Element(T value, Binding<T> binding) {
            this.value = value;
            this.binding = binding;
        }
    }
}

