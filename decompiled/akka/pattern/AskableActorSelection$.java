/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.ActorSelection;
import akka.actor.InternalActorRef;
import akka.pattern.AskableActorRef$;
import akka.pattern.AskableActorSelection;
import akka.pattern.PromiseActorRef$;
import akka.util.ByteString$;
import akka.util.Timeout;
import java.net.URLEncoder;
import scala.concurrent.Future;
import scala.concurrent.Future$;

public final class AskableActorSelection$ {
    public static final AskableActorSelection$ MODULE$ = new AskableActorSelection$();

    public final Future<Object> ask$extension(ActorSelection $this, Object message, Timeout timeout2) {
        return this.internalAsk$extension($this, message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public final Future<Object> ask$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final ActorRef ask$default$3$extension(ActorSelection $this, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public final Future<Object> $qmark$extension(ActorSelection $this, Object message, Timeout timeout2) {
        return this.internalAsk$extension($this, message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public final Future<Object> $qmark$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final Future<Object> internalAsk$extension(ActorSelection $this, Object message, Timeout timeout2, ActorRef sender) {
        ActorRef actorRef2 = $this.anchor();
        if (actorRef2 instanceof InternalActorRef) {
            InternalActorRef internalActorRef = (InternalActorRef)actorRef2;
            if (timeout2.duration().length() <= 0L) {
                return Future$.MODULE$.failed(AskableActorRef$.MODULE$.negativeTimeoutException($this, message, sender));
            }
            String refPrefix = URLEncoder.encode($this.pathString().replace("/", "_"), ByteString$.MODULE$.UTF_8());
            return PromiseActorRef$.MODULE$.apply(internalActorRef.provider(), timeout2, $this, message.getClass().getName(), refPrefix, sender, PromiseActorRef$.MODULE$.apply$default$7()).ask($this, message, timeout2);
        }
        return Future$.MODULE$.failed(AskableActorRef$.MODULE$.unsupportedRecipientType($this, message, sender));
    }

    public final ActorRef $qmark$default$3$extension(ActorSelection $this, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public final int hashCode$extension(ActorSelection $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(ActorSelection $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof AskableActorSelection)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorSelection actorSelection = x$1 == null ? null : ((AskableActorSelection)x$1).actorSel();
        ActorSelection actorSelection2 = $this;
        ActorSelection actorSelection3 = actorSelection;
        if (actorSelection2 != null) {
            if (!((Object)actorSelection2).equals(actorSelection3)) return false;
            return true;
        }
        if (actorSelection3 == null) return true;
        return false;
    }

    private AskableActorSelection$() {
    }
}

