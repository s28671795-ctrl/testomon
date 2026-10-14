/*
 * Decompiled with CFR 0.152.
 */
package akka.event.jul;

import akka.event.jul.Logger$;
import java.util.logging.Logger;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u0003\u0013\u0001\u0011\u00051\u0003\u0003\u0005\u0018\u0001!\u0015\r\u0011\"\u0001\u0019\u0005-Q\u0015M^1M_\u001e<\u0017N\\4\u000b\u0005\u00151\u0011a\u00016vY*\u0011q\u0001C\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u0013\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0007\u0011\u00055\u0001R\"\u0001\b\u000b\u0003=\tQa]2bY\u0006L!!\u0005\b\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\tA\u0003\u0005\u0002\u000e+%\u0011aC\u0004\u0002\u0005+:LG/A\u0002m_\u001e,\u0012!\u0007\t\u00035\u0005j\u0011a\u0007\u0006\u00039u\tq\u0001\\8hO&twM\u0003\u0002\u001f?\u0005!Q\u000f^5m\u0015\u0005\u0001\u0013\u0001\u00026bm\u0006L!AI\u000e\u0003\r1{wmZ3sQ\t\u0011A\u0005\u0005\u0002\u000eK%\u0011aE\u0004\u0002\niJ\fgn]5f]RDc\u0001\u0001\u0015,Y9z\u0003CA\u0007*\u0013\tQcB\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001.\u00039*6/\u001a\u0011T\u0019\u001a#$\nI8sA\u0011L'/Z2uA)\fg/\u0019\u0018vi&dg\u0006\\8hO&tw\rI5ogR,\u0017\r\u001a\u0018\u0002\u000bMLgnY3\"\u0003A\nQA\r\u00187]A\u0002")
public interface JavaLogging {
    public static /* synthetic */ Logger log$(JavaLogging $this) {
        return $this.log();
    }

    default public Logger log() {
        return Logger$.MODULE$.apply(this.getClass().getName());
    }

    public static void $init$(JavaLogging $this) {
    }
}

