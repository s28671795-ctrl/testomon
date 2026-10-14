/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichUnaryOperatorAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bUBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'\u0001\b*jG\",f.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|g.\r\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\u000b\u00031-\u001a\"\u0001A\r\u0011\u0005iYR\"\u0001\u000b\n\u0005q!\"AB!osZ\u000bG.\u0001*tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDWK\\1ss>\u0003XM]1u_J\f5OR;oGRLwN\\\u0019%IUtG-\u001a:ms&tw-F\u0001 !\r\u0001s%K\u0007\u0002C)\u0011!eI\u0001\tMVt7\r^5p]*\u0011A%J\u0001\u0005kRLGNC\u0001'\u0003\u0011Q\u0017M^1\n\u0005!\n#!D+oCJLx\n]3sCR|'\u000f\u0005\u0002+W1\u0001A!\u0002\u0017\u0001\u0005\u0004i#!\u0001+\u0012\u00059\n\u0004C\u0001\u000e0\u0013\t\u0001DCA\u0004O_RD\u0017N\\4\u0011\u0005i\u0011\u0014BA\u001a\u0015\u0005\r\te._\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007.\u00168bef|\u0005/\u001a:bi>\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t9\u0014\bE\u00029\u0001%j\u0011A\u0004\u0005\u0006u\r\u0001\raH\u0001\u000bk:$WM\u001d7zS:<\u0017aB1t'\u000e\fG.Y\u000b\u0002{A!!DP\u0015*\u0013\tyDCA\u0005Gk:\u001cG/[8oc!\u0012A!\u0011\t\u00035\tK!a\u0011\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#\u0001$\u0011\u0005i9\u0015B\u0001%\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0005-s\u0005C\u0001\u000eM\u0013\tiECA\u0004C_>dW-\u00198\t\u000f=3\u0011\u0011!a\u0001c\u0005\u0019\u0001\u0010J\u0019\u00029IK7\r[+oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8ocA\u0011\u0001\bC\n\u0003\u0011M\u0003\"A\u0007+\n\u0005U#\"AB!osJ+g\rF\u0001R\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\\\u000b\u00033r#\"AW/\u0011\tiq4l\u0017\t\u0003Uq#Q\u0001\f\u0006C\u00025BQA\u0018\u0006A\u0002}\u000bQ\u0001\n;iSN\u00042\u0001\u000f\u0001\\Q\tQ\u0011)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWCA2h)\t)E\rC\u0003_\u0017\u0001\u0007Q\rE\u00029\u0001\u0019\u0004\"AK4\u0005\u000b1Z!\u0019A\u0017\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWC\u00016q)\tYW\u000e\u0006\u0002LY\"9q\nDA\u0001\u0002\u0004\t\u0004\"\u00020\r\u0001\u0004q\u0007c\u0001\u001d\u0001_B\u0011!\u0006\u001d\u0003\u0006Y1\u0011\r!\f")
public final class RichUnaryOperatorAsFunction1<T> {
    private final UnaryOperator<T> scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying;

    public static <T> boolean equals$extension(UnaryOperator<T> unaryOperator, Object object) {
        return RichUnaryOperatorAsFunction1$.MODULE$.equals$extension(unaryOperator, object);
    }

    public static <T> int hashCode$extension(UnaryOperator<T> unaryOperator) {
        return RichUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(unaryOperator);
    }

    public static <T> Function1<T, T> asScala$extension(UnaryOperator<T> unaryOperator) {
        return RichUnaryOperatorAsFunction1$.MODULE$.asScala$extension(unaryOperator);
    }

    public UnaryOperator<T> scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying;
    }

    public Function1<T, T> asScala() {
        return RichUnaryOperatorAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichUnaryOperatorAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying(), x$1);
    }

    public RichUnaryOperatorAsFunction1(UnaryOperator<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichUnaryOperatorAsFunction1$$underlying = underlying;
    }
}

