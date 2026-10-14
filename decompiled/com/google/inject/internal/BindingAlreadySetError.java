/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binding;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.spi.ErrorDetail;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

final class BindingAlreadySetError
extends InternalErrorDetail<BindingAlreadySetError> {
    private final Binding<?> binding;
    private final Binding<?> original;

    BindingAlreadySetError(Binding<?> binding, Binding<?> original, List<Object> sources) {
        super(ErrorId.BINDING_ALREADY_SET, String.format("%s was bound multiple times.", Messages.convert(binding.getKey())), sources, null);
        this.binding = binding;
        this.original = original;
    }

    @Override
    public boolean isMergeable(ErrorDetail<?> otherError) {
        return otherError instanceof BindingAlreadySetError && ((BindingAlreadySetError)otherError).binding.getKey().equals(this.binding.getKey());
    }

    @Override
    public void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        ArrayList<ImmutableList<Object>> sourcesList = new ArrayList<ImmutableList<Object>>();
        sourcesList.add(ImmutableList.of(this.original.getSource()));
        sourcesList.add(ImmutableList.of(this.binding.getSource()));
        sourcesList.addAll(mergeableErrors.stream().map(e -> ((BindingAlreadySetError)e).binding.getSource()).map(ImmutableList::of).collect(Collectors.toList()));
        formatter.format("\n%s\n", Messages.bold("Bound at:"));
        for (int i = 0; i < sourcesList.size(); ++i) {
            ErrorFormatter.formatSources(i + 1, (List)sourcesList.get(i), formatter);
        }
    }

    @Override
    public BindingAlreadySetError withSources(List<Object> newSources) {
        return new BindingAlreadySetError(this.binding, this.original, newSources);
    }
}

