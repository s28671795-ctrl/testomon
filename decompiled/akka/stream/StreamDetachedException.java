/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005A2A\u0001B\u0003\u0003\u0015!A\u0011\u0005\u0001B\u0001B\u0003%!\u0005C\u0003+\u0001\u0011\u00051\u0006C\u0003+\u0001\u0011\u0005qFA\fTiJ,\u0017-\u001c#fi\u0006\u001c\u0007.\u001a3Fq\u000e,\u0007\u000f^5p]*\u0011aaB\u0001\u0007gR\u0014X-Y7\u000b\u0003!\tA!Y6lC\u000e\u00011c\u0001\u0001\f3A\u0011AB\u0006\b\u0003\u001bMq!AD\t\u000e\u0003=Q!\u0001E\u0005\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012!B:dC2\f\u0017B\u0001\u000b\u0016\u0003\u001d\u0001\u0018mY6bO\u0016T\u0011AE\u0005\u0003/a\u0011\u0001CU;oi&lW-\u0012=dKB$\u0018n\u001c8\u000b\u0005Q)\u0002C\u0001\u000e \u001b\u0005Y\"B\u0001\u000f\u001e\u0003\u001d\u0019wN\u001c;s_2T!AH\u000b\u0002\tU$\u0018\u000e\\\u0005\u0003Am\u0011ABT8Ti\u0006\u001c7\u000e\u0016:bG\u0016\fq!\\3tg\u0006<W\r\u0005\u0002$O9\u0011A%\n\t\u0003\u001dUI!AJ\u000b\u0002\rA\u0013X\rZ3g\u0013\tA\u0013F\u0001\u0004TiJLgn\u001a\u0006\u0003MU\ta\u0001P5oSRtDC\u0001\u0017/!\ti\u0003!D\u0001\u0006\u0011\u0015\t#\u00011\u0001#)\u0005a\u0003")
public final class StreamDetachedException
extends RuntimeException
implements NoStackTrace {
    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public StreamDetachedException(String message) {
        super(message);
        NoStackTrace.$init$(this);
    }

    public StreamDetachedException() {
        this("Stream is terminated. Materialized value is detached.");
    }
}

