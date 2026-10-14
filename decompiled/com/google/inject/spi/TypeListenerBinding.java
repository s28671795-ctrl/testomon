/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.TypeListener;

public final class TypeListenerBinding
implements Element {
    private final Object source;
    private final Matcher<? super TypeLiteral<?>> typeMatcher;
    private final TypeListener listener;

    TypeListenerBinding(Object source2, TypeListener listener, Matcher<? super TypeLiteral<?>> typeMatcher) {
        this.source = source2;
        this.listener = listener;
        this.typeMatcher = typeMatcher;
    }

    public TypeListener getListener() {
        return this.listener;
    }

    public Matcher<? super TypeLiteral<?>> getTypeMatcher() {
        return this.typeMatcher;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    @Override
    public <T> T acceptVisitor(ElementVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).bindListener(this.typeMatcher, this.listener);
    }
}

