/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import scala.collection.AnyStepper;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.collection.StepperShape$;

public final class StreamConverters$ {
    public static final StreamConverters$ MODULE$ = new StreamConverters$();

    public <A> Stream<A> asJavaSeqStream(IterableOnce<A> cc) {
        return StreamSupport.stream(((AnyStepper)cc.stepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), false);
    }

    public IntStream asJavaSeqIntStream(IterableOnce<Integer> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), false);
    }

    public IntStream asJavaSeqIntStreamFromByte(IterableOnce<Byte> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), false);
    }

    public IntStream asJavaSeqIntStreamFromShort(IterableOnce<Short> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), false);
    }

    public IntStream asJavaSeqIntStreamFromChar(IterableOnce<Character> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), false);
    }

    public DoubleStream asJavaSeqDoubleStream(IterableOnce<Double> cc) {
        return StreamSupport.doubleStream(cc.stepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), false);
    }

    public DoubleStream asJavaSeqDoubleStreamFromFloat(IterableOnce<Float> cc) {
        return StreamSupport.doubleStream(cc.stepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), false);
    }

    public LongStream asJavaSeqLongStream(IterableOnce<Long> cc) {
        return StreamSupport.longStream(cc.stepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), false);
    }

    public <K, V> Stream<K> asJavaSeqKeyStream(Map<K, V> m4) {
        return StreamSupport.stream(((AnyStepper)m4.keyStepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), false);
    }

    public <V> IntStream asJavaSeqKeyIntStream(Map<Integer, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), false);
    }

    public <V> IntStream asJavaSeqKeyIntStreamFromByte(Map<Byte, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), false);
    }

    public <V> IntStream asJavaSeqKeyIntStreamFromShort(Map<Short, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), false);
    }

    public <V> IntStream asJavaSeqKeyIntStreamFromChar(Map<Character, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), false);
    }

    public <V> DoubleStream asJavaSeqKeyDoubleStream(Map<Double, V> m4) {
        return StreamSupport.doubleStream(m4.keyStepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), false);
    }

    public <V> DoubleStream asJavaSeqKeyDoubleStreamFromFloat(Map<Float, V> m4) {
        return StreamSupport.doubleStream(m4.keyStepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), false);
    }

    public <V> LongStream asJavaSeqKeyLongStream(Map<Long, V> m4) {
        return StreamSupport.longStream(m4.keyStepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), false);
    }

    public <K, V> Stream<V> asJavaSeqValueStream(Map<K, V> m4) {
        return StreamSupport.stream(((AnyStepper)m4.valueStepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), false);
    }

    public <K> IntStream asJavaSeqValueIntStream(Map<K, Integer> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), false);
    }

    public <K> IntStream asJavaSeqValueIntStreamFromByte(Map<K, Byte> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), false);
    }

    public <K> IntStream asJavaSeqValueIntStreamFromShort(Map<K, Short> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), false);
    }

    public <K> IntStream asJavaSeqValueIntStreamFromChar(Map<K, Character> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), false);
    }

    public <K> DoubleStream asJavaSeqValueDoubleStream(Map<K, Double> m4) {
        return StreamSupport.doubleStream(m4.valueStepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), false);
    }

    public <K> DoubleStream asJavaSeqValueDoubleStreamFromFloat(Map<K, Float> m4) {
        return StreamSupport.doubleStream(m4.valueStepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), false);
    }

    public <K> LongStream asJavaSeqValueLongStream(Map<K, Long> m4) {
        return StreamSupport.longStream(m4.valueStepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), false);
    }

    public <A> Stream<A> asJavaParStream(IterableOnce<A> cc) {
        return StreamSupport.stream(((AnyStepper)cc.stepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), true);
    }

    public IntStream asJavaParIntStream(IterableOnce<Integer> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), true);
    }

    public IntStream asJavaParIntStreamFromByte(IterableOnce<Byte> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), true);
    }

    public IntStream asJavaParIntStreamFromShort(IterableOnce<Short> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), true);
    }

    public IntStream asJavaParIntStreamFromChar(IterableOnce<Character> cc) {
        return StreamSupport.intStream(cc.stepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), true);
    }

    public DoubleStream asJavaParDoubleStream(IterableOnce<Double> cc) {
        return StreamSupport.doubleStream(cc.stepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), true);
    }

    public DoubleStream asJavaParDoubleStreamFromFloat(IterableOnce<Float> cc) {
        return StreamSupport.doubleStream(cc.stepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), true);
    }

    public LongStream asJavaParLongStream(IterableOnce<Long> cc) {
        return StreamSupport.longStream(cc.stepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), true);
    }

    public <K, V> Stream<K> asJavaParKeyStream(Map<K, V> m4) {
        return StreamSupport.stream(((AnyStepper)m4.keyStepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), true);
    }

    public <V> IntStream asJavaParKeyIntStream(Map<Integer, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), true);
    }

    public <V> IntStream asJavaParKeyIntStreamFromByte(Map<Byte, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), true);
    }

    public <V> IntStream asJavaParKeyIntStreamFromShort(Map<Short, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), true);
    }

    public <V> IntStream asJavaParKeyIntStreamFromChar(Map<Character, V> m4) {
        return StreamSupport.intStream(m4.keyStepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), true);
    }

    public <V> DoubleStream asJavaParKeyDoubleStream(Map<Double, V> m4) {
        return StreamSupport.doubleStream(m4.keyStepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), true);
    }

    public <V> DoubleStream asJavaParKeyDoubleStreamFromFloat(Map<Float, V> m4) {
        return StreamSupport.doubleStream(m4.keyStepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), true);
    }

    public <V> LongStream asJavaParKeyLongStream(Map<Long, V> m4) {
        return StreamSupport.longStream(m4.keyStepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), true);
    }

    public <K, V> Stream<V> asJavaParValueStream(Map<K, V> m4) {
        return StreamSupport.stream(((AnyStepper)m4.valueStepper(StepperShape$.MODULE$.anyStepperShapePrototype())).spliterator(), true);
    }

    public <K> IntStream asJavaParValueIntStream(Map<K, Integer> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jIntegerStepperShape()).spliterator(), true);
    }

    public <K> IntStream asJavaParValueIntStreamFromByte(Map<K, Byte> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jByteStepperShape()).spliterator(), true);
    }

    public <K> IntStream asJavaParValueIntStreamFromShort(Map<K, Short> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jShortStepperShape()).spliterator(), true);
    }

    public <K> IntStream asJavaParValueIntStreamFromChar(Map<K, Character> m4) {
        return StreamSupport.intStream(m4.valueStepper(StepperShape$.MODULE$.jCharacterStepperShape()).spliterator(), true);
    }

    public <K> DoubleStream asJavaParValueDoubleStream(Map<K, Double> m4) {
        return StreamSupport.doubleStream(m4.valueStepper(StepperShape$.MODULE$.jDoubleStepperShape()).spliterator(), true);
    }

    public <K> DoubleStream asJavaParValueDoubleStreamFromFloat(Map<K, Float> m4) {
        return StreamSupport.doubleStream(m4.valueStepper(StepperShape$.MODULE$.jFloatStepperShape()).spliterator(), true);
    }

    public <K> LongStream asJavaParValueLongStream(Map<K, Long> m4) {
        return StreamSupport.longStream(m4.valueStepper(StepperShape$.MODULE$.jLongStepperShape()).spliterator(), true);
    }

    private StreamConverters$() {
    }
}

