/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.Dependency;

interface InternalFactory<T> {
    public T get(InternalContext var1, Dependency<?> var2, boolean var3) throws InternalProvisionException;
}

