/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderError;

public interface LoaderContext {
    @NotNull
    @CheckReturnValue
    public LoaderContext row(Object[] var1);

    @Nullable
    @CheckReturnValue
    public @Nullable Object @NotNull [] row();

    @NotNull
    @CheckReturnValue
    public List<LoaderError> errors();

    public int processed();

    public int executed();

    public int ignored();

    public int stored();
}

