/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.impl.CompletedTraversalBuilder;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Traversal;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.collection.immutable.Map;
import scala.runtime.AbstractFunction5;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class CompletedTraversalBuilder$
extends AbstractFunction5<Traversal, Object, Map<InPort, Object>, Attributes, OptionVal<IslandTag>, CompletedTraversalBuilder>
implements Serializable {
    public static final CompletedTraversalBuilder$ MODULE$ = new CompletedTraversalBuilder$();

    public IslandTag $lessinit$greater$default$5() {
        OptionVal$.MODULE$.None();
        return null;
    }

    @Override
    public final String toString() {
        return "CompletedTraversalBuilder";
    }

    @Override
    public CompletedTraversalBuilder apply(Traversal traversalSoFar, int inSlots, Map<InPort, Object> inToOffset, Attributes attributes, IslandTag islandTag) {
        return new CompletedTraversalBuilder(traversalSoFar, inSlots, inToOffset, attributes, islandTag);
    }

    public IslandTag apply$default$5() {
        OptionVal$.MODULE$.None();
        return null;
    }

    public Option<Tuple5<Traversal, Object, Map<InPort, Object>, Attributes, OptionVal<IslandTag>>> unapply(CompletedTraversalBuilder x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<Traversal, Object, Map<InPort, Object>, Attributes, OptionVal<IslandTag>>>(new Tuple5<Traversal, Integer, Map<InPort, Object>, Attributes, OptionVal<IslandTag>>(x$0.traversalSoFar(), BoxesRunTime.boxToInteger(x$0.inSlots()), x$0.inToOffset(), x$0.attributes(), new OptionVal<IslandTag>(x$0.islandTag())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(CompletedTraversalBuilder$.class);
    }

    private CompletedTraversalBuilder$() {
    }
}

