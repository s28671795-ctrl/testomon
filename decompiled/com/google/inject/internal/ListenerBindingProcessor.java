/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.spi.ProvisionListenerBinding;
import com.google.inject.spi.TypeListenerBinding;

final class ListenerBindingProcessor
extends AbstractProcessor {
    ListenerBindingProcessor(Errors errors2) {
        super(errors2);
    }

    @Override
    public Boolean visit(TypeListenerBinding binding) {
        this.injector.getBindingData().addTypeListener(binding);
        return true;
    }

    @Override
    public Boolean visit(ProvisionListenerBinding binding) {
        this.injector.getBindingData().addProvisionListener(binding);
        return true;
    }
}

