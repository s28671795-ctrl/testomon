/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.StringOps$;
import scala.collection.mutable.StringBuilder;
import scala.math.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;

@InternalApi
public final class FrequencySketchUtil$ {
    public static final FrequencySketchUtil$ MODULE$ = new FrequencySketchUtil$();

    public String debugString(long[][] matrix, int rowWidth, int slots, int counterWidth, long counterMax) {
        int indexDigits = FrequencySketchUtil$.digits$1(rowWidth);
        int counterDigits = package$.MODULE$.max(2, FrequencySketchUtil$.digits$1(counterMax));
        StringBuilder builder = new StringBuilder();
        builder.$plus$plus$eq(FrequencySketchUtil$.divider$1("\u2554", "\u2550", "\u2566", "\u2564", "\u2557", indexDigits, counterDigits, slots));
        builder.$plus$plus$eq(new java.lang.StringBuilder(1).append("\u2551").append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(" "), indexDigits + 2)).toString());
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), slots).foreach((Function1<Object, StringBuilder> & Serializable)slot -> FrequencySketchUtil$.$anonfun$debugString$1(builder, counterDigits, BoxesRunTime.unboxToInt(slot)));
        builder.$plus$plus$eq("\u2551\n");
        ArrayOps$.MODULE$.indices$extension(Predef$.MODULE$.refArrayOps((Object[])matrix)).foreach$mVc$sp(row -> ArrayOps$.MODULE$.indices$extension(Predef$.MODULE$.longArrayOps(matrix[0])).foreach((Function1<Object, StringBuilder> & Serializable)column -> FrequencySketchUtil$.$anonfun$debugString$3(builder, indexDigits, matrix, row, counterMax, counterDigits, counterWidth, slots, BoxesRunTime.unboxToInt(column))));
        builder.$plus$plus$eq(FrequencySketchUtil$.divider$1("\u255a", "\u2550", "\u2569", "\u2567", "\u255d", indexDigits, counterDigits, slots));
        return builder.result();
    }

    private static final int digits$1(long n) {
        return (int)package$.MODULE$.floor(package$.MODULE$.log10(n)) + 1;
    }

    private static final String divider$1(String start2, String line, String separator1, String separator, String end, int indexDigits$1, int counterDigits$1, int slots$1) {
        return new java.lang.StringBuilder(1).append(start2).append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(line), indexDigits$1 + 2)).append(separator1).append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(line), counterDigits$1 + 2)).append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(new java.lang.StringBuilder(0).append(separator).append(StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(line), counterDigits$1 + 2)).toString()), slots$1 - 1)).append(end).append("\n").toString();
    }

    public static final /* synthetic */ StringBuilder $anonfun$debugString$1(StringBuilder builder$1, int counterDigits$1, int slot) {
        builder$1.$plus$plus$eq(slot == 0 ? "\u2551" : "\u2502");
        return builder$1.$plus$plus$eq(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString(new java.lang.StringBuilder(4).append(" %").append(counterDigits$1).append("d ").toString()), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(slot)})));
    }

    public static final /* synthetic */ StringBuilder $anonfun$debugString$3(StringBuilder builder$1, int indexDigits$1, long[][] matrix$1, int row$1, long counterMax$1, int counterDigits$1, int counterWidth$1, int slots$1, int column) {
        builder$1.$plus$plus$eq(column == 0 ? FrequencySketchUtil$.divider$1("\u2560", "\u2550", "\u256c", "\u256a", "\u2563", indexDigits$1, counterDigits$1, slots$1) : FrequencySketchUtil$.divider$1("\u255f", "\u2500", "\u256b", "\u253c", "\u2562", indexDigits$1, counterDigits$1, slots$1));
        builder$1.$plus$plus$eq(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString(new java.lang.StringBuilder(5).append("\u2551 %").append(indexDigits$1).append("d ").toString()), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToInteger(column)})));
        for (int shift = 0; shift < 64; shift += counterWidth$1) {
            long count = matrix$1[row$1][column] >>> shift & counterMax$1;
            builder$1.$plus$plus$eq(shift == 0 ? "\u2551" : "\u2502");
            builder$1.$plus$plus$eq(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString(new java.lang.StringBuilder(4).append(" %").append(counterDigits$1).append("d ").toString()), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToLong(count)})));
        }
        return builder$1.$plus$plus$eq("\u2551\n");
    }

    private FrequencySketchUtil$() {
    }
}

