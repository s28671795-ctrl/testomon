/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.compression.CompressionStrategy;
import io.javalin.http.HttpStatus;
import io.javalin.http.RequestLogger;
import io.javalin.http.SinglePageHandler;
import io.javalin.http.servlet.DefaultTasks;
import io.javalin.http.servlet.JavaLangErrorHandler;
import io.javalin.http.servlet.JavalinServletContext;
import io.javalin.http.servlet.TaskInitializer;
import io.javalin.http.staticfiles.ResourceHandler;
import io.javalin.security.AccessManager;
import io.javalin.util.JavalinLogger;
import io.javalin.websocket.WsConfig;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u000eR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00168\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00188\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d0\u001c8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u0004\u0018\u00010 8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00020\"8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010#\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010%\u001a\u0004\u0018\u00010&8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2={"Lio/javalin/config/PrivateConfig;", "", "()V", "accessManager", "Lio/javalin/security/AccessManager;", "appAttributes", "", "", "compressionStrategy", "Lio/javalin/compression/CompressionStrategy;", "httpConfigurationConfig", "Ljava/util/function/Consumer;", "Lorg/eclipse/jetty/server/HttpConfiguration;", "javaLangErrorHandler", "Lio/javalin/http/servlet/JavaLangErrorHandler;", "getJavaLangErrorHandler$javalin", "()Lio/javalin/http/servlet/JavaLangErrorHandler;", "setJavaLangErrorHandler$javalin", "(Lio/javalin/http/servlet/JavaLangErrorHandler;)V", "requestLogger", "Lio/javalin/http/RequestLogger;", "resourceHandler", "Lio/javalin/http/staticfiles/ResourceHandler;", "server", "Lorg/eclipse/jetty/server/Server;", "servletContextHandlerConsumer", "Lorg/eclipse/jetty/servlet/ServletContextHandler;", "servletRequestLifecycle", "", "Lio/javalin/http/servlet/TaskInitializer;", "Lio/javalin/http/servlet/JavalinServletContext;", "sessionHandler", "Lorg/eclipse/jetty/server/session/SessionHandler;", "singlePageHandler", "Lio/javalin/http/SinglePageHandler;", "wsFactoryConfig", "Lorg/eclipse/jetty/websocket/server/JettyWebSocketServletFactory;", "wsLogger", "Lio/javalin/websocket/WsConfig;", "handler", "javalin"})
@SourceDebugExtension(value={"SMAP\nPrivateConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PrivateConfig.kt\nio/javalin/config/PrivateConfig\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"})
public final class PrivateConfig {
    @JvmField
    @NotNull
    public Map<String, Object> appAttributes = new HashMap();
    @JvmField
    @Nullable
    public RequestLogger requestLogger;
    @JvmField
    @Nullable
    public ResourceHandler resourceHandler;
    @JvmField
    @Nullable
    public AccessManager accessManager;
    @JvmField
    @NotNull
    public SinglePageHandler singlePageHandler = new SinglePageHandler();
    @JvmField
    @Nullable
    public SessionHandler sessionHandler;
    @JvmField
    @Nullable
    public Consumer<JettyWebSocketServletFactory> wsFactoryConfig;
    @JvmField
    @Nullable
    public WsConfig wsLogger;
    @JvmField
    @Nullable
    public Server server;
    @JvmField
    @Nullable
    public Consumer<ServletContextHandler> servletContextHandlerConsumer;
    @JvmField
    @NotNull
    public CompressionStrategy compressionStrategy = CompressionStrategy.GZIP;
    @JvmField
    @NotNull
    public List<? extends TaskInitializer<JavalinServletContext>> servletRequestLifecycle;
    @JvmField
    @Nullable
    public Consumer<HttpConfiguration> httpConfigurationConfig;
    @NotNull
    private JavaLangErrorHandler javaLangErrorHandler;

    public PrivateConfig() {
        TaskInitializer[] taskInitializerArray = new TaskInitializer[]{DefaultTasks.INSTANCE.getBEFORE(), DefaultTasks.INSTANCE.getHTTP(), DefaultTasks.INSTANCE.getERROR(), DefaultTasks.INSTANCE.getAFTER()};
        this.servletRequestLifecycle = CollectionsKt.listOf(taskInitializerArray);
        this.javaLangErrorHandler = PrivateConfig::javaLangErrorHandler$lambda$1;
    }

    @NotNull
    public final PrivateConfig javaLangErrorHandler(@NotNull JavaLangErrorHandler handler) {
        PrivateConfig privateConfig;
        Intrinsics.checkNotNullParameter(handler, "handler");
        PrivateConfig $this$javaLangErrorHandler_u24lambda_u240 = privateConfig = this;
        boolean bl = false;
        $this$javaLangErrorHandler_u24lambda_u240.javaLangErrorHandler = handler;
        return privateConfig;
    }

    @NotNull
    public final JavaLangErrorHandler getJavaLangErrorHandler$javalin() {
        return this.javaLangErrorHandler;
    }

    public final void setJavaLangErrorHandler$javalin(@NotNull JavaLangErrorHandler javaLangErrorHandler) {
        Intrinsics.checkNotNullParameter(javaLangErrorHandler, "<set-?>");
        this.javaLangErrorHandler = javaLangErrorHandler;
    }

    private static final void javaLangErrorHandler$lambda$1(HttpServletResponse res, Error error) {
        Intrinsics.checkNotNullParameter(res, "res");
        Intrinsics.checkNotNullParameter(error, "error");
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
        JavalinLogger.error("Fatal error occurred while servicing http-request", error);
    }
}

