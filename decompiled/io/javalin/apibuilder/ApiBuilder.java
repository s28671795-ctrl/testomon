/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.apibuilder;

import io.javalin.Javalin;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Handler;
import io.javalin.http.sse.SseClient;
import io.javalin.security.RouteRole;
import io.javalin.websocket.WsConfig;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class ApiBuilder {
    private static final ThreadLocal<Javalin> staticJavalin = new ThreadLocal();
    private static final ThreadLocal<Deque<String>> pathDeque = ThreadLocal.withInitial(ArrayDeque::new);

    public static void setStaticJavalin(@NotNull Javalin javalin) {
        staticJavalin.set(javalin);
    }

    public static void clearStaticJavalin() {
        staticJavalin.remove();
    }

    public static void path(@NotNull String path, @NotNull EndpointGroup endpointGroup) {
        path = ((String)path).startsWith("/") ? path : "/" + (String)path;
        pathDeque.get().addLast((String)path);
        endpointGroup.addEndpoints();
        pathDeque.get().removeLast();
    }

    public static String prefixPath(@NotNull String path) {
        if (!path.equals("*")) {
            path = path.startsWith("/") || path.isEmpty() ? path : "/" + path;
        }
        return String.join((CharSequence)"", (Iterable<? extends CharSequence>)pathDeque.get()) + path;
    }

    public static Javalin staticInstance() {
        Javalin javalin = staticJavalin.get();
        if (javalin == null) {
            throw new IllegalStateException("The static API can only be used within a routes() call.");
        }
        return javalin;
    }

    public static void get(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().get(ApiBuilder.prefixPath(path), handler);
    }

    public static void get(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().get(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void get(@NotNull Handler handler) {
        ApiBuilder.staticInstance().get(ApiBuilder.prefixPath(""), handler);
    }

    public static void get(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().get(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void post(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().post(ApiBuilder.prefixPath(path), handler);
    }

    public static void post(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().post(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void post(@NotNull Handler handler) {
        ApiBuilder.staticInstance().post(ApiBuilder.prefixPath(""), handler);
    }

    public static void post(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().post(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void put(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().put(ApiBuilder.prefixPath(path), handler);
    }

    public static void put(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().put(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void put(@NotNull Handler handler) {
        ApiBuilder.staticInstance().put(ApiBuilder.prefixPath(""), handler);
    }

    public static void put(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().put(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void patch(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().patch(ApiBuilder.prefixPath(path), handler);
    }

    public static void patch(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().patch(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void patch(@NotNull Handler handler) {
        ApiBuilder.staticInstance().patch(ApiBuilder.prefixPath(""), handler);
    }

    public static void patch(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().patch(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void delete(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().delete(ApiBuilder.prefixPath(path), handler);
    }

    public static void delete(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().delete(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void delete(@NotNull Handler handler) {
        ApiBuilder.staticInstance().delete(ApiBuilder.prefixPath(""), handler);
    }

    public static void delete(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().delete(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void head(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().head(ApiBuilder.prefixPath(path), handler);
    }

    public static void head(@NotNull String path, @NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().head(ApiBuilder.prefixPath(path), handler, roles);
    }

    public static void head(@NotNull Handler handler) {
        ApiBuilder.staticInstance().head(ApiBuilder.prefixPath(""), handler);
    }

    public static void head(@NotNull Handler handler, RouteRole ... roles) {
        ApiBuilder.staticInstance().head(ApiBuilder.prefixPath(""), handler, roles);
    }

    public static void before(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().before(ApiBuilder.prefixPath(path), handler);
    }

    public static void before(@NotNull Handler handler) {
        ApiBuilder.staticInstance().before(ApiBuilder.prefixPath("*"), handler);
    }

    public static void after(@NotNull String path, @NotNull Handler handler) {
        ApiBuilder.staticInstance().after(ApiBuilder.prefixPath(path), handler);
    }

    public static void after(@NotNull Handler handler) {
        ApiBuilder.staticInstance().after(ApiBuilder.prefixPath("*"), handler);
    }

    public static void ws(@NotNull String path, @NotNull Consumer<WsConfig> ws) {
        ApiBuilder.staticInstance().ws(ApiBuilder.prefixPath(path), ws);
    }

    public static void ws(@NotNull String path, @NotNull Consumer<WsConfig> ws, RouteRole ... roles) {
        ApiBuilder.staticInstance().ws(ApiBuilder.prefixPath(path), ws, roles);
    }

    public static void ws(@NotNull Consumer<WsConfig> ws) {
        ApiBuilder.staticInstance().ws(ApiBuilder.prefixPath(""), ws);
    }

    public static void ws(@NotNull Consumer<WsConfig> ws, RouteRole ... roles) {
        ApiBuilder.staticInstance().ws(ApiBuilder.prefixPath(""), ws, roles);
    }

    public Javalin wsBefore(@NotNull String path, @NotNull Consumer<WsConfig> wsConfig) {
        return ApiBuilder.staticInstance().wsBefore(ApiBuilder.prefixPath(path), wsConfig);
    }

    public Javalin wsBefore(@NotNull Consumer<WsConfig> wsConfig) {
        return ApiBuilder.staticInstance().wsBefore(ApiBuilder.prefixPath("*"), wsConfig);
    }

    public Javalin wsAfter(@NotNull String path, @NotNull Consumer<WsConfig> wsConfig) {
        return ApiBuilder.staticInstance().wsAfter(ApiBuilder.prefixPath(path), wsConfig);
    }

    public Javalin wsAfter(@NotNull Consumer<WsConfig> wsConfig) {
        return ApiBuilder.staticInstance().wsAfter(ApiBuilder.prefixPath("*"), wsConfig);
    }

    public static void sse(@NotNull String path, @NotNull Consumer<SseClient> client) {
        ApiBuilder.staticInstance().sse(ApiBuilder.prefixPath(path), client);
    }

    public static void sse(@NotNull String path, @NotNull Consumer<SseClient> client, RouteRole ... roles) {
        ApiBuilder.staticInstance().sse(ApiBuilder.prefixPath(path), client, roles);
    }

    public static void sse(@NotNull Consumer<SseClient> client) {
        ApiBuilder.staticInstance().sse(ApiBuilder.prefixPath(""), client);
    }

    public static void sse(@NotNull Consumer<SseClient> client, RouteRole ... roles) {
        ApiBuilder.staticInstance().sse(ApiBuilder.prefixPath(""), client, roles);
    }

    public static void crud(@NotNull CrudHandler crudHandler) {
        ApiBuilder.crud("", crudHandler, new RouteRole[0]);
    }

    public static void crud(@NotNull CrudHandler crudHandler, RouteRole ... roles) {
        ApiBuilder.crud("", crudHandler, roles);
    }

    public static void crud(@NotNull String path, @NotNull CrudHandler crudHandler) {
        ApiBuilder.crud(path, crudHandler, new RouteRole[0]);
    }

    public static void crud(@NotNull String path, @NotNull CrudHandler crudHandler, RouteRole ... roles) {
        String fullPath = ApiBuilder.prefixPath(path);
        String[] subPaths = (String[])Arrays.stream(fullPath.split("/")).filter(it -> !it.isEmpty()).toArray(String[]::new);
        if (subPaths.length < 2) {
            throw new IllegalArgumentException("CrudHandler requires a path like '/resource/{resource-id}'");
        }
        String resourceId = subPaths[subPaths.length - 1];
        if (!resourceId.startsWith("{") || !resourceId.endsWith("}")) {
            throw new IllegalArgumentException("CrudHandler requires a path-parameter at the end of the provided path, e.g. '/users/{user-id}'");
        }
        String resourceBase = subPaths[subPaths.length - 2];
        if (resourceBase.startsWith("{") || resourceBase.startsWith("<") || resourceBase.endsWith("}") || resourceBase.endsWith(">")) {
            throw new IllegalArgumentException("CrudHandler requires a resource base at the beginning of the provided path, e.g. '/users/{user-id}'");
        }
        ApiBuilder.staticInstance().get(fullPath, ctx -> crudHandler.getOne(ctx, ctx.pathParam(resourceId)), roles);
        ApiBuilder.staticInstance().get(fullPath.replace(resourceId, ""), crudHandler::getAll, roles);
        ApiBuilder.staticInstance().post(fullPath.replace(resourceId, ""), crudHandler::create, roles);
        ApiBuilder.staticInstance().patch(fullPath, ctx -> crudHandler.update(ctx, ctx.pathParam(resourceId)), roles);
        ApiBuilder.staticInstance().delete(fullPath, ctx -> crudHandler.delete(ctx, ctx.pathParam(resourceId)), roles);
    }
}

