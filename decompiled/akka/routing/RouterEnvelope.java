/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.WrappedMessage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i1qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0016\u0001\u0019\u0005aC\u0001\bS_V$XM]#om\u0016dw\u000e]3\u000b\u0005\u0011)\u0011a\u0002:pkRLgn\u001a\u0006\u0002\r\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u0005\u0010!\tQQ\"D\u0001\f\u0015\u0005a\u0011!B:dC2\f\u0017B\u0001\b\f\u0005\u0019\te.\u001f*fMB\u0011\u0001cE\u0007\u0002#)\u0011!#B\u0001\u0006C\u000e$xN]\u0005\u0003)E\u0011ab\u0016:baB,G-T3tg\u0006<W-A\u0004nKN\u001c\u0018mZ3\u0016\u0003]\u0001\"A\u0003\r\n\u0005eY!aA!os\u0002")
public interface RouterEnvelope
extends WrappedMessage {
    @Override
    public Object message();
}

