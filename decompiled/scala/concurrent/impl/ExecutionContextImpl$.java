/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.impl.ExecutionContextImpl;
import scala.runtime.BoxedUnit;

public final class ExecutionContextImpl$ {
    public static final ExecutionContextImpl$ MODULE$ = new ExecutionContextImpl$();

    public ExecutionContextExecutorService createDefaultExecutorService(Function1<Throwable, BoxedUnit> reporter) {
        int desiredParallelism = Math.min(Math.max(ExecutionContextImpl$.getInt$1("scala.concurrent.context.minThreads", "1"), ExecutionContextImpl$.getInt$1("scala.concurrent.context.numThreads", "x1")), ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxThreads", "x1"));
        ExecutionContextImpl.DefaultThreadFactory threadFactory = new ExecutionContextImpl.DefaultThreadFactory(true, ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxExtraThreads", "256"), "scala-execution-context-global", (thread2, cause) -> reporter.apply(cause));
        return new ExecutionContextExecutorService(desiredParallelism, threadFactory){

            public ExecutionContext prepare() {
                return ExecutionContext.prepare$(this);
            }

            public final void reportFailure(Throwable cause) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.getUncaughtExceptionHandler();
                if (uncaughtExceptionHandler == null) {
                    return;
                }
                uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), cause);
            }
        };
    }

    public ExecutionContextExecutor fromExecutor(Executor e, Function1<Throwable, BoxedUnit> reporter) {
        if (e == null) {
            int createDefaultExecutorService_desiredParallelism = Math.min(Math.max(ExecutionContextImpl$.getInt$1("scala.concurrent.context.minThreads", "1"), ExecutionContextImpl$.getInt$1("scala.concurrent.context.numThreads", "x1")), ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxThreads", "x1"));
            ExecutionContextImpl.DefaultThreadFactory createDefaultExecutorService_threadFactory = new ExecutionContextImpl.DefaultThreadFactory(true, ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxExtraThreads", "256"), "scala-execution-context-global", (thread2, cause) -> reporter.apply(cause));
            return new /* invalid duplicate definition of identical inner class */;
        }
        return new ExecutionContextImpl(e, reporter);
    }

    public Function1<Throwable, BoxedUnit> fromExecutor$default$2() {
        return ExecutionContext$.MODULE$.defaultReporter();
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService es, Function1<Throwable, BoxedUnit> reporter) {
        if (es == null) {
            int createDefaultExecutorService_desiredParallelism = Math.min(Math.max(ExecutionContextImpl$.getInt$1("scala.concurrent.context.minThreads", "1"), ExecutionContextImpl$.getInt$1("scala.concurrent.context.numThreads", "x1")), ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxThreads", "x1"));
            ExecutionContextImpl.DefaultThreadFactory createDefaultExecutorService_threadFactory = new ExecutionContextImpl.DefaultThreadFactory(true, ExecutionContextImpl$.getInt$1("scala.concurrent.context.maxExtraThreads", "256"), "scala-execution-context-global", (thread2, cause) -> reporter.apply(cause));
            return new /* invalid duplicate definition of identical inner class */;
        }
        return new ExecutionContextExecutorService(es, reporter){

            private final ExecutorService asExecutorService() {
                return (ExecutorService)this.executor();
            }

            public final void shutdown() {
                ((ExecutorService)this.executor()).shutdown();
            }

            public final List<Runnable> shutdownNow() {
                return ((ExecutorService)this.executor()).shutdownNow();
            }

            public final boolean isShutdown() {
                return ((ExecutorService)this.executor()).isShutdown();
            }

            public final boolean isTerminated() {
                return ((ExecutorService)this.executor()).isTerminated();
            }

            public final boolean awaitTermination(long l, TimeUnit timeUnit) {
                return ((ExecutorService)this.executor()).awaitTermination(l, timeUnit);
            }

            public final <T> Future<T> submit(Callable<T> callable) {
                return ((ExecutorService)this.executor()).submit(callable);
            }

            public final <T> Future<T> submit(Runnable runnable2, T t2) {
                return ((ExecutorService)this.executor()).submit(runnable2, t2);
            }

            public final Future<?> submit(Runnable runnable2) {
                return ((ExecutorService)this.executor()).submit(runnable2);
            }

            public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables) {
                return ((ExecutorService)this.executor()).invokeAll(callables);
            }

            public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
                return ((ExecutorService)this.executor()).invokeAll(callables, l, timeUnit);
            }

            public final <T> T invokeAny(Collection<? extends Callable<T>> callables) {
                return ((ExecutorService)this.executor()).invokeAny(callables);
            }

            public final <T> T invokeAny(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
                return ((ExecutorService)this.executor()).invokeAny(callables, l, timeUnit);
            }
        };
    }

    public Function1<Throwable, BoxedUnit> fromExecutorService$default$2() {
        return ExecutionContext$.MODULE$.defaultReporter();
    }

    private static final int getInt$1(String name, String string) {
        String string2;
        try {
            string2 = System.getProperty(name, string);
        }
        catch (SecurityException securityException) {
            string2 = string;
        }
        String string3 = string2;
        switch (string3 == null ? 0 : string3.hashCode()) {
            default: 
        }
        if (string3.charAt(0) == 'x') {
            double d = (double)Runtime.getRuntime().availableProcessors() * Double.parseDouble(string3.substring(1));
            return (int)Math.ceil(d);
        }
        return Integer.parseInt(string3);
    }

    private ExecutionContextImpl$() {
    }
}

