/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005}1AAA\u0002\u0003\u0011!)1\u0004\u0001C\u00019\tqBk\\8NC:L8+\u001e2tiJ,\u0017-\\:Pa\u0016tW\t_2faRLwN\u001c\u0006\u0003\t\u0015\taa\u001d;sK\u0006l'\"\u0001\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001\u0011\"\u0005\t\u0003\u0015=i\u0011a\u0003\u0006\u0003\u00195\tA\u0001\\1oO*\ta\"\u0001\u0003kCZ\f\u0017B\u0001\t\f\u0005UIE\u000e\\3hC2\u001cF/\u0019;f\u000bb\u001cW\r\u001d;j_:\u0004\"AE\r\u000e\u0003MQ!\u0001F\u000b\u0002\u000f\r|g\u000e\u001e:pY*\u0011acF\u0001\u0005kRLGNC\u0001\u0019\u0003\u0015\u00198-\u00197b\u0013\tQ2C\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW-\u0001\u0004=S:LGO\u0010\u000b\u0002;A\u0011a\u0004A\u0007\u0002\u0007\u0001")
public final class TooManySubstreamsOpenException
extends IllegalStateException
implements NoStackTrace {
    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public TooManySubstreamsOpenException() {
        super("Cannot open a new substream as there are too many substreams open");
        NoStackTrace.$init$(this);
    }
}

