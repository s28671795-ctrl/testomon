/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.LoaderCSVOptionsStep;
import org.jooq.LoaderFieldMapper;
import org.jooq.LoaderListenerStep;
import org.jooq.Record;
import org.jooq.Support;

public interface LoaderRowsStep<R extends Record> {
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderListenerStep<R> fields(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderListenerStep<R> fields(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderListenerStep<R> fields(LoaderFieldMapper var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> fieldsFromSource();

    @Support
    @NotNull
    @CheckReturnValue
    public LoaderCSVOptionsStep<R> fieldsCorresponding();
}

