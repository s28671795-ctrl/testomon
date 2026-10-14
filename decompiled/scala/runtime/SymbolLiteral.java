/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class SymbolLiteral {
    private SymbolLiteral() {
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, String value) throws Throwable {
        ClassLoader classLoader = lookup.lookupClass().getClassLoader();
        MethodType type = MethodType.fromMethodDescriptorString("(Ljava/lang/String;)Lscala/Symbol;", classLoader);
        Class<?> symbolClass = Class.forName("scala.Symbol", false, classLoader);
        MethodHandle factoryMethod = lookup.findStatic(symbolClass, "apply", type);
        Object symbolValue = factoryMethod.invokeWithArguments(value);
        return new ConstantCallSite(MethodHandles.constant(symbolClass, symbolValue));
    }
}

