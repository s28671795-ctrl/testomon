/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;

public final class Broadcast$ {
    public static final Broadcast$ MODULE$ = new Broadcast$();

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean eagerCancel) {
        return akka.stream.scaladsl.Broadcast$.MODULE$.apply(outputCount, eagerCancel);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount) {
        return this.create(outputCount, false);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount) {
        return this.create(outputCount);
    }

    private Broadcast$() {
    }
}

