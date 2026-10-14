/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorIdentity;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSelectionMessage;
import akka.actor.DeadLetter;
import akka.actor.DeadLetterSuppression;
import akka.actor.Identify;
import akka.actor.InternalActorRef;
import akka.actor.InvalidMessageException$;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.SuppressedDeadLetter;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.event.EventStream;
import java.io.ObjectStreamException;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M4QAD\b\u0001#MA\u0001b\u0007\u0001\u0003\u0006\u0004%\t%\b\u0005\tC\u0001\u0011\t\u0011)A\u0005=!A!\u0005\u0001BC\u0002\u0013\u00053\u0005\u0003\u0005(\u0001\t\u0005\t\u0015!\u0003%\u0011!A\u0003A!b\u0001\n\u0003I\u0003\u0002\u0003\u0019\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0016\t\u000bE\u0002A\u0011\u0001\u001a\t\r]\u0002A\u0011I\t9\u0011\u0015y\u0004\u0001\"\u0011A\u0011\u0015q\u0005\u0001\"\u0011P\u0011\u001dQ\u0006!%A\u0005\u0002mCQa\u001a\u0001\u0005\u0012!DQ\u0001\u001c\u0001\u0005\n5\u0014!#R7qifdunY1m\u0003\u000e$xN\u001d*fM*\u0011\u0001#E\u0001\u0006C\u000e$xN\u001d\u0006\u0002%\u0005!\u0011m[6b'\r\u0001A\u0003\u0007\t\u0003+Yi\u0011aD\u0005\u0003/=\u0011\u0001#\u00138uKJt\u0017\r\\!di>\u0014(+\u001a4\u0011\u0005UI\u0012B\u0001\u000e\u0010\u0005=i\u0015N\\5nC2\f5\r^8s%\u00164\u0017\u0001\u00039s_ZLG-\u001a:\u0004\u0001U\ta\u0004\u0005\u0002\u0016?%\u0011\u0001e\u0004\u0002\u0011\u0003\u000e$xN\u001d*fMB\u0013xN^5eKJ\f\u0011\u0002\u001d:pm&$WM\u001d\u0011\u0002\tA\fG\u000f[\u000b\u0002IA\u0011Q#J\u0005\u0003M=\u0011\u0011\"Q2u_J\u0004\u0016\r\u001e5\u0002\u000bA\fG\u000f\u001b\u0011\u0002\u0017\u00154XM\u001c;TiJ,\u0017-\\\u000b\u0002UA\u00111FL\u0007\u0002Y)\u0011Q&E\u0001\u0006KZ,g\u000e^\u0005\u0003_1\u00121\"\u0012<f]R\u001cFO]3b[\u0006aQM^3oiN#(/Z1nA\u00051A(\u001b8jiz\"Ba\r\u001b6mA\u0011Q\u0003\u0001\u0005\u00067\u001d\u0001\rA\b\u0005\u0006E\u001d\u0001\r\u0001\n\u0005\u0006Q\u001d\u0001\rAK\u0001\rSN$VM]7j]\u0006$X\rZ\u000b\u0002sA\u0011!(P\u0007\u0002w)\tA(A\u0003tG\u0006d\u0017-\u0003\u0002?w\t9!i\\8mK\u0006t\u0017!E:f]\u0012\u001c\u0016p\u001d;f[6+7o]1hKR\u0011\u0011\t\u0012\t\u0003u\tK!aQ\u001e\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u000b&\u0001\rAR\u0001\b[\u0016\u001c8/Y4f!\t9E*D\u0001I\u0015\tI%*\u0001\u0004tsNl7o\u001a\u0006\u0003\u0017F\t\u0001\u0002Z5ta\u0006$8\r[\u0005\u0003\u001b\"\u0013QbU=ti\u0016lW*Z:tC\u001e,\u0017!\u0002\u0013cC:<GC\u0001)W)\t\t\u0015\u000bC\u0004S\u0015A\u0005\t9A*\u0002\rM,g\u000eZ3s!\t)B+\u0003\u0002V\u001f\tA\u0011i\u0019;peJ+g\rC\u0003F\u0015\u0001\u0007q\u000b\u0005\u0002;1&\u0011\u0011l\u000f\u0002\u0004\u0003:L\u0018a\u0004\u0013cC:<G\u0005Z3gCVdG\u000f\n\u001a\u0015\u0005q3'FA*^W\u0005q\u0006CA0e\u001b\u0005\u0001'BA1c\u0003%)hn\u00195fG.,GM\u0003\u0002dw\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0015\u0004'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\")Qi\u0003a\u0001/\u0006i1\u000f]3dS\u0006d\u0007*\u00198eY\u0016$2!O5l\u0011\u0015QG\u00021\u0001X\u0003\ri7o\u001a\u0005\u0006%2\u0001\raU\u0001\u001baV\u0014G.[:i'V\u0004(/Z:tK\u0012$U-\u00193MKR$XM\u001d\u000b\u0004\u0003:\u0014\b\"\u00026\u000e\u0001\u0004y\u0007CA\u000bq\u0013\t\txBA\u000bEK\u0006$G*\u001a;uKJ\u001cV\u000f\u001d9sKN\u001c\u0018n\u001c8\t\u000bIk\u0001\u0019A*")
public class EmptyLocalActorRef
extends InternalActorRef
implements MinimalActorRef {
    private final ActorRefProvider provider;
    private final ActorPath path;
    private final EventStream eventStream;

    @Override
    public InternalActorRef getParent() {
        return MinimalActorRef.getParent$(this);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        return MinimalActorRef.getChild$(this, names);
    }

    @Override
    public void start() {
        MinimalActorRef.start$(this);
    }

    @Override
    public void suspend() {
        MinimalActorRef.suspend$(this);
    }

    @Override
    public void resume(Throwable causedByFailure) {
        MinimalActorRef.resume$(this, causedByFailure);
    }

    @Override
    public void stop() {
        MinimalActorRef.stop$(this);
    }

    @Override
    public void restart(Throwable cause) {
        MinimalActorRef.restart$(this, cause);
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return MinimalActorRef.writeReplace$(this);
    }

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public ActorRefProvider provider() {
        return this.provider;
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    @Override
    public boolean isTerminated() {
        return true;
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        this.specialHandle(message, this.provider().deadLetters());
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        Object object = message;
        if (object == null) {
            throw InvalidMessageException$.MODULE$.apply("Message is null");
        }
        if (object instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter)object;
            this.specialHandle(deadLetter.message(), deadLetter.sender());
            return;
        }
        if (!this.specialHandle(message, sender)) {
            this.eventStream().publish(new DeadLetter(message, sender == Actor$.MODULE$.noSender() ? this.provider().deadLetters() : sender, this));
            return;
        }
    }

    public boolean specialHandle(Object msg, ActorRef sender) {
        Object object = msg;
        if (object instanceof Watch) {
            Watch watch = (Watch)object;
            InternalActorRef internalActorRef = watch.watchee();
            EmptyLocalActorRef emptyLocalActorRef = this;
            if (!(internalActorRef != null ? !((Object)internalActorRef).equals(emptyLocalActorRef) : emptyLocalActorRef != null)) {
                InternalActorRef internalActorRef2 = watch.watcher();
                EmptyLocalActorRef emptyLocalActorRef2 = this;
                if (internalActorRef2 == null ? emptyLocalActorRef2 != null : !((Object)internalActorRef2).equals(emptyLocalActorRef2)) {
                    watch.watcher().sendSystemMessage(new DeathWatchNotification(watch.watchee(), false, false));
                }
            }
            return true;
        }
        if (object instanceof Unwatch) {
            return true;
        }
        if (object instanceof Identify) {
            Identify identify = (Identify)object;
            Object messageId = identify.messageId();
            ActorIdentity x$1 = new ActorIdentity(messageId, None$.MODULE$);
            ActorRef x$2 = sender.$bang$default$2(x$1);
            sender.$bang(x$1, x$2);
            return true;
        }
        if (object instanceof ActorSelectionMessage) {
            ActorSelectionMessage actorSelectionMessage = (ActorSelectionMessage)object;
            Option<Identify> option = actorSelectionMessage.identifyRequest();
            if (option instanceof Some) {
                Some some = (Some)option;
                Identify identify = (Identify)some.value();
                if (!actorSelectionMessage.wildcardFanOut()) {
                    ActorIdentity x$3 = new ActorIdentity(identify.messageId(), None$.MODULE$);
                    ActorRef x$4 = sender.$bang$default$2(x$3);
                    sender.$bang(x$3, x$4);
                }
            } else if (None$.MODULE$.equals(option)) {
                Object object2 = actorSelectionMessage.msg();
                if (object2 instanceof DeadLetterSuppression) {
                    DeadLetterSuppression deadLetterSuppression = (DeadLetterSuppression)object2;
                    this.publishSupressedDeadLetter(deadLetterSuppression, sender);
                } else {
                    this.eventStream().publish(new DeadLetter(actorSelectionMessage.msg(), sender == Actor$.MODULE$.noSender() ? this.provider().deadLetters() : sender, this));
                }
            } else {
                throw new MatchError(option);
            }
            return true;
        }
        if (object instanceof DeadLetterSuppression) {
            DeadLetterSuppression deadLetterSuppression = (DeadLetterSuppression)object;
            this.publishSupressedDeadLetter(deadLetterSuppression, sender);
            return true;
        }
        return false;
    }

    private void publishSupressedDeadLetter(DeadLetterSuppression msg, ActorRef sender) {
        this.eventStream().publish(new SuppressedDeadLetter(msg, sender == Actor$.MODULE$.noSender() ? this.provider().deadLetters() : sender, this));
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    public EmptyLocalActorRef(ActorRefProvider provider, ActorPath path, EventStream eventStream) {
        this.provider = provider;
        this.path = path;
        this.eventStream = eventStream;
        LocalRef.$init$(this);
        MinimalActorRef.$init$(this);
    }
}

