/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;

public final class RequireExactBindingAnnotationsOption
implements Element {
    private final Object source;

    RequireExactBindingAnnotationsOption(Object source2) {
        this.source = Preconditions.checkNotNull(source2, "source");
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).requireExactBindingAnnotations();
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

