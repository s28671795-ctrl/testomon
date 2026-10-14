/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Lists;
import com.google.inject.internal.ErrorFormatter;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.InternalErrorDetail;
import com.google.inject.internal.Messages;
import com.google.inject.spi.ErrorDetail;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

final class ScopeNotFoundError
extends InternalErrorDetail<ScopeNotFoundError> {
    private final Class<? extends Annotation> scopeAnnotation;

    ScopeNotFoundError(Class<? extends Annotation> scopeAnnotation, List<Object> sources) {
        super(ErrorId.SCOPE_NOT_FOUND, String.format("No scope is bound to %s.", Messages.convert(scopeAnnotation)), sources, null);
        this.scopeAnnotation = scopeAnnotation;
    }

    @Override
    public boolean isMergeable(ErrorDetail<?> other) {
        return other instanceof ScopeNotFoundError && ((ScopeNotFoundError)other).scopeAnnotation.equals(this.scopeAnnotation);
    }

    @Override
    protected void formatDetail(List<ErrorDetail<?>> mergeableErrors, Formatter formatter) {
        ArrayList<List<Object>> sourcesSet = new ArrayList<List<Object>>();
        sourcesSet.add(this.getSources());
        mergeableErrors.stream().map(ErrorDetail::getSources).forEach(sourcesSet::add);
        formatter.format("\n%s\n", "Used at:");
        int sourceListIndex = 1;
        for (List list : sourcesSet) {
            ErrorFormatter.formatSources(sourceListIndex++, Lists.reverse(list), formatter);
        }
    }

    @Override
    public ScopeNotFoundError withSources(List<Object> newSources) {
        return new ScopeNotFoundError(this.scopeAnnotation, newSources);
    }
}

