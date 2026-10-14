/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.scaladsl.Balance;

public final class Balance$ {
    public static final Balance$ MODULE$ = new Balance$();

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean waitForAllDownstreams) {
        return akka.stream.scaladsl.Balance$.MODULE$.apply(outputCount, waitForAllDownstreams);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, boolean waitForAllDownstreams, boolean eagerCancel) {
        return new Balance(outputCount, waitForAllDownstreams, eagerCancel);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount) {
        return this.create(outputCount, false);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount) {
        return this.create(outputCount);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, boolean waitForAllDownstreams) {
        return this.create(outputCount, waitForAllDownstreams);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, boolean waitForAllDownstreams, boolean eagerCancel) {
        return new Balance(outputCount, waitForAllDownstreams, eagerCancel);
    }

    private Balance$() {
    }
}

