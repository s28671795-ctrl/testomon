/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Configuration;

@FunctionalInterface
public interface TransactionalRunnable {
    public void run(Configuration var1) throws Throwable;

    public static TransactionalRunnable of(TransactionalRunnable ... runnables) {
        return TransactionalRunnable.of(Arrays.asList(runnables));
    }

    public static TransactionalRunnable of(Collection<? extends TransactionalRunnable> runnables) {
        return configuration -> {
            for (TransactionalRunnable runnable2 : runnables) {
                configuration.dsl().transaction(runnable2);
            }
        };
    }
}

