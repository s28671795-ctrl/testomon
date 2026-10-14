/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.util.Index;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3\u0001b\u0003\u0007\u0011\u0002\u0007\u0005\u0011#\u0016\u0005\u00061\u0001!\t!\u0007\u0005\b;\u0001\u0011\r\u0011\"\u0006\u001f\u0011\u0015q\u0003A\"\u00050\u0011\u0015\u0019\u0004A\"\u00055\u0011\u0015I\u0004A\"\u0005;\u0011\u0015y\u0004A\"\u0005A\u0011\u0015!\u0005\u0001\"\u0001F\u0011\u0015a\u0005\u0001\"\u0001N\u0011\u0015a\u0005\u0001\"\u0001R\u0011\u0015y\u0004\u0001\"\u0001T\u0005Qaun\\6va\u000ec\u0017m]:jM&\u001c\u0017\r^5p]*\u0011QBD\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u001f\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t!\u0004\u0005\u0002\u00147%\u0011A\u0004\u0006\u0002\u0005+:LG/A\u0006tk\n\u001c8M]5cKJ\u001cX#A\u0010\u0011\t\u0001\u001aSeK\u0007\u0002C)\u0011!ED\u0001\u0005kRLG.\u0003\u0002%C\t)\u0011J\u001c3fqB\u0011aeJ\u0007\u0002\u0001%\u0011\u0001&\u000b\u0002\u000b\u00072\f7o]5gS\u0016\u0014\u0018B\u0001\u0016\r\u0005!)e/\u001a8u\u0005V\u001c\bC\u0001\u0014-\u0013\ti\u0013F\u0001\u0006Tk\n\u001c8M]5cKJ\fq!\\1q'&TX\rF\u00011!\t\u0019\u0012'\u0003\u00023)\t\u0019\u0011J\u001c;\u0002%\r|W\u000e]1sKN+(m]2sS\n,'o\u001d\u000b\u0004aU:\u0004\"\u0002\u001c\u0005\u0001\u0004Y\u0013!A1\t\u000ba\"\u0001\u0019A\u0016\u0002\u0003\t\f\u0001b\u00197bgNLg-\u001f\u000b\u0003KmBQ!D\u0003A\u0002q\u0002\"AJ\u001f\n\u0005yJ#!B#wK:$\u0018a\u00029vE2L7\u000f\u001b\u000b\u00045\u0005\u0013\u0005\"B\u0007\u0007\u0001\u0004a\u0004\"B\"\u0007\u0001\u0004Y\u0013AC:vEN\u001c'/\u001b2fe\u0006I1/\u001e2tGJL'-\u001a\u000b\u0004\r&S\u0005CA\nH\u0013\tAECA\u0004C_>dW-\u00198\t\u000b\r;\u0001\u0019A\u0016\t\u000b-;\u0001\u0019A\u0013\u0002\u0005Q|\u0017aC;ogV\u00147o\u0019:jE\u0016$2A\u0012(P\u0011\u0015\u0019\u0005\u00021\u0001,\u0011\u0015\u0001\u0006\u00021\u0001&\u0003\u00111'o\\7\u0015\u0005i\u0011\u0006\"B\"\n\u0001\u0004YCC\u0001\u000eU\u0011\u0015i!\u00021\u0001=%\r1\u0006L\u0017\u0004\u0005/\u0002\u0001QK\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0002Z\u00015\tA\u0002\u0005\u0002ZS\u0001")
public interface LookupClassification {
    public void akka$event$LookupClassification$_setter_$subscribers_$eq(Index<Object, Object> var1);

    public Index<Object, Object> subscribers();

    public int mapSize();

    public int compareSubscribers(Object var1, Object var2);

    public Object classify(Object var1);

    public void publish(Object var1, Object var2);

    public static /* synthetic */ boolean subscribe$(LookupClassification $this, Object subscriber, Object to) {
        return $this.subscribe(subscriber, to);
    }

    default public boolean subscribe(Object subscriber, Object to) {
        return this.subscribers().put(to, subscriber);
    }

    public static /* synthetic */ boolean unsubscribe$(LookupClassification $this, Object subscriber, Object from) {
        return $this.unsubscribe(subscriber, from);
    }

    default public boolean unsubscribe(Object subscriber, Object from) {
        return this.subscribers().remove(from, subscriber);
    }

    public static /* synthetic */ void unsubscribe$(LookupClassification $this, Object subscriber) {
        $this.unsubscribe(subscriber);
    }

    default public void unsubscribe(Object subscriber) {
        this.subscribers().removeValue(subscriber);
    }

    public static /* synthetic */ void publish$(LookupClassification $this, Object event) {
        $this.publish(event);
    }

    default public void publish(Object event) {
        Iterator<Object> i = this.subscribers().valueIterator(this.classify(event));
        while (i.hasNext()) {
            this.publish(event, i.next());
        }
    }

    public static void $init$(LookupClassification $this) {
        $this.akka$event$LookupClassification$_setter_$subscribers_$eq(new Index<Object, Object>($this.mapSize(), new Comparator<Object>($this){
            private final /* synthetic */ LookupClassification $outer;

            public Comparator<Object> reversed() {
                return Comparator.super.reversed();
            }

            public Comparator<Object> thenComparing(Comparator<? super Object> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public <U> Comparator<Object> thenComparing(Function<? super Object, ? extends U> x$1, Comparator<? super U> x$2) {
                return Comparator.super.thenComparing(x$1, x$2);
            }

            public <U extends Comparable<? super U>> Comparator<Object> thenComparing(Function<? super Object, ? extends U> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public Comparator<Object> thenComparingInt(ToIntFunction<? super Object> x$1) {
                return Comparator.super.thenComparingInt(x$1);
            }

            public Comparator<Object> thenComparingLong(ToLongFunction<? super Object> x$1) {
                return Comparator.super.thenComparingLong(x$1);
            }

            public Comparator<Object> thenComparingDouble(ToDoubleFunction<? super Object> x$1) {
                return Comparator.super.thenComparingDouble(x$1);
            }

            public int compare(Object a, Object b) {
                return this.$outer.compareSubscribers(a, b);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }));
    }
}

