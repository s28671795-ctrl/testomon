/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsIntToDoubleFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b!BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\t*jG\"4UO\\2uS>t\u0017'Q:J]R$v\u000eR8vE2,g)\u001e8di&|gN\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u00021N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NLe\u000e\u001e+p\t>,(\r\\3Gk:\u001cG/[8oI\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A!\u0011dH\u0011%\u0013\t\u0001CCA\u0005Gk:\u001cG/[8ocA\u0011\u0011DI\u0005\u0003GQ\u00111!\u00138u!\tIR%\u0003\u0002')\t1Ai\\;cY\u0016\f\u0011l]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\f\u0014i]%oiR{Gi\\;cY\u00164UO\\2uS>tG\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u0016-!\tY\u0003!D\u0001\u000f\u0011\u0015i3\u00011\u0001\u001f\u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u0003A\u0002\"!\r\u001d\u000e\u0003IR!a\r\u001b\u0002\u0011\u0019,hn\u0019;j_:T!!\u000e\u001c\u0002\tU$\u0018\u000e\u001c\u0006\u0002o\u0005!!.\u0019<b\u0013\tI$GA\nJ]R$v\u000eR8vE2,g)\u001e8di&|g\u000e\u000b\u0002\u0005wA\u0011\u0011\u0004P\u0005\u0003{Q\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0005\na!Z9vC2\u001cHC\u0001\"F!\tI2)\u0003\u0002E)\t9!i\\8mK\u0006t\u0007b\u0002$\u0007\u0003\u0003\u0005\raR\u0001\u0004q\u0012\n\u0004CA\rI\u0013\tIECA\u0002B]f\f!EU5dQ\u001a+hn\u0019;j_:\f\u0014i]%oiR{Gi\\;cY\u00164UO\\2uS>t\u0007CA\u0016\t'\tAQ\n\u0005\u0002\u001a\u001d&\u0011q\n\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003-\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005A\u001a\u0006\"\u0002+\u000b\u0001\u0004Q\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u0006<\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005}B\u0006\"\u0002+\f\u0001\u0004Q\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tYV\f\u0006\u0002C9\"9a\tDA\u0001\u0002\u00049\u0005\"\u0002+\r\u0001\u0004Q\u0003")
public final class RichFunction1AsIntToDoubleFunction {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.hashCode$extension(function1);
    }

    public static IntToDoubleFunction asJava$extension(Function1 function1) {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying;
    }

    public IntToDoubleFunction asJava() {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsIntToDoubleFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying(), x$1);
    }

    public RichFunction1AsIntToDoubleFunction(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntToDoubleFunction$$underlying = underlying;
    }
}

