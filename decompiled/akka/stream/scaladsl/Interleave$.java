/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.scaladsl.Interleave;

public final class Interleave$ {
    public static final Interleave$ MODULE$ = new Interleave$();

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, int segmentSize, boolean eagerClose) {
        return GraphStages$.MODULE$.withDetachedInputs(new Interleave(inputPorts, segmentSize, eagerClose));
    }

    public <T> boolean apply$default$3() {
        return false;
    }

    private Interleave$() {
    }
}

