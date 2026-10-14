/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2\u0001\u0002B\u0003\u0011\u0002\u0007\u0005!\"\r\u0005\u0006#\u0001!\tA\u0005\u0005\u0006-\u0001!\ta\u0006\u0005\bI\u0001\t\n\u0011\"\u0001&\u0005M\u00196-\u00197b\u0003\u000e$xN]*fY\u0016\u001cG/[8o\u0015\t1q!A\u0003bGR|'OC\u0001\t\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001a\u0003\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005\u0019\u0002C\u0001\u0007\u0015\u0013\t)RB\u0001\u0003V]&$\u0018!\u0002\u0013cC:<GC\u0001\r )\t\u0019\u0012\u0004C\u0004\u001b\u0005A\u0005\t9A\u000e\u0002\rM,g\u000eZ3s!\taR$D\u0001\u0006\u0013\tqRA\u0001\u0005BGR|'OU3g\u0011\u0015\u0001#\u00011\u0001\"\u0003\ri7o\u001a\t\u0003\u0019\tJ!aI\u0007\u0003\u0007\u0005s\u00170A\b%E\u0006tw\r\n3fM\u0006,H\u000e\u001e\u00133)\t1\u0003G\u000b\u0002\u001cO-\n\u0001\u0006\u0005\u0002*]5\t!F\u0003\u0002,Y\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0003[5\t!\"\u00198o_R\fG/[8o\u0013\ty#FA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016DQ\u0001I\u0002A\u0002\u0005\u00122A\r\u001b6\r\u0011\u0019\u0004\u0001A\u0019\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0005q\u0001\u0001C\u0001\u000f7\u0013\t9TA\u0001\bBGR|'oU3mK\u000e$\u0018n\u001c8")
public interface ScalaActorSelection {
    public static /* synthetic */ void $bang$(ScalaActorSelection $this, Object msg, ActorRef sender) {
        $this.$bang(msg, sender);
    }

    default public void $bang(Object msg, ActorRef sender) {
        ((ActorSelection)((Object)this)).tell(msg, sender);
    }

    public static /* synthetic */ ActorRef $bang$default$2$(ScalaActorSelection $this, Object msg) {
        return $this.$bang$default$2(msg);
    }

    default public ActorRef $bang$default$2(Object msg) {
        return Actor$.MODULE$.noSender();
    }

    public static void $init$(ScalaActorSelection $this) {
    }
}

