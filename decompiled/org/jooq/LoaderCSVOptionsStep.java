/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderListenerStep;
import org.jooq.Record;
import org.jooq.Support;

public interface LoaderCSVOptionsStep<R extends Record>
extends LoaderListenerStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> ignoreRows(int var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> quote(char var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> separator(char var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> nullString(String var1);
}

