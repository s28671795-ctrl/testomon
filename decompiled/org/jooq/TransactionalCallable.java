/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;
import org.jooq.Configuration;

@FunctionalInterface
public interface TransactionalCallable<T> {
    public T run(Configuration var1) throws Throwable;

    @SafeVarargs
    public static <T> TransactionalCallable<T> of(TransactionalCallable<T> ... callables) {
        return TransactionalCallable.of(Arrays.asList(callables));
    }

    public static <T> TransactionalCallable<T> of(Collection<? extends TransactionalCallable<T>> callables) {
        return configuration -> {
            Object result = null;
            for (TransactionalCallable callable : callables) {
                result = configuration.dsl().transactionResult(callable);
            }
            return result;
        };
    }

    public static <T, R> TransactionalCallable<R> of(TransactionalCallable<T>[] callables, Collector<T, ?, R> collector) {
        return TransactionalCallable.of(Arrays.asList(callables), collector);
    }

    public static <T, R> TransactionalCallable<R> of(Collection<? extends TransactionalCallable<T>> callables, Collector<T, ?, R> collector) {
        return configuration -> callables.stream().map(configuration.dsl()::transactionResult).collect(collector);
    }
}

