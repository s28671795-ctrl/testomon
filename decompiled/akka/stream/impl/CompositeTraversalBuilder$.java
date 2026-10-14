/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.impl.AppendTraversal;
import akka.stream.impl.BuilderKey;
import akka.stream.impl.CompositeTraversalBuilder;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuildStep;
import akka.stream.impl.TraversalBuilder;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple10;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.runtime.AbstractFunction10;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class CompositeTraversalBuilder$
extends AbstractFunction10<Traversal, List<TraversalBuildStep>, Object, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Object, Attributes, OptionVal<IslandTag>, CompositeTraversalBuilder>
implements Serializable {
    public static final CompositeTraversalBuilder$ MODULE$ = new CompositeTraversalBuilder$();

    public Traversal $lessinit$greater$default$1() {
        return EmptyTraversal$.MODULE$;
    }

    public List<TraversalBuildStep> $lessinit$greater$default$2() {
        AppendTraversal appendTraversal = new AppendTraversal(PushNotUsed$.MODULE$);
        return Nil$.MODULE$.$colon$colon(appendTraversal);
    }

    public int $lessinit$greater$default$3() {
        return 0;
    }

    public Map<InPort, Object> $lessinit$greater$default$4() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<OutPort, Object> $lessinit$greater$default$5() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<BuilderKey, TraversalBuilder> $lessinit$greater$default$6() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<OutPort, BuilderKey> $lessinit$greater$default$7() {
        return Predef$.MODULE$.Map().empty();
    }

    public int $lessinit$greater$default$8() {
        return 0;
    }

    public IslandTag $lessinit$greater$default$10() {
        OptionVal$.MODULE$.None();
        return null;
    }

    @Override
    public final String toString() {
        return "CompositeTraversalBuilder";
    }

    @Override
    public CompositeTraversalBuilder apply(Traversal finalSteps, List<TraversalBuildStep> reverseBuildSteps, int inSlots, Map<InPort, Object> inOffsets, Map<OutPort, Object> inBaseOffsetForOut, Map<BuilderKey, TraversalBuilder> pendingBuilders, Map<OutPort, BuilderKey> outOwners, int unwiredOuts, Attributes attributes, IslandTag islandTag) {
        return new CompositeTraversalBuilder(finalSteps, reverseBuildSteps, inSlots, inOffsets, inBaseOffsetForOut, pendingBuilders, outOwners, unwiredOuts, attributes, islandTag);
    }

    public Traversal apply$default$1() {
        return EmptyTraversal$.MODULE$;
    }

    public IslandTag apply$default$10() {
        OptionVal$.MODULE$.None();
        return null;
    }

    public List<TraversalBuildStep> apply$default$2() {
        AppendTraversal appendTraversal = new AppendTraversal(PushNotUsed$.MODULE$);
        return Nil$.MODULE$.$colon$colon(appendTraversal);
    }

    public int apply$default$3() {
        return 0;
    }

    public Map<InPort, Object> apply$default$4() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<OutPort, Object> apply$default$5() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<BuilderKey, TraversalBuilder> apply$default$6() {
        return Predef$.MODULE$.Map().empty();
    }

    public Map<OutPort, BuilderKey> apply$default$7() {
        return Predef$.MODULE$.Map().empty();
    }

    public int apply$default$8() {
        return 0;
    }

    public Option<Tuple10<Traversal, List<TraversalBuildStep>, Object, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Object, Attributes, OptionVal<IslandTag>>> unapply(CompositeTraversalBuilder x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple10<Traversal, List<TraversalBuildStep>, Object, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Object, Attributes, OptionVal<IslandTag>>>(new Tuple10<Traversal, List<TraversalBuildStep>, Integer, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Integer, Attributes, OptionVal<IslandTag>>(x$0.finalSteps(), x$0.reverseBuildSteps(), BoxesRunTime.boxToInteger(x$0.inSlots()), x$0.inOffsets(), x$0.inBaseOffsetForOut(), x$0.pendingBuilders(), x$0.outOwners(), BoxesRunTime.boxToInteger(x$0.unwiredOuts()), x$0.attributes(), new OptionVal<IslandTag>(x$0.islandTag())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CompositeTraversalBuilder$.class);
    }

    private CompositeTraversalBuilder$() {
    }
}

