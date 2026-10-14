/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.ConstructionProxy;
import com.google.inject.internal.ErrorsException;

interface ConstructionProxyFactory<T> {
    public ConstructionProxy<T> create() throws ErrorsException;
}

