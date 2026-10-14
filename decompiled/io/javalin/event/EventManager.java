/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.event;

import io.javalin.event.EventHandler;
import io.javalin.event.HandlerMetaInfo;
import io.javalin.event.JavalinEvent;
import io.javalin.event.WsHandlerMetaInfo;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\r\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u001b\u001a\u00020\u0006J\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00050\u00042\u0006\u0010\u001b\u001a\u00020\u0014R&\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR-\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u00100\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\b\u00a8\u0006\u001d"}, d2={"Lio/javalin/event/EventManager;", "", "()V", "handlerAddedHandlers", "", "Ljava/util/function/Consumer;", "Lio/javalin/event/HandlerMetaInfo;", "getHandlerAddedHandlers", "()Ljava/util/Set;", "setHandlerAddedHandlers", "(Ljava/util/Set;)V", "lifecycleHandlers", "", "Lio/javalin/event/JavalinEvent;", "Ljava/util/HashSet;", "Lio/javalin/event/EventHandler;", "Lkotlin/collections/HashSet;", "getLifecycleHandlers", "()Ljava/util/Map;", "wsHandlerAddedHandlers", "Lio/javalin/event/WsHandlerMetaInfo;", "getWsHandlerAddedHandlers", "fireEvent", "", "javalinEvent", "(Lio/javalin/event/JavalinEvent;)Lkotlin/Unit;", "fireHandlerAddedEvent", "metaInfo", "fireWsHandlerAddedEvent", "javalin"})
@SourceDebugExtension(value={"SMAP\nEventManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventManager.kt\nio/javalin/event/EventManager\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,26:1\n9496#2,2:27\n9646#2,4:29\n1855#3,2:33\n2634#3:35\n2634#3:37\n1#4:36\n1#4:38\n*S KotlinDebug\n*F\n+ 1 EventManager.kt\nio/javalin/event/EventManager\n*L\n15#1:27,2\n15#1:29,4\n18#1:33,2\n19#1:35\n20#1:37\n19#1:36\n20#1:38\n*E\n"})
public final class EventManager {
    @NotNull
    private final Map<JavalinEvent, HashSet<EventHandler>> lifecycleHandlers;
    @NotNull
    private Set<Consumer<HandlerMetaInfo>> handlerAddedHandlers;
    @NotNull
    private final Set<Consumer<WsHandlerMetaInfo>> wsHandlerAddedHandlers;

    /*
     * WARNING - void declaration
     */
    public EventManager() {
        void $this$associateWith$iv;
        JavalinEvent[] javalinEventArray = JavalinEvent.values();
        EventManager eventManager = this;
        boolean $i$f$associateWith = false;
        LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(((void)$this$associateWith$iv).length), 16));
        void $this$associateWithTo$iv$iv = $this$associateWith$iv;
        boolean $i$f$associateWithTo = false;
        int n = ((void)$this$associateWithTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void var9_10 = element$iv$iv = $this$associateWithTo$iv$iv[i];
            void var13_13 = element$iv$iv;
            Map map = result$iv;
            boolean bl = false;
            HashSet hashSet = new HashSet();
            map.put(var13_13, hashSet);
        }
        eventManager.lifecycleHandlers = result$iv;
        this.handlerAddedHandlers = new LinkedHashSet();
        this.wsHandlerAddedHandlers = new LinkedHashSet();
    }

    @NotNull
    public final Map<JavalinEvent, HashSet<EventHandler>> getLifecycleHandlers() {
        return this.lifecycleHandlers;
    }

    @NotNull
    public final Set<Consumer<HandlerMetaInfo>> getHandlerAddedHandlers() {
        return this.handlerAddedHandlers;
    }

    public final void setHandlerAddedHandlers(@NotNull Set<Consumer<HandlerMetaInfo>> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.handlerAddedHandlers = set;
    }

    @NotNull
    public final Set<Consumer<WsHandlerMetaInfo>> getWsHandlerAddedHandlers() {
        return this.wsHandlerAddedHandlers;
    }

    @Nullable
    public final Unit fireEvent(@NotNull JavalinEvent javalinEvent) {
        Unit unit;
        Intrinsics.checkNotNullParameter((Object)javalinEvent, "javalinEvent");
        HashSet<EventHandler> hashSet = this.lifecycleHandlers.get((Object)javalinEvent);
        if (hashSet != null) {
            Iterable $this$forEach$iv = hashSet;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                EventHandler it = (EventHandler)element$iv;
                boolean bl = false;
                it.handleEvent();
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        return unit;
    }

    @NotNull
    public final Set<Consumer<HandlerMetaInfo>> fireHandlerAddedEvent(@NotNull HandlerMetaInfo metaInfo) {
        Iterable iterable;
        Intrinsics.checkNotNullParameter(metaInfo, "metaInfo");
        Iterable $this$onEach$iv = this.handlerAddedHandlers;
        boolean $i$f$onEach = false;
        Iterable $this$onEach_u24lambda_u2416$iv = iterable = $this$onEach$iv;
        boolean bl = false;
        for (Object element$iv : $this$onEach_u24lambda_u2416$iv) {
            Consumer it = (Consumer)element$iv;
            boolean bl2 = false;
            it.accept(metaInfo);
        }
        return (Set)iterable;
    }

    @NotNull
    public final Set<Consumer<WsHandlerMetaInfo>> fireWsHandlerAddedEvent(@NotNull WsHandlerMetaInfo metaInfo) {
        Iterable iterable;
        Intrinsics.checkNotNullParameter(metaInfo, "metaInfo");
        Iterable $this$onEach$iv = this.wsHandlerAddedHandlers;
        boolean $i$f$onEach = false;
        Iterable $this$onEach_u24lambda_u2416$iv = iterable = $this$onEach$iv;
        boolean bl = false;
        for (Object element$iv : $this$onEach_u24lambda_u2416$iv) {
            Consumer it = (Consumer)element$iv;
            boolean bl2 = false;
            it.accept(metaInfo);
        }
        return (Set)iterable;
    }
}

