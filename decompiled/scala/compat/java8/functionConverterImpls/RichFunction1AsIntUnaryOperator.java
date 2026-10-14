/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsIntUnaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001di\u0004!!A\u0005By:qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b\u0015BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&a\b*jG\"4UO\\2uS>t\u0017'Q:J]R,f.\u0019:z\u001fB,'/\u0019;pe*\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001\u0001\u0007\t\u00033ii\u0011\u0001F\u0005\u00037Q\u0011a!\u00118z-\u0006d\u0017!V:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:J]R,f.\u0019:z\u001fB,'/\u0019;pe\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003y\u0001B!G\u0010\"C%\u0011\u0001\u0005\u0006\u0002\n\rVt7\r^5p]F\u0002\"!\u0007\u0012\n\u0005\r\"\"aA%oi\u000616oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]F\n5/\u00138u+:\f'/_(qKJ\fGo\u001c:%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u001dJ\u0003C\u0001\u0015\u0001\u001b\u0005q\u0001\"\u0002\u0016\u0004\u0001\u0004q\u0012AC;oI\u0016\u0014H._5oO\u00061\u0011m\u001d&bm\u0006,\u0012!\f\t\u0003]Uj\u0011a\f\u0006\u0003aE\n\u0001BZ;oGRLwN\u001c\u0006\u0003eM\nA!\u001e;jY*\tA'\u0001\u0003kCZ\f\u0017B\u0001\u001c0\u0005AIe\u000e^+oCJLx\n]3sCR|'\u000f\u000b\u0002\u0005qA\u0011\u0011$O\u0005\u0003uQ\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0005\na!Z9vC2\u001cHCA C!\tI\u0002)\u0003\u0002B)\t9!i\\8mK\u0006t\u0007bB\"\u0007\u0003\u0003\u0005\r\u0001R\u0001\u0004q\u0012\n\u0004CA\rF\u0013\t1ECA\u0002B]f\fqDU5dQ\u001a+hn\u0019;j_:\f\u0014i]%oiVs\u0017M]=Pa\u0016\u0014\u0018\r^8s!\tA\u0003b\u0005\u0002\t\u0015B\u0011\u0011dS\u0005\u0003\u0019R\u0011a!\u00118z%\u00164G#\u0001%\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tGCA\u0017Q\u0011\u0015\t&\u00021\u0001(\u0003\u0015!C\u000f[5tQ\tQ\u0001(\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tGC\u0001\u001fV\u0011\u0015\t6\u00021\u0001(\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002Y5R\u0011q(\u0017\u0005\b\u00072\t\t\u00111\u0001E\u0011\u0015\tF\u00021\u0001(\u0001")
public final class RichFunction1AsIntUnaryOperator {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsIntUnaryOperator$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsIntUnaryOperator$.MODULE$.hashCode$extension(function1);
    }

    public static IntUnaryOperator asJava$extension(Function1 function1) {
        return RichFunction1AsIntUnaryOperator$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying;
    }

    public IntUnaryOperator asJava() {
        return RichFunction1AsIntUnaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsIntUnaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsIntUnaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying(), x$1);
    }

    public RichFunction1AsIntUnaryOperator(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntUnaryOperator$$underlying = underlying;
    }
}

