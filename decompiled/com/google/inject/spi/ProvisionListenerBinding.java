/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementVisitor;
import com.google.inject.spi.ProvisionListener;
import java.util.List;

public final class ProvisionListenerBinding
implements Element {
    private final Object source;
    private final Matcher<? super Binding<?>> bindingMatcher;
    private final List<ProvisionListener> listeners;

    ProvisionListenerBinding(Object source2, Matcher<? super Binding<?>> bindingMatcher, ProvisionListener[] listeners) {
        this.source = source2;
        this.bindingMatcher = bindingMatcher;
        this.listeners = ImmutableList.copyOf(listeners);
    }

    public List<ProvisionListener> getListeners() {
        return this.listeners;
    }

    public Matcher<? super Binding<?>> getBindingMatcher() {
        return this.bindingMatcher;
    }

    @Override
    public Object getSource() {
        return this.source;
    }

    public <R> R acceptVisitor(ElementVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(this.getSource()).bindListener(this.bindingMatcher, this.listeners.toArray(new ProvisionListener[this.listeners.size()]));
    }
}

