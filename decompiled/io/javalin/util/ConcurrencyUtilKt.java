/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import io.javalin.util.ConcurrencyUtil;
import io.javalin.util.ReentrantLazy;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\u00a8\u0006\u0007"}, d2={"javalinLazy", "Lkotlin/Lazy;", "T", "threadSafetyMode", "Lkotlin/LazyThreadSafetyMode;", "initializer", "Lkotlin/Function0;", "javalin"})
public final class ConcurrencyUtilKt {
    @NotNull
    public static final <T> Lazy<T> javalinLazy(@NotNull LazyThreadSafetyMode threadSafetyMode, @NotNull Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter((Object)threadSafetyMode, "threadSafetyMode");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        return WhenMappings.$EnumSwitchMapping$0[threadSafetyMode.ordinal()] == 1 ? (ConcurrencyUtil.INSTANCE.getUseLoom() && ConcurrencyUtil.isLoomAvailable() ? (Lazy<Object>)new ReentrantLazy<T>(initializer) : LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, initializer)) : LazyKt.lazy(threadSafetyMode, initializer);
    }

    public static /* synthetic */ Lazy javalinLazy$default(LazyThreadSafetyMode lazyThreadSafetyMode, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        }
        return ConcurrencyUtilKt.javalinLazy(lazyThreadSafetyMode, function0);
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[LazyThreadSafetyMode.values().length];
            try {
                nArray[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

