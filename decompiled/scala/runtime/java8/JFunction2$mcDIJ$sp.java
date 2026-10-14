/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005\u00113q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u00038\u0001\u0011\u0005\u0003HA\nK\rVt7\r^5p]J\"Sn\u0019#J\u0015\u0012\u001a\bO\u0003\u0002\u0007\u000f\u0005)!.\u0019<bq)\u0011\u0001\"C\u0001\beVtG/[7f\u0015\u0005Q\u0011!B:dC2\f7\u0001A\n\u0005\u00015\tr\u0003\u0005\u0002\u000f\u001f5\t\u0011\"\u0003\u0002\u0011\u0013\t1\u0011I\\=SK\u001a\u0004RA\u0004\n\u0015)QI!aE\u0005\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\b\u0016\u0013\t1\u0012BA\u0002B]f\u0004\"\u0001\u0007\u0011\u000f\u0005eqbB\u0001\u000e\u001e\u001b\u0005Y\"B\u0001\u000f\f\u0003\u0019a$o\\8u}%\t!\"\u0003\u0002 \u0013\u00059\u0001/Y2lC\u001e,\u0017BA\u0011#\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ty\u0012\"\u0001\u0004%S:LG\u000f\n\u000b\u0002KA\u0011aBJ\u0005\u0003O%\u0011A!\u00168ji\u0006q\u0011\r\u001d9ms\u0012j7\rR%KIM\u0004Hc\u0001\u0016.eA\u0011abK\u0005\u0003Y%\u0011a\u0001R8vE2,\u0007\"\u0002\u0018\u0003\u0001\u0004y\u0013A\u0001<2!\tq\u0001'\u0003\u00022\u0013\t\u0019\u0011J\u001c;\t\u000bM\u0012\u0001\u0019\u0001\u001b\u0002\u0005Y\u0014\u0004C\u0001\b6\u0013\t1\u0014B\u0001\u0003M_:<\u0017!B1qa2LHc\u0001\u000b:u!)af\u0001a\u0001)!)1g\u0001a\u0001)!\u0012\u0001\u0001\u0010\t\u0003{\tk\u0011A\u0010\u0006\u0003\u007f\u0001\u000bA\u0001\\1oO*\t\u0011)\u0001\u0003kCZ\f\u0017BA\"?\u0005M1UO\\2uS>t\u0017\r\\%oi\u0016\u0014h-Y2f\u0001")
public interface JFunction2$mcDIJ$sp
extends Function2<Object, Object, Object>,
Serializable {
    @Override
    public double apply$mcDIJ$sp(int var1, long var2);

    public static /* synthetic */ Object apply$(JFunction2$mcDIJ$sp $this, Object v1, Object v2) {
        return $this.apply(v1, v2);
    }

    @Override
    default public Object apply(Object v1, Object v2) {
        return this.apply$mcDIJ$sp(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToLong(v2));
    }

    public static void $init$(JFunction2$mcDIJ$sp $this) {
    }
}

