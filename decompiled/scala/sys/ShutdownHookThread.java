/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.sys.ShutdownHookThread$;

@ScalaSignature(bytes="\u0006\u000553Aa\u0003\u0007\u0001#!A!\u0004\u0001B\u0001B\u0003%1\u0004\u0003\u0005\u001f\u0001\t\u0005\t\u0015!\u0003 \u0011\u0015Q\u0003\u0001\"\u0003,\u0011\u0015\u0001\u0004\u0001\"\u00012\u000f\u00151D\u0002#\u00018\r\u0015YA\u0002#\u00019\u0011\u0015Qc\u0001\"\u0001=\u0011\u0019id\u0001)Q\u0005}!)\u0011I\u0002C\u0005\u0005\")1I\u0002C\u0001\t\n\u00112\u000b[;uI><h\u000eS8pWRC'/Z1e\u0015\tia\"A\u0002tsNT\u0011aD\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001!\u0003\u0005\u0002\u001415\tAC\u0003\u0002\u0016-\u0005!A.\u00198h\u0015\u00059\u0012\u0001\u00026bm\u0006L!!\u0007\u000b\u0003\rQC'/Z1e\u0003!\u0011XO\u001c8bE2,\u0007CA\n\u001d\u0013\tiBC\u0001\u0005Sk:t\u0017M\u00197f\u0003\u0011q\u0017-\\3\u0011\u0005\u0001:cBA\u0011&!\t\u0011c\"D\u0001$\u0015\t!\u0003#\u0001\u0004=e>|GOP\u0005\u0003M9\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0015*\u0005\u0019\u0019FO]5oO*\u0011aED\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00071rs\u0006\u0005\u0002.\u00015\tA\u0002C\u0003\u001b\u0007\u0001\u00071\u0004C\u0003\u001f\u0007\u0001\u0007q$\u0001\u0004sK6|g/\u001a\u000b\u0002eA\u00111\u0007N\u0007\u0002\u001d%\u0011QG\u0004\u0002\b\u0005>|G.Z1o\u0003I\u0019\u0006.\u001e;e_^t\u0007j\\8l)\"\u0014X-\u00193\u0011\u0005521C\u0001\u0004:!\t\u0019$(\u0003\u0002<\u001d\t1\u0011I\\=SK\u001a$\u0012aN\u0001\u000eQ>|7NT1nK\u000e{WO\u001c;\u0011\u0005Mz\u0014B\u0001!\u000f\u0005\rIe\u000e^\u0001\tQ>|7NT1nKR\tq$A\u0003baBd\u0017\u0010\u0006\u0002-\u000b\"1aI\u0003CA\u0002\u001d\u000bAAY8esB\u00191\u0007\u0013&\n\u0005%s!\u0001\u0003\u001fcs:\fW.\u001a \u0011\u0005MZ\u0015B\u0001'\u000f\u0005\u0011)f.\u001b;")
public class ShutdownHookThread
extends Thread {
    public static ShutdownHookThread apply(Function0<BoxedUnit> body2) {
        return ShutdownHookThread$.MODULE$.apply(body2);
    }

    public boolean remove() {
        return Runtime.getRuntime().removeShutdownHook(this);
    }

    public ShutdownHookThread(Runnable runnable2, String name) {
        super(runnable2, name);
    }
}

