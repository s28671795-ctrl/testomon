/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression.impl;

import io.javalin.compression.CompressionType;
import io.javalin.compression.Compressor;
import io.javalin.compression.impl.LeveledBrotliJvmStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\r"}, d2={"Lio/javalin/compression/impl/JvmBrotliCompressor;", "Lio/javalin/compression/Compressor;", "level", "", "(I)V", "getLevel", "()I", "compress", "Ljava/io/OutputStream;", "out", "encoding", "", "extension", "javalin"})
@SourceDebugExtension(value={"SMAP\nJvmBrotliCompressor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JvmBrotliCompressor.kt\nio/javalin/compression/impl/JvmBrotliCompressor\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,23:1\n1#2:24\n*E\n"})
public final class JvmBrotliCompressor
implements Compressor {
    private final int level;

    public JvmBrotliCompressor(int level) {
        this.level = level;
        int n = this.level;
        if (!(0 <= n ? n < 12 : false)) {
            boolean bl = false;
            String string = "Valid range for parameter level is 0 to 11";
            throw new IllegalArgumentException(string.toString());
        }
    }

    public final int getLevel() {
        return this.level;
    }

    @Override
    @NotNull
    public String encoding() {
        return CompressionType.BR.getTypeName();
    }

    @Override
    @NotNull
    public String extension() {
        return CompressionType.BR.getExtension();
    }

    @Override
    @NotNull
    public OutputStream compress(@NotNull OutputStream out) {
        Intrinsics.checkNotNullParameter(out, "out");
        return (OutputStream)((Object)new LeveledBrotliJvmStream(out, this.level));
    }
}

