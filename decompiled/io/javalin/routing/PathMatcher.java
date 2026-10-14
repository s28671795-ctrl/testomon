/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import io.javalin.http.HandlerType;
import io.javalin.routing.HandlerEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u001b\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\r\u001a\u00020\u0005H\u0000\u00a2\u0006\u0002\b\u0011J\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u0014J\u0018\u0010\u0015\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u000fH\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lio/javalin/routing/PathMatcher;", "", "()V", "handlerEntries", "", "Lio/javalin/http/HandlerType;", "", "Lio/javalin/routing/HandlerEntry;", "add", "", "entry", "findEntries", "", "handlerType", "requestUri", "", "getAllEntriesOfType", "getAllEntriesOfType$javalin", "hasEntries", "", "hasEntries$javalin", "match", "requestPath", "javalin"})
@SourceDebugExtension(value={"SMAP\nPathMatcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PathMatcher.kt\nio/javalin/routing/PathMatcher\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n9646#2,4:41\n1#3:45\n766#4:46\n857#4,2:47\n1747#4,3:49\n*S KotlinDebug\n*F\n+ 1 PathMatcher.kt\nio/javalin/routing/PathMatcher\n*L\n15#1:41,4\n25#1:46\n25#1:47,2\n28#1:49,3\n*E\n"})
public final class PathMatcher {
    @NotNull
    private final Map<HandlerType, List<HandlerEntry>> handlerEntries;

    /*
     * WARNING - void declaration
     */
    public PathMatcher() {
        void $this$associateWithTo$iv;
        HandlerType[] handlerTypeArray = HandlerType.values();
        Map map = new EnumMap(HandlerType.class);
        PathMatcher pathMatcher = this;
        boolean $i$f$associateWithTo = false;
        int n = ((void)$this$associateWithTo$iv).length;
        for (int i = 0; i < n; ++i) {
            void destination$iv;
            void element$iv;
            void var7_8 = element$iv = $this$associateWithTo$iv[i];
            void var11_11 = element$iv;
            void var10_10 = destination$iv;
            boolean bl = false;
            ArrayList arrayList = new ArrayList();
            var10_10.put(var11_11, arrayList);
        }
        pathMatcher.handlerEntries = map;
    }

    public final void add(@NotNull HandlerEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        if (entry.getType().isHttpMethod()) {
            Object v1;
            block3: {
                List<HandlerEntry> list = this.handlerEntries.get((Object)entry.getType());
                Intrinsics.checkNotNull(list);
                Iterable iterable = list;
                for (Object t2 : iterable) {
                    HandlerEntry it = (HandlerEntry)t2;
                    boolean bl = false;
                    if (!(it.getType() == entry.getType() && Intrinsics.areEqual(it.getPath(), entry.getPath()))) continue;
                    v1 = t2;
                    break block3;
                }
                v1 = null;
            }
            if (v1 != null) {
                throw new IllegalArgumentException("Handler with type='" + entry.getType() + "' and path='" + entry.getPath() + "' already exists.");
            }
        }
        List<HandlerEntry> list = this.handlerEntries.get((Object)entry.getType());
        Intrinsics.checkNotNull(list);
        list.add(entry);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<HandlerEntry> findEntries(@NotNull HandlerType handlerType, @NotNull String requestUri) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        Intrinsics.checkNotNullParameter(requestUri, "requestUri");
        List<HandlerEntry> list = this.handlerEntries.get((Object)handlerType);
        Intrinsics.checkNotNull(list);
        Iterable $this$filter$iv = list;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            HandlerEntry he = (HandlerEntry)element$iv$iv;
            boolean bl = false;
            if (!this.match(he, requestUri)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    public final boolean hasEntries$javalin(@NotNull HandlerType handlerType, @NotNull String requestUri) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
            Intrinsics.checkNotNullParameter(requestUri, "requestUri");
            List<HandlerEntry> list = this.handlerEntries.get((Object)handlerType);
            Intrinsics.checkNotNull(list);
            Iterable $this$any$iv = list;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    HandlerEntry entry = (HandlerEntry)element$iv;
                    boolean bl2 = false;
                    if (!this.match(entry, requestUri)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    @NotNull
    public final List<HandlerEntry> getAllEntriesOfType$javalin(@NotNull HandlerType handlerType) {
        Intrinsics.checkNotNullParameter((Object)handlerType, "handlerType");
        List<HandlerEntry> list = this.handlerEntries.get((Object)handlerType);
        Intrinsics.checkNotNull(list);
        return list;
    }

    private final boolean match(HandlerEntry entry, String requestPath) {
        String string = entry.getPath();
        return Intrinsics.areEqual(string, "*") ? true : (Intrinsics.areEqual(string, requestPath) ? true : entry.matches(requestPath));
    }
}

