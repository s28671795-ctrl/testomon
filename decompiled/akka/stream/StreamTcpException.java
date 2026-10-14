/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u000592Aa\u0001\u0003\u0001\u0013!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005C\u0003*\u0001\u0011\u0005!F\u0001\nTiJ,\u0017-\u001c+da\u0016C8-\u001a9uS>t'BA\u0003\u0007\u0003\u0019\u0019HO]3b[*\tq!\u0001\u0003bW.\f7\u0001A\n\u0004\u0001)A\u0002CA\u0006\u0016\u001d\ta!C\u0004\u0002\u000e!5\taB\u0003\u0002\u0010\u0011\u00051AH]8pizJ\u0011!E\u0001\u0006g\u000e\fG.Y\u0005\u0003'Q\tq\u0001]1dW\u0006<WMC\u0001\u0012\u0013\t1rC\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u00111\u0003\u0006\t\u00033yi\u0011A\u0007\u0006\u00037q\tqaY8oiJ|GN\u0003\u0002\u001e)\u0005!Q\u000f^5m\u0013\ty\"D\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW-A\u0002ng\u001e\u0004\"A\t\u0014\u000f\u0005\r\"\u0003CA\u0007\u0015\u0013\t)C#\u0001\u0004Qe\u0016$WMZ\u0005\u0003O!\u0012aa\u0015;sS:<'BA\u0013\u0015\u0003\u0019a\u0014N\\5u}Q\u00111&\f\t\u0003Y\u0001i\u0011\u0001\u0002\u0005\u0006A\t\u0001\r!\t")
public class StreamTcpException
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

    public StreamTcpException(String msg) {
        super(msg);
        NoStackTrace.$init$(this);
    }
}

