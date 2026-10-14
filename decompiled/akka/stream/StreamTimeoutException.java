/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import java.util.concurrent.TimeoutException;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\t3Aa\u0001\u0003\u0011\u0013!AA\u0005\u0001B\u0001B\u0003%Q\u0005C\u0003.\u0001\u0011\u0005aF\u0001\fTiJ,\u0017-\u001c+j[\u0016|W\u000f^#yG\u0016\u0004H/[8o\u0015\t)a!\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u000f\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\u0006\u001d!\tY\u0011D\u0004\u0002\r-9\u0011Qb\u0005\b\u0003\u001dEi\u0011a\u0004\u0006\u0003!!\ta\u0001\u0010:p_Rt\u0014\"\u0001\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q)\u0012AC2p]\u000e,(O]3oi*\t!#\u0003\u0002\u00181\u00059\u0001/Y2lC\u001e,'B\u0001\u000b\u0016\u0013\tQ2D\u0001\tUS6,w.\u001e;Fq\u000e,\u0007\u000f^5p]*\u0011q\u0003\u0007\t\u0003;\tj\u0011A\b\u0006\u0003?\u0001\nqaY8oiJ|GN\u0003\u0002\"+\u0005!Q\u000f^5m\u0013\t\u0019cD\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW-A\u0002ng\u001e\u0004\"A\n\u0016\u000f\u0005\u001dB\u0003C\u0001\b\u0016\u0013\tIS#\u0001\u0004Qe\u0016$WMZ\u0005\u0003W1\u0012aa\u0015;sS:<'BA\u0015\u0016\u0003\u0019a\u0014N\\5u}Q\u0011q&\r\t\u0003a\u0001i\u0011\u0001\u0002\u0005\u0006I\t\u0001\r!J\u0015\u0006\u0001M*t'O\u0005\u0003i\u0011\u0011ADQ1dWB\u0014Xm]:ve\u0016$\u0016.\\3pkR,\u0005pY3qi&|g.\u0003\u00027\t\tQ2i\\7qY\u0016$\u0018n\u001c8US6,w.\u001e;Fq\u000e,\u0007\u000f^5p]&\u0011\u0001\b\u0002\u0002\u0018\u0013:LG/[1m)&lWm\\;u\u000bb\u001cW\r\u001d;j_:L!A\u000f\u0003\u00035M#(/Z1n\u0013\u0012dW\rV5nK>,H/\u0012=dKB$\u0018n\u001c8)\u0005\u0001a\u0004CA\u001fA\u001b\u0005q$BA \u0007\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u0003z\u0012A\u0002R8O_RLe\u000e[3sSR\u0004")
@DoNotInherit
public class StreamTimeoutException
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

    public StreamTimeoutException(String msg) {
        super(msg);
        NoStackTrace.$init$(this);
    }
}

