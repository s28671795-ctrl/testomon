/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.immutable.List$;
import scala.collection.immutable.Seq;
import scala.runtime.ModuleSerializationProxy;

public final class Seq$
extends SeqFactory.Delegate<Seq> {
    public static final Seq$ MODULE$ = new Seq$();
    private static final long serialVersionUID = 3L;

    @Override
    public <E> Seq<E> from(IterableOnce<E> it) {
        if (it instanceof Seq) {
            return (Seq)it;
        }
        return (Seq)super.from((IterableOnce)it);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Seq$.class);
    }

    private Seq$() {
        super(List$.MODULE$);
    }
}

