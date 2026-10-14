/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BooleanSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichBooleanSupplierAsFunction0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b%BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&A\b*jG\"\u0014un\u001c7fC:\u001cV\u000f\u001d9mS\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c81\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0001\u0004\u0005\u0002\u001a55\tA#\u0003\u0002\u001c)\t1\u0011I\\=WC2\fAk]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\n{w\u000e\\3b]N+\b\u000f\u001d7jKJ\f5OR;oGRLwN\u001c\u0019%IUtG-\u001a:ms&tw-F\u0001\u001f!\tyb%D\u0001!\u0015\t\t#%\u0001\u0005gk:\u001cG/[8o\u0015\t\u0019C%\u0001\u0003vi&d'\"A\u0013\u0002\t)\fg/Y\u0005\u0003O\u0001\u0012qBQ8pY\u0016\fgnU;qa2LWM]\u0001Vg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NQ8pY\u0016\fgnU;qa2LWM]!t\rVt7\r^5p]B\"C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aK\u0017\u0011\u00051\u0002Q\"\u0001\b\t\u000b9\u001a\u0001\u0019\u0001\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003E\u00022!\u0007\u001a5\u0013\t\u0019DCA\u0005Gk:\u001cG/[8oaA\u0011\u0011$N\u0005\u0003mQ\u0011qAQ8pY\u0016\fg\u000e\u000b\u0002\u0005qA\u0011\u0011$O\u0005\u0003uQ\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003u\u0002\"!\u0007 \n\u0005}\"\"aA%oi\u00061Q-];bYN$\"\u0001\u000e\"\t\u000f\r3\u0011\u0011!a\u0001\t\u0006\u0019\u0001\u0010J\u0019\u0011\u0005e)\u0015B\u0001$\u0015\u0005\r\te._\u0001\u001f%&\u001c\u0007NQ8pY\u0016\fgnU;qa2LWM]!t\rVt7\r^5p]B\u0002\"\u0001\f\u0005\u0014\u0005!Q\u0005CA\rL\u0013\taEC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002\u0011\u0006\t\u0012m]*dC2\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005E\u0002\u0006\"B)\u000b\u0001\u0004Y\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u00069\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005q*\u0006\"B)\f\u0001\u0004Y\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tA&\f\u0006\u000253\"91\tDA\u0001\u0002\u0004!\u0005\"B)\r\u0001\u0004Y\u0003")
public final class RichBooleanSupplierAsFunction0 {
    private final BooleanSupplier scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying;

    public static boolean equals$extension(BooleanSupplier booleanSupplier, Object object) {
        return RichBooleanSupplierAsFunction0$.MODULE$.equals$extension(booleanSupplier, object);
    }

    public static int hashCode$extension(BooleanSupplier booleanSupplier) {
        return RichBooleanSupplierAsFunction0$.MODULE$.hashCode$extension(booleanSupplier);
    }

    public static Function0<Object> asScala$extension(BooleanSupplier booleanSupplier) {
        return RichBooleanSupplierAsFunction0$.MODULE$.asScala$extension(booleanSupplier);
    }

    public BooleanSupplier scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying;
    }

    public Function0<Object> asScala() {
        return RichBooleanSupplierAsFunction0$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying());
    }

    public int hashCode() {
        return RichBooleanSupplierAsFunction0$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichBooleanSupplierAsFunction0$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying(), x$1);
    }

    public RichBooleanSupplierAsFunction0(BooleanSupplier underlying) {
        this.scala$compat$java8$functionConverterImpls$RichBooleanSupplierAsFunction0$$underlying = underlying;
    }
}

