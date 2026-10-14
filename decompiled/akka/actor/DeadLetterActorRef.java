/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorIdentity;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.DeadLetterActorRef$;
import akka.actor.EmptyLocalActorRef;
import akka.actor.Identify;
import akka.actor.InternalActorRef;
import akka.actor.InvalidMessageException$;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.Watch;
import akka.event.EventStream;
import akka.serialization.JavaSerializer$;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.None$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]rA\u0002\n\u0014\u0011\u0003)rC\u0002\u0004\u001a'!\u0005QC\u0007\u0005\u0006S\u0005!\ta\u000b\u0004\u0005Y\u0005\u0001Q\u0006C\u0003*\u0007\u0011\u0005\u0011\bC\u0003=\u0007\u0011%Q\bC\u0004M\u0003\t\u0007I\u0011A'\t\r9\u000b\u0001\u0015!\u0003;\u0011\u001dy\u0015!!A\u0005\nA3Q!G\n\u0001+]C\u0011bW\u0005\u0003\u0002\u0003\u0006I\u0001X0\t\u0013\u0005L!\u0011!Q\u0001\n\t,\u0007\"C4\n\u0005\u0003\u0005\u000b\u0011\u00025o\u0011\u0015I\u0013\u0002\"\u0001q\u0011\u0015)\u0018\u0002\"\u0011w\u0011%\tY!CI\u0001\n\u0003\ti\u0001C\u0004\u0002&%!\t&a\n\t\u000b=KA\u0011K\u001f\u0002%\u0011+\u0017\r\u001a'fiR,'/Q2u_J\u0014VM\u001a\u0006\u0003)U\tQ!Y2u_JT\u0011AF\u0001\u0005C.\\\u0017\r\u0005\u0002\u0019\u00035\t1C\u0001\nEK\u0006$G*\u001a;uKJ\f5\r^8s%\u001647cA\u0001\u001cCA\u0011AdH\u0007\u0002;)\ta$A\u0003tG\u0006d\u0017-\u0003\u0002!;\t1\u0011I\\=SK\u001a\u0004\"AI\u0014\u000e\u0003\rR!\u0001J\u0013\u0002\u0005%|'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\r\u0012AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002/\ta2+\u001a:jC2L'0\u001a3EK\u0006$G*\u001a;uKJ\f5\r^8s%\u001647cA\u0002\u001c]A\u0011qf\u000e\b\u0003aUr!!\r\u001b\u000e\u0003IR!a\r\u0016\u0002\rq\u0012xn\u001c;?\u0013\u0005q\u0012B\u0001\u001c\u001e\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001\u000b\u001d\u000b\u0005YjB#\u0001\u001e\u0011\u0005m\u001aQ\"A\u0001\u0002\u0017I,\u0017\r\u001a*fg>dg/\u001a\u000b\u00027!\u001aQaP#\u0011\u0007q\u0001%)\u0003\u0002B;\t1A\u000f\u001b:poN\u0004\"AI\"\n\u0005\u0011\u001b#!F(cU\u0016\u001cGo\u0015;sK\u0006lW\t_2faRLwN\\\u0012\u0002\u0005\"\"1a\u0012&L!\ta\u0002*\u0003\u0002J;\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005Q1/\u001a:jC2L'0\u001a3\u0016\u0003i\n1b]3sS\u0006d\u0017N_3eA\u0005aqO]5uKJ+\u0007\u000f\\1dKR\t\u0011\u000b\u0005\u0002S+6\t1K\u0003\u0002UK\u0005!A.\u00198h\u0013\t16K\u0001\u0004PE*,7\r^\n\u0003\u0013a\u0003\"\u0001G-\n\u0005i\u001b\"AE#naRLHj\\2bY\u0006\u001bGo\u001c:SK\u001a\f\u0011b\u00189s_ZLG-\u001a:\u0011\u0005ai\u0016B\u00010\u0014\u0005A\t5\r^8s%\u00164\u0007K]8wS\u0012,'/\u0003\u0002a3\u0006A\u0001O]8wS\u0012,'/A\u0003`a\u0006$\b\u000e\u0005\u0002\u0019G&\u0011Am\u0005\u0002\n\u0003\u000e$xN\u001d)bi\"L!AZ-\u0002\tA\fG\u000f[\u0001\r?\u00164XM\u001c;TiJ,\u0017-\u001c\t\u0003S2l\u0011A\u001b\u0006\u0003WV\tQ!\u001a<f]RL!!\u001c6\u0003\u0017\u00153XM\u001c;TiJ,\u0017-\\\u0005\u0003_f\u000b1\"\u001a<f]R\u001cFO]3b[R!\u0011O]:u!\tA\u0012\u0002C\u0003\\\u001b\u0001\u0007A\fC\u0003b\u001b\u0001\u0007!\rC\u0003h\u001b\u0001\u0007\u0001.A\u0003%E\u0006tw\rF\u0002x\u0003\u0003!\"\u0001_>\u0011\u0005qI\u0018B\u0001>\u001e\u0005\u0011)f.\u001b;\t\u000fqt\u0001\u0013!a\u0002{\u000611/\u001a8eKJ\u0004\"\u0001\u0007@\n\u0005}\u001c\"\u0001C!di>\u0014(+\u001a4\t\u000f\u0005\ra\u00021\u0001\u0002\u0006\u00059Q.Z:tC\u001e,\u0007c\u0001\u000f\u0002\b%\u0019\u0011\u0011B\u000f\u0003\u0007\u0005s\u00170A\b%E\u0006tw\r\n3fM\u0006,H\u000e\u001e\u00133)\u0011\ty!a\t+\u0007u\f\tb\u000b\u0002\u0002\u0014A!\u0011QCA\u0010\u001b\t\t9B\u0003\u0003\u0002\u001a\u0005m\u0011!C;oG\",7m[3e\u0015\r\ti\"H\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0011\u0003/\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0011\u001d\t\u0019a\u0004a\u0001\u0003\u000b\tQb\u001d9fG&\fG\u000eS1oI2,GCBA\u0015\u0003_\t\u0019\u0004E\u0002\u001d\u0003WI1!!\f\u001e\u0005\u001d\u0011un\u001c7fC:Dq!!\r\u0011\u0001\u0004\t)!A\u0002ng\u001eDQ\u0001 \tA\u0002uD3!E F\u0001")
public class DeadLetterActorRef
extends EmptyLocalActorRef {
    public static SerializedDeadLetterActorRef serialized() {
        return DeadLetterActorRef$.MODULE$.serialized();
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        Object object = message;
        if (object == null) {
            throw InvalidMessageException$.MODULE$.apply("Message is null");
        }
        if (object instanceof Identify) {
            Identify identify = (Identify)object;
            Object messageId = identify.messageId();
            sender.$bang(new ActorIdentity(messageId, None$.MODULE$), sender);
            return;
        }
        if (object instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter)object;
            if (!this.specialHandle(deadLetter.message(), deadLetter.sender())) {
                this.eventStream().publish(deadLetter);
                return;
            }
            return;
        }
        if (!this.specialHandle(message, sender)) {
            this.eventStream().publish(new DeadLetter(message, sender == Actor$.MODULE$.noSender() ? this.provider().deadLetters() : sender, this));
            return;
        }
    }

    @Override
    public boolean specialHandle(Object msg, ActorRef sender) {
        Object object = msg;
        if (object instanceof Watch) {
            Watch watch = (Watch)object;
            InternalActorRef internalActorRef = watch.watchee();
            DeadLetterActorRef deadLetterActorRef = this;
            if (internalActorRef == null ? deadLetterActorRef != null : !((Object)internalActorRef).equals(deadLetterActorRef)) {
                InternalActorRef internalActorRef2 = watch.watcher();
                DeadLetterActorRef deadLetterActorRef2 = this;
                if (internalActorRef2 == null ? deadLetterActorRef2 != null : !((Object)internalActorRef2).equals(deadLetterActorRef2)) {
                    watch.watcher().sendSystemMessage(new DeathWatchNotification(watch.watchee(), false, false));
                }
            }
            return true;
        }
        return super.specialHandle(msg, sender);
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return DeadLetterActorRef$.MODULE$.serialized();
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return this;
    }

    public DeadLetterActorRef(ActorRefProvider _provider, ActorPath _path, EventStream _eventStream) {
        super(_provider, _path, _eventStream);
    }

    public static class SerializedDeadLetterActorRef
    implements Serializable {
        private static final long serialVersionUID = 1L;

        private Object readResolve() throws ObjectStreamException {
            return ((ActorSystem)JavaSerializer$.MODULE$.currentSystem().value()).deadLetters();
        }
    }
}

