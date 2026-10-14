/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.sse;

import io.javalin.http.Context;
import io.javalin.http.sse.Emitter;
import io.javalin.json.JsonMapper;
import io.javalin.json.JsonMapperKt;
import io.javalin.util.JavalinLogger;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.TypesJVMKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0006\u0010\u0002\u001a\u00020\u0003J\u0006\u0010\u000f\u001a\u00020\u000eJ\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017J$\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0014H\u0007J\u0006\u0010\u000b\u001a\u00020\u001aR\u0014\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lio/javalin/http/sse/SseClient;", "Ljava/io/Closeable;", "ctx", "Lio/javalin/http/Context;", "(Lio/javalin/http/Context;)V", "blockingFuture", "Ljava/util/concurrent/CompletableFuture;", "closeCallback", "Ljava/lang/Runnable;", "emitter", "Lio/javalin/http/sse/Emitter;", "terminated", "Ljava/util/concurrent/atomic/AtomicBoolean;", "close", "", "keepAlive", "logTerminated", "onClose", "sendComment", "comment", "", "sendEvent", "data", "", "event", "id", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nSseClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SseClient.kt\nio/javalin/http/sse/SseClient\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 JsonMapper.kt\nio/javalin/json/JsonMapperKt\n*L\n1#1,90:1\n1#2:91\n73#3:92\n*S KotlinDebug\n*F\n+ 1 SseClient.kt\nio/javalin/http/sse/SseClient\n*L\n67#1:92\n*E\n"})
public final class SseClient
implements Closeable {
    @NotNull
    private final Context ctx;
    @NotNull
    private final AtomicBoolean terminated;
    @NotNull
    private final Emitter emitter;
    @Nullable
    private CompletableFuture<?> blockingFuture;
    @NotNull
    private Runnable closeCallback;

    public SseClient(@NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.ctx = ctx;
        this.terminated = new AtomicBoolean(false);
        this.emitter = new Emitter(this.ctx.res());
        this.closeCallback = SseClient::closeCallback$lambda$0;
    }

    @NotNull
    public final Context ctx() {
        return this.ctx;
    }

    public final boolean terminated() {
        return this.terminated.get();
    }

    /*
     * WARNING - void declaration
     */
    public final void keepAlive() {
        void it;
        CompletableFuture completableFuture;
        CompletableFuture completableFuture2 = completableFuture = new CompletableFuture();
        SseClient sseClient = this;
        boolean bl = false;
        this.ctx.future(() -> SseClient.keepAlive$lambda$2$lambda$1((CompletableFuture)it));
        sseClient.blockingFuture = completableFuture;
    }

    public final void onClose(@NotNull Runnable closeCallback) {
        Intrinsics.checkNotNullParameter(closeCallback, "closeCallback");
        this.closeCallback = closeCallback;
    }

    @Override
    public void close() {
        block1: {
            if (this.terminated.getAndSet(true)) {
                return;
            }
            this.closeCallback.run();
            CompletableFuture<?> completableFuture = this.blockingFuture;
            if (completableFuture == null) break block1;
            completableFuture.complete(null);
        }
    }

    public final void sendEvent(@NotNull Object data) {
        Intrinsics.checkNotNullParameter(data, "data");
        SseClient.sendEvent$default(this, "message", data, null, 4, null);
    }

    @JvmOverloads
    public final void sendEvent(@NotNull String event, @NotNull Object data, @Nullable String id) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(data, "data");
        if (this.terminated.get()) {
            this.logTerminated();
            return;
        }
        Object object = data;
        if (object instanceof InputStream) {
            this.emitter.emit(event, (InputStream)data, id);
        } else if (object instanceof String) {
            String string = (String)data;
            Charset charset = Charsets.UTF_8;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
            this.emitter.emit(event, new ByteArrayInputStream(byArray), id);
        } else {
            JsonMapper $this$toJsonString$iv = JsonMapperKt.jsonMapper(this.ctx);
            boolean $i$f$toJsonString = false;
            String string = $this$toJsonString$iv.toJsonString(data, TypesJVMKt.getJavaType(Reflection.typeOf(Object.class)));
            Charset charset = Charsets.UTF_8;
            byte[] byArray = string.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
            this.emitter.emit(event, new ByteArrayInputStream(byArray), id);
        }
        if (this.emitter.getClosed()) {
            this.close();
        }
    }

    public static /* synthetic */ void sendEvent$default(SseClient sseClient, String string, Object object, String string2, int n, Object object2) {
        if ((n & 4) != 0) {
            string2 = null;
        }
        sseClient.sendEvent(string, object, string2);
    }

    public final void sendComment(@NotNull String comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        if (this.terminated.get()) {
            this.logTerminated();
            return;
        }
        this.emitter.emit(comment);
        if (this.emitter.getClosed()) {
            this.close();
        }
    }

    private final void logTerminated() {
        JavalinLogger.warn$default("Cannot send data, SseClient has been terminated.", null, 2, null);
    }

    @JvmOverloads
    public final void sendEvent(@NotNull String event, @NotNull Object data) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(data, "data");
        SseClient.sendEvent$default(this, event, data, null, 4, null);
    }

    private static final void closeCallback$lambda$0() {
    }

    private static final CompletableFuture keepAlive$lambda$2$lambda$1(CompletableFuture $it) {
        Intrinsics.checkNotNullParameter($it, "$it");
        return $it;
    }
}

