/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Concat$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00113\u0001\u0002B\u0003\u0011\u0002\u0007\u0005\u0012b\u0003\u0005\u0006%\u0001!\t\u0001\u0006\u0005\u00061\u0001!\t!\u0007\u0005\u0006=\u0001!\ta\b\u0002\n)J\fg/\u001a:tC2T!AB\u0004\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u0011%\taa\u001d;sK\u0006l'\"\u0001\u0006\u0002\t\u0005\\7.Y\n\u0003\u00011\u0001\"!\u0004\t\u000e\u00039Q\u0011aD\u0001\u0006g\u000e\fG.Y\u0005\u0003#9\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$He\u0001\u0001\u0015\u0003U\u0001\"!\u0004\f\n\u0005]q!\u0001B+oSR\faaY8oG\u0006$HC\u0001\u000e\u001d!\tY\u0002!D\u0001\u0006\u0011\u0015i\"\u00011\u0001\u001b\u0003\u0011!\b.\u0019;\u0002\u001bI,w/\u001b:f\r&\u00148\u000f\u001e+p)\tQ\u0002\u0005C\u0003\"\u0007\u0001\u0007!%\u0001\bsK2\fG/\u001b<f\u001f\u001a47/\u001a;\u0011\u00055\u0019\u0013B\u0001\u0013\u000f\u0005\rIe\u000e\u001e\u0015\u0003A\u0019\u0002\"a\n\u0016\u000e\u0003!R!!K\u0005\u0002\tU$\u0018\u000e\\\u0005\u0003W!\u0012a!\u001e8vg\u0016$\u0017&\u0003\u0001._E\u001aTgN\u001d<\u0013\tqSA\u0001\u0004D_:\u001c\u0017\r\u001e\u0006\u0003a\u0015\ta\"R7qif$&/\u0019<feN\fG.\u0003\u00023\u000b\tYQI\u001c;fe&\u001bH.\u00198e\u0015\t!T!\u0001\u0006Fq&$\u0018j\u001d7b]\u0012L!AN\u0003\u0003#5\u000bG/\u001a:jC2L'0Z!u_6L7-\u0003\u00029\u000b\t\u0019R*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3Pa*\u0011!(B\u0001\u000e!>\u0004\u0018\t\u001e;sS\n,H/Z:\n\u0005q*!A\u0004)vg\"\fE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0001y\u0002\"a\u0010\"\u000e\u0003\u0001S!!Q\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002D\u0001\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public interface Traversal {
    public static /* synthetic */ Traversal concat$(Traversal $this, Traversal that) {
        return $this.concat(that);
    }

    default public Traversal concat(Traversal that) {
        return Concat$.MODULE$.normalizeConcat(this, that);
    }

    public static /* synthetic */ Traversal rewireFirstTo$(Traversal $this, int relativeOffset) {
        return $this.rewireFirstTo(relativeOffset);
    }

    default public Traversal rewireFirstTo(int relativeOffset) {
        return null;
    }

    public static void $init$(Traversal $this) {
    }
}

