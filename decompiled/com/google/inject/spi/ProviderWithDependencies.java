/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.Provider;
import com.google.inject.spi.HasDependencies;

public interface ProviderWithDependencies<T>
extends Provider<T>,
HasDependencies {
}

