/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jooq.Publisher;
import org.jooq.exception.DataAccessException;

public interface Batch
extends Serializable,
Publisher<Integer> {
    @Blocking
    public int @NotNull [] execute() throws DataAccessException;

    @NotNull
    public CompletionStage<int[]> executeAsync();

    @NotNull
    public CompletionStage<int[]> executeAsync(Executor var1);

    public int size();
}

