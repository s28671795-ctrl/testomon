/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Initializable;

final class Initializables {
    Initializables() {
    }

    static <T> Initializable<T> of(final T instance) {
        return new Initializable<T>(){

            @Override
            public T get() {
                return instance;
            }

            public String toString() {
                return String.valueOf(instance);
            }
        };
    }
}

