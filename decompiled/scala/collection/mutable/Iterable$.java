/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.IterableFactory;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Iterable;
import scala.runtime.ModuleSerializationProxy;

public final class Iterable$
extends IterableFactory.Delegate<Iterable> {
    public static final Iterable$ MODULE$ = new Iterable$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(Iterable$.class);
    }

    private Iterable$() {
        super(ArrayBuffer$.MODULE$);
    }
}

