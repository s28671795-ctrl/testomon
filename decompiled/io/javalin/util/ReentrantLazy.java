/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000 \u000f*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u000fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00028\u00008VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2={"Lio/javalin/util/ReentrantLazy;", "T", "Lkotlin/Lazy;", "initializer", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "_value", "", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "Companion", "javalin"})
public final class ReentrantLazy<T>
implements Lazy<T> {
    @NotNull
    private static final Companion Companion = new Companion(null);
    @Nullable
    private Function0<? extends T> initializer;
    @Nullable
    private volatile ReentrantLock lock;
    @Nullable
    private volatile Object _value;

    public ReentrantLazy(@NotNull Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        this.initializer = initializer;
        this.lock = new ReentrantLock();
        this._value = Companion.UNINITIALIZED_VALUE.INSTANCE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T getValue() {
        ReentrantLock reentrantLock = this.lock;
        if (reentrantLock != null) {
            Lock lock = reentrantLock;
            lock.lock();
            try {
                boolean bl = false;
                if (this._value == Companion.UNINITIALIZED_VALUE.INSTANCE) {
                    Function0<T> function0 = this.initializer;
                    Intrinsics.checkNotNull(function0);
                    this._value = function0.invoke();
                    this.lock = null;
                    this.initializer = null;
                }
                Unit unit = Unit.INSTANCE;
            }
            finally {
                lock.unlock();
            }
        }
        return (T)this._value;
    }

    @Override
    public boolean isInitialized() {
        return this._value != Companion.UNINITIALIZED_VALUE.INSTANCE;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lio/javalin/util/ReentrantLazy$Companion;", "", "()V", "UNINITIALIZED_VALUE", "javalin"})
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lio/javalin/util/ReentrantLazy$Companion$UNINITIALIZED_VALUE;", "", "()V", "javalin"})
        private static final class UNINITIALIZED_VALUE {
            @NotNull
            public static final UNINITIALIZED_VALUE INSTANCE = new UNINITIALIZED_VALUE();

            private UNINITIALIZED_VALUE() {
            }
        }
    }
}

