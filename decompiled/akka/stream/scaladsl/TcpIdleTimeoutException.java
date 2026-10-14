/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import java.util.concurrent.TimeoutException;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0003\u0019!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005\u0003\u0005-\u0001\t\u0005\t\u0015!\u0003.\u0011\u0015Q\u0004\u0001\"\u0001<\u0005]!6\r]%eY\u0016$\u0016.\\3pkR,\u0005pY3qi&|gN\u0003\u0002\u0007\u000f\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\t\u0013\u000511\u000f\u001e:fC6T\u0011AC\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001iq\u0003\u0005\u0002\u000f+5\tqB\u0003\u0002\u0011#\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005I\u0019\u0012\u0001B;uS2T\u0011\u0001F\u0001\u0005U\u00064\u0018-\u0003\u0002\u0017\u001f\t\u0001B+[7f_V$X\t_2faRLwN\u001c\t\u00031yi\u0011!\u0007\u0006\u00035m\tqaY8oiJ|GN\u0003\u0002\u00139)\tQ$A\u0003tG\u0006d\u0017-\u0003\u0002 3\taaj\\*uC\u000e\\GK]1dK\u0006\u0019Qn]4\u0011\u0005\tJcBA\u0012(!\t!C$D\u0001&\u0015\t13\"\u0001\u0004=e>|GOP\u0005\u0003Qq\ta\u0001\u0015:fI\u00164\u0017B\u0001\u0016,\u0005\u0019\u0019FO]5oO*\u0011\u0001\u0006H\u0001\bi&lWm\\;u!\tq#'D\u00010\u0015\t\u0001\u0014'\u0001\u0005ekJ\fG/[8o\u0015\t\u0001B$\u0003\u00024_\tAA)\u001e:bi&|g\u000e\u000b\u0002\u0003kA\u0011a\u0007O\u0007\u0002o)\u0011!#C\u0005\u0003s]\u0012a!\u001e8vg\u0016$\u0017A\u0002\u001fj]&$h\bF\u0002=}}\u0002\"!\u0010\u0001\u000e\u0003\u0015AQ\u0001I\u0002A\u0002\u0005BQ\u0001L\u0002A\u00025B#aP\u001b")
public final class TcpIdleTimeoutException
extends TimeoutException
implements NoStackTrace {
    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public TcpIdleTimeoutException(String msg, Duration timeout2) {
        super(msg);
        NoStackTrace.$init$(this);
    }
}

