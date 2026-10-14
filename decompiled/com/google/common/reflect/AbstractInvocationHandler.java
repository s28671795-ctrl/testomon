/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.reflect;

import com.google.common.reflect.ElementTypesAreNonnullByDefault;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import org.checkerframework.checker.nullness.qual.Nullable;

@ElementTypesAreNonnullByDefault
public abstract class AbstractInvocationHandler
implements InvocationHandler {
    private static final Object[] NO_ARGS = new Object[0];

    @Override
    @CheckForNull
    public final Object invoke(Object proxy, Method method2, @CheckForNull @Nullable Object[] args2) throws Throwable {
        if (args2 == null) {
            args2 = NO_ARGS;
        }
        if (args2.length == 0 && method2.getName().equals("hashCode")) {
            return this.hashCode();
        }
        if (args2.length == 1 && method2.getName().equals("equals") && method2.getParameterTypes()[0] == Object.class) {
            Object arg = args2[0];
            if (arg == null) {
                return false;
            }
            if (proxy == arg) {
                return true;
            }
            return AbstractInvocationHandler.isProxyOfSameInterfaces(arg, proxy.getClass()) && this.equals(Proxy.getInvocationHandler(arg));
        }
        if (args2.length == 0 && method2.getName().equals("toString")) {
            return this.toString();
        }
        return this.handleInvocation(proxy, method2, args2);
    }

    @CheckForNull
    protected abstract Object handleInvocation(Object var1, Method var2, @Nullable Object[] var3) throws Throwable;

    public boolean equals(@CheckForNull Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return super.toString();
    }

    private static boolean isProxyOfSameInterfaces(Object arg, Class<?> proxyClass) {
        return proxyClass.isInstance(arg) || Proxy.isProxyClass(arg.getClass()) && Arrays.equals(arg.getClass().getInterfaces(), proxyClass.getInterfaces());
    }
}

