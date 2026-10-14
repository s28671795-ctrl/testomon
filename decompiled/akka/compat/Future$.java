/*
 * Decompiled with CFR 0.152.
 */
package akka.compat;

import akka.annotation.InternalApi;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.collection.IterableFactory$;
import scala.collection.IterableOnce;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Iterable$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

@InternalApi
public final class Future$ {
    public static final Future$ MODULE$ = new Future$();

    public <T, R> Future<R> fold(IterableOnce<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.foldLeft(futures.iterator().to(IterableFactory$.MODULE$.toFactory(Iterable$.MODULE$)), zero, op, executor2);
    }

    public <T, R> Future<R> fold(Iterable<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.foldLeft(futures, zero, op, executor2);
    }

    public <T, R> Future<R> reduce(IterableOnce<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.reduceLeft(futures.iterator().to(IterableFactory$.MODULE$.toFactory(Iterable$.MODULE$)), op, executor2);
    }

    public <T, R> Future<R> reduce(Iterable<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.reduceLeft(futures, op, executor2);
    }

    public <T> Future<Option<T>> find(IterableOnce<Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.find(futures.iterator().to(IterableFactory$.MODULE$.toFactory(Iterable$.MODULE$)), p, executor2);
    }

    public <T> Future<Option<T>> find(Iterable<Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.find(futures, p, executor2);
    }

    private Future$() {
    }
}

