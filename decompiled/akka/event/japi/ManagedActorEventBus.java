/*
 * Decompiled with CFR 0.152.
 */
package akka.event.japi;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.ActorEventBus;
import akka.event.ManagedActorClassification;
import akka.event.japi.EventBus;
import akka.event.japi.ManagedActorEventBus$;
import java.util.concurrent.atomic.AtomicReference;
import scala.collection.immutable.TreeSet;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005)4Qa\u0003\u0007\u0002\u0002MA\u0001\u0002\r\u0001\u0003\u0002\u0003\u0006I!\r\u0005\u0006i\u0001!\t!\u000e\u0005\bq\u0001\u0011\r\u0011\"\u0003:\u0011\u0019i\u0004\u0001)A\u0005u!)A\n\u0001D\t\u001b\")\u0011\u000b\u0001D\t%\")A\u000b\u0001C!+\")Q\f\u0001C!=\")Q\f\u0001C!E\")q\r\u0001C!Q\n!R*\u00198bO\u0016$\u0017i\u0019;pe\u00163XM\u001c;CkNT!!\u0004\b\u0002\t)\f\u0007/\u001b\u0006\u0003\u001fA\tQ!\u001a<f]RT\u0011!E\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0005Q\t3c\u0001\u0001\u00167A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t1\u0011I\\=SK\u001a\u0004R\u0001H\u000f U)j\u0011\u0001D\u0005\u0003=1\u0011\u0001\"\u0012<f]R\u0014Uo\u001d\t\u0003A\u0005b\u0001\u0001B\u0003#\u0001\t\u00071EA\u0001F#\t!s\u0005\u0005\u0002\u0017K%\u0011ae\u0006\u0002\b\u001d>$\b.\u001b8h!\t1\u0002&\u0003\u0002*/\t\u0019\u0011I\\=\u0011\u0005-rS\"\u0001\u0017\u000b\u00055\u0002\u0012!B1di>\u0014\u0018BA\u0018-\u0005!\t5\r^8s%\u00164\u0017AB:zgR,W\u000e\u0005\u0002,e%\u00111\u0007\f\u0002\f\u0003\u000e$xN]*zgR,W.\u0001\u0004=S:LGO\u0010\u000b\u0003m]\u00022\u0001\b\u0001 \u0011\u0015\u0001$\u00011\u00012\u0003\r\u0011Wo]\u000b\u0002uI)1(\u0006 C\u000b\u001a!A\b\u0002\u0001;\u00051a$/\u001a4j]\u0016lWM\u001c;?\u0003\u0011\u0011Wo\u001d\u0011\u0011\u0005}\u0002U\"\u0001\b\n\u0005\u0005s!!D!di>\u0014XI^3oi\n+8\u000f\u0005\u0002@\u0007&\u0011AI\u0004\u0002\u001b\u001b\u0006t\u0017mZ3e\u0003\u000e$xN]\"mCN\u001c\u0018NZ5dCRLwN\u001c\t\u0003\u007f\u0019K!a\u0012\b\u0003\u001f\u0005\u001bGo\u001c:DY\u0006\u001c8/\u001b4jKJ,A!S\u001e\u0001?\t)QI^3oi\"9\u0001g\u000fb\u0001\n\u0003ZU#A\u0019\u0002\u000f5\f\u0007oU5{KR\ta\n\u0005\u0002\u0017\u001f&\u0011\u0001k\u0006\u0002\u0004\u0013:$\u0018\u0001C2mCN\u001c\u0018NZ=\u0015\u0005)\u001a\u0006\"B\b\u0007\u0001\u0004y\u0012!C:vEN\u001c'/\u001b2f)\r1\u0016l\u0017\t\u0003-]K!\u0001W\f\u0003\u000f\t{w\u000e\\3b]\")!l\u0002a\u0001U\u0005Q1/\u001e2tGJL'-\u001a:\t\u000bq;\u0001\u0019\u0001\u0016\u0002\u0005Q|\u0017aC;ogV\u00147o\u0019:jE\u0016$2AV0a\u0011\u0015Q\u0006\u00021\u0001+\u0011\u0015\t\u0007\u00021\u0001+\u0003\u00111'o\\7\u0015\u0005\r4\u0007C\u0001\fe\u0013\t)wC\u0001\u0003V]&$\b\"\u0002.\n\u0001\u0004Q\u0013a\u00029vE2L7\u000f\u001b\u000b\u0003G&DQa\u0004\u0006A\u0002}\u0001")
public abstract class ManagedActorEventBus<E>
implements EventBus<E, ActorRef, ActorRef> {
    public final ActorSystem akka$event$japi$ManagedActorEventBus$$system;
    private final ActorEventBus bus;

    private ActorEventBus bus() {
        return this.bus;
    }

    public abstract int mapSize();

    public abstract ActorRef classify(E var1);

    @Override
    public boolean subscribe(ActorRef subscriber, ActorRef to) {
        return ((ManagedActorClassification)((Object)this.bus())).subscribe(subscriber, to);
    }

    @Override
    public boolean unsubscribe(ActorRef subscriber, ActorRef from) {
        return ((ManagedActorClassification)((Object)this.bus())).unsubscribe(subscriber, from);
    }

    @Override
    public void unsubscribe(ActorRef subscriber) {
        ((ManagedActorClassification)((Object)this.bus())).unsubscribe(subscriber);
    }

    @Override
    public void publish(E event) {
        ((ManagedActorClassification)((Object)this.bus())).publish(event);
    }

    public ManagedActorEventBus(ActorSystem system) {
        this.akka$event$japi$ManagedActorEventBus$$system = system;
        this.bus = new ActorEventBus(this){
            private final ActorSystem system;
            private AtomicReference<ManagedActorClassification.ManagedActorClassificationMappings> akka$event$ManagedActorClassification$$mappings;
            private TreeSet<ActorRef> akka$event$ManagedActorClassification$$empty;
            private ActorRef unsubscriber;
            private volatile boolean bitmap$0;
            private final /* synthetic */ ManagedActorEventBus $outer;

            public final boolean associate(ActorRef monitored, ActorRef monitor) {
                return ManagedActorClassification.associate$(this, monitored, monitor);
            }

            public final void dissociate(ActorRef actor) {
                ManagedActorClassification.dissociate$(this, actor);
            }

            public final boolean dissociate(ActorRef monitored, ActorRef monitor) {
                return ManagedActorClassification.dissociate$(this, monitored, monitor);
            }

            public void publish(Object event) {
                ManagedActorClassification.publish$(this, event);
            }

            public boolean subscribe(ActorRef subscriber, ActorRef to) {
                return ManagedActorClassification.subscribe$(this, subscriber, to);
            }

            public boolean unsubscribe(ActorRef subscriber, ActorRef from) {
                return ManagedActorClassification.unsubscribe$(this, subscriber, from);
            }

            public void unsubscribe(ActorRef subscriber) {
                ManagedActorClassification.unsubscribe$(this, subscriber);
            }

            public boolean registerWithUnsubscriber(ActorRef subscriber, int seqNr) {
                return ManagedActorClassification.registerWithUnsubscriber$(this, subscriber, seqNr);
            }

            public boolean unregisterFromUnsubscriber(ActorRef subscriber, int seqNr) {
                return ManagedActorClassification.unregisterFromUnsubscriber$(this, subscriber, seqNr);
            }

            public int compareSubscribers(ActorRef a, ActorRef b) {
                return ActorEventBus.compareSubscribers$(this, a, b);
            }

            public AtomicReference<ManagedActorClassification.ManagedActorClassificationMappings> akka$event$ManagedActorClassification$$mappings() {
                return this.akka$event$ManagedActorClassification$$mappings;
            }

            public TreeSet<ActorRef> akka$event$ManagedActorClassification$$empty() {
                return this.akka$event$ManagedActorClassification$$empty;
            }

            private ActorRef unsubscriber$lzycompute() {
                $anon$4 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.unsubscriber = ManagedActorClassification.unsubscriber$(this);
                        this.bitmap$0 = true;
                    }
                }
                return this.unsubscriber;
            }

            public ActorRef unsubscriber() {
                if (!this.bitmap$0) {
                    return this.unsubscriber$lzycompute();
                }
                return this.unsubscriber;
            }

            public final void akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$mappings_$eq(AtomicReference<ManagedActorClassification.ManagedActorClassificationMappings> x$1) {
                this.akka$event$ManagedActorClassification$$mappings = x$1;
            }

            public final void akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$empty_$eq(TreeSet<ActorRef> x$1) {
                this.akka$event$ManagedActorClassification$$empty = x$1;
            }

            public ActorSystem system() {
                return this.system;
            }

            public int mapSize() {
                return this.$outer.mapSize();
            }

            public ActorRef classify(E event) {
                return this.$outer.classify(event);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                ActorEventBus.$init$(this);
                ManagedActorClassification.$init$(this);
                this.system = $outer.akka$event$japi$ManagedActorEventBus$$system;
                Statics.releaseFence();
            }
        };
    }
}

