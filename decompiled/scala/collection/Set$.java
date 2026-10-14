/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.IterableFactory;
import scala.collection.Set;
import scala.runtime.ModuleSerializationProxy;

public final class Set$
extends IterableFactory.Delegate<Set> {
    public static final Set$ MODULE$ = new Set$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(Set$.class);
    }

    private Set$() {
        super(scala.collection.immutable.Set$.MODULE$);
    }
}

