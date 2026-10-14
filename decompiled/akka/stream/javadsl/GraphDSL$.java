/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.japi.function.Function2;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.javadsl.GraphCreate;
import akka.stream.javadsl.GraphDSL;
import akka.stream.scaladsl.GenericGraph;
import akka.stream.scaladsl.GraphDSL;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.mutable.Buffer;
import scala.runtime.ScalaRunTime$;

public final class GraphDSL$
extends GraphCreate {
    public static final GraphDSL$ MODULE$ = new GraphDSL$();

    public <M> GraphDSL.Builder<M> builder() {
        return new GraphDSL.Builder(new GraphDSL.Builder());
    }

    public <IS extends Shape, S extends Shape, M, G extends Graph<IS, M>> Graph<S, List<M>> create(List<G> graphs, Function2<GraphDSL.Builder<List<M>>, List<IS>, S> buildBlock) {
        Predef$.MODULE$.require(!graphs.isEmpty(), (Function0<Object>)(Function0<String> & Serializable)() -> "The input list must have one or more Graph elements");
        GraphDSL.Builder gbuilder = this.builder();
        Function1<Object, ArrayList> & Serializable toList = (Function1<Object, ArrayList> & Serializable)m1 -> new ArrayList<Object>(Arrays.asList(ScalaRunTime$.MODULE$.toObjectArray(new Object[]{m1})));
        scala.Function2<List, Object, ArrayList> & Serializable combine = (scala.Function2<List, Object, ArrayList> & Serializable)(s2, m22) -> {
            ArrayList<Object> newList = new ArrayList<Object>((Collection<Object>)s2);
            newList.add(m22);
            return newList;
        };
        Object sListH = gbuilder.delegate().add((Graph)graphs.get(0), toList);
        List sListT = package$JavaConverters$.MODULE$.BufferHasAsJava((Buffer)package$JavaConverters$.MODULE$.ListHasAsScala(graphs.subList(1, graphs.size())).asScala().map((Function1<Graph, Shape> & Serializable)g2 -> gbuilder.delegate().add(g2, combine))).asJava();
        ArrayList<Object> newList = new ArrayList<Object>();
        newList.add(sListH);
        newList.addAll(sListT);
        Shape s3 = (Shape)buildBlock.apply(gbuilder, newList);
        return new GenericGraph(s3, gbuilder.delegate().result(s3));
    }

    private GraphDSL$() {
    }
}

