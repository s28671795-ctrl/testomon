/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.scaladsl.Partition;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.runtime.BoxesRunTime;

public final class Partition$ {
    public static final Partition$ MODULE$ = new Partition$();

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, Function<T, Integer> partitioner) {
        return new Partition(outputCount, (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)partitioner.apply(param2))), false);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, Function<T, Integer> partitioner, boolean eagerCancel) {
        return new Partition(outputCount, (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)partitioner.apply(param2))), eagerCancel);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, Function<T, Integer> partitioner) {
        return new Partition(outputCount, (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)partitioner.apply(param2))), false);
    }

    public <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, Function<T, Integer> partitioner, boolean eagerCancel) {
        return new Partition(outputCount, (Function1<Object, Object> & Serializable)param2 -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)partitioner.apply(param2))), eagerCancel);
    }

    private Partition$() {
    }
}

