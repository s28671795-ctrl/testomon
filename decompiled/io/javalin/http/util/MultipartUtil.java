/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.util;

import io.javalin.http.UploadedFile;
import io.javalin.http.util.MultipartUtil;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.Closeable;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00110\u00102\u0006\u0010\u0012\u001a\u00020\tJ\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J \u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00110\u00102\u0006\u0010\u0012\u001a\u00020\tJ\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00112\u0006\u0010\u0012\u001a\u00020\tJ\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u001d"}, d2={"Lio/javalin/http/util/MultipartUtil;", "", "()V", "MULTIPART_CONFIG_ATTRIBUTE", "", "defaultConfig", "Ljakarta/servlet/MultipartConfigElement;", "preUploadFunction", "Lkotlin/Function1;", "Ljakarta/servlet/http/HttpServletRequest;", "", "getPreUploadFunction", "()Lkotlin/jvm/functions/Function1;", "setPreUploadFunction", "(Lkotlin/jvm/functions/Function1;)V", "getFieldMap", "", "", "req", "getPartValue", "partName", "getUploadedFileMap", "Lio/javalin/http/UploadedFile;", "getUploadedFiles", "isField", "", "filePart", "Ljakarta/servlet/http/Part;", "isFile", "javalin"})
@SourceDebugExtension(value={"SMAP\nMultipartUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MultipartUtil.kt\nio/javalin/http/util/MultipartUtil\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n766#2:59\n857#2,2:60\n1549#2:62\n1620#2,3:63\n766#2:66\n857#2,2:67\n1549#2:69\n1620#2,3:70\n766#2:73\n857#2,2:74\n1477#2:76\n1502#2,3:77\n1505#2,3:87\n1238#2,2:92\n1549#2:94\n1620#2,3:95\n1241#2:98\n1179#2,2:99\n1253#2,4:101\n766#2:105\n857#2,2:106\n1549#2:108\n1620#2,2:109\n1622#2:112\n361#3,7:80\n442#3:90\n392#3:91\n1#4:111\n*S KotlinDebug\n*F\n+ 1 MultipartUtil.kt\nio/javalin/http/util/MultipartUtil\n*L\n27#1:59\n27#1:60,2\n27#1:62\n27#1:63,3\n32#1:66\n32#1:67,2\n32#1:69\n32#1:70,3\n39#1:73\n39#1:74,2\n40#1:76\n40#1:77,3\n40#1:87,3\n41#1:92,2\n41#1:94\n41#1:95,3\n41#1:98\n46#1:99,2\n46#1:101,4\n50#1:105\n50#1:106,2\n50#1:108\n50#1:109,2\n50#1:112\n40#1:80,7\n41#1:90\n41#1:91\n*E\n"})
public final class MultipartUtil {
    @NotNull
    public static final MultipartUtil INSTANCE = new MultipartUtil();
    @NotNull
    public static final String MULTIPART_CONFIG_ATTRIBUTE = "org.eclipse.jetty.multipartConfig";
    @NotNull
    private static final MultipartConfigElement defaultConfig = new MultipartConfigElement(System.getProperty("java.io.tmpdir"), -1L, -1L, 1);
    @NotNull
    private static Function1<? super HttpServletRequest, Unit> preUploadFunction = preUploadFunction.1.INSTANCE;

    private MultipartUtil() {
    }

    @NotNull
    public final Function1<HttpServletRequest, Unit> getPreUploadFunction() {
        return preUploadFunction;
    }

    public final void setPreUploadFunction(@NotNull Function1<? super HttpServletRequest, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        preUploadFunction = function1;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<UploadedFile> getUploadedFiles(@NotNull HttpServletRequest req, @NotNull String partName) {
        void $this$mapTo$iv$iv;
        Part it;
        Iterable $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(req, "req");
        Intrinsics.checkNotNullParameter(partName, "partName");
        preUploadFunction.invoke(req);
        Collection<Part> collection = req.getParts();
        Intrinsics.checkNotNullExpressionValue(collection, "req.parts");
        Iterable $this$filter$iv = collection;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Part)element$iv$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (!(INSTANCE.isFile(it) && Intrinsics.areEqual(it.getName(), partName))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (Part)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            collection2.add(new UploadedFile(it));
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<UploadedFile> getUploadedFiles(@NotNull HttpServletRequest req) {
        void $this$mapTo$iv$iv;
        Part p0;
        Iterable $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(req, "req");
        preUploadFunction.invoke(req);
        Collection<Part> collection = req.getParts();
        Intrinsics.checkNotNullExpressionValue(collection, "req.parts");
        Iterable $this$filter$iv = collection;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            p0 = (Part)element$iv$iv;
            boolean bl = false;
            if (!this.isFile(p0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            p0 = (Part)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            collection2.add(new UploadedFile((Part)it));
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Map<String, List<UploadedFile>> getUploadedFileMap(@NotNull HttpServletRequest req) {
        void $this$mapValuesTo$iv$iv;
        Object value$iv$iv$iv;
        Object key$iv$iv;
        Object $this$groupByTo$iv$iv;
        Iterable $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(req, "req");
        preUploadFunction.invoke(req);
        Collection<Part> collection = req.getParts();
        Intrinsics.checkNotNullExpressionValue(collection, "req\n            .parts");
        Iterable $this$filter$iv = collection;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Object destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Part p0 = (Part)element$iv$iv;
            boolean bl = false;
            if (!this.isFile(p0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$groupBy$iv = (List)destination$iv$iv;
        boolean $i$f$groupBy = false;
        $this$filterTo$iv$iv = $this$groupBy$iv;
        destination$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        Iterator iterator2 = $this$groupByTo$iv$iv.iterator();
        while (iterator2.hasNext()) {
            Object object;
            Object element$iv$iv;
            element$iv$iv = iterator2.next();
            Part it = (Part)element$iv$iv;
            boolean bl = false;
            key$iv$iv = it.getName();
            Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
            boolean $i$f$getOrPut = false;
            value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                boolean bl2 = false;
                List answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                object = answer$iv$iv$iv;
            } else {
                object = value$iv$iv$iv;
            }
            List list$iv$iv = (List)object;
            list$iv$iv.add(element$iv$iv);
        }
        Object $this$mapValues$iv = destination$iv$iv;
        boolean $i$f$mapValues = false;
        $this$groupByTo$iv$iv = $this$mapValues$iv;
        destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
        boolean $i$f$mapValuesTo = false;
        Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            void $this$mapTo$iv$iv;
            void entry;
            void it$iv$iv;
            key$iv$iv = (Map.Entry)element$iv$iv$iv;
            Object object = destination$iv$iv;
            boolean bl = false;
            value$iv$iv$iv = (Map.Entry)element$iv$iv$iv;
            Object k = it$iv$iv.getKey();
            Object object2 = object;
            boolean bl3 = false;
            Iterable $this$map$iv = (Iterable)entry.getValue();
            boolean $i$f$map = false;
            Iterable iterable2 = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Part part = (Part)item$iv$iv;
                Collection collection2 = destination$iv$iv2;
                boolean bl4 = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                collection2.add(new UploadedFile((Part)it));
            }
            List list = (List)destination$iv$iv2;
            object2.put(k, list);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Map<String, List<String>> getFieldMap(@NotNull HttpServletRequest req) {
        void $this$associateTo$iv$iv;
        Intrinsics.checkNotNullParameter(req, "req");
        preUploadFunction.invoke(req);
        Collection<Part> collection = req.getParts();
        Intrinsics.checkNotNullExpressionValue(collection, "req.parts");
        Iterable $this$associate$iv = collection;
        boolean $i$f$associate = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
        Iterable iterable = $this$associate$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Object element$iv$iv : $this$associateTo$iv$iv) {
            Map map = destination$iv$iv;
            Part part = (Part)element$iv$iv;
            boolean bl = false;
            String string = part.getName();
            String string2 = part.getName();
            Intrinsics.checkNotNullExpressionValue(string2, "part.name");
            Pair<String, List<String>> pair = TuplesKt.to(string, INSTANCE.getPartValue(req, string2));
            map.put(pair.getFirst(), pair.getSecond());
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private final List<String> getPartValue(HttpServletRequest req, String partName) {
        void $this$mapTo$iv$iv;
        Part it;
        Iterable $this$filterTo$iv$iv;
        Collection<Part> collection = req.getParts();
        Intrinsics.checkNotNullExpressionValue(collection, "req.parts");
        Iterable $this$filter$iv = collection;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Part)element$iv$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (!(INSTANCE.isField(it) && Intrinsics.areEqual(it.getName(), partName))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String string;
            void part;
            it = (Part)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            Closeable closeable = part.getInputStream();
            Throwable throwable = null;
            try {
                Charset charset;
                InputStream it2 = (InputStream)closeable;
                boolean bl2 = false;
                Intrinsics.checkNotNullExpressionValue(it2, "it");
                byte[] byArray = ByteStreamsKt.readBytes(it2);
                Intrinsics.checkNotNullExpressionValue(Charset.forName("UTF-8"), "forName(\"UTF-8\")");
                string = new String(byArray, charset);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally(closeable, throwable);
            }
            collection2.add(string);
        }
        return CollectionsKt.toList((List)destination$iv$iv);
    }

    private final boolean isField(Part filePart) {
        return filePart.getSubmittedFileName() == null;
    }

    private final boolean isFile(Part filePart) {
        return !this.isField(filePart);
    }

    public static final /* synthetic */ MultipartConfigElement access$getDefaultConfig$p() {
        return defaultConfig;
    }
}

