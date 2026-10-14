/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import java.util.concurrent.TimeoutException;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u00013AAB\u0004\u0001\u0019!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005\u0003\u0005-\u0001\t\u0005\t\u0015!\u0003.\u0011\u00151\u0004\u0001\"\u00018\u0011\u00151\u0004\u0001\"\u0001=\u0011\u0015q\u0004\u0001\"\u0011@\u0005M\t5o\u001b+j[\u0016|W\u000f^#yG\u0016\u0004H/[8o\u0015\tA\u0011\"A\u0004qCR$XM\u001d8\u000b\u0003)\tA!Y6lC\u000e\u00011c\u0001\u0001\u000e/A\u0011a\"F\u0007\u0002\u001f)\u0011\u0001#E\u0001\u000bG>t7-\u001e:sK:$(B\u0001\n\u0014\u0003\u0011)H/\u001b7\u000b\u0003Q\tAA[1wC&\u0011ac\u0004\u0002\u0011)&lWm\\;u\u000bb\u001cW\r\u001d;j_:\u0004\"\u0001\u0007\u0010\u000e\u0003eQ!AG\u000e\u0002\u000f\r|g\u000e\u001e:pY*\u0011!\u0003\b\u0006\u0002;\u0005)1oY1mC&\u0011q$\u0007\u0002\r\u001d>\u001cF/Y2l)J\f7-Z\u0001\b[\u0016\u001c8/Y4f!\t\u0011\u0013F\u0004\u0002$OA\u0011A\u0005H\u0007\u0002K)\u0011aeC\u0001\u0007yI|w\u000e\u001e \n\u0005!b\u0012A\u0002)sK\u0012,g-\u0003\u0002+W\t11\u000b\u001e:j]\u001eT!\u0001\u000b\u000f\u0002\u000b\r\fWo]3\u0011\u00059\u001adBA\u00182\u001d\t!\u0003'C\u0001\u001e\u0013\t\u0011D$A\u0004qC\u000e\\\u0017mZ3\n\u0005Q*$!\u0003+ie><\u0018M\u00197f\u0015\t\u0011D$\u0001\u0004=S:LGO\u0010\u000b\u0004qiZ\u0004CA\u001d\u0001\u001b\u00059\u0001\"\u0002\u0011\u0004\u0001\u0004\t\u0003\"\u0002\u0017\u0004\u0001\u0004iCC\u0001\u001d>\u0011\u0015\u0001C\u00011\u0001\"\u0003!9W\r^\"bkN,G#A\u0017")
public class AskTimeoutException
extends TimeoutException
implements NoStackTrace {
    private final Throwable cause;

    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    public AskTimeoutException(String message, Throwable cause) {
        this.cause = cause;
        super(message);
        NoStackTrace.$init$(this);
    }

    public AskTimeoutException(String message) {
        this(message, null);
    }
}

