/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.reflect.ReflectionFactory$GetReflectionFactoryAction
 */
package org.jboss.sun.corba;

import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import org.jboss.sun.corba.BridgePermission;
import sun.misc.Unsafe;
import sun.reflect.ReflectionFactory;

public final class Bridge {
    private static final Class<?>[] NO_ARGS = new Class[0];
    private static final Permission getBridgePermission = new BridgePermission("getBridge");
    private static Bridge bridge = null;
    private final Method latestUserDefinedLoaderMethod = this.getLatestUserDefinedLoaderMethod();
    private final Unsafe unsafe = this.getUnsafe();
    private final ReflectionFactory reflectionFactory = (ReflectionFactory)AccessController.doPrivileged(new ReflectionFactory.GetReflectionFactoryAction());
    public static final long INVALID_FIELD_OFFSET = -1L;

    private Method getLatestUserDefinedLoaderMethod() {
        return AccessController.doPrivileged(new PrivilegedAction<Method>(){

            @Override
            public Method run() {
                Method result = null;
                try {
                    Class<ObjectInputStream> io = ObjectInputStream.class;
                    result = io.getDeclaredMethod("latestUserDefinedLoader", NO_ARGS);
                    result.setAccessible(true);
                }
                catch (NoSuchMethodException nsme) {
                    Error err = new Error("java.io.ObjectInputStream latestUserDefinedLoader " + nsme);
                    err.initCause(nsme);
                    throw err;
                }
                return result;
            }
        });
    }

    private Unsafe getUnsafe() {
        Field fld = AccessController.doPrivileged(new PrivilegedAction<Field>(){

            @Override
            public Field run() {
                Field fld = null;
                try {
                    Class<Unsafe> unsafeClass = Unsafe.class;
                    fld = unsafeClass.getDeclaredField("theUnsafe");
                    fld.setAccessible(true);
                    return fld;
                }
                catch (NoSuchFieldException exc) {
                    Error err = new Error("Could not access Unsafe");
                    err.initCause(exc);
                    throw err;
                }
            }
        });
        Unsafe unsafe = null;
        try {
            unsafe = (Unsafe)fld.get(null);
        }
        catch (Throwable t2) {
            Error err = new Error("Could not access Unsafe");
            err.initCause(t2);
            throw err;
        }
        return unsafe;
    }

    private Bridge() {
    }

    public static final synchronized Bridge get() {
        SecurityManager sman = System.getSecurityManager();
        if (sman != null) {
            sman.checkPermission(getBridgePermission);
        }
        if (bridge == null) {
            bridge = new Bridge();
        }
        return bridge;
    }

    public final ClassLoader getLatestUserDefinedLoader() {
        try {
            return (ClassLoader)this.latestUserDefinedLoaderMethod.invoke(null, (Object[])NO_ARGS);
        }
        catch (InvocationTargetException ite) {
            Error err = new Error("sun.corba.Bridge.latestUserDefinedLoader: " + ite);
            err.initCause(ite);
            throw err;
        }
        catch (IllegalAccessException iae) {
            Error err = new Error("sun.corba.Bridge.latestUserDefinedLoader: " + iae);
            err.initCause(iae);
            throw err;
        }
    }

    public final int getInt(Object o, long offset) {
        return this.unsafe.getInt(o, offset);
    }

    public final void putInt(Object o, long offset, int x) {
        this.unsafe.putInt(o, offset, x);
    }

    public final Object getObject(Object o, long offset) {
        return this.unsafe.getObject(o, offset);
    }

    public final void putObject(Object o, long offset, Object x) {
        this.unsafe.putObject(o, offset, x);
    }

    public final boolean getBoolean(Object o, long offset) {
        return this.unsafe.getBoolean(o, offset);
    }

    public final void putBoolean(Object o, long offset, boolean x) {
        this.unsafe.putBoolean(o, offset, x);
    }

    public final byte getByte(Object o, long offset) {
        return this.unsafe.getByte(o, offset);
    }

    public final void putByte(Object o, long offset, byte x) {
        this.unsafe.putByte(o, offset, x);
    }

    public final short getShort(Object o, long offset) {
        return this.unsafe.getShort(o, offset);
    }

    public final void putShort(Object o, long offset, short x) {
        this.unsafe.putShort(o, offset, x);
    }

    public final char getChar(Object o, long offset) {
        return this.unsafe.getChar(o, offset);
    }

    public final void putChar(Object o, long offset, char x) {
        this.unsafe.putChar(o, offset, x);
    }

    public final long getLong(Object o, long offset) {
        return this.unsafe.getLong(o, offset);
    }

    public final void putLong(Object o, long offset, long x) {
        this.unsafe.putLong(o, offset, x);
    }

    public final float getFloat(Object o, long offset) {
        return this.unsafe.getFloat(o, offset);
    }

    public final void putFloat(Object o, long offset, float x) {
        this.unsafe.putFloat(o, offset, x);
    }

    public final double getDouble(Object o, long offset) {
        return this.unsafe.getDouble(o, offset);
    }

    public final void putDouble(Object o, long offset, double x) {
        this.unsafe.putDouble(o, offset, x);
    }

    public final long objectFieldOffset(Field f) {
        return this.unsafe.objectFieldOffset(f);
    }

    public final void throwException(Throwable ee) {
        this.unsafe.throwException(ee);
    }

    public final Constructor<?> newConstructorForSerialization(Class<?> cl, Constructor<?> cons) {
        return this.reflectionFactory.newConstructorForSerialization(cl, cons);
    }
}

