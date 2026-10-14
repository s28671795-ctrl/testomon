/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichBinaryOperatorAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bUBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'!\b*jG\"\u0014\u0015N\\1ss>\u0003XM]1u_J\f5OR;oGRLwN\u001c\u001a\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005aY3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NQ5oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u00012\u0001I\u0014*\u001b\u0005\t#B\u0001\u0012$\u0003!1WO\\2uS>t'B\u0001\u0013&\u0003\u0011)H/\u001b7\u000b\u0003\u0019\nAA[1wC&\u0011\u0001&\t\u0002\u000f\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003Q\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!os\u0006!6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8s\u0003N4UO\\2uS>t'\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00028sA\u0019\u0001\bA\u0015\u000e\u00039AQAO\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012!\u0010\t\u00065yJ\u0013&K\u0005\u0003\u007fQ\u0011\u0011BR;oGRLwN\u001c\u001a)\u0005\u0011\t\u0005C\u0001\u000eC\u0013\t\u0019EC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\ta\t\u0005\u0002\u001b\u000f&\u0011\u0001\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002L\u001dB\u0011!\u0004T\u0005\u0003\u001bR\u0011qAQ8pY\u0016\fg\u000eC\u0004P\r\u0005\u0005\t\u0019A\u0019\u0002\u0007a$\u0013'A\u000fSS\u000eD')\u001b8bef|\u0005/\u001a:bi>\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c83!\tA\u0004b\u0005\u0002\t'B\u0011!\u0004V\u0005\u0003+R\u0011a!\u00118z%\u00164G#A)\u0002#\u0005\u001c8kY1mC\u0012*\u0007\u0010^3og&|g.\u0006\u0002Z9R\u0011!,\u0018\t\u00065yZ6l\u0017\t\u0003Uq#Q\u0001\f\u0006C\u00025BQA\u0018\u0006A\u0002}\u000bQ\u0001\n;iSN\u00042\u0001\u000f\u0001\\Q\tQ\u0011)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWCA2h)\t)E\rC\u0003_\u0017\u0001\u0007Q\rE\u00029\u0001\u0019\u0004\"AK4\u0005\u000b1Z!\u0019A\u0017\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWC\u00016q)\tYW\u000e\u0006\u0002LY\"9q\nDA\u0001\u0002\u0004\t\u0004\"\u00020\r\u0001\u0004q\u0007c\u0001\u001d\u0001_B\u0011!\u0006\u001d\u0003\u0006Y1\u0011\r!\f")
public final class RichBinaryOperatorAsFunction2<T> {
    private final BinaryOperator<T> scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying;

    public static <T> boolean equals$extension(BinaryOperator<T> binaryOperator, Object object) {
        return RichBinaryOperatorAsFunction2$.MODULE$.equals$extension(binaryOperator, object);
    }

    public static <T> int hashCode$extension(BinaryOperator<T> binaryOperator) {
        return RichBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(binaryOperator);
    }

    public static <T> Function2<T, T, T> asScala$extension(BinaryOperator<T> binaryOperator) {
        return RichBinaryOperatorAsFunction2$.MODULE$.asScala$extension(binaryOperator);
    }

    public BinaryOperator<T> scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying;
    }

    public Function2<T, T, T> asScala() {
        return RichBinaryOperatorAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichBinaryOperatorAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying(), x$1);
    }

    public RichBinaryOperatorAsFunction2(BinaryOperator<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichBinaryOperatorAsFunction2$$underlying = underlying;
    }
}

