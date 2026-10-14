/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.http.HttpStatus;
import io.javalin.jetty.JettyServer;
import io.javalin.util.ConcurrencyUtil;
import io.javalin.util.JavalinLogger;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0007\u00a8\u0006\u0015"}, d2={"Lio/javalin/jetty/JettyUtil;", "", "()V", "defaultThreadPool", "Lorg/eclipse/jetty/util/thread/ThreadPool;", "getOrDefault", "Lorg/eclipse/jetty/server/Server;", "server", "isClientAbortException", "", "t", "", "isJettyTimeoutException", "isSomewhatExpectedException", "logDebugAndSetError", "", "res", "Ljakarta/servlet/http/HttpServletResponse;", "maybeLogIfServerNotStarted", "jettyServer", "Lio/javalin/jetty/JettyServer;", "javalin"})
public final class JettyUtil {
    @NotNull
    public static final JettyUtil INSTANCE = new JettyUtil();

    private JettyUtil() {
    }

    @JvmStatic
    @NotNull
    public static final Server getOrDefault(@Nullable Server server) {
        Server server2 = server;
        if (server2 == null) {
            Server server3;
            Server $this$getOrDefault_u24lambda_u240 = server3 = new Server(INSTANCE.defaultThreadPool());
            boolean bl = false;
            $this$getOrDefault_u24lambda_u240.addBean(new LowResourceMonitor($this$getOrDefault_u24lambda_u240));
            $this$getOrDefault_u24lambda_u240.insertHandler(new StatisticsHandler());
            $this$getOrDefault_u24lambda_u240.setAttribute("is-default-server", true);
            server2 = server3;
        }
        return server2;
    }

    private final ThreadPool defaultThreadPool() {
        return ConcurrencyUtil.jettyThreadPool("JettyServerThreadPool", 8, 250);
    }

    @JvmStatic
    public static final void maybeLogIfServerNotStarted(@NotNull JettyServer jettyServer) {
        Intrinsics.checkNotNullParameter(jettyServer, "jettyServer");
        new Thread(() -> JettyUtil.maybeLogIfServerNotStarted$lambda$1(jettyServer)).start();
    }

    public final boolean isClientAbortException(@NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(t2, "t");
        return Intrinsics.areEqual(t2.getClass().getName(), "org.eclipse.jetty.io.EofException");
    }

    public final boolean isJettyTimeoutException(@NotNull Throwable t2) {
        Intrinsics.checkNotNullParameter(t2, "t");
        return t2 instanceof IOException && t2.getCause() instanceof TimeoutException;
    }

    public final boolean isSomewhatExpectedException(@NotNull Throwable t2) {
        Throwable unwrapped;
        Intrinsics.checkNotNullParameter(t2, "t");
        Throwable throwable = t2 instanceof CompletionException ? (CompletionException)t2 : null;
        if (throwable == null || (throwable = throwable.getCause()) == null) {
            throwable = t2;
        }
        return this.isClientAbortException(unwrapped = throwable) || this.isJettyTimeoutException(unwrapped);
    }

    public final void logDebugAndSetError(@NotNull Throwable t2, @NotNull HttpServletResponse res) {
        Intrinsics.checkNotNullParameter(t2, "t");
        Intrinsics.checkNotNullParameter(res, "res");
        JavalinLogger.debug("Client aborted or timed out", t2);
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getCode());
    }

    private static final void maybeLogIfServerNotStarted$lambda$1(JettyServer $jettyServer) {
        Intrinsics.checkNotNullParameter($jettyServer, "$jettyServer");
        Thread.sleep(5000L);
        if (!$jettyServer.started) {
            JavalinLogger.startup("It looks like you created a Javalin instance, but you never started it.");
            JavalinLogger.startup("Try: Javalin app = Javalin.create().start();");
            JavalinLogger.startup("For more help, visit https://javalin.io/documentation#server-setup");
        }
    }
}

