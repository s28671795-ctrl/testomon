/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.JavalinLogger;
import io.javalin.util.NamedVirtualThreadFactory;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\fJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0014"}, d2={"Lio/javalin/util/LoomUtil;", "", "()V", "loomAvailable", "", "getLoomAvailable", "()Z", "getExecutorService", "Ljava/util/concurrent/ExecutorService;", "name", "", "getThreadPool", "Lorg/eclipse/jetty/util/thread/ThreadPool;", "isLoomThreadPool", "threadPool", "logIfLoom", "", "server", "Lorg/eclipse/jetty/server/Server;", "LoomThreadPool", "javalin"})
@SourceDebugExtension(value={"SMAP\nConcurrencyUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConcurrencyUtil.kt\nio/javalin/util/LoomUtil\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,172:1\n1#2:173\n*E\n"})
public final class LoomUtil {
    @NotNull
    public static final LoomUtil INSTANCE;
    private static final boolean loomAvailable;

    private LoomUtil() {
    }

    public final boolean getLoomAvailable() {
        return loomAvailable;
    }

    @NotNull
    public final ExecutorService getExecutorService(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (!loomAvailable) {
            boolean bl = false;
            String string = "Your Java version (" + System.getProperty("java.version") + ") doesn't support Loom";
            throw new IllegalArgumentException(string.toString());
        }
        Class[] classArray = new Class[]{ThreadFactory.class};
        Method factoryMethod = Executors.class.getMethod("newThreadPerTaskExecutor", classArray);
        Object[] objectArray = new Object[]{new NamedVirtualThreadFactory(name)};
        Object object = factoryMethod.invoke(Executors.class, objectArray);
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type java.util.concurrent.ExecutorService");
        return (ExecutorService)object;
    }

    @NotNull
    public final ThreadPool getThreadPool(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new LoomThreadPool(name);
    }

    public final boolean isLoomThreadPool(@NotNull ThreadPool threadPool) {
        Intrinsics.checkNotNullParameter(threadPool, "threadPool");
        return threadPool instanceof LoomThreadPool;
    }

    public final void logIfLoom(@NotNull Server server) {
        Intrinsics.checkNotNullParameter(server, "server");
        ThreadPool threadPool = server.getThreadPool();
        Intrinsics.checkNotNullExpressionValue(threadPool, "server.threadPool");
        if (!this.isLoomThreadPool(threadPool)) {
            return;
        }
        JavalinLogger.startup("Your JDK supports Loom. Javalin will prefer Virtual Threads by default. Disable with `ConcurrencyUtil.useLoom = false`.");
    }

    static {
        Object object;
        LoomUtil loomUtil = INSTANCE = new LoomUtil();
        try {
            LoomUtil $this$loomAvailable_u24lambda_u240 = loomUtil;
            boolean bl = false;
            Method factoryMethod = Executors.class.getMethod("newVirtualThreadPerTaskExecutor", new Class[0]);
            Object object2 = factoryMethod.invoke(Executors.class, new Object[0]);
            Intrinsics.checkNotNull(object2, "null cannot be cast to non-null type java.util.concurrent.ExecutorService");
            object = Result.constructor-impl((ExecutorService)object2);
        }
        catch (Throwable throwable) {
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        loomAvailable = Result.isSuccess-impl(object);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/util/LoomUtil$LoomThreadPool;", "Lorg/eclipse/jetty/util/thread/ThreadPool;", "name", "", "(Ljava/lang/String;)V", "executorService", "Ljava/util/concurrent/ExecutorService;", "execute", "", "command", "Ljava/lang/Runnable;", "getIdleThreads", "", "getThreads", "isLowOnThreads", "", "join", "javalin"})
    private static final class LoomThreadPool
    implements ThreadPool {
        @NotNull
        private final ExecutorService executorService;

        public LoomThreadPool(@NotNull String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            this.executorService = INSTANCE.getExecutorService(name);
        }

        @Override
        public void join() {
        }

        @Override
        public int getThreads() {
            return 1;
        }

        @Override
        public int getIdleThreads() {
            return 1;
        }

        @Override
        public boolean isLowOnThreads() {
            return false;
        }

        @Override
        public void execute(@NotNull Runnable command) {
            Intrinsics.checkNotNullParameter(command, "command");
            this.executorService.submit(command);
        }
    }
}

