/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.internal;

import akka.annotation.InternalApi;
import akka.dispatch.internal.ScalaBatchable$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=:a\u0001B\u0003\t\u0002%YaAB\u0007\u0006\u0011\u0003Ia\u0002C\u0003\u0016\u0003\u0011\u0005q\u0003C\u0003\u0019\u0003\u0011\u0005\u0011$\u0001\bTG\u0006d\u0017MQ1uG\"\f'\r\\3\u000b\u0005\u00199\u0011\u0001C5oi\u0016\u0014h.\u00197\u000b\u0005!I\u0011\u0001\u00033jgB\fGo\u00195\u000b\u0003)\tA!Y6lCB\u0011A\"A\u0007\u0002\u000b\tq1kY1mC\n\u000bGo\u00195bE2,7CA\u0001\u0010!\t\u00012#D\u0001\u0012\u0015\u0005\u0011\u0012!B:dC2\f\u0017B\u0001\u000b\u0012\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\f\u0003-I7OQ1uG\"\f'\r\\3\u0015\u0005ii\u0002C\u0001\t\u001c\u0013\ta\u0012CA\u0004C_>dW-\u00198\t\u000by\u0019\u0001\u0019A\u0010\u0002\u0011I,hN\\1cY\u0016\u0004\"\u0001I\u0013\u000e\u0003\u0005R!AI\u0012\u0002\t1\fgn\u001a\u0006\u0002I\u0005!!.\u0019<b\u0013\t1\u0013E\u0001\u0005Sk:t\u0017M\u00197fQ\t\t\u0001\u0006\u0005\u0002*Y5\t!F\u0003\u0002,\u0013\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00055R#aC%oi\u0016\u0014h.\u00197Ba&D#\u0001\u0001\u0015")
@InternalApi
public final class ScalaBatchable {
    public static boolean isBatchable(Runnable runnable2) {
        return ScalaBatchable$.MODULE$.isBatchable(runnable2);
    }
}

