/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.Attributes;
import akka.stream.Shape;
import akka.stream.impl.fusing.GraphStageModule;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

public final class GraphStageModule$
implements Serializable {
    public static final GraphStageModule$ MODULE$ = new GraphStageModule$();

    public final String toString() {
        return "GraphStageModule";
    }

    public <S extends Shape, M> GraphStageModule<S, M> apply(S shape, Attributes attributes, GraphStageWithMaterializedValue<S, M> stage) {
        return new GraphStageModule<S, M>(shape, attributes, stage);
    }

    public <S extends Shape, M> Option<Tuple3<S, Attributes, GraphStageWithMaterializedValue<S, M>>> unapply(GraphStageModule<S, M> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<S, Attributes, GraphStageWithMaterializedValue<S, M>>>(new Tuple3<S, Attributes, GraphStageWithMaterializedValue<S, M>>(x$0.shape(), x$0.attributes(), x$0.stage()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(GraphStageModule$.class);
    }

    private GraphStageModule$() {
    }
}

