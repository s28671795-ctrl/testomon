/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

public interface Type {
    public Class<?> getJavaType();

    public String getName();

    public static interface InferredType
    extends Type {
    }
}

