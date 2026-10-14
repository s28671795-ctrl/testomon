/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.reflect.Type;
import org.glassfish.jersey.internal.inject.Binding;

public class InstanceBinding<T>
extends Binding<T, InstanceBinding<T>> {
    private final T service;

    InstanceBinding(T service2) {
        this(service2, null);
    }

    InstanceBinding(T service2, Type contractType) {
        this.service = service2;
        if (contractType != null) {
            this.to(contractType);
        }
        this.asType(service2.getClass());
    }

    public T getService() {
        return this.service;
    }
}

