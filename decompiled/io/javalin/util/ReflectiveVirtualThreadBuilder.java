/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u001a\u0010\u0003\u001a\u00020\u0001X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000f"}, d2={"Lio/javalin/util/ReflectiveVirtualThreadBuilder;", "", "()V", "virtualBuilder", "getVirtualBuilder", "()Ljava/lang/Object;", "setVirtualBuilder", "(Ljava/lang/Object;)V", "name", "", "unstarted", "Ljava/lang/Thread;", "runnable", "Ljava/lang/Runnable;", "Companion", "javalin"})
public class ReflectiveVirtualThreadBuilder {
    @NotNull
    protected static final Companion Companion = new Companion(null);
    @NotNull
    private Object virtualBuilder = OF_VIRTUAL.invoke();
    @NotNull
    private static final MethodHandle OF_VIRTUAL;
    @NotNull
    private static final MethodHandle NAME;
    @NotNull
    private static final MethodHandle UNSTARTED;

    @NotNull
    protected final Object getVirtualBuilder() {
        return this.virtualBuilder;
    }

    protected final void setVirtualBuilder(@NotNull Object object) {
        Intrinsics.checkNotNullParameter(object, "<set-?>");
        this.virtualBuilder = object;
    }

    @NotNull
    public final ReflectiveVirtualThreadBuilder name(@NotNull String name) {
        ReflectiveVirtualThreadBuilder reflectiveVirtualThreadBuilder;
        Intrinsics.checkNotNullParameter(name, "name");
        ReflectiveVirtualThreadBuilder it = reflectiveVirtualThreadBuilder = this;
        boolean bl = false;
        this.virtualBuilder = NAME.invoke(this.virtualBuilder, name);
        return reflectiveVirtualThreadBuilder;
    }

    @NotNull
    public final Thread unstarted(@NotNull Runnable runnable2) {
        Intrinsics.checkNotNullParameter(runnable2, "runnable");
        return UNSTARTED.invoke(this.virtualBuilder, runnable2);
    }

    static {
        Class<?> builderClass = Class.forName("java.lang.Thread$Builder$OfVirtual");
        MethodHandles.Lookup handles = MethodHandles.publicLookup();
        MethodHandle methodHandle = handles.findStatic(Thread.class, "ofVirtual", MethodType.methodType(builderClass));
        Intrinsics.checkNotNullExpressionValue(methodHandle, "handles.findStatic(Threa\u2026methodType(builderClass))");
        OF_VIRTUAL = methodHandle;
        MethodHandle methodHandle2 = handles.findVirtual(builderClass, "name", MethodType.methodType(builderClass, String.class));
        Intrinsics.checkNotNullExpressionValue(methodHandle2, "handles.findVirtual(buil\u2026ass, String::class.java))");
        NAME = methodHandle2;
        MethodHandle methodHandle3 = handles.findVirtual(builderClass, "unstarted", MethodType.methodType(Thread.class, Runnable.class));
        Intrinsics.checkNotNullExpressionValue(methodHandle3, "handles.findVirtual(buil\u2026a, Runnable::class.java))");
        UNSTARTED = methodHandle3;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0084\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lio/javalin/util/ReflectiveVirtualThreadBuilder$Companion;", "", "()V", "NAME", "Ljava/lang/invoke/MethodHandle;", "getNAME", "()Ljava/lang/invoke/MethodHandle;", "OF_VIRTUAL", "getOF_VIRTUAL", "UNSTARTED", "getUNSTARTED", "javalin"})
    protected static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MethodHandle getOF_VIRTUAL() {
            return OF_VIRTUAL;
        }

        @NotNull
        public final MethodHandle getNAME() {
            return NAME;
        }

        @NotNull
        public final MethodHandle getUNSTARTED() {
            return UNSTARTED;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

