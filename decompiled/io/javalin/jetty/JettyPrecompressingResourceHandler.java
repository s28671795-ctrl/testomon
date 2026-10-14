/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.jetty;

import io.javalin.compression.Brotli;
import io.javalin.compression.CompressionStrategy;
import io.javalin.compression.CompressionStrategyKt;
import io.javalin.compression.Compressor;
import io.javalin.compression.Gzip;
import io.javalin.util.JavalinLogger;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.util.resource.Resource;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0005H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0005H\u0002J\u001a\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0019H\u0002J$\u0010\u001f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u00052\b\u0010\u001e\u001a\u0004\u0018\u00010\u0019H\u0002J&\u0010!\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u00128\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lio/javalin/jetty/JettyPrecompressingResourceHandler;", "", "()V", "compressedFiles", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "getCompressedFiles", "()Ljava/util/concurrent/ConcurrentHashMap;", "compressionStrategy", "Lio/javalin/compression/CompressionStrategy;", "getCompressionStrategy", "()Lio/javalin/compression/CompressionStrategy;", "excludedMimeTypes", "", "getExcludedMimeTypes", "()Ljava/util/List;", "resourceMaxSize", "", "clearCache", "", "excludedMimeType", "", "mimeType", "findMatchingCompressor", "Lio/javalin/compression/Compressor;", "contentTypeHeader", "getCompressedByteArray", "resource", "Lorg/eclipse/jetty/util/resource/Resource;", "compressor", "getStaticResourceByteArray", "target", "handle", "req", "Ljakarta/servlet/http/HttpServletRequest;", "res", "Ljakarta/servlet/http/HttpServletResponse;", "javalin"})
@SourceDebugExtension(value={"SMAP\nJettyPrecompressingResourceHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JettyPrecompressingResourceHandler.kt\nio/javalin/jetty/JettyPrecompressingResourceHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,109:1\n1747#2,3:110\n1549#2:113\n1620#2,3:114\n1#3:117\n*S KotlinDebug\n*F\n+ 1 JettyPrecompressingResourceHandler.kt\nio/javalin/jetty/JettyPrecompressingResourceHandler\n*L\n98#1:110,3\n105#1:113\n105#1:114,3\n*E\n"})
public final class JettyPrecompressingResourceHandler {
    @NotNull
    public static final JettyPrecompressingResourceHandler INSTANCE = new JettyPrecompressingResourceHandler();
    @NotNull
    private static final ConcurrentHashMap<String, byte[]> compressedFiles = new ConcurrentHashMap();
    @NotNull
    private static final CompressionStrategy compressionStrategy = CompressionStrategy.Companion.brotliImplAvailable() ? new CompressionStrategy(new Brotli(11), new Gzip(9)) : new CompressionStrategy(null, new Gzip(9));
    @JvmField
    public static int resourceMaxSize = 0x200000;
    @NotNull
    private static final List<String> excludedMimeTypes = new CompressionStrategy(null, null, 3, null).getExcludedMimeTypesFromCompression();

    private JettyPrecompressingResourceHandler() {
    }

    @NotNull
    public final ConcurrentHashMap<String, byte[]> getCompressedFiles() {
        return compressedFiles;
    }

    @JvmStatic
    public static final void clearCache() {
        compressedFiles.clear();
    }

    @NotNull
    public final CompressionStrategy getCompressionStrategy() {
        return compressionStrategy;
    }

    @NotNull
    public final List<String> getExcludedMimeTypes() {
        return excludedMimeTypes;
    }

    public final boolean handle(@NotNull String target, @NotNull Resource resource, @NotNull HttpServletRequest req, @NotNull HttpServletResponse res) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(resource, "resource");
        Intrinsics.checkNotNullParameter(req, "req");
        Intrinsics.checkNotNullParameter(res, "res");
        if (resource.exists() && !resource.isDirectory()) {
            Compressor compressor = null;
            String string = req.getHeader("Accept-Encoding");
            if (string == null) {
                string = "";
            }
            compressor = this.findMatchingCompressor(string);
            String contentType = MimeTypes.getDefaultMimeByExtension(target);
            if (contentType == null || this.excludedMimeType(contentType)) {
                compressor = null;
            }
            byte[] byArray = this.getStaticResourceByteArray(resource, target, compressor);
            if (byArray == null) {
                return false;
            }
            byte[] resultByteArray = byArray;
            res.setContentLength(resultByteArray.length);
            res.setHeader("Content-Type", contentType);
            Compressor compressor2 = compressor;
            if (compressor2 != null) {
                Compressor it = compressor2;
                boolean bl = false;
                res.setHeader("Content-Encoding", compressor.encoding());
            }
            String weakETag = resource.getWeakETag();
            String string2 = req.getHeader("If-None-Match");
            if (string2 != null) {
                String etag = string2;
                boolean bl = false;
                if (Intrinsics.areEqual(etag, weakETag)) {
                    res.setStatus(304);
                    return true;
                }
            }
            res.setHeader("ETag", weakETag);
            InputStream inputStream2 = new ByteArrayInputStream(resultByteArray);
            ServletOutputStream servletOutputStream = res.getOutputStream();
            Intrinsics.checkNotNullExpressionValue(servletOutputStream, "res.outputStream");
            ByteStreamsKt.copyTo$default(inputStream2, servletOutputStream, 0, 2, null);
            res.getOutputStream().close();
            return true;
        }
        return false;
    }

    private final byte[] getStaticResourceByteArray(Resource resource, String target, Compressor compressor) {
        if (resource.length() > (long)resourceMaxSize) {
            JavalinLogger.warn$default("Static file '" + target + "' is larger than configured max size for pre-compression (" + resourceMaxSize + " bytes).\nYou can configure the max size with `JettyPrecompressingResourceHandler.resourceMaxSize = newMaxSize`.", null, 2, null);
            return null;
        }
        Object object = compressor;
        if (object == null || (object = object.extension()) == null) {
            object = "";
        }
        Object ext = object;
        return compressedFiles.computeIfAbsent(target + (String)ext, arg_0 -> JettyPrecompressingResourceHandler.getStaticResourceByteArray$lambda$2(new Function1<String, byte[]>(resource, compressor){
            final /* synthetic */ Resource $resource;
            final /* synthetic */ Compressor $compressor;
            {
                this.$resource = $resource;
                this.$compressor = $compressor;
                super(1);
            }

            @NotNull
            public final byte[] invoke(@NotNull String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return JettyPrecompressingResourceHandler.access$getCompressedByteArray(JettyPrecompressingResourceHandler.INSTANCE, this.$resource, this.$compressor);
            }
        }, arg_0));
    }

    private final byte[] getCompressedByteArray(Resource resource, Compressor compressor) {
        InputStream fileInput = resource.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Object object = compressor;
        if (object == null || (object = object.compress(byteArrayOutputStream)) == null) {
            object = byteArrayOutputStream;
        }
        Object outputStream2 = object;
        Intrinsics.checkNotNullExpressionValue(fileInput, "fileInput");
        ByteStreamsKt.copyTo$default(fileInput, (OutputStream)outputStream2, 0, 2, null);
        fileInput.close();
        ((OutputStream)outputStream2).close();
        byte[] byArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byArray, "byteArrayOutputStream.toByteArray()");
        return byArray;
    }

    private final boolean excludedMimeType(String mimeType) {
        boolean bl;
        block5: {
            if (Intrinsics.areEqual(mimeType, "")) {
                bl = false;
            } else {
                Iterable $this$any$iv = excludedMimeTypes;
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        String excluded = (String)element$iv;
                        boolean bl2 = false;
                        if (!StringsKt.contains((CharSequence)mimeType, excluded, true)) continue;
                        bl = true;
                        break block5;
                    }
                    bl = false;
                }
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final Compressor findMatchingCompressor(String contentTypeHeader) {
        Object object;
        block2: {
            void $this$mapTo$iv$iv;
            String[] stringArray = new String[]{","};
            Iterable $this$map$iv = StringsKt.split$default((CharSequence)contentTypeHeader, stringArray, false, 0, 6, null);
            boolean $i$f$map = false;
            Object object2 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(((Object)StringsKt.trim((CharSequence)it)).toString());
            }
            for (String it : (Iterable)((List)destination$iv$iv)) {
                boolean bl = false;
                object2 = CompressionStrategyKt.forType(compressionStrategy.getCompressors(), it);
                if (object2 == null) continue;
                object = object2;
                break block2;
            }
            object = null;
        }
        return object;
    }

    private static final byte[] getStaticResourceByteArray$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
        return (byte[])$tmp0.invoke(p0);
    }

    public static final /* synthetic */ byte[] access$getCompressedByteArray(JettyPrecompressingResourceHandler $this, Resource resource, Compressor compressor) {
        return $this.getCompressedByteArray(resource, compressor);
    }
}

