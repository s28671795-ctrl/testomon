/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.ClosedShape;
import akka.stream.Graph;
import akka.stream.impl.TraversalBuilder;
import akka.stream.scaladsl.RunnableGraph;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class RunnableGraph$
implements Serializable {
    public static final RunnableGraph$ MODULE$ = new RunnableGraph$();

    public <Mat> RunnableGraph<Mat> fromGraph(Graph<ClosedShape, Mat> g2) {
        Graph<ClosedShape, Mat> graph = g2;
        if (graph instanceof RunnableGraph) {
            RunnableGraph runnableGraph = (RunnableGraph)graph;
            return runnableGraph;
        }
        return new RunnableGraph(graph.traversalBuilder());
    }

    public <Mat> RunnableGraph<Mat> apply(TraversalBuilder traversalBuilder) {
        return new RunnableGraph(traversalBuilder);
    }

    public <Mat> Option<TraversalBuilder> unapply(RunnableGraph<Mat> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<TraversalBuilder>(x$0.traversalBuilder());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RunnableGraph$.class);
    }

    private RunnableGraph$() {
    }
}

