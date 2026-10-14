/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.protocol.commons.concurrent;

import com.hierynomus.protocol.commons.concurrent.AFuture;
import com.hierynomus.protocol.commons.concurrent.ExceptionWrapper;
import com.hierynomus.protocol.commons.concurrent.SequencedFuture;
import com.hierynomus.protocol.commons.concurrent.TransformedFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Futures {
    public static <T, E extends Throwable> T get(Future<T> future, ExceptionWrapper<E> wrapper) throws E {
        try {
            return future.get();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw wrapper.wrap(e);
        }
        catch (ExecutionException e) {
            throw wrapper.wrap(e);
        }
    }

    public static <T, E extends Throwable> T get(Future<T> future, long timeout2, TimeUnit unit, ExceptionWrapper<E> wrapper) throws E {
        try {
            return future.get(timeout2, unit);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw wrapper.wrap(e);
        }
        catch (ExecutionException | TimeoutException e) {
            throw wrapper.wrap(e);
        }
    }

    public static <T> Future<List<T>> sequence(List<Future<T>> futures) {
        return new SequencedFuture(futures);
    }

    public static <F, T> Future<T> transform(Future<F> future, AFuture.Function<F, T> f) {
        return new TransformedFuture<F, T>(future, f);
    }
}

