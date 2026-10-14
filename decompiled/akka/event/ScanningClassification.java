/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.MatchError;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194\u0001b\u0003\u0007\u0011\u0002\u0007\u0005\u0011\u0003\u0019\u0005\u00061\u0001!\t!\u0007\u0005\b;\u0001\u0011\r\u0011\"\u0006\u001f\u0011\u0015)\u0004A\"\u00057\u0011\u0015q\u0004A\"\u0005@\u0011\u0015\u0011\u0005A\"\u0005D\u0011\u0015i\u0005A\"\u0005O\u0011\u0015\u0011\u0006\u0001\"\u0001T\u0011\u00159\u0006\u0001\"\u0001Y\u0011\u00159\u0006\u0001\"\u0001]\u0011\u0015i\u0005\u0001\"\u0001_\u0005Y\u00196-\u00198oS:<7\t\\1tg&4\u0017nY1uS>t'BA\u0007\u000f\u0003\u0015)g/\u001a8u\u0015\u0005y\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001%A\u00111CF\u0007\u0002))\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u0018)\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001\u000e\u0011\u0005MY\u0012B\u0001\u000f\u0015\u0005\u0011)f.\u001b;\u0002\u0017M,(m]2sS\n,'o]\u000b\u0002?A\u0019\u0001eJ\u0015\u000e\u0003\u0005R!AI\u0012\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002%K\u0005!Q\u000f^5m\u0015\u00051\u0013\u0001\u00026bm\u0006L!\u0001K\u0011\u0003+\r{gnY;se\u0016tGoU6ja2K7\u000f^*fiB!1C\u000b\u00173\u0013\tYCC\u0001\u0004UkBdWM\r\t\u0003[9j\u0011\u0001A\u0005\u0003_A\u0012!b\u00117bgNLg-[3s\u0013\t\tDB\u0001\u0005Fm\u0016tGOQ;t!\ti3'\u0003\u00025a\tQ1+\u001e2tGJL'-\u001a:\u0002%\r|W\u000e]1sK\u000ec\u0017m]:jM&,'o\u001d\u000b\u0004oib\u0004CA\n9\u0013\tIDCA\u0002J]RDQaO\u0002A\u00021\n\u0011!\u0019\u0005\u0006{\r\u0001\r\u0001L\u0001\u0002E\u0006\u00112m\\7qCJ,7+\u001e2tGJL'-\u001a:t)\r9\u0004)\u0011\u0005\u0006w\u0011\u0001\rA\r\u0005\u0006{\u0011\u0001\rAM\u0001\b[\u0006$8\r[3t)\r!u)\u0013\t\u0003'\u0015K!A\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\")\u0001*\u0002a\u0001Y\u0005Q1\r\\1tg&4\u0017.\u001a:\t\u000b5)\u0001\u0019\u0001&\u0011\u00055Z\u0015B\u0001'1\u0005\u0015)e/\u001a8u\u0003\u001d\u0001XO\u00197jg\"$2AG(Q\u0011\u0015ia\u00011\u0001K\u0011\u0015\tf\u00011\u00013\u0003)\u0019XOY:de&\u0014WM]\u0001\ngV\u00147o\u0019:jE\u0016$2\u0001\u0012+V\u0011\u0015\tv\u00011\u00013\u0011\u00151v\u00011\u0001-\u0003\t!x.A\u0006v]N,(m]2sS\n,Gc\u0001#Z5\")\u0011\u000b\u0003a\u0001e!)1\f\u0003a\u0001Y\u0005!aM]8n)\tQR\fC\u0003R\u0013\u0001\u0007!\u0007\u0006\u0002\u001b?\")QB\u0003a\u0001\u0015J\u0019\u0011mY3\u0007\t\t\u0004\u0001\u0001\u0019\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0003I\u0002i\u0011\u0001\u0004\t\u0003IB\u0002")
public interface ScanningClassification {
    public void akka$event$ScanningClassification$_setter_$subscribers_$eq(ConcurrentSkipListSet<Tuple2<Object, Object>> var1);

    public ConcurrentSkipListSet<Tuple2<Object, Object>> subscribers();

    public int compareClassifiers(Object var1, Object var2);

    public int compareSubscribers(Object var1, Object var2);

    public boolean matches(Object var1, Object var2);

    public void publish(Object var1, Object var2);

    public static /* synthetic */ boolean subscribe$(ScanningClassification $this, Object subscriber, Object to) {
        return $this.subscribe(subscriber, to);
    }

    default public boolean subscribe(Object subscriber, Object to) {
        return this.subscribers().add(new Tuple2<Object, Object>(to, subscriber));
    }

    public static /* synthetic */ boolean unsubscribe$(ScanningClassification $this, Object subscriber, Object from) {
        return $this.unsubscribe(subscriber, from);
    }

    default public boolean unsubscribe(Object subscriber, Object from) {
        return this.subscribers().remove(new Tuple2<Object, Object>(from, subscriber));
    }

    public static /* synthetic */ void unsubscribe$(ScanningClassification $this, Object subscriber) {
        $this.unsubscribe(subscriber);
    }

    default public void unsubscribe(Object subscriber) {
        Iterator<Tuple2<Object, Object>> i = this.subscribers().iterator();
        while (i.hasNext()) {
            Tuple2<Object, Object> e = i.next();
            if (this.compareSubscribers(subscriber, e._2()) != 0) continue;
            i.remove();
        }
    }

    public static /* synthetic */ void publish$(ScanningClassification $this, Object event) {
        $this.publish(event);
    }

    default public void publish(Object event) {
        for (Tuple2<Object, Object> tuple2 : this.subscribers()) {
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            Object classifier = tuple2._1();
            Object subscriber = tuple2._2();
            Tuple2<Object, Object> tuple22 = new Tuple2<Object, Object>(classifier, subscriber);
            Object classifier2 = tuple22._1();
            Object subscriber2 = tuple22._2();
            if (!this.matches(classifier2, event)) continue;
            this.publish(event, subscriber2);
        }
    }

    public static void $init$(ScanningClassification $this) {
        $this.akka$event$ScanningClassification$_setter_$subscribers_$eq(new ConcurrentSkipListSet<Tuple2<Object, Object>>(new Comparator<Tuple2<Object, Object>>($this){
            private final /* synthetic */ ScanningClassification $outer;

            public Comparator<Tuple2<Object, Object>> reversed() {
                return Comparator.super.reversed();
            }

            public Comparator<Tuple2<Object, Object>> thenComparing(Comparator<? super Tuple2<Object, Object>> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public <U> Comparator<Tuple2<Object, Object>> thenComparing(Function<? super Tuple2<Object, Object>, ? extends U> x$1, Comparator<? super U> x$2) {
                return Comparator.super.thenComparing(x$1, x$2);
            }

            public <U extends Comparable<? super U>> Comparator<Tuple2<Object, Object>> thenComparing(Function<? super Tuple2<Object, Object>, ? extends U> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public Comparator<Tuple2<Object, Object>> thenComparingInt(ToIntFunction<? super Tuple2<Object, Object>> x$1) {
                return Comparator.super.thenComparingInt(x$1);
            }

            public Comparator<Tuple2<Object, Object>> thenComparingLong(ToLongFunction<? super Tuple2<Object, Object>> x$1) {
                return Comparator.super.thenComparingLong(x$1);
            }

            public Comparator<Tuple2<Object, Object>> thenComparingDouble(ToDoubleFunction<? super Tuple2<Object, Object>> x$1) {
                return Comparator.super.thenComparingDouble(x$1);
            }

            public int compare(Tuple2<Object, Object> a, Tuple2<Object, Object> b) {
                int n = this.$outer.compareClassifiers(a._1(), b._1());
                switch (n) {
                    case 0: {
                        return this.$outer.compareSubscribers(a._2(), b._2());
                    }
                }
                return n;
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

