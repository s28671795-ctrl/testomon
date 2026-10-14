/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.SeqOps;
import scala.collection.generic.IsSeq;

public final class Searching$ {
    public static final Searching$ MODULE$ = new Searching$();

    public <Repr, A> SeqOps<Object, Object, ?> search(Repr coll, IsSeq<Repr> fr) {
        return fr.conversion().apply(coll);
    }

    private Searching$() {
    }
}

