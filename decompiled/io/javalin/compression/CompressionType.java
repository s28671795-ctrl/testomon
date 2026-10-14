/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0001\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0003R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000f"}, d2={"Lio/javalin/compression/CompressionType;", "", "typeName", "", "extension", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getExtension", "()Ljava/lang/String;", "getTypeName", "acceptEncoding", "", "GZIP", "BR", "NONE", "Companion", "javalin"})
public final class CompressionType
extends Enum<CompressionType> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final String typeName;
    @NotNull
    private final String extension;
    public static final /* enum */ CompressionType GZIP;
    public static final /* enum */ CompressionType BR;
    public static final /* enum */ CompressionType NONE;
    private static final /* synthetic */ CompressionType[] $VALUES;

    private CompressionType(String typeName, String extension) {
        this.typeName = typeName;
        this.extension = extension;
    }

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    @NotNull
    public final String getExtension() {
        return this.extension;
    }

    public final boolean acceptEncoding(@NotNull String acceptEncoding) {
        Intrinsics.checkNotNullParameter(acceptEncoding, "acceptEncoding");
        return StringsKt.contains((CharSequence)acceptEncoding, this.typeName, true);
    }

    public static CompressionType[] values() {
        return (CompressionType[])$VALUES.clone();
    }

    public static CompressionType valueOf(String value) {
        return Enum.valueOf(CompressionType.class, value);
    }

    static {
        GZIP = new CompressionType("gzip", ".gz");
        BR = new CompressionType("br", ".br");
        NONE = new CompressionType("", "");
        $VALUES = compressionTypeArray = new CompressionType[]{CompressionType.GZIP, CompressionType.BR, CompressionType.NONE};
        Companion = new Companion(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/compression/CompressionType$Companion;", "", "()V", "getByAcceptEncoding", "Lio/javalin/compression/CompressionType;", "acceptEncoding", "", "javalin"})
    @SourceDebugExtension(value={"SMAP\nCompressionType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CompressionType.kt\nio/javalin/compression/CompressionType$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,18:1\n1#2:19\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final CompressionType getByAcceptEncoding(@NotNull String acceptEncoding) {
            CompressionType compressionType;
            CompressionType compressionType2;
            block2: {
                Intrinsics.checkNotNullParameter(acceptEncoding, "acceptEncoding");
                CompressionType[] compressionTypeArray = CompressionType.values();
                int n = compressionTypeArray.length;
                for (int i = 0; i < n; ++i) {
                    CompressionType compressionType3;
                    CompressionType it = compressionType3 = compressionTypeArray[i];
                    boolean bl = false;
                    if (!it.acceptEncoding(acceptEncoding)) continue;
                    compressionType2 = compressionType3;
                    break block2;
                }
                compressionType2 = compressionType = null;
            }
            if (compressionType2 == null) {
                compressionType = NONE;
            }
            return compressionType;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

