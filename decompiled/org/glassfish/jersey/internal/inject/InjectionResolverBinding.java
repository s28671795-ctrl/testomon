/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import org.glassfish.jersey.internal.inject.Binding;
import org.glassfish.jersey.internal.inject.InjectionResolver;

public class InjectionResolverBinding<T extends InjectionResolver>
extends Binding<T, InjectionResolverBinding<T>> {
    private final T resolver;

    InjectionResolverBinding(T resolver2) {
        this.resolver = resolver2;
    }

    public T getResolver() {
        return this.resolver;
    }
}

