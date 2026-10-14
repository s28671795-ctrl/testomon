/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.SortedIterableFactory;
import scala.collection.SortedSet;
import scala.runtime.ModuleSerializationProxy;

public final class SortedSet$
extends SortedIterableFactory.Delegate<SortedSet> {
    public static final SortedSet$ MODULE$ = new SortedSet$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedSet$.class);
    }

    private SortedSet$() {
        super(scala.collection.immutable.SortedSet$.MODULE$);
    }
}

