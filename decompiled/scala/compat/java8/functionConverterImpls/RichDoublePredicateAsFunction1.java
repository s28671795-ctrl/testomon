/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoublePredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichDoublePredicateAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\b*jG\"$u.\u001e2mKB\u0013X\rZ5dCR,\u0017i\u001d$v]\u000e$\u0018n\u001c82\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0001\u0004\u0005\u0002\u001a55\tA#\u0003\u0002\u001c)\t1\u0011I\\=WC2\fAk]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u0012{WO\u00197f!J,G-[2bi\u0016\f5OR;oGRLwN\\\u0019%IUtG-\u001a:ms&tw-F\u0001\u001f!\tyb%D\u0001!\u0015\t\t#%\u0001\u0005gk:\u001cG/[8o\u0015\t\u0019C%\u0001\u0003vi&d'\"A\u0013\u0002\t)\fg/Y\u0005\u0003O\u0001\u0012q\u0002R8vE2,\u0007K]3eS\u000e\fG/Z\u0001Vg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007\u000eR8vE2,\u0007K]3eS\u000e\fG/Z!t\rVt7\r^5p]F\"C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aK\u0017\u0011\u00051\u0002Q\"\u0001\b\t\u000b9\u001a\u0001\u0019\u0001\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003E\u0002B!\u0007\u001a5o%\u00111\u0007\u0006\u0002\n\rVt7\r^5p]F\u0002\"!G\u001b\n\u0005Y\"\"A\u0002#pk\ndW\r\u0005\u0002\u001aq%\u0011\u0011\b\u0006\u0002\b\u0005>|G.Z1oQ\t!1\b\u0005\u0002\u001ay%\u0011Q\b\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u0011\t\u00033\u0005K!A\u0011\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003o\u0015CqA\u0012\u0004\u0002\u0002\u0003\u0007q)A\u0002yIE\u0002\"!\u0007%\n\u0005%#\"aA!os\u0006q\"+[2i\t>,(\r\\3Qe\u0016$\u0017nY1uK\u0006\u001bh)\u001e8di&|g.\r\t\u0003Y!\u0019\"\u0001C'\u0011\u0005eq\u0015BA(\u0015\u0005\u0019\te.\u001f*fMR\t1*A\tbgN\u001b\u0017\r\\1%Kb$XM\\:j_:$\"!M*\t\u000bQS\u0001\u0019A\u0016\u0002\u000b\u0011\"\b.[:)\u0005)Y\u0014A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"a\u0010-\t\u000bQ[\u0001\u0019A\u0016\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGCA.^)\t9D\fC\u0004G\u0019\u0005\u0005\t\u0019A$\t\u000bQc\u0001\u0019A\u0016")
public final class RichDoublePredicateAsFunction1 {
    private final DoublePredicate scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying;

    public static boolean equals$extension(DoublePredicate doublePredicate, Object object) {
        return RichDoublePredicateAsFunction1$.MODULE$.equals$extension(doublePredicate, object);
    }

    public static int hashCode$extension(DoublePredicate doublePredicate) {
        return RichDoublePredicateAsFunction1$.MODULE$.hashCode$extension(doublePredicate);
    }

    public static Function1<Object, Object> asScala$extension(DoublePredicate doublePredicate) {
        return RichDoublePredicateAsFunction1$.MODULE$.asScala$extension(doublePredicate);
    }

    public DoublePredicate scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying;
    }

    public Function1<Object, Object> asScala() {
        return RichDoublePredicateAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichDoublePredicateAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichDoublePredicateAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying(), x$1);
    }

    public RichDoublePredicateAsFunction1(DoublePredicate underlying) {
        this.scala$compat$java8$functionConverterImpls$RichDoublePredicateAsFunction1$$underlying = underlying;
    }
}

