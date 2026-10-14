/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class SeqFactory$
implements Serializable {
    public static final SeqFactory$ MODULE$ = new SeqFactory$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(SeqFactory$.class);
    }

    private SeqFactory$() {
    }
}

