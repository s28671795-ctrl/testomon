/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.inject.Scope;
import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.Errors;
import com.google.inject.spi.ScopeBinding;
import java.lang.annotation.Annotation;

final class ScopeBindingProcessor
extends AbstractProcessor {
    ScopeBindingProcessor(Errors errors2) {
        super(errors2);
    }

    @Override
    public Boolean visit(ScopeBinding command) {
        ScopeBinding existing;
        Scope scope = Preconditions.checkNotNull(command.getScope(), "scope");
        Class<? extends Annotation> annotationType = Preconditions.checkNotNull(command.getAnnotationType(), "annotation type");
        if (!Annotations.isScopeAnnotation(annotationType)) {
            this.errors.missingScopeAnnotation(annotationType);
        }
        if (!Annotations.isRetainedAtRuntime(annotationType)) {
            this.errors.missingRuntimeRetention(annotationType);
        }
        if ((existing = this.injector.getBindingData().getScopeBinding(annotationType)) != null) {
            if (!scope.equals(existing.getScope())) {
                this.errors.duplicateScopes(existing, annotationType, scope);
            }
        } else {
            this.injector.getBindingData().putScopeBinding(annotationType, command);
        }
        return true;
    }
}

