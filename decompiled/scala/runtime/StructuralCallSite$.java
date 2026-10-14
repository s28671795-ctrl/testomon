/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import scala.runtime.StructuralCallSite;

public final class StructuralCallSite$ {
    public static final StructuralCallSite$ MODULE$ = new StructuralCallSite$();

    public CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, MethodType reflectiveCallType) {
        StructuralCallSite structuralCallSite = new StructuralCallSite(reflectiveCallType);
        return new ConstantCallSite(MethodHandles.constant(StructuralCallSite.class, structuralCallSite));
    }

    private StructuralCallSite$() {
    }
}

