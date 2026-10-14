/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorSubscription;
import akka.stream.impl.SubscriptionWithCursor;
import org.reactivestreams.Subscriber;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-3Q\u0001B\u0003\u0001\u0013-A\u0011b\t\u0001\u0003\u0002\u0003\u0006I\u0001\n\u0016\t\u0013-\u0002!\u0011!Q\u0001\n1J\u0004\"B\u001e\u0001\t\u0003a$aG!di>\u00148+\u001e2tGJL\u0007\u000f^5p]^KG\u000f[\"veN|'O\u0003\u0002\u0007\u000f\u0005!\u0011.\u001c9m\u0015\tA\u0011\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0015\u0005!\u0011m[6b+\ta1cE\u0002\u0001\u001b\u0001\u00022AD\b\u0012\u001b\u0005)\u0011B\u0001\t\u0006\u0005E\t5\r^8s'V\u00147o\u0019:jaRLwN\u001c\t\u0003%Ma\u0001\u0001B\u0003\u0015\u0001\t\u0007aCA\u0001U\u0007\u0001\t\"aF\u000f\u0011\u0005aYR\"A\r\u000b\u0003i\tQa]2bY\u0006L!\u0001H\r\u0003\u000f9{G\u000f[5oOB\u0011\u0001DH\u0005\u0003?e\u00111!\u00118z!\rq\u0011%E\u0005\u0003E\u0015\u0011acU;cg\u000e\u0014\u0018\u000e\u001d;j_:<\u0016\u000e\u001e5DkJ\u001cxN]\u0001\u0006?&l\u0007\u000f\u001c\t\u0003K!j\u0011A\n\u0006\u0003O%\tQ!Y2u_JL!!\u000b\u0014\u0003\u0011\u0005\u001bGo\u001c:SK\u001aL!AB\b\u0002\u0017}\u001bXOY:de&\u0014WM\u001d\u0019\u0003[Y\u00022AL\u001a6\u001b\u0005y#B\u0001\u00192\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u0001\u001a\u0002\u0007=\u0014x-\u0003\u00025_\tQ1+\u001e2tGJL'-\u001a:\u0011\u0005I1D!C\u001c\u0003\u0003\u0003\u0005\tQ!\u00019\u0005\ryFEN\t\u0003#uI!AO\b\u0002\u0015M,(m]2sS\n,'/\u0001\u0004=S:LGO\u0010\u000b\u0004{yz\u0004c\u0001\b\u0001#!)1e\u0001a\u0001I!)1f\u0001a\u0001\u0001B\u0012\u0011i\u0011\t\u0004]M\u0012\u0005C\u0001\nD\t%9t(!A\u0001\u0002\u000b\u0005\u0001\b\u000b\u0002\u0001\u000bB\u0011a)S\u0007\u0002\u000f*\u0011\u0001*C\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001&H\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public class ActorSubscriptionWithCursor<T>
extends ActorSubscription<T>
implements SubscriptionWithCursor<T> {
    private boolean active;
    private long totalDemand;
    private int cursor;

    @Override
    public void dispatch(T element) {
        SubscriptionWithCursor.dispatch$(this, element);
    }

    @Override
    public boolean active() {
        return this.active;
    }

    @Override
    public void active_$eq(boolean x$1) {
        this.active = x$1;
    }

    @Override
    public long totalDemand() {
        return this.totalDemand;
    }

    @Override
    public void totalDemand_$eq(long x$1) {
        this.totalDemand = x$1;
    }

    @Override
    public int cursor() {
        return this.cursor;
    }

    @Override
    public void cursor_$eq(int x$1) {
        this.cursor = x$1;
    }

    public ActorSubscriptionWithCursor(ActorRef _impl, Subscriber<? super T> _subscriber) {
        super(_impl, _subscriber);
        SubscriptionWithCursor.$init$(this);
    }
}

