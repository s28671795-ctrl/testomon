/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.scaladsl.Concat;
import akka.stream.stage.GraphStage;

public final class Concat$ {
    public static final Concat$ MODULE$ = new Concat$();
    private static final Concat<Object> _concatTwo = new Concat(2);

    private Concat<Object> _concatTwo() {
        return _concatTwo;
    }

    private <T> GraphStage<UniformFanInShape<T, T>> concatTwo() {
        return this._concatTwo();
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts) {
        return this.apply(inputPorts, true);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, boolean detachedInputs) {
        Concat concat;
        Concat concat2 = concat = inputPorts == 2 ? this.concatTwo() : new Concat(inputPorts);
        if (detachedInputs) {
            return GraphStages$.MODULE$.withDetachedInputs(concat);
        }
        return concat;
    }

    public <T> int apply$default$1() {
        return 2;
    }

    private Concat$() {
    }
}

