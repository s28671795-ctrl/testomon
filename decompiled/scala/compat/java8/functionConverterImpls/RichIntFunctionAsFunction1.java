/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichIntFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bUBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'A\u0007*jG\"Le\u000e\u001e$v]\u000e$\u0018n\u001c8Bg\u001a+hn\u0019;j_:\f$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001QC\u0001\r,'\t\u0001\u0011\u0004\u0005\u0002\u001b75\tA#\u0003\u0002\u001d)\t1\u0011I\\=WC2\f\u0001k]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ&sGOR;oGRLwN\\!t\rVt7\r^5p]F\"C%\u001e8eKJd\u00170\u001b8h+\u0005y\u0002c\u0001\u0011(S5\t\u0011E\u0003\u0002#G\u0005Aa-\u001e8di&|gN\u0003\u0002%K\u0005!Q\u000f^5m\u0015\u00051\u0013\u0001\u00026bm\u0006L!\u0001K\u0011\u0003\u0017%sGOR;oGRLwN\u001c\t\u0003U-b\u0001\u0001B\u0003-\u0001\t\u0007QFA\u0001S#\tq\u0013\u0007\u0005\u0002\u001b_%\u0011\u0001\u0007\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ\"'\u0003\u00024)\t\u0019\u0011I\\=\u0002#N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r[%oi\u001a+hn\u0019;j_:\f5OR;oGRLwN\\\u0019%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005]J\u0004c\u0001\u001d\u0001S5\ta\u0002C\u0003;\u0007\u0001\u0007q$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fq!Y:TG\u0006d\u0017-F\u0001>!\u0011Qb\bQ\u0015\n\u0005}\"\"!\u0003$v]\u000e$\u0018n\u001c82!\tQ\u0012)\u0003\u0002C)\t\u0019\u0011J\u001c;)\u0005\u0011!\u0005C\u0001\u000eF\u0013\t1EC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001)\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0017:\u0003\"A\u0007'\n\u00055#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u001f\u001a\t\t\u00111\u00012\u0003\rAH%M\u0001\u001b%&\u001c\u0007.\u00138u\rVt7\r^5p]\u0006\u001bh)\u001e8di&|g.\r\t\u0003q!\u0019\"\u0001C*\u0011\u0005i!\u0016BA+\u0015\u0005\u0019\te.\u001f*fMR\t\u0011+A\tbgN\u001b\u0017\r\\1%Kb$XM\\:j_:,\"!\u0017/\u0015\u0005ik\u0006\u0003\u0002\u000e?\u0001n\u0003\"A\u000b/\u0005\u000b1R!\u0019A\u0017\t\u000byS\u0001\u0019A0\u0002\u000b\u0011\"\b.[:\u0011\u0007a\u00021\f\u000b\u0002\u000b\t\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\t\u0019w\r\u0006\u0002II\")al\u0003a\u0001KB\u0019\u0001\b\u00014\u0011\u0005):G!\u0002\u0017\f\u0005\u0004i\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\tQ\u0007\u000f\u0006\u0002l[R\u00111\n\u001c\u0005\b\u001f2\t\t\u00111\u00012\u0011\u0015qF\u00021\u0001o!\rA\u0004a\u001c\t\u0003UA$Q\u0001\f\u0007C\u00025\u0002")
public final class RichIntFunctionAsFunction1<R> {
    private final IntFunction<R> scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying;

    public static <R> boolean equals$extension(IntFunction<R> intFunction, Object object) {
        return RichIntFunctionAsFunction1$.MODULE$.equals$extension(intFunction, object);
    }

    public static <R> int hashCode$extension(IntFunction<R> intFunction) {
        return RichIntFunctionAsFunction1$.MODULE$.hashCode$extension(intFunction);
    }

    public static <R> Function1<Object, R> asScala$extension(IntFunction<R> intFunction) {
        return RichIntFunctionAsFunction1$.MODULE$.asScala$extension(intFunction);
    }

    public IntFunction<R> scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying;
    }

    public Function1<Object, R> asScala() {
        return RichIntFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichIntFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichIntFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying(), x$1);
    }

    public RichIntFunctionAsFunction1(IntFunction<R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichIntFunctionAsFunction1$$underlying = underlying;
    }
}

