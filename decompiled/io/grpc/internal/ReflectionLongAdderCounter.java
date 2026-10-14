/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import io.grpc.internal.LongCounter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ReflectionLongAdderCounter
implements LongCounter {
    private static final Logger logger = Logger.getLogger(ReflectionLongAdderCounter.class.getName());
    private static final Constructor<?> defaultConstructor;
    private static final Method addMethod;
    private static final Method sumMethod;
    private static final RuntimeException initializationException;
    private final Object instance;
    private static final Object[] one;

    ReflectionLongAdderCounter() {
        if (initializationException != null) {
            throw initializationException;
        }
        try {
            this.instance = defaultConstructor.newInstance(new Object[0]);
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean isAvailable() {
        return initializationException == null;
    }

    @Override
    public void add(long delta) {
        try {
            Object[] objectArray;
            if (delta == 1L) {
                objectArray = one;
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = delta;
            }
            addMethod.invoke(this.instance, objectArray);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long value() {
        try {
            return (Long)sumMethod.invoke(this.instance, new Object[0]);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException();
        }
    }

    static {
        Class<?> klass = null;
        Constructor<?> defaultConstructorLookup = null;
        Method addMethodLookup = null;
        Method sumMethodLookup = null;
        Throwable caught = null;
        try {
            Constructor<?>[] constructors;
            klass = Class.forName("java.util.concurrent.atomic.LongAdder");
            addMethodLookup = klass.getMethod("add", Long.TYPE);
            sumMethodLookup = klass.getMethod("sum", new Class[0]);
            for (Constructor<?> ctor : constructors = klass.getConstructors()) {
                if (ctor.getParameterTypes().length != 0) continue;
                defaultConstructorLookup = ctor;
                break;
            }
        }
        catch (Throwable e) {
            logger.log(Level.FINE, "LongAdder can not be found via reflection, this is normal for JDK7 and below", e);
            caught = e;
        }
        if (caught == null && defaultConstructorLookup != null) {
            defaultConstructor = defaultConstructorLookup;
            addMethod = addMethodLookup;
            sumMethod = sumMethodLookup;
            initializationException = null;
        } else {
            defaultConstructor = null;
            addMethod = null;
            sumMethod = null;
            initializationException = new RuntimeException(caught);
        }
        one = new Object[]{1L};
    }
}

