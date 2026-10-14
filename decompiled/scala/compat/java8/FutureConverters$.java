/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.java8.FuturesConvertersImpl;
import scala.concurrent.java8.FuturesConvertersImplCompat$;
import scala.runtime.BoxedUnit;

public final class FutureConverters$ {
    public static final FutureConverters$ MODULE$ = new FutureConverters$();

    public <T> CompletionStage<T> toJava(Future<T> f) {
        CompletionStage completionStage;
        Future<T> future = f;
        if (future instanceof FuturesConvertersImpl.P) {
            FuturesConvertersImpl.P p = (FuturesConvertersImpl.P)future;
            completionStage = p.wrapped();
        } else {
            FuturesConvertersImpl.CF<T> cf = new FuturesConvertersImpl.CF<T>(f);
            ExecutionContext$parasitic$ ec = FuturesConvertersImplCompat$.MODULE$.InternalCallbackExecutor();
            f.onComplete(cf, ec);
            completionStage = cf;
        }
        return completionStage;
    }

    public <T> Future<T> toScala(CompletionStage<T> cs) {
        Future future;
        CompletionStage<T> completionStage = cs;
        if (completionStage instanceof FuturesConvertersImpl.CF) {
            FuturesConvertersImpl.CF cF = (FuturesConvertersImpl.CF)completionStage;
            future = cF.wrapped();
        } else {
            FuturesConvertersImpl.P<T> p = new FuturesConvertersImpl.P<T>(cs);
            cs.whenComplete(p);
            future = p.future();
        }
        return future;
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService e, Consumer<Throwable> reporter) {
        return ExecutionContext$.MODULE$.fromExecutorService(e, (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)x$1 -> {
            reporter.accept(x$1);
            return BoxedUnit.UNIT;
        });
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService e) {
        return ExecutionContext$.MODULE$.fromExecutorService(e, ExecutionContext$.MODULE$.defaultReporter());
    }

    public ExecutionContextExecutor fromExecutor(Executor e, Consumer<Throwable> reporter) {
        return ExecutionContext$.MODULE$.fromExecutor(e, (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)x$1 -> {
            reporter.accept(x$1);
            return BoxedUnit.UNIT;
        });
    }

    public ExecutionContextExecutor fromExecutor(Executor e) {
        return ExecutionContext$.MODULE$.fromExecutor(e, ExecutionContext$.MODULE$.defaultReporter());
    }

    public ExecutionContext globalExecutionContext() {
        return ExecutionContext$.MODULE$.global();
    }

    public <T> Promise<T> promise() {
        return Promise$.MODULE$.apply();
    }

    public <T> Promise<T> keptPromise(T v) {
        return Promise$.MODULE$.successful(v);
    }

    public <T> Promise<T> failedPromise(Throwable ex) {
        return Promise$.MODULE$.failed(ex);
    }

    public <T> Future<T> FutureOps(Future<T> f) {
        return f;
    }

    public <T> CompletionStage<T> CompletionStageOps(CompletionStage<T> cs) {
        return cs;
    }

    private FutureConverters$() {
    }
}

