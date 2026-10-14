/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.inject.TypeLiteral;

public interface TypeConverter {
    public Object convert(String var1, TypeLiteral<?> var2);
}

