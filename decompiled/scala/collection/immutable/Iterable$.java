/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List$;
import scala.runtime.ModuleSerializationProxy;

public final class Iterable$
extends IterableFactory.Delegate<Iterable> {
    public static final Iterable$ MODULE$ = new Iterable$();
    private static final long serialVersionUID = 3L;

    @Override
    public <E> Iterable<E> from(IterableOnce<E> it) {
        if (it instanceof Iterable) {
            return (Iterable)it;
        }
        return (Iterable)super.from(it);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Iterable$.class);
    }

    private Iterable$() {
        super(List$.MODULE$);
    }
}

