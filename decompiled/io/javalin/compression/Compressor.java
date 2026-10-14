/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import java.io.OutputStream;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H\u0016\u00f8\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001\u00a8\u0006\b\u00c0\u0006\u0001"}, d2={"Lio/javalin/compression/Compressor;", "", "compress", "Ljava/io/OutputStream;", "out", "encoding", "", "extension", "javalin"})
public interface Compressor {
    @NotNull
    public String encoding();

    @NotNull
    default public String extension() {
        return "";
    }

    @NotNull
    public OutputStream compress(@NotNull OutputStream var1);
}

