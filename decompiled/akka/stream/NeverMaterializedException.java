/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r2A\u0001B\u0003\u0003\u0015!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003\u001e\u0001\u0011\u0005a\u0004C\u0003\u001e\u0001\u0011\u0005!E\u0001\u000eOKZ,'/T1uKJL\u0017\r\\5{K\u0012,\u0005pY3qi&|gN\u0003\u0002\u0007\u000f\u000511\u000f\u001e:fC6T\u0011\u0001C\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001Y\u0001C\u0001\u0007\u0017\u001d\ti1C\u0004\u0002\u000f#5\tqB\u0003\u0002\u0011\u0013\u00051AH]8pizJ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)U\tq\u0001]1dW\u0006<WMC\u0001\u0013\u0013\t9\u0002D\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u0011A#F\u0001\u0006G\u0006,8/\u001a\t\u0003\u0019mI!\u0001\b\r\u0003\u0013QC'o\\<bE2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002 CA\u0011\u0001\u0005A\u0007\u0002\u000b!)\u0011D\u0001a\u00015Q\tq\u0004")
public final class NeverMaterializedException
extends RuntimeException {
    public NeverMaterializedException(Throwable cause) {
        super("Downstream canceled without triggering lazy source materialization", cause);
    }

    public NeverMaterializedException() {
        this((Throwable)null);
    }
}

