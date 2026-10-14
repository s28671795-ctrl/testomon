/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.SeqFactory;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Seq;
import scala.runtime.ModuleSerializationProxy;

public final class Seq$
extends SeqFactory.Delegate<Seq> {
    public static final Seq$ MODULE$ = new Seq$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(Seq$.class);
    }

    private Seq$() {
        super(ArrayBuffer$.MODULE$);
    }
}

