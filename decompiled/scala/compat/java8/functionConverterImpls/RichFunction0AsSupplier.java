/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichFunction0AsSupplier$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IQ\u0006\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006]\u0001!\ta\f\u0005\u0006i\u0001!\t!\u000e\u0005\b\t\u0002\t\t\u0011\"\u0011F\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b9BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'a\u0006*jG\"4UO\\2uS>t\u0007'Q:TkB\u0004H.[3s\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tABe\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002\u001bN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c81\u0003N\u001cV\u000f\u001d9mS\u0016\u0014H\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0002\u001bA\tJ!!\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004CA\u0012%\u0019\u0001!Q!\n\u0001C\u0002\u0019\u0012\u0011\u0001V\t\u0003O)\u0002\"A\u0007\u0015\n\u0005%\"\"a\u0002(pi\"Lgn\u001a\t\u00035-J!\u0001\f\u000b\u0003\u0007\u0005s\u00170\u0001(tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|g\u000eM!t'V\u0004\b\u000f\\5fe\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001G\r\t\u0004c\u0001\u0011S\"\u0001\b\t\u000bM\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002mA\u0019qG\u0010\u0012\u000e\u0003aR!!\u000f\u001e\u0002\u0011\u0019,hn\u0019;j_:T!a\u000f\u001f\u0002\tU$\u0018\u000e\u001c\u0006\u0002{\u0005!!.\u0019<b\u0013\ty\u0004H\u0001\u0005TkB\u0004H.[3sQ\t!\u0011\t\u0005\u0002\u001b\u0005&\u00111\t\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012A\u0012\t\u00035\u001dK!\u0001\u0013\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u0017:\u0003\"A\u0007'\n\u00055#\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u001f\u001a\t\t\u00111\u0001+\u0003\rAH%M\u0001\u0018%&\u001c\u0007NR;oGRLwN\u001c\u0019BgN+\b\u000f\u001d7jKJ\u0004\"!\r\u0005\u0014\u0005!\u0019\u0006C\u0001\u000eU\u0013\t)FC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002#\u0006\u0001\u0012m\u001d&bm\u0006$S\r\u001f;f]NLwN\\\u000b\u00033r#\"AW/\u0011\u0007]r4\f\u0005\u0002$9\u0012)QE\u0003b\u0001M!)aL\u0003a\u0001?\u0006)A\u0005\u001e5jgB\u0019\u0011\u0007A.)\u0005)\t\u0015A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,\"aY4\u0015\u0005\u0015#\u0007\"\u00020\f\u0001\u0004)\u0007cA\u0019\u0001MB\u00111e\u001a\u0003\u0006K-\u0011\rAJ\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,\"A\u001b9\u0015\u0005-lGCA&m\u0011\u001dyE\"!AA\u0002)BQA\u0018\u0007A\u00029\u00042!\r\u0001p!\t\u0019\u0003\u000fB\u0003&\u0019\t\u0007a\u0005")
public final class RichFunction0AsSupplier<T> {
    private final Function0<T> scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying;

    public static <T> boolean equals$extension(Function0<T> function0, Object object) {
        return RichFunction0AsSupplier$.MODULE$.equals$extension(function0, object);
    }

    public static <T> int hashCode$extension(Function0<T> function0) {
        return RichFunction0AsSupplier$.MODULE$.hashCode$extension(function0);
    }

    public static <T> Supplier<T> asJava$extension(Function0<T> function0) {
        return RichFunction0AsSupplier$.MODULE$.asJava$extension(function0);
    }

    public Function0<T> scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying;
    }

    public Supplier<T> asJava() {
        return RichFunction0AsSupplier$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying());
    }

    public int hashCode() {
        return RichFunction0AsSupplier$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction0AsSupplier$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying(), x$1);
    }

    public RichFunction0AsSupplier(Function0<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction0AsSupplier$$underlying = underlying;
    }
}

