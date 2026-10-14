/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.annotation.InternalStableApi;
import akka.dispatch.internal.SameThreadExecutionContext$;
import akka.japi.Procedure;
import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.runtime.BoxedUnit;

public final class ExecutionContexts$ {
    public static final ExecutionContexts$ MODULE$ = new ExecutionContexts$();
    @InternalStableApi
    private static final ExecutionContext parasitic = SameThreadExecutionContext$.MODULE$.apply();

    public ExecutionContextExecutor fromExecutor(Executor executor2) {
        return ExecutionContext$.MODULE$.fromExecutor(executor2);
    }

    public ExecutionContextExecutor fromExecutor(Executor executor2, Procedure<Throwable> errorReporter) {
        return ExecutionContext$.MODULE$.fromExecutor(executor2, (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)param2 -> {
            errorReporter.apply(param2);
            return BoxedUnit.UNIT;
        });
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2) {
        return ExecutionContext$.MODULE$.fromExecutorService(executorService2);
    }

    public ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2, Procedure<Throwable> errorReporter) {
        return ExecutionContext$.MODULE$.fromExecutorService(executorService2, (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)param2 -> {
            errorReporter.apply(param2);
            return BoxedUnit.UNIT;
        });
    }

    public ExecutionContextExecutor global() {
        return ExecutionContext$.MODULE$.global();
    }

    public ExecutionContext parasitic() {
        return parasitic;
    }

    private ExecutionContexts$() {
    }
}

