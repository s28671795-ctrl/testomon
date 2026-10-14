/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichLongUnaryOperatorAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&\u0001\t*jG\"duN\\4V]\u0006\u0014\u0018p\u00149fe\u0006$xN]!t\rVt7\r^5p]FR!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001\u0001\r\u0011\u0005eQR\"\u0001\u000b\n\u0005m!\"AB!osZ\u000bG.\u0001,tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDGj\u001c8h+:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\fD\u0005J;oI\u0016\u0014H._5oOV\ta\u0004\u0005\u0002 M5\t\u0001E\u0003\u0002\"E\u0005Aa-\u001e8di&|gN\u0003\u0002$I\u0005!Q\u000f^5m\u0015\u0005)\u0013\u0001\u00026bm\u0006L!a\n\u0011\u0003#1{gnZ+oCJLx\n]3sCR|'/A,tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDGj\u001c8h+:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\fD\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u0016.!\ta\u0003!D\u0001\u000f\u0011\u0015q3\u00011\u0001\u001f\u0003))h\u000eZ3sYfLgnZ\u0001\bCN\u001c6-\u00197b+\u0005\t\u0004\u0003B\r3iQJ!a\r\u000b\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\r6\u0013\t1DC\u0001\u0003M_:<\u0007F\u0001\u00039!\tI\u0012(\u0003\u0002;)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002{A\u0011\u0011DP\u0005\u0003\u007fQ\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!)\u0012\t\u00033\rK!\u0001\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\"9aIBA\u0001\u0002\u00049\u0015a\u0001=%cA\u0011\u0011\u0004S\u0005\u0003\u0013R\u00111!\u00118z\u0003\u0001\u0012\u0016n\u00195M_:<WK\\1ss>\u0003XM]1u_J\f5OR;oGRLwN\\\u0019\u0011\u00051B1C\u0001\u0005N!\tIb*\u0003\u0002P)\t1\u0011I\\=SK\u001a$\u0012aS\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tGCA\u0019T\u0011\u0015!&\u00021\u0001,\u0003\u0015!C\u000f[5tQ\tQ\u0001(\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tGC\u0001\u001fY\u0011\u0015!6\u00021\u0001,\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002\\;R\u0011!\t\u0018\u0005\b\r2\t\t\u00111\u0001H\u0011\u0015!F\u00021\u0001,\u0001")
public final class RichLongUnaryOperatorAsFunction1 {
    private final LongUnaryOperator scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying;

    public static boolean equals$extension(LongUnaryOperator longUnaryOperator, Object object) {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.equals$extension(longUnaryOperator, object);
    }

    public static int hashCode$extension(LongUnaryOperator longUnaryOperator) {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(longUnaryOperator);
    }

    public static Function1<Object, Object> asScala$extension(LongUnaryOperator longUnaryOperator) {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.asScala$extension(longUnaryOperator);
    }

    public LongUnaryOperator scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying;
    }

    public Function1<Object, Object> asScala() {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongUnaryOperatorAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying(), x$1);
    }

    public RichLongUnaryOperatorAsFunction1(LongUnaryOperator underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongUnaryOperatorAsFunction1$$underlying = underlying;
    }
}

