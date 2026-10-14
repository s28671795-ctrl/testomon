/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichLongBinaryOperatorAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&!\t*jG\"duN\\4CS:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\u0014$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Xg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007\u000eT8oO\nKg.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|gN\r\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\u0005}1S\"\u0001\u0011\u000b\u0005\u0005\u0012\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\r\"\u0013\u0001B;uS2T\u0011!J\u0001\u0005U\u00064\u0018-\u0003\u0002(A\t\u0011Bj\u001c8h\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003a\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195M_:<')\u001b8bef|\u0005/\u001a:bi>\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c83I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\tYS\u0006\u0005\u0002-\u00015\ta\u0002C\u0003/\u0007\u0001\u0007a$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fq!Y:TG\u0006d\u0017-F\u00012!\u0015I\"\u0007\u000e\u001b5\u0013\t\u0019DCA\u0005Gk:\u001cG/[8oeA\u0011\u0011$N\u0005\u0003mQ\u0011A\u0001T8oO\"\u0012A\u0001\u000f\t\u00033eJ!A\u000f\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#A\u001f\u0011\u0005eq\u0014BA \u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0005\t+\u0005CA\rD\u0013\t!ECA\u0004C_>dW-\u00198\t\u000f\u00193\u0011\u0011!a\u0001\u000f\u0006\u0019\u0001\u0010J\u0019\u0011\u0005eA\u0015BA%\u0015\u0005\r\te._\u0001\"%&\u001c\u0007\u000eT8oO\nKg.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|gN\r\t\u0003Y!\u0019\"\u0001C'\u0011\u0005eq\u0015BA(\u0015\u0005\u0019\te.\u001f*fMR\t1*A\tbgN\u001b\u0017\r\\1%Kb$XM\\:j_:$\"!M*\t\u000bQS\u0001\u0019A\u0016\u0002\u000b\u0011\"\b.[:)\u0005)A\u0014A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"\u0001\u0010-\t\u000bQ[\u0001\u0019A\u0016\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGCA.^)\t\u0011E\fC\u0004G\u0019\u0005\u0005\t\u0019A$\t\u000bQc\u0001\u0019A\u0016")
public final class RichLongBinaryOperatorAsFunction2 {
    private final LongBinaryOperator scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying;

    public static boolean equals$extension(LongBinaryOperator longBinaryOperator, Object object) {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.equals$extension(longBinaryOperator, object);
    }

    public static int hashCode$extension(LongBinaryOperator longBinaryOperator) {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(longBinaryOperator);
    }

    public static Function2<Object, Object, Object> asScala$extension(LongBinaryOperator longBinaryOperator) {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.asScala$extension(longBinaryOperator);
    }

    public LongBinaryOperator scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying;
    }

    public Function2<Object, Object, Object> asScala() {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongBinaryOperatorAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying(), x$1);
    }

    public RichLongBinaryOperatorAsFunction2(LongBinaryOperator underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongBinaryOperatorAsFunction2$$underlying = underlying;
    }
}

