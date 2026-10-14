/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Shape;
import akka.stream.impl.MaterializeAtomic;
import akka.stream.impl.StreamLayout;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class MaterializeAtomic$
extends AbstractFunction2<StreamLayout.AtomicModule<Shape, Object>, int[], MaterializeAtomic>
implements Serializable {
    public static final MaterializeAtomic$ MODULE$ = new MaterializeAtomic$();

    @Override
    public final String toString() {
        return "MaterializeAtomic";
    }

    @Override
    public MaterializeAtomic apply(StreamLayout.AtomicModule<Shape, Object> module, int[] outToSlots) {
        return new MaterializeAtomic(module, outToSlots);
    }

    public Option<Tuple2<StreamLayout.AtomicModule<Shape, Object>, int[]>> unapply(MaterializeAtomic x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<StreamLayout.AtomicModule<Shape, Object>, int[]>>(new Tuple2<StreamLayout.AtomicModule<Shape, Object>, int[]>(x$0.module(), x$0.outToSlots()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MaterializeAtomic$.class);
    }

    private MaterializeAtomic$() {
    }
}

