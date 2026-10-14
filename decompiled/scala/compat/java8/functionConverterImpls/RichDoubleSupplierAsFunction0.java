/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichDoubleSupplierAsFunction0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&!\b*jG\"$u.\u001e2mKN+\b\u000f\u001d7jKJ\f5OR;oGRLwN\u001c\u0019\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001A\u0002CA\r\u001b\u001b\u0005!\u0012BA\u000e\u0015\u0005\u0019\te.\u001f,bY\u0006\u00196oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\t>,(\r\\3TkB\u0004H.[3s\u0003N4UO\\2uS>t\u0007\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012A\b\t\u0003?\u0019j\u0011\u0001\t\u0006\u0003C\t\n\u0001BZ;oGRLwN\u001c\u0006\u0003G\u0011\nA!\u001e;jY*\tQ%\u0001\u0003kCZ\f\u0017BA\u0014!\u00059!u.\u001e2mKN+\b\u000f\u001d7jKJ\fAk]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u0012{WO\u00197f'V\u0004\b\u000f\\5fe\u0006\u001bh)\u001e8di&|g\u000e\r\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003W5\u0002\"\u0001\f\u0001\u000e\u00039AQAL\u0002A\u0002y\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012!\r\t\u00043I\"\u0014BA\u001a\u0015\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0002\u001ak%\u0011a\u0007\u0006\u0002\u0007\t>,(\r\\3)\u0005\u0011A\u0004CA\r:\u0013\tQDC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tQ\b\u0005\u0002\u001a}%\u0011q\b\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002C\u000bB\u0011\u0011dQ\u0005\u0003\tR\u0011qAQ8pY\u0016\fg\u000eC\u0004G\r\u0005\u0005\t\u0019A$\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u0011&\u0011\u0011\n\u0006\u0002\u0004\u0003:L\u0018!\b*jG\"$u.\u001e2mKN+\b\u000f\u001d7jKJ\f5OR;oGRLwN\u001c\u0019\u0011\u00051B1C\u0001\u0005N!\tIb*\u0003\u0002P)\t1\u0011I\\=SK\u001a$\u0012aS\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tGCA\u0019T\u0011\u0015!&\u00021\u0001,\u0003\u0015!C\u000f[5tQ\tQ\u0001(\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tGC\u0001\u001fY\u0011\u0015!6\u00021\u0001,\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002\\;R\u0011!\t\u0018\u0005\b\r2\t\t\u00111\u0001H\u0011\u0015!F\u00021\u0001,\u0001")
public final class RichDoubleSupplierAsFunction0 {
    private final DoubleSupplier scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying;

    public static boolean equals$extension(DoubleSupplier doubleSupplier, Object object) {
        return RichDoubleSupplierAsFunction0$.MODULE$.equals$extension(doubleSupplier, object);
    }

    public static int hashCode$extension(DoubleSupplier doubleSupplier) {
        return RichDoubleSupplierAsFunction0$.MODULE$.hashCode$extension(doubleSupplier);
    }

    public static Function0<Object> asScala$extension(DoubleSupplier doubleSupplier) {
        return RichDoubleSupplierAsFunction0$.MODULE$.asScala$extension(doubleSupplier);
    }

    public DoubleSupplier scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying;
    }

    public Function0<Object> asScala() {
        return RichDoubleSupplierAsFunction0$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying());
    }

    public int hashCode() {
        return RichDoubleSupplierAsFunction0$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichDoubleSupplierAsFunction0$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying(), x$1);
    }

    public RichDoubleSupplierAsFunction0(DoubleSupplier underlying) {
        this.scala$compat$java8$functionConverterImpls$RichDoubleSupplierAsFunction0$$underlying = underlying;
    }
}

