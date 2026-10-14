/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.scaladsl.MergeSequence;
import java.io.Serializable;
import scala.Function1;
import scala.math.Ordering;
import scala.math.Ordering$Long$;
import scala.package$;
import scala.runtime.BoxesRunTime;

public final class MergeSequence$ {
    public static final MergeSequence$ MODULE$ = new MergeSequence$();

    public <T> Ordering<MergeSequence.Pushed<T>> akka$stream$scaladsl$MergeSequence$$ordering() {
        return package$.MODULE$.Ordering().by((Function1<MergeSequence.Pushed, Object> & Serializable)x$8 -> BoxesRunTime.boxToLong(x$8.sequence()), Ordering$Long$.MODULE$).reverse();
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, Function1<T, Object> extractSequence) {
        return GraphStages$.MODULE$.withDetachedInputs(new MergeSequence<T>(inputPorts, extractSequence));
    }

    public <T> int apply$default$1() {
        return 2;
    }

    private MergeSequence$() {
    }
}

