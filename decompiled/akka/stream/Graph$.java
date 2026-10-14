/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.scaladsl.GenericGraph;
import scala.Function1;

public final class Graph$ {
    public static final Graph$ MODULE$ = new Graph$();

    public <S extends Shape, M1, M2> Graph<S, M2> mapMaterializedValue(Graph<S, M1> g2, Function1<M1, M2> f) {
        return new GenericGraph<S, M1>(g2.shape(), g2.traversalBuilder()).mapMaterializedValue(f);
    }

    public final <S extends Shape, M> Graph.GraphMapMatVal<S, M> GraphMapMatVal(Graph<S, M> self) {
        return new Graph.GraphMapMatVal<S, M>(self);
    }

    private Graph$() {
    }
}

