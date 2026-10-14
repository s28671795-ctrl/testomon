/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.InternalActorRef;
import akka.annotation.InternalApi;
import akka.pattern.AskTimeoutException;
import akka.pattern.AskableActorRef;
import akka.pattern.PromiseActorRef$;
import akka.pattern.StatusReply;
import akka.pattern.StatusReply$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.reflect.ClassTag$;

public final class AskableActorRef$ {
    public static final AskableActorRef$ MODULE$ = new AskableActorRef$();

    private String messagePartOfException(Object message, ActorRef sender) {
        Object msg = message == null ? "unknown" : message;
        ActorRef actorRef2 = sender;
        ActorRef actorRef3 = ActorRef$.MODULE$.noSender();
        String wasSentBy = !(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null) ? "" : new StringBuilder(15).append(" was sent by [").append(sender).append("]").toString();
        return new StringBuilder(19).append("Message of type [").append(msg.getClass().getName()).append("]").append(wasSentBy).append(".").toString();
    }

    @InternalApi
    public IllegalArgumentException negativeTimeoutException(Object recipient, Object message, ActorRef sender) {
        return new IllegalArgumentException(new StringBuilder(58).append("Timeout length must be positive, question not sent to [").append(recipient).append("]. ").append(this.messagePartOfException(message, sender)).toString());
    }

    @InternalApi
    public AskTimeoutException recipientTerminatedException(Object recipient, Object message, ActorRef sender) {
        return new AskTimeoutException(new StringBuilder(42).append("Recipient [").append(recipient).append("] had already been terminated. ").append(this.messagePartOfException(message, sender)).toString());
    }

    @InternalApi
    public IllegalArgumentException unsupportedRecipientType(Object recipient, Object message, ActorRef sender) {
        return new IllegalArgumentException(new StringBuilder(53).append("Unsupported recipient type, question not sent to [").append(recipient).append("]. ").append(this.messagePartOfException(message, sender)).toString());
    }

    public final Future<Object> ask$extension(ActorRef $this, Object message, Timeout timeout2) {
        return this.internalAsk$extension($this, message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public final Future<Object> ask$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final ActorRef ask$default$3$extension(ActorRef $this, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public final Future<Object> askWithStatus$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return this.internalAskWithStatus$extension($this, message, timeout2, sender);
    }

    public final ActorRef askWithStatus$default$3$extension(ActorRef $this, Object message) {
        return Actor$.MODULE$.noSender();
    }

    @InternalApi
    public final Future<Object> internalAskWithStatus$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return StatusReply$.MODULE$.flattenStatusFuture(this.internalAsk$extension($this, message, timeout2, sender).mapTo(ClassTag$.MODULE$.apply(StatusReply.class)));
    }

    public final Future<Object> $qmark$extension(ActorRef $this, Object message, Timeout timeout2) {
        return this.internalAsk$extension($this, message, timeout2, ActorRef$.MODULE$.noSender());
    }

    public final Future<Object> $qmark$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        return this.internalAsk$extension($this, message, timeout2, sender);
    }

    public final Future<Object> internalAsk$extension(ActorRef $this, Object message, Timeout timeout2, ActorRef sender) {
        boolean bl = false;
        InternalActorRef internalActorRef = null;
        ActorRef actorRef2 = $this;
        if (actorRef2 instanceof InternalActorRef) {
            bl = true;
            internalActorRef = (InternalActorRef)actorRef2;
            if (internalActorRef.isTerminated()) {
                Object x$1 = message;
                ActorRef x$2 = $this.$bang$default$2(x$1);
                $this.$bang(x$1, x$2);
                return Future$.MODULE$.failed(this.recipientTerminatedException($this, message, sender));
            }
        }
        if (bl) {
            if (timeout2.duration().length() <= 0L) {
                return Future$.MODULE$.failed(this.negativeTimeoutException($this, message, sender));
            }
            return PromiseActorRef$.MODULE$.apply(internalActorRef.provider(), timeout2, $this, message.getClass().getName(), internalActorRef.path().name(), sender, PromiseActorRef$.MODULE$.apply$default$7()).ask($this, message, timeout2);
        }
        return Future$.MODULE$.failed(this.unsupportedRecipientType($this, message, sender));
    }

    public final ActorRef internalAskWithStatus$default$3$extension(ActorRef $this, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public final ActorRef $qmark$default$3$extension(ActorRef $this, Object message) {
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
        if (!(object instanceof AskableActorRef)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorRef actorRef2 = x$1 == null ? null : ((AskableActorRef)x$1).actorRef();
        ActorRef actorRef3 = $this;
        ActorRef actorRef4 = actorRef2;
        if (actorRef3 != null) {
            if (!((Object)actorRef3).equals(actorRef4)) return false;
            return true;
        }
        if (actorRef4 == null) return true;
        return false;
    }

    private AskableActorRef$() {
    }
}

