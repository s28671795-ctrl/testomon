/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.util.AsyncUtil;
import io.javalin.util.ConcurrencyUtil;
import io.javalin.util.function.ThrowingRunnable;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J<\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u00060\u0012j\u0002`\u00130\u0011Ji\u0010\u0014\u001a\u00020\u0015*\u00020\u00152\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\b0\u00172\u0014\b\u0002\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\b0\u00172\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\b0\u00172\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\b0\u0017H\u0000\u00a2\u0006\u0002\b\u001bJ\u0011\u0010\u001c\u001a\u00020\u001d*\u00020\nH\u0000\u00a2\u0006\u0002\b\u001eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u001f"}, d2={"Lio/javalin/http/util/AsyncUtil;", "", "()V", "defaultExecutor", "Ljava/util/concurrent/ExecutorService;", "getDefaultExecutor", "()Ljava/util/concurrent/ExecutorService;", "submitAsyncTask", "", "context", "Lio/javalin/http/Context;", "executor", "timeout", "", "onTimeout", "Ljava/lang/Runnable;", "task", "Lio/javalin/util/function/ThrowingRunnable;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "addListener", "Ljakarta/servlet/AsyncContext;", "onComplete", "Lkotlin/Function1;", "Ljakarta/servlet/AsyncEvent;", "onError", "onStartAsync", "addListener$javalin", "isAsync", "", "isAsync$javalin", "javalin"})
@SourceDebugExtension(value={"SMAP\nAsyncUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AsyncUtil.kt\nio/javalin/http/util/AsyncUtil\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,59:1\n1#2:60\n*E\n"})
public final class AsyncUtil {
    @NotNull
    public static final AsyncUtil INSTANCE = new AsyncUtil();
    @NotNull
    private static final ExecutorService defaultExecutor = ConcurrencyUtil.executorService("JavalinDefaultAsyncThreadPool");

    private AsyncUtil() {
    }

    @NotNull
    public final ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    public final void submitAsyncTask(@NotNull Context context, @Nullable ExecutorService executor2, long timeout2, @Nullable Runnable onTimeout, @NotNull ThrowingRunnable<Exception> task) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(task, "task");
        context.future(() -> AsyncUtil.submitAsyncTask$lambda$4(context, executor2, task, timeout2, onTimeout));
    }

    public final boolean isAsync$javalin(@NotNull Context $this$isAsync) {
        Intrinsics.checkNotNullParameter($this$isAsync, "<this>");
        return $this$isAsync.req().isAsyncStarted();
    }

    @NotNull
    public final AsyncContext addListener$javalin(@NotNull AsyncContext $this$addListener, @NotNull Function1<? super AsyncEvent, Unit> onComplete, @NotNull Function1<? super AsyncEvent, Unit> onError2, @NotNull Function1<? super AsyncEvent, Unit> onStartAsync, @NotNull Function1<? super AsyncEvent, Unit> onTimeout) {
        AsyncContext asyncContext;
        Intrinsics.checkNotNullParameter($this$addListener, "<this>");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        Intrinsics.checkNotNullParameter(onError2, "onError");
        Intrinsics.checkNotNullParameter(onStartAsync, "onStartAsync");
        Intrinsics.checkNotNullParameter(onTimeout, "onTimeout");
        AsyncContext $this$addListener_u24lambda_u245 = asyncContext = $this$addListener;
        boolean bl = false;
        $this$addListener_u24lambda_u245.addListener(new AsyncListener(onComplete, onError2, onStartAsync, onTimeout){
            final /* synthetic */ Function1<AsyncEvent, Unit> $onComplete;
            final /* synthetic */ Function1<AsyncEvent, Unit> $onError;
            final /* synthetic */ Function1<AsyncEvent, Unit> $onStartAsync;
            final /* synthetic */ Function1<AsyncEvent, Unit> $onTimeout;
            {
                this.$onComplete = $onComplete;
                this.$onError = $onError;
                this.$onStartAsync = $onStartAsync;
                this.$onTimeout = $onTimeout;
            }

            public void onComplete(@NotNull AsyncEvent event) {
                Intrinsics.checkNotNullParameter(event, "event");
                this.$onComplete.invoke(event);
            }

            public void onError(@NotNull AsyncEvent event) {
                Intrinsics.checkNotNullParameter(event, "event");
                this.$onError.invoke(event);
            }

            public void onStartAsync(@NotNull AsyncEvent event) {
                Intrinsics.checkNotNullParameter(event, "event");
                this.$onStartAsync.invoke(event);
            }

            public void onTimeout(@NotNull AsyncEvent event) {
                Intrinsics.checkNotNullParameter(event, "event");
                this.$onTimeout.invoke(event);
            }
        });
        return asyncContext;
    }

    public static /* synthetic */ AsyncContext addListener$javalin$default(AsyncUtil asyncUtil, AsyncContext asyncContext, Function1 function1, Function1 function12, Function1 function13, Function1 function14, int n, Object object) {
        if ((n & 1) != 0) {
            function1 = addListener.1.INSTANCE;
        }
        if ((n & 2) != 0) {
            function12 = addListener.2.INSTANCE;
        }
        if ((n & 4) != 0) {
            function13 = addListener.3.INSTANCE;
        }
        if ((n & 8) != 0) {
            function14 = addListener.4.INSTANCE;
        }
        return asyncUtil.addListener$javalin(asyncContext, function1, function12, function13, function14);
    }

    private static final void submitAsyncTask$lambda$4$lambda$0(ThrowingRunnable $task) {
        Intrinsics.checkNotNullParameter($task, "$task");
        $task.run();
    }

    private static final Void submitAsyncTask$lambda$4$lambda$3$lambda$2(Runnable $onTimeout, Throwable exception) {
        TimeoutException timeoutException = exception instanceof TimeoutException ? (TimeoutException)exception : null;
        if (timeoutException == null) {
            Throwable throwable = exception;
            Throwable throwable2 = throwable != null ? throwable.getCause() : null;
            timeoutException = throwable2 instanceof TimeoutException ? (TimeoutException)throwable2 : null;
            if (timeoutException == null) {
                Intrinsics.checkNotNullExpressionValue(exception, "exception");
                throw exception;
            }
        }
        $onTimeout.run();
        return null;
    }

    private static final CompletableFuture submitAsyncTask$lambda$4(Context $context, ExecutorService $executor, ThrowingRunnable $task, long $timeout, Runnable $onTimeout) {
        Intrinsics.checkNotNullParameter($context, "$context");
        Intrinsics.checkNotNullParameter($task, "$task");
        $context.req().getAsyncContext().setTimeout(0L);
        Runnable runnable2 = () -> AsyncUtil.submitAsyncTask$lambda$4$lambda$0($task);
        ExecutorService executorService2 = $executor;
        if (executorService2 == null) {
            executorService2 = defaultExecutor;
        }
        CompletableFuture<Void> it = CompletableFuture.runAsync(runnable2, executorService2);
        boolean bl = false;
        it = $timeout > 0L ? it.orTimeout($timeout, TimeUnit.MILLISECONDS) : it;
        boolean bl2 = false;
        Runnable runnable3 = $onTimeout;
        return runnable3 == null ? it : it.exceptionally(arg_0 -> AsyncUtil.submitAsyncTask$lambda$4$lambda$3$lambda$2(runnable3, arg_0));
    }
}

