/*
 * Decompiled with CFR 0.152.
 */
package okhttp3.sse;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J,\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\tH\u0016J$\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0016\u00a8\u0006\u0012"}, d2={"Lokhttp3/sse/EventSourceListener;", "", "()V", "onClosed", "", "eventSource", "Lokhttp3/sse/EventSource;", "onEvent", "id", "", "type", "data", "onFailure", "t", "", "response", "Lokhttp3/Response;", "onOpen", "okhttp-sse"})
public abstract class EventSourceListener {
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
        Intrinsics.checkNotNullParameter(response, "response");
    }

    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
        Intrinsics.checkNotNullParameter(data, "data");
    }

    public void onClosed(@NotNull EventSource eventSource) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
    }

    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t2, @Nullable Response response) {
        Intrinsics.checkNotNullParameter(eventSource, "eventSource");
    }
}

