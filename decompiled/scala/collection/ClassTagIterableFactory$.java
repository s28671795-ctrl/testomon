/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class ClassTagIterableFactory$
implements Serializable {
    public static final ClassTagIterableFactory$ MODULE$ = new ClassTagIterableFactory$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(ClassTagIterableFactory$.class);
    }

    private ClassTagIterableFactory$() {
    }
}

