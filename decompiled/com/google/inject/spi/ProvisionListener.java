/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.Binding;

public interface ProvisionListener {
    public <T> void onProvision(ProvisionInvocation<T> var1);

    public static abstract class ProvisionInvocation<T> {
        public abstract Binding<T> getBinding();

        public abstract T provision();
    }
}

