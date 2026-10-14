/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.websocket;

import io.javalin.websocket.WsEntry;
import io.javalin.websocket.WsHandlerType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u000eJ\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\f2\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lio/javalin/websocket/WsPathMatcher;", "", "()V", "wsHandlerEntries", "Ljava/util/EnumMap;", "Lio/javalin/websocket/WsHandlerType;", "", "Lio/javalin/websocket/WsEntry;", "add", "", "entry", "findAfterHandlerEntries", "", "path", "", "findBeforeHandlerEntries", "findEndpointHandlerEntry", "findEntries", "handlerType", "javalin"})
@SourceDebugExtension(value={"SMAP\nWsPathMatcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WsPathMatcher.kt\nio/javalin/websocket/WsPathMatcher\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,56:1\n9358#2,4:57\n1#3:61\n766#4:62\n857#4,2:63\n*S KotlinDebug\n*F\n+ 1 WsPathMatcher.kt\nio/javalin/websocket/WsPathMatcher\n*L\n32#1:57,4\n54#1:62\n54#1:63,2\n*E\n"})
public final class WsPathMatcher {
    @NotNull
    private final EnumMap<WsHandlerType, List<WsEntry>> wsHandlerEntries;

    /*
     * WARNING - void declaration
     */
    public WsPathMatcher() {
        void $this$associateTo$iv;
        WsHandlerType[] wsHandlerTypeArray = WsHandlerType.values();
        Map map = new EnumMap(WsHandlerType.class);
        WsPathMatcher wsPathMatcher = this;
        boolean $i$f$associateTo = false;
        for (void element$iv : $this$associateTo$iv) {
            void destination$iv;
            void var7_8 = destination$iv;
            void it = element$iv;
            boolean bl = false;
            Pair<void, List> pair = TuplesKt.to(it, (List)new ArrayList());
            var7_8.put(pair.getFirst(), pair.getSecond());
        }
        wsPathMatcher.wsHandlerEntries = (EnumMap)map;
    }

    public final void add(@NotNull WsEntry entry) {
        Object v1;
        block2: {
            Intrinsics.checkNotNullParameter(entry, "entry");
            List<WsEntry> list = this.wsHandlerEntries.get((Object)entry.getType());
            Intrinsics.checkNotNull(list);
            Iterable iterable = list;
            for (Object t2 : iterable) {
                WsEntry it = (WsEntry)t2;
                boolean bl = false;
                if (!(it.getType() == entry.getType() && Intrinsics.areEqual(it.getPath(), entry.getPath()))) continue;
                v1 = t2;
                break block2;
            }
            v1 = null;
        }
        if (v1 != null) {
            throw new IllegalArgumentException("Handler with type='" + entry.getType() + "' and path='" + entry.getPath() + "' already exists.");
        }
        List<WsEntry> list = this.wsHandlerEntries.get((Object)entry.getType());
        Intrinsics.checkNotNull(list);
        list.add(entry);
    }

    @NotNull
    public final List<WsEntry> findBeforeHandlerEntries(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.findEntries(WsHandlerType.WS_BEFORE, path);
    }

    @Nullable
    public final WsEntry findEndpointHandlerEntry(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return CollectionsKt.firstOrNull(this.findEntries(WsHandlerType.WEBSOCKET, path));
    }

    @NotNull
    public final List<WsEntry> findAfterHandlerEntries(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return this.findEntries(WsHandlerType.WS_AFTER, path);
    }

    /*
     * WARNING - void declaration
     */
    private final List<WsEntry> findEntries(WsHandlerType handlerType, String path) {
        void $this$filterTo$iv$iv;
        List<WsEntry> list = this.wsHandlerEntries.get((Object)handlerType);
        Intrinsics.checkNotNull(list);
        Iterable $this$filter$iv = list;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            WsEntry entry = (WsEntry)element$iv$iv;
            boolean bl = false;
            if (!(Intrinsics.areEqual(entry.getPath(), "*") || entry.matches(path))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }
}

