/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Binding;
import org.jooq.Configuration;
import org.jooq.ContextConverter;
import org.jooq.DataType;
import org.jooq.QueryPart;

public interface Typed<T>
extends QueryPart {
    @NotNull
    public ContextConverter<?, T> getConverter();

    @NotNull
    public Binding<?, T> getBinding();

    @NotNull
    public Class<T> getType();

    @NotNull
    public DataType<T> getDataType();

    @NotNull
    public DataType<T> getDataType(Configuration var1);

    @ApiStatus.Experimental
    @NotNull
    public DataType<T> $dataType();
}

