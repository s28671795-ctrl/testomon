/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Vector$;
import scala.runtime.ModuleSerializationProxy;

public final class IndexedSeq$
extends SeqFactory.Delegate<IndexedSeq> {
    public static final IndexedSeq$ MODULE$ = new IndexedSeq$();
    private static final long serialVersionUID = 3L;

    @Override
    public <E> IndexedSeq<E> from(IterableOnce<E> it) {
        if (it instanceof IndexedSeq) {
            return (IndexedSeq)it;
        }
        return (IndexedSeq)super.from((IterableOnce)it);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IndexedSeq$.class);
    }

    private IndexedSeq$() {
        super(Vector$.MODULE$);
    }
}

