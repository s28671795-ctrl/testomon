/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection.internal;

import java.lang.reflect.Method;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class MethodWrapperImpl
implements MethodWrapper {
    private final Method method;
    private final int hashCode;

    public MethodWrapperImpl(Method method2) {
        if (method2 == null) {
            throw new IllegalArgumentException();
        }
        this.method = method2;
        int hashCode = 0;
        hashCode ^= method2.getName().hashCode();
        hashCode ^= method2.getReturnType().hashCode();
        for (Class<?> param2 : method2.getParameterTypes()) {
            hashCode ^= param2.hashCode();
        }
        this.hashCode = hashCode;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object o) {
        Class<?>[] otherParams;
        if (o == null) {
            return false;
        }
        if (!(o instanceof MethodWrapperImpl)) {
            return false;
        }
        MethodWrapperImpl other = (MethodWrapperImpl)o;
        if (!this.method.getName().equals(other.method.getName())) {
            return false;
        }
        if (!this.method.getReturnType().equals(other.method.getReturnType())) {
            return false;
        }
        Class<?>[] myParams = this.method.getParameterTypes();
        if (myParams.length != (otherParams = other.method.getParameterTypes()).length) {
            return false;
        }
        if (ReflectionHelper.isPrivate(this.method) || ReflectionHelper.isPrivate(other.method)) {
            return false;
        }
        for (int lcv = 0; lcv < myParams.length; ++lcv) {
            if (myParams[lcv].equals(otherParams[lcv])) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return "MethodWrapperImpl(" + Pretty.method(this.method) + "," + System.identityHashCode(this) + ")";
    }
}

