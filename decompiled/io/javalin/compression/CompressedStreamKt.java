/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.compression;

import io.javalin.compression.CompressionStrategy;
import io.javalin.compression.Compressor;
import io.javalin.http.Context;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0016\u0010\u0006\u001a\u00020\u0007*\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002\u00a8\u0006\n"}, d2={"tryMatchCompression", "Lio/javalin/compression/Compressor;", "compression", "Lio/javalin/compression/CompressionStrategy;", "ctx", "Lio/javalin/http/Context;", "allowsForCompression", "", "contentType", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nCompressedStream.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CompressedStream.kt\nio/javalin/compression/CompressedStreamKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,45:1\n288#2,2:46\n2624#2,3:48\n*S KotlinDebug\n*F\n+ 1 CompressedStream.kt\nio/javalin/compression/CompressedStreamKt\n*L\n38#1:46,2\n43#1:48,3\n*E\n"})
public final class CompressedStreamKt {
    private static final Compressor tryMatchCompression(CompressionStrategy compression, Context ctx) {
        Compressor compressor;
        String string = ctx.header("Accept-Encoding");
        if (string != null) {
            Object v1;
            block3: {
                String acceptedEncoding = string;
                boolean bl = false;
                Iterable $this$firstOrNull$iv = compression.getCompressors();
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    Compressor it = (Compressor)element$iv;
                    boolean bl2 = false;
                    if (!StringsKt.contains((CharSequence)acceptedEncoding, it.encoding(), true)) continue;
                    v1 = element$iv;
                    break block3;
                }
                v1 = null;
            }
            compressor = v1;
        } else {
            compressor = null;
        }
        return compressor;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final boolean allowsForCompression(CompressionStrategy $this$allowsForCompression, String contentType) {
        String excluded;
        if (contentType == null) return true;
        Iterable $this$none$iv = $this$allowsForCompression.getExcludedMimeTypesFromCompression();
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return true;
        }
        Iterator iterator2 = $this$none$iv.iterator();
        do {
            if (!iterator2.hasNext()) return true;
            Object element$iv = iterator2.next();
            excluded = (String)element$iv;
            boolean bl = false;
        } while (!StringsKt.contains((CharSequence)contentType, excluded, true));
        return false;
    }

    public static final /* synthetic */ boolean access$allowsForCompression(CompressionStrategy $receiver, String contentType) {
        return CompressedStreamKt.allowsForCompression($receiver, contentType);
    }

    public static final /* synthetic */ Compressor access$tryMatchCompression(CompressionStrategy compression, Context ctx) {
        return CompressedStreamKt.tryMatchCompression(compression, ctx);
    }
}

