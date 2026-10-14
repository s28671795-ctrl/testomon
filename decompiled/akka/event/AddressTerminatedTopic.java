/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.ActorSystem;
import akka.actor.AddressTerminated;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.event.AddressTerminatedTopic$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005e<aAD\b\t\u0002E\u0019bAB\u000b\u0010\u0011\u0003\tb\u0003C\u0003)\u0003\u0011\u0005A\rC\u0003f\u0003\u0011\u0005c\rC\u0003f\u0003\u0011\u0005C\u000eC\u0003r\u0003\u0011\u0005#\u000fC\u0003t\u0003\u0011\u0005COB\u0003\u0016\u001f\t\tB\u0005C\u0003)\u000f\u0011\u0005!\u0006C\u0004,\u000f\t\u0007I\u0011\u0002\u0017\t\r\u001d;\u0001\u0015!\u0003.\u0011\u0015Au\u0001\"\u0001J\u0011\u00151v\u0001\"\u0001X\u0011\u0015Qv\u0001\"\u0001\\\u0003Y\tE\r\u001a:fgN$VM]7j]\u0006$X\r\u001a+pa&\u001c'B\u0001\t\u0012\u0003\u0015)g/\u001a8u\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u0004\"\u0001F\u0001\u000e\u0003=\u0011a#\u00113ee\u0016\u001c8\u000fV3s[&t\u0017\r^3e)>\u0004\u0018nY\n\u0005\u0003]i\u0012\r\u0005\u0002\u001975\t\u0011DC\u0001\u001b\u0003\u0015\u00198-\u00197b\u0013\ta\u0012D\u0001\u0004B]f\u0014VM\u001a\t\u0004=\u0005\u001aS\"A\u0010\u000b\u0005\u0001\n\u0012!B1di>\u0014\u0018B\u0001\u0012 \u0005-)\u0005\u0010^3og&|g.\u00133\u0011\u0005Q91cA\u0004\u0018KA\u0011aDJ\u0005\u0003O}\u0011\u0011\"\u0012=uK:\u001c\u0018n\u001c8\u0002\rqJg.\u001b;?\u0007\u0001!\u0012aI\u0001\fgV\u00147o\u0019:jE\u0016\u00148/F\u0001.!\rqs'O\u0007\u0002_)\u0011\u0001'M\u0001\u0007CR|W.[2\u000b\u0005I\u001a\u0014AC2p]\u000e,(O]3oi*\u0011A'N\u0001\u0005kRLGNC\u00017\u0003\u0011Q\u0017M^1\n\u0005az#aD!u_6L7MU3gKJ,gnY3\u0011\u0007i\nEI\u0004\u0002<\u007fA\u0011A(G\u0007\u0002{)\u0011a(K\u0001\u0007yI|w\u000e\u001e \n\u0005\u0001K\u0012A\u0002)sK\u0012,g-\u0003\u0002C\u0007\n\u00191+\u001a;\u000b\u0005\u0001K\u0002C\u0001\u0010F\u0013\t1uD\u0001\u0005BGR|'OU3g\u00031\u0019XOY:de&\u0014WM]:!\u0003%\u0019XOY:de&\u0014W\r\u0006\u0002K\u001bB\u0011\u0001dS\u0005\u0003\u0019f\u0011A!\u00168ji\")aj\u0003a\u0001\t\u0006Q1/\u001e2tGJL'-\u001a:)\u0005-\u0001\u0006CA)U\u001b\u0005\u0011&BA*\u001a\u0003)\tgN\\8uCRLwN\\\u0005\u0003+J\u0013q\u0001^1jYJ,7-A\u0006v]N,(m]2sS\n,GC\u0001&Y\u0011\u0015qE\u00021\u0001EQ\ta\u0001+A\u0004qk\nd\u0017n\u001d5\u0015\u0005)c\u0006\"B/\u000e\u0001\u0004q\u0016aA7tOB\u0011adX\u0005\u0003A~\u0011\u0011#\u00113ee\u0016\u001c8\u000fV3s[&t\u0017\r^3e!\tq\"-\u0003\u0002d?\t\u0019R\t\u001f;f]NLwN\\%e!J|g/\u001b3feR\t1#A\u0002hKR$\"aI4\t\u000b!\u001c\u0001\u0019A5\u0002\rML8\u000f^3n!\tq\".\u0003\u0002l?\tY\u0011i\u0019;peNK8\u000f^3n)\t\u0019S\u000eC\u0003i\t\u0001\u0007a\u000e\u0005\u0002\u001f_&\u0011\u0001o\b\u0002\u001b\u00072\f7o]5d\u0003\u000e$xN]*zgR,W\u000e\u0015:pm&$WM]\u0001\u0007Y>|7.\u001e9\u0016\u0003M\tqb\u0019:fCR,W\t\u001f;f]NLwN\u001c\u000b\u0003GUDQ\u0001\u001b\u0004A\u0002Y\u0004\"AH<\n\u0005a|\"aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0007")
public final class AddressTerminatedTopic
implements Extension {
    private final AtomicReference<Set<ActorRef>> subscribers = new AtomicReference<Object>(Predef$.MODULE$.Set().empty());

    public static AddressTerminatedTopic createExtension(ExtendedActorSystem system) {
        return AddressTerminatedTopic$.MODULE$.createExtension(system);
    }

    public static AddressTerminatedTopic$ lookup() {
        return AddressTerminatedTopic$.MODULE$.lookup();
    }

    public static AddressTerminatedTopic get(ClassicActorSystemProvider system) {
        return AddressTerminatedTopic$.MODULE$.get(system);
    }

    public static AddressTerminatedTopic get(ActorSystem system) {
        return AddressTerminatedTopic$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return AddressTerminatedTopic$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return AddressTerminatedTopic$.MODULE$.apply(system);
    }

    private AtomicReference<Set<ActorRef>> subscribers() {
        return this.subscribers;
    }

    public void subscribe(ActorRef subscriber) {
        Set<ActorRef> current;
        do {
            current = this.subscribers().get();
        } while (!this.subscribers().compareAndSet(current, (Set<ActorRef>)current.$plus(subscriber)));
    }

    public void unsubscribe(ActorRef subscriber) {
        Set<ActorRef> current;
        do {
            current = this.subscribers().get();
        } while (!this.subscribers().compareAndSet(current, (Set<ActorRef>)current.$minus(subscriber)));
    }

    public void publish(AddressTerminated msg) {
        ((IterableOnceOps)this.subscribers().get()).foreach((Function1<ActorRef, Object> & Serializable)x$1 -> {
            x$1.tell(msg, ActorRef$.MODULE$.noSender());
            return BoxedUnit.UNIT;
        });
    }
}

