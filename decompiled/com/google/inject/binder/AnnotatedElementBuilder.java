/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.binder;

import java.lang.annotation.Annotation;

public interface AnnotatedElementBuilder {
    public void annotatedWith(Class<? extends Annotation> var1);

    public void annotatedWith(Annotation var1);
}

