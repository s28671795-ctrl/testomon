/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.spi.ModuleAnnotatedMethodScannerBinding;

final class ModuleAnnotatedMethodScannerProcessor
extends AbstractProcessor {
    ModuleAnnotatedMethodScannerProcessor(Errors errors2) {
        super(errors2);
    }

    @Override
    public Boolean visit(ModuleAnnotatedMethodScannerBinding command) {
        this.injector.getBindingData().addScanner(command);
        return true;
    }
}

