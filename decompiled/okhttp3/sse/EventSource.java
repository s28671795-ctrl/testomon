/*
 * Decompiled with CFR 0.152.
 */
package okhttp3.sse;

import kotlin.Metadata;
import okhttp3.Request;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2={"Lokhttp3/sse/EventSource;", "", "cancel", "", "request", "Lokhttp3/Request;", "Factory", "okhttp-sse"})
public interface EventSource {
    @NotNull
    public Request request();

    public void cancel();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2={"Lokhttp3/sse/EventSource$Factory;", "", "newEventSource", "Lokhttp3/sse/EventSource;", "request", "Lokhttp3/Request;", "listener", "Lokhttp3/sse/EventSourceListener;", "okhttp-sse"})
    public static interface Factory {
        @NotNull
        public EventSource newEventSource(@NotNull Request var1, @NotNull EventSourceListener var2);
    }
}

