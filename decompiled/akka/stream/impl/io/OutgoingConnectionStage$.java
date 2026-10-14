/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.io.Inet;
import java.net.InetSocketAddress;
import scala.None$;
import scala.Option;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;

public final class OutgoingConnectionStage$ {
    public static final OutgoingConnectionStage$ MODULE$ = new OutgoingConnectionStage$();

    public Option<InetSocketAddress> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    public Iterable<Inet.SocketOption> $lessinit$greater$default$4() {
        return Nil$.MODULE$;
    }

    public boolean $lessinit$greater$default$5() {
        return true;
    }

    public Duration $lessinit$greater$default$6() {
        return Duration$.MODULE$.Inf();
    }

    private OutgoingConnectionStage$() {
    }
}

