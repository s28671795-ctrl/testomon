/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.aayushatharva.brotli4j.Brotli4jLoader
 *  com.nixxcode.jvmbrotli.common.BrotliLoader
 */
package io.javalin.compression;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.nixxcode.jvmbrotli.common.BrotliLoader;
import io.javalin.compression.Brotli;
import io.javalin.compression.Compressor;
import io.javalin.compression.Gzip;
import io.javalin.compression.impl.Brotli4jCompressor;
import io.javalin.compression.impl.GzipCompressor;
import io.javalin.compression.impl.JvmBrotliCompressor;
import io.javalin.util.CoreDependency;
import io.javalin.util.DependencyUtil;
import io.javalin.util.JavalinLogger;
import io.javalin.util.Util;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lio/javalin/compression/CompressionStrategy;", "", "brotli", "Lio/javalin/compression/Brotli;", "gzip", "Lio/javalin/compression/Gzip;", "(Lio/javalin/compression/Brotli;Lio/javalin/compression/Gzip;)V", "compressors", "", "Lio/javalin/compression/Compressor;", "getCompressors", "()Ljava/util/List;", "excludedMimeTypesFromCompression", "", "getExcludedMimeTypesFromCompression", "setExcludedMimeTypesFromCompression", "(Ljava/util/List;)V", "minSizeForCompression", "", "getMinSizeForCompression", "()I", "setMinSizeForCompression", "(I)V", "tryLoadBrotli", "Companion", "javalin"})
@SourceDebugExtension(value={"SMAP\nCompressionStrategy.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CompressionStrategy.kt\nio/javalin/compression/CompressionStrategy\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,113:1\n1#2:114\n*E\n"})
public final class CompressionStrategy {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<Compressor> compressors;
    private int minSizeForCompression;
    @NotNull
    private List<String> excludedMimeTypesFromCompression;
    @JvmField
    @NotNull
    public static final CompressionStrategy NONE = new CompressionStrategy(null, null, 3, null);
    @JvmField
    @NotNull
    public static final CompressionStrategy GZIP = new CompressionStrategy(null, new Gzip(0, 1, null));

    public CompressionStrategy(@Nullable Brotli brotli, @Nullable Gzip gzip) {
        List comp = new ArrayList();
        if (brotli != null) {
            Compressor compressor = this.tryLoadBrotli(brotli);
            if (compressor != null) {
                Compressor it = compressor;
                boolean bl = false;
                comp.add(it);
            }
        }
        if (gzip != null) {
            comp.add(new GzipCompressor(gzip.getLevel()));
        }
        this.compressors = CollectionsKt.toList(comp);
        this.minSizeForCompression = 1500;
        String[] stringArray = new String[]{"image/", "audio/", "video/", "application/compress", "application/zip", "application/gzip", "application/bzip2", "application/brotli", "application/x-xz", "application/x-rar-compressed"};
        this.excludedMimeTypesFromCompression = CollectionsKt.listOf(stringArray);
    }

    public /* synthetic */ CompressionStrategy(Brotli brotli, Gzip gzip, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            brotli = null;
        }
        if ((n & 2) != 0) {
            gzip = null;
        }
        this(brotli, gzip);
    }

    @NotNull
    public final List<Compressor> getCompressors() {
        return this.compressors;
    }

    public final int getMinSizeForCompression() {
        return this.minSizeForCompression;
    }

    public final void setMinSizeForCompression(int n) {
        this.minSizeForCompression = n;
    }

    @NotNull
    public final List<String> getExcludedMimeTypesFromCompression() {
        return this.excludedMimeTypesFromCompression;
    }

    public final void setExcludedMimeTypesFromCompression(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.excludedMimeTypesFromCompression = list;
    }

    private final Compressor tryLoadBrotli(Brotli brotli) {
        if (!Companion.brotliJvmPresent() && !Companion.brotli4jPresent()) {
            throw new IllegalStateException(DependencyUtil.INSTANCE.missingDependencyMessage(CoreDependency.BROTLI4J));
        }
        if (Brotli4jLoader.isAvailable()) {
            return new Brotli4jCompressor(brotli.getLevel());
        }
        if (BrotliLoader.isBrotliAvailable()) {
            return new JvmBrotliCompressor(brotli.getLevel());
        }
        JavalinLogger.warn$default("\nFailed to enable Brotli compression, because the brotli4j native library couldn't be loaded.\nbrotli4j is currently only supported on Windows, Linux and Mac OSX.\nIf you are running Javalin on a supported system, but are still getting this error,\ntry re-importing your Maven and/or Gradle dependencies. If that doesn't resolve it,\nplease create an issue at https://github.com/javalin/javalin/\n---------------------------------------------------------------\nIf you still want compression, please ensure GZIP is enabled!\n---------------------------------------------------------------", null, 2, null);
        return null;
    }

    public CompressionStrategy() {
        this(null, null, 3, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007J\u0006\u0010\b\u001a\u00020\u0007J\u0006\u0010\t\u001a\u00020\u0007J\u0006\u0010\n\u001a\u00020\u0007J\u0006\u0010\u000b\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lio/javalin/compression/CompressionStrategy$Companion;", "", "()V", "GZIP", "Lio/javalin/compression/CompressionStrategy;", "NONE", "brotli4jAvailable", "", "brotli4jPresent", "brotliImplAvailable", "brotliJvmAvailable", "brotliJvmPresent", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        public final boolean brotliJvmPresent() {
            return Util.INSTANCE.classExists(CoreDependency.JVMBROTLI.getTestClass());
        }

        public final boolean brotli4jPresent() {
            return Util.INSTANCE.classExists(CoreDependency.BROTLI4J.getTestClass());
        }

        public final boolean brotliJvmAvailable() {
            boolean bl;
            try {
                bl = BrotliLoader.isBrotliAvailable();
            }
            catch (Throwable t2) {
                bl = false;
            }
            return bl;
        }

        public final boolean brotli4jAvailable() {
            boolean bl;
            try {
                bl = Brotli4jLoader.isAvailable();
            }
            catch (Throwable t2) {
                bl = false;
            }
            return bl;
        }

        public final boolean brotliImplAvailable() {
            return this.brotliJvmPresent() && this.brotliJvmAvailable() || this.brotli4jPresent() && this.brotli4jAvailable();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

