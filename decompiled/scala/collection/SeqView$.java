/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class SeqView$
implements Serializable {
    public static final SeqView$ MODULE$ = new SeqView$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(SeqView$.class);
    }

    private SeqView$() {
    }
}

