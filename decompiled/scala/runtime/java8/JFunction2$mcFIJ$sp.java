/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005\u00113q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u00038\u0001\u0011\u0005\u0003HA\nK\rVt7\r^5p]J\"Sn\u0019$J\u0015\u0012\u001a\bO\u0003\u0002\u0007\u000f\u0005)!.\u0019<bq)\u0011\u0001\"C\u0001\beVtG/[7f\u0015\u0005Q\u0011!B:dC2\f7\u0001A\n\u0005\u00015\tr\u0003\u0005\u0002\u000f\u001f5\t\u0011\"\u0003\u0002\u0011\u0013\t1\u0011I\\=SK\u001a\u0004RA\u0004\n\u0015)QI!aE\u0005\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\b\u0016\u0013\t1\u0012BA\u0002B]f\u0004\"\u0001\u0007\u0011\u000f\u0005eqbB\u0001\u000e\u001e\u001b\u0005Y\"B\u0001\u000f\f\u0003\u0019a$o\\8u}%\t!\"\u0003\u0002 \u0013\u00059\u0001/Y2lC\u001e,\u0017BA\u0011#\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ty\u0012\"\u0001\u0004%S:LG\u000f\n\u000b\u0002KA\u0011aBJ\u0005\u0003O%\u0011A!\u00168ji\u0006q\u0011\r\u001d9ms\u0012j7MR%KIM\u0004Hc\u0001\u0016.eA\u0011abK\u0005\u0003Y%\u0011QA\u00127pCRDQA\f\u0002A\u0002=\n!A^\u0019\u0011\u00059\u0001\u0014BA\u0019\n\u0005\rIe\u000e\u001e\u0005\u0006g\t\u0001\r\u0001N\u0001\u0003mJ\u0002\"AD\u001b\n\u0005YJ!\u0001\u0002'p]\u001e\fQ!\u00199qYf$2\u0001F\u001d;\u0011\u0015q3\u00011\u0001\u0015\u0011\u0015\u00194\u00011\u0001\u0015Q\t\u0001A\b\u0005\u0002>\u00056\taH\u0003\u0002@\u0001\u0006!A.\u00198h\u0015\u0005\t\u0015\u0001\u00026bm\u0006L!a\u0011 \u0003'\u0019+hn\u0019;j_:\fG.\u00138uKJ4\u0017mY3")
public interface JFunction2$mcFIJ$sp
extends Function2<Object, Object, Object>,
Serializable {
    @Override
    public float apply$mcFIJ$sp(int var1, long var2);

    public static /* synthetic */ Object apply$(JFunction2$mcFIJ$sp $this, Object v1, Object v2) {
        return $this.apply(v1, v2);
    }

    @Override
    default public Object apply(Object v1, Object v2) {
        return Float.valueOf(this.apply$mcFIJ$sp(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToLong(v2)));
    }

    public static void $init$(JFunction2$mcFIJ$sp $this) {
    }
}

