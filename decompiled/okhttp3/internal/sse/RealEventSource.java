/*
 * Decompiled with CFR 0.152.
 */
package okhttp3.internal.sse;

import java.io.Closeable;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.sse.ServerSentEventReader;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fJ$\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u000e\u0010\u001f\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u0004\u001a\u00020\u0005H\u0016J\f\u0010 \u001a\u00020!*\u00020\"H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lokhttp3/internal/sse/RealEventSource;", "Lokhttp3/sse/EventSource;", "Lokhttp3/internal/sse/ServerSentEventReader$Callback;", "Lokhttp3/Callback;", "request", "Lokhttp3/Request;", "listener", "Lokhttp3/sse/EventSourceListener;", "(Lokhttp3/Request;Lokhttp3/sse/EventSourceListener;)V", "call", "Lokhttp3/internal/connection/RealCall;", "cancel", "", "connect", "client", "Lokhttp3/OkHttpClient;", "onEvent", "id", "", "type", "data", "onFailure", "Lokhttp3/Call;", "e", "Ljava/io/IOException;", "onResponse", "response", "Lokhttp3/Response;", "onRetryChange", "timeMs", "", "processResponse", "isEventStream", "", "Lokhttp3/ResponseBody;", "okhttp-sse"})
public final class RealEventSource
implements EventSource,
ServerSentEventReader.Callback,
Callback {
    @NotNull
    private final Request request;
    @NotNull
    private final EventSourceListener listener;
    private RealCall call;

    public RealEventSource(@NotNull Request request, @NotNull EventSourceListener listener) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.request = request;
        this.listener = listener;
    }

    public final void connect(@NotNull OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        OkHttpClient client2 = client.newBuilder().eventListener(EventListener.NONE).build();
        Call call = client2.newCall(this.request);
        Intrinsics.checkNotNull(call, "null cannot be cast to non-null type okhttp3.internal.connection.RealCall");
        this.call = (RealCall)call;
        RealCall realCall = this.call;
        if (realCall == null) {
            Intrinsics.throwUninitializedPropertyAccessException("call");
            realCall = null;
        }
        realCall.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(response, "response");
        this.processResponse(response);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void processResponse(@NotNull Response response) {
        Intrinsics.checkNotNullParameter(response, "response");
        Closeable closeable = response;
        Throwable throwable = null;
        try {
            Response it = (Response)closeable;
            boolean bl = false;
            if (!response.isSuccessful()) {
                this.listener.onFailure(this, null, response);
                return;
            }
            ResponseBody responseBody = response.body();
            Intrinsics.checkNotNull(responseBody);
            ResponseBody body2 = responseBody;
            if (!this.isEventStream(body2)) {
                this.listener.onFailure(this, new IllegalStateException("Invalid content-type: " + body2.contentType()), response);
                return;
            }
            RealCall realCall = this.call;
            if (realCall == null) {
                Intrinsics.throwUninitializedPropertyAccessException("call");
                realCall = null;
            }
            realCall.timeoutEarlyExit();
            Response response2 = response.newBuilder().body(Util.EMPTY_RESPONSE).build();
            ServerSentEventReader reader = new ServerSentEventReader(body2.source(), this);
            try {
                this.listener.onOpen(this, response2);
                while (reader.processNextEvent()) {
                }
            }
            catch (Exception e) {
                this.listener.onFailure(this, e, response2);
                CloseableKt.closeFinally(closeable, throwable);
                return;
            }
            this.listener.onClosed(this);
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

    private final boolean isEventStream(ResponseBody $this$isEventStream) {
        MediaType mediaType = $this$isEventStream.contentType();
        if (mediaType == null) {
            return false;
        }
        MediaType contentType = mediaType;
        return Intrinsics.areEqual(contentType.type(), "text") && Intrinsics.areEqual(contentType.subtype(), "event-stream");
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(e, "e");
        this.listener.onFailure(this, e, null);
    }

    @Override
    @NotNull
    public Request request() {
        return this.request;
    }

    @Override
    public void cancel() {
        RealCall realCall = this.call;
        if (realCall == null) {
            Intrinsics.throwUninitializedPropertyAccessException("call");
            realCall = null;
        }
        realCall.cancel();
    }

    @Override
    public void onEvent(@Nullable String id, @Nullable String type, @NotNull String data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.listener.onEvent(this, id, type, data);
    }

    @Override
    public void onRetryChange(long timeMs) {
    }
}

