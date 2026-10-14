/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000baBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(a\u0006*jG\"4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c82\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rA2&N\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u00035\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u0005A\u001dJC'D\u0001\"\u0015\t\u00113%\u0001\u0005gk:\u001cG/[8o\u0015\t!S%\u0001\u0003vi&d'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012\u0001BR;oGRLwN\u001c\t\u0003U-b\u0001\u0001B\u0003-\u0001\t\u0007QFA\u0001U#\tq\u0013\u0007\u0005\u0002\u001b_%\u0011\u0001\u0007\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ\"'\u0003\u00024)\t\u0019\u0011I\\=\u0011\u0005)*D!\u0002\u001c\u0001\u0005\u0004i#!\u0001*\u0002\u001dN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\fD\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u001e=!\u0011Y\u0004!\u000b\u001b\u000e\u00039AQ!P\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012\u0001\u0011\t\u00055\u0005KC'\u0003\u0002C)\tIa)\u001e8di&|g.\r\u0015\u0003\t\u0011\u0003\"AG#\n\u0005\u0019#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005I\u0005C\u0001\u000eK\u0013\tYECA\u0002J]R\fa!Z9vC2\u001cHC\u0001(R!\tQr*\u0003\u0002Q)\t9!i\\8mK\u0006t\u0007b\u0002*\u0007\u0003\u0003\u0005\r!M\u0001\u0004q\u0012\n\u0014a\u0006*jG\"4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c82!\tY\u0004b\u0005\u0002\t-B\u0011!dV\u0005\u00031R\u0011a!\u00118z%\u00164G#\u0001+\u0002#\u0005\u001c8kY1mC\u0012*\u0007\u0010^3og&|g.F\u0002]?\u0006$\"!\u00182\u0011\ti\te\f\u0019\t\u0003U}#Q\u0001\f\u0006C\u00025\u0002\"AK1\u0005\u000bYR!\u0019A\u0017\t\u000b\rT\u0001\u0019\u00013\u0002\u000b\u0011\"\b.[:\u0011\tm\u0002a\f\u0019\u0015\u0003\u0015\u0011\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u0019\u0001\u000e\u001c8\u0015\u0005!K\u0007\"B2\f\u0001\u0004Q\u0007\u0003B\u001e\u0001W6\u0004\"A\u000b7\u0005\u000b1Z!\u0019A\u0017\u0011\u0005)rG!\u0002\u001c\f\u0005\u0004i\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\r\tx/\u001f\u000b\u0003eR$\"AT:\t\u000fIc\u0011\u0011!a\u0001c!)1\r\u0004a\u0001kB!1\b\u0001<y!\tQs\u000fB\u0003-\u0019\t\u0007Q\u0006\u0005\u0002+s\u0012)a\u0007\u0004b\u0001[\u0001")
public final class RichFunctionAsFunction1<T, R> {
    private final Function<T, R> scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying;

    public static <T, R> boolean equals$extension(Function<T, R> function, Object object) {
        return RichFunctionAsFunction1$.MODULE$.equals$extension(function, object);
    }

    public static <T, R> int hashCode$extension(Function<T, R> function) {
        return RichFunctionAsFunction1$.MODULE$.hashCode$extension(function);
    }

    public static <T, R> Function1<T, R> asScala$extension(Function<T, R> function) {
        return RichFunctionAsFunction1$.MODULE$.asScala$extension(function);
    }

    public Function<T, R> scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying;
    }

    public Function1<T, R> asScala() {
        return RichFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying(), x$1);
    }

    public RichFunctionAsFunction1(Function<T, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunctionAsFunction1$$underlying = underlying;
    }
}

