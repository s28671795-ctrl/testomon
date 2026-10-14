/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichDoubleConsumerAsFunction1$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00054A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b%BA\u0011A*\t\u000bQCAQA+\t\u000feC\u0011\u0011!C\u00035\"9A\fCA\u0001\n\u000bi&!\b*jG\"$u.\u001e2mK\u000e{gn];nKJ\f5OR;oGRLwN\\\u0019\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001A\u0002CA\r\u001b\u001b\u0005!\u0012BA\u000e\u0015\u0005\u0019\te.\u001f,bY\u0006\u00196oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\t>,(\r\\3D_:\u001cX/\\3s\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012A\b\t\u0003?\u0019j\u0011\u0001\t\u0006\u0003C\t\n\u0001BZ;oGRLwN\u001c\u0006\u0003G\u0011\nA!\u001e;jY*\tQ%\u0001\u0003kCZ\f\u0017BA\u0014!\u00059!u.\u001e2mK\u000e{gn];nKJ\fAk]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u0012{WO\u00197f\u0007>t7/^7fe\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003W5\u0002\"\u0001\f\u0001\u000e\u00039AQAL\u0002A\u0002y\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012!\r\t\u00053I\"t'\u0003\u00024)\tIa)\u001e8di&|g.\r\t\u00033UJ!A\u000e\u000b\u0003\r\u0011{WO\u00197f!\tI\u0002(\u0003\u0002:)\t!QK\\5uQ\t!1\b\u0005\u0002\u001ay%\u0011Q\b\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001\u0011\t\u00033\u0005K!A\u0011\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u000b\"\u0003\"!\u0007$\n\u0005\u001d#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u0013\u001a\t\t\u00111\u0001K\u0003\rAH%\r\t\u00033-K!\u0001\u0014\u000b\u0003\u0007\u0005s\u00170A\u000fSS\u000eDGi\\;cY\u0016\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c82!\ta\u0003b\u0005\u0002\t!B\u0011\u0011$U\u0005\u0003%R\u0011a!\u00118z%\u00164G#\u0001(\u0002#\u0005\u001c8kY1mC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00022-\")qK\u0003a\u0001W\u0005)A\u0005\u001e5jg\"\u0012!bO\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002@7\")qk\u0003a\u0001W\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\u001c\u000b\u0003=\u0002$\"!R0\t\u000f%c\u0011\u0011!a\u0001\u0015\")q\u000b\u0004a\u0001W\u0001")
public final class RichDoubleConsumerAsFunction1 {
    private final DoubleConsumer scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying;

    public static boolean equals$extension(DoubleConsumer doubleConsumer, Object object) {
        return RichDoubleConsumerAsFunction1$.MODULE$.equals$extension(doubleConsumer, object);
    }

    public static int hashCode$extension(DoubleConsumer doubleConsumer) {
        return RichDoubleConsumerAsFunction1$.MODULE$.hashCode$extension(doubleConsumer);
    }

    public static Function1<Object, BoxedUnit> asScala$extension(DoubleConsumer doubleConsumer) {
        return RichDoubleConsumerAsFunction1$.MODULE$.asScala$extension(doubleConsumer);
    }

    public DoubleConsumer scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying;
    }

    public Function1<Object, BoxedUnit> asScala() {
        return RichDoubleConsumerAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichDoubleConsumerAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichDoubleConsumerAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying(), x$1);
    }

    public RichDoubleConsumerAsFunction1(DoubleConsumer underlying) {
        this.scala$compat$java8$functionConverterImpls$RichDoubleConsumerAsFunction1$$underlying = underlying;
    }
}

