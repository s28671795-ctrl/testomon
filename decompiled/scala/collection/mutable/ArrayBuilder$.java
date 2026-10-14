/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.collection.mutable.ArrayBuilder;
import scala.reflect.ClassTag;
import scala.runtime.ModuleSerializationProxy;

public final class ArrayBuilder$
implements Serializable {
    public static final ArrayBuilder$ MODULE$ = new ArrayBuilder$();

    public <T> ArrayBuilder<T> make(ClassTag<T> evidence$1) {
        Class<?> clazz = evidence$1.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofByte();
        }
        Class<Short> clazz3 = Short.TYPE;
        if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofShort();
        }
        Class<Character> clazz4 = Character.TYPE;
        if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofChar();
        }
        Class<Integer> clazz5 = Integer.TYPE;
        if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofInt();
        }
        Class<Long> clazz6 = Long.TYPE;
        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofLong();
        }
        Class<Float> clazz7 = Float.TYPE;
        if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofFloat();
        }
        Class<Double> clazz8 = Double.TYPE;
        if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofDouble();
        }
        Class<Boolean> clazz9 = Boolean.TYPE;
        if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofBoolean();
        }
        Class<Void> clazz10 = Void.TYPE;
        if (!(clazz10 != null ? !clazz10.equals(clazz) : clazz != null)) {
            return new ArrayBuilder.ofUnit();
        }
        return new ArrayBuilder.ofRef<T>(evidence$1);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ArrayBuilder$.class);
    }

    private ArrayBuilder$() {
    }
}

