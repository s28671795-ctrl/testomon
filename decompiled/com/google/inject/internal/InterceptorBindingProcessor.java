/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InternalFlags;
import com.google.inject.spi.InterceptorBinding;

final class InterceptorBindingProcessor
extends AbstractProcessor {
    InterceptorBindingProcessor(Errors errors2) {
        super(errors2);
    }

    @Override
    public Boolean visit(InterceptorBinding command) {
        if (InternalFlags.isBytecodeGenEnabled()) {
            this.injector.getBindingData().addInterceptorBinding(command);
        } else {
            this.errors.aopDisabled(command);
        }
        return true;
    }
}

