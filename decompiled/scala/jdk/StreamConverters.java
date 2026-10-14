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
import scala.jdk.StreamConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m9Qa\u0001\u0003\t\u0002%1Qa\u0003\u0003\t\u00021AQ!G\u0001\u0005\u0002i\t\u0001c\u0015;sK\u0006l7i\u001c8wKJ$XM]:\u000b\u0005\u00151\u0011a\u00016eW*\tq!A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005)\tQ\"\u0001\u0003\u0003!M#(/Z1n\u0007>tg/\u001a:uKJ\u001c8cA\u0001\u000e#A\u0011abD\u0007\u0002\r%\u0011\u0001C\u0002\u0002\u0007\u0003:L(+\u001a4\u0011\u0005I9R\"A\n\u000b\u0005Q)\u0012aB2p]Z,'\u000f\u001e\u0006\u0003-\u0019\t!bY8mY\u0016\u001cG/[8o\u0013\tA2C\u0001\tTiJ,\u0017-\\#yi\u0016t7/[8og\u00061A(\u001b8jiz\"\u0012!\u0003")
public final class StreamConverters {
    public static StreamExtensions.DoubleStreamHasToScala DoubleStreamHasToScala(DoubleStream stream) {
        return StreamConverters$.MODULE$.DoubleStreamHasToScala(stream);
    }

    public static StreamExtensions.LongStreamHasToScala LongStreamHasToScala(LongStream stream) {
        return StreamConverters$.MODULE$.LongStreamHasToScala(stream);
    }

    public static StreamExtensions.IntStreamHasToScala IntStreamHasToScala(IntStream stream) {
        return StreamConverters$.MODULE$.IntStreamHasToScala(stream);
    }

    public static <A> StreamExtensions.StreamHasToScala<A> StreamHasToScala(Stream<A> stream) {
        return StreamConverters$.MODULE$.StreamHasToScala(stream);
    }

    public static StreamExtensions.StringHasSeqParStream StringHasSeqParStream(String s2) {
        return StreamConverters$.MODULE$.StringHasSeqParStream(s2);
    }

    public static StreamExtensions.FloatArrayHasSeqParStream FloatArrayHasSeqParStream(float[] a) {
        return StreamConverters$.MODULE$.FloatArrayHasSeqParStream(a);
    }

    public static StreamExtensions.CharArrayHasSeqParStream CharArrayHasSeqParStream(char[] a) {
        return StreamConverters$.MODULE$.CharArrayHasSeqParStream(a);
    }

    public static StreamExtensions.ShortArrayHasSeqParStream ShortArrayHasSeqParStream(short[] a) {
        return StreamConverters$.MODULE$.ShortArrayHasSeqParStream(a);
    }

    public static StreamExtensions.ByteArrayHasSeqParStream ByteArrayHasSeqParStream(byte[] a) {
        return StreamConverters$.MODULE$.ByteArrayHasSeqParStream(a);
    }

    public static <A> StreamExtensions.AnyArrayHasSeqParStream<A> AnyArrayHasSeqParStream(A[] a) {
        return StreamConverters$.MODULE$.AnyArrayHasSeqParStream(a);
    }

    public static StreamExtensions.LongArrayHasSeqParStream LongArrayHasSeqParStream(long[] a) {
        return StreamConverters$.MODULE$.LongArrayHasSeqParStream(a);
    }

    public static StreamExtensions.IntArrayHasSeqParStream IntArrayHasSeqParStream(int[] a) {
        return StreamConverters$.MODULE$.IntArrayHasSeqParStream(a);
    }

    public static StreamExtensions.DoubleArrayHasSeqParStream DoubleArrayHasSeqParStream(double[] a) {
        return StreamConverters$.MODULE$.DoubleArrayHasSeqParStream(a);
    }

    public static <A> StreamExtensions.StepperHasParStream<A> StepperHasParStream(Stepper<A> stepper) {
        return StreamConverters$.MODULE$.StepperHasParStream(stepper);
    }

    public static <A> StreamExtensions.StepperHasSeqStream<A> StepperHasSeqStream(Stepper<A> stepper) {
        return StreamConverters$.MODULE$.StepperHasSeqStream(stepper);
    }

    public static <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasParKeyValueStream<K, V, CC> MapHasParKeyValueStream(CC cc) {
        return StreamConverters$.MODULE$.MapHasParKeyValueStream(cc);
    }

    public static <K, V, CC extends MapOps<Object, Object, Map, ?>> StreamExtensions.MapHasSeqKeyValueStream<K, V, CC> MapHasSeqKeyValueStream(CC cc) {
        return StreamConverters$.MODULE$.MapHasSeqKeyValueStream(cc);
    }

    public static <A, C extends IterableOnce<?>> StreamExtensions.IterableNonGenericHasParStream<A, C> IterableNonGenericHasParStream(C c, $less$colon$less<C, IterableOnce<A>> ev) {
        return StreamConverters$.MODULE$.IterableNonGenericHasParStream(c, ev);
    }

    public static <A> StreamExtensions.IterableHasSeqStream<A> IterableHasSeqStream(IterableOnce<A> cc) {
        return StreamConverters$.MODULE$.IterableHasSeqStream(cc);
    }
}

