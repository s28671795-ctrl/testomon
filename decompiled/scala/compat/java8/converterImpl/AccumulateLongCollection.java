/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.collection.IterableOnce;
import scala.compat.java8.converterImpl.AccumulateLongCollection$;
import scala.jdk.LongAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IQ\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000b9BA\u0011A-\t\u000biCAQA.\t\u000fyC\u0011\u0011!C\u0003?\"9\u0011\rCA\u0001\n\u000b\u0011'\u0001G!dGVlW\u000f\\1uK2{gnZ\"pY2,7\r^5p]*\u0011q\u0002E\u0001\u000eG>tg/\u001a:uKJLU\u000e\u001d7\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001A\u0002CA\r\u001b\u001b\u0005!\u0012BA\u000e\u0015\u0005\u0019\te.\u001f,bY\u0006)5oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\r|gN^3si\u0016\u0014\u0018*\u001c9mI\u0005\u001b7-^7vY\u0006$X\rT8oO\u000e{G\u000e\\3di&|g\u000e\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012A\b\t\u0004?\u001dRcB\u0001\u0011&\u001d\t\tC%D\u0001#\u0015\t\u0019c#\u0001\u0004=e>|GOP\u0005\u0002+%\u0011a\u0005F\u0001\ba\u0006\u001c7.Y4f\u0013\tA\u0013F\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cWM\u0003\u0002')A\u0011\u0011dK\u0005\u0003YQ\u0011A\u0001T8oO\u000615oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\r|gN^3si\u0016\u0014\u0018*\u001c9mI\u0005\u001b7-^7vY\u0006$X\rT8oO\u000e{G\u000e\\3di&|g\u000e\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00021eA\u0011\u0011\u0007A\u0007\u0002\u001d!)1g\u0001a\u0001=\u0005QQO\u001c3fe2L\u0018N\\4\u0002\u0015\u0005\u001c7-^7vY\u0006$X-F\u00017!\t9\u0014I\u0004\u00029\u007f9\u0011\u0011(\u0010\b\u0003uqr!\u0001I\u001e\n\u0005M!\u0012BA\t\u0013\u0013\tq\u0004#\u0001\bd_2dWm\u0019;j_:LU\u000e\u001d7\n\u0005\u0019\u0002%B\u0001 \u0011\u0013\t\u00115IA\bM_:<\u0017iY2v[Vd\u0017\r^8s\u0015\t1\u0003)\u0001\u0005iCND7i\u001c3f)\u00051\u0005CA\rH\u0013\tAECA\u0002J]R\fa!Z9vC2\u001cHCA&O!\tIB*\u0003\u0002N)\t9!i\\8mK\u0006t\u0007bB(\u0007\u0003\u0003\u0005\r\u0001U\u0001\u0004q\u0012\n\u0004CA\rR\u0013\t\u0011FCA\u0002B]f\f\u0001$Q2dk6,H.\u0019;f\u0019>twmQ8mY\u0016\u001cG/[8o!\t\t\u0004b\u0005\u0002\t-B\u0011\u0011dV\u0005\u00031R\u0011a!\u00118z%\u00164G#\u0001+\u0002)\u0005\u001c7-^7vY\u0006$X\rJ3yi\u0016t7/[8o)\t1D\fC\u0003^\u0015\u0001\u0007\u0001'A\u0003%i\"L7/\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tGCA#a\u0011\u0015i6\u00021\u00011\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002dKR\u00111\n\u001a\u0005\b\u001f2\t\t\u00111\u0001Q\u0011\u0015iF\u00021\u00011\u0001")
public final class AccumulateLongCollection {
    private final IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying;

    public static boolean equals$extension(IterableOnce iterableOnce, Object object) {
        return AccumulateLongCollection$.MODULE$.equals$extension(iterableOnce, object);
    }

    public static int hashCode$extension(IterableOnce iterableOnce) {
        return AccumulateLongCollection$.MODULE$.hashCode$extension(iterableOnce);
    }

    public static LongAccumulator accumulate$extension(IterableOnce iterableOnce) {
        return AccumulateLongCollection$.MODULE$.accumulate$extension(iterableOnce);
    }

    public IterableOnce<Object> scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying;
    }

    public LongAccumulator accumulate() {
        return AccumulateLongCollection$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying());
    }

    public int hashCode() {
        return AccumulateLongCollection$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateLongCollection$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying(), x$1);
    }

    public AccumulateLongCollection(IterableOnce<Object> underlying) {
        this.scala$compat$java8$converterImpl$AccumulateLongCollection$$underlying = underlying;
    }
}

