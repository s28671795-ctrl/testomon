/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Attributes;
import akka.stream.Shape;
import akka.stream.impl.AtomicTraversalBuilder;
import akka.stream.impl.StreamLayout;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.AbstractFunction4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class AtomicTraversalBuilder$
extends AbstractFunction4<StreamLayout.AtomicModule<Shape, Object>, int[], Object, Attributes, AtomicTraversalBuilder>
implements Serializable {
    public static final AtomicTraversalBuilder$ MODULE$ = new AtomicTraversalBuilder$();

    @Override
    public final String toString() {
        return "AtomicTraversalBuilder";
    }

    @Override
    public AtomicTraversalBuilder apply(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlot, int unwiredOuts, Attributes attributes) {
        return new AtomicTraversalBuilder(module, outToSlot, unwiredOuts, attributes);
    }

    public Option<Tuple4<StreamLayout.AtomicModule<Shape, Object>, int[], Object, Attributes>> unapply(AtomicTraversalBuilder x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<StreamLayout.AtomicModule<Shape, Object>, int[], Object, Attributes>>(new Tuple4<StreamLayout.AtomicModule<Shape, Object>, int[], Integer, Attributes>(x$0.module(), x$0.outToSlot(), BoxesRunTime.boxToInteger(x$0.unwiredOuts()), x$0.attributes()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AtomicTraversalBuilder$.class);
    }

    private AtomicTraversalBuilder$() {
    }
}

