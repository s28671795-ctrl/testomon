/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichSupplierAsFunction0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bUBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'a\u0006*jG\"\u001cV\u000f\u001d9mS\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c81\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tA2f\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002\u001bN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r[*vaBd\u0017.\u001a:Bg\u001a+hn\u0019;j_:\u0004D\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0002!O%j\u0011!\t\u0006\u0003E\r\n\u0001BZ;oGRLwN\u001c\u0006\u0003I\u0015\nA!\u001e;jY*\ta%\u0001\u0003kCZ\f\u0017B\u0001\u0015\"\u0005!\u0019V\u000f\u001d9mS\u0016\u0014\bC\u0001\u0016,\u0019\u0001!Q\u0001\f\u0001C\u00025\u0012\u0011\u0001V\t\u0003]E\u0002\"AG\u0018\n\u0005A\"\"a\u0002(pi\"Lgn\u001a\t\u00035IJ!a\r\u000b\u0003\u0007\u0005s\u00170\u0001(tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eD7+\u001e9qY&,'/Q:Gk:\u001cG/[8oa\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011q'\u000f\t\u0004q\u0001IS\"\u0001\b\t\u000bi\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003u\u00022A\u0007 *\u0013\tyDCA\u0005Gk:\u001cG/[8oa!\u0012A!\u0011\t\u00035\tK!a\u0011\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#\u0001$\u0011\u0005i9\u0015B\u0001%\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0005-s\u0005C\u0001\u000eM\u0013\tiECA\u0004C_>dW-\u00198\t\u000f=3\u0011\u0011!a\u0001c\u0005\u0019\u0001\u0010J\u0019\u0002/IK7\r[*vaBd\u0017.\u001a:Bg\u001a+hn\u0019;j_:\u0004\u0004C\u0001\u001d\t'\tA1\u000b\u0005\u0002\u001b)&\u0011Q\u000b\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003E\u000b\u0011#Y:TG\u0006d\u0017\rJ3yi\u0016t7/[8o+\tIF\f\u0006\u0002[;B\u0019!DP.\u0011\u0005)bF!\u0002\u0017\u000b\u0005\u0004i\u0003\"\u00020\u000b\u0001\u0004y\u0016!\u0002\u0013uQ&\u001c\bc\u0001\u001d\u00017\"\u0012!\"Q\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.\u0006\u0002dOR\u0011Q\t\u001a\u0005\u0006=.\u0001\r!\u001a\t\u0004q\u00011\u0007C\u0001\u0016h\t\u0015a3B1\u0001.\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g.\u0006\u0002kaR\u00111.\u001c\u000b\u0003\u00172Dqa\u0014\u0007\u0002\u0002\u0003\u0007\u0011\u0007C\u0003_\u0019\u0001\u0007a\u000eE\u00029\u0001=\u0004\"A\u000b9\u0005\u000b1b!\u0019A\u0017")
public final class RichSupplierAsFunction0<T> {
    private final Supplier<T> scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying;

    public static <T> boolean equals$extension(Supplier<T> supplier, Object object) {
        return RichSupplierAsFunction0$.MODULE$.equals$extension(supplier, object);
    }

    public static <T> int hashCode$extension(Supplier<T> supplier) {
        return RichSupplierAsFunction0$.MODULE$.hashCode$extension(supplier);
    }

    public static <T> Function0<T> asScala$extension(Supplier<T> supplier) {
        return RichSupplierAsFunction0$.MODULE$.asScala$extension(supplier);
    }

    public Supplier<T> scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying;
    }

    public Function0<T> asScala() {
        return RichSupplierAsFunction0$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying());
    }

    public int hashCode() {
        return RichSupplierAsFunction0$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichSupplierAsFunction0$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying(), x$1);
    }

    public RichSupplierAsFunction0(Supplier<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichSupplierAsFunction0$$underlying = underlying;
    }
}

