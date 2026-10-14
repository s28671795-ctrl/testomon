/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.PartitionHub;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.scaladsl.PartitionHub;
import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.ToLongBiFunction;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Predef$;
import scala.runtime.BoxesRunTime;

public final class PartitionHub$ {
    public static final PartitionHub$ MODULE$ = new PartitionHub$();

    public <T> Sink<T, Source<T, NotUsed>> ofStateful(Class<T> clazz, Supplier<ToLongBiFunction<PartitionHub.ConsumerInfo, T>> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        Function0<Function2> & Serializable p = (Function0<Function2> & Serializable)() -> {
            ToLongBiFunction f = (ToLongBiFunction)partitioner.get();
            return (Function2<PartitionHub.ConsumerInfo, Object, Object> & Serializable)(info, elem) -> BoxesRunTime.boxToLong(f.applyAsLong(info, elem));
        };
        return akka.stream.scaladsl.PartitionHub$.MODULE$.statefulSink(p, startAfterNrOfConsumers, bufferSize).mapMaterializedValue((Function1<akka.stream.scaladsl.Source, Source> & Serializable)x$3 -> x$3.asJava()).asJava();
    }

    public <T> Sink<T, Source<T, NotUsed>> ofStateful(Class<T> clazz, Supplier<ToLongBiFunction<PartitionHub.ConsumerInfo, T>> partitioner, int startAfterNrOfConsumers) {
        return this.ofStateful(clazz, partitioner, startAfterNrOfConsumers, akka.stream.scaladsl.PartitionHub$.MODULE$.defaultBufferSize());
    }

    public <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, BiFunction<Integer, T, Integer> partitioner, int startAfterNrOfConsumers, int bufferSize) {
        return akka.stream.scaladsl.PartitionHub$.MODULE$.sink((Function2<Object, Object, Object> & Serializable)(size, elem) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)partitioner.apply(Predef$.MODULE$.int2Integer(BoxesRunTime.unboxToInt(size)), elem))), startAfterNrOfConsumers, bufferSize).mapMaterializedValue((Function1<akka.stream.scaladsl.Source, Source> & Serializable)x$4 -> x$4.asJava()).asJava();
    }

    public <T> Sink<T, Source<T, NotUsed>> of(Class<T> clazz, BiFunction<Integer, T, Integer> partitioner, int startAfterNrOfConsumers) {
        return this.of(clazz, partitioner, startAfterNrOfConsumers, akka.stream.scaladsl.PartitionHub$.MODULE$.defaultBufferSize());
    }

    private PartitionHub$() {
    }
}

