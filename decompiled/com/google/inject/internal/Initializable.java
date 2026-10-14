/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.InternalProvisionException;

interface Initializable<T> {
    public T get() throws InternalProvisionException;
}

