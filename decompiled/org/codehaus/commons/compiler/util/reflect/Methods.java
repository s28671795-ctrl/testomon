/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.codehaus.commons.nullanalysis.Nullable;

public final class Methods {
    private Methods() {
    }

    public static <R, EX extends Throwable> R invoke(Method method2, @Nullable Object obj, Object ... args2) throws EX {
        try {
            Object returnValue = method2.invoke(obj, args2);
            return (R)returnValue;
        }
        catch (InvocationTargetException ite) {
            Throwable targetException = ite.getTargetException();
            throw targetException;
        }
        catch (Exception e) {
            throw new AssertionError((Object)e);
        }
    }
}

