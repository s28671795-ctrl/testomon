/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.Context;
import io.javalin.http.servlet.JavalinServlet;
import io.javalin.http.servlet.SubmitOrder;
import io.javalin.http.servlet.Task;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00e6\u0080\u0001\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J?\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00020\u000eH&\u00a2\u0006\u0002\u0010\u000f\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\u0010\u00c0\u0006\u0001"}, d2={"Lio/javalin/http/servlet/TaskInitializer;", "CTX", "Lio/javalin/http/Context;", "", "createTasks", "", "submitTask", "Lkotlin/Function2;", "Lio/javalin/http/servlet/SubmitOrder;", "Lio/javalin/http/servlet/Task;", "servlet", "Lio/javalin/http/servlet/JavalinServlet;", "ctx", "requestUri", "", "(Lkotlin/jvm/functions/Function2;Lio/javalin/http/servlet/JavalinServlet;Lio/javalin/http/Context;Ljava/lang/String;)V", "javalin"})
public interface TaskInitializer<CTX extends Context> {
    public void createTasks(@NotNull Function2<? super SubmitOrder, ? super Task, Unit> var1, @NotNull JavalinServlet var2, @NotNull CTX var3, @NotNull String var4);
}

