/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.event.ActorEventBus;
import akka.event.EventStreamUnsubscriber;
import akka.event.EventStreamUnsubscriber$;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.LoggingBus;
import akka.event.SubchannelClassification;
import akka.util.ReentrantGuard;
import akka.util.Subclassification;
import akka.util.SubclassifiedIndex;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

@ScalaSignature(bytes="\u0006\u0005\u0005Ed\u0001B\u000b\u0017\u0001mA\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\ta\u0001\u0011)\u0019!C\u0005c!AQ\u0007\u0001B\u0001B\u0003%!\u0007C\u00037\u0001\u0011\u0005q\u0007C\u00037\u0001\u0011\u00051(\u0002\u0003>\u0001\u0001qT\u0001B!\u0001\u0001\tCqA\u0016\u0001C\u0002\u0013%q\u000b\u0003\u0004t\u0001\u0001\u0006I\u0001\u0017\u0005\bi\u0002\u0011\r\u0011b\u0005v\u0011\u0019i\b\u0001)A\u0005m\")a\u0010\u0001C\t\u007f\"9\u0011Q\u0002\u0001\u0005\u0012\u0005=\u0001bBA\u000f\u0001\u0011\u0005\u0013q\u0004\u0005\b\u0003c\u0001A\u0011IA\u001a\u0011\u001d\t\t\u0004\u0001C!\u0003\u0007Bq!a\u0012\u0001\t\u0003\tI\u0005\u0003\u0005\u0002L\u0001!)\u0001GA'\u0011\u001d\t\t\u0007\u0001C\u0005\u0003GBq!!\u001b\u0001\t\u0013\tYGA\u0006Fm\u0016tGo\u0015;sK\u0006l'BA\f\u0019\u0003\u0015)g/\u001a8u\u0015\u0005I\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u00019\t2\u0003CA\u000f!\u001b\u0005q\"\"A\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0005r\"AB!osJ+g\r\u0005\u0002$I5\ta#\u0003\u0002&-\tQAj\\4hS:<')^:\u0011\u0005\r:\u0013B\u0001\u0015\u0017\u0005a\u0019VOY2iC:tW\r\\\"mCN\u001c\u0018NZ5dCRLwN\\\u0001\u0004gf\u001c\bCA\u0016/\u001b\u0005a#BA\u0017\u0019\u0003\u0015\t7\r^8s\u0013\tyCFA\u0006BGR|'oU=ti\u0016l\u0017!\u00023fEV<W#\u0001\u001a\u0011\u0005u\u0019\u0014B\u0001\u001b\u001f\u0005\u001d\u0011un\u001c7fC:\fa\u0001Z3ck\u001e\u0004\u0013A\u0002\u001fj]&$h\bF\u00029si\u0002\"a\t\u0001\t\u000b%\"\u0001\u0019\u0001\u0016\t\u000bA\"\u0001\u0019\u0001\u001a\u0015\u0005ab\u0004\"B\u0015\u0006\u0001\u0004Q#!B#wK:$\bCA\u000f@\u0013\t\u0001eDA\u0002B]f\u0014!b\u00117bgNLg-[3sa\t\u0019\u0005\u000bE\u0002E\u0017:s!!R%\u0011\u0005\u0019sR\"A$\u000b\u0005!S\u0012A\u0002\u001fs_>$h(\u0003\u0002K=\u00051\u0001K]3eK\u001aL!\u0001T'\u0003\u000b\rc\u0017m]:\u000b\u0005)s\u0002CA(Q\u0019\u0001!\u0011\"U\u0004\u0002\u0002\u0003\u0005)\u0011\u0001*\u0003\u0007}#\u0013'\u0005\u0002T}A\u0011Q\u0004V\u0005\u0003+z\u0011qAT8uQ&tw-A\u0011j]&$\u0018.\u00197msN+(m]2sS\n,Gm\u0014:V]N,(m]2sS\n,'/F\u0001Y!\rI&\rZ\u0007\u00025*\u00111\fX\u0001\u0007CR|W.[2\u000b\u0005us\u0016AC2p]\u000e,(O]3oi*\u0011q\fY\u0001\u0005kRLGNC\u0001b\u0003\u0011Q\u0017M^1\n\u0005\rT&aD!u_6L7MU3gKJ,gnY3\u0011\t\u0015TW\u000e\u001d\b\u0003M\"t!AR4\n\u0003}I!!\u001b\u0010\u0002\u000fA\f7m[1hK&\u00111\u000e\u001c\u0002\u0007\u000b&$\b.\u001a:\u000b\u0005%t\u0002c\u0001#oa&\u0011q.\u0014\u0002\u0004'\u0016$\bCA\u0016r\u0013\t\u0011HF\u0001\u0005BGR|'OU3g\u0003\tJg.\u001b;jC2d\u0017pU;cg\u000e\u0014\u0018NY3e\u001fJ,fn];cg\u000e\u0014\u0018NY3sA\u0005\t2/\u001e2dY\u0006\u001c8/\u001b4jG\u0006$\u0018n\u001c8\u0016\u0003Y\u00042a^=|\u001b\u0005A(BA0\u0019\u0013\tQ\bPA\tTk\n\u001cG.Y:tS\u001aL7-\u0019;j_:\u0004\"\u0001`\u0004\u000e\u0003\u0001\t!c];cG2\f7o]5gS\u000e\fG/[8oA\u0005A1\r\\1tg&4\u0017\u0010\u0006\u0003\u0002\u0002\u0005-\u0001\u0007BA\u0002\u0003\u000f\u0001B\u0001R&\u0002\u0006A\u0019q*a\u0002\u0005\u0015\u0005%A\"!A\u0001\u0002\u000b\u0005!KA\u0002`I]BQa\u0006\u0007A\u0002y\nq\u0001];cY&\u001c\b\u000e\u0006\u0004\u0002\u0012\u0005]\u0011\u0011\u0004\t\u0004;\u0005M\u0011bAA\u000b=\t!QK\\5u\u0011\u00159R\u00021\u0001?\u0011\u0019\tY\"\u0004a\u0001a\u0006Q1/\u001e2tGJL'-\u001a:\u0002\u0013M,(m]2sS\n,G#\u0002\u001a\u0002\"\u0005\r\u0002BBA\u000e\u001d\u0001\u0007\u0001\u000fC\u0004\u0002&9\u0001\r!a\n\u0002\u000f\rD\u0017M\u001c8fYB\"\u0011\u0011FA\u0017!\u0011!5*a\u000b\u0011\u0007=\u000bi\u0003B\u0006\u00020\u0005\r\u0012\u0011!A\u0001\u0006\u0003\u0011&aA0%q\u0005YQO\\:vEN\u001c'/\u001b2f)\u0015\u0011\u0014QGA\u001c\u0011\u0019\tYb\u0004a\u0001a\"9\u0011QE\bA\u0002\u0005e\u0002\u0007BA\u001e\u0003\u007f\u0001B\u0001R&\u0002>A\u0019q*a\u0010\u0005\u0017\u0005\u0005\u0013qGA\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0004?\u0012JD\u0003BA\t\u0003\u000bBa!a\u0007\u0011\u0001\u0004\u0001\u0018!E:uCJ$XK\\:vEN\u001c'/\u001b2feR\u0011\u0011\u0011C\u0001\u0011S:LG/\u00168tk\n\u001c8M]5cKJ$2AMA(\u0011\u0019\t\tF\u0005a\u0001a\u0006aQO\\:vEN\u001c'/\u001b2fe\"\u001a!#!\u0016\u0011\t\u0005]\u0013QL\u0007\u0003\u00033R1!a\u0017\u001f\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003?\nIFA\u0004uC&d'/Z2\u00021I,w-[:uKJ<\u0016\u000e\u001e5V]N,(m]2sS\n,'\u000f\u0006\u0003\u0002\u0012\u0005\u0015\u0004BBA\u000e'\u0001\u0007\u0001\u000fK\u0002\u0014\u0003+\nA%\u001e8sK\u001eL7\u000f^3s\u0013\u001atu.T8sKN+(m]2sS\n,Gm\u00115b]:,Gn\u001d\u000b\u0005\u0003#\ti\u0007\u0003\u0004\u0002\u001cQ\u0001\r\u0001\u001d\u0015\u0004)\u0005U\u0003")
public class EventStream
implements LoggingBus,
SubchannelClassification {
    private final ActorSystem sys;
    private final boolean debug;
    private final AtomicReference<Either<Set<ActorRef>, ActorRef>> initiallySubscribedOrUnsubscriber;
    private final Subclassification<Class<?>> subclassification;
    private SubclassifiedIndex<Class<?>, ActorRef> akka$event$SubchannelClassification$$subscriptions;
    private volatile Map<Class<?>, Set<ActorRef>> akka$event$SubchannelClassification$$cache;
    private ReentrantGuard akka$event$LoggingBus$$guard;
    private Seq<ActorRef> akka$event$LoggingBus$$loggers;
    private volatile int akka$event$LoggingBus$$_logLevel;
    private volatile boolean bitmap$0;

    @Override
    public void publish(Object event) {
        SubchannelClassification.publish$(this, event);
    }

    @Override
    public boolean hasSubscriptions(Object subscriber) {
        return SubchannelClassification.hasSubscriptions$(this, subscriber);
    }

    @Override
    public int logLevel() {
        return LoggingBus.logLevel$(this);
    }

    @Override
    public void setLogLevel(int level) {
        LoggingBus.setLogLevel$(this, level);
    }

    @Override
    public void startStdoutLogger(ActorSystem.Settings config) {
        LoggingBus.startStdoutLogger$(this, config);
    }

    @Override
    public void startDefaultLoggers(ActorSystemImpl system) {
        LoggingBus.startDefaultLoggers$(this, system);
    }

    @Override
    public void stopDefaultLoggers(ActorSystem system) {
        LoggingBus.stopDefaultLoggers$(this, system);
    }

    @Override
    public int compareSubscribers(ActorRef a, ActorRef b) {
        return ActorEventBus.compareSubscribers$(this, a, b);
    }

    private SubclassifiedIndex<Class<?>, ActorRef> akka$event$SubchannelClassification$$subscriptions$lzycompute() {
        EventStream eventStream = this;
        synchronized (eventStream) {
            if (!this.bitmap$0) {
                this.akka$event$SubchannelClassification$$subscriptions = SubchannelClassification.akka$event$SubchannelClassification$$subscriptions$(this);
                this.bitmap$0 = true;
            }
        }
        return this.akka$event$SubchannelClassification$$subscriptions;
    }

    public SubclassifiedIndex<Class<?>, ActorRef> akka$event$SubchannelClassification$$subscriptions() {
        if (!this.bitmap$0) {
            return this.akka$event$SubchannelClassification$$subscriptions$lzycompute();
        }
        return this.akka$event$SubchannelClassification$$subscriptions;
    }

    public Map<Class<?>, Set<ActorRef>> akka$event$SubchannelClassification$$cache() {
        return this.akka$event$SubchannelClassification$$cache;
    }

    public void akka$event$SubchannelClassification$$cache_$eq(Map<Class<?>, Set<ActorRef>> x$1) {
        this.akka$event$SubchannelClassification$$cache = x$1;
    }

    @Override
    public ReentrantGuard akka$event$LoggingBus$$guard() {
        return this.akka$event$LoggingBus$$guard;
    }

    @Override
    public Seq<ActorRef> akka$event$LoggingBus$$loggers() {
        return this.akka$event$LoggingBus$$loggers;
    }

    @Override
    public void akka$event$LoggingBus$$loggers_$eq(Seq<ActorRef> x$1) {
        this.akka$event$LoggingBus$$loggers = x$1;
    }

    @Override
    public int akka$event$LoggingBus$$_logLevel() {
        return this.akka$event$LoggingBus$$_logLevel;
    }

    @Override
    public void akka$event$LoggingBus$$_logLevel_$eq(int x$1) {
        this.akka$event$LoggingBus$$_logLevel = x$1;
    }

    @Override
    public final void akka$event$LoggingBus$_setter_$akka$event$LoggingBus$$guard_$eq(ReentrantGuard x$1) {
        this.akka$event$LoggingBus$$guard = x$1;
    }

    private boolean debug() {
        return this.debug;
    }

    private AtomicReference<Either<Set<ActorRef>, ActorRef>> initiallySubscribedOrUnsubscriber() {
        return this.initiallySubscribedOrUnsubscriber;
    }

    public Subclassification<Class<?>> subclassification() {
        return this.subclassification;
    }

    @Override
    public Class<?> classify(Object event) {
        return event.getClass();
    }

    public void publish(Object event, ActorRef subscriber) {
        if (this.sys == null && subscriber.isTerminated()) {
            this.unsubscribe(subscriber);
            return;
        }
        Object x$1 = event;
        ActorRef x$2 = subscriber.$bang$default$2(x$1);
        subscriber.$bang(x$1, x$2);
    }

    public boolean subscribe(ActorRef subscriber, Class<?> channel) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber is null");
        }
        if (this.debug()) {
            this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), new StringBuilder(24).append("subscribing ").append(subscriber).append(" to channel ").append(channel).toString()));
        }
        this.registerWithUnsubscriber(subscriber);
        return SubchannelClassification.subscribe$(this, subscriber, channel);
    }

    public boolean unsubscribe(ActorRef subscriber, Class<?> channel) {
        boolean ret;
        block1: {
            if (subscriber == null) {
                throw new IllegalArgumentException("subscriber is null");
            }
            ret = SubchannelClassification.unsubscribe$(this, subscriber, channel);
            this.unregisterIfNoMoreSubscribedChannels(subscriber);
            if (!this.debug()) break block1;
            this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), new StringBuilder(28).append("unsubscribing ").append(subscriber).append(" from channel ").append(channel).toString()));
        }
        return ret;
    }

    public void unsubscribe(ActorRef subscriber) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber is null");
        }
        SubchannelClassification.unsubscribe$(this, subscriber);
        this.unregisterIfNoMoreSubscribedChannels(subscriber);
        if (this.debug()) {
            this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), new StringBuilder(32).append("unsubscribing ").append(subscriber).append(" from all channels").toString()));
            return;
        }
    }

    public void startUnsubscriber() {
        if (this.sys != null) {
            EventStreamUnsubscriber$.MODULE$.start(this.sys, this);
            return;
        }
    }

    public final boolean initUnsubscriber(ActorRef unsubscriber) {
        Either<Set<ActorRef>, ActorRef> either;
        while (true) {
            if (this.sys == null) {
                return false;
            }
            either = this.initiallySubscribedOrUnsubscriber().get();
            if (!(either instanceof Left)) break;
            Left left = (Left)either;
            Set subscribers = (Set)left.value();
            if (!this.initiallySubscribedOrUnsubscriber().compareAndSet(left, package$.MODULE$.Right().apply(unsubscriber))) continue;
            if (this.debug()) {
                this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), new StringBuilder(71).append("initialized unsubscriber to: ").append(unsubscriber).append(", registering ").append(subscribers.size()).append(" initial subscribers with it").toString()));
            }
            subscribers.foreach((Function1<ActorRef, Object> & Serializable)subscriber -> {
                this.registerWithUnsubscriber(subscriber);
                return BoxedUnit.UNIT;
            });
            return true;
        }
        if (either instanceof Right) {
            Right right = (Right)either;
            ActorRef presentUnsubscriber = (ActorRef)right.value();
            if (this.debug()) {
                this.publish(new Logging.Debug(Logging$.MODULE$.simpleName(this), this.getClass(), new StringBuilder(58).append("not using unsubscriber ").append(unsubscriber).append(", because already initialized with ").append(presentUnsubscriber).toString()));
            }
            return false;
        }
        throw new MatchError(either);
    }

    private void registerWithUnsubscriber(ActorRef subscriber) {
        block5: {
            BoxedUnit boxedUnit;
            while (this.sys != null) {
                Either<Set<ActorRef>, ActorRef> either = this.initiallySubscribedOrUnsubscriber().get();
                if (either instanceof Left) {
                    Left left = (Left)either;
                    Set subscribers = (Set)left.value();
                    if (!this.initiallySubscribedOrUnsubscriber().compareAndSet(left, package$.MODULE$.Left().apply(subscribers.$plus(subscriber)))) continue;
                } else if (either instanceof Right) {
                    Right right = (Right)either;
                    ActorRef unsubscriber = (ActorRef)right.value();
                    EventStreamUnsubscriber.Register x$1 = new EventStreamUnsubscriber.Register(subscriber);
                    ActorRef x$2 = unsubscriber.$bang$default$2(x$1);
                    unsubscriber.$bang(x$1, x$2);
                } else {
                    throw new MatchError(either);
                }
                boxedUnit = BoxedUnit.UNIT;
                break block5;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    private void unregisterIfNoMoreSubscribedChannels(ActorRef subscriber) {
        block5: {
            BoxedUnit boxedUnit;
            while (this.sys != null) {
                Either<Set<ActorRef>, ActorRef> either = this.initiallySubscribedOrUnsubscriber().get();
                if (either instanceof Left) {
                    Left left = (Left)either;
                    Set subscribers = (Set)left.value();
                    if (!this.initiallySubscribedOrUnsubscriber().compareAndSet(left, package$.MODULE$.Left().apply(subscribers.$minus(subscriber)))) continue;
                } else if (either instanceof Right) {
                    Right right = (Right)either;
                    ActorRef unsubscriber = (ActorRef)right.value();
                    EventStreamUnsubscriber.UnregisterIfNoMoreSubscribedChannels x$1 = new EventStreamUnsubscriber.UnregisterIfNoMoreSubscribedChannels(subscriber);
                    ActorRef x$2 = unsubscriber.$bang$default$2(x$1);
                    unsubscriber.$bang(x$1, x$2);
                } else {
                    throw new MatchError(either);
                }
                boxedUnit = BoxedUnit.UNIT;
                break block5;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public EventStream(ActorSystem sys, boolean debug) {
        this.sys = sys;
        this.debug = debug;
        ActorEventBus.$init$(this);
        LoggingBus.$init$(this);
        SubchannelClassification.$init$(this);
        this.initiallySubscribedOrUnsubscriber = new AtomicReference(package$.MODULE$.Left().apply(Predef$.MODULE$.Set().empty()));
        this.subclassification = new Subclassification<Class<?>>(null){

            public boolean isEqual(Class<?> x, Class<?> y) {
                Class<?> clazz = x;
                Class<?> clazz2 = y;
                return !(clazz != null ? !clazz.equals(clazz2) : clazz2 != null);
            }

            public boolean isSubclass(Class<?> x, Class<?> y) {
                return y.isAssignableFrom(x);
            }
        };
        Statics.releaseFence();
    }

    public EventStream(ActorSystem sys) {
        this(sys, false);
    }
}

