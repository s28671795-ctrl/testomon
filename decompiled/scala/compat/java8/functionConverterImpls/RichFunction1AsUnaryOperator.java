/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.UnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsUnaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IQ\u0006\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b9BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'\u0001\b*jG\"4UO\\2uS>t\u0017'Q:V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\u000b\u00031\u0011\u001a\"\u0001A\r\u0011\u0005iYR\"\u0001\u000b\n\u0005q!\"AB!osZ\u000bG.\u0001*tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|g.M!t+:\f'/_(qKJ\fGo\u001c:%IUtG-\u001a:ms&tw-F\u0001 !\u0011Q\u0002E\t\u0012\n\u0005\u0005\"\"!\u0003$v]\u000e$\u0018n\u001c82!\t\u0019C\u0005\u0004\u0001\u0005\u000b\u0015\u0002!\u0019\u0001\u0014\u0003\u0003Q\u000b\"a\n\u0016\u0011\u0005iA\u0013BA\u0015\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AG\u0016\n\u00051\"\"aA!os\u0006\u00196oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]F\n5/\u00168bef|\u0005/\u001a:bi>\u0014H\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u00193!\r\t\u0004AI\u0007\u0002\u001d!)1g\u0001a\u0001?\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u00051\u0004cA\u001c?E5\t\u0001H\u0003\u0002:u\u0005Aa-\u001e8di&|gN\u0003\u0002<y\u0005!Q\u000f^5m\u0015\u0005i\u0014\u0001\u00026bm\u0006L!a\u0010\u001d\u0003\u001bUs\u0017M]=Pa\u0016\u0014\u0018\r^8sQ\t!\u0011\t\u0005\u0002\u001b\u0005&\u00111\t\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012A\u0012\t\u00035\u001dK!\u0001\u0013\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0017:\u0003\"A\u0007'\n\u00055#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u001f\u001a\t\t\u00111\u0001+\u0003\rAH%M\u0001\u001d%&\u001c\u0007NR;oGRLwN\\\u0019BgVs\u0017M]=Pa\u0016\u0014\u0018\r^8s!\t\t\u0004b\u0005\u0002\t'B\u0011!\u0004V\u0005\u0003+R\u0011a!\u00118z%\u00164G#A)\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tWCA-])\tQV\fE\u00028}m\u0003\"a\t/\u0005\u000b\u0015R!\u0019\u0001\u0014\t\u000byS\u0001\u0019A0\u0002\u000b\u0011\"\b.[:\u0011\u0007E\u00021\f\u000b\u0002\u000b\u0003\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\t\u0019w\r\u0006\u0002FI\")al\u0003a\u0001KB\u0019\u0011\u0007\u00014\u0011\u0005\r:G!B\u0013\f\u0005\u00041\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\tQ\u0007\u000f\u0006\u0002l[R\u00111\n\u001c\u0005\b\u001f2\t\t\u00111\u0001+\u0011\u0015qF\u00021\u0001o!\r\t\u0004a\u001c\t\u0003GA$Q!\n\u0007C\u0002\u0019\u0002")
public final class RichFunction1AsUnaryOperator<T> {
    private final Function1<T, T> scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying;

    public static <T> boolean equals$extension(Function1<T, T> function1, Object object) {
        return RichFunction1AsUnaryOperator$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, T> function1) {
        return RichFunction1AsUnaryOperator$.MODULE$.hashCode$extension(function1);
    }

    public static <T> UnaryOperator<T> asJava$extension(Function1<T, T> function1) {
        return RichFunction1AsUnaryOperator$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, T> scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying;
    }

    public UnaryOperator<T> asJava() {
        return RichFunction1AsUnaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsUnaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsUnaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying(), x$1);
    }

    public RichFunction1AsUnaryOperator(Function1<T, T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsUnaryOperator$$underlying = underlying;
    }
}

