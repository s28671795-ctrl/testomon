/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.javadsl.BidiFlow;
import akka.stream.javadsl.Flow$;
import akka.stream.javadsl.package$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function1;

public final class BidiFlow$ {
    public static final BidiFlow$ MODULE$ = new BidiFlow$();
    private static final BidiFlow<Object, Object, Object, Object, NotUsed> _identity = MODULE$.fromFlows(Flow$.MODULE$.of(Object.class), Flow$.MODULE$.of(Object.class));

    public <A, B> BidiFlow<A, A, B, B, NotUsed> identity() {
        return _identity;
    }

    public <I1, O1, I2, O2, M> BidiFlow<I1, O1, I2, O2, M> fromGraph(Graph<BidiShape<I1, O1, I2, O2>, M> g2) {
        Graph<BidiShape<I1, O1, I2, O2>, M> graph = g2;
        if (graph instanceof BidiFlow) {
            BidiFlow bidiFlow = (BidiFlow)graph;
            return bidiFlow;
        }
        return new BidiFlow<I1, O1, I2, O2, M>(akka.stream.scaladsl.BidiFlow$.MODULE$.fromGraph(graph));
    }

    public <I1, O1, I2, O2, M1, M2, M> BidiFlow<I1, O1, I2, O2, M> fromFlowsMat(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2, Function2<M1, M2, M> combine) {
        return new BidiFlow<I1, O1, I2, O2, M>(akka.stream.scaladsl.BidiFlow$.MODULE$.fromFlowsMat(flow1, flow2, package$.MODULE$.combinerToScala(combine)));
    }

    public <I1, O1, I2, O2, M1, M2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFlows(Graph<FlowShape<I1, O1>, M1> flow1, Graph<FlowShape<I2, O2>, M2> flow2) {
        return new BidiFlow<I1, O1, I2, O2, NotUsed>(akka.stream.scaladsl.BidiFlow$.MODULE$.fromFlows(flow1, flow2));
    }

    public <I1, O1, I2, O2> BidiFlow<I1, O1, I2, O2, NotUsed> fromFunctions(Function<I1, O1> top, Function<I2, O2> bottom) {
        return new BidiFlow(akka.stream.scaladsl.BidiFlow$.MODULE$.fromFunctions((Function1<Object, Object> & Serializable)(I1 param2) -> top.apply(param2), (Function1<Object, Object> & Serializable)(I2 param2) -> bottom.apply(param2)));
    }

    public <I, O> BidiFlow<I, I, O, O, NotUsed> bidirectionalIdleTimeout(Duration timeout2) {
        return new BidiFlow(akka.stream.scaladsl.BidiFlow$.MODULE$.bidirectionalIdleTimeout(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))));
    }

    private BidiFlow$() {
    }
}

