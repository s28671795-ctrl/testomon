/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.EmptyTraversal$;
import akka.stream.impl.Traversal;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d:a\u0001B\u0003\t\u0002%YaAB\u0007\u0006\u0011\u0003Ia\u0002C\u0003\u0019\u0003\u0011\u0005!\u0004C\u0003\u001c\u0003\u0011\u0005C$\u0001\bF[B$\u0018\u0010\u0016:bm\u0016\u00148/\u00197\u000b\u0005\u00199\u0011\u0001B5na2T!\u0001C\u0005\u0002\rM$(/Z1n\u0015\u0005Q\u0011\u0001B1lW\u0006\u0004\"\u0001D\u0001\u000e\u0003\u0015\u0011a\"R7qif$&/\u0019<feN\fGnE\u0002\u0002\u001fU\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0007C\u0001\u0007\u0017\u0013\t9RAA\u0005Ue\u00064XM]:bY\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\f\u0003\u0019\u0019wN\\2biR\u0011Q#\b\u0005\u0006=\r\u0001\r!F\u0001\u0005i\"\fG\u000f\u000b\u0002\u0002AA\u0011\u0011\u0005J\u0007\u0002E)\u00111%C\u0001\u000bC:tw\u000e^1uS>t\u0017BA\u0013#\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u0001\u0001\u0003")
@InternalApi
public final class EmptyTraversal {
    public static Traversal concat(Traversal that) {
        return EmptyTraversal$.MODULE$.concat(that);
    }

    public static Traversal rewireFirstTo(int relativeOffset) {
        return EmptyTraversal$.MODULE$.rewireFirstTo(relativeOffset);
    }
}

