/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import scala.$less$colon$less;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.collection.MapOps;
import scala.collection.Stepper;
import scala.collection.convert.StreamExtensions;

public final class StreamConverters$
implements StreamExtensions {
    public static final StreamConverters$ MODULE$ = new StreamConverters$();

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
    public StreamExtensions.StringHasSeqParStream StringHasSeqParStream(String s2) {
        return StreamExtensions.StringHasSeqParStream$(this, s2);
    }

    @Override
    public <A> StreamExtensions.StreamHasToScala<A> StreamHasToScala(Stream<A> stream) {
        return StreamExtensions.StreamHasToScala$(this, stream);
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

    private StreamConverters$() {
    }
}

