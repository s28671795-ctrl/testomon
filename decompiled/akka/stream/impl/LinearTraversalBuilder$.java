/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.InPort;
import akka.stream.Inlet;
import akka.stream.OutPort;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.impl.CompletedTraversalBuilder;
import akka.stream.impl.Compose;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.MaterializeAtomic;
import akka.stream.impl.Pop$;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.scaladsl.Keep$;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.$less$colon$less$;
import scala.Array$;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple9;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

@InternalApi
public final class LinearTraversalBuilder$
implements Serializable {
    public static final LinearTraversalBuilder$ MODULE$ = new LinearTraversalBuilder$();
    private static final LinearTraversalBuilder cachedEmptyLinear;
    private static final int[] wireBackward;
    private static final int[] noWire;

    static {
        OptionVal$.MODULE$.None();
        OptionVal$.MODULE$.None();
        OptionVal$.MODULE$.None();
        cachedEmptyLinear = new LinearTraversalBuilder(null, null, 0, 0, PushNotUsed$.MODULE$, null, Attributes$.MODULE$.none(), MODULE$.apply$default$8(), MODULE$.apply$default$9());
        wireBackward = new int[]{-1};
        noWire = (int[])Array$.MODULE$.apply(Nil$.MODULE$, ClassTag$.MODULE$.Int());
    }

    public Traversal $lessinit$greater$default$8() {
        return EmptyTraversal$.MODULE$;
    }

    public IslandTag $lessinit$greater$default$9() {
        OptionVal$.MODULE$.None();
        return null;
    }

    private LinearTraversalBuilder cachedEmptyLinear() {
        return cachedEmptyLinear;
    }

    public LinearTraversalBuilder empty(Attributes attributes) {
        if (attributes == Attributes$.MODULE$.none()) {
            return this.cachedEmptyLinear();
        }
        OptionVal$.MODULE$.None();
        OptionVal$.MODULE$.None();
        OptionVal$.MODULE$.None();
        return new LinearTraversalBuilder(null, null, 0, 0, PushNotUsed$.MODULE$, null, attributes, EmptyTraversal$.MODULE$, this.apply$default$9());
    }

    public Attributes empty$default$1() {
        return Attributes$.MODULE$.none();
    }

    public LinearTraversalBuilder fromModule(StreamLayout.AtomicModule<Shape, Object> module, Attributes attributes) {
        if (((Shape)module.shape()).inlets().size() > 1) {
            throw new IllegalStateException("Modules with more than one input port cannot be linear.");
        }
        if (((Shape)module.shape()).outlets().size() > 1) {
            throw new IllegalStateException("Modules with more than one output port cannot be linear.");
        }
        TraversalBuilder$.MODULE$.initShape((Shape)module.shape());
        Inlet inPortOpt = (Inlet)OptionVal$.MODULE$.apply(((Shape)module.shape()).inlets().headOption().orNull($less$colon$less$.MODULE$.refl()));
        Outlet outPortOpt = (Outlet)OptionVal$.MODULE$.apply(((Shape)module.shape()).outlets().headOption().orNull($less$colon$less$.MODULE$.refl()));
        int[] wiring = OptionVal$.MODULE$.isDefined$extension(outPortOpt) ? wireBackward : noWire;
        int n = OptionVal$.MODULE$.isDefined$extension(inPortOpt) ? 1 : 0;
        MaterializeAtomic materializeAtomic = new MaterializeAtomic(module, wiring);
        OptionVal$.MODULE$.None();
        return new LinearTraversalBuilder(inPortOpt, outPortOpt, 0, n, materializeAtomic, null, attributes, this.apply$default$8(), this.apply$default$9());
    }

    public Traversal addMatCompose(Traversal t2, Function2<Nothing$, Nothing$, Object> matCompose) {
        if (matCompose == Keep$.MODULE$.left()) {
            return Pop$.MODULE$.concat(t2);
        }
        if (matCompose == Keep$.MODULE$.right()) {
            return t2.concat(Pop$.MODULE$);
        }
        if (matCompose == Keep$.MODULE$.none()) {
            return t2.concat(Pop$.MODULE$).concat(Pop$.MODULE$).concat(PushNotUsed$.MODULE$);
        }
        return t2.concat(new Compose(matCompose, true));
    }

    public LinearTraversalBuilder fromBuilder(TraversalBuilder traversalBuilder, Shape shape, Function2<Nothing$, Nothing$, Object> combine) {
        int n;
        TraversalBuilder traversalBuilder2 = traversalBuilder;
        if (traversalBuilder2 instanceof LinearTraversalBuilder) {
            LinearTraversalBuilder linearTraversalBuilder = (LinearTraversalBuilder)traversalBuilder2;
            if (combine == Keep$.MODULE$.right()) {
                return linearTraversalBuilder;
            }
            return this.empty(this.empty$default$1()).append(linearTraversalBuilder, combine);
        }
        if (traversalBuilder2 instanceof CompletedTraversalBuilder) {
            int n2;
            CompletedTraversalBuilder completedTraversalBuilder = (CompletedTraversalBuilder)traversalBuilder2;
            Inlet inOpt = (Inlet)OptionVal$.MODULE$.apply(shape.inlets().headOption().orNull($less$colon$less$.MODULE$.refl()));
            Inlet inlet = inOpt;
            Inlet inlet2 = OptionVal$Some$.MODULE$.unapply(inlet);
            if (!OptionVal$.MODULE$.isEmpty$extension(inlet2)) {
                Inlet in = OptionVal$.MODULE$.get$extension(inlet2);
                n2 = completedTraversalBuilder.offsetOf(in);
            } else {
                n2 = 0;
            }
            int inOffs = n2;
            InPort inPort = (InPort)OptionVal$.MODULE$.apply(OptionVal$.MODULE$.orNull$extension(inOpt, $less$colon$less$.MODULE$.refl()));
            OptionVal$.MODULE$.None();
            int n3 = completedTraversalBuilder.inSlots();
            Traversal traversal = completedTraversalBuilder.traversal().concat(this.addMatCompose(PushNotUsed$.MODULE$, combine));
            OptionVal$.MODULE$.None();
            return new LinearTraversalBuilder(inPort, null, inOffs, n3, traversal, null, Attributes$.MODULE$.none(), this.apply$default$8(), this.apply$default$9());
        }
        Inlet inOpt = (Inlet)OptionVal$.MODULE$.apply(shape.inlets().headOption().orNull($less$colon$less$.MODULE$.refl()));
        Outlet out = (Outlet)shape.outlets().head();
        Inlet inlet = inOpt;
        Inlet inlet3 = OptionVal$Some$.MODULE$.unapply(inlet);
        if (!OptionVal$.MODULE$.isEmpty$extension(inlet3)) {
            Inlet in = OptionVal$.MODULE$.get$extension(inlet3);
            n = traversalBuilder2.offsetOf(in);
        } else {
            n = 0;
        }
        int inOffs = n;
        return new LinearTraversalBuilder((InPort)OptionVal$.MODULE$.apply(OptionVal$.MODULE$.orNull$extension(inOpt, $less$colon$less$.MODULE$.refl())), OptionVal$Some$.MODULE$.apply(out), inOffs, traversalBuilder2.inSlots(), this.addMatCompose(PushNotUsed$.MODULE$, combine), OptionVal$Some$.MODULE$.apply(traversalBuilder2), Attributes$.MODULE$.none(), EmptyTraversal$.MODULE$, this.apply$default$9());
    }

    public Function2<Nothing$, Nothing$, Object> fromBuilder$default$3() {
        return Keep$.MODULE$.right();
    }

    public LinearTraversalBuilder apply(InPort inPort, OutPort outPort, int inOffset, int inSlots, Traversal traversalSoFar, TraversalBuilder pendingBuilder, Attributes attributes, Traversal beforeBuilder, IslandTag islandTag) {
        return new LinearTraversalBuilder(inPort, outPort, inOffset, inSlots, traversalSoFar, pendingBuilder, attributes, beforeBuilder, islandTag);
    }

    public Traversal apply$default$8() {
        return EmptyTraversal$.MODULE$;
    }

    public IslandTag apply$default$9() {
        OptionVal$.MODULE$.None();
        return null;
    }

    public Option<Tuple9<OptionVal<InPort>, OptionVal<OutPort>, Object, Object, Traversal, OptionVal<TraversalBuilder>, Attributes, Traversal, OptionVal<IslandTag>>> unapply(LinearTraversalBuilder x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple9<OptionVal<InPort>, OptionVal<OutPort>, Object, Object, Traversal, OptionVal<TraversalBuilder>, Attributes, Traversal, OptionVal<IslandTag>>>(new Tuple9<OptionVal<InPort>, OptionVal<OutPort>, Integer, Integer, Traversal, OptionVal<TraversalBuilder>, Attributes, Traversal, OptionVal<IslandTag>>(new OptionVal<InPort>(x$0.inPort()), new OptionVal<OutPort>(x$0.outPort()), BoxesRunTime.boxToInteger(x$0.inOffset()), BoxesRunTime.boxToInteger(x$0.inSlots()), x$0.traversalSoFar(), new OptionVal<TraversalBuilder>(x$0.pendingBuilder()), x$0.attributes(), x$0.beforeBuilder(), new OptionVal<IslandTag>(x$0.islandTag())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LinearTraversalBuilder$.class);
    }

    private LinearTraversalBuilder$() {
    }
}

