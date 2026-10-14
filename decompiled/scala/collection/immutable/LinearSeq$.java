/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.immutable.LinearSeq;
import scala.collection.immutable.List$;
import scala.runtime.ModuleSerializationProxy;

public final class LinearSeq$
extends SeqFactory.Delegate<LinearSeq> {
    public static final LinearSeq$ MODULE$ = new LinearSeq$();
    private static final long serialVersionUID = 3L;

    @Override
    public <E> LinearSeq<E> from(IterableOnce<E> it) {
        if (it instanceof LinearSeq) {
            return (LinearSeq)it;
        }
        return (LinearSeq)super.from((IterableOnce)it);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LinearSeq$.class);
    }

    private LinearSeq$() {
        super(List$.MODULE$);
    }
}

