/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.spi.InjectionPoint;

interface SingleMemberInjector {
    public void inject(InternalContext var1, Object var2) throws InternalProvisionException;

    public InjectionPoint getInjectionPoint();
}

