/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.Done;
import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.FlowMonitor;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanInShape$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.GraphStages$Detacher$;
import akka.stream.impl.fusing.GraphStages$Identity$;
import akka.stream.impl.fusing.GraphStages$TerminationWatcher$;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;

@InternalApi
public final class GraphStages$ {
    public static final GraphStages$ MODULE$ = new GraphStages$();

    public <T> GraphStages.SimpleLinearGraphStage<T> identity() {
        return GraphStages$Identity$.MODULE$;
    }

    public <T> GraphStage<FlowShape<T, T>> detacher() {
        return GraphStages$Detacher$.MODULE$;
    }

    public <T> GraphStageWithMaterializedValue<FlowShape<T, T>, Future<Done>> terminationWatcher() {
        return GraphStages$TerminationWatcher$.MODULE$;
    }

    public <T> GraphStageWithMaterializedValue<FlowShape<T, T>, FlowMonitor<T>> monitor() {
        return new GraphStages.MonitorFlow();
    }

    @InternalApi
    public <T> Graph<UniformFanInShape<T, T>, NotUsed> withDetachedInputs(GraphStage<UniformFanInShape<T, T>> stage) {
        return GraphDSL$.MODULE$.create((Function1<GraphDSL.Builder, UniformFanInShape> & Serializable)builder -> {
            UniformFanInShape concat = (UniformFanInShape)builder.add(stage);
            Seq ds = (Seq)concat.inlets().map((Function1<Inlet, Inlet> & Serializable)inlet -> {
                FlowShape detacher = (FlowShape)builder.add(MODULE$.detacher());
                GraphDSL$Implicits$.MODULE$.flow2flow(detacher, (GraphDSL.Builder<?>)builder).$tilde$greater(inlet, (GraphDSL.Builder<?>)builder);
                return detacher.in();
            });
            return UniformFanInShape$.MODULE$.apply(concat.out(), ds);
        });
    }

    private GraphStages$() {
    }
}

