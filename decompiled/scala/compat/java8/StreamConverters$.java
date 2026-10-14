/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import scala.$less$colon$less;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.collection.MapOps;
import scala.collection.Stepper;
import scala.compat.java8.StreamExtensions;
import scala.compat.java8.converterImpl.Priority1AccumulatorConverters;
import scala.compat.java8.converterImpl.Priority2AccumulatorConverters;
import scala.compat.java8.converterImpl.Priority3AccumulatorConverters;
import scala.compat.java8.converterImpl.StepperExtensions;
import scala.compat.java8.converterImpl.StepperExtensions$mcD$sp;
import scala.compat.java8.converterImpl.StepperExtensions$mcI$sp;
import scala.compat.java8.converterImpl.StepperExtensions$mcJ$sp;

public final class StreamConverters$
implements StreamExtensions,
Priority1AccumulatorConverters {
    public static final StreamConverters$ MODULE$ = new StreamConverters$();

    static {
        StreamExtensions.$init$(MODULE$);
        Priority3AccumulatorConverters.$init$(MODULE$);
        Priority2AccumulatorConverters.$init$(MODULE$);
        Priority1AccumulatorConverters.$init$(MODULE$);
    }

    @Override
    public double[] accumulateDoubleArray(double[] underlying) {
        return Priority1AccumulatorConverters.accumulateDoubleArray$(this, underlying);
    }

    @Override
    public int[] accumulateIntArray(int[] underlying) {
        return Priority1AccumulatorConverters.accumulateIntArray$(this, underlying);
    }

    @Override
    public long[] accumulateLongArray(long[] underlying) {
        return Priority1AccumulatorConverters.accumulateLongArray$(this, underlying);
    }

    @Override
    public IterableOnce<Object> accumulateDoubleCollection(IterableOnce<Object> underlying) {
        return Priority2AccumulatorConverters.accumulateDoubleCollection$(this, underlying);
    }

    @Override
    public IterableOnce<Object> accumulateIntCollection(IterableOnce<Object> underlying) {
        return Priority2AccumulatorConverters.accumulateIntCollection$(this, underlying);
    }

    @Override
    public IterableOnce<Object> accumulateLongCollection(IterableOnce<Object> underlying) {
        return Priority2AccumulatorConverters.accumulateLongCollection$(this, underlying);
    }

    @Override
    public <A> Object accumulateAnyArray(Object underlying) {
        return Priority2AccumulatorConverters.accumulateAnyArray$(this, underlying);
    }

    @Override
    public <A> IterableOnce<A> collectionCanAccumulate(IterableOnce<A> underlying) {
        return Priority3AccumulatorConverters.collectionCanAccumulate$(this, underlying);
    }

    @Override
    public <A> StepperExtensions<A> richStepper(Stepper<A> s2) {
        return StreamExtensions.richStepper$(this, s2);
    }

    @Override
    public <A> StreamExtensions.IterableHasSeqStream<A> IterableHasSeqStream(IterableOnce<A> cc) {
        return StreamExtensions.IterableHasSeqStream$(this, cc);
    }

    @Override
    public <A, C extends IterableOnce<?>> StreamExtensions.IterableNonGenericHasParStream<A, C> IterableNonGenericHasParStream(C c, $less$colon$less<C, IterableOnce<A>> ev) {
        return StreamExtensions.IterableNonGenericHasParStream$(this, c, ev);
    }

    @Override
    public <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasSeqKeyValueStream<K, V, CC> MapHasSeqKeyValueStream(CC cc) {
        return StreamExtensions.MapHasSeqKeyValueStream$(this, cc);
    }

    @Override
    public <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasParKeyValueStream<K, V, CC> MapHasParKeyValueStream(CC cc) {
        return StreamExtensions.MapHasParKeyValueStream$(this, cc);
    }

    @Override
    public <A> StreamExtensions.StepperHasSeqStream<A> StepperHasSeqStream(Stepper<A> stepper) {
        return StreamExtensions.StepperHasSeqStream$(this, stepper);
    }

    @Override
    public <A> StreamExtensions.StepperHasParStream<A> StepperHasParStream(Stepper<A> stepper) {
        return StreamExtensions.StepperHasParStream$(this, stepper);
    }

    @Override
    public StreamExtensions.DoubleArrayHasSeqParStream DoubleArrayHasSeqParStream(double[] a) {
        return StreamExtensions.DoubleArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.IntArrayHasSeqParStream IntArrayHasSeqParStream(int[] a) {
        return StreamExtensions.IntArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.LongArrayHasSeqParStream LongArrayHasSeqParStream(long[] a) {
        return StreamExtensions.LongArrayHasSeqParStream$(this, a);
    }

    @Override
    public <A> StreamExtensions.AnyArrayHasSeqParStream<A> AnyArrayHasSeqParStream(A[] a) {
        return StreamExtensions.AnyArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.ByteArrayHasSeqParStream ByteArrayHasSeqParStream(byte[] a) {
        return StreamExtensions.ByteArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.ShortArrayHasSeqParStream ShortArrayHasSeqParStream(short[] a) {
        return StreamExtensions.ShortArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.CharArrayHasSeqParStream CharArrayHasSeqParStream(char[] a) {
        return StreamExtensions.CharArrayHasSeqParStream$(this, a);
    }

    @Override
    public StreamExtensions.FloatArrayHasSeqParStream FloatArrayHasSeqParStream(float[] a) {
        return StreamExtensions.FloatArrayHasSeqParStream$(this, a);
    }

    @Override
    public <A> StreamExtensions.StreamHasToScala<A> StreamHasToScala(Stream<A> stream) {
        return StreamExtensions.StreamHasToScala$(this, stream);
    }

    @Override
    public StreamExtensions.StreamIntHasAccumulatePrimitive StreamIntHasAccumulatePrimitive(Stream<Object> s2) {
        return StreamExtensions.StreamIntHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.StreamLongHasAccumulatePrimitive StreamLongHasAccumulatePrimitive(Stream<Object> s2) {
        return StreamExtensions.StreamLongHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.StreamDoubleHasAccumulatePrimitive StreamDoubleHasAccumulatePrimitive(Stream<Object> s2) {
        return StreamExtensions.StreamDoubleHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.StreamJIntegerHasAccumulatePrimitive StreamJIntegerHasAccumulatePrimitive(Stream<Integer> s2) {
        return StreamExtensions.StreamJIntegerHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.StreamJLongHasAccumulatePrimitive StreamJLongHasAccumulatePrimitive(Stream<Long> s2) {
        return StreamExtensions.StreamJLongHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.StreamJDoubleHasAccumulatePrimitive StreamJDoubleHasAccumulatePrimitive(Stream<Double> s2) {
        return StreamExtensions.StreamJDoubleHasAccumulatePrimitive$(this, s2);
    }

    @Override
    public StreamExtensions.IntStreamHasToScala IntStreamHasToScala(IntStream stream) {
        return StreamExtensions.IntStreamHasToScala$(this, stream);
    }

    @Override
    public StreamExtensions.LongStreamHasToScala LongStreamHasToScala(LongStream stream) {
        return StreamExtensions.LongStreamHasToScala$(this, stream);
    }

    @Override
    public StreamExtensions.DoubleStreamHasToScala DoubleStreamHasToScala(DoubleStream stream) {
        return StreamExtensions.DoubleStreamHasToScala$(this, stream);
    }

    public StepperExtensions<Object> richIntStepper(Stepper<Object> s2) {
        return new StepperExtensions$mcI$sp(s2);
    }

    public StepperExtensions<Object> richLongStepper(Stepper<Object> s2) {
        return new StepperExtensions$mcJ$sp(s2);
    }

    public StepperExtensions<Object> richDoubleStepper(Stepper<Object> s2) {
        return new StepperExtensions$mcD$sp(s2);
    }

    private StreamConverters$() {
    }
}

