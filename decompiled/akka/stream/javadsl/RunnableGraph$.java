/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.javadsl.RunnableGraph;

public final class RunnableGraph$ {
    public static final RunnableGraph$ MODULE$ = new RunnableGraph$();

    public <Mat> RunnableGraph<Mat> fromGraph(Graph<ClosedShape, Mat> graph) {
        Graph<ClosedShape, Mat> graph2 = graph;
        if (graph2 instanceof RunnableGraph) {
            RunnableGraph runnableGraph = (RunnableGraph)graph2;
            return runnableGraph;
        }
        return new RunnableGraph.RunnableGraphAdapter<Mat>(akka.stream.scaladsl.RunnableGraph$.MODULE$.fromGraph(graph));
    }

    private RunnableGraph$() {
    }
}

