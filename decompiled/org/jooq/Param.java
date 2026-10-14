/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ParamMode;
import org.jooq.ParamOrVariable;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataTypeException;

public interface Param<T>
extends ParamOrVariable<T> {
    @Nullable
    public String getParamName();

    @Nullable
    public T getValue();

    @Deprecated(forRemoval=true, since="3.8")
    public void setValue(T var1);

    @Deprecated(forRemoval=true, since="3.8")
    public void setConverted(Object var1) throws DataTypeException;

    @Deprecated(forRemoval=true, since="3.8")
    public void setInline(boolean var1);

    public boolean isInline();

    @NotNull
    public ParamType getParamType();

    @NotNull
    public ParamMode getParamMode();

    @ApiStatus.Experimental
    public T $value();

    @ApiStatus.Experimental
    @NotNull
    public Param<T> $value(T var1);

    @ApiStatus.Experimental
    public boolean $inline();

    @ApiStatus.Experimental
    @NotNull
    public Param<T> $inline(boolean var1);
}

