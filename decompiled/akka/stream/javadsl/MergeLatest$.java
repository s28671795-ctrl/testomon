/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.UniformFanInShape;
import akka.stream.scaladsl.MergeLatest;
import akka.stream.stage.GraphStage;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.Predef$;

public final class MergeLatest$ {
    public static final MergeLatest$ MODULE$ = new MergeLatest$();

    public <T> GraphStage<UniformFanInShape<T, List<T>>> create(int inputPorts, boolean eagerComplete) {
        return new MergeLatest(inputPorts, eagerComplete, (Function1<Object, List> & Serializable)x -> package$JavaConverters$.MODULE$.SeqHasAsJava(Predef$.MODULE$.genericWrapArray(x).toList()).asJava());
    }

    public <T> GraphStage<UniformFanInShape<T, List<T>>> create(int inputPorts) {
        return this.create(inputPorts, false);
    }

    private MergeLatest$() {
    }
}

