/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.NoRoutee$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQaF\u0001\u0005\u0002aAQ!G\u0001\u0005Bi\t\u0001BT8S_V$X-\u001a\u0006\u0003\r\u001d\tqA]8vi&twMC\u0001\t\u0003\u0011\t7n[1\u0004\u0001A\u00111\"A\u0007\u0002\u000b\tAaj\u001c*pkR,WmE\u0002\u0002\u001dQ\u0001\"a\u0004\n\u000e\u0003AQ\u0011!E\u0001\u0006g\u000e\fG.Y\u0005\u0003'A\u0011a!\u00118z%\u00164\u0007CA\u0006\u0016\u0013\t1RA\u0001\u0004S_V$X-Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003)\tAa]3oIR\u00191DH\u0012\u0011\u0005=a\u0012BA\u000f\u0011\u0005\u0011)f.\u001b;\t\u000b}\u0019\u0001\u0019\u0001\u0011\u0002\u000f5,7o]1hKB\u0011q\"I\u0005\u0003EA\u00111!\u00118z\u0011\u0015!3\u00011\u0001&\u0003\u0019\u0019XM\u001c3feB\u0011a%K\u0007\u0002O)\u0011\u0001fB\u0001\u0006C\u000e$xN]\u0005\u0003U\u001d\u0012\u0001\"Q2u_J\u0014VM\u001a")
public final class NoRoutee {
    public static void send(Object message, ActorRef sender) {
        NoRoutee$.MODULE$.send(message, sender);
    }
}

