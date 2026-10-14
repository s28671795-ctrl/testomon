/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Cancellable$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2qAC\u0006\u0011\u0002G\u0005\u0001\u0003C\u0003\u0018\u0001\u0019\u0005\u0001\u0004C\u0003\u001d\u0001\u0019\u0005QdB\u0003\u001f\u0017!\u0005qDB\u0003\u000b\u0017!\u0005\u0011\u0005C\u0003#\t\u0011\u00051\u0005C\u0004%\t\t\u0007I\u0011A\u0013\t\r\u001d\"\u0001\u0015!\u0003'\u0011!ACA1A\u0005\u00025)\u0003BB\u0015\u0005A\u0003%aEA\u0006DC:\u001cW\r\u001c7bE2,'B\u0001\u0007\u000e\u0003\u0015\t7\r^8s\u0015\u0005q\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001#A\u0011!#F\u0007\u0002')\tA#A\u0003tG\u0006d\u0017-\u0003\u0002\u0017'\t1\u0011I\\=SK\u001a\faaY1oG\u0016dG#A\r\u0011\u0005IQ\u0012BA\u000e\u0014\u0005\u001d\u0011un\u001c7fC:\f1\"[:DC:\u001cW\r\u001c7fIV\t\u0011$A\u0006DC:\u001cW\r\u001c7bE2,\u0007C\u0001\u0011\u0005\u001b\u0005Y1C\u0001\u0003\u0012\u0003\u0019a\u0014N\\5u}Q\tq$\u0001\tbYJ,\u0017\rZ=DC:\u001cW\r\u001c7fIV\ta\u0005\u0005\u0002!\u0001\u0005\t\u0012\r\u001c:fC\u0012L8)\u00198dK2dW\r\u001a\u0011\u0002'%t\u0017\u000e^5bY:{GoQ1oG\u0016dG.\u001a3\u0002)%t\u0017\u000e^5bY:{GoQ1oG\u0016dG.\u001a3!Q\tI1\u0006\u0005\u0002-_5\tQF\u0003\u0002/\u001b\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Aj#aC%oi\u0016\u0014h.\u00197Ba&\u0004")
public interface Cancellable {
    public static Cancellable alreadyCancelled() {
        return Cancellable$.MODULE$.alreadyCancelled();
    }

    public boolean cancel();

    public boolean isCancelled();
}

