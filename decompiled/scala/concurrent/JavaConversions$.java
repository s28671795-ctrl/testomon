/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;

public final class JavaConversions$ {
    public static final JavaConversions$ MODULE$ = new JavaConversions$();

    public ExecutionContextExecutorService asExecutionContext(ExecutorService exec) {
        return ExecutionContext$.MODULE$.fromExecutorService(exec);
    }

    public ExecutionContextExecutor asExecutionContext(Executor exec) {
        return ExecutionContext$.MODULE$.fromExecutor(exec);
    }

    private JavaConversions$() {
    }
}

