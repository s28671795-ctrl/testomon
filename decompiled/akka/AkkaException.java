/*
 * Decompiled with CFR 0.152.
 */
package akka;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2A!\u0002\u0004\u0001\u0013!A1\u0004\u0001B\u0001B\u0003%A\u0004\u0003\u0005%\u0001\t\u0005\t\u0015!\u0003&\u0011\u0015A\u0003\u0001\"\u0001*\u0011\u0015A\u0003\u0001\"\u0001/\u00055\t5n[1Fq\u000e,\u0007\u000f^5p]*\tq!\u0001\u0003bW.\f7\u0001A\n\u0004\u0001)A\u0002CA\u0006\u0016\u001d\ta!C\u0004\u0002\u000e!5\taB\u0003\u0002\u0010\u0011\u00051AH]8pizJ\u0011!E\u0001\u0006g\u000e\fG.Y\u0005\u0003'Q\tq\u0001]1dW\u0006<WMC\u0001\u0012\u0013\t1rC\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u00111\u0003\u0006\t\u0003\u0017eI!AG\f\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\u000f5,7o]1hKB\u0011Q$\t\b\u0003=}\u0001\"!\u0004\u000b\n\u0005\u0001\"\u0012A\u0002)sK\u0012,g-\u0003\u0002#G\t11\u000b\u001e:j]\u001eT!\u0001\t\u000b\u0002\u000b\r\fWo]3\u0011\u0005-1\u0013BA\u0014\u0018\u0005%!\u0006N]8xC\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0004U1j\u0003CA\u0016\u0001\u001b\u00051\u0001\"B\u000e\u0004\u0001\u0004a\u0002\"\u0002\u0013\u0004\u0001\u0004)CC\u0001\u00160\u0011\u0015\u0001D\u00011\u0001\u001d\u0003\ri7o\u001a\u0015\u0005\u0001I2t\u0007\u0005\u00024i5\tA#\u0003\u00026)\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0001")
public class AkkaException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AkkaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AkkaException(String msg) {
        this(msg, null);
    }
}

