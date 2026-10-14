/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.reactivestreams.Publisher;

@FunctionalInterface
public interface TransactionalPublishable<T> {
    @NotNull
    public Publisher<T> run(Configuration var1);
}

