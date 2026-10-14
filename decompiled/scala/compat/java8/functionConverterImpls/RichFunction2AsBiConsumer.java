/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BiConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsBiConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bQBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(!\u0007*jG\"4UO\\2uS>t''Q:CS\u000e{gn];nKJT!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001)2\u0001\u0007\u0013/'\t\u0001\u0011\u0004\u0005\u0002\u001b75\tA#\u0003\u0002\u001d)\t1\u0011I\\=WC2\fqj]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\u0014\u0014i\u001d\"j\u0007>t7/^7fe\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001RA\u0007\u0011#[AJ!!\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u0012%\u0019\u0001!Q!\n\u0001C\u0002\u0019\u0012\u0011\u0001V\t\u0003O)\u0002\"A\u0007\u0015\n\u0005%\"\"a\u0002(pi\"Lgn\u001a\t\u00035-J!\u0001\f\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$]\u0011)q\u0006\u0001b\u0001M\t\tQ\u000b\u0005\u0002\u001bc%\u0011!\u0007\u0006\u0002\u0005+:LG/\u0001)tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|gNM!t\u0005&\u001cuN\\:v[\u0016\u0014H\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u001c9!\u00119\u0004AI\u0017\u000e\u00039AQ!O\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019\t7OS1wCV\tA\b\u0005\u0003>\t\njS\"\u0001 \u000b\u0005}\u0002\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u0005\u0013\u0015\u0001B;uS2T\u0011aQ\u0001\u0005U\u00064\u0018-\u0003\u0002F}\tQ!)[\"p]N,X.\u001a:)\u0005\u00119\u0005C\u0001\u000eI\u0013\tIEC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA\n\u0005\u0002\u001b\u001b&\u0011a\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002R)B\u0011!DU\u0005\u0003'R\u0011qAQ8pY\u0016\fg\u000eC\u0004V\r\u0005\u0005\t\u0019\u0001\u0016\u0002\u0007a$\u0013'A\rSS\u000eDg)\u001e8di&|gNM!t\u0005&\u001cuN\\:v[\u0016\u0014\bCA\u001c\t'\tA\u0011\f\u0005\u0002\u001b5&\u00111\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003]\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007}\u0013G\r\u0006\u0002aKB!Q\bR1d!\t\u0019#\rB\u0003&\u0015\t\u0007a\u0005\u0005\u0002$I\u0012)qF\u0003b\u0001M!)aM\u0003a\u0001O\u0006)A\u0005\u001e5jgB!q\u0007A1dQ\tQq)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWcA6pcR\u00111\n\u001c\u0005\u0006M.\u0001\r!\u001c\t\u0005o\u0001q\u0007\u000f\u0005\u0002$_\u0012)Qe\u0003b\u0001MA\u00111%\u001d\u0003\u0006_-\u0011\rAJ\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,2\u0001\u001e>})\t)x\u000f\u0006\u0002Rm\"9Q\u000bDA\u0001\u0002\u0004Q\u0003\"\u00024\r\u0001\u0004A\b\u0003B\u001c\u0001sn\u0004\"a\t>\u0005\u000b\u0015b!\u0019\u0001\u0014\u0011\u0005\rbH!B\u0018\r\u0005\u00041\u0003")
public final class RichFunction2AsBiConsumer<T, U> {
    private final Function2<T, U, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying;

    public static <T, U> boolean equals$extension(Function2<T, U, BoxedUnit> function2, Object object) {
        return RichFunction2AsBiConsumer$.MODULE$.equals$extension(function2, object);
    }

    public static <T, U> int hashCode$extension(Function2<T, U, BoxedUnit> function2) {
        return RichFunction2AsBiConsumer$.MODULE$.hashCode$extension(function2);
    }

    public static <T, U> BiConsumer<T, U> asJava$extension(Function2<T, U, BoxedUnit> function2) {
        return RichFunction2AsBiConsumer$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, U, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying;
    }

    public BiConsumer<T, U> asJava() {
        return RichFunction2AsBiConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsBiConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsBiConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying(), x$1);
    }

    public RichFunction2AsBiConsumer(Function2<T, U, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsBiConsumer$$underlying = underlying;
    }
}

