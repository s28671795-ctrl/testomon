/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.MapFactory;
import scala.collection.immutable.SeqMap;
import scala.runtime.ModuleSerializationProxy;

public final class SeqMap$
extends MapFactory.Delegate<SeqMap> {
    public static final SeqMap$ MODULE$ = new SeqMap$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(SeqMap$.class);
    }

    private SeqMap$() {
        super(scala.collection.immutable.SeqMap$.MODULE$);
    }
}

