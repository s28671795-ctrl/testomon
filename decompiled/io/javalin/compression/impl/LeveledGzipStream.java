/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression.impl;

import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/compression/impl/LeveledGzipStream;", "Ljava/util/zip/GZIPOutputStream;", "out", "Ljava/io/OutputStream;", "level", "", "(Ljava/io/OutputStream;I)V", "javalin"})
public final class LeveledGzipStream
extends GZIPOutputStream {
    public LeveledGzipStream(@NotNull OutputStream out, int level) {
        Intrinsics.checkNotNullParameter(out, "out");
        super(out);
        this.def.setLevel(level);
    }
}

