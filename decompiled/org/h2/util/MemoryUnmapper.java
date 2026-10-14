/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import org.h2.engine.SysProperties;

public final class MemoryUnmapper {
    private static final boolean ENABLED;
    private static final Object UNSAFE;
    private static final Method INVOKE_CLEANER;

    public static boolean unmap(ByteBuffer byteBuffer) {
        if (!ENABLED) {
            return false;
        }
        try {
            if (INVOKE_CLEANER != null) {
                INVOKE_CLEANER.invoke(UNSAFE, byteBuffer);
                return true;
            }
            Method method2 = byteBuffer.getClass().getMethod("cleaner", new Class[0]);
            method2.setAccessible(true);
            Object object = method2.invoke((Object)byteBuffer, new Object[0]);
            if (object != null) {
                Method method3 = object.getClass().getMethod("clean", new Class[0]);
                method3.invoke(object, new Object[0]);
            }
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private MemoryUnmapper() {
    }

    static {
        boolean bl = SysProperties.NIO_CLEANER_HACK;
        Object object = null;
        Method method2 = null;
        if (bl) {
            try {
                Class<?> clazz = Class.forName("sun.misc.Unsafe");
                Field field2 = clazz.getDeclaredField("theUnsafe");
                field2.setAccessible(true);
                object = field2.get(null);
                method2 = clazz.getMethod("invokeCleaner", ByteBuffer.class);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                object = null;
            }
            catch (Throwable throwable) {
                bl = false;
                object = null;
            }
        }
        ENABLED = bl;
        UNSAFE = object;
        INVOKE_CLEANER = method2;
    }
}

