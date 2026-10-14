/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.SortedIterableFactory;
import scala.collection.mutable.SortedSet;
import scala.collection.mutable.TreeSet$;
import scala.runtime.ModuleSerializationProxy;

public final class SortedSet$
extends SortedIterableFactory.Delegate<SortedSet> {
    public static final SortedSet$ MODULE$ = new SortedSet$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedSet$.class);
    }

    private SortedSet$() {
        super(TreeSet$.MODULE$);
    }
}

