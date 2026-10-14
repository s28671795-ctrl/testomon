/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.ActorClassificationUnsubscriber;
import akka.event.ActorClassificationUnsubscriber$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.collection.immutable.SetOps;
import scala.collection.immutable.TreeSet;
import scala.collection.immutable.TreeSet$;
import scala.math.Ordering$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00055e!C\u000e\u001d!\u0003\r\t!IA>\u0011\u0015A\u0003\u0001\"\u0001*\u0011\u0015i\u0003A\"\u0005/\r\u0011)\u0004\u0001\u0002\u001c\t\u0011]\u001a!Q1A\u0005\u0002aB\u0001\u0002P\u0002\u0003\u0002\u0003\u0006I!\u000f\u0005\t{\r\u0011)\u0019!C\u0001}!AQk\u0001B\u0001B\u0003%q\bC\u0003W\u0007\u0011\u0005q\u000bC\u0003]\u0007\u0011\u0005Q\fC\u0003a\u0007\u0011\u0005\u0011\rC\u0003f\u0007\u0011\u0005a\rC\u0003f\u0007\u0011\u0005\u0011\u000eC\u0004l\u0001\t\u0007I\u0011\u00027\t\u000fe\u0004!\u0019!C\u0005u\"A1\u0010\u0001EC\u0002\u0013EA\u0010C\u0003~\u0001\u0011Ua\u0010C\u0004\u0002\u0018\u0001!)\"!\u0007\t\u000f\u0005]\u0001\u0001\"\u0006\u0002\u001e!9\u0011Q\u0005\u0001\u0007\u0012\u0005\u001d\u0002BBA \u0001\u0019E\u0001\bC\u0004\u0002B\u0001!\t!a\u0011\t\u000f\u0005\u001d\u0003\u0001\"\u0001\u0002J!9\u0011Q\f\u0001\u0005\u0002\u0005}\u0003bBA/\u0001\u0011\u0005\u0011q\r\u0005\t\u0003W\u0002A\u0011\u0001\u0010\u0002n!A\u00111\u000f\u0001\u0005\u0002y\t)H\u0001\u000eNC:\fw-\u001a3BGR|'o\u00117bgNLg-[2bi&|gN\u0003\u0002\u001e=\u0005)QM^3oi*\tq$\u0001\u0003bW.\f7\u0001A\n\u0003\u0001\t\u0002\"a\t\u0014\u000e\u0003\u0011R\u0011!J\u0001\u0006g\u000e\fG.Y\u0005\u0003O\u0011\u0012a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001+!\t\u00193&\u0003\u0002-I\t!QK\\5u\u0003\u0019\u0019\u0018p\u001d;f[V\tq\u0006\u0005\u00021g5\t\u0011G\u0003\u00023=\u0005)\u0011m\u0019;pe&\u0011A'\r\u0002\f\u0003\u000e$xN]*zgR,WN\u0001\u0012NC:\fw-\u001a3BGR|'o\u00117bgNLg-[2bi&|g.T1qa&twm]\n\u0003\u0007\t\nQa]3r\u001dJ,\u0012!\u000f\t\u0003GiJ!a\u000f\u0013\u0003\u0007%sG/\u0001\u0004tKFt%\u000fI\u0001\bE\u0006\u001c7.\u001b8h+\u0005y\u0004\u0003\u0002!H\u00156s!!Q#\u0011\u0005\t#S\"A\"\u000b\u0005\u0011\u0003\u0013A\u0002\u001fs_>$h(\u0003\u0002GI\u00051\u0001K]3eK\u001aL!\u0001S%\u0003\u00075\u000b\u0007O\u0003\u0002GIA\u0011\u0001gS\u0005\u0003\u0019F\u0012\u0001\"Q2u_J\u0014VM\u001a\t\u0004\u001dNSU\"A(\u000b\u0005A\u000b\u0016!C5n[V$\u0018M\u00197f\u0015\t\u0011F%\u0001\u0006d_2dWm\u0019;j_:L!\u0001V(\u0003\u000fQ\u0013X-Z*fi\u0006A!-Y2lS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u00041j[\u0006CA-\u0004\u001b\u0005\u0001\u0001\"B\u001c\t\u0001\u0004I\u0004\"B\u001f\t\u0001\u0004y\u0014aA4fiR\u0011QJ\u0018\u0005\u0006?&\u0001\rAS\u0001\n[>t\u0017\u000e^8sK\u0012\f1!\u00193e)\rA&m\u0019\u0005\u0006?*\u0001\rA\u0013\u0005\u0006I*\u0001\rAS\u0001\b[>t\u0017\u000e^8s\u0003\u0019\u0011X-\\8wKR\u0019\u0001l\u001a5\t\u000b}[\u0001\u0019\u0001&\t\u000b\u0011\\\u0001\u0019\u0001&\u0015\u0005aS\u0007\"B0\r\u0001\u0004Q\u0015\u0001C7baBLgnZ:\u0016\u00035\u00042A\\<Y\u001b\u0005y'B\u00019r\u0003\u0019\tGo\\7jG*\u0011!o]\u0001\u000bG>t7-\u001e:sK:$(B\u0001;v\u0003\u0011)H/\u001b7\u000b\u0003Y\fAA[1wC&\u0011\u0001p\u001c\u0002\u0010\u0003R|W.[2SK\u001a,'/\u001a8dK\u0006)Q-\u001c9usV\tQ*\u0001\u0007v]N,(m]2sS\n,'/F\u0001K\u0003%\t7o]8dS\u0006$X\rF\u0003\u0000\u0003\u000b\t9\u0001E\u0002$\u0003\u0003I1!a\u0001%\u0005\u001d\u0011un\u001c7fC:DQa\u0018\tA\u0002)CQ\u0001\u001a\tA\u0002)C3\u0001EA\u0006!\u0011\ti!a\u0005\u000e\u0005\u0005=!bAA\tI\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005U\u0011q\u0002\u0002\bi\u0006LGN]3d\u0003)!\u0017n]:pG&\fG/\u001a\u000b\u0004U\u0005m\u0001\"\u0002\u001a\u0012\u0001\u0004QE#B@\u0002 \u0005\u0005\u0002\"B0\u0013\u0001\u0004Q\u0005\"\u00023\u0013\u0001\u0004Q\u0005f\u0001\n\u0002\f\u0005A1\r\\1tg&4\u0017\u0010\u0006\u0003\u0002*\u0005M\u0002cA-\u0002,%!\u0011QFA\u0018\u0005)\u0019E.Y:tS\u001aLWM]\u0005\u0004\u0003ca\"aD!di>\u00148\t\\1tg&4\u0017.\u001a:\t\ru\u0019\u0002\u0019AA\u001b!\rI\u0016qG\u0005\u0005\u0003s\tYDA\u0003Fm\u0016tG/C\u0002\u0002>q\u0011\u0001\"\u0012<f]R\u0014Uo]\u0001\b[\u0006\u00048+\u001b>f\u0003\u001d\u0001XO\u00197jg\"$2AKA#\u0011\u0019iR\u00031\u0001\u00026\u0005I1/\u001e2tGJL'-\u001a\u000b\u0006\u007f\u0006-\u0013\u0011\f\u0005\b\u0003\u001b2\u0002\u0019AA(\u0003)\u0019XOY:de&\u0014WM\u001d\t\u00043\u0006E\u0013\u0002BA*\u0003+\u0012!bU;cg\u000e\u0014\u0018NY3s\u0013\r\t9\u0006\b\u0002\u000e\u0003\u000e$xN]#wK:$()^:\t\u000f\u0005mc\u00031\u0001\u0002*\u0005\u0011Ao\\\u0001\fk:\u001cXOY:de&\u0014W\rF\u0003\u0000\u0003C\n\u0019\u0007C\u0004\u0002N]\u0001\r!a\u0014\t\u000f\u0005\u0015t\u00031\u0001\u0002*\u0005!aM]8n)\rQ\u0013\u0011\u000e\u0005\b\u0003\u001bB\u0002\u0019AA(\u0003a\u0011XmZ5ti\u0016\u0014x+\u001b;i+:\u001cXOY:de&\u0014WM\u001d\u000b\u0006\u007f\u0006=\u0014\u0011\u000f\u0005\u0007\u0003\u001bJ\u0002\u0019\u0001&\t\u000b]J\u0002\u0019A\u001d\u00025Ut'/Z4jgR,'O\u0012:p[Vs7/\u001e2tGJL'-\u001a:\u0015\u000b}\f9(!\u001f\t\r\u00055#\u00041\u0001K\u0011\u00159$\u00041\u0001:%\u0019\ti(!!\u0002\u0006\u001a1\u0011q\u0010\u0001\u0001\u0003w\u0012A\u0002\u0010:fM&tW-\\3oiz\u00022!a!\u0001\u001b\u0005a\"CBAD\u0003\u0013\u000bYI\u0002\u0004\u0002\u0000\u0001\u0001\u0011Q\u0011\t\u0005\u0003\u0007\u000b)\u0006\u0005\u0003\u0002\u0004\u0006=\u0002")
public interface ManagedActorClassification {
    public void akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$mappings_$eq(AtomicReference<ManagedActorClassificationMappings> var1);

    public void akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$empty_$eq(TreeSet<ActorRef> var1);

    public ActorSystem system();

    public AtomicReference<ManagedActorClassificationMappings> akka$event$ManagedActorClassification$$mappings();

    public TreeSet<ActorRef> akka$event$ManagedActorClassification$$empty();

    public static /* synthetic */ ActorRef unsubscriber$(ManagedActorClassification $this) {
        return $this.unsubscriber();
    }

    default public ActorRef unsubscriber() {
        return ActorClassificationUnsubscriber$.MODULE$.start(this.system(), this.toString(), (Function1<ActorRef, BoxedUnit>)(Function1<ActorRef, Object> & Serializable)subscriber -> {
            this.unsubscribe(subscriber);
            return BoxedUnit.UNIT;
        }, ActorClassificationUnsubscriber$.MODULE$.start$default$4());
    }

    public static /* synthetic */ boolean associate$(ManagedActorClassification $this, ActorRef monitored, ActorRef monitor) {
        return $this.associate(monitored, monitor);
    }

    default public boolean associate(ActorRef monitored, ActorRef monitor) {
        Option option;
        while (true) {
            boolean noChange;
            ManagedActorClassificationMappings current;
            if (None$.MODULE$.equals(option = (current = this.akka$event$ManagedActorClassification$$mappings().get()).backing().get(monitored))) {
                ManagedActorClassificationMappings added = current.add(monitored, monitor);
                if (!this.akka$event$ManagedActorClassification$$mappings().compareAndSet(current, added)) continue;
                return this.registerWithUnsubscriber(monitor, added.seqNr());
            }
            if (!(option instanceof Some)) break;
            Some some = (Some)option;
            TreeSet monitors = (TreeSet)some.value();
            if (monitors.contains(monitored)) {
                return false;
            }
            ManagedActorClassificationMappings added = current.add(monitored, monitor);
            Map<ActorRef, TreeSet<ActorRef>> map = current.backing();
            Map<ActorRef, TreeSet<ActorRef>> map2 = added.backing();
            boolean bl = !(map != null ? !map.equals(map2) : map2 != null) ? true : (noChange = false);
            if (noChange) {
                return false;
            }
            if (!this.akka$event$ManagedActorClassification$$mappings().compareAndSet(current, added)) continue;
            return this.registerWithUnsubscriber(monitor, added.seqNr());
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ void dissociate$(ManagedActorClassification $this, ActorRef actor) {
        $this.dissociate(actor);
    }

    default public void dissociate(ActorRef actor) {
        try {
            this.dissociateAsMonitored$1(actor);
        }
        finally {
            this.dissociateAsMonitor$1(actor);
        }
    }

    public static /* synthetic */ boolean dissociate$(ManagedActorClassification $this, ActorRef monitored, ActorRef monitor) {
        return $this.dissociate(monitored, monitor);
    }

    default public boolean dissociate(ActorRef monitored, ActorRef monitor) {
        Option option;
        while (true) {
            ManagedActorClassificationMappings removed;
            ManagedActorClassificationMappings current;
            block6: {
                block5: {
                    if (None$.MODULE$.equals(option = (current = this.akka$event$ManagedActorClassification$$mappings().get()).backing().get(monitored))) {
                        return false;
                    }
                    if (!(option instanceof Some)) break;
                    Some some = (Some)option;
                    TreeSet monitors = (TreeSet)some.value();
                    removed = current.remove(monitored, monitor);
                    TreeSet<ActorRef> removedMonitors = removed.get(monitored);
                    if (monitors.isEmpty()) break block5;
                    TreeSet treeSet = monitors;
                    TreeSet<ActorRef> treeSet2 = removedMonitors;
                    if (treeSet != null ? !((Object)treeSet).equals(treeSet2) : treeSet2 != null) break block6;
                }
                return false;
            }
            if (!this.akka$event$ManagedActorClassification$$mappings().compareAndSet(current, removed)) continue;
            return this.unregisterFromUnsubscriber(monitor, removed.seqNr());
        }
        throw new MatchError(option);
    }

    public ActorRef classify(Object var1);

    public int mapSize();

    public static /* synthetic */ void publish$(ManagedActorClassification $this, Object event) {
        $this.publish(event);
    }

    default public void publish(Object event) {
        Option option = this.akka$event$ManagedActorClassification$$mappings().get().backing().get(this.classify(event));
        if (None$.MODULE$.equals(option)) {
            return;
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            TreeSet refs = (TreeSet)some.value();
            refs.foreach((Function1<ActorRef, Object> & Serializable)x$5 -> {
                ManagedActorClassification.$anonfun$publish$2(event, x$5);
                return BoxedUnit.UNIT;
            });
            return;
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ boolean subscribe$(ManagedActorClassification $this, ActorRef subscriber, ActorRef to) {
        return $this.subscribe(subscriber, to);
    }

    default public boolean subscribe(ActorRef subscriber, ActorRef to) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber is null");
        }
        if (to == null) {
            throw new IllegalArgumentException("Classifier is null");
        }
        return this.associate(to, subscriber);
    }

    public static /* synthetic */ boolean unsubscribe$(ManagedActorClassification $this, ActorRef subscriber, ActorRef from) {
        return $this.unsubscribe(subscriber, from);
    }

    default public boolean unsubscribe(ActorRef subscriber, ActorRef from) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber is null");
        }
        if (from == null) {
            throw new IllegalArgumentException("Classifier is null");
        }
        return this.dissociate(from, subscriber);
    }

    public static /* synthetic */ void unsubscribe$(ManagedActorClassification $this, ActorRef subscriber) {
        $this.unsubscribe(subscriber);
    }

    default public void unsubscribe(ActorRef subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber is null");
        }
        this.dissociate(subscriber);
    }

    public static /* synthetic */ boolean registerWithUnsubscriber$(ManagedActorClassification $this, ActorRef subscriber, int seqNr) {
        return $this.registerWithUnsubscriber(subscriber, seqNr);
    }

    default public boolean registerWithUnsubscriber(ActorRef subscriber, int seqNr) {
        ActorClassificationUnsubscriber.Register x$1 = new ActorClassificationUnsubscriber.Register(subscriber, seqNr);
        ActorRef x$2 = this.unsubscriber().$bang$default$2(x$1);
        this.unsubscriber().$bang(x$1, x$2);
        return true;
    }

    public static /* synthetic */ boolean unregisterFromUnsubscriber$(ManagedActorClassification $this, ActorRef subscriber, int seqNr) {
        return $this.unregisterFromUnsubscriber(subscriber, seqNr);
    }

    default public boolean unregisterFromUnsubscriber(ActorRef subscriber, int seqNr) {
        ActorClassificationUnsubscriber.Unregister x$1 = new ActorClassificationUnsubscriber.Unregister(subscriber, seqNr);
        ActorRef x$2 = this.unsubscriber().$bang$default$2(x$1);
        this.unsubscriber().$bang(x$1, x$2);
        return true;
    }

    private void dissociateAsMonitored$1(ActorRef monitored) {
        block1: {
            BoxedUnit boxedUnit;
            ManagedActorClassificationMappings current;
            while ((current = this.akka$event$ManagedActorClassification$$mappings().get()).backing().contains(monitored)) {
                ManagedActorClassificationMappings removed = current.remove(monitored);
                if (!this.akka$event$ManagedActorClassification$$mappings().compareAndSet(current, removed)) continue;
                boxedUnit = BoxedUnit.UNIT;
                break block1;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    private void dissociateAsMonitor$1(ActorRef monitor) {
        ManagedActorClassificationMappings current = this.akka$event$ManagedActorClassification$$mappings().get();
        Iterator i = current.backing().iterator();
        while (i.hasNext()) {
            TreeSet value;
            Tuple2 tuple2 = (Tuple2)i.next();
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            ActorRef key = (ActorRef)tuple2._1();
            TreeSet value2 = (TreeSet)tuple2._2();
            Tuple2<ActorRef, TreeSet> tuple22 = new Tuple2<ActorRef, TreeSet>(key, value2);
            ActorRef key2 = tuple22._1();
            TreeSet treeSet = value = tuple22._2();
            if (treeSet == null) {
                continue;
            }
            if (!treeSet.contains(monitor)) continue;
            BoxesRunTime.boxToBoolean(this.dissociate(key2, monitor));
        }
    }

    public static /* synthetic */ void $anonfun$publish$2(Object event$2, ActorRef x$5) {
        Object x$1 = event$2;
        ActorRef x$2 = x$5.$bang$default$2(x$1);
        x$5.$bang(x$1, x$2);
    }

    public static void $init$(ManagedActorClassification $this) {
        $this.akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$mappings_$eq(new AtomicReference<ManagedActorClassificationMappings>(new ManagedActorClassificationMappings($this, 0, (Map<ActorRef, TreeSet<ActorRef>>)Predef$.MODULE$.Map().empty())));
        $this.akka$event$ManagedActorClassification$_setter_$akka$event$ManagedActorClassification$$empty_$eq(TreeSet$.MODULE$.empty(Ordering$.MODULE$.ordered(Predef$.MODULE$.$conforms())));
    }

    private class ManagedActorClassificationMappings {
        private final int seqNr;
        private final Map<ActorRef, TreeSet<ActorRef>> backing;
        public final /* synthetic */ ManagedActorClassification $outer;

        public int seqNr() {
            return this.seqNr;
        }

        public Map<ActorRef, TreeSet<ActorRef>> backing() {
            return this.backing;
        }

        public TreeSet<ActorRef> get(ActorRef monitored) {
            return (TreeSet)this.backing().getOrElse(monitored, (Function0<TreeSet> & Serializable)() -> this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer().akka$event$ManagedActorClassification$$empty());
        }

        public ManagedActorClassificationMappings add(ActorRef monitored, ActorRef monitor) {
            TreeSet watchers = (TreeSet)((SetOps)this.backing().get(monitored).getOrElse((Function0<TreeSet> & Serializable)() -> this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer().akka$event$ManagedActorClassification$$empty())).$plus(monitor);
            return new ManagedActorClassificationMappings(this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer(), this.seqNr() + 1, (Map)this.backing().updated(monitored, watchers));
        }

        public ManagedActorClassificationMappings remove(ActorRef monitored, ActorRef monitor) {
            TreeSet monitors = (TreeSet)((SetOps)this.backing().get(monitored).getOrElse((Function0<TreeSet> & Serializable)() -> this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer().akka$event$ManagedActorClassification$$empty())).$minus(monitor);
            return new ManagedActorClassificationMappings(this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer(), this.seqNr() + 1, (Map)this.backing().updated(monitored, monitors));
        }

        public ManagedActorClassificationMappings remove(ActorRef monitored) {
            Map v = (Map)this.backing().$minus(monitored);
            return new ManagedActorClassificationMappings(this.akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer(), this.seqNr() + 1, v);
        }

        public /* synthetic */ ManagedActorClassification akka$event$ManagedActorClassification$ManagedActorClassificationMappings$$$outer() {
            return this.$outer;
        }

        public ManagedActorClassificationMappings(ManagedActorClassification $outer, int seqNr, Map<ActorRef, TreeSet<ActorRef>> backing) {
            this.seqNr = seqNr;
            this.backing = backing;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

