/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsToIntBiFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bQBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(A\b*jG\"4UO\\2uS>t''Q:U_&sGOQ5Gk:\u001cG/[8o\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rABEL\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u0003Q\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHk\\%oi\nKg)\u001e8di&|g\u000e\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u00065\u0001\u0012S\u0006M\u0005\u0003CQ\u0011\u0011BR;oGRLwN\u001c\u001a\u0011\u0005\r\"C\u0002\u0001\u0003\u0006K\u0001\u0011\rA\n\u0002\u0002)F\u0011qE\u000b\t\u00035!J!!\u000b\u000b\u0003\u000f9{G\u000f[5oOB\u0011!dK\u0005\u0003YQ\u00111!\u00118z!\t\u0019c\u0006B\u00030\u0001\t\u0007aEA\u0001V!\tQ\u0012'\u0003\u00023)\t\u0019\u0011J\u001c;\u0002+N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N$v.\u00138u\u0005&4UO\\2uS>tG\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u001c9!\u00119\u0004AI\u0017\u000e\u00039AQ!O\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019\t7OS1wCV\tA\b\u0005\u0003>\t\njS\"\u0001 \u000b\u0005}\u0002\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u0005\u0013\u0015\u0001B;uS2T\u0011aQ\u0001\u0005U\u00064\u0018-\u0003\u0002F}\tyAk\\%oi\nKg)\u001e8di&|g\u000e\u000b\u0002\u0005\u000fB\u0011!\u0004S\u0005\u0003\u0013R\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003A\na!Z9vC2\u001cHC\u0001(R!\tQr*\u0003\u0002Q)\t9!i\\8mK\u0006t\u0007b\u0002*\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014A\b*jG\"4UO\\2uS>t''Q:U_&sGOQ5Gk:\u001cG/[8o!\t9\u0004b\u0005\u0002\t-B\u0011!dV\u0005\u00031R\u0011a!\u00118z%\u00164G#\u0001+\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tWc\u0001/`CR\u0011QL\u0019\t\u0005{\u0011s\u0006\r\u0005\u0002$?\u0012)QE\u0003b\u0001MA\u00111%\u0019\u0003\u0006_)\u0011\rA\n\u0005\u0006G*\u0001\r\u0001Z\u0001\u0006IQD\u0017n\u001d\t\u0005o\u0001q\u0006\r\u000b\u0002\u000b\u000f\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\rAGN\u001c\u000b\u0003\u0017&DQaY\u0006A\u0002)\u0004Ba\u000e\u0001l[B\u00111\u0005\u001c\u0003\u0006K-\u0011\rA\n\t\u0003G9$QaL\u0006C\u0002\u0019\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007E<\u0018\u0010\u0006\u0002siR\u0011aj\u001d\u0005\b%2\t\t\u00111\u0001+\u0011\u0015\u0019G\u00021\u0001v!\u00119\u0004A\u001e=\u0011\u0005\r:H!B\u0013\r\u0005\u00041\u0003CA\u0012z\t\u0015yCB1\u0001'\u0001")
public final class RichFunction2AsToIntBiFunction<T, U> {
    private final Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying;

    public static <T, U> boolean equals$extension(Function2<T, U, Object> function2, Object object) {
        return RichFunction2AsToIntBiFunction$.MODULE$.equals$extension(function2, object);
    }

    public static <T, U> int hashCode$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToIntBiFunction$.MODULE$.hashCode$extension(function2);
    }

    public static <T, U> ToIntBiFunction<T, U> asJava$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToIntBiFunction$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying;
    }

    public ToIntBiFunction<T, U> asJava() {
        return RichFunction2AsToIntBiFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsToIntBiFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsToIntBiFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying(), x$1);
    }

    public RichFunction2AsToIntBiFunction(Function2<T, U, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsToIntBiFunction$$underlying = underlying;
    }
}

