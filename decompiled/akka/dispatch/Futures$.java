/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.compat.Future$;
import akka.japi.Function;
import akka.japi.Option$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Predef$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.runtime.BoxesRunTime;

public final class Futures$ {
    public static final Futures$ MODULE$ = new Futures$();

    public <T> Future<T> future(Callable<T> body2, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.apply((Function0<Object> & Serializable)() -> body2.call(), executor2);
    }

    public <T> Promise<T> promise() {
        return Promise$.MODULE$.apply();
    }

    public <T> Future<T> failed(Throwable exception) {
        return scala.concurrent.Future$.MODULE$.failed(exception);
    }

    public <T> Future<T> successful(T result) {
        return scala.concurrent.Future$.MODULE$.successful(result);
    }

    public <T> CompletionStage<T> failedCompletionStage(Throwable ex) {
        CompletableFuture<Object> f = CompletableFuture.completedFuture(null);
        f.obtrudeException(ex);
        return f;
    }

    public <T> Future<akka.japi.Option<T>> find(Iterable<Future<T>> futures, Function<T, Boolean> predicate, ExecutionContext executor2) {
        ExecutionContext ec = executor2;
        return Future$.MODULE$.find(package$JavaConverters$.MODULE$.IterableHasAsScala(futures).asScala(), (Function1<Object, Object> & Serializable)(T x$1) -> BoxesRunTime.boxToBoolean(Predef$.MODULE$.Boolean2boolean((Boolean)predicate.apply(x$1))), executor2).map((Function1<Option, akka.japi.Option> & Serializable)scalaOption -> Option$.MODULE$.fromScalaOption(scalaOption), ec);
    }

    public <T> Future<T> firstCompletedOf(Iterable<Future<T>> futures, ExecutionContext executor2) {
        return scala.concurrent.Future$.MODULE$.firstCompletedOf(package$JavaConverters$.MODULE$.IterableHasAsScala(futures).asScala(), executor2);
    }

    public <T, R> Future<R> fold(R zero, Iterable<Future<T>> futures, akka.japi.Function2<R, T, R> fun, ExecutionContext executor2) {
        return Future$.MODULE$.fold(package$JavaConverters$.MODULE$.IterableHasAsScala(futures).asScala(), zero, (Function2<Object, Object, Object> & Serializable)(R arg1, T arg2) -> fun.apply(arg1, arg2), executor2);
    }

    public <T, R> Future<R> reduce(Iterable<Future<T>> futures, akka.japi.Function2<R, T, R> fun, ExecutionContext executor2) {
        return Future$.MODULE$.reduce(package$JavaConverters$.MODULE$.IterableHasAsScala(futures).asScala(), (Function2<Object, Object, Object> & Serializable)(R arg1, T arg2) -> fun.apply(arg1, arg2), executor2);
    }

    public <A> Future<Iterable<A>> sequence(Iterable<Future<A>> in, ExecutionContext executor2) {
        ExecutionContext d = executor2;
        return package$JavaConverters$.MODULE$.IterableHasAsScala(in).asScala().foldLeft(scala.concurrent.Future$.MODULE$.apply((Function0<LinkedList> & Serializable)() -> new LinkedList(), d), (Function2<Future, Future, Future> & Serializable)(fr, fa) -> fr.flatMap((Function1<LinkedList, Future> & Serializable)r -> fa.map((Function1<Object, LinkedList> & Serializable)a -> {
            r.add(a);
            return r;
        }, d), d));
    }

    public <A, B> Future<Iterable<B>> traverse(Iterable<A> in, Function<A, Future<B>> fn, ExecutionContext executor2) {
        ExecutionContext d = executor2;
        return package$JavaConverters$.MODULE$.IterableHasAsScala(in).asScala().foldLeft(scala.concurrent.Future$.MODULE$.apply((Function0<LinkedList> & Serializable)() -> new LinkedList(), d), (Function2<Future, Object, Future> & Serializable)(fr, a) -> {
            Future fb = (Future)fn.apply(a);
            return fr.flatMap((Function1<LinkedList, Future> & Serializable)r -> fb.map((Function1<Object, LinkedList> & Serializable)b -> {
                r.add(b);
                return r;
            }, d), d);
        });
    }

    private Futures$() {
    }
}

