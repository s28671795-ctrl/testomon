/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRefScope;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y1\u0001b\u0001\u0003\u0011\u0002\u0007\u0005a\u0001\u0003\u0005\u0006'\u0001!\t!\u0006\u0005\u00063\u0001!)A\u0007\u0002\t\u0019>\u001c\u0017\r\u001c*fM*\u0011QAB\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u000f\u0005!\u0011m[6b'\r\u0001\u0011b\u0004\t\u0003\u00155i\u0011a\u0003\u0006\u0002\u0019\u0005)1oY1mC&\u0011ab\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0005A\tR\"\u0001\u0003\n\u0005I!!!D!di>\u0014(+\u001a4TG>\u0004X-\u0001\u0004%S:LG\u000fJ\u0002\u0001)\u00051\u0002C\u0001\u0006\u0018\u0013\tA2B\u0001\u0003V]&$\u0018aB5t\u0019>\u001c\u0017\r\\\u000b\u00027A\u0011!\u0002H\u0005\u0003;-\u0011qAQ8pY\u0016\fg\u000e")
public interface LocalRef
extends ActorRefScope {
    public static /* synthetic */ boolean isLocal$(LocalRef $this) {
        return $this.isLocal();
    }

    @Override
    default public boolean isLocal() {
        return true;
    }

    public static void $init$(LocalRef $this) {
    }
}

