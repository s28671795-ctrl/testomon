/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
@BindingAnnotation
@interface Element {
    public String setName();

    public int uniqueId();

    public Type type();

    public String keyType();

    public static enum Type {
        MAPBINDER,
        MULTIBINDER;

    }
}

