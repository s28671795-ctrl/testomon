/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class IndexedSeqView$
implements Serializable {
    public static final IndexedSeqView$ MODULE$ = new IndexedSeqView$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(IndexedSeqView$.class);
    }

    private IndexedSeqView$() {
    }
}

