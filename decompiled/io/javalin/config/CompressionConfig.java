/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.compression.Brotli;
import io.javalin.compression.CompressionStrategy;
import io.javalin.compression.Gzip;
import io.javalin.config.PrivateConfig;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u0007J\u0012\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\bH\u0007J\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ\u0012\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\bH\u0007J\u0006\u0010\u0010\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/config/CompressionConfig;", "", "pvt", "Lio/javalin/config/PrivateConfig;", "(Lio/javalin/config/PrivateConfig;)V", "brotliAndGzip", "", "gzipLevel", "", "brotliLevel", "brotliOnly", "level", "custom", "compressionStrategy", "Lio/javalin/compression/CompressionStrategy;", "gzipOnly", "none", "javalin"})
public final class CompressionConfig {
    @NotNull
    private final PrivateConfig pvt;

    public CompressionConfig(@NotNull PrivateConfig pvt) {
        Intrinsics.checkNotNullParameter(pvt, "pvt");
        this.pvt = pvt;
    }

    public final void custom(@NotNull CompressionStrategy compressionStrategy) {
        Intrinsics.checkNotNullParameter(compressionStrategy, "compressionStrategy");
        this.pvt.compressionStrategy = compressionStrategy;
    }

    @JvmOverloads
    public final void brotliAndGzip(int gzipLevel, int brotliLevel) {
        this.pvt.compressionStrategy = new CompressionStrategy(new Brotli(brotliLevel), new Gzip(gzipLevel));
    }

    public static /* synthetic */ void brotliAndGzip$default(CompressionConfig compressionConfig, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 6;
        }
        if ((n3 & 2) != 0) {
            n2 = 4;
        }
        compressionConfig.brotliAndGzip(n, n2);
    }

    @JvmOverloads
    public final void gzipOnly(int level) {
        this.pvt.compressionStrategy = new CompressionStrategy(null, new Gzip(level));
    }

    public static /* synthetic */ void gzipOnly$default(CompressionConfig compressionConfig, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 6;
        }
        compressionConfig.gzipOnly(n);
    }

    @JvmOverloads
    public final void brotliOnly(int level) {
        this.pvt.compressionStrategy = new CompressionStrategy(new Brotli(level), null);
    }

    public static /* synthetic */ void brotliOnly$default(CompressionConfig compressionConfig, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = 4;
        }
        compressionConfig.brotliOnly(n);
    }

    public final void none() {
        this.pvt.compressionStrategy = new CompressionStrategy(null, null);
    }

    @JvmOverloads
    public final void brotliAndGzip(int gzipLevel) {
        CompressionConfig.brotliAndGzip$default(this, gzipLevel, 0, 2, null);
    }

    @JvmOverloads
    public final void brotliAndGzip() {
        CompressionConfig.brotliAndGzip$default(this, 0, 0, 3, null);
    }

    @JvmOverloads
    public final void gzipOnly() {
        CompressionConfig.gzipOnly$default(this, 0, 1, null);
    }

    @JvmOverloads
    public final void brotliOnly() {
        CompressionConfig.brotliOnly$default(this, 0, 1, null);
    }
}

