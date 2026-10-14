/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.Graph;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.TcpIdleTimeoutException;
import akka.util.ByteString;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;

@InternalApi
public final class TcpIdleTimeout$ {
    public static final TcpIdleTimeout$ MODULE$ = new TcpIdleTimeout$();

    public BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> apply(FiniteDuration idleTimeout, Option<InetSocketAddress> remoteAddress) {
        String string;
        Option<InetSocketAddress> option = remoteAddress;
        if (option instanceof Some) {
            Some some = (Some)option;
            InetSocketAddress address = (InetSocketAddress)some.value();
            string = new StringBuilder(20).append(" on connection to [").append(address).append("]").toString();
        } else {
            string = "";
        }
        String connectionToString = string;
        BidiFlow toNetTimeout = BidiFlow$.MODULE$.fromFlows((Graph)((Object)Flow$.MODULE$.apply().mapError((PartialFunction<Throwable, Throwable>)((Object)new Serializable(connectionToString, idleTimeout){
            private static final long serialVersionUID = 0L;
            private final String connectionToString$1;
            private final FiniteDuration idleTimeout$1;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof TimeoutException) {
                    return (B1)new TcpIdleTimeoutException(new StringBuilder(58).append("TCP idle-timeout encountered").append(this.connectionToString$1).append(", no bytes passed in the last ").append(this.idleTimeout$1).toString(), this.idleTimeout$1);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                Throwable throwable = x1;
                return throwable instanceof TimeoutException;
            }
            {
                this.connectionToString$1 = connectionToString$1;
                this.idleTimeout$1 = idleTimeout$1;
            }
        }))), Flow$.MODULE$.apply());
        BidiFlow fromNetTimeout = toNetTimeout.reversed();
        return fromNetTimeout.atop(BidiFlow$.MODULE$.bidirectionalIdleTimeout(idleTimeout)).atop(toNetTimeout);
    }

    private TcpIdleTimeout$() {
    }
}

