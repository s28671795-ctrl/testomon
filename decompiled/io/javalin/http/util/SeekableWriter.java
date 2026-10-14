/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.servlet.ServletOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J.\u0010\t\u001a\u00020\n*\u00020\u00132\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0018"}, d2={"Lio/javalin/http/util/SeekableWriter;", "", "()V", "chunkSize", "", "getChunkSize", "()I", "setChunkSize", "(I)V", "write", "", "ctx", "Lio/javalin/http/Context;", "inputStream", "Ljava/io/InputStream;", "contentType", "", "totalBytes", "", "Ljava/io/OutputStream;", "from", "to", "buffer", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nSeekableWriter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SeekableWriter.kt\nio/javalin/http/util/SeekableWriter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n766#2:50\n857#2,2:51\n*S KotlinDebug\n*F\n+ 1 SeekableWriter.kt\nio/javalin/http/util/SeekableWriter\n*L\n20#1:50\n20#1:51,2\n*E\n"})
public final class SeekableWriter {
    @NotNull
    public static final SeekableWriter INSTANCE = new SeekableWriter();
    private static int chunkSize = 128000;

    private SeekableWriter() {
    }

    public final int getChunkSize() {
        return chunkSize;
    }

    public final void setChunkSize(int n) {
        chunkSize = n;
    }

    public final void write(@NotNull Context ctx, @NotNull InputStream inputStream2, @NotNull String contentType, long totalBytes) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(inputStream2, "inputStream");
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        ctx.async(() -> SeekableWriter.write$lambda$1(ctx, contentType, inputStream2, totalBytes));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void write(OutputStream $this$write, InputStream inputStream2, long from, long to, byte[] buffer) {
        Closeable closeable = inputStream2;
        Throwable throwable = null;
        try {
            int read;
            long skipped;
            InputStream it = (InputStream)closeable;
            boolean bl = false;
            for (long toSkip = from; toSkip > 0L; toSkip -= skipped) {
                skipped = it.skip(toSkip);
            }
            for (long bytesLeft = to - from + 1L; bytesLeft != 0L; bytesLeft -= (long)read) {
                read = it.read(buffer, 0, (int)RangesKt.coerceAtMost((long)buffer.length, bytesLeft));
                $this$write.write(buffer, 0, read);
            }
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

    static /* synthetic */ void write$default(SeekableWriter seekableWriter, OutputStream outputStream2, InputStream inputStream2, long l, long l2, byte[] byArray, int n, Object object) {
        if ((n & 8) != 0) {
            byArray = new byte[1024];
        }
        seekableWriter.write(outputStream2, inputStream2, l, l2, byArray);
    }

    /*
     * WARNING - void declaration
     */
    private static final void write$lambda$1(Context $ctx, String $contentType, InputStream $inputStream, long $totalBytes) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($ctx, "$ctx");
        Intrinsics.checkNotNullParameter($contentType, "$contentType");
        Intrinsics.checkNotNullParameter($inputStream, "$inputStream");
        ServletOutputStream uncompressedStream = $ctx.res().getOutputStream();
        if ($ctx.header("Range") == null) {
            $ctx.header("Content-Type", $contentType);
            $inputStream.transferTo(uncompressedStream);
            $inputStream.close();
            return;
        }
        String string = $ctx.header("Range");
        Intrinsics.checkNotNull(string);
        String[] stringArray = new String[]{"="};
        CharSequence charSequence = (CharSequence)StringsKt.split$default((CharSequence)string, stringArray, false, 0, 6, null).get(1);
        stringArray = new String[]{"-"};
        Iterable $this$filter$iv = StringsKt.split$default(charSequence, stringArray, false, 0, 6, null);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            String it = (String)element$iv$iv;
            boolean bl = false;
            boolean bl2 = ((CharSequence)it).length() > 0;
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List requestedRange = (List)destination$iv$iv;
        long from = Long.parseLong((String)requestedRange.get(0));
        long to = from + (long)chunkSize > $totalBytes ? $totalBytes - 1L : (requestedRange.size() == 2 ? Long.parseLong((String)requestedRange.get(1)) : from + (long)chunkSize - 1L);
        $ctx.status(HttpStatus.PARTIAL_CONTENT);
        $ctx.header("Content-Type", $contentType);
        $ctx.header("Accept-Ranges", "bytes");
        $ctx.header("Content-Range", "bytes " + from + "-" + to + "/" + $totalBytes);
        $ctx.header("Content-Length", String.valueOf(Math.min(to - from + 1L, $totalBytes)));
        Intrinsics.checkNotNullExpressionValue(uncompressedStream, "uncompressedStream");
        SeekableWriter.write$default(INSTANCE, uncompressedStream, $inputStream, from, to, null, 8, null);
    }
}

