/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.Function1;
import scala.concurrent.Batchable;
import scala.concurrent.BatchingExecutor;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.impl.ExecutionContextImpl$;
import scala.concurrent.impl.Promise;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

public final class ExecutionContext$ {
    public static final ExecutionContext$ MODULE$ = new ExecutionContext$();
    private static ExecutionContextExecutor global;
    private static ExecutionContextExecutor opportunistic;
    private static final Function1<Throwable, BoxedUnit> defaultReporter;
    private static volatile byte bitmap$0;

    static {
        defaultReporter = (Function1<Throwable, Object> & Serializable)x$1 -> {
            x$1.printStackTrace();
            return BoxedUnit.UNIT;
        };
    }

    private ExecutionContextExecutor global$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 1) == 0) {
                global = ExecutionContextImpl$.MODULE$.fromExecutor(null, MODULE$.defaultReporter());
                bitmap$0 = (byte)(bitmap$0 | 1);
            }
        }
        return global;
    }

    public final ExecutionContextExecutor global() {
        if ((byte)(bitmap$0 & 1) == 0) {
            return this.global$lzycompute();
        }
        return global;
    }

    private ExecutionContextExecutor opportunistic$lzycompute() {
        synchronized (this) {
            if ((byte)(bitmap$0 & 2) == 0) {
                opportunistic = new ExecutionContextExecutor(){
                    private ThreadLocal<Object> scala$concurrent$BatchingExecutor$$_tasksLocal;

                    public final void submitAsyncBatched(Runnable runnable2) {
                        BatchingExecutor.submitAsyncBatched$(this, runnable2);
                    }

                    public final void submitSyncBatched(Runnable runnable2) {
                        BatchingExecutor.submitSyncBatched$(this, runnable2);
                    }

                    public ExecutionContext prepare() {
                        return ExecutionContext.prepare$(this);
                    }

                    public final ThreadLocal<Object> scala$concurrent$BatchingExecutor$$_tasksLocal() {
                        return this.scala$concurrent$BatchingExecutor$$_tasksLocal;
                    }

                    public final void scala$concurrent$BatchingExecutor$_setter_$scala$concurrent$BatchingExecutor$$_tasksLocal_$eq(ThreadLocal<Object> x$1) {
                        this.scala$concurrent$BatchingExecutor$$_tasksLocal = x$1;
                    }

                    public final void submitForExecution(Runnable runnable2) {
                        ExecutionContext$.MODULE$.global().execute(runnable2);
                    }

                    public final void execute(Runnable runnable2) {
                        if ((!(runnable2 instanceof Promise.Transformation) || ((Promise.Transformation)runnable2).benefitsFromBatching()) && runnable2 instanceof Batchable) {
                            Object submitAsyncBatched_b = this.scala$concurrent$BatchingExecutor$$_tasksLocal().get();
                            if (submitAsyncBatched_b instanceof BatchingExecutor.AsyncBatch) {
                                ((BatchingExecutor.AsyncBatch)submitAsyncBatched_b).push(runnable2);
                                return;
                            }
                            this.submitForExecution(new BatchingExecutor.AsyncBatch(this, runnable2));
                            return;
                        }
                        ExecutionContext$.MODULE$.global().execute(runnable2);
                    }

                    public final void reportFailure(Throwable t2) {
                        ExecutionContext$.MODULE$.global().reportFailure(t2);
                    }
                    {
                        BatchingExecutor.$init$(this);
                        Statics.releaseFence();
                    }
                };
                bitmap$0 = (byte)(bitmap$0 | 2);
            }
        }
        return opportunistic;
    }

    public ExecutionContextExecutor opportunistic() {
        if ((byte)(bitmap$0 & 2) == 0) {
            return this.opportunistic$lzycompute();
        }
        return opportunistic;
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService e, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutorService(e, reporter);
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService e) {
        Function1<Throwable, BoxedUnit> fromExecutorService_reporter = this.defaultReporter();
        return ExecutionContextImpl$.MODULE$.fromExecutorService(e, fromExecutorService_reporter);
    }

    public ExecutionContextExecutor fromExecutor(Executor e, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutor(e, reporter);
    }

    public ExecutionContextExecutor fromExecutor(Executor e) {
        Function1<Throwable, BoxedUnit> fromExecutor_reporter = this.defaultReporter();
        return ExecutionContextImpl$.MODULE$.fromExecutor(e, fromExecutor_reporter);
    }

    public final Function1<Throwable, BoxedUnit> defaultReporter() {
        return defaultReporter;
    }

    private ExecutionContext$() {
    }
}

