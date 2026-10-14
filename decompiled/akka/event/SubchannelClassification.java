/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.util.Subclassification;
import akka.util.SubclassifiedIndex;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.SetOps;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00055a!C\b\u0011!\u0003\r\t!FA\u0001\u0011\u0015a\u0002\u0001\"\u0001\u001e\u0011\u0015\t\u0003Ab\u0005#\u0011!y\u0003\u0001#b\u0001\n\u0013\u0001\u0004bB\u001c\u0001\u0001\u0004%I\u0001\u000f\u0005\b!\u0002\u0001\r\u0011\"\u0003R\u0011\u0015!\u0006A\"\u0005V\u0011\u0015Q\u0006A\"\u0005\\\u0011\u0015y\u0006\u0001\"\u0001a\u0011\u00159\u0007\u0001\"\u0001i\u0011\u00159\u0007\u0001\"\u0001m\u0011\u0015Q\u0006\u0001\"\u0001o\u0011\u0019\u0001\b\u0001\"\u0001\u0013c\")1\u000f\u0001C\u0005i\")Q\u0010\u0001C\u0005}\nA2+\u001e2dQ\u0006tg.\u001a7DY\u0006\u001c8/\u001b4jG\u0006$\u0018n\u001c8\u000b\u0005E\u0011\u0012!B3wK:$(\"A\n\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001a\u0003\u0005\u0002\u001855\t\u0001DC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0013\tY\u0002D\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003y\u0001\"aF\u0010\n\u0005\u0001B\"\u0001B+oSR\f\u0011c];cG2\f7o]5gS\u000e\fG/[8o+\u0005\u0019\u0003c\u0001\u0013(S5\tQE\u0003\u0002'%\u0005!Q\u000f^5m\u0013\tASEA\tTk\n\u001cG.Y:tS\u001aL7-\u0019;j_:\u0004\"AK\u0016\u000e\u0003\u0001I!\u0001L\u0017\u0003\u0015\rc\u0017m]:jM&,'/\u0003\u0002/!\tAQI^3oi\n+8/A\u0007tk\n\u001c8M]5qi&|gn]\u000b\u0002cA!AEM\u00155\u0013\t\u0019TE\u0001\nTk\n\u001cG.Y:tS\u001aLW\rZ%oI\u0016D\bC\u0001\u00166\u0013\t1TF\u0001\u0006Tk\n\u001c8M]5cKJ\fQaY1dQ\u0016,\u0012!\u000f\t\u0005u}J\u0013)D\u0001<\u0015\taT(A\u0005j[6,H/\u00192mK*\u0011a\bG\u0001\u000bG>dG.Z2uS>t\u0017B\u0001!<\u0005\ri\u0015\r\u001d\t\u0004\u0005&#dBA\"H!\t!\u0005$D\u0001F\u0015\t1E#\u0001\u0004=e>|GOP\u0005\u0003\u0011b\ta\u0001\u0015:fI\u00164\u0017B\u0001&L\u0005\r\u0019V\r\u001e\u0006\u0003\u0011bA#\u0001B'\u0011\u0005]q\u0015BA(\u0019\u0005!1x\u000e\\1uS2,\u0017!C2bG\",w\fJ3r)\tq\"\u000bC\u0004T\u000b\u0005\u0005\t\u0019A\u001d\u0002\u0007a$\u0013'\u0001\u0005dY\u0006\u001c8/\u001b4z)\tIc\u000bC\u0003\u0012\r\u0001\u0007q\u000b\u0005\u0002+1&\u0011\u0011,\f\u0002\u0006\u000bZ,g\u000e^\u0001\baV\u0014G.[:i)\rqB,\u0018\u0005\u0006#\u001d\u0001\ra\u0016\u0005\u0006=\u001e\u0001\r\u0001N\u0001\u000bgV\u00147o\u0019:jE\u0016\u0014\u0018!C:vEN\u001c'/\u001b2f)\r\tG-\u001a\t\u0003/\tL!a\u0019\r\u0003\u000f\t{w\u000e\\3b]\")a\f\u0003a\u0001i!)a\r\u0003a\u0001S\u0005\u0011Ao\\\u0001\fk:\u001cXOY:de&\u0014W\rF\u0002bS*DQAX\u0005A\u0002QBQa[\u0005A\u0002%\nAA\u001a:p[R\u0011a$\u001c\u0005\u0006=*\u0001\r\u0001\u000e\u000b\u0003==DQ!E\u0006A\u0002]\u000b\u0001\u0003[1t'V\u00147o\u0019:jaRLwN\\:\u0015\u0005\u0005\u0014\b\"\u00020\r\u0001\u0004!\u0014a\u0004:f[>4XM\u0012:p[\u000e\u000b7\r[3\u0015\u0005y)\b\"\u0002<\u000e\u0001\u00049\u0018aB2iC:<Wm\u001d\t\u0004uaT\u0018BA=<\u0005\r\u0019V-\u001d\t\u0005/mL\u0013)\u0003\u0002}1\t1A+\u001e9mKJ\n!\"\u00193e)>\u001c\u0015m\u00195f)\tqr\u0010C\u0003w\u001d\u0001\u0007qO\u0005\u0004\u0002\u0004\u0005\u001d\u00111\u0002\u0004\u0007\u0003\u000b\u0001\u0001!!\u0001\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0007\u0005%\u0001!D\u0001\u0011!\r\tI!\f")
public interface SubchannelClassification {
    public Subclassification<Object> subclassification();

    public static /* synthetic */ SubclassifiedIndex akka$event$SubchannelClassification$$subscriptions$(SubchannelClassification $this) {
        return $this.akka$event$SubchannelClassification$$subscriptions();
    }

    default public SubclassifiedIndex<Object, Object> akka$event$SubchannelClassification$$subscriptions() {
        return new SubclassifiedIndex<Object, Object>(this.subclassification());
    }

    public Map<Object, Set<Object>> akka$event$SubchannelClassification$$cache();

    public void akka$event$SubchannelClassification$$cache_$eq(Map<Object, Set<Object>> var1);

    public Object classify(Object var1);

    public void publish(Object var1, Object var2);

    public static /* synthetic */ boolean subscribe$(SubchannelClassification $this, Object subscriber, Object to) {
        return $this.subscribe(subscriber, to);
    }

    default public boolean subscribe(Object subscriber, Object to) {
        boolean bl;
        SubclassifiedIndex<Object, Object> subclassifiedIndex = this.akka$event$SubchannelClassification$$subscriptions();
        synchronized (subclassifiedIndex) {
            Seq<Tuple2<Object, Set<Object>>> diff = this.akka$event$SubchannelClassification$$subscriptions().addValue(to, subscriber);
            this.addToCache(diff);
            bl = diff.nonEmpty();
        }
        return bl;
    }

    public static /* synthetic */ boolean unsubscribe$(SubchannelClassification $this, Object subscriber, Object from) {
        return $this.unsubscribe(subscriber, from);
    }

    default public boolean unsubscribe(Object subscriber, Object from) {
        boolean bl;
        SubclassifiedIndex<Object, Object> subclassifiedIndex = this.akka$event$SubchannelClassification$$subscriptions();
        synchronized (subclassifiedIndex) {
            Seq<Tuple2<Object, Set<Object>>> diff = this.akka$event$SubchannelClassification$$subscriptions().removeValue(from, subscriber);
            this.akka$event$SubchannelClassification$$cache_$eq((Map)this.akka$event$SubchannelClassification$$cache().$plus$plus(diff));
            bl = diff.nonEmpty();
        }
        return bl;
    }

    public static /* synthetic */ void unsubscribe$(SubchannelClassification $this, Object subscriber) {
        $this.unsubscribe(subscriber);
    }

    default public void unsubscribe(Object subscriber) {
        SubclassifiedIndex<Object, Object> subclassifiedIndex = this.akka$event$SubchannelClassification$$subscriptions();
        synchronized (subclassifiedIndex) {
            this.removeFromCache(this.akka$event$SubchannelClassification$$subscriptions().removeValue(subscriber));
        }
    }

    public static /* synthetic */ void publish$(SubchannelClassification $this, Object event) {
        $this.publish(event);
    }

    default public void publish(Object event) {
        Set set;
        Object c = this.classify(event);
        if (this.akka$event$SubchannelClassification$$cache().contains(c)) {
            set = (Set)this.akka$event$SubchannelClassification$$cache().apply(c);
        } else {
            SubclassifiedIndex<Object, Object> subclassifiedIndex = this.akka$event$SubchannelClassification$$subscriptions();
            synchronized (subclassifiedIndex) {
                Set set2;
                if (this.akka$event$SubchannelClassification$$cache().contains(c)) {
                    set2 = (Set)this.akka$event$SubchannelClassification$$cache().apply(c);
                } else {
                    this.addToCache(this.akka$event$SubchannelClassification$$subscriptions().addKey(c));
                    set2 = (Set)this.akka$event$SubchannelClassification$$cache().apply(c);
                }
                Set set3 = set2;
                // MONITOREXIT @DISABLED, blocks:[0, 1, 5] lbl14 : MonitorExitStatement: MONITOREXIT : var4_3
                set = set3;
            }
        }
        Set recv = set;
        recv.foreach((Function1<Object, Object> & Serializable)x$1 -> {
            this.publish(event, x$1);
            return BoxedUnit.UNIT;
        });
    }

    public static /* synthetic */ boolean hasSubscriptions$(SubchannelClassification $this, Object subscriber) {
        return $this.hasSubscriptions(subscriber);
    }

    default public boolean hasSubscriptions(Object subscriber) {
        return this.akka$event$SubchannelClassification$$cache().values().exists((Function1<Set, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(x$2.contains(subscriber)));
    }

    private void removeFromCache(Seq<Tuple2<Object, Set<Object>>> changes) {
        this.akka$event$SubchannelClassification$$cache_$eq(changes.foldLeft(this.akka$event$SubchannelClassification$$cache(), (Function2<Map, Tuple2, Map> & Serializable)(x0$1, x1$1) -> {
            Tuple2<Map, Tuple2> tuple2 = new Tuple2<Map, Tuple2>((Map)x0$1, (Tuple2)x1$1);
            if (tuple2 != null) {
                Map m4 = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    Object c = tuple22._1();
                    Set cs = (Set)tuple22._2();
                    return (Map)m4.updated(c, ((SetOps)m4.getOrElse(c, (Function0<Set> & Serializable)() -> Predef$.MODULE$.Set().empty())).diff(cs));
                }
            }
            throw new MatchError(tuple2);
        }));
    }

    private void addToCache(Seq<Tuple2<Object, Set<Object>>> changes) {
        this.akka$event$SubchannelClassification$$cache_$eq(changes.foldLeft(this.akka$event$SubchannelClassification$$cache(), (Function2<Map, Tuple2, Map> & Serializable)(x0$1, x1$1) -> {
            Tuple2<Map, Tuple2> tuple2 = new Tuple2<Map, Tuple2>((Map)x0$1, (Tuple2)x1$1);
            if (tuple2 != null) {
                Map m4 = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    Object c = tuple22._1();
                    Set cs = (Set)tuple22._2();
                    return (Map)m4.updated(c, ((scala.collection.SetOps)m4.getOrElse(c, (Function0<Set> & Serializable)() -> Predef$.MODULE$.Set().empty())).union(cs));
                }
            }
            throw new MatchError(tuple2);
        }));
    }

    public static void $init$(SubchannelClassification $this) {
        $this.akka$event$SubchannelClassification$$cache_$eq((Map<Object, Set<Object>>)Predef$.MODULE$.Map().empty());
    }
}

