/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.Timers;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Keep$;
import scala.Function1;
import scala.Function2;
import scala.concurrent.duration.FiniteDuration;

public final class BidiFlow$ {
    public static final BidiFlow$ MODULE$ = new BidiFlow$();
    private static final BidiFlow<Object, Object, Object, Object, NotUsed> _identity = MODULE$.fromFlows(Flow$.MODULE$.apply(), Flow$.MODULE$.apply());

    public <A, B> BidiFlow<A, A, B, B, NotUsed> identity() {
        return _identity;
    }

    public <I1, O1, I2, O2, Mat> BidiFlow<I1, O1, I2, O2, Mat> fromGraph(Graph<BidiShape<I1, O1, I2, O2>, Mat> graph) {
        Graph<BidiShape<I1, O1, I2, O2>, Mat> graph2 = graph;
        if (graph2 instanceof BidiFlow) {
            BidiFlow bidiFlow = (BidiFlow)graph2;
            return bidiFlow;
        }
        if (graph2 instanceof akka.stream.javadsl.BidiFlow) {
            akka.stream.javadsl.BidiFlow bidiFlow = (akka.stream.javadsl.BidiFlow)graph2;
            return bidiFlow.asScala();
        }
        return new BidiFlow(graph2.traversalBuilder(), graph2.shape());
    }

    public <I1, O1, I2, O2, M1, M2, M> BidiFlow<I1, O1, I2, O2, M> fromFlowsMat(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2, Function2<M1, M2, M> combine) {
        Shape newFlow1Shape = flow1.shape().deepCopy();
        Shape newFlow2Shape = flow2.shape().deepCopy();
        return new BidiFlow(TraversalBuilder$.MODULE$.empty(TraversalBuilder$.MODULE$.empty$default$1()).add(flow1.traversalBuilder(), newFlow1Shape, Keep$.MODULE$.right()).add(flow2.traversalBuilder(), newFlow2Shape, combine), new BidiShape(((FlowShape)newFlow1Shape).in(), ((FlowShape)newFlow1Shape).out(), ((FlowShape)newFlow2Shape).in(), ((FlowShape)newFlow2Shape).out()));
    }

    public <I1, O1, I2, O2, M1, M2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFlows(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2) {
        return this.fromFlowsMat(flow1, flow2, Keep$.MODULE$.none());
    }

    public <I1, O1, I2, O2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFunctions(Function1<I1, O1> outbound, Function1<I2, O2> inbound) {
        return this.fromFlows((Graph)((Object)Flow$.MODULE$.apply().map(outbound)), (Graph)((Object)Flow$.MODULE$.apply().map(inbound)));
    }

    public <I, O> BidiFlow<I, I, O, O, NotUsed> bidirectionalIdleTimeout(FiniteDuration timeout2) {
        return this.fromGraph(new Timers.IdleTimeoutBidi(timeout2));
    }

    private BidiFlow$() {
    }
}

