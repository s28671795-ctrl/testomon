/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005\u00053q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u00035\u0001\u0011\u0005SGA\nK\rVt7\r^5p]J\"Sn\u0019.J\u0013\u0012\u001a\bO\u0003\u0002\u0007\u000f\u0005)!.\u0019<bq)\u0011\u0001\"C\u0001\beVtG/[7f\u0015\u0005Q\u0011!B:dC2\f7\u0001A\n\u0005\u00015\tr\u0003\u0005\u0002\u000f\u001f5\t\u0011\"\u0003\u0002\u0011\u0013\t1\u0011I\\=SK\u001a\u0004RA\u0004\n\u0015)QI!aE\u0005\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\b\u0016\u0013\t1\u0012BA\u0002B]f\u0004\"\u0001\u0007\u0011\u000f\u0005eqbB\u0001\u000e\u001e\u001b\u0005Y\"B\u0001\u000f\f\u0003\u0019a$o\\8u}%\t!\"\u0003\u0002 \u0013\u00059\u0001/Y2lC\u001e,\u0017BA\u0011#\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ty\u0012\"\u0001\u0004%S:LG\u000f\n\u000b\u0002KA\u0011aBJ\u0005\u0003O%\u0011A!\u00168ji\u0006q\u0011\r\u001d9ms\u0012j7MW%JIM\u0004Hc\u0001\u0016.eA\u0011abK\u0005\u0003Y%\u0011qAQ8pY\u0016\fg\u000eC\u0003/\u0005\u0001\u0007q&\u0001\u0002wcA\u0011a\u0002M\u0005\u0003c%\u00111!\u00138u\u0011\u0015\u0019$\u00011\u00010\u0003\t1('A\u0003baBd\u0017\u0010F\u0002\u0015m]BQAL\u0002A\u0002QAQaM\u0002A\u0002QA#\u0001A\u001d\u0011\u0005izT\"A\u001e\u000b\u0005qj\u0014\u0001\u00027b]\u001eT\u0011AP\u0001\u0005U\u00064\u0018-\u0003\u0002Aw\t\u0019b)\u001e8di&|g.\u00197J]R,'OZ1dK\u0002")
public interface JFunction2$mcZII$sp
extends Function2<Object, Object, Object>,
Serializable {
    @Override
    public boolean apply$mcZII$sp(int var1, int var2);

    public static /* synthetic */ Object apply$(JFunction2$mcZII$sp $this, Object v1, Object v2) {
        return $this.apply(v1, v2);
    }

    @Override
    default public Object apply(Object v1, Object v2) {
        return this.apply$mcZII$sp(BoxesRunTime.unboxToInt(v1), BoxesRunTime.unboxToInt(v2));
    }

    public static void $init$(JFunction2$mcZII$sp $this) {
    }
}

