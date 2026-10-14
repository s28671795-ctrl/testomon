/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import io.javalin.compression.Compressor;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2={"forType", "Lio/javalin/compression/Compressor;", "", "type", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nCompressionStrategy.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CompressionStrategy.kt\nio/javalin/compression/CompressionStrategyKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n288#2,2:114\n*S KotlinDebug\n*F\n+ 1 CompressionStrategy.kt\nio/javalin/compression/CompressionStrategyKt\n*L\n112#1:114,2\n*E\n"})
public final class CompressionStrategyKt {
    @Nullable
    public static final Compressor forType(@NotNull List<? extends Compressor> $this$forType, @NotNull String type) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter($this$forType, "<this>");
            Intrinsics.checkNotNullParameter(type, "type");
            Iterable $this$firstOrNull$iv = $this$forType;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Compressor it = (Compressor)element$iv;
                boolean bl = false;
                if (!StringsKt.equals(it.encoding(), type, true)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }
}

