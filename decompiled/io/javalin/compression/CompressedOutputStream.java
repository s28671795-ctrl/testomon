/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import io.javalin.compression.CompressedStreamKt;
import io.javalin.compression.CompressionStrategy;
import io.javalin.compression.Compressor;
import io.javalin.http.Context;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J \u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001aH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\n \u000e*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lio/javalin/compression/CompressedOutputStream;", "Ljakarta/servlet/ServletOutputStream;", "compression", "Lio/javalin/compression/CompressionStrategy;", "ctx", "Lio/javalin/http/Context;", "(Lio/javalin/compression/CompressionStrategy;Lio/javalin/http/Context;)V", "compressedStream", "Ljava/io/OutputStream;", "getCompression", "()Lio/javalin/compression/CompressionStrategy;", "getCtx", "()Lio/javalin/http/Context;", "originStream", "kotlin.jvm.PlatformType", "close", "", "isReady", "", "setWriteListener", "writeListener", "Ljakarta/servlet/WriteListener;", "write", "bytes", "", "offset", "", "length", "byte", "javalin"})
public final class CompressedOutputStream
extends ServletOutputStream {
    @NotNull
    private final CompressionStrategy compression;
    @NotNull
    private final Context ctx;
    private final ServletOutputStream originStream;
    @Nullable
    private OutputStream compressedStream;

    public CompressedOutputStream(@NotNull CompressionStrategy compression, @NotNull Context ctx) {
        Intrinsics.checkNotNullParameter(compression, "compression");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        this.compression = compression;
        this.ctx = ctx;
        this.originStream = this.ctx.res().getOutputStream();
    }

    @NotNull
    public final CompressionStrategy getCompression() {
        return this.compression;
    }

    @NotNull
    public final Context getCtx() {
        return this.ctx;
    }

    @Override
    public void write(@NotNull byte[] bytes, int offset, int length) {
        OutputStream outputStream2;
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (this.compressedStream == null && length >= this.compression.getMinSizeForCompression() && CompressedStreamKt.access$allowsForCompression(this.compression, this.ctx.res().getContentType()) && !this.ctx.res().containsHeader("Content-Encoding")) {
            Compressor compressor = CompressedStreamKt.access$tryMatchCompression(this.compression, this.ctx);
            if (compressor != null) {
                Compressor compressor2;
                Compressor it = compressor2 = compressor;
                boolean bl = false;
                ServletOutputStream servletOutputStream = this.originStream;
                Intrinsics.checkNotNullExpressionValue(servletOutputStream, "originStream");
                this.compressedStream = it.compress(servletOutputStream);
                this.ctx.header("Content-Encoding", it.encoding());
            }
        }
        if ((outputStream2 = this.compressedStream) == null) {
            outputStream2 = this.originStream;
        }
        outputStream2.write(bytes, offset, length);
    }

    @Override
    public void write(int n) {
        this.originStream.write(n);
    }

    @Override
    public void setWriteListener(@Nullable WriteListener writeListener) {
        this.originStream.setWriteListener(writeListener);
    }

    @Override
    public boolean isReady() {
        return this.originStream.isReady();
    }

    @Override
    public void close() {
        block0: {
            OutputStream outputStream2 = this.compressedStream;
            if (outputStream2 == null) break block0;
            outputStream2.close();
        }
    }
}

