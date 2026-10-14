/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.MultipartConfig;
import io.javalin.config.PrivateConfig;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0014\u0010\f\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nJ\u0016\u0010\u000e\u001a\u00020\b2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fJ\u0014\u0010\u0011\u001a\u00020\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000fJ\u0014\u0010\u0014\u001a\u00020\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\nR\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lio/javalin/config/JettyConfig;", "", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "multipartConfig", "Lio/javalin/config/MultipartConfig;", "contextHandlerConfig", "", "consumer", "Ljava/util/function/Consumer;", "Lorg/eclipse/jetty/servlet/ServletContextHandler;", "httpConfigurationConfig", "Lorg/eclipse/jetty/server/HttpConfiguration;", "server", "Ljava/util/function/Supplier;", "Lorg/eclipse/jetty/server/Server;", "sessionHandler", "sessionHandlerSupplier", "Lorg/eclipse/jetty/server/session/SessionHandler;", "wsFactoryConfig", "Lorg/eclipse/jetty/websocket/server/JettyWebSocketServletFactory;", "javalin"})
public final class JettyConfig {
    @NotNull
    private final PrivateConfig pvt;
    @JvmField
    @NotNull
    public final MultipartConfig multipartConfig;

    public JettyConfig(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
        this.multipartConfig = new MultipartConfig();
    }

    public final void server(@NotNull Supplier<Server> server) {
        Intrinsics.checkNotNullParameter(server, "server");
        this.pvt.server = server.get();
    }

    public final void contextHandlerConfig(@NotNull Consumer<ServletContextHandler> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        this.pvt.servletContextHandlerConsumer = consumer;
    }

    public final void sessionHandler(@NotNull Supplier<SessionHandler> sessionHandlerSupplier) {
        Intrinsics.checkNotNullParameter(sessionHandlerSupplier, "sessionHandlerSupplier");
        this.pvt.sessionHandler = sessionHandlerSupplier.get();
    }

    public final void wsFactoryConfig(@NotNull Consumer<JettyWebSocketServletFactory> wsFactoryConfig) {
        Intrinsics.checkNotNullParameter(wsFactoryConfig, "wsFactoryConfig");
        this.pvt.wsFactoryConfig = wsFactoryConfig;
    }

    public final void httpConfigurationConfig(@NotNull Consumer<HttpConfiguration> httpConfigurationConfig) {
        Intrinsics.checkNotNullParameter(httpConfigurationConfig, "httpConfigurationConfig");
        this.pvt.httpConfigurationConfig = httpConfigurationConfig;
    }
}

