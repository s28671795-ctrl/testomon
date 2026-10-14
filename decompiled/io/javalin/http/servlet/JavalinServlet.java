/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.RequestLogger;
import io.javalin.http.servlet.ErrorMapper;
import io.javalin.http.servlet.ExceptionMapper;
import io.javalin.http.servlet.JavalinServlet;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.http.servlet.SubmitOrder;
import io.javalin.http.servlet.Task;
import io.javalin.http.servlet.TaskHandler;
import io.javalin.http.servlet.TaskInitializer;
import io.javalin.http.util.AsyncUtil;
import io.javalin.http.util.ETagGenerator;
import io.javalin.routing.PathMatcher;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\f\u0010\u0019\u001a\u00020\u0014*\u00020\u001aH\u0002J'\u0010\u001b\u001a\u0004\u0018\u0001H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u001a2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001eH\u0002\u00a2\u0006\u0002\u0010\u001fJ\f\u0010 \u001a\u00020\u0014*\u00020\u001aH\u0002J\u0014\u0010!\u001a\n #*\u0004\u0018\u00010\"0\"*\u00020\u001aH\u0002J\f\u0010$\u001a\u00020\u0014*\u00020\u001aH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006%"}, d2={"Lio/javalin/http/servlet/JavalinServlet;", "Ljakarta/servlet/http/HttpServlet;", "cfg", "Lio/javalin/config/JavalinConfig;", "(Lio/javalin/config/JavalinConfig;)V", "getCfg", "()Lio/javalin/config/JavalinConfig;", "errorMapper", "Lio/javalin/http/servlet/ErrorMapper;", "getErrorMapper", "()Lio/javalin/http/servlet/ErrorMapper;", "exceptionMapper", "Lio/javalin/http/servlet/ExceptionMapper;", "getExceptionMapper", "()Lio/javalin/http/servlet/ExceptionMapper;", "matcher", "Lio/javalin/routing/PathMatcher;", "getMatcher", "()Lio/javalin/routing/PathMatcher;", "service", "", "request", "Ljakarta/servlet/http/HttpServletRequest;", "response", "Ljakarta/servlet/http/HttpServletResponse;", "handleSync", "Lio/javalin/http/servlet/JavalinServletContext;", "handleTask", "R", "handler", "Lio/javalin/http/servlet/TaskHandler;", "(Lio/javalin/http/servlet/JavalinServletContext;Lio/javalin/http/servlet/TaskHandler;)Ljava/lang/Object;", "handleUserFuture", "startAsyncAndAddDefaultTimeoutListeners", "Ljakarta/servlet/AsyncContext;", "kotlin.jvm.PlatformType", "writeResponseAndLog", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinServlet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinServlet.kt\nio/javalin/http/servlet/JavalinServlet\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,113:1\n1855#2,2:114\n1#3:116\n*S KotlinDebug\n*F\n+ 1 JavalinServlet.kt\nio/javalin/http/servlet/JavalinServlet\n*L\n39#1:114,2\n*E\n"})
public final class JavalinServlet
extends HttpServlet {
    @NotNull
    private final JavalinConfig cfg;
    @NotNull
    private final PathMatcher matcher;
    @NotNull
    private final ExceptionMapper exceptionMapper;
    @NotNull
    private final ErrorMapper errorMapper;

    public JavalinServlet(@NotNull JavalinConfig cfg) {
        Intrinsics.checkNotNullParameter(cfg, "cfg");
        this.cfg = cfg;
        this.matcher = new PathMatcher();
        this.exceptionMapper = new ExceptionMapper(this.cfg);
        this.errorMapper = new ErrorMapper();
    }

    @NotNull
    public final JavalinConfig getCfg() {
        return this.cfg;
    }

    @NotNull
    public final PathMatcher getMatcher() {
        return this.matcher;
    }

    @NotNull
    public final ExceptionMapper getExceptionMapper() {
        return this.exceptionMapper;
    }

    @NotNull
    public final ErrorMapper getErrorMapper() {
        return this.errorMapper;
    }

    @Override
    protected void service(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(response, "response");
        try {
            JavalinConfig javalinConfig = this.cfg;
            JavalinServletContext ctx = new JavalinServletContext(javalinConfig, null, false, null, request, response, null, null, null, null, null, null, null, null, null, 32718, null);
            Function2 submitTask2 = new Function2<SubmitOrder, Task, Unit>(ctx){
                final /* synthetic */ JavalinServletContext $ctx;
                {
                    this.$ctx = $ctx;
                    super(2);
                }

                public final void invoke(@NotNull SubmitOrder order, @NotNull Task task) {
                    Intrinsics.checkNotNullParameter((Object)((Object)order), "order");
                    Intrinsics.checkNotNullParameter(task, "task");
                    switch (service.submitTask.WhenMappings.$EnumSwitchMapping$0[order.ordinal()]) {
                        case 1: {
                            this.$ctx.getTasks().offerFirst(task);
                            break;
                        }
                        case 2: {
                            this.$ctx.getTasks().add(task);
                        }
                    }
                }
            };
            String requestUri = StringsKt.removePrefix(ctx.path(), (CharSequence)ctx.contextPath());
            Iterable $this$forEach$iv = this.cfg.pvt.servletRequestLifecycle;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                TaskInitializer it = (TaskInitializer)element$iv;
                boolean bl = false;
                it.createTasks(submitTask2, this, (Context)ctx, requestUri);
            }
            this.handleSync(ctx);
        }
        catch (Throwable throwable) {
            this.exceptionMapper.handleUnexpectedThrowable$javalin(response, throwable);
        }
    }

    private final void handleSync(JavalinServletContext $this$handleSync) {
        while ($this$handleSync.getUserFutureSupplier$javalin() == null && !((Collection)$this$handleSync.getTasks()).isEmpty()) {
            Task task = $this$handleSync.getTasks().poll();
            if ($this$handleSync.getExceptionOccurred() && task.getSkipIfExceptionOccurred()) continue;
            this.handleTask($this$handleSync, task.getHandler());
        }
        if ($this$handleSync.getUserFutureSupplier$javalin() != null) {
            this.handleUserFuture($this$handleSync);
        } else {
            this.writeResponseAndLog($this$handleSync);
        }
    }

    private final void handleUserFuture(JavalinServletContext $this$handleUserFuture) {
        Supplier<CompletableFuture<?>> supplier;
        Supplier<CompletableFuture<?>> supplier2 = $this$handleUserFuture.getUserFutureSupplier$javalin();
        Intrinsics.checkNotNull(supplier2);
        Supplier<CompletableFuture<?>> it = supplier = supplier2;
        boolean bl = false;
        $this$handleUserFuture.setUserFutureSupplier$javalin(null);
        Supplier<CompletableFuture<?>> userFutureSupplier = supplier;
        if (!AsyncUtil.INSTANCE.isAsync$javalin($this$handleUserFuture)) {
            this.startAsyncAndAddDefaultTimeoutListeners($this$handleUserFuture);
        }
        CompletableFuture completableFuture = this.handleTask($this$handleUserFuture, () -> JavalinServlet.handleUserFuture$lambda$2(userFutureSupplier));
        if (completableFuture == null) {
            this.handleSync($this$handleUserFuture);
            return;
        }
        CompletableFuture userFuture = completableFuture;
        AsyncContext asyncContext = $this$handleUserFuture.req().getAsyncContext();
        Intrinsics.checkNotNullExpressionValue(asyncContext, "req().asyncContext");
        AsyncUtil.addListener$javalin$default(AsyncUtil.INSTANCE, asyncContext, null, null, null, new Function1<AsyncEvent, Unit>(userFuture){
            final /* synthetic */ CompletableFuture<?> $userFuture;
            {
                this.$userFuture = $userFuture;
                super(1);
            }

            public final void invoke(@NotNull AsyncEvent it) {
                Intrinsics.checkNotNullParameter(it, "it");
                this.$userFuture.cancel(true);
            }
        }, 7, null);
        ((CompletableFuture)userFuture.thenApply(arg_0 -> JavalinServlet.handleUserFuture$lambda$3(this, $this$handleUserFuture, arg_0))).exceptionally(arg_0 -> JavalinServlet.handleUserFuture$lambda$4(this, $this$handleUserFuture, arg_0));
    }

    private final AsyncContext startAsyncAndAddDefaultTimeoutListeners(JavalinServletContext $this$startAsyncAndAddDefaultTimeoutListeners) {
        AsyncContext asyncContext;
        AsyncContext it = asyncContext = $this$startAsyncAndAddDefaultTimeoutListeners.req().startAsync();
        boolean bl = false;
        it.setTimeout(this.cfg.http.asyncTimeout);
        Intrinsics.checkNotNullExpressionValue(it, "it");
        AsyncUtil.addListener$javalin$default(AsyncUtil.INSTANCE, it, null, null, null, new Function1<AsyncEvent, Unit>($this$startAsyncAndAddDefaultTimeoutListeners, this){
            final /* synthetic */ JavalinServletContext $this_startAsyncAndAddDefaultTimeoutListeners;
            final /* synthetic */ JavalinServlet this$0;
            {
                this.$this_startAsyncAndAddDefaultTimeoutListeners = $receiver;
                this.this$0 = $receiver2;
                super(1);
            }

            public final void invoke(@NotNull AsyncEvent it) {
                Intrinsics.checkNotNullParameter(it, "it");
                this.$this_startAsyncAndAddDefaultTimeoutListeners.status(HttpStatus.INTERNAL_SERVER_ERROR);
                this.this$0.getErrorMapper().handle(this.$this_startAsyncAndAddDefaultTimeoutListeners.statusCode(), this.$this_startAsyncAndAddDefaultTimeoutListeners);
                if (this.$this_startAsyncAndAddDefaultTimeoutListeners.resultInputStream() == null) {
                    this.$this_startAsyncAndAddDefaultTimeoutListeners.result(HttpStatus.REQUEST_TIMEOUT.getMessage());
                }
                JavalinServlet.access$writeResponseAndLog(this.this$0, this.$this_startAsyncAndAddDefaultTimeoutListeners);
            }
        }, 7, null);
        return asyncContext;
    }

    private final <R> R handleTask(JavalinServletContext $this$handleTask, TaskHandler<R> handler) {
        R r;
        try {
            r = handler.handle();
        }
        catch (Throwable throwable) {
            $this$handleTask.setExceptionOccurred(true);
            $this$handleTask.setUserFutureSupplier$javalin(null);
            $this$handleTask.getTasks().offerFirst(new Task(false, () -> JavalinServlet.handleTask$lambda$6(this, $this$handleTask, throwable)));
            r = null;
        }
        return r;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void writeResponseAndLog(JavalinServletContext $this$writeResponseAndLog) {
        try {
            if ($this$writeResponseAndLog.getResponseWritten().getAndSet(true)) {
                return;
            }
            InputStream inputStream2 = $this$writeResponseAndLog.resultInputStream();
            if (inputStream2 != null) {
                Closeable closeable = inputStream2;
                Throwable throwable = null;
                try {
                    InputStream resultStream = (InputStream)closeable;
                    boolean bl = false;
                    boolean etagWritten = ETagGenerator.INSTANCE.tryWriteEtagAndClose(this.cfg.http.generateEtags, $this$writeResponseAndLog, resultStream);
                    if (!etagWritten) {
                        ByteStreamsKt.copyTo(resultStream, $this$writeResponseAndLog.outputStream(), 4096);
                    }
                    Unit unit = Unit.INSTANCE;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally(closeable, throwable);
                }
            }
            RequestLogger requestLogger = this.cfg.pvt.requestLogger;
            if (requestLogger != null) {
                requestLogger.handle($this$writeResponseAndLog, Float.valueOf($this$writeResponseAndLog.executionTimeMs()));
            }
        }
        catch (Throwable throwable) {
            this.exceptionMapper.handleUnexpectedThrowable$javalin($this$writeResponseAndLog.res(), throwable);
        }
        finally {
            if ($this$writeResponseAndLog.getOutputStreamWrapper$javalin().isInitialized()) {
                $this$writeResponseAndLog.outputStream().close();
            }
            if (AsyncUtil.INSTANCE.isAsync$javalin($this$writeResponseAndLog)) {
                $this$writeResponseAndLog.req().getAsyncContext().complete();
            }
        }
    }

    private static final CompletableFuture handleUserFuture$lambda$2(Supplier $userFutureSupplier) {
        Intrinsics.checkNotNullParameter($userFutureSupplier, "$userFutureSupplier");
        return (CompletableFuture)$userFutureSupplier.get();
    }

    private static final Unit handleUserFuture$lambda$3(JavalinServlet this$0, JavalinServletContext $this_handleUserFuture, Object it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($this_handleUserFuture, "$this_handleUserFuture");
        this$0.handleSync($this_handleUserFuture);
        return Unit.INSTANCE;
    }

    private static final Unit handleUserFuture$lambda$4(JavalinServlet this$0, JavalinServletContext $this_handleUserFuture, Throwable it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($this_handleUserFuture, "$this_handleUserFuture");
        ExceptionMapper exceptionMapper = this$0.exceptionMapper;
        Context context = $this_handleUserFuture;
        Intrinsics.checkNotNullExpressionValue(it, "it");
        exceptionMapper.handle$javalin(context, it);
        this$0.writeResponseAndLog($this_handleUserFuture);
        return Unit.INSTANCE;
    }

    private static final Unit handleTask$lambda$6(JavalinServlet this$0, JavalinServletContext $this_handleTask, Throwable $throwable) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($this_handleTask, "$this_handleTask");
        Intrinsics.checkNotNullParameter($throwable, "$throwable");
        this$0.exceptionMapper.handle$javalin($this_handleTask, $throwable);
        return Unit.INSTANCE;
    }

    public static final /* synthetic */ void access$writeResponseAndLog(JavalinServlet $this, JavalinServletContext $receiver) {
        $this.writeResponseAndLog($receiver);
    }
}

