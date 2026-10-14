/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public final class Statics {
    public static final Object pfMarker = new Object();

    public static int mix(int hash, int data) {
        int h2 = Statics.mixLast(hash, data);
        h2 = Integer.rotateLeft(h2, 13);
        return h2 * 5 + -430675100;
    }

    public static int mixLast(int hash, int data) {
        int k = data;
        k *= -862048943;
        k = Integer.rotateLeft(k, 15);
        return hash ^ (k *= 461845907);
    }

    public static int finalizeHash(int hash, int length) {
        return Statics.avalanche(hash ^ length);
    }

    public static int avalanche(int h2) {
        h2 ^= h2 >>> 16;
        h2 *= -2048144789;
        h2 ^= h2 >>> 13;
        h2 *= -1028477387;
        h2 ^= h2 >>> 16;
        return h2;
    }

    public static int longHash(long lv) {
        int iv = (int)lv;
        if ((long)iv == lv) {
            return iv;
        }
        return Long.hashCode(lv);
    }

    public static int doubleHash(double dv) {
        int iv = (int)dv;
        if ((double)iv == dv) {
            return iv;
        }
        long lv = (long)dv;
        if ((double)lv == dv) {
            return Long.hashCode(lv);
        }
        float fv = (float)dv;
        if ((double)fv == dv) {
            return Float.hashCode(fv);
        }
        return Double.hashCode(dv);
    }

    public static int floatHash(float fv) {
        int iv = (int)fv;
        if ((float)iv == fv) {
            return iv;
        }
        long lv = (long)fv;
        if ((float)lv == fv) {
            return Long.hashCode(lv);
        }
        return Float.hashCode(fv);
    }

    public static int anyHash(Object x) {
        if (x == null) {
            return 0;
        }
        if (x instanceof Number) {
            return Statics.anyHashNumber((Number)x);
        }
        return x.hashCode();
    }

    private static int anyHashNumber(Number x) {
        if (x instanceof Long) {
            return Statics.longHash((Long)x);
        }
        if (x instanceof Double) {
            return Statics.doubleHash((Double)x);
        }
        if (x instanceof Float) {
            return Statics.floatHash(((Float)x).floatValue());
        }
        return x.hashCode();
    }

    public static void releaseFence() throws Throwable {
        VM.RELEASE_FENCE.invoke();
    }

    public static final <T> T ioobe(int n) throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException(String.valueOf(n));
    }

    static final class VM {
        static final MethodHandle RELEASE_FENCE = VM.mkHandle();

        VM() {
        }

        private static MethodHandle mkHandle() {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            try {
                return lookup.findStatic(Class.forName("java.lang.invoke.VarHandle"), "releaseFence", MethodType.methodType(Void.TYPE));
            }
            catch (ClassNotFoundException | NoSuchMethodException e) {
                try {
                    Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                    return lookup.findVirtual(unsafeClass, "storeFence", MethodType.methodType(Void.TYPE)).bindTo(VM.findUnsafe(unsafeClass));
                }
                catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e1) {
                    ExceptionInInitializerError error = new ExceptionInInitializerError(e1);
                    error.addSuppressed(e);
                    throw error;
                }
            }
            catch (IllegalAccessException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        private static Object findUnsafe(Class<?> unsafeClass) throws IllegalAccessException {
            Object found = null;
            for (Field field2 : unsafeClass.getDeclaredFields()) {
                if (field2.getType() != unsafeClass) continue;
                field2.setAccessible(true);
                found = field2.get(null);
                break;
            }
            if (found == null) {
                throw new IllegalStateException("No instance of Unsafe found");
            }
            return found;
        }
    }
}

