/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.math.Equiv;
import scala.math.Equiv$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2\u0001b\u0001\u0003\u0011\u0002\u0007\u0005\u0011b\f\u0005\u0006\u001d\u0001!\ta\u0004\u0005\u0006'\u0001!\u0019\u0001\u0006\u0002\u0011\u0019><\bK]5pe&$\u00180R9vSZT!!\u0002\u0004\u0002\t5\fG\u000f\u001b\u0006\u0002\u000f\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u000b!\tYA\"D\u0001\u0007\u0013\tiaA\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003A\u0001\"aC\t\n\u0005I1!\u0001B+oSR\fa\"\u001e8jm\u0016\u00148/\u00197FcVLg/\u0006\u0002\u00169U\ta\u0003E\u0002\u00181ii\u0011\u0001B\u0005\u00033\u0011\u0011Q!R9vSZ\u0004\"a\u0007\u000f\r\u0001\u0011)QD\u0001b\u0001=\t\tA+\u0005\u0002 EA\u00111\u0002I\u0005\u0003C\u0019\u0011qAT8uQ&tw\r\u0005\u0002\fG%\u0011AE\u0002\u0002\u0004\u0003:L\bF\u0002\u0002'S)bS\u0006\u0005\u0002\fO%\u0011\u0001F\u0002\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002W\u0005\t)'V:fA\u0015D\b\u000f\\5dSR\u0004S)];jm:*h.\u001b<feN\fG\u000eI5ogR,\u0017\r\u001a\u0018!'\u0016,\u0007eU2bY\u0006$wn\u0019\u0011f]R\u0014\u0018\u0010\t4pe\u0002jwN]3!S:4wN]7bi&|gN\u000f\u0011iiR\u00048OO\u00180o^<hf]2bY\u0006lC.\u00198h]=\u0014xmL1qS>\u001aWO\u001d:f]R|3oY1mC>j\u0017\r\u001e50\u000bF,\u0018N\u001e\u0013/QRlGnI;oSZ,'o]1m\u000bF,\u0018N^.U;j\u001a8-\u00197b]5\fG\u000f\u001b\u0018FcVLgo\u0017+^\u0003\u0015\u0019\u0018N\\2fC\u0005q\u0013A\u0002\u001a/cMr\u0003G\u0004\u0002\u0018a%\u0011\u0011\u0007B\u0001\u0006\u000bF,\u0018N\u001e")
public interface LowPriorityEquiv {
    public static /* synthetic */ Equiv universalEquiv$(LowPriorityEquiv $this) {
        return $this.universalEquiv();
    }

    default public <T> Equiv<T> universalEquiv() {
        return ((Equiv$)this).universal();
    }

    public static void $init$(LowPriorityEquiv $this) {
    }
}

