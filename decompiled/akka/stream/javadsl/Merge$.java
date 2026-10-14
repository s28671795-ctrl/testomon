/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;

public final class Merge$ {
    public static final Merge$ MODULE$ = new Merge$();

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputPorts) {
        return akka.stream.scaladsl.Merge$.MODULE$.apply(inputPorts, akka.stream.scaladsl.Merge$.MODULE$.apply$default$2());
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputPorts) {
        return this.create(inputPorts);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputPorts, boolean eagerComplete) {
        return akka.stream.scaladsl.Merge$.MODULE$.apply(inputPorts, eagerComplete);
    }

    public <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputPorts, boolean eagerComplete) {
        return this.create(inputPorts, eagerComplete);
    }

    private Merge$() {
    }
}

