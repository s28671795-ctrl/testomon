/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.binder;

import com.google.inject.Scope;
import java.lang.annotation.Annotation;

public interface ScopedBindingBuilder {
    public void in(Class<? extends Annotation> var1);

    public void in(Scope var1);

    public void asEagerSingleton();
}

