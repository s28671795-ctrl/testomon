/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.UniformFanInShape;
import akka.stream.scaladsl.MergeLatest;
import akka.stream.stage.GraphStage;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.List;

public final class MergeLatest$ {
    public static final MergeLatest$ MODULE$ = new MergeLatest$();

    public <T> GraphStage<UniformFanInShape<T, List<T>>> apply(int inputPorts, boolean eagerComplete) {
        return new MergeLatest(inputPorts, eagerComplete, (Function1<Object, List> & Serializable)x$1 -> Predef$.MODULE$.genericWrapArray(x$1).toList());
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    private MergeLatest$() {
    }
}

