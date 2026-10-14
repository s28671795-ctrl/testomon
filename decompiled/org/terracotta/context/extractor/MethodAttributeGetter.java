/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.context.extractor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.terracotta.context.extractor.AttributeGetter;

abstract class MethodAttributeGetter<T>
implements AttributeGetter<T> {
    private final Method method;

    MethodAttributeGetter(Method method2) {
        method2.setAccessible(true);
        this.method = method2;
    }

    abstract Object target();

    @Override
    public T get() {
        try {
            return (T)this.method.invoke(this.target(), new Object[0]);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
        catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}

