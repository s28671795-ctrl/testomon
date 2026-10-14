/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.InternalActorRef;
import akka.pattern.AskableActorRef$;
import akka.pattern.ExplicitlyAskableActorRef;
import akka.pattern.PromiseActorRef;
import akka.pattern.PromiseActorRef$;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;
import scala.concurrent.Future$;

public final class ExplicitlyAskableActorRef$ {
    public static final ExplicitlyAskableActorRef$ MODULE$ = new ExplicitlyAskableActorRef$();

    public final Future<Object> ask$extension(ActorRef $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final ActorRef ask$default$3$extension(ActorRef $this, Function1<ActorRef, Object> message) {
        return Actor$.MODULE$.noSender();
    }

    public final Future<Object> $qmark$extension(ActorRef $this, Function1<ActorRef, Object> message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final Future<Object> internalAsk$extension(ActorRef $this, Function1<ActorRef, Object> messageFactory, Timeout timeout2, ActorRef sender) {
        boolean bl = false;
        InternalActorRef internalActorRef = null;
        ActorRef actorRef2 = $this;
        if (actorRef2 instanceof InternalActorRef) {
            bl = true;
            internalActorRef = (InternalActorRef)actorRef2;
            if (internalActorRef.isTerminated()) {
                Object message;
                Object x$1 = message = messageFactory.apply(internalActorRef.provider().deadLetters());
                ActorRef x$2 = $this.$bang$default$2(x$1);
                $this.$bang(x$1, x$2);
                return Future$.MODULE$.failed(AskableActorRef$.MODULE$.recipientTerminatedException($this, message, sender));
            }
        }
        if (bl) {
            if (timeout2.duration().length() <= 0L) {
                Object message = messageFactory.apply(internalActorRef.provider().deadLetters());
                return Future$.MODULE$.failed(AskableActorRef$.MODULE$.negativeTimeoutException($this, message, sender));
            }
            PromiseActorRef a = PromiseActorRef$.MODULE$.apply(internalActorRef.provider(), timeout2, $this, "unknown", internalActorRef.path().name(), sender, PromiseActorRef$.MODULE$.apply$default$7());
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

    public final ActorRef $qmark$default$3$extension(ActorRef $this, Function1<ActorRef, Object> message) {
        return Actor$.MODULE$.noSender();
    }

    public final int hashCode$extension(ActorRef $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(ActorRef $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof ExplicitlyAskableActorRef)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorRef actorRef2 = x$1 == null ? null : ((ExplicitlyAskableActorRef)x$1).actorRef();
        ActorRef actorRef3 = $this;
        ActorRef actorRef4 = actorRef2;
        if (actorRef3 != null) {
            if (!((Object)actorRef3).equals(actorRef4)) return false;
            return true;
        }
        if (actorRef4 == null) return true;
        return false;
    }

    private ExplicitlyAskableActorRef$() {
    }
}

