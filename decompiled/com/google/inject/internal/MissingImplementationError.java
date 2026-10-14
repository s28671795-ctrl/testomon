/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.internal.MissingImplementationErrorHints;
import com.google.inject.spi.ErrorDetail;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class MissingImplementationError<T>
extends InternalErrorDetail<MissingImplementationError<T>> {
    private final Key<T> key;
    private final Supplier<ImmutableList<String>> suggestionsSupplier;

    public MissingImplementationError(Key<T> key, Injector injector, List<Object> sources) {
        this(key, Suppliers.memoize(() -> MissingImplementationErrorHints.getSuggestions(key, injector)), sources);
    }

    private MissingImplementationError(Key<T> key, Supplier<ImmutableList<String>> suggestionsSupplier, List<Object> sources) {
        super(ErrorId.MISSING_IMPLEMENTATION, String.format("No implementation for %s was bound.", Messages.convert(key)), sources, null);
        this.key = key;
        this.suggestionsSupplier = suggestionsSupplier;
    }

    @Override
    public boolean isMergeable(ErrorDetail<?> otherError) {
        return otherError instanceof MissingImplementationError && ((MissingImplementationError)otherError).key.equals(this.key);
    }

    @Override
    public void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        ImmutableList<String> suggestions = this.suggestionsSupplier.get();
        if (!suggestions.isEmpty()) {
            suggestions.forEach(x$0 -> formatter.format((String)x$0, new Object[0]));
        }
        ArrayList<List<Object>> sourcesList = new ArrayList<List<Object>>();
        sourcesList.add(this.getSources());
        sourcesList.addAll(mergeableErrors.stream().map(ErrorDetail::getSources).collect(Collectors.toList()));
        List filteredSourcesList = sourcesList.stream().map(this::trimSource).filter(sources -> !sources.isEmpty()).collect(Collectors.toList());
        if (!filteredSourcesList.isEmpty()) {
            formatter.format("\n%s\n", Messages.bold("Requested by:"));
            int sourceListIndex = 1;
            for (List sources2 : filteredSourcesList) {
                ErrorFormatter.formatSources(sourceListIndex++, Lists.reverse(sources2), formatter);
            }
        }
    }

    @Override
    public MissingImplementationError<T> withSources(List<Object> newSources) {
        return new MissingImplementationError<T>(this.key, this.suggestionsSupplier, newSources);
    }

    private List<Object> trimSource(List<Object> sources) {
        return sources.stream().filter(source2 -> !source2.equals(this.key)).collect(Collectors.toList());
    }
}

