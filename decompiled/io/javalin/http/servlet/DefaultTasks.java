/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.MethodNotAllowedResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.servlet.JavalinResourceResponseWrapper;
import io.javalin.http.servlet.JavalinServlet;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.http.servlet.SubmitOrder;
import io.javalin.http.servlet.Task;
import io.javalin.http.servlet.TaskInitializer;
import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.http.util.MethodNotAllowedUtil;
import io.javalin.routing.HandlerEntry;
import io.javalin.security.AccessManager;
import io.javalin.security.AccessManagerKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007\u00a8\u0006\u000e"}, d2={"Lio/javalin/http/servlet/DefaultTasks;", "", "()V", "AFTER", "Lio/javalin/http/servlet/TaskInitializer;", "Lio/javalin/http/servlet/JavalinServletContext;", "getAFTER", "()Lio/javalin/http/servlet/TaskInitializer;", "BEFORE", "getBEFORE", "ERROR", "getERROR", "HTTP", "getHTTP", "javalin"})
@SourceDebugExtension(value={"SMAP\nDefaultTasks.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefaultTasks.kt\nio/javalin/http/servlet/DefaultTasks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,79:1\n1855#2,2:80\n288#2,2:82\n1855#2,2:84\n*S KotlinDebug\n*F\n+ 1 DefaultTasks.kt\nio/javalin/http/servlet/DefaultTasks\n*L\n18#1:80,2\n24#1:82,2\n69#1:84,2\n*E\n"})
public final class DefaultTasks {
    @NotNull
    public static final DefaultTasks INSTANCE = new DefaultTasks();
    @NotNull
    private static final TaskInitializer<JavalinServletContext> BEFORE = DefaultTasks::BEFORE$lambda$2;
    @NotNull
    private static final TaskInitializer<JavalinServletContext> HTTP = DefaultTasks::HTTP$lambda$8;
    @NotNull
    private static final TaskInitializer<JavalinServletContext> ERROR = DefaultTasks::ERROR$lambda$10;
    @NotNull
    private static final TaskInitializer<JavalinServletContext> AFTER = DefaultTasks::AFTER$lambda$13;

    private DefaultTasks() {
    }

    @NotNull
    public final TaskInitializer<JavalinServletContext> getBEFORE() {
        return BEFORE;
    }

    @NotNull
    public final TaskInitializer<JavalinServletContext> getHTTP() {
        return HTTP;
    }

    @NotNull
    public final TaskInitializer<JavalinServletContext> getERROR() {
        return ERROR;
    }

    @NotNull
    public final TaskInitializer<JavalinServletContext> getAFTER() {
        return AFTER;
    }

    private static final Unit BEFORE$lambda$2$lambda$1$lambda$0(HandlerEntry $entry, JavalinServletContext $ctx, String $requestUri) {
        Intrinsics.checkNotNullParameter($entry, "$entry");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        $entry.handle($ctx, $requestUri);
        return Unit.INSTANCE;
    }

    private static final void BEFORE$lambda$2(Function2 submitTask2, JavalinServlet servlet, JavalinServletContext ctx, String requestUri) {
        Intrinsics.checkNotNullParameter(submitTask2, "submitTask");
        Intrinsics.checkNotNullParameter(servlet, "servlet");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        Iterable $this$forEach$iv = servlet.getMatcher().findEntries(HandlerType.BEFORE, requestUri);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            HandlerEntry entry = (HandlerEntry)element$iv;
            boolean bl = false;
            submitTask2.invoke(SubmitOrder.LAST, new Task(true, () -> DefaultTasks.BEFORE$lambda$2$lambda$1$lambda$0(entry, ctx, requestUri)));
        }
    }

    private static final Unit HTTP$lambda$8$lambda$6$lambda$5$lambda$4$lambda$3(HandlerEntry $entry, JavalinServletContext $ctx, String $requestUri) {
        Intrinsics.checkNotNullParameter($entry, "$entry");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        $entry.handle($ctx, $requestUri);
        return Unit.INSTANCE;
    }

    private static final void HTTP$lambda$8$lambda$6$lambda$5$lambda$4(Function2 $submitTask, HandlerEntry $entry, JavalinServletContext $ctx, String $requestUri, Context it) {
        Intrinsics.checkNotNullParameter($submitTask, "$submitTask");
        Intrinsics.checkNotNullParameter($entry, "$entry");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        Intrinsics.checkNotNullParameter(it, "it");
        $submitTask.invoke(SubmitOrder.FIRST, new Task(false, () -> DefaultTasks.HTTP$lambda$8$lambda$6$lambda$5$lambda$4$lambda$3($entry, $ctx, $requestUri), 1, null));
    }

    private static final Unit HTTP$lambda$8$lambda$6$lambda$5(JavalinServlet $servlet, JavalinServletContext $ctx, HandlerEntry $entry, String $requestUri, Function2 $submitTask) {
        Intrinsics.checkNotNullParameter($servlet, "$servlet");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($entry, "$entry");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        Intrinsics.checkNotNullParameter($submitTask, "$submitTask");
        if ($servlet.getCfg().pvt.accessManager != null) {
            $ctx.update($entry, $requestUri);
            AccessManager accessManager = $servlet.getCfg().pvt.accessManager;
            if (accessManager != null) {
                accessManager.manage(arg_0 -> DefaultTasks.HTTP$lambda$8$lambda$6$lambda$5$lambda$4($submitTask, $entry, $ctx, $requestUri, arg_0), $ctx, $entry.getRoles());
            }
        } else {
            if (!((Collection)$entry.getRoles()).isEmpty()) {
                throw AccessManagerKt.accessManagerNotConfiguredException();
            }
            $entry.handle($ctx, $requestUri);
        }
        return Unit.INSTANCE;
    }

    private static final Unit HTTP$lambda$8$lambda$7(JavalinServletContext $ctx, JavalinServlet $servlet, String $requestUri) {
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($servlet, "$servlet");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        if ($ctx.method() == HandlerType.HEAD && $servlet.getMatcher().hasEntries$javalin(HandlerType.GET, $requestUri)) {
            return Unit.INSTANCE;
        }
        if ($ctx.method() == HandlerType.HEAD || $ctx.method() == HandlerType.GET) {
            ResourceHandler resourceHandler = $servlet.getCfg().pvt.resourceHandler;
            boolean bl = resourceHandler != null ? resourceHandler.handle($ctx.req(), new JavalinResourceResponseWrapper($ctx)) : false;
            if (bl) {
                return Unit.INSTANCE;
            }
            if ($servlet.getCfg().pvt.singlePageHandler.handle($ctx)) {
                return Unit.INSTANCE;
            }
        }
        if ($ctx.handlerType() == HandlerType.BEFORE) {
            $ctx.setEndpointHandlerPath$javalin("No handler matched request path/method (404/405)");
        }
        List<HandlerType> availableHandlerTypes = MethodNotAllowedUtil.INSTANCE.findAvailableHttpHandlerTypes($servlet.getMatcher(), $requestUri);
        if ($servlet.getCfg().http.prefer405over404 && !((Collection)availableHandlerTypes).isEmpty()) {
            throw new MethodNotAllowedResponse(null, MethodNotAllowedUtil.INSTANCE.getAvailableHandlerTypes($ctx, availableHandlerTypes), 1, null);
        }
        throw new NotFoundResponse(null, null, 3, null);
    }

    private static final void HTTP$lambda$8(Function2 submitTask2, JavalinServlet servlet, JavalinServletContext ctx, String requestUri) {
        Intrinsics.checkNotNullParameter(submitTask2, "submitTask");
        Intrinsics.checkNotNullParameter(servlet, "servlet");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        Iterable $this$firstOrNull$iv = servlet.getMatcher().findEntries(ctx.method(), requestUri);
        boolean $i$f$firstOrNull = false;
        Iterator iterator2 = $this$firstOrNull$iv.iterator();
        if (iterator2.hasNext()) {
            Object element$iv = iterator2.next();
            HandlerEntry entry = (HandlerEntry)element$iv;
            boolean bl = false;
            submitTask2.invoke(SubmitOrder.LAST, new Task(false, () -> DefaultTasks.HTTP$lambda$8$lambda$6$lambda$5(servlet, ctx, entry, requestUri, submitTask2), 1, null));
            return;
        }
        submitTask2.invoke(SubmitOrder.LAST, new Task(false, () -> DefaultTasks.HTTP$lambda$8$lambda$7(ctx, servlet, requestUri), 1, null));
    }

    private static final Unit ERROR$lambda$10$lambda$9(JavalinServlet $servlet, JavalinServletContext $ctx) {
        Intrinsics.checkNotNullParameter($servlet, "$servlet");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        $servlet.getErrorMapper().handle($ctx.statusCode(), $ctx);
        return Unit.INSTANCE;
    }

    private static final void ERROR$lambda$10(Function2 submitTask2, JavalinServlet servlet, JavalinServletContext ctx, String string) {
        Intrinsics.checkNotNullParameter(submitTask2, "submitTask");
        Intrinsics.checkNotNullParameter(servlet, "servlet");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(string, "<anonymous parameter 3>");
        submitTask2.invoke(SubmitOrder.LAST, new Task(false, () -> DefaultTasks.ERROR$lambda$10$lambda$9(servlet, ctx)));
    }

    private static final Unit AFTER$lambda$13$lambda$12$lambda$11(HandlerEntry $entry, JavalinServletContext $ctx, String $requestUri) {
        Intrinsics.checkNotNullParameter($entry, "$entry");
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($requestUri, "$requestUri");
        $entry.handle($ctx, $requestUri);
        return Unit.INSTANCE;
    }

    private static final void AFTER$lambda$13(Function2 submitTask2, JavalinServlet servlet, JavalinServletContext ctx, String requestUri) {
        Intrinsics.checkNotNullParameter(submitTask2, "submitTask");
        Intrinsics.checkNotNullParameter(servlet, "servlet");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        Iterable $this$forEach$iv = servlet.getMatcher().findEntries(HandlerType.AFTER, requestUri);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            HandlerEntry entry = (HandlerEntry)element$iv;
            boolean bl = false;
            submitTask2.invoke(SubmitOrder.LAST, new Task(false, () -> DefaultTasks.AFTER$lambda$13$lambda$12$lambda$11(entry, ctx, requestUri)));
        }
    }
}

