/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.Batch;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.ExecutorProviderCompletionStage;
import org.jooq.impl.Tools;

abstract class AbstractBatch
implements Batch {
    final Configuration configuration;
    final DSLContext dsl;

    AbstractBatch(Configuration configuration) {
        this.configuration = configuration;
        this.dsl = DSL.using(configuration);
    }

    @Override
    public final CompletionStage<int[]> executeAsync() {
        return this.executeAsync(this.configuration.executorProvider().provide());
    }

    @Override
    public final CompletionStage<int[]> executeAsync(Executor executor2) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(this::execute), executor2), () -> executor2);
    }
}

