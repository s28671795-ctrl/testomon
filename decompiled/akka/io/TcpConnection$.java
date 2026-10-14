/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import java.io.IOException;
import scala.Function0;
import scala.runtime.BoxedUnit;
import scala.util.control.NoStackTrace;

public final class TcpConnection$ {
    public static final TcpConnection$ MODULE$ = new TcpConnection$();
    private static final Function0<BoxedUnit> doNothing = () -> {};
    private static final IOException DroppingWriteBecauseWritingIsSuspendedException = new NoStackTrace(){

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
    private static final IOException DroppingWriteBecauseQueueIsFullException = new NoStackTrace(){

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

    public Function0<BoxedUnit> doNothing() {
        return doNothing;
    }

    public IOException DroppingWriteBecauseWritingIsSuspendedException() {
        return DroppingWriteBecauseWritingIsSuspendedException;
    }

    public IOException DroppingWriteBecauseQueueIsFullException() {
        return DroppingWriteBecauseQueueIsFullException;
    }

    private TcpConnection$() {
    }
}

