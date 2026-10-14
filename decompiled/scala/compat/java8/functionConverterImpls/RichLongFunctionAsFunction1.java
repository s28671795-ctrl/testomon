/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichLongFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bUBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\u0007*jG\"duN\\4Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017G\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0019WM\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017!U:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"duN\\4Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u0004A\u001dJS\"A\u0011\u000b\u0005\t\u001a\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u0011*\u0013\u0001B;uS2T\u0011AJ\u0001\u0005U\u00064\u0018-\u0003\u0002)C\taAj\u001c8h\rVt7\r^5p]B\u0011!f\u000b\u0007\u0001\t\u0015a\u0003A1\u0001.\u0005\u0005\u0011\u0016C\u0001\u00182!\tQr&\u0003\u00021)\t9aj\u001c;iS:<\u0007C\u0001\u000e3\u0013\t\u0019DCA\u0002B]f\f!k]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ2{gn\u001a$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\fD\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u001c:!\rA\u0004!K\u0007\u0002\u001d!)!h\u0001a\u0001?\u0005QQO\u001c3fe2L\u0018N\\4\u0002\u000f\u0005\u001c8kY1mCV\tQ\b\u0005\u0003\u001b}\u0001K\u0013BA \u0015\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\u001b\u0003&\u0011!\t\u0006\u0002\u0005\u0019>tw\r\u000b\u0002\u0005\tB\u0011!$R\u0005\u0003\rR\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003%\u0003\"A\u0007&\n\u0005-#\"aA%oi\u00061Q-];bYN$\"AT)\u0011\u0005iy\u0015B\u0001)\u0015\u0005\u001d\u0011un\u001c7fC:DqA\u0015\u0004\u0002\u0002\u0003\u0007\u0011'A\u0002yIE\n1DU5dQ2{gn\u001a$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\f\u0004C\u0001\u001d\t'\tAa\u000b\u0005\u0002\u001b/&\u0011\u0001\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003Q\u000b\u0011#Y:TG\u0006d\u0017\rJ3yi\u0016t7/[8o+\tav\f\u0006\u0002^AB!!D\u0010!_!\tQs\fB\u0003-\u0015\t\u0007Q\u0006C\u0003b\u0015\u0001\u0007!-A\u0003%i\"L7\u000fE\u00029\u0001yC#A\u0003#\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0003M*$\"\u0001S4\t\u000b\u0005\\\u0001\u0019\u00015\u0011\u0007a\u0002\u0011\u000e\u0005\u0002+U\u0012)Af\u0003b\u0001[\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0003[N$\"A\u001c9\u0015\u00059{\u0007b\u0002*\r\u0003\u0003\u0005\r!\r\u0005\u0006C2\u0001\r!\u001d\t\u0004q\u0001\u0011\bC\u0001\u0016t\t\u0015aCB1\u0001.\u0001")
public final class RichLongFunctionAsFunction1<R> {
    private final LongFunction<R> scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying;

    public static <R> boolean equals$extension(LongFunction<R> longFunction, Object object) {
        return RichLongFunctionAsFunction1$.MODULE$.equals$extension(longFunction, object);
    }

    public static <R> int hashCode$extension(LongFunction<R> longFunction) {
        return RichLongFunctionAsFunction1$.MODULE$.hashCode$extension(longFunction);
    }

    public static <R> Function1<Object, R> asScala$extension(LongFunction<R> longFunction) {
        return RichLongFunctionAsFunction1$.MODULE$.asScala$extension(longFunction);
    }

    public LongFunction<R> scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying;
    }

    public Function1<Object, R> asScala() {
        return RichLongFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichLongFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying(), x$1);
    }

    public RichLongFunctionAsFunction1(LongFunction<R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongFunctionAsFunction1$$underlying = underlying;
    }
}

