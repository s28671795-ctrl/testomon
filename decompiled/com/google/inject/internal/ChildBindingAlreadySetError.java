/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.inject.Key;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.internal.SourceFormatter;
import com.google.inject.spi.ErrorDetail;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

final class ChildBindingAlreadySetError
extends InternalErrorDetail<ChildBindingAlreadySetError> {
    private final Key<?> key;
    private final ImmutableList<Object> existingSources;

    ChildBindingAlreadySetError(Key<?> key, Iterable<Object> existingSoruces, List<Object> sources) {
        super(ErrorId.CHILD_BINDING_ALREADY_SET, String.format("Unable to create binding for %s because it was already configured on one or more child injectors or private modules.", Messages.convert(key)), sources, null);
        this.key = key;
        this.existingSources = ImmutableList.copyOf(Streams.stream(existingSoruces).map(source2 -> source2 == null ? "" : source2).collect(Collectors.toList()));
    }

    @Override
    public boolean isMergeable(ErrorDetail<?> otherError) {
        return otherError instanceof ChildBindingAlreadySetError && ((ChildBindingAlreadySetError)otherError).key.equals(this.key);
    }

    @Override
    public void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        formatter.format("\n%s\n", Messages.bold("Bound at:"));
        int index = 1;
        for (Object e : this.existingSources) {
            formatter.format("%-2s: ", index++);
            if (e.equals("")) {
                formatter.format("as a just-in-time binding\n", new Object[0]);
                continue;
            }
            new SourceFormatter(e, formatter, true).format();
        }
        ArrayList<List<Object>> sourcesList = new ArrayList<List<Object>>();
        sourcesList.add(this.getSources());
        mergeableErrors.forEach(error -> sourcesList.add(error.getSources()));
        List list2 = sourcesList.stream().map(this::trimSource).filter(list -> !list.isEmpty()).collect(Collectors.toList());
        if (!list2.isEmpty()) {
            formatter.format("\n%s\n", Messages.bold("Requested by:"));
            for (int i = 0; i < sourcesList.size(); ++i) {
                ErrorFormatter.formatSources(i + 1, (List)sourcesList.get(i), formatter);
            }
        }
    }

    @Override
    public ChildBindingAlreadySetError withSources(List<Object> newSources) {
        return new ChildBindingAlreadySetError(this.key, this.existingSources, newSources);
    }

    private List<Object> trimSource(List<Object> sources) {
        return sources.stream().filter(source2 -> !source2.equals(this.key)).collect(Collectors.toList());
    }
}

