/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderLoadStep;
import org.jooq.LoaderRowListener;
import org.jooq.Record;
import org.jooq.Support;

public interface LoaderListenerStep<R extends Record>
extends LoaderLoadStep<R> {
    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderLoadStep<R> onRow(LoaderRowListener var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderLoadStep<R> onRowStart(LoaderRowListener var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderLoadStep<R> onRowEnd(LoaderRowListener var1);
}

