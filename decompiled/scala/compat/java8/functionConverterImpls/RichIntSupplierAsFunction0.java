/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichIntSupplierAsFunction0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001di\u0004!!A\u0005By:qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b%BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&A\u0007*jG\"Le\u000e^*vaBd\u0017.\u001a:Bg\u001a+hn\u0019;j_:\u0004$BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Qg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007.\u00138u'V\u0004\b\u000f\\5fe\u0006\u001bh)\u001e8di&|g\u000e\r\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\u0005}1S\"\u0001\u0011\u000b\u0005\u0005\u0012\u0013\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\r\"\u0013\u0001B;uS2T\u0011!J\u0001\u0005U\u00064\u0018-\u0003\u0002(A\tY\u0011J\u001c;TkB\u0004H.[3s\u0003E\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195J]R\u001cV\u000f\u001d9mS\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c81I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\tYS\u0006\u0005\u0002-\u00015\ta\u0002C\u0003/\u0007\u0001\u0007a$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fq!Y:TG\u0006d\u0017-F\u00012!\rI\"\u0007N\u0005\u0003gQ\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u0005e)\u0014B\u0001\u001c\u0015\u0005\rIe\u000e\u001e\u0015\u0003\ta\u0002\"!G\u001d\n\u0005i\"\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005!\u0014AB3rk\u0006d7\u000f\u0006\u0002@\u0005B\u0011\u0011\u0004Q\u0005\u0003\u0003R\u0011qAQ8pY\u0016\fg\u000eC\u0004D\r\u0005\u0005\t\u0019\u0001#\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u000b&\u0011a\t\u0006\u0002\u0004\u0003:L\u0018A\u0007*jG\"Le\u000e^*vaBd\u0017.\u001a:Bg\u001a+hn\u0019;j_:\u0004\u0004C\u0001\u0017\t'\tA!\n\u0005\u0002\u001a\u0017&\u0011A\n\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003!\u000b\u0011#Y:TG\u0006d\u0017\rJ3yi\u0016t7/[8o)\t\t\u0004\u000bC\u0003R\u0015\u0001\u00071&A\u0003%i\"L7\u000f\u000b\u0002\u000bq\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\taT\u000bC\u0003R\u0017\u0001\u00071&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R\u0011\u0001L\u0017\u000b\u0003\u007feCqa\u0011\u0007\u0002\u0002\u0003\u0007A\tC\u0003R\u0019\u0001\u00071\u0006")
public final class RichIntSupplierAsFunction0 {
    private final IntSupplier scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying;

    public static boolean equals$extension(IntSupplier intSupplier, Object object) {
        return RichIntSupplierAsFunction0$.MODULE$.equals$extension(intSupplier, object);
    }

    public static int hashCode$extension(IntSupplier intSupplier) {
        return RichIntSupplierAsFunction0$.MODULE$.hashCode$extension(intSupplier);
    }

    public static Function0<Object> asScala$extension(IntSupplier intSupplier) {
        return RichIntSupplierAsFunction0$.MODULE$.asScala$extension(intSupplier);
    }

    public IntSupplier scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying;
    }

    public Function0<Object> asScala() {
        return RichIntSupplierAsFunction0$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying());
    }

    public int hashCode() {
        return RichIntSupplierAsFunction0$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichIntSupplierAsFunction0$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying(), x$1);
    }

    public RichIntSupplierAsFunction0(IntSupplier underlying) {
        this.scala$compat$java8$functionConverterImpls$RichIntSupplierAsFunction0$$underlying = underlying;
    }
}

