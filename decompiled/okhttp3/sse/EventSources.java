/*
 * Decompiled with CFR 0.152.
 */
package okhttp3.sse;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.sse.RealEventSource;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\r"}, d2={"Lokhttp3/sse/EventSources;", "", "()V", "createFactory", "Lokhttp3/sse/EventSource$Factory;", "client", "Lokhttp3/OkHttpClient;", "processResponse", "", "response", "Lokhttp3/Response;", "listener", "Lokhttp3/sse/EventSourceListener;", "okhttp-sse"})
public final class EventSources {
    @NotNull
    public static final EventSources INSTANCE = new EventSources();

    private EventSources() {
    }

    @JvmStatic
    @NotNull
    public static final EventSource.Factory createFactory(@NotNull OkHttpClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        return (arg_0, arg_1) -> EventSources.createFactory$lambda$1(client, arg_0, arg_1);
    }

    @JvmStatic
    public static final void processResponse(@NotNull Response response, @NotNull EventSourceListener listener) {
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(listener, "listener");
        RealEventSource eventSource = new RealEventSource(response.request(), listener);
        eventSource.processResponse(response);
    }

    private static final EventSource createFactory$lambda$1(OkHttpClient $client, Request request, EventSourceListener listener) {
        RealEventSource realEventSource;
        Intrinsics.checkNotNullParameter($client, "$client");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(listener, "listener");
        Request actualRequest = request.header("Accept") == null ? request.newBuilder().addHeader("Accept", "text/event-stream").build() : request;
        RealEventSource $this$createFactory_u24lambda_u241_u24lambda_u240 = realEventSource = new RealEventSource(actualRequest, listener);
        boolean bl = false;
        $this$createFactory_u24lambda_u241_u24lambda_u240.connect($client);
        return realEventSource;
    }
}

