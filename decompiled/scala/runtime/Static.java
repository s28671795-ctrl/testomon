/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class Static {
    private Static() {
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, MethodHandle handle2, Object ... args2) throws Throwable {
        Object value = handle2.invokeWithArguments(args2);
        return new ConstantCallSite(MethodHandles.constant(invokedType.returnType(), value));
    }
}

