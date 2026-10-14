/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.AkkaException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%2Aa\u0001\u0003\u0001\u0013!Aa\u0002\u0001B\u0001B\u0003%q\u0002\u0003\u0004\u001e\u0001\u0011\u0005aA\b\u0002\u001a\u0003\u000e$xN]%oi\u0016\u0014(/\u001e9uK\u0012,\u0005pY3qi&|gN\u0003\u0002\u0006\r\u0005)\u0011m\u0019;pe*\tq!\u0001\u0003bW.\f7\u0001A\n\u0003\u0001)\u0001\"a\u0003\u0007\u000e\u0003\u0019I!!\u0004\u0004\u0003\u001b\u0005[7.Y#yG\u0016\u0004H/[8o\u0003\u0015\u0019\u0017-^:f!\t\u0001\"D\u0004\u0002\u0012/9\u0011!#F\u0007\u0002')\u0011A\u0003C\u0001\u0007yI|w\u000e\u001e \n\u0003Y\tQa]2bY\u0006L!\u0001G\r\u0002\u000fA\f7m[1hK*\ta#\u0003\u0002\u001c9\tIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u00031e\ta\u0001P5oSRtDCA\u0010\"!\t\u0001\u0003!D\u0001\u0005\u0011\u0015q!\u00011\u0001\u0010Q\u0011\u00011e\n\u0015\u0011\u0005\u0011*S\"A\r\n\u0005\u0019J\"\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001")
public class ActorInterruptedException
extends AkkaException {
    private static final long serialVersionUID = 1L;

    public ActorInterruptedException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}

