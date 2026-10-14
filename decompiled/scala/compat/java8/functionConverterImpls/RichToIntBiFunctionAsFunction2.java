/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichToIntBiFunctionAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000baBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(A\b*jG\"$v.\u00138u\u0005&4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c83\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rA2&N\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u0003Q\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195U_&sGOQ5Gk:\u001cG/[8o\u0003N4UO\\2uS>t'\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u0005A\u001dJC'D\u0001\"\u0015\t\u00113%\u0001\u0005gk:\u001cG/[8o\u0015\t!S%\u0001\u0003vi&d'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012q\u0002V8J]R\u0014\u0015NR;oGRLwN\u001c\t\u0003U-b\u0001\u0001B\u0003-\u0001\t\u0007QFA\u0001U#\tq\u0013\u0007\u0005\u0002\u001b_%\u0011\u0001\u0007\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ\"'\u0003\u00024)\t\u0019\u0011I\\=\u0011\u0005)*D!\u0002\u001c\u0001\u0005\u0004i#!A+\u0002+N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b+p\u0013:$()\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\u0014D\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u001e=!\u0011Y\u0004!\u000b\u001b\u000e\u00039AQ!P\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012\u0001\u0011\t\u00065\u0005KCgQ\u0005\u0003\u0005R\u0011\u0011BR;oGRLwN\u001c\u001a\u0011\u0005i!\u0015BA#\u0015\u0005\rIe\u000e\u001e\u0015\u0003\t\u001d\u0003\"A\u0007%\n\u0005%#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005\u0019\u0015AB3rk\u0006d7\u000f\u0006\u0002O#B\u0011!dT\u0005\u0003!R\u0011qAQ8pY\u0016\fg\u000eC\u0004S\r\u0005\u0005\t\u0019A\u0019\u0002\u0007a$\u0013'\u0001\u0010SS\u000eDGk\\%oi\nKg)\u001e8di&|g.Q:Gk:\u001cG/[8oeA\u00111\bC\n\u0003\u0011Y\u0003\"AG,\n\u0005a#\"AB!osJ+g\rF\u0001U\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\\\u000b\u00049~\u000bGCA/c!\u0015Q\u0012I\u00181D!\tQs\fB\u0003-\u0015\t\u0007Q\u0006\u0005\u0002+C\u0012)aG\u0003b\u0001[!)1M\u0003a\u0001I\u0006)A\u0005\u001e5jgB!1\b\u00010aQ\tQq)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWc\u00015m]R\u00111*\u001b\u0005\u0006G.\u0001\rA\u001b\t\u0005w\u0001YW\u000e\u0005\u0002+Y\u0012)Af\u0003b\u0001[A\u0011!F\u001c\u0003\u0006m-\u0011\r!L\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,2!]<z)\t\u0011H\u000f\u0006\u0002Og\"9!\u000bDA\u0001\u0002\u0004\t\u0004\"B2\r\u0001\u0004)\b\u0003B\u001e\u0001mb\u0004\"AK<\u0005\u000b1b!\u0019A\u0017\u0011\u0005)JH!\u0002\u001c\r\u0005\u0004i\u0003")
public final class RichToIntBiFunctionAsFunction2<T, U> {
    private final ToIntBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying;

    public static <T, U> boolean equals$extension(ToIntBiFunction<T, U> toIntBiFunction, Object object) {
        return RichToIntBiFunctionAsFunction2$.MODULE$.equals$extension(toIntBiFunction, object);
    }

    public static <T, U> int hashCode$extension(ToIntBiFunction<T, U> toIntBiFunction) {
        return RichToIntBiFunctionAsFunction2$.MODULE$.hashCode$extension(toIntBiFunction);
    }

    public static <T, U> Function2<T, U, Object> asScala$extension(ToIntBiFunction<T, U> toIntBiFunction) {
        return RichToIntBiFunctionAsFunction2$.MODULE$.asScala$extension(toIntBiFunction);
    }

    public ToIntBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying;
    }

    public Function2<T, U, Object> asScala() {
        return RichToIntBiFunctionAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichToIntBiFunctionAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichToIntBiFunctionAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying(), x$1);
    }

    public RichToIntBiFunctionAsFunction2(ToIntBiFunction<T, U> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichToIntBiFunctionAsFunction2$$underlying = underlying;
    }
}

