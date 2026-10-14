/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsDoublePredicate$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b!BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\b*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.\u001a)sK\u0012L7-\u0019;f\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0001\u0004\u0005\u0002\u001a55\tA#\u0003\u0002\u001c)\t1\u0011I\\=WC2\fAk]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d#pk\ndW\r\u0015:fI&\u001c\u0017\r^3%IUtG-\u001a:ms&tw-F\u0001\u001f!\u0011Ir$\t\u0013\n\u0005\u0001\"\"!\u0003$v]\u000e$\u0018n\u001c82!\tI\"%\u0003\u0002$)\t1Ai\\;cY\u0016\u0004\"!G\u0013\n\u0005\u0019\"\"a\u0002\"p_2,\u0017M\\\u0001Vg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg\u0012{WO\u00197f!J,G-[2bi\u0016$C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"A\u000b\u0017\u0011\u0005-\u0002Q\"\u0001\b\t\u000b5\u001a\u0001\u0019\u0001\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002aA\u0011\u0011\u0007O\u0007\u0002e)\u00111\u0007N\u0001\tMVt7\r^5p]*\u0011QGN\u0001\u0005kRLGNC\u00018\u0003\u0011Q\u0017M^1\n\u0005e\u0012$a\u0004#pk\ndW\r\u0015:fI&\u001c\u0017\r^3)\u0005\u0011Y\u0004CA\r=\u0013\tiDC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001\t\u0005\u0002\u001a\u0003&\u0011!\t\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002%\u000b\"9aIBA\u0001\u0002\u00049\u0015a\u0001=%cA\u0011\u0011\u0004S\u0005\u0003\u0013R\u00111!\u00118z\u0003y\u0011\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHi\\;cY\u0016\u0004&/\u001a3jG\u0006$X\r\u0005\u0002,\u0011M\u0011\u0001\"\u0014\t\u000339K!a\u0014\u000b\u0003\r\u0005s\u0017PU3g)\u0005Y\u0015\u0001E1t\u0015\u00064\u0018\rJ3yi\u0016t7/[8o)\t\u00014\u000bC\u0003U\u0015\u0001\u0007!&A\u0003%i\"L7\u000f\u000b\u0002\u000bw\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\ty\u0004\fC\u0003U\u0017\u0001\u0007!&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R\u00111,\u0018\u000b\u0003IqCqA\u0012\u0007\u0002\u0002\u0003\u0007q\tC\u0003U\u0019\u0001\u0007!\u0006")
public final class RichFunction1AsDoublePredicate {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsDoublePredicate$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsDoublePredicate$.MODULE$.hashCode$extension(function1);
    }

    public static DoublePredicate asJava$extension(Function1 function1) {
        return RichFunction1AsDoublePredicate$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying;
    }

    public DoublePredicate asJava() {
        return RichFunction1AsDoublePredicate$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsDoublePredicate$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsDoublePredicate$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying(), x$1);
    }

    public RichFunction1AsDoublePredicate(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoublePredicate$$underlying = underlying;
    }
}

