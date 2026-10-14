/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.LongStepper;
import scala.collection.Map;
import scala.collection.StepperShape;
import scala.jdk.AnyAccumulator;
import scala.jdk.DoubleAccumulator$;
import scala.jdk.IntAccumulator$;
import scala.jdk.LongAccumulator$;
import scala.jdk.javaapi.StreamConverters;

public class ScalaStreamSupport {
    public static <T> Stream<T> stream(IterableOnce<T> iterableOnce) {
        return StreamConverters.asJavaSeqStream(iterableOnce);
    }

    public static <K> Stream<K> streamKeys(Map<K, ?> map) {
        return StreamSupport.stream(map.keyStepper(StepperShape.anyStepperShape()).spliterator(), false);
    }

    public static <V> Stream<V> streamValues(Map<?, V> map) {
        return StreamSupport.stream(map.valueStepper(StepperShape.anyStepperShape()).spliterator(), false);
    }

    public static <K, V> Stream<Tuple2<K, V>> stream(Map<K, V> map) {
        return StreamConverters.asJavaSeqStream(map);
    }

    public static <T> Stream<T> streamAccumulated(IterableOnce<T> iterableOnce) {
        return StreamConverters.asJavaSeqStream(AnyAccumulator.from(iterableOnce));
    }

    public static <K> Stream<K> streamAccumulatedKeys(Map<K, ?> map) {
        return StreamConverters.asJavaSeqStream(AnyAccumulator.from(map.keysIterator()));
    }

    public static <V> Stream<V> streamAccumulatedValues(Map<?, V> map) {
        return StreamConverters.asJavaSeqStream(AnyAccumulator.from(map.valuesIterator()));
    }

    public static DoubleStream doubleStream(IterableOnce<Double> iterableOnce) {
        return StreamConverters.asJavaSeqDoubleStream(iterableOnce);
    }

    public static DoubleStream doubleStreamKeys(Map<Double, ?> map) {
        return StreamSupport.doubleStream(map.keyStepper(StepperShape.doubleStepperShape()).spliterator(), false);
    }

    public static DoubleStream doubleStreamValues(Map<?, Double> map) {
        return StreamSupport.doubleStream(map.valueStepper(StepperShape.doubleStepperShape()).spliterator(), false);
    }

    public static DoubleStream doubleStreamAccumulated(IterableOnce<Double> iterableOnce) {
        return StreamConverters.asJavaSeqDoubleStream((IterableOnce)DoubleAccumulator$.MODULE$.fromSpecific((IterableOnce)iterableOnce));
    }

    public static DoubleStream doubleStreamAccumulatedKeys(Map<Double, ?> map) {
        return StreamConverters.asJavaSeqDoubleStream((IterableOnce)DoubleAccumulator$.MODULE$.fromSpecific((IterableOnce)map.keysIterator()));
    }

    public static DoubleStream doubleStreamAccumulatedValues(Map<?, Double> map) {
        return StreamConverters.asJavaSeqDoubleStream((IterableOnce)DoubleAccumulator$.MODULE$.fromSpecific((IterableOnce)map.valuesIterator()));
    }

    public static IntStream intStream(IterableOnce<Integer> iterableOnce) {
        return StreamConverters.asJavaSeqIntStream(iterableOnce);
    }

    public static IntStream intStreamKeys(Map<Integer, ?> map) {
        return StreamSupport.intStream(map.keyStepper(StepperShape.intStepperShape()).spliterator(), false);
    }

    public static IntStream intStreamValues(Map<?, Integer> map) {
        return StreamSupport.intStream(map.valueStepper(StepperShape.intStepperShape()).spliterator(), false);
    }

    public static IntStream intStreamAccumulated(IterableOnce<Integer> iterableOnce) {
        return StreamConverters.asJavaSeqIntStream((IterableOnce)IntAccumulator$.MODULE$.fromSpecific((IterableOnce)iterableOnce));
    }

    public static IntStream intStreamAccumulatedKeys(Map<Integer, ?> map) {
        return StreamConverters.asJavaSeqIntStream((IterableOnce)IntAccumulator$.MODULE$.fromSpecific((IterableOnce)map.keysIterator()));
    }

    public static IntStream intStreamAccumulatedValues(Map<?, Integer> map) {
        return StreamConverters.asJavaSeqIntStream((IterableOnce)IntAccumulator$.MODULE$.fromSpecific((IterableOnce)map.valuesIterator()));
    }

    public static LongStream longStream(IterableOnce<Long> iterableOnce) {
        return StreamConverters.asJavaSeqLongStream(iterableOnce);
    }

    public static LongStream longStreamKeys(Map<Long, ?> map) {
        return StreamSupport.longStream(((LongStepper)((Object)map.keyStepper(StepperShape.doubleStepperShape()))).spliterator(), false);
    }

    public static LongStream longStreamValues(Map<?, Long> map) {
        return StreamSupport.longStream(((LongStepper)((Object)map.valueStepper(StepperShape.doubleStepperShape()))).spliterator(), false);
    }

    public static LongStream longStreamAccumulated(IterableOnce<Long> iterableOnce) {
        return StreamConverters.asJavaSeqLongStream((IterableOnce)LongAccumulator$.MODULE$.fromSpecific((IterableOnce)iterableOnce));
    }

    public static LongStream longStreamAccumulatedKeys(Map<Long, ?> map) {
        return StreamConverters.asJavaSeqLongStream((IterableOnce)LongAccumulator$.MODULE$.fromSpecific((IterableOnce)map.keysIterator()));
    }

    public static LongStream longStreamAccumulatedValues(Map<?, Long> map) {
        return StreamConverters.asJavaSeqLongStream((IterableOnce)LongAccumulator$.MODULE$.fromSpecific((IterableOnce)map.valuesIterator()));
    }
}

