/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBinaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IQ\u0006\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b9BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'!\b*jG\"4UO\\2uS>t''Q:CS:\f'/_(qKJ\fGo\u001c:\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005a!3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\u001c\u001aBg\nKg.\u0019:z\u001fB,'/\u0019;pe\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001RA\u0007\u0011#E\tJ!!\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u0012%\u0019\u0001!Q!\n\u0001C\u0002\u0019\u0012\u0011\u0001V\t\u0003O)\u0002\"A\u0007\u0015\n\u0005%\"\"a\u0002(pi\"Lgn\u001a\t\u00035-J!\u0001\f\u000b\u0003\u0007\u0005s\u00170\u0001+tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|gNM!t\u0005&t\u0017M]=Pa\u0016\u0014\u0018\r^8sI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0001$\u0007E\u00022\u0001\tj\u0011A\u0004\u0005\u0006g\r\u0001\raH\u0001\u000bk:$WM\u001d7zS:<\u0017AB1t\u0015\u00064\u0018-F\u00017!\r9dHI\u0007\u0002q)\u0011\u0011HO\u0001\tMVt7\r^5p]*\u00111\bP\u0001\u0005kRLGNC\u0001>\u0003\u0011Q\u0017M^1\n\u0005}B$A\u0004\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0015\u0003\t\u0005\u0003\"A\u0007\"\n\u0005\r#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u00051\u0005C\u0001\u000eH\u0013\tAECA\u0002J]R\fa!Z9vC2\u001cHCA&O!\tQB*\u0003\u0002N)\t9!i\\8mK\u0006t\u0007bB(\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014!\b*jG\"4UO\\2uS>t''Q:CS:\f'/_(qKJ\fGo\u001c:\u0011\u0005EB1C\u0001\u0005T!\tQB+\u0003\u0002V)\t1\u0011I\\=SK\u001a$\u0012!U\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:,\"!\u0017/\u0015\u0005ik\u0006cA\u001c?7B\u00111\u0005\u0018\u0003\u0006K)\u0011\rA\n\u0005\u0006=*\u0001\raX\u0001\u0006IQD\u0017n\u001d\t\u0004c\u0001Y\u0006F\u0001\u0006B\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005\r<GCA#e\u0011\u0015q6\u00021\u0001f!\r\t\u0004A\u001a\t\u0003G\u001d$Q!J\u0006C\u0002\u0019\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005)\u0004HCA6n)\tYE\u000eC\u0004P\u0019\u0005\u0005\t\u0019\u0001\u0016\t\u000byc\u0001\u0019\u00018\u0011\u0007E\u0002q\u000e\u0005\u0002$a\u0012)Q\u0005\u0004b\u0001M\u0001")
public final class RichFunction2AsBinaryOperator<T> {
    private final Function2<T, T, T> scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying;

    public static <T> boolean equals$extension(Function2<T, T, T> function2, Object object) {
        return RichFunction2AsBinaryOperator$.MODULE$.equals$extension(function2, object);
    }

    public static <T> int hashCode$extension(Function2<T, T, T> function2) {
        return RichFunction2AsBinaryOperator$.MODULE$.hashCode$extension(function2);
    }

    public static <T> BinaryOperator<T> asJava$extension(Function2<T, T, T> function2) {
        return RichFunction2AsBinaryOperator$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, T, T> scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying;
    }

    public BinaryOperator<T> asJava() {
        return RichFunction2AsBinaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsBinaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsBinaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying(), x$1);
    }

    public RichFunction2AsBinaryOperator(Function2<T, T, T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsBinaryOperator$$underlying = underlying;
    }
}

