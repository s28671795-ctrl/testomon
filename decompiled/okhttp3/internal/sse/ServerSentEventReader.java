/*
 * Decompiled with CFR 0.152.
 */
package okhttp3.internal.sse;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0002\u0011\u0012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lokhttp3/internal/sse/ServerSentEventReader;", "", "source", "Lokio/BufferedSource;", "callback", "Lokhttp3/internal/sse/ServerSentEventReader$Callback;", "(Lokio/BufferedSource;Lokhttp3/internal/sse/ServerSentEventReader$Callback;)V", "lastId", "", "completeEvent", "", "id", "type", "data", "Lokio/Buffer;", "processNextEvent", "", "Callback", "Companion", "okhttp-sse"})
@SourceDebugExtension(value={"SMAP\nServerSentEventReader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServerSentEventReader.kt\nokhttp3/internal/sse/ServerSentEventReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,159:1\n1#2:160\n*E\n"})
public final class ServerSentEventReader {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BufferedSource source;
    @NotNull
    private final Callback callback;
    @Nullable
    private String lastId;
    @NotNull
    private static final Options options;
    @NotNull
    private static final ByteString CRLF;

    public ServerSentEventReader(@NotNull BufferedSource source2, @NotNull Callback callback) {
        Intrinsics.checkNotNullParameter(source2, "source");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.source = source2;
        this.callback = callback;
    }

    public final boolean processNextEvent() throws IOException {
        block9: {
            String id = this.lastId;
            String type = null;
            Buffer data = new Buffer();
            while (true) {
                String it;
                int n;
                boolean bl = 0 <= (n = this.source.select(options)) ? n < 3 : false;
                if (bl) {
                    this.completeEvent(id, type, data);
                    return true;
                }
                boolean bl2 = 3 <= n ? n < 5 : false;
                if (bl2) {
                    ServerSentEventReader.Companion.readData(this.source, data);
                    continue;
                }
                boolean bl3 = 5 <= n ? n < 8 : false;
                if (bl3) {
                    data.writeByte(10);
                    continue;
                }
                boolean bl4 = 8 <= n ? n < 10 : false;
                if (bl4) {
                    String string;
                    it = string = this.source.readUtf8LineStrict();
                    boolean bl5 = false;
                    id = ((CharSequence)it).length() > 0 ? string : null;
                    continue;
                }
                boolean bl6 = 10 <= n ? n < 13 : false;
                if (bl6) {
                    id = null;
                    continue;
                }
                boolean bl7 = 13 <= n ? n < 15 : false;
                if (bl7) {
                    String string;
                    it = string = this.source.readUtf8LineStrict();
                    boolean bl8 = false;
                    type = ((CharSequence)it).length() > 0 ? string : null;
                    continue;
                }
                boolean bl9 = 15 <= n ? n < 18 : false;
                if (bl9) {
                    type = null;
                    continue;
                }
                boolean bl10 = 18 <= n ? n < 20 : false;
                if (bl10) {
                    long retryMs = ServerSentEventReader.Companion.readRetryMs(this.source);
                    if (retryMs == -1L) continue;
                    this.callback.onRetryChange(retryMs);
                    continue;
                }
                if (n != -1) break block9;
                long lineEnd = this.source.indexOfElement(CRLF);
                if (lineEnd == -1L) break;
                this.source.skip(lineEnd);
                this.source.select(options);
            }
            return false;
        }
        throw new AssertionError();
    }

    private final void completeEvent(String id, String type, Buffer data) throws IOException {
        if (data.size() != 0L) {
            this.lastId = id;
            data.skip(1L);
            this.callback.onEvent(id, type, data.readUtf8());
        }
    }

    static {
        ByteString[] byteStringArray = new ByteString[]{ByteString.Companion.encodeUtf8("\r\n"), ByteString.Companion.encodeUtf8("\r"), ByteString.Companion.encodeUtf8("\n"), ByteString.Companion.encodeUtf8("data: "), ByteString.Companion.encodeUtf8("data:"), ByteString.Companion.encodeUtf8("data\r\n"), ByteString.Companion.encodeUtf8("data\r"), ByteString.Companion.encodeUtf8("data\n"), ByteString.Companion.encodeUtf8("id: "), ByteString.Companion.encodeUtf8("id:"), ByteString.Companion.encodeUtf8("id\r\n"), ByteString.Companion.encodeUtf8("id\r"), ByteString.Companion.encodeUtf8("id\n"), ByteString.Companion.encodeUtf8("event: "), ByteString.Companion.encodeUtf8("event:"), ByteString.Companion.encodeUtf8("event\r\n"), ByteString.Companion.encodeUtf8("event\r"), ByteString.Companion.encodeUtf8("event\n"), ByteString.Companion.encodeUtf8("retry: "), ByteString.Companion.encodeUtf8("retry:")};
        options = Options.Companion.of(byteStringArray);
        CRLF = ByteString.Companion.encodeUtf8("\r\n");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2={"Lokhttp3/internal/sse/ServerSentEventReader$Callback;", "", "onEvent", "", "id", "", "type", "data", "onRetryChange", "timeMs", "", "okhttp-sse"})
    public static interface Callback {
        public void onEvent(@Nullable String var1, @Nullable String var2, @NotNull String var3);

        public void onRetryChange(long var1);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\f\u0010\u000e\u001a\u00020\u000f*\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0010"}, d2={"Lokhttp3/internal/sse/ServerSentEventReader$Companion;", "", "()V", "CRLF", "Lokio/ByteString;", "options", "Lokio/Options;", "getOptions", "()Lokio/Options;", "readData", "", "Lokio/BufferedSource;", "data", "Lokio/Buffer;", "readRetryMs", "", "okhttp-sse"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Options getOptions() {
            return options;
        }

        private final void readData(BufferedSource $this$readData, Buffer data) throws IOException {
            data.writeByte(10);
            $this$readData.readFully(data, $this$readData.indexOfElement(CRLF));
            $this$readData.select(this.getOptions());
        }

        private final long readRetryMs(BufferedSource $this$readRetryMs) throws IOException {
            String retryString = $this$readRetryMs.readUtf8LineStrict();
            return Util.toLongOrDefault(retryString, -1L);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

