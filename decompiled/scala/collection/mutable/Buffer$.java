/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.SeqFactory;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Buffer;
import scala.runtime.ModuleSerializationProxy;

public final class Buffer$
extends SeqFactory.Delegate<Buffer> {
    public static final Buffer$ MODULE$ = new Buffer$();
    private static final long serialVersionUID = 3L;

    private Object writeReplace() {
        return new ModuleSerializationProxy(Buffer$.class);
    }

    private Buffer$() {
        super(ArrayBuffer$.MODULE$);
    }
}

