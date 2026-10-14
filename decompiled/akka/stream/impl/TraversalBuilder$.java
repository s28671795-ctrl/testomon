/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Graph;
import akka.stream.InPort;
import akka.stream.Inlet;
import akka.stream.OutPort;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.Shape$;
import akka.stream.SourceShape;
import akka.stream.impl.AtomicTraversalBuilder;
import akka.stream.impl.CompletedTraversalBuilder;
import akka.stream.impl.CompletedTraversalBuilder$;
import akka.stream.impl.Compose;
import akka.stream.impl.Concat;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.EnterIsland;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.LinearTraversalBuilder;
import akka.stream.impl.MaterializeAtomic;
import akka.stream.impl.Pop$;
import akka.stream.impl.PopAttributes$;
import akka.stream.impl.PushAttributes;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.StreamLayout;
import akka.stream.impl.Transform;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuilder;
import akka.stream.impl.fusing.GraphStageModule;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.$less$colon$less$;
import scala.Function1;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Product;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;

@InternalApi
public final class TraversalBuilder$ {
    public static final TraversalBuilder$ MODULE$ = new TraversalBuilder$();
    private static final CompletedTraversalBuilder cachedEmptyCompleted = new CompletedTraversalBuilder(PushNotUsed$.MODULE$, 0, (Map<InPort, Object>)Predef$.MODULE$.Map().empty(), Attributes$.MODULE$.none(), CompletedTraversalBuilder$.MODULE$.apply$default$5());

    private CompletedTraversalBuilder cachedEmptyCompleted() {
        return cachedEmptyCompleted;
    }

    @InternalApi
    public void initShape(Shape shape) {
        Seq<Outlet<?>> outlets;
        Seq<Inlet<?>> inlets = shape.inlets();
        if (inlets.nonEmpty()) {
            if (Shape$.MODULE$.hasOnePort(inlets)) {
                ((InPort)inlets.head()).id_$eq(0);
            } else {
                Iterator inIter = inlets.iterator();
                int i = 0;
                while (inIter.hasNext()) {
                    ((InPort)inIter.next()).id_$eq(i);
                    ++i;
                }
            }
        }
        if ((outlets = shape.outlets()).nonEmpty()) {
            if (Shape$.MODULE$.hasOnePort(outlets)) {
                ((OutPort)outlets.head()).id_$eq(0);
                return;
            }
            Iterator outIter = shape.outlets().iterator();
            int i = 0;
            while (outIter.hasNext()) {
                ((OutPort)outIter.next()).id_$eq(i);
                ++i;
            }
            return;
        }
    }

    @InternalApi
    public TraversalBuilder empty(Attributes attributes) {
        if (attributes == Attributes$.MODULE$.none()) {
            return this.cachedEmptyCompleted();
        }
        return new CompletedTraversalBuilder(PushNotUsed$.MODULE$, 0, (Map<InPort, Object>)Predef$.MODULE$.Map().empty(), attributes, CompletedTraversalBuilder$.MODULE$.apply$default$5());
    }

    public Attributes empty$default$1() {
        return Attributes$.MODULE$.none();
    }

    @InternalApi
    public TraversalBuilder atomic(StreamLayout.AtomicModule<Shape, Object> module, Attributes attributes) {
        Product product;
        this.initShape((Shape)module.shape());
        if (((Shape)module.shape()).outlets().isEmpty()) {
            CompletedTraversalBuilder b = new CompletedTraversalBuilder(new MaterializeAtomic(module, new int[((Shape)module.shape()).outlets().size()]), ((Shape)module.shape()).inlets().size(), ((IterableOnceOps)((Shape)module.shape()).inlets().map((Function1<Inlet, Tuple2> & Serializable)in -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(in), BoxesRunTime.boxToInteger(in.id())))).toMap($less$colon$less$.MODULE$.refl()), Attributes$.MODULE$.none(), CompletedTraversalBuilder$.MODULE$.apply$default$5());
            product = b;
        } else {
            product = new AtomicTraversalBuilder(module, new int[((Shape)module.shape()).outlets().size()], ((Shape)module.shape()).outlets().size(), Attributes$.MODULE$.none());
        }
        AtomicTraversalBuilder builder = product;
        return ((TraversalBuilder)builder).setAttributes(attributes);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @InternalApi
    public void printTraversal(Traversal t, int indent) {
        current = t;
        while (true) {
            block17: {
                block19: {
                    block18: {
                        block16: {
                            v0 = current;
                            var5_4 = EmptyTraversal$.MODULE$;
                            if (!(v0 == null ? var5_4 != null : v0.equals(var5_4) == false)) break;
                            nextStep /* !! */  = EmptyTraversal$.MODULE$;
                            var7_6 = false;
                            var8_7 = null;
                            var9_8 = current;
                            if (!PushNotUsed$.MODULE$.equals(var9_8)) break block16;
                            TraversalBuilder$.prindent$1("push NotUsed", indent);
                            break block17;
                        }
                        if (!Pop$.MODULE$.equals(var9_8)) break block18;
                        TraversalBuilder$.prindent$1("pop mat", indent);
                        break block17;
                    }
                    if (!(var9_8 instanceof Transform)) break block19;
                    TraversalBuilder$.prindent$1("transform mat", indent);
                    break block17;
                }
                if (!(var9_8 instanceof Compose)) ** GOTO lbl-1000
                var7_6 = true;
                var8_7 = (Compose)var9_8;
                var10_9 = var8_7.reverse();
                if (!var10_9) {
                    TraversalBuilder$.prindent$1("compose mat", indent);
                } else if (var7_6 && (var11_10 = var8_7.reverse())) {
                    TraversalBuilder$.prindent$1("compose reversed mat", indent);
                } else if (var9_8 instanceof PushAttributes) {
                    var12_11 = (PushAttributes)var9_8;
                    attr = var12_11.attributes();
                    TraversalBuilder$.prindent$1(new StringBuilder(10).append("push attr ").append(attr).toString(), indent);
                } else if (PopAttributes$.MODULE$.equals(var9_8)) {
                    TraversalBuilder$.prindent$1("pop attr", indent);
                } else if (var9_8 instanceof EnterIsland) {
                    var14_13 = (EnterIsland)var9_8;
                    tag = var14_13.islandTag();
                    TraversalBuilder$.prindent$1(new StringBuilder(13).append("enter island ").append(tag).toString(), indent);
                } else if (ExitIsland$.MODULE$.equals(var9_8)) {
                    TraversalBuilder$.prindent$1("exit island", indent);
                } else if (var9_8 instanceof MaterializeAtomic) {
                    var16_15 = (MaterializeAtomic)var9_8;
                    mod = var16_15.module();
                    outToSlots = var16_15.outToSlots();
                    TraversalBuilder$.prindent$1(new StringBuilder(13).append("materialize ").append(mod).append(" ").append(Predef$.MODULE$.wrapIntArray(outToSlots).mkString("[", ", ", "]")).toString(), indent);
                } else if (var9_8 instanceof Concat) {
                    var19_18 = (Concat)var9_8;
                    first = var19_18.first();
                    next = var19_18.next();
                    this.printTraversal(first, indent + 1);
                    nextStep /* !! */  = next;
                }
            }
            current = nextStep /* !! */ ;
        }
    }

    public int printTraversal$default$2() {
        return 0;
    }

    @InternalApi
    public int printWiring(Traversal t2, int baseSlot) {
        Traversal current = t2;
        IntRef slot = IntRef.create(baseSlot);
        while (true) {
            Traversal traversal = current;
            EmptyTraversal$ emptyTraversal$ = EmptyTraversal$.MODULE$;
            if (!(traversal == null ? emptyTraversal$ != null : !traversal.equals(emptyTraversal$))) break;
            Traversal nextStep = EmptyTraversal$.MODULE$;
            Traversal traversal2 = current;
            if (traversal2 instanceof MaterializeAtomic) {
                MaterializeAtomic materializeAtomic = (MaterializeAtomic)traversal2;
                StreamLayout.AtomicModule<Shape, Object> mod = materializeAtomic.module();
                int[] outToSlots = materializeAtomic.outToSlots();
                Predef$.MODULE$.println(new StringBuilder(12).append("materialize ").append(mod).toString());
                int base = slot.elem;
                ((Shape)mod.shape()).inlets().foreach((Function1<Inlet, Object> & Serializable)in -> {
                    TraversalBuilder$.$anonfun$printWiring$1(slot, in);
                    return BoxedUnit.UNIT;
                });
                ((Shape)mod.shape()).outlets().foreach((Function1<Outlet, Object> & Serializable)out -> {
                    Predef$.MODULE$.println(new StringBuilder(13).append("  wiring ").append(out).append(" to ").append(base + outToSlots[out.id()]).toString());
                    return BoxedUnit.UNIT;
                });
            } else if (traversal2 instanceof Concat) {
                Concat concat = (Concat)traversal2;
                Traversal first = concat.first();
                Traversal next = concat.next();
                slot.elem = this.printWiring(first, slot.elem);
                nextStep = next;
            }
            current = nextStep;
        }
        return slot.elem;
    }

    public int printWiring$default$2() {
        return 0;
    }

    public <A> GraphStages.SingleSource<A> getSingleSource(Graph<SourceShape<A>, ?> graph) {
        Graph<SourceShape<A>, ?> graph2 = graph;
        if (graph2 instanceof GraphStages.SingleSource) {
            GraphStages.SingleSource singleSource = (GraphStages.SingleSource)graph2;
            OptionVal<GraphStages.SingleSource> optionVal = new OptionVal<GraphStages.SingleSource>(OptionVal$Some$.MODULE$.apply(singleSource));
            if (optionVal == null) {
                return null;
            }
            return optionVal.x();
        }
        TraversalBuilder traversalBuilder = graph.traversalBuilder();
        if (traversalBuilder instanceof LinearTraversalBuilder) {
            TraversalBuilder a;
            LinearTraversalBuilder linearTraversalBuilder = (LinearTraversalBuilder)traversalBuilder;
            TraversalBuilder traversalBuilder2 = linearTraversalBuilder.pendingBuilder();
            TraversalBuilder traversalBuilder3 = OptionVal$Some$.MODULE$.unapply(traversalBuilder2);
            if (!OptionVal$.MODULE$.isEmpty$extension(traversalBuilder3) && (a = OptionVal$.MODULE$.get$extension(traversalBuilder3)) instanceof AtomicTraversalBuilder) {
                AtomicTraversalBuilder atomicTraversalBuilder = (AtomicTraversalBuilder)a;
                StreamLayout.AtomicModule<Shape, Object> atomicModule = atomicTraversalBuilder.module();
                if (atomicModule instanceof GraphStageModule) {
                    GraphStageModule graphStageModule = (GraphStageModule)atomicModule;
                    GraphStageWithMaterializedValue graphStageWithMaterializedValue = graphStageModule.stage();
                    if (graphStageWithMaterializedValue instanceof GraphStages.SingleSource) {
                        GraphStages.SingleSource singleSource = (GraphStages.SingleSource)graphStageWithMaterializedValue;
                        if (linearTraversalBuilder.traversalSoFar() == EmptyTraversal$.MODULE$ && !linearTraversalBuilder.attributes().isAsync()) {
                            return OptionVal$Some$.MODULE$.apply(singleSource);
                        }
                        OptionVal$.MODULE$.None();
                        return null;
                    }
                    OptionVal$.MODULE$.None();
                    return null;
                }
                OptionVal$.MODULE$.None();
                return null;
            }
            OptionVal$.MODULE$.None();
            return null;
        }
        OptionVal$.MODULE$.None();
        return null;
    }

    public boolean isEmptySource(Graph<SourceShape<?>, ?> graph) {
        Source source2;
        akka.stream.scaladsl.Source source3;
        Graph<SourceShape<?>, ?> graph2 = graph;
        if (graph2 instanceof akka.stream.scaladsl.Source && (source3 = (akka.stream.scaladsl.Source)graph2) == Source$.MODULE$.empty()) {
            return true;
        }
        return graph2 instanceof Source && (source2 = (Source)graph2) == akka.stream.javadsl.Source$.MODULE$.empty();
    }

    private static final void prindent$1(String s2, int indent$1) {
        Predef$.MODULE$.println(new StringBuilder(0).append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(" | "), indent$1)).append(s2).toString());
    }

    public static final /* synthetic */ void $anonfun$printWiring$1(IntRef slot$1, Inlet in) {
        Predef$.MODULE$.println(new StringBuilder(13).append("  wiring ").append(in).append(" to ").append(slot$1.elem).toString());
        ++slot$1.elem;
    }

    private TraversalBuilder$() {
    }
}

