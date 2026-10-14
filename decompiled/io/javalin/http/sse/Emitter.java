/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.sse;

import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ \u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\rJ\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\rH\u0002R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lio/javalin/http/sse/Emitter;", "", "response", "Ljakarta/servlet/http/HttpServletResponse;", "(Ljakarta/servlet/http/HttpServletResponse;)V", "<set-?>", "", "closed", "getClosed", "()Z", "emit", "", "comment", "", "event", "data", "Ljava/io/InputStream;", "id", "write", "value", "javalin"})
@SourceDebugExtension(value={"SMAP\nEmitter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitter.kt\nio/javalin/http/sse/Emitter\n+ 2 ReadWrite.kt\nkotlin/io/TextStreamsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n52#2:48\n1#3:49\n1295#4,2:50\n1855#5,2:52\n*S KotlinDebug\n*F\n+ 1 Emitter.kt\nio/javalin/http/sse/Emitter\n*L\n22#1:48\n22#1:49\n23#1:50,2\n35#1:52,2\n*E\n"})
public final class Emitter {
    @NotNull
    private HttpServletResponse response;
    private boolean closed;

    public Emitter(@NotNull HttpServletResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.response = response;
    }

    public final boolean getClosed() {
        return this.closed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public final void emit(@NotNull String event, @NotNull InputStream data, @Nullable String id) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(data, "data");
        Emitter emitter = this;
        synchronized (emitter) {
            boolean bl = false;
            try {
                void $this$useLines$iv;
                if (id != null) {
                    this.write("id: " + id + "\n");
                }
                this.write("event: " + event + "\n");
                Closeable closeable = data;
                int n = 8192;
                closeable = closeable instanceof BufferedInputStream ? (BufferedInputStream)closeable : new BufferedInputStream((InputStream)closeable, n);
                Charset charset = Charsets.UTF_8;
                closeable = new InputStreamReader((InputStream)closeable, charset);
                boolean $i$f$useLines = false;
                Closeable closeable2 = $this$useLines$iv;
                int n2 = 8192;
                closeable2 = closeable2 instanceof BufferedReader ? (BufferedReader)closeable2 : new BufferedReader((Reader)closeable2, n2);
                Throwable throwable = null;
                try {
                    BufferedReader it$iv = (BufferedReader)closeable2;
                    boolean bl2 = false;
                    Sequence<String> it = TextStreamsKt.lineSequence(it$iv);
                    boolean bl3 = false;
                    Sequence<String> $this$forEach$iv = it;
                    boolean $i$f$forEach = false;
                    Iterator<String> iterator2 = $this$forEach$iv.iterator();
                    while (iterator2.hasNext()) {
                        String element$iv;
                        String line = element$iv = iterator2.next();
                        boolean bl4 = false;
                        this.write("data: " + line + "\n");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally(closeable2, throwable);
                }
                this.write("\n");
                this.response.flushBuffer();
            }
            catch (IOException ignored) {
                this.closed = true;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void emit(@NotNull String comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        try {
            String[] stringArray = new String[]{"\n"};
            Iterable $this$forEach$iv = StringsKt.split$default((CharSequence)comment, stringArray, false, 0, 6, null);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it = (String)element$iv;
                boolean bl = false;
                this.write(": " + it + "\n");
            }
            this.response.flushBuffer();
        }
        catch (IOException ignored) {
            this.closed = true;
        }
    }

    private final void write(String value) {
        this.response.getOutputStream().print(value);
    }
}

