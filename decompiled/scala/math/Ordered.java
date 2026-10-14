/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3q\u0001D\u0007\u0011\u0002\u0007\u0005!\u0003C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0003.\u0001\u0019\u0005a\u0006C\u00035\u0001\u0011\u0005Q\u0007C\u0003;\u0001\u0011\u00051\bC\u0003>\u0001\u0011\u0005a\bC\u0003A\u0001\u0011\u0005\u0011\tC\u0003D\u0001\u0011\u0005AiB\u0003G\u001b!\u0005qIB\u0003\r\u001b!\u0005\u0011\nC\u0003N\u0013\u0011\u0005a\nC\u0003P\u0013\u0011\r\u0001KA\u0004Pe\u0012,'/\u001a3\u000b\u00059y\u0011\u0001B7bi\"T\u0011\u0001E\u0001\u0006g\u000e\fG.Y\u0002\u0001+\t\u0019\"eE\u0002\u0001)a\u0001\"!\u0006\f\u000e\u0003=I!aF\b\u0003\u0007\u0005s\u0017\u0010E\u0002\u001a=\u0001j\u0011A\u0007\u0006\u00037q\tA\u0001\\1oO*\tQ$\u0001\u0003kCZ\f\u0017BA\u0010\u001b\u0005)\u0019u.\u001c9be\u0006\u0014G.\u001a\t\u0003C\tb\u0001\u0001B\u0003$\u0001\t\u0007AEA\u0001B#\t)C\u0003\u0005\u0002\u0016M%\u0011qe\u0004\u0002\b\u001d>$\b.\u001b8h\u0003\u0019!\u0013N\\5uIQ\t!\u0006\u0005\u0002\u0016W%\u0011Af\u0004\u0002\u0005+:LG/A\u0004d_6\u0004\u0018M]3\u0015\u0005=\u0012\u0004CA\u000b1\u0013\t\ttBA\u0002J]RDQa\r\u0002A\u0002\u0001\nA\u0001\u001e5bi\u0006)A\u0005\\3tgR\u0011a'\u000f\t\u0003+]J!\u0001O\b\u0003\u000f\t{w\u000e\\3b]\")1g\u0001a\u0001A\u0005AAe\u001a:fCR,'\u000f\u0006\u00027y!)1\u0007\u0002a\u0001A\u0005AA\u0005\\3tg\u0012*\u0017\u000f\u0006\u00027\u007f!)1'\u0002a\u0001A\u0005YAe\u001a:fCR,'\u000fJ3r)\t1$\tC\u00034\r\u0001\u0007\u0001%A\u0005d_6\u0004\u0018M]3U_R\u0011q&\u0012\u0005\u0006g\u001d\u0001\r\u0001I\u0001\b\u001fJ$WM]3e!\tA\u0015\"D\u0001\u000e'\tI!\n\u0005\u0002\u0016\u0017&\u0011Aj\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u00059\u0015!E8sI\u0016\u0014\u0018N\\4U_>\u0013H-\u001a:fIV\u0011\u0011+\u0016\u000b\u0003%r#\"aU,\u0011\u0007!\u0003A\u000b\u0005\u0002\"+\u0012)ak\u0003b\u0001I\t\tA\u000bC\u0003Y\u0017\u0001\u000f\u0011,A\u0002pe\u0012\u00042\u0001\u0013.U\u0013\tYVB\u0001\u0005Pe\u0012,'/\u001b8h\u0011\u0015i6\u00021\u0001U\u0003\u0005A\b")
public interface Ordered<A>
extends Comparable<A> {
    public static <T> Ordered<T> orderingToOrdered(T x, Ordering<T> ord) {
        return new Ordered<T>(ord, x){
            private final Ordering ord$1;
            private final Object x$1;

            public boolean $less(T that) {
                return Ordered.$less$(this, that);
            }

            public boolean $greater(T that) {
                return Ordered.$greater$(this, that);
            }

            public boolean $less$eq(T that) {
                return Ordered.$less$eq$(this, that);
            }

            public boolean $greater$eq(T that) {
                return Ordered.$greater$eq$(this, that);
            }

            public int compareTo(T that) {
                return Ordered.compareTo$(this, that);
            }

            public int compare(T that) {
                return this.ord$1.compare(this.x$1, that);
            }
            {
                this.ord$1 = ord$1;
                this.x$1 = x$1;
            }
        };
    }

    public int compare(A var1);

    public static /* synthetic */ boolean $less$(Ordered $this, Object that) {
        return $this.$less(that);
    }

    default public boolean $less(A that) {
        return this.compare(that) < 0;
    }

    public static /* synthetic */ boolean $greater$(Ordered $this, Object that) {
        return $this.$greater(that);
    }

    default public boolean $greater(A that) {
        return this.compare(that) > 0;
    }

    public static /* synthetic */ boolean $less$eq$(Ordered $this, Object that) {
        return $this.$less$eq(that);
    }

    default public boolean $less$eq(A that) {
        return this.compare(that) <= 0;
    }

    public static /* synthetic */ boolean $greater$eq$(Ordered $this, Object that) {
        return $this.$greater$eq(that);
    }

    default public boolean $greater$eq(A that) {
        return this.compare(that) >= 0;
    }

    public static /* synthetic */ int compareTo$(Ordered $this, Object that) {
        return $this.compareTo((A)that);
    }

    @Override
    default public int compareTo(A that) {
        return this.compare(that);
    }

    public static void $init$(Ordered $this) {
    }
}

