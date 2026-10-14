/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class CheckedIndexedSeqView$
implements Serializable {
    public static final CheckedIndexedSeqView$ MODULE$ = new CheckedIndexedSeqView$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(CheckedIndexedSeqView$.class);
    }

    private CheckedIndexedSeqView$() {
    }
}

