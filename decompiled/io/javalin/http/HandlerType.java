/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0086\u0001\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0011B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0012"}, d2={"Lio/javalin/http/HandlerType;", "", "(Ljava/lang/String;I)V", "isHttpMethod", "", "GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "TRACE", "CONNECT", "OPTIONS", "BEFORE", "AFTER", "INVALID", "Companion", "javalin"})
@SourceDebugExtension(value={"SMAP\nHandlerType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HandlerType.kt\nio/javalin/http/HandlerType\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,28:1\n8811#2,2:29\n9071#2,4:31\n*S KotlinDebug\n*F\n+ 1 HandlerType.kt\nio/javalin/http/HandlerType\n*L\n20#1:29,2\n20#1:31,4\n*E\n"})
public final class HandlerType
extends Enum<HandlerType> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private static final Map<String, HandlerType> methodMap;
    public static final /* enum */ HandlerType GET;
    public static final /* enum */ HandlerType POST;
    public static final /* enum */ HandlerType PUT;
    public static final /* enum */ HandlerType PATCH;
    public static final /* enum */ HandlerType DELETE;
    public static final /* enum */ HandlerType HEAD;
    public static final /* enum */ HandlerType TRACE;
    public static final /* enum */ HandlerType CONNECT;
    public static final /* enum */ HandlerType OPTIONS;
    public static final /* enum */ HandlerType BEFORE;
    public static final /* enum */ HandlerType AFTER;
    public static final /* enum */ HandlerType INVALID;
    private static final /* synthetic */ HandlerType[] $VALUES;

    public final boolean isHttpMethod() {
        boolean bl;
        switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: {
                bl = true;
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    public static HandlerType[] values() {
        return (HandlerType[])$VALUES.clone();
    }

    public static HandlerType valueOf(String value) {
        return Enum.valueOf(HandlerType.class, value);
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$associateByTo$iv$iv;
        GET = new HandlerType();
        POST = new HandlerType();
        PUT = new HandlerType();
        PATCH = new HandlerType();
        DELETE = new HandlerType();
        HEAD = new HandlerType();
        TRACE = new HandlerType();
        CONNECT = new HandlerType();
        OPTIONS = new HandlerType();
        BEFORE = new HandlerType();
        AFTER = new HandlerType();
        INVALID = new HandlerType();
        $VALUES = handlerTypeArray = new HandlerType[]{HandlerType.GET, HandlerType.POST, HandlerType.PUT, HandlerType.PATCH, HandlerType.DELETE, HandlerType.HEAD, HandlerType.TRACE, HandlerType.CONNECT, HandlerType.OPTIONS, HandlerType.BEFORE, HandlerType.AFTER, HandlerType.INVALID};
        Companion = new Companion(null);
        HandlerType[] $this$associateBy$iv = HandlerType.values();
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity($this$associateBy$iv.length), 16);
        HandlerType[] handlerTypeArray = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        int n = ((void)$this$associateByTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void element$iv$iv;
            void var9_9 = element$iv$iv = $this$associateByTo$iv$iv[i];
            Map map = destination$iv$iv;
            boolean bl = false;
            map.put(it.toString(), element$iv$iv);
        }
        methodMap = destination$iv$iv;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/http/HandlerType$Companion;", "", "()V", "methodMap", "", "", "Lio/javalin/http/HandlerType;", "findByName", "name", "javalin"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final HandlerType findByName(@NotNull String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            Map map = methodMap;
            String string = name.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            HandlerType handlerType = (HandlerType)((Object)map.get(string));
            if (handlerType == null) {
                handlerType = INVALID;
            }
            return handlerType;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[HandlerType.values().length];
            try {
                nArray[HandlerType.GET.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.HEAD.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.POST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.PUT.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.DELETE.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.CONNECT.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.OPTIONS.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.TRACE.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[HandlerType.PATCH.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

