/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class SortedIterableFactory$
implements Serializable {
    public static final SortedIterableFactory$ MODULE$ = new SortedIterableFactory$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedIterableFactory$.class);
    }

    private SortedIterableFactory$() {
    }
}

