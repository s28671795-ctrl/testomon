/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.internal.Errors;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;

public final class ModuleAnnotatedMethodScannerBinding
implements Element {
    private final Object source;
    private final ModuleAnnotatedMethodScanner scanner;

    public ModuleAnnotatedMethodScannerBinding(Object source2, ModuleAnnotatedMethodScanner scanner) {
        this.source = Preconditions.checkNotNull(source2, "source");
        this.scanner = Preconditions.checkNotNull(scanner, "scanner");
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public ModuleAnnotatedMethodScanner getScanner() {
        return this.scanner;
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).scanModulesForAnnotatedMethods(this.scanner);
    }

    public String toString() {
        return this.scanner + " which scans for " + this.scanner.annotationClasses() + " (bound at " + Errors.convert(this.source) + ")";
    }
}

