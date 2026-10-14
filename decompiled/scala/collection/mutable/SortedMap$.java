/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.SortedMapFactory;
import scala.collection.mutable.SortedMap;
import scala.collection.mutable.TreeMap$;
import scala.runtime.ModuleSerializationProxy;

public final class SortedMap$
extends SortedMapFactory.Delegate<SortedMap> {
    public static final SortedMap$ MODULE$ = new SortedMap$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(SortedMap$.class);
    }

    private SortedMap$() {
        super(TreeMap$.MODULE$);
    }
}

