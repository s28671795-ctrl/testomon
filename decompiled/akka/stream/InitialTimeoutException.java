/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamTimeoutException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012Aa\u0001\u0003\u0003\u0013!Aa\u0002\u0001B\u0001B\u0003%q\u0002C\u0003\u001d\u0001\u0011\u0005QDA\fJ]&$\u0018.\u00197US6,w.\u001e;Fq\u000e,\u0007\u000f^5p]*\u0011QAB\u0001\u0007gR\u0014X-Y7\u000b\u0003\u001d\tA!Y6lC\u000e\u00011C\u0001\u0001\u000b!\tYA\"D\u0001\u0005\u0013\tiAA\u0001\fTiJ,\u0017-\u001c+j[\u0016|W\u000f^#yG\u0016\u0004H/[8o\u0003\ri7o\u001a\t\u0003!eq!!E\f\u0011\u0005I)R\"A\n\u000b\u0005QA\u0011A\u0002\u001fs_>$hHC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tAR#\u0001\u0004Qe\u0016$WMZ\u0005\u00035m\u0011aa\u0015;sS:<'B\u0001\r\u0016\u0003\u0019a\u0014N\\5u}Q\u0011ad\b\t\u0003\u0017\u0001AQA\u0004\u0002A\u0002=\u0001")
public final class InitialTimeoutException
extends StreamTimeoutException {
    public InitialTimeoutException(String msg) {
        super(msg);
    }
}

