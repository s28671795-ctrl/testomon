/*
 * Decompiled with CFR 0.152.
 */
package akka.event.japi;

import akka.event.ScanningClassification;
import akka.event.japi.EventBus;
import java.util.concurrent.ConcurrentSkipListSet;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005E4Q\u0001D\u0007\u0002\u0002QAQ!\r\u0001\u0005\u0002IBq\u0001\u000e\u0001C\u0002\u0013%Q\u0007\u0003\u0004:\u0001\u0001\u0006IA\u000e\u0005\u0006\r\u00021\tb\u0012\u0005\u0006\u001f\u00021\t\u0002\u0015\u0005\u0006'\u00021\t\u0002\u0016\u0005\u00067\u00021\t\u0002\u0018\u0005\u0006G\u0002!\t\u0005\u001a\u0005\u0006Q\u0002!\t%\u001b\u0005\u0006Q\u0002!\t%\u001c\u0005\u00067\u0002!\te\u001c\u0002\u0011'\u000e\fgN\\5oO\u00163XM\u001c;CkNT!AD\b\u0002\t)\f\u0007/\u001b\u0006\u0003!E\tQ!\u001a<f]RT\u0011AE\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\tU\u0011CfL\n\u0004\u0001Ya\u0002CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g\rE\u0003\u001e=\u0001Zc&D\u0001\u000e\u0013\tyRB\u0001\u0005Fm\u0016tGOQ;t!\t\t#\u0005\u0004\u0001\u0005\u000b\r\u0002!\u0019\u0001\u0013\u0003\u0003\u0015\u000b\"!\n\u0015\u0011\u0005]1\u0013BA\u0014\u0019\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aF\u0015\n\u0005)B\"aA!osB\u0011\u0011\u0005\f\u0003\u0006[\u0001\u0011\r\u0001\n\u0002\u0002'B\u0011\u0011e\f\u0003\u0006a\u0001\u0011\r\u0001\n\u0002\u0002\u0007\u00061A(\u001b8jiz\"\u0012a\r\t\u0006;\u0001\u00013FL\u0001\u0004EV\u001cX#\u0001\u001c\u0013\t]2\"(\u0010\u0004\u0005q\r\u0001aG\u0001\u0007=e\u00164\u0017N\\3nK:$h(\u0001\u0003ckN\u0004\u0003CA\u001e=\u001b\u0005y\u0011BA\u0010\u0010!\tYd(\u0003\u0002@\u001f\t12kY1o]&twm\u00117bgNLg-[2bi&|g.\u0002\u0003Bo\u0001\u0001#!B#wK:$X\u0001B\"8\u0001-\u0012!bU;cg\u000e\u0014\u0018NY3s\u000b\u0011)u\u0007\u0001\u0018\u0003\u0015\rc\u0017m]:jM&,'/\u0001\nd_6\u0004\u0018M]3DY\u0006\u001c8/\u001b4jKJ\u001cHc\u0001%L\u001bB\u0011q#S\u0005\u0003\u0015b\u00111!\u00138u\u0011\u0015aE\u00011\u0001/\u0003\u0005\t\u0007\"\u0002(\u0005\u0001\u0004q\u0013!\u00012\u0002%\r|W\u000e]1sKN+(m]2sS\n,'o\u001d\u000b\u0004\u0011F\u0013\u0006\"\u0002'\u0006\u0001\u0004Y\u0003\"\u0002(\u0006\u0001\u0004Y\u0013aB7bi\u000eDWm\u001d\u000b\u0004+bS\u0006CA\fW\u0013\t9\u0006DA\u0004C_>dW-\u00198\t\u000be3\u0001\u0019\u0001\u0018\u0002\u0015\rd\u0017m]:jM&,'\u000fC\u0003\u0011\r\u0001\u0007\u0001%A\u0004qk\nd\u0017n\u001d5\u0015\u0007u\u0003\u0017\r\u0005\u0002\u0018=&\u0011q\f\u0007\u0002\u0005+:LG\u000fC\u0003\u0011\u000f\u0001\u0007\u0001\u0005C\u0003c\u000f\u0001\u00071&\u0001\u0006tk\n\u001c8M]5cKJ\f\u0011b];cg\u000e\u0014\u0018NY3\u0015\u0007U+g\rC\u0003c\u0011\u0001\u00071\u0006C\u0003h\u0011\u0001\u0007a&\u0001\u0002u_\u0006YQO\\:vEN\u001c'/\u001b2f)\r)&n\u001b\u0005\u0006E&\u0001\ra\u000b\u0005\u0006Y&\u0001\rAL\u0001\u0005MJ|W\u000e\u0006\u0002^]\")!M\u0003a\u0001WQ\u0011Q\f\u001d\u0005\u0006!-\u0001\r\u0001\t")
public abstract class ScanningEventBus<E, S, C>
implements EventBus<E, S, C> {
    private final akka.event.EventBus bus = new akka.event.EventBus(this){
        private ConcurrentSkipListSet<Tuple2<C, S>> subscribers;
        private final /* synthetic */ ScanningEventBus $outer;

        public boolean subscribe(Object subscriber, Object to) {
            return ScanningClassification.subscribe$(this, subscriber, to);
        }

        public boolean unsubscribe(Object subscriber, Object from) {
            return ScanningClassification.unsubscribe$(this, subscriber, from);
        }

        public void unsubscribe(Object subscriber) {
            ScanningClassification.unsubscribe$(this, subscriber);
        }

        public void publish(Object event) {
            ScanningClassification.publish$(this, event);
        }

        public final ConcurrentSkipListSet<Tuple2<C, S>> subscribers() {
            return this.subscribers;
        }

        public final void akka$event$ScanningClassification$_setter_$subscribers_$eq(ConcurrentSkipListSet<Tuple2<C, S>> x$1) {
            this.subscribers = x$1;
        }

        public int compareClassifiers(C a, C b) {
            return this.$outer.compareClassifiers(a, b);
        }

        public int compareSubscribers(S a, S b) {
            return this.$outer.compareSubscribers(a, b);
        }

        public boolean matches(C classifier, E event) {
            return this.$outer.matches(classifier, event);
        }

        public void publish(E event, S subscriber) {
            this.$outer.publish(event, subscriber);
        }
        {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            ScanningClassification.$init$(this);
            Statics.releaseFence();
        }
    };

    private akka.event.EventBus bus() {
        return this.bus;
    }

    public abstract int compareClassifiers(C var1, C var2);

    public abstract int compareSubscribers(S var1, S var2);

    public abstract boolean matches(C var1, E var2);

    public abstract void publish(E var1, S var2);

    @Override
    public boolean subscribe(S subscriber, C to) {
        return ((ScanningClassification)((Object)this.bus())).subscribe(subscriber, to);
    }

    @Override
    public boolean unsubscribe(S subscriber, C from) {
        return ((ScanningClassification)((Object)this.bus())).unsubscribe(subscriber, from);
    }

    @Override
    public void unsubscribe(S subscriber) {
        ((ScanningClassification)((Object)this.bus())).unsubscribe(subscriber);
    }

    @Override
    public void publish(E event) {
        ((ScanningClassification)((Object)this.bus())).publish(event);
    }
}

