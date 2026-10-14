/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;

public final class Concat$ {
    public static final Concat$ MODULE$ = new Concat$();

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create() {
        return akka.stream.scaladsl.Concat$.MODULE$.apply(akka.stream.scaladsl.Concat$.MODULE$.apply$default$1());
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount) {
        return akka.stream.scaladsl.Concat$.MODULE$.apply(inputCount);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount, boolean detachedInputs) {
        return akka.stream.scaladsl.Concat$.MODULE$.apply(inputCount, detachedInputs);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz) {
        return this.create();
    }

    private Concat$() {
    }
}

