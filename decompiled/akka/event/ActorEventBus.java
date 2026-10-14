/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.event.EventBus;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u0016\u0001\u0011\u0005a#\u0002\u0003\u001b\u0001\u0001Y\u0002\"B\u0011\u0001\t#\u0011#!D!di>\u0014XI^3oi\n+8O\u0003\u0002\u0007\u000f\u0005)QM^3oi*\t\u0001\"\u0001\u0003bW.\f7\u0001A\n\u0004\u0001-\t\u0002C\u0001\u0007\u0010\u001b\u0005i!\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Ai!AB!osJ+g\r\u0005\u0002\u0013'5\tQ!\u0003\u0002\u0015\u000b\tAQI^3oi\n+8/\u0001\u0004%S:LG\u000f\n\u000b\u0002/A\u0011A\u0002G\u0005\u000335\u0011A!\u00168ji\nQ1+\u001e2tGJL'-\u001a:\u0011\u0005qyR\"A\u000f\u000b\u0005y9\u0011!B1di>\u0014\u0018B\u0001\u0011\u001e\u0005!\t5\r^8s%\u00164\u0017AE2p[B\f'/Z*vEN\u001c'/\u001b2feN$2a\t\u0014)!\taA%\u0003\u0002&\u001b\t\u0019\u0011J\u001c;\t\u000b\u001d\u001a\u0001\u0019A\u000e\u0002\u0003\u0005DQ!K\u0002A\u0002m\t\u0011A\u0019")
public interface ActorEventBus
extends EventBus {
    public static /* synthetic */ int compareSubscribers$(ActorEventBus $this, ActorRef a, ActorRef b) {
        return $this.compareSubscribers(a, b);
    }

    default public int compareSubscribers(ActorRef a, ActorRef b) {
        return a.compareTo(b);
    }

    public static void $init$(ActorEventBus $this) {
    }
}

