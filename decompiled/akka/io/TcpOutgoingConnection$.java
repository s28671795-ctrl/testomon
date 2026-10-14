/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.annotation.InternalApi;
import java.net.ConnectException;
import scala.Option;
import scala.concurrent.duration.FiniteDuration;
import scala.util.control.NoStackTrace;

@InternalApi
public final class TcpOutgoingConnection$ {
    public static final TcpOutgoingConnection$ MODULE$ = new TcpOutgoingConnection$();
    private static final ConnectException FinishConnectNeverReturnedTrueException = new NoStackTrace(){

        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }
        {
            NoStackTrace.$init$(this);
        }
    };

    public ConnectException FinishConnectNeverReturnedTrueException() {
        return FinishConnectNeverReturnedTrueException;
    }

    public ConnectException connectTimeoutExpired(Option<FiniteDuration> timeout2) {
        return new NoStackTrace(timeout2){

            public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                return super.fillInStackTrace();
            }

            public Throwable fillInStackTrace() {
                return NoStackTrace.fillInStackTrace$(this);
            }
            {
                NoStackTrace.$init$(this);
            }
        };
    }

    private TcpOutgoingConnection$() {
    }
}

