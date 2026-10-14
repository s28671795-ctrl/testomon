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
import scala.compat.java8.StreamConverters$;
import scala.compat.java8.StreamExtensions;
import scala.compat.java8.converterImpl.StepperExtensions;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005;QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQaH\u0001\u0005\u0002\u0001BQ!I\u0001\u0005\u0004\tBQ!M\u0001\u0005\u0004IBQ!O\u0001\u0005\u0004i\n\u0001c\u0015;sK\u0006l7i\u001c8wKJ$XM]:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005=\tQ\"A\u0004\u0003!M#(/Z1n\u0007>tg/\u001a:uKJ\u001c8\u0003B\u0001\u0013-e\u0001\"a\u0005\u000b\u000e\u0003-I!!F\u0006\u0003\r\u0005s\u0017PU3g!\tyq#\u0003\u0002\u0019\u000f\t\u00012\u000b\u001e:fC6,\u0005\u0010^3og&|gn\u001d\t\u00035ui\u0011a\u0007\u0006\u00039\u001d\tQbY8om\u0016\u0014H/\u001a:J[Bd\u0017B\u0001\u0010\u001c\u0005y\u0001&/[8sSRL\u0018'Q2dk6,H.\u0019;pe\u000e{gN^3si\u0016\u00148/\u0001\u0004=S:LGO\u0010\u000b\u0002\u001d\u0005q!/[2i\u0013:$8\u000b^3qa\u0016\u0014HCA\u0012*!\rQBEJ\u0005\u0003Km\u0011\u0011c\u0015;faB,'/\u0012=uK:\u001c\u0018n\u001c8t!\t\u0019r%\u0003\u0002)\u0017\t\u0019\u0011J\u001c;\t\u000b)\u001a\u0001\u0019A\u0016\u0002\u0003M\u00042\u0001L\u0018'\u001b\u0005i#B\u0001\u0018\f\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003a5\u0012qa\u0015;faB,'/A\bsS\u000eDGj\u001c8h'R,\u0007\u000f]3s)\t\u0019t\u0007E\u0002\u001bIQ\u0002\"aE\u001b\n\u0005YZ!\u0001\u0002'p]\u001eDQA\u000b\u0003A\u0002a\u00022\u0001L\u00185\u0003E\u0011\u0018n\u00195E_V\u0014G.Z*uKB\u0004XM\u001d\u000b\u0003w}\u00022A\u0007\u0013=!\t\u0019R(\u0003\u0002?\u0017\t1Ai\\;cY\u0016DQAK\u0003A\u0002\u0001\u00032\u0001L\u0018=\u0001")
public final class StreamConverters {
    public static StepperExtensions<Object> richDoubleStepper(Stepper<Object> stepper) {
        return StreamConverters$.MODULE$.richDoubleStepper(stepper);
    }

    public static StepperExtensions<Object> richLongStepper(Stepper<Object> stepper) {
        return StreamConverters$.MODULE$.richLongStepper(stepper);
    }

    public static StepperExtensions<Object> richIntStepper(Stepper<Object> stepper) {
        return StreamConverters$.MODULE$.richIntStepper(stepper);
    }

    public static long[] accumulateLongArray(long[] lArray) {
        return StreamConverters$.MODULE$.accumulateLongArray(lArray);
    }

    public static int[] accumulateIntArray(int[] nArray) {
        return StreamConverters$.MODULE$.accumulateIntArray(nArray);
    }

    public static double[] accumulateDoubleArray(double[] dArray) {
        return StreamConverters$.MODULE$.accumulateDoubleArray(dArray);
    }

    public static Object accumulateAnyArray(Object object) {
        return StreamConverters$.MODULE$.accumulateAnyArray(object);
    }

    public static IterableOnce accumulateLongCollection(IterableOnce iterableOnce) {
        return StreamConverters$.MODULE$.accumulateLongCollection(iterableOnce);
    }

    public static IterableOnce accumulateIntCollection(IterableOnce iterableOnce) {
        return StreamConverters$.MODULE$.accumulateIntCollection(iterableOnce);
    }

    public static IterableOnce accumulateDoubleCollection(IterableOnce iterableOnce) {
        return StreamConverters$.MODULE$.accumulateDoubleCollection(iterableOnce);
    }

    public static IterableOnce collectionCanAccumulate(IterableOnce iterableOnce) {
        return StreamConverters$.MODULE$.collectionCanAccumulate(iterableOnce);
    }

    public static StreamExtensions.DoubleStreamHasToScala DoubleStreamHasToScala(DoubleStream doubleStream) {
        return StreamConverters$.MODULE$.DoubleStreamHasToScala(doubleStream);
    }

    public static StreamExtensions.LongStreamHasToScala LongStreamHasToScala(LongStream longStream) {
        return StreamConverters$.MODULE$.LongStreamHasToScala(longStream);
    }

    public static StreamExtensions.IntStreamHasToScala IntStreamHasToScala(IntStream intStream) {
        return StreamConverters$.MODULE$.IntStreamHasToScala(intStream);
    }

    public static StreamExtensions.StreamJDoubleHasAccumulatePrimitive StreamJDoubleHasAccumulatePrimitive(Stream<Double> stream) {
        return StreamConverters$.MODULE$.StreamJDoubleHasAccumulatePrimitive(stream);
    }

    public static StreamExtensions.StreamJLongHasAccumulatePrimitive StreamJLongHasAccumulatePrimitive(Stream<Long> stream) {
        return StreamConverters$.MODULE$.StreamJLongHasAccumulatePrimitive(stream);
    }

    public static StreamExtensions.StreamJIntegerHasAccumulatePrimitive StreamJIntegerHasAccumulatePrimitive(Stream<Integer> stream) {
        return StreamConverters$.MODULE$.StreamJIntegerHasAccumulatePrimitive(stream);
    }

    public static StreamExtensions.StreamDoubleHasAccumulatePrimitive StreamDoubleHasAccumulatePrimitive(Stream<Object> stream) {
        return StreamConverters$.MODULE$.StreamDoubleHasAccumulatePrimitive(stream);
    }

    public static StreamExtensions.StreamLongHasAccumulatePrimitive StreamLongHasAccumulatePrimitive(Stream<Object> stream) {
        return StreamConverters$.MODULE$.StreamLongHasAccumulatePrimitive(stream);
    }

    public static StreamExtensions.StreamIntHasAccumulatePrimitive StreamIntHasAccumulatePrimitive(Stream<Object> stream) {
        return StreamConverters$.MODULE$.StreamIntHasAccumulatePrimitive(stream);
    }

    public static <A> StreamExtensions.StreamHasToScala<A> StreamHasToScala(Stream<A> stream) {
        return StreamConverters$.MODULE$.StreamHasToScala(stream);
    }

    public static StreamExtensions.FloatArrayHasSeqParStream FloatArrayHasSeqParStream(float[] fArray) {
        return StreamConverters$.MODULE$.FloatArrayHasSeqParStream(fArray);
    }

    public static StreamExtensions.CharArrayHasSeqParStream CharArrayHasSeqParStream(char[] cArray) {
        return StreamConverters$.MODULE$.CharArrayHasSeqParStream(cArray);
    }

    public static StreamExtensions.ShortArrayHasSeqParStream ShortArrayHasSeqParStream(short[] sArray) {
        return StreamConverters$.MODULE$.ShortArrayHasSeqParStream(sArray);
    }

    public static StreamExtensions.ByteArrayHasSeqParStream ByteArrayHasSeqParStream(byte[] byArray) {
        return StreamConverters$.MODULE$.ByteArrayHasSeqParStream(byArray);
    }

    public static <A> StreamExtensions.AnyArrayHasSeqParStream<A> AnyArrayHasSeqParStream(A[] AArray) {
        return StreamConverters$.MODULE$.AnyArrayHasSeqParStream(AArray);
    }

    public static StreamExtensions.LongArrayHasSeqParStream LongArrayHasSeqParStream(long[] lArray) {
        return StreamConverters$.MODULE$.LongArrayHasSeqParStream(lArray);
    }

    public static StreamExtensions.IntArrayHasSeqParStream IntArrayHasSeqParStream(int[] nArray) {
        return StreamConverters$.MODULE$.IntArrayHasSeqParStream(nArray);
    }

    public static StreamExtensions.DoubleArrayHasSeqParStream DoubleArrayHasSeqParStream(double[] dArray) {
        return StreamConverters$.MODULE$.DoubleArrayHasSeqParStream(dArray);
    }

    public static <A> StreamExtensions.StepperHasParStream<A> StepperHasParStream(Stepper<A> stepper) {
        return StreamConverters$.MODULE$.StepperHasParStream(stepper);
    }

    public static <A> StreamExtensions.StepperHasSeqStream<A> StepperHasSeqStream(Stepper<A> stepper) {
        return StreamConverters$.MODULE$.StepperHasSeqStream(stepper);
    }

    public static <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasParKeyValueStream<K, V, CC> MapHasParKeyValueStream(CC CC) {
        return StreamConverters$.MODULE$.MapHasParKeyValueStream(CC);
    }

    public static <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasSeqKeyValueStream<K, V, CC> MapHasSeqKeyValueStream(CC CC) {
        return StreamConverters$.MODULE$.MapHasSeqKeyValueStream(CC);
    }

    public static <A, C extends IterableOnce<?>> StreamExtensions.IterableNonGenericHasParStream<A, C> IterableNonGenericHasParStream(C c, $less$colon$less<C, IterableOnce<A>> $less$colon$less) {
        return StreamConverters$.MODULE$.IterableNonGenericHasParStream(c, $less$colon$less);
    }

    public static <A> StreamExtensions.IterableHasSeqStream<A> IterableHasSeqStream(IterableOnce<A> iterableOnce) {
        return StreamConverters$.MODULE$.IterableHasSeqStream(iterableOnce);
    }

    public static <A> StepperExtensions<A> richStepper(Stepper<A> stepper) {
        return StreamConverters$.MODULE$.richStepper(stepper);
    }
}

