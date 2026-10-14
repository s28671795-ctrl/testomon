/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsLongBinaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b\u0015BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&!\t*jG\"4UO\\2uS>t''Q:M_:<')\u001b8bef|\u0005/\u001a:bi>\u0014(BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Xg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\u001c\u001aBg2{gn\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\u000bey\u0012%I\u0011\n\u0005\u0001\"\"!\u0003$v]\u000e$\u0018n\u001c83!\tI\"%\u0003\u0002$)\t!Aj\u001c8h\u0003a\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHj\u001c8h\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8sI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t9\u0013\u0006\u0005\u0002)\u00015\ta\u0002C\u0003+\u0007\u0001\u0007a$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fa!Y:KCZ\fW#A\u0017\u0011\u00059*T\"A\u0018\u000b\u0005A\n\u0014\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005I\u001a\u0014\u0001B;uS2T\u0011\u0001N\u0001\u0005U\u00064\u0018-\u0003\u00027_\t\u0011Bj\u001c8h\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8sQ\t!\u0001\b\u0005\u0002\u001as%\u0011!\b\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!\u0010\t\u00033yJ!a\u0010\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0005\u0016\u0003\"!G\"\n\u0005\u0011#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\r\u001a\t\t\u00111\u0001H\u0003\rAH%\r\t\u00033!K!!\u0013\u000b\u0003\u0007\u0005s\u00170A\u0011SS\u000eDg)\u001e8di&|gNM!t\u0019>twMQ5oCJLx\n]3sCR|'\u000f\u0005\u0002)\u0011M\u0011\u0001\"\u0014\t\u000339K!a\u0014\u000b\u0003\r\u0005s\u0017PU3g)\u0005Y\u0015\u0001E1t\u0015\u00064\u0018\rJ3yi\u0016t7/[8o)\ti3\u000bC\u0003U\u0015\u0001\u0007q%A\u0003%i\"L7\u000f\u000b\u0002\u000bq\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\ta\u0004\fC\u0003U\u0017\u0001\u0007q%\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R\u00111,\u0018\u000b\u0003\u0005rCqA\u0012\u0007\u0002\u0002\u0003\u0007q\tC\u0003U\u0019\u0001\u0007q\u0005")
public final class RichFunction2AsLongBinaryOperator {
    private final Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying;

    public static boolean equals$extension(Function2 function2, Object object) {
        return RichFunction2AsLongBinaryOperator$.MODULE$.equals$extension(function2, object);
    }

    public static int hashCode$extension(Function2 function2) {
        return RichFunction2AsLongBinaryOperator$.MODULE$.hashCode$extension(function2);
    }

    public static LongBinaryOperator asJava$extension(Function2 function2) {
        return RichFunction2AsLongBinaryOperator$.MODULE$.asJava$extension(function2);
    }

    public Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying;
    }

    public LongBinaryOperator asJava() {
        return RichFunction2AsLongBinaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsLongBinaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsLongBinaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying(), x$1);
    }

    public RichFunction2AsLongBinaryOperator(Function2<Object, Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsLongBinaryOperator$$underlying = underlying;
    }
}

