/*
 * Decompiled with CFR 0.152.
 */
package akka.event.japi;

import akka.event.SubchannelClassification;
import akka.event.japi.EventBus;
import akka.event.japi.SubchannelEventBus$;
import akka.util.Subclassification;
import akka.util.SubclassifiedIndex;
import scala.collection.immutable.Map;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4Qa\u0003\u0007\u0002\u0002MAQ\u0001\r\u0001\u0005\u0002EBqa\r\u0001C\u0002\u0013%A\u0007\u0003\u00049\u0001\u0001\u0006I!\u000e\u0005\u0006\u000b\u00021\tA\u0012\u0005\u0006\u001b\u00021\tB\u0014\u0005\u0006!\u00021\t\"\u0015\u0005\u00061\u0002!\t%\u0017\u0005\u0006A\u0002!\t%\u0019\u0005\u0006A\u0002!\t%\u001a\u0005\u0006!\u0002!\te\u001a\u0002\u0013'V\u00147\r[1o]\u0016dWI^3oi\n+8O\u0003\u0002\u000e\u001d\u0005!!.\u00199j\u0015\ty\u0001#A\u0003fm\u0016tGOC\u0001\u0012\u0003\u0011\t7n[1\u0004\u0001U!A#I\u0016/'\r\u0001Qc\u0007\t\u0003-ei\u0011a\u0006\u0006\u00021\u0005)1oY1mC&\u0011!d\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u000bqirDK\u0017\u000e\u00031I!A\b\u0007\u0003\u0011\u00153XM\u001c;CkN\u0004\"\u0001I\u0011\r\u0001\u0011)!\u0005\u0001b\u0001G\t\tQ)\u0005\u0002%OA\u0011a#J\u0005\u0003M]\u0011qAT8uQ&tw\r\u0005\u0002\u0017Q%\u0011\u0011f\u0006\u0002\u0004\u0003:L\bC\u0001\u0011,\t\u0015a\u0003A1\u0001$\u0005\u0005\u0019\u0006C\u0001\u0011/\t\u0015y\u0003A1\u0001$\u0005\u0005\u0019\u0015A\u0002\u001fj]&$h\bF\u00013!\u0015a\u0002a\b\u0016.\u0003\r\u0011Wo]\u000b\u0002kI!a'F\u001d=\r\u001194\u0001A\u001b\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\t\t,8\u000f\t\t\u0003umj\u0011AD\u0005\u0003=9\u0001\"AO\u001f\n\u0005yr!\u0001G*vE\u000eD\u0017M\u001c8fY\u000ec\u0017m]:jM&\u001c\u0017\r^5p]\u0016!\u0001I\u000e\u0001 \u0005\u0015)e/\u001a8u\u000b\u0011\u0011e\u0007\u0001\u0016\u0003\u0015M+(m]2sS\n,'/\u0002\u0003Em\u0001i#AC\"mCN\u001c\u0018NZ5fe\u0006\t2/\u001e2dY\u0006\u001c8/\u001b4jG\u0006$\u0018n\u001c8\u0016\u0003\u001d\u00032\u0001S&.\u001b\u0005I%B\u0001&\u0011\u0003\u0011)H/\u001b7\n\u00051K%!E*vE\u000ed\u0017m]:jM&\u001c\u0017\r^5p]\u0006A1\r\\1tg&4\u0017\u0010\u0006\u0002.\u001f\")q\"\u0002a\u0001?\u00059\u0001/\u001e2mSNDGc\u0001*V-B\u0011acU\u0005\u0003)^\u0011A!\u00168ji\")qB\u0002a\u0001?!)qK\u0002a\u0001U\u0005Q1/\u001e2tGJL'-\u001a:\u0002\u0013M,(m]2sS\n,Gc\u0001.^=B\u0011acW\u0005\u00039^\u0011qAQ8pY\u0016\fg\u000eC\u0003X\u000f\u0001\u0007!\u0006C\u0003`\u000f\u0001\u0007Q&\u0001\u0002u_\u0006YQO\\:vEN\u001c'/\u001b2f)\rQ&m\u0019\u0005\u0006/\"\u0001\rA\u000b\u0005\u0006I\"\u0001\r!L\u0001\u0005MJ|W\u000e\u0006\u0002SM\")q+\u0003a\u0001UQ\u0011!\u000b\u001b\u0005\u0006\u001f)\u0001\ra\b")
public abstract class SubchannelEventBus<E, S, C>
implements EventBus<E, S, C> {
    private final akka.event.EventBus bus = new akka.event.EventBus(this){
        private SubclassifiedIndex<C, S> akka$event$SubchannelClassification$$subscriptions;
        private volatile Map<C, Set<S>> akka$event$SubchannelClassification$$cache;
        private volatile boolean bitmap$0;
        private final /* synthetic */ SubchannelEventBus $outer;

        public boolean subscribe(Object subscriber, Object to) {
            return SubchannelClassification.subscribe$(this, subscriber, to);
        }

        public boolean unsubscribe(Object subscriber, Object from) {
            return SubchannelClassification.unsubscribe$(this, subscriber, from);
        }

        public void unsubscribe(Object subscriber) {
            SubchannelClassification.unsubscribe$(this, subscriber);
        }

        public void publish(Object event) {
            SubchannelClassification.publish$(this, event);
        }

        public boolean hasSubscriptions(Object subscriber) {
            return SubchannelClassification.hasSubscriptions$(this, subscriber);
        }

        private SubclassifiedIndex<C, S> akka$event$SubchannelClassification$$subscriptions$lzycompute() {
            $anon$2 var1_1 = this;
            synchronized (var1_1) {
                if (!this.bitmap$0) {
                    this.akka$event$SubchannelClassification$$subscriptions = SubchannelClassification.akka$event$SubchannelClassification$$subscriptions$(this);
                    this.bitmap$0 = true;
                }
            }
            return this.akka$event$SubchannelClassification$$subscriptions;
        }

        public SubclassifiedIndex<C, S> akka$event$SubchannelClassification$$subscriptions() {
            if (!this.bitmap$0) {
                return this.akka$event$SubchannelClassification$$subscriptions$lzycompute();
            }
            return this.akka$event$SubchannelClassification$$subscriptions;
        }

        public Map<C, Set<S>> akka$event$SubchannelClassification$$cache() {
            return this.akka$event$SubchannelClassification$$cache;
        }

        public void akka$event$SubchannelClassification$$cache_$eq(Map<C, Set<S>> x$1) {
            this.akka$event$SubchannelClassification$$cache = x$1;
        }

        public Subclassification<C> subclassification() {
            return this.$outer.subclassification();
        }

        public C classify(E event) {
            return this.$outer.classify(event);
        }

        public void publish(E event, S subscriber) {
            this.$outer.publish(event, subscriber);
        }
        {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            SubchannelClassification.$init$(this);
        }
    };

    private akka.event.EventBus bus() {
        return this.bus;
    }

    public abstract Subclassification<C> subclassification();

    public abstract C classify(E var1);

    public abstract void publish(E var1, S var2);

    @Override
    public boolean subscribe(S subscriber, C to) {
        return ((SubchannelClassification)((Object)this.bus())).subscribe(subscriber, to);
    }

    @Override
    public boolean unsubscribe(S subscriber, C from) {
        return ((SubchannelClassification)((Object)this.bus())).unsubscribe(subscriber, from);
    }

    @Override
    public void unsubscribe(S subscriber) {
        ((SubchannelClassification)((Object)this.bus())).unsubscribe(subscriber);
    }

    @Override
    public void publish(E event) {
        ((SubchannelClassification)((Object)this.bus())).publish(event);
    }
}

