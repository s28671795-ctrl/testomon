/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.LoaderContext;
import org.jooq.LoaderError;
import org.jooq.Record;

public interface Loader<R extends Record> {
    @NotNull
    @CheckReturnValue
    public List<LoaderError> errors();

    public int processed();

    public int executed();

    public int ignored();

    public int stored();

    @NotNull
    @CheckReturnValue
    public LoaderContext result();
}

