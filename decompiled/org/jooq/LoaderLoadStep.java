/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.IOException;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.Loader;
import org.jooq.Record;
import org.jooq.Support;

public interface LoaderLoadStep<R extends Record> {
    @Support
    @NotNull
    @Blocking
    public Loader<R> execute() throws IOException;
}

