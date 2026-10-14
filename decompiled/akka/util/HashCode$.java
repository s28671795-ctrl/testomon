/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.lang.reflect.Array;
import scala.Predef$;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.RichInt$;

public final class HashCode$ {
    public static final HashCode$ MODULE$ = new HashCode$();
    private static final int SEED = 23;
    private static final int PRIME = 37;

    public int SEED() {
        return SEED;
    }

    public int hash(int seed, Object any) {
        Object object = any;
        if (object instanceof Boolean) {
            boolean bl = BoxesRunTime.unboxToBoolean(object);
            return this.hash(seed, bl);
        }
        if (object instanceof Character) {
            char c = BoxesRunTime.unboxToChar(object);
            return this.hash(seed, c);
        }
        if (object instanceof Short) {
            short s2 = BoxesRunTime.unboxToShort(object);
            return this.hash(seed, s2);
        }
        if (object instanceof Integer) {
            int n = BoxesRunTime.unboxToInt(object);
            return this.hash(seed, n);
        }
        if (object instanceof Long) {
            long l = BoxesRunTime.unboxToLong(object);
            return this.hash(seed, l);
        }
        if (object instanceof Float) {
            float f = BoxesRunTime.unboxToFloat(object);
            return this.hash(seed, f);
        }
        if (object instanceof Double) {
            double d = BoxesRunTime.unboxToDouble(object);
            return this.hash(seed, d);
        }
        if (object instanceof Byte) {
            byte by = BoxesRunTime.unboxToByte(object);
            return this.hash(seed, by);
        }
        if (object instanceof Object) {
            Object object2 = object;
            IntRef result = IntRef.create(seed);
            if (object2 == null) {
                result.elem = this.hash(result.elem, 0);
            } else if (!this.isArray(object2)) {
                result.elem = this.hash(result.elem, object2.hashCode());
            } else {
                RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), Array.getLength(object2)).foreach$mVc$sp(id -> {
                    result$1.elem = MODULE$.hash(result$1.elem, Array.get(object2, id));
                });
            }
            return result.elem;
        }
        throw new IllegalArgumentException(new StringBuilder(27).append("Unexpected hash parameter: ").append(object).toString());
    }

    public int hash(int seed, boolean value) {
        return this.firstTerm(seed) + (value ? 1 : 0);
    }

    public int hash(int seed, char value) {
        return this.firstTerm(seed) + value;
    }

    public int hash(int seed, int value) {
        return this.firstTerm(seed) + value;
    }

    public int hash(int seed, long value) {
        return this.firstTerm(seed) + (int)(value ^ value >>> 32);
    }

    public int hash(int seed, float value) {
        return this.hash(seed, Float.floatToIntBits(value));
    }

    public int hash(int seed, double value) {
        return this.hash(seed, Double.doubleToLongBits(value));
    }

    private int firstTerm(int seed) {
        return this.PRIME() * seed;
    }

    private boolean isArray(Object anyRef) {
        return anyRef.getClass().isArray();
    }

    private int PRIME() {
        return PRIME;
    }

    private HashCode$() {
    }
}

