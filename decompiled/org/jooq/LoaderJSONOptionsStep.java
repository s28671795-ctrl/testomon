/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderListenerStep;
import org.jooq.Record;
import org.jooq.Support;

public interface LoaderJSONOptionsStep<R extends Record>
extends LoaderListenerStep<R> {
    @Deprecated
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderJSONOptionsStep<R> ignoreRows(int var1);
}

