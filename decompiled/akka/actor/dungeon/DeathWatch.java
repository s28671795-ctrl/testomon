/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.Actor;
import akka.actor.ActorCell;
import akka.actor.ActorCell$;
import akka.actor.ActorRef;
import akka.actor.ActorRefScope;
import akka.actor.Address;
import akka.actor.InternalActorRef;
import akka.actor.Terminated;
import akka.actor.Terminated$;
import akka.actor.dungeon.Children;
import akka.actor.dungeon.FaultHandling;
import akka.dispatch.Envelope;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.event.AddressTerminatedTopic;
import akka.event.AddressTerminatedTopic$;
import akka.event.Logging;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005]eA\u0003\u000e\u001c!\u0003\r\taH\u0011\u0002\u0012\")\u0001\u0006\u0001C\u0001U!9a\u0006\u0001a\u0001\n\u0013y\u0003bB#\u0001\u0001\u0004%IA\u0012\u0005\b\u0013\u0002\u0001\r\u0011\"\u0003K\u0011\u001dq\u0005\u00011A\u0005\n=Cq!\u0015\u0001A\u0002\u0013%q\u0006C\u0004S\u0001\u0001\u0007I\u0011B*\t\u000bU\u0003A\u0011\u0001,\t\u000bq\u0003AQI/\t\u000b\u0001\u0004AQI1\t\u000b\u0015\u0004AQ\t4\t\u000b!\u0004A\u0011C5\t\u000b=\u0004A\u0011\u00039\t\rY\u0004A\u0011A\u0010x\u0011\u0015Y\b\u0001\"\u0003}\u0011\u001d\t9\u0001\u0001C\u0005\u0003\u0013Aa!a\u0004\u0001\t#Q\u0003bBA\t\u0001\u0011E\u00111\u0003\u0005\b\u0003W\u0001A\u0011CA\u0017\u0011\u001d\t9\u0004\u0001C\t\u0003sAa!\u001e\u0001\u0005\u0012\u0005}\u0002bBA&\u0001\u0011%\u0011Q\n\u0005\n\u0003c\u0002\u0011\u0013!C\u0005\u0003gBa!!$\u0001\t\u0013Q\u0003BBAH\u0001\u0011%!F\u0001\u0006EK\u0006$\bnV1uG\"T!\u0001H\u000f\u0002\u000f\u0011,hnZ3p]*\u0011adH\u0001\u0006C\u000e$xN\u001d\u0006\u0002A\u0005!\u0011m[6b'\t\u0001!\u0005\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0004\u0001Q\t1\u0006\u0005\u0002$Y%\u0011Q\u0006\n\u0002\u0005+:LG/\u0001\u0005xCR\u001c\u0007.\u001b8h+\u0005\u0001\u0004\u0003B\u00199w}r!A\r\u001c\u0011\u0005M\"S\"\u0001\u001b\u000b\u0005UJ\u0013A\u0002\u001fs_>$h(\u0003\u00028I\u00051\u0001K]3eK\u001aL!!\u000f\u001e\u0003\u00075\u000b\u0007O\u0003\u00028IA\u0011A(P\u0007\u0002;%\u0011a(\b\u0002\t\u0003\u000e$xN\u001d*fMB\u00191\u0005\u0011\"\n\u0005\u0005##AB(qi&|g\u000e\u0005\u0002$\u0007&\u0011A\t\n\u0002\u0004\u0003:L\u0018\u0001D<bi\u000eD\u0017N\\4`I\u0015\fHCA\u0016H\u0011\u001dA5!!AA\u0002A\n1\u0001\u001f\u00132\u0003%9\u0018\r^2iK\u0012\u0014\u00150F\u0001L!\r\tDjO\u0005\u0003\u001bj\u00121aU3u\u000359\u0018\r^2iK\u0012\u0014\u0015p\u0018\u0013fcR\u00111\u0006\u0015\u0005\b\u0011\u0016\t\t\u00111\u0001L\u0003A!XM]7j]\u0006$X\rZ)vKV,G-\u0001\u000buKJl\u0017N\\1uK\u0012\fV/Z;fI~#S-\u001d\u000b\u0003WQCq\u0001S\u0004\u0002\u0002\u0003\u0007\u0001'\u0001\u0006jg^\u000bGo\u00195j]\u001e$\"a\u0016.\u0011\u0005\rB\u0016BA-%\u0005\u001d\u0011un\u001c7fC:DQa\u0017\u0005A\u0002m\n1A]3g\u0003\u00159\u0018\r^2i)\tYd\fC\u0003`\u0013\u0001\u00071(A\u0004tk\nTWm\u0019;\u0002\u0013]\fGo\u00195XSRDGcA\u001ecG\")qL\u0003a\u0001w!)AM\u0003a\u0001\u0005\u0006\u0019Qn]4\u0002\u000fUtw/\u0019;dQR\u00111h\u001a\u0005\u0006?.\u0001\raO\u0001\u0013e\u0016\u001cW-\u001b<fIR+'/\\5oCR,G\r\u0006\u0002,U\")1\u000e\u0004a\u0001Y\u0006\tA\u000f\u0005\u0002=[&\u0011a.\b\u0002\u000b)\u0016\u0014X.\u001b8bi\u0016$\u0017AF<bi\u000eDW\rZ!di>\u0014H+\u001a:nS:\fG/\u001a3\u0015\t-\n(\u000f\u001e\u0005\u0006=5\u0001\ra\u000f\u0005\u0006g6\u0001\raV\u0001\u0013KbL7\u000f^3oG\u0016\u001cuN\u001c4je6,G\rC\u0003v\u001b\u0001\u0007q+A\tbI\u0012\u0014Xm]:UKJl\u0017N\\1uK\u0012\f1\u0003^3s[&t\u0017\r^3e#V,W/\u001a3G_J$2a\u000b=z\u0011\u0015yf\u00021\u0001<\u0011\u0015Qh\u00021\u0001@\u00035\u0019Wo\u001d;p[6+7o]1hK\u0006qQ\u000f\u001d3bi\u0016<\u0016\r^2iS:<G\u0003B\u0016~\u0003\u0007AQaW\bA\u0002y\u0004\"\u0001P@\n\u0007\u0005\u0005QD\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fM\"1\u0011QA\bA\u0002}\n!B\\3x\u001b\u0016\u001c8/Y4f\u0003E\u0019\u0007.Z2l/\u0006$8\r[5oON\u000bW.\u001a\u000b\u0006W\u0005-\u0011Q\u0002\u0005\u00067B\u0001\rA \u0005\u0007\u0003\u000b\u0001\u0002\u0019A \u0002%Q,G\u000e\\,bi\u000eDWM]:XK\u0012KW\rZ\u0001\u0015k:<\u0018\r^2i/\u0006$8\r[3e\u0003\u000e$xN]:\u0015\u0007-\n)\u0002\u0003\u0004\u001f%\u0001\u0007\u0011q\u0003\t\u0004y\u0005e\u0011bAA\u000e;\t)\u0011i\u0019;pe\"\"\u0011QCA\u0010!\u0011\t\t#a\n\u000e\u0005\u0005\r\"bAA\u0013?\u0005!Q\u000f^5m\u0013\u0011\tI#a\t\u0003\rUtWo]3e\u0003)\tG\rZ,bi\u000eDWM\u001d\u000b\u0006W\u0005=\u00121\u0007\u0005\u0007\u0003c\u0019\u0002\u0019A\u001e\u0002\u000f]\fGo\u00195fK\"1\u0011QG\nA\u0002m\nqa^1uG\",'/\u0001\u0006sK6<\u0016\r^2iKJ$RaKA\u001e\u0003{Aa!!\r\u0015\u0001\u0004Y\u0004BBA\u001b)\u0001\u00071\bF\u0002,\u0003\u0003Bq!a\u0011\u0016\u0001\u0004\t)%A\u0004bI\u0012\u0014Xm]:\u0011\u0007q\n9%C\u0002\u0002Ju\u0011q!\u00113ee\u0016\u001c8/A\u0013nC&tG/Y5o\u0003\u0012$'/Z:t)\u0016\u0014X.\u001b8bi\u0016$7+\u001e2tGJL\u0007\u000f^5p]V!\u0011qJA,)\u0011\t\t&!\u001c\u0015\t\u0005M\u00131\r\t\u0005\u0003+\n9\u0006\u0004\u0001\u0005\u000f\u0005ecC1\u0001\u0002\\\t\tA+E\u0002\u0002^\t\u00032aIA0\u0013\r\t\t\u0007\n\u0002\b\u001d>$\b.\u001b8h\u0011!\t)G\u0006CA\u0002\u0005\u001d\u0014!\u00022m_\u000e\\\u0007#B\u0012\u0002j\u0005M\u0013bAA6I\tAAHY=oC6,g\b\u0003\u0005\u0002pY\u0001\n\u00111\u0001<\u0003\u0019\u0019\u0007.\u00198hK\u0006yS.Y5oi\u0006Lg.\u00113ee\u0016\u001c8\u000fV3s[&t\u0017\r^3e'V\u00147o\u0019:jaRLwN\u001c\u0013eK\u001a\fW\u000f\u001c;%cU!\u0011QOAF+\t\t9HK\u0002<\u0003sZ#!a\u001f\u0011\t\u0005u\u0014qQ\u0007\u0003\u0003\u007fRA!!!\u0002\u0004\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u000b#\u0013AC1o]>$\u0018\r^5p]&!\u0011\u0011RA@\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\b\u00033:\"\u0019AA.\u0003q)hn];cg\u000e\u0014\u0018NY3BI\u0012\u0014Xm]:UKJl\u0017N\\1uK\u0012\f!d];cg\u000e\u0014\u0018NY3BI\u0012\u0014Xm]:UKJl\u0017N\\1uK\u0012\u00042\u0001PAJ\u0013\r\t)*\b\u0002\n\u0003\u000e$xN]\"fY2\u0004")
public interface DeathWatch {
    public Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$watching();

    public void akka$actor$dungeon$DeathWatch$$watching_$eq(Map<ActorRef, Option<Object>> var1);

    public Set<ActorRef> akka$actor$dungeon$DeathWatch$$watchedBy();

    public void akka$actor$dungeon$DeathWatch$$watchedBy_$eq(Set<ActorRef> var1);

    public Map<ActorRef, Option<Object>> akka$actor$dungeon$DeathWatch$$terminatedQueued();

    public void akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq(Map<ActorRef, Option<Object>> var1);

    public static /* synthetic */ boolean isWatching$(DeathWatch $this, ActorRef ref) {
        return $this.isWatching(ref);
    }

    default public boolean isWatching(ActorRef ref) {
        return this.akka$actor$dungeon$DeathWatch$$watching().contains(ref);
    }

    public static /* synthetic */ ActorRef watch$(DeathWatch $this, ActorRef subject) {
        return $this.watch(subject);
    }

    default public ActorRef watch(ActorRef subject) {
        ActorRef actorRef2 = subject;
        if (actorRef2 instanceof InternalActorRef) {
            Object object;
            InternalActorRef internalActorRef;
            InternalActorRef internalActorRef2 = internalActorRef = (InternalActorRef)actorRef2;
            InternalActorRef internalActorRef3 = ((ActorCell)this).self();
            if (internalActorRef2 == null ? internalActorRef3 != null : !((Object)internalActorRef2).equals(internalActorRef3)) {
                if (!this.akka$actor$dungeon$DeathWatch$$watching().contains(internalActorRef)) {
                    object = this.maintainAddressTerminatedSubscription(internalActorRef, () -> {
                        internalActorRef.sendSystemMessage(new Watch(internalActorRef, ((ActorCell)this).self()));
                        this.updateWatching(internalActorRef, None$.MODULE$);
                    });
                } else {
                    this.checkWatchingSame(internalActorRef, None$.MODULE$);
                    object = BoxedUnit.UNIT;
                }
            } else {
                object = BoxedUnit.UNIT;
            }
            return internalActorRef;
        }
        throw new IllegalArgumentException(new StringBuilder(26).append("ActorRef is not internal: ").append(actorRef2).toString());
    }

    public static /* synthetic */ ActorRef watchWith$(DeathWatch $this, ActorRef subject, Object msg) {
        return $this.watchWith(subject, msg);
    }

    default public ActorRef watchWith(ActorRef subject, Object msg) {
        ActorRef actorRef2 = subject;
        if (actorRef2 instanceof InternalActorRef) {
            Object object;
            InternalActorRef internalActorRef;
            InternalActorRef internalActorRef2 = internalActorRef = (InternalActorRef)actorRef2;
            InternalActorRef internalActorRef3 = ((ActorCell)this).self();
            if (internalActorRef2 == null ? internalActorRef3 != null : !((Object)internalActorRef2).equals(internalActorRef3)) {
                if (!this.akka$actor$dungeon$DeathWatch$$watching().contains(internalActorRef)) {
                    object = this.maintainAddressTerminatedSubscription(internalActorRef, () -> {
                        internalActorRef.sendSystemMessage(new Watch(internalActorRef, ((ActorCell)this).self()));
                        this.updateWatching(internalActorRef, new Some<Object>(msg));
                    });
                } else {
                    this.checkWatchingSame(internalActorRef, new Some<Object>(msg));
                    object = BoxedUnit.UNIT;
                }
            } else {
                object = BoxedUnit.UNIT;
            }
            return internalActorRef;
        }
        throw new IllegalArgumentException(new StringBuilder(26).append("ActorRef is not internal: ").append(actorRef2).toString());
    }

    public static /* synthetic */ ActorRef unwatch$(DeathWatch $this, ActorRef subject) {
        return $this.unwatch(subject);
    }

    default public ActorRef unwatch(ActorRef subject) {
        ActorRef actorRef2 = subject;
        if (actorRef2 instanceof InternalActorRef) {
            Object object;
            InternalActorRef internalActorRef;
            InternalActorRef internalActorRef2 = internalActorRef = (InternalActorRef)actorRef2;
            InternalActorRef internalActorRef3 = ((ActorCell)this).self();
            if ((internalActorRef2 == null ? internalActorRef3 != null : !((Object)internalActorRef2).equals(internalActorRef3)) && this.akka$actor$dungeon$DeathWatch$$watching().contains(internalActorRef)) {
                internalActorRef.sendSystemMessage(new Unwatch(internalActorRef, ((ActorCell)this).self()));
                object = this.maintainAddressTerminatedSubscription(internalActorRef, () -> this.akka$actor$dungeon$DeathWatch$$watching_$eq((Map)this.akka$actor$dungeon$DeathWatch$$watching().$minus(internalActorRef)));
            } else {
                object = BoxedUnit.UNIT;
            }
            this.akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq((Map)this.akka$actor$dungeon$DeathWatch$$terminatedQueued().$minus(internalActorRef));
            return internalActorRef;
        }
        throw new IllegalArgumentException(new StringBuilder(26).append("ActorRef is not internal: ").append(actorRef2).toString());
    }

    public static /* synthetic */ void receivedTerminated$(DeathWatch $this, Terminated t2) {
        $this.receivedTerminated(t2);
    }

    default public void receivedTerminated(Terminated t2) {
        this.akka$actor$dungeon$DeathWatch$$terminatedQueued().get(t2.actor()).foreach((Function1<Option, Object> & Serializable)optionalMessage -> {
            DeathWatch.$anonfun$receivedTerminated$1(this, t2, optionalMessage);
            return BoxedUnit.UNIT;
        });
    }

    public static /* synthetic */ void watchedActorTerminated$(DeathWatch $this, ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        $this.watchedActorTerminated(actor, existenceConfirmed, addressTerminated);
    }

    default public void watchedActorTerminated(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        Option option = this.akka$actor$dungeon$DeathWatch$$watching().get(actor);
        if (None$.MODULE$.equals(option)) {
        } else if (option instanceof Some) {
            Some some = (Some)option;
            Option optionalMessage = (Option)some.value();
            this.maintainAddressTerminatedSubscription(actor, () -> this.akka$actor$dungeon$DeathWatch$$watching_$eq((Map)this.akka$actor$dungeon$DeathWatch$$watching().$minus(actor)));
            if (!((Children)((Object)this)).isTerminating()) {
                ((ActorCell)this).self().tell(Terminated$.MODULE$.apply(actor, existenceConfirmed, addressTerminated), actor);
                this.terminatedQueuedFor(actor, optionalMessage);
            }
        } else {
            throw new MatchError(option);
        }
        if (((Children)((Object)this)).childrenRefs().getByRef(actor).isDefined()) {
            ((FaultHandling)((Object)this)).handleChildTerminated(actor);
            return;
        }
    }

    public static /* synthetic */ void terminatedQueuedFor$(DeathWatch $this, ActorRef subject, Option customMessage) {
        $this.terminatedQueuedFor(subject, customMessage);
    }

    default public void terminatedQueuedFor(ActorRef subject, Option<Object> customMessage) {
        if (!this.akka$actor$dungeon$DeathWatch$$terminatedQueued().contains(subject)) {
            this.akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq((Map)this.akka$actor$dungeon$DeathWatch$$terminatedQueued().$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(subject), customMessage)));
            return;
        }
    }

    private void updateWatching(InternalActorRef ref, Option<Object> newMessage) {
        this.akka$actor$dungeon$DeathWatch$$watching_$eq((Map)this.akka$actor$dungeon$DeathWatch$$watching().updated(ref, newMessage));
    }

    private void checkWatchingSame(InternalActorRef ref, Option<Object> newMessage) {
        Option previous;
        Option option = previous = (Option)this.akka$actor$dungeon$DeathWatch$$watching().apply(ref);
        Option<Object> option2 = newMessage;
        if (option == null ? option2 != null : !option.equals(option2)) {
            throw new IllegalStateException(new StringBuilder(157).append("Watch(").append(((ActorCell)this).self()).append(", ").append(ref).append(") termination message was not overwritten from [").append(previous).append("] to [").append(newMessage).append("]. ").append("If this was intended, unwatch first before using `watch` / `watchWith` with another message.").toString());
        }
    }

    public static /* synthetic */ void tellWatchersWeDied$(DeathWatch $this) {
        $this.tellWatchersWeDied();
    }

    default public void tellWatchersWeDied() {
        if (this.akka$actor$dungeon$DeathWatch$$watchedBy().nonEmpty()) {
            try {
                this.akka$actor$dungeon$DeathWatch$$watchedBy().foreach((Function1<ActorRef, Object> & Serializable)watcher -> {
                    this.sendTerminated$1(false, watcher);
                    return BoxedUnit.UNIT;
                });
                this.akka$actor$dungeon$DeathWatch$$watchedBy().foreach((Function1<ActorRef, Object> & Serializable)watcher -> {
                    this.sendTerminated$1(true, watcher);
                    return BoxedUnit.UNIT;
                });
            }
            finally {
                this.maintainAddressTerminatedSubscription(this.maintainAddressTerminatedSubscription$default$1(), () -> this.akka$actor$dungeon$DeathWatch$$watchedBy_$eq(ActorCell$.MODULE$.emptyActorRefSet()));
            }
            return;
        }
    }

    public static /* synthetic */ void unwatchWatchedActors$(DeathWatch $this, Actor actor) {
        $this.unwatchWatchedActors(actor);
    }

    default public void unwatchWatchedActors(Actor actor) {
        if (this.akka$actor$dungeon$DeathWatch$$watching().nonEmpty()) {
            this.maintainAddressTerminatedSubscription(this.maintainAddressTerminatedSubscription$default$1(), () -> {
                try {
                    this.akka$actor$dungeon$DeathWatch$$watching().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                        DeathWatch.$anonfun$unwatchWatchedActors$2(this, x0$1);
                        return BoxedUnit.UNIT;
                    });
                }
                finally {
                    this.akka$actor$dungeon$DeathWatch$$watching_$eq((Map<ActorRef, Option<Object>>)Predef$.MODULE$.Map().empty());
                    this.akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq((Map<ActorRef, Option<Object>>)Predef$.MODULE$.Map().empty());
                }
            });
            return;
        }
    }

    public static /* synthetic */ void addWatcher$(DeathWatch $this, ActorRef watchee, ActorRef watcher) {
        $this.addWatcher(watchee, watcher);
    }

    default public void addWatcher(ActorRef watchee, ActorRef watcher) {
        boolean watcherSelf;
        ActorRef actorRef2 = watchee;
        InternalActorRef internalActorRef = ((ActorCell)this).self();
        boolean watcheeSelf = !(actorRef2 != null ? !((Object)actorRef2).equals(internalActorRef) : internalActorRef != null);
        ActorRef actorRef3 = watcher;
        InternalActorRef internalActorRef2 = ((ActorCell)this).self();
        boolean bl = !(actorRef3 != null ? !((Object)actorRef3).equals(internalActorRef2) : internalActorRef2 != null) ? true : (watcherSelf = false);
        if (watcheeSelf && !watcherSelf) {
            if (!this.akka$actor$dungeon$DeathWatch$$watchedBy().contains(watcher)) {
                this.maintainAddressTerminatedSubscription(watcher, () -> {
                    this.akka$actor$dungeon$DeathWatch$$watchedBy_$eq((Set)this.akka$actor$dungeon$DeathWatch$$watchedBy().$plus(watcher));
                    if (((ActorCell)this).system().settings().DebugLifecycle()) {
                        ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(15).append("now watched by ").append(watcher).toString()));
                        return;
                    }
                });
                return;
            }
            return;
        }
        if (!watcheeSelf && watcherSelf) {
            this.watch(watchee);
            return;
        }
        ((ActorCell)this).publish(new Logging.Warning(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("BUG: illegal Watch(%s,%s) for %s"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{watchee, watcher, ((ActorCell)this).self()}))));
    }

    public static /* synthetic */ void remWatcher$(DeathWatch $this, ActorRef watchee, ActorRef watcher) {
        $this.remWatcher(watchee, watcher);
    }

    default public void remWatcher(ActorRef watchee, ActorRef watcher) {
        boolean watcherSelf;
        ActorRef actorRef2 = watchee;
        InternalActorRef internalActorRef = ((ActorCell)this).self();
        boolean watcheeSelf = !(actorRef2 != null ? !((Object)actorRef2).equals(internalActorRef) : internalActorRef != null);
        ActorRef actorRef3 = watcher;
        InternalActorRef internalActorRef2 = ((ActorCell)this).self();
        boolean bl = !(actorRef3 != null ? !((Object)actorRef3).equals(internalActorRef2) : internalActorRef2 != null) ? true : (watcherSelf = false);
        if (watcheeSelf && !watcherSelf) {
            if (this.akka$actor$dungeon$DeathWatch$$watchedBy().contains(watcher)) {
                this.maintainAddressTerminatedSubscription(watcher, () -> {
                    this.akka$actor$dungeon$DeathWatch$$watchedBy_$eq((Set)this.akka$actor$dungeon$DeathWatch$$watchedBy().$minus(watcher));
                    if (((ActorCell)this).system().settings().DebugLifecycle()) {
                        ((ActorCell)this).publish(new Logging.Debug(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), new StringBuilder(21).append("no longer watched by ").append(watcher).toString()));
                        return;
                    }
                });
                return;
            }
            return;
        }
        if (!watcheeSelf && watcherSelf) {
            this.unwatch(watchee);
            return;
        }
        ((ActorCell)this).publish(new Logging.Warning(((ActorCell)this).self().path().toString(), ((ActorCell)this).clazz(((ActorCell)this).actor()), StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("BUG: illegal Unwatch(%s,%s) for %s"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{watchee, watcher, ((ActorCell)this).self()}))));
    }

    public static /* synthetic */ void addressTerminated$(DeathWatch $this, Address address) {
        $this.addressTerminated(address);
    }

    default public void addressTerminated(Address address) {
        this.maintainAddressTerminatedSubscription(this.maintainAddressTerminatedSubscription$default$1(), () -> this.akka$actor$dungeon$DeathWatch$$watchedBy().withFilter((Function1<ActorRef, Object> & Serializable)a -> BoxesRunTime.boxToBoolean(DeathWatch.$anonfun$addressTerminated$2(address, a))).foreach((Function1<ActorRef, Object> & Serializable)a -> {
            this.akka$actor$dungeon$DeathWatch$$watchedBy_$eq((Set)this.akka$actor$dungeon$DeathWatch$$watchedBy().$minus(a));
            return BoxedUnit.UNIT;
        }));
        ((IterableOps.WithFilter)this.akka$actor$dungeon$DeathWatch$$watching().withFilter((Function1<Tuple2<ActorRef, Option<Object>>, Object>)(Function1<Tuple2, Object> & Serializable)check$ifrefutable$1 -> BoxesRunTime.boxToBoolean(DeathWatch.$anonfun$addressTerminated$4(check$ifrefutable$1))).withFilter((Function1<Tuple2, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(DeathWatch.$anonfun$addressTerminated$5(address, x$1)))).foreach((Function1<Tuple2, Object> & Serializable)x$2 -> {
            DeathWatch.$anonfun$addressTerminated$6(this, x$2);
            return BoxedUnit.UNIT;
        });
    }

    private <T> T maintainAddressTerminatedSubscription(ActorRef change, Function0<T> block) {
        if (DeathWatch.isNonLocal$1(change)) {
            boolean had = this.hasNonLocalAddress$1();
            T result = block.apply();
            boolean has = this.hasNonLocalAddress$1();
            if (had && !has) {
                this.unsubscribeAddressTerminated();
            } else if (!had && has) {
                this.subscribeAddressTerminated();
            }
            return result;
        }
        return block.apply();
    }

    private <T> ActorRef maintainAddressTerminatedSubscription$default$1() {
        return null;
    }

    private void unsubscribeAddressTerminated() {
        ((AddressTerminatedTopic)AddressTerminatedTopic$.MODULE$.apply(((ActorCell)this).system())).unsubscribe(((ActorCell)this).self());
    }

    private void subscribeAddressTerminated() {
        ((AddressTerminatedTopic)AddressTerminatedTopic$.MODULE$.apply(((ActorCell)this).system())).subscribe(((ActorCell)this).self());
    }

    public static /* synthetic */ void $anonfun$receivedTerminated$1(DeathWatch $this, Terminated t$1, Option optionalMessage) {
        $this.akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq((Map)$this.akka$actor$dungeon$DeathWatch$$terminatedQueued().$minus(t$1.actor()));
        Option option = optionalMessage;
        if (option instanceof Some) {
            Some some = (Some)option;
            Object customTermination = some.value();
            Envelope qual$1 = ((ActorCell)$this).currentMessage();
            Object x$1 = customTermination;
            ActorRef x$2 = qual$1.copy$default$2();
            ((ActorCell)$this).currentMessage_$eq(qual$1.copy(x$1, x$2));
            ((ActorCell)$this).receiveMessage(customTermination);
            return;
        }
        if (None$.MODULE$.equals(option)) {
            ((ActorCell)$this).receiveMessage(t$1);
            return;
        }
        throw new MatchError(option);
    }

    private void sendTerminated$1(boolean ifLocal, ActorRef watcher) {
        if (((ActorRefScope)((Object)watcher)).isLocal() == ifLocal) {
            ActorRef actorRef2 = watcher;
            InternalActorRef internalActorRef = ((ActorCell)this).parent();
            if (actorRef2 == null ? internalActorRef != null : !((Object)actorRef2).equals(internalActorRef)) {
                ((InternalActorRef)watcher).sendSystemMessage(new DeathWatchNotification(((ActorCell)this).self(), true, false));
                return;
            }
        }
    }

    public static /* synthetic */ void $anonfun$unwatchWatchedActors$2(DeathWatch $this, Tuple2 x0$1) {
        ActorRef watchee;
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null && (watchee = (ActorRef)tuple2._1()) instanceof InternalActorRef) {
            InternalActorRef internalActorRef = (InternalActorRef)watchee;
            internalActorRef.sendSystemMessage(new Unwatch(internalActorRef, ((ActorCell)$this).self()));
            return;
        }
        if (tuple2 != null) {
            ActorRef watchee2 = (ActorRef)tuple2._1();
            throw new IllegalStateException(new StringBuilder(37).append("Expected InternalActorRef, but got [").append(watchee2.getClass().getName()).append("]").toString());
        }
        throw new MatchError(tuple2);
    }

    public static /* synthetic */ boolean $anonfun$addressTerminated$2(Address address$1, ActorRef a) {
        Address address = a.path().address();
        Address address2 = address$1;
        return !(address != null ? !((Object)address).equals(address2) : address2 != null);
    }

    public static /* synthetic */ boolean $anonfun$addressTerminated$4(Tuple2 check$ifrefutable$1) {
        Tuple2 tuple2 = check$ifrefutable$1;
        return tuple2 != null;
    }

    public static /* synthetic */ boolean $anonfun$addressTerminated$5(Address address$1, Tuple2 x$1) {
        Tuple2 tuple2 = x$1;
        if (tuple2 != null) {
            ActorRef a = (ActorRef)tuple2._1();
            Address address = a.path().address();
            Address address2 = address$1;
            return !(address != null ? !((Object)address).equals(address2) : address2 != null);
        }
        throw new MatchError(tuple2);
    }

    public static /* synthetic */ void $anonfun$addressTerminated$6(DeathWatch $this, Tuple2 x$2) {
        Tuple2 tuple2 = x$2;
        if (tuple2 != null) {
            ActorRef a = (ActorRef)tuple2._1();
            ((ActorCell)$this).self().sendSystemMessage(new DeathWatchNotification(a, ((Children)((Object)$this)).childrenRefs().getByRef(a).isDefined(), true));
            return;
        }
        throw new MatchError(tuple2);
    }

    private static boolean isNonLocal$1(ActorRef ref) {
        InternalActorRef internalActorRef;
        ActorRef actorRef2 = ref;
        if (actorRef2 == null) {
            return true;
        }
        return actorRef2 instanceof InternalActorRef && !(internalActorRef = (InternalActorRef)actorRef2).isLocal();
    }

    private boolean hasNonLocalAddress$1() {
        return this.akka$actor$dungeon$DeathWatch$$watching().keysIterator().exists((Function1<ActorRef, Object> & Serializable)ref -> BoxesRunTime.boxToBoolean(DeathWatch.isNonLocal$1(ref))) || this.akka$actor$dungeon$DeathWatch$$watchedBy().exists((Function1<ActorRef, Object> & Serializable)ref -> BoxesRunTime.boxToBoolean(DeathWatch.isNonLocal$1(ref)));
    }

    public static void $init$(DeathWatch $this) {
        $this.akka$actor$dungeon$DeathWatch$$watching_$eq((Map<ActorRef, Option<Object>>)Predef$.MODULE$.Map().empty());
        $this.akka$actor$dungeon$DeathWatch$$watchedBy_$eq(ActorCell$.MODULE$.emptyActorRefSet());
        $this.akka$actor$dungeon$DeathWatch$$terminatedQueued_$eq((Map<ActorRef, Option<Object>>)Predef$.MODULE$.Map().empty());
    }
}

