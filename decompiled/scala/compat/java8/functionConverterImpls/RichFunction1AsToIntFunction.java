/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsToIntFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bEBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'\u0001\b*jG\"4UO\\2uS>t\u0017'Q:U_&sGOR;oGRLwN\u001c\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\u000b\u00031\u0011\u001a\"\u0001A\r\u0011\u0005iYR\"\u0001\u000b\n\u0005q!\"AB!osZ\u000bG.\u0001*tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|g.M!t)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8%IUtG-\u001a:ms&tw-F\u0001 !\u0011Q\u0002EI\u0017\n\u0005\u0005\"\"!\u0003$v]\u000e$\u0018n\u001c82!\t\u0019C\u0005\u0004\u0001\u0005\u000b\u0015\u0002!\u0019\u0001\u0014\u0003\u0003Q\u000b\"a\n\u0016\u0011\u0005iA\u0013BA\u0015\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AG\u0016\n\u00051\"\"aA!osB\u0011!DL\u0005\u0003_Q\u00111!\u00138u\u0003M\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHk\\%oi\u001a+hn\u0019;j_:$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aM\u001b\u0011\u0007Q\u0002!%D\u0001\u000f\u0011\u001514\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u0003e\u00022AO!#\u001b\u0005Y$B\u0001\u001f>\u0003!1WO\\2uS>t'B\u0001 @\u0003\u0011)H/\u001b7\u000b\u0003\u0001\u000bAA[1wC&\u0011!i\u000f\u0002\u000e)>Le\u000e\u001e$v]\u000e$\u0018n\u001c8)\u0005\u0011!\u0005C\u0001\u000eF\u0013\t1EC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tQ&\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0017:\u0003\"A\u0007'\n\u00055#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u001f\u001a\t\t\u00111\u0001+\u0003\rAH%M\u0001\u001d%&\u001c\u0007NR;oGRLwN\\\u0019BgR{\u0017J\u001c;Gk:\u001cG/[8o!\t!\u0004b\u0005\u0002\t'B\u0011!\u0004V\u0005\u0003+R\u0011a!\u00118z%\u00164G#A)\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tWCA-])\tQV\fE\u0002;\u0003n\u0003\"a\t/\u0005\u000b\u0015R!\u0019\u0001\u0014\t\u000byS\u0001\u0019A0\u0002\u000b\u0011\"\b.[:\u0011\u0007Q\u00021\f\u000b\u0002\u000b\t\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\t\u0019w\r\u0006\u0002II\")al\u0003a\u0001KB\u0019A\u0007\u00014\u0011\u0005\r:G!B\u0013\f\u0005\u00041\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\tQ\u0007\u000f\u0006\u0002l[R\u00111\n\u001c\u0005\b\u001f2\t\t\u00111\u0001+\u0011\u0015qF\u00021\u0001o!\r!\u0004a\u001c\t\u0003GA$Q!\n\u0007C\u0002\u0019\u0002")
public final class RichFunction1AsToIntFunction<T> {
    private final Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying;

    public static <T> boolean equals$extension(Function1<T, Object> function1, Object object) {
        return RichFunction1AsToIntFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, Object> function1) {
        return RichFunction1AsToIntFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <T> ToIntFunction<T> asJava$extension(Function1<T, Object> function1) {
        return RichFunction1AsToIntFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying;
    }

    public ToIntFunction<T> asJava() {
        return RichFunction1AsToIntFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsToIntFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsToIntFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying(), x$1);
    }

    public RichFunction1AsToIntFunction(Function1<T, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsToIntFunction$$underlying = underlying;
    }
}

