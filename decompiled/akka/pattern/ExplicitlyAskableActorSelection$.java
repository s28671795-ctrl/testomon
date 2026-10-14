/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.InternalActorRef;
import akka.pattern.AskableActorRef$;
import akka.pattern.ExplicitlyAskableActorSelection;
import akka.pattern.PromiseActorRef;
import akka.pattern.PromiseActorRef$;
import akka.util.ByteString$;
import akka.util.Timeout;
import java.net.URLEncoder;
import scala.Function1;
import scala.concurrent.Future;
import scala.concurrent.Future$;

public final class ExplicitlyAskableActorSelection$ {
    public static final ExplicitlyAskableActorSelection$ MODULE$ = new ExplicitlyAskableActorSelection$();

    public final Future<Object> ask$extension(ActorSelection $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final ActorRef ask$default$3$extension(ActorSelection $this, Function1<ActorRef, Object> message) {
        return Actor$.MODULE$.noSender();
    }

    public final Future<Object> $qmark$extension(ActorSelection $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final Future<Object> internalAsk$extension(ActorSelection $this, Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        ActorRef actorRef2 = $this.anchor();
        if (actorRef2 instanceof InternalActorRef) {
            InternalActorRef internalActorRef = (InternalActorRef)actorRef2;
            if (timeout2.duration().length() <= 0L) {
                Object message = messageFactory.apply(internalActorRef.provider().deadLetters());
                return Future$.MODULE$.failed(AskableActorRef$.MODULE$.negativeTimeoutException($this, message, sender));
            }
            String refPrefix = URLEncoder.encode($this.pathString().replace("/", "_"), ByteString$.MODULE$.UTF_8());
            PromiseActorRef a = PromiseActorRef$.MODULE$.apply(internalActorRef.provider(), timeout2, $this, "unknown", refPrefix, sender, PromiseActorRef$.MODULE$.apply$default$7());
            Object message = messageFactory.apply(a);
            a.messageClassName_$eq(message.getClass().getName());
            return a.ask($this, message, timeout2);
        }
        if (sender == null) {
            return Future$.MODULE$.failed(new IllegalArgumentException(new StringBuilder(65).append("No recipient for the reply was provided, ").append("question not sent to [").append($this).append("].").toString()));
        }
        Object message = sender == null ? null : messageFactory.apply(((InternalActorRef)sender).provider().deadLetters());
        return Future$.MODULE$.failed(AskableActorRef$.MODULE$.unsupportedRecipientType($this, message, sender));
    }

    public final ActorRef $qmark$default$3$extension(ActorSelection $this, Function1<ActorRef, Object> message) {
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
        if (!(object instanceof ExplicitlyAskableActorSelection)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorSelection actorSelection = x$1 == null ? null : ((ExplicitlyAskableActorSelection)x$1).actorSel();
        ActorSelection actorSelection2 = $this;
        ActorSelection actorSelection3 = actorSelection;
        if (actorSelection2 != null) {
            if (!((Object)actorSelection2).equals(actorSelection3)) return false;
            return true;
        }
        if (actorSelection3 == null) return true;
        return false;
    }

    private ExplicitlyAskableActorSelection$() {
    }
}

