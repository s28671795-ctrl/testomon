/*
 * Decompiled with CFR 0.152.
 */
package akka.event.japi;

import akka.event.LookupClassification;
import akka.event.japi.EventBus;
import akka.util.Index;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u000554Q\u0001D\u0007\u0002\u0002QAQ!\r\u0001\u0005\u0002IBq\u0001\u000e\u0001C\u0002\u0013%Q\u0007\u0003\u0004:\u0001\u0001\u0006IA\u000e\u0005\u0006\r\u00021\tb\u0012\u0005\u0006\u0017\u00021\t\u0002\u0014\u0005\u0006#\u00021\tB\u0015\u0005\u0006)\u00021\t\"\u0016\u0005\u00069\u0002!\t%\u0018\u0005\u0006I\u0002!\t%\u001a\u0005\u0006I\u0002!\t%\u001b\u0005\u0006)\u0002!\te\u001b\u0002\u000f\u0019>|7.\u001e9Fm\u0016tGOQ;t\u0015\tqq\"\u0001\u0003kCBL'B\u0001\t\u0012\u0003\u0015)g/\u001a8u\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0003\u0016E1z3c\u0001\u0001\u00179A\u0011qCG\u0007\u00021)\t\u0011$A\u0003tG\u0006d\u0017-\u0003\u0002\u001c1\t1\u0011I\\=SK\u001a\u0004R!\b\u0010!W9j\u0011!D\u0005\u0003?5\u0011\u0001\"\u0012<f]R\u0014Uo\u001d\t\u0003C\tb\u0001\u0001B\u0003$\u0001\t\u0007AEA\u0001F#\t)\u0003\u0006\u0005\u0002\u0018M%\u0011q\u0005\u0007\u0002\b\u001d>$\b.\u001b8h!\t9\u0012&\u0003\u0002+1\t\u0019\u0011I\\=\u0011\u0005\u0005bC!B\u0017\u0001\u0005\u0004!#!A*\u0011\u0005\u0005zC!\u0002\u0019\u0001\u0005\u0004!#!A\"\u0002\rqJg.\u001b;?)\u0005\u0019\u0004#B\u000f\u0001A-r\u0013a\u00012vgV\taG\u0005\u00038-ijd\u0001\u0002\u001d\u0004\u0001Y\u0012A\u0002\u0010:fM&tW-\\3oiz\nAAY;tAA\u00111\bP\u0007\u0002\u001f%\u0011qd\u0004\t\u0003wyJ!aP\b\u0003)1{wn[;q\u00072\f7o]5gS\u000e\fG/[8o\u000b\u0011\tu\u0007\u0001\u0011\u0003\u000b\u00153XM\u001c;\u0006\t\r;\u0004a\u000b\u0002\u000b'V\u00147o\u0019:jE\u0016\u0014X\u0001B#8\u00019\u0012!b\u00117bgNLg-[3s\u0003\u001di\u0017\r]*ju\u0016$\u0012\u0001\u0013\t\u0003/%K!A\u0013\r\u0003\u0007%sG/\u0001\nd_6\u0004\u0018M]3Tk\n\u001c8M]5cKJ\u001cHc\u0001%N\u001f\")a*\u0002a\u0001W\u0005\t\u0011\rC\u0003Q\u000b\u0001\u00071&A\u0001c\u0003!\u0019G.Y:tS\u001aLHC\u0001\u0018T\u0011\u0015\u0001b\u00011\u0001!\u0003\u001d\u0001XO\u00197jg\"$2AV-[!\t9r+\u0003\u0002Y1\t!QK\\5u\u0011\u0015\u0001r\u00011\u0001!\u0011\u0015Yv\u00011\u0001,\u0003)\u0019XOY:de&\u0014WM]\u0001\ngV\u00147o\u0019:jE\u0016$2AX1c!\t9r,\u0003\u0002a1\t9!i\\8mK\u0006t\u0007\"B.\t\u0001\u0004Y\u0003\"B2\t\u0001\u0004q\u0013A\u0001;p\u0003-)hn];cg\u000e\u0014\u0018NY3\u0015\u0007y3w\rC\u0003\\\u0013\u0001\u00071\u0006C\u0003i\u0013\u0001\u0007a&\u0001\u0003ge>lGC\u0001,k\u0011\u0015Y&\u00021\u0001,)\t1F\u000eC\u0003\u0011\u0017\u0001\u0007\u0001\u0005")
public abstract class LookupEventBus<E, S, C>
implements EventBus<E, S, C> {
    private final akka.event.EventBus bus = new akka.event.EventBus(this){
        private Index<C, S> subscribers;
        private final /* synthetic */ LookupEventBus $outer;

        public boolean subscribe(Object subscriber, Object to) {
            return LookupClassification.subscribe$(this, subscriber, to);
        }

        public boolean unsubscribe(Object subscriber, Object from) {
            return LookupClassification.unsubscribe$(this, subscriber, from);
        }

        public void unsubscribe(Object subscriber) {
            LookupClassification.unsubscribe$(this, subscriber);
        }

        public void publish(Object event) {
            LookupClassification.publish$(this, event);
        }

        public final Index<C, S> subscribers() {
            return this.subscribers;
        }

        public final void akka$event$LookupClassification$_setter_$subscribers_$eq(Index<C, S> x$1) {
            this.subscribers = x$1;
        }

        public int mapSize() {
            return this.$outer.mapSize();
        }

        public int compareSubscribers(S a, S b) {
            return this.$outer.compareSubscribers(a, b);
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
            LookupClassification.$init$(this);
            Statics.releaseFence();
        }
    };

    private akka.event.EventBus bus() {
        return this.bus;
    }

    public abstract int mapSize();

    public abstract int compareSubscribers(S var1, S var2);

    public abstract C classify(E var1);

    public abstract void publish(E var1, S var2);

    @Override
    public boolean subscribe(S subscriber, C to) {
        return ((LookupClassification)((Object)this.bus())).subscribe(subscriber, to);
    }

    @Override
    public boolean unsubscribe(S subscriber, C from) {
        return ((LookupClassification)((Object)this.bus())).unsubscribe(subscriber, from);
    }

    @Override
    public void unsubscribe(S subscriber) {
        ((LookupClassification)((Object)this.bus())).unsubscribe(subscriber);
    }

    @Override
    public void publish(E event) {
        ((LookupClassification)((Object)this.bus())).publish(event);
    }
}

