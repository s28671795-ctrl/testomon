/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.Cancel;
import akka.stream.impl.RequestMore;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00014Q\u0001C\u0005\u0001\u001b=A\u0001B\u0003\u0001\u0003\u0006\u0004%)A\t\u0005\tS\u0001\u0011\t\u0011)A\u0007G!A!\u0006\u0001BC\u0002\u0013\u00151\u0006\u0003\u00055\u0001\t\u0005\t\u0015!\u0004-\u0011\u0015\u0019\u0005\u0001\"\u0001E\u0011\u0015i\u0005\u0001\"\u0011O\u0011\u00159\u0006\u0001\"\u0011Y\u0005E\t5\r^8s'V\u00147o\u0019:jaRLwN\u001c\u0006\u0003\u0015-\tA![7qY*\u0011A\"D\u0001\u0007gR\u0014X-Y7\u000b\u00039\tA!Y6lCV\u0011\u0001cN\n\u0004\u0001EI\u0002C\u0001\n\u0018\u001b\u0005\u0019\"B\u0001\u000b\u0016\u0003\u0011a\u0017M\\4\u000b\u0003Y\tAA[1wC&\u0011\u0001d\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005iyR\"A\u000e\u000b\u0005qi\u0012a\u0004:fC\u000e$\u0018N^3tiJ,\u0017-\\:\u000b\u0003y\t1a\u001c:h\u0013\t\u00013D\u0001\u0007Tk\n\u001c8M]5qi&|gn\u0001\u0001\u0016\u0003\r\u0002\"\u0001J\u0014\u000e\u0003\u0015R!AJ\u0007\u0002\u000b\u0005\u001cGo\u001c:\n\u0005!*#\u0001C!di>\u0014(+\u001a4\u0002\u000b%l\u0007\u000f\u001c\u0011\u0002\u0015M,(m]2sS\n,'/F\u0001-a\ti#\u0007E\u0002\u001b]AJ!aL\u000e\u0003\u0015M+(m]2sS\n,'\u000f\u0005\u00022e1\u0001A!C\u001a\u0005\u0003\u0003\u0005\tQ!\u00016\u0005\ryF%N\u0001\fgV\u00147o\u0019:jE\u0016\u0014\b%\u0005\u00027\u0001B\u0011\u0011g\u000e\u0003\u0006q\u0001\u0011\r!\u000f\u0002\u0002)F\u0011!\b\u0011\t\u0003wyj\u0011\u0001\u0010\u0006\u0002{\u0005)1oY1mC&\u0011q\b\u0010\u0002\b\u001d>$\b.\u001b8h!\tY\u0014)\u0003\u0002Cy\t\u0019\u0011I\\=\u0002\rqJg.\u001b;?)\r)u\t\u0013\t\u0004\r\u00021T\"A\u0005\t\u000b))\u0001\u0019A\u0012\t\u000b)*\u0001\u0019A%1\u0005)c\u0005c\u0001\u000e/\u0017B\u0011\u0011\u0007\u0014\u0003\ng!\u000b\t\u0011!A\u0003\u0002U\nqA]3rk\u0016\u001cH\u000f\u0006\u0002P%B\u00111\bU\u0005\u0003#r\u0012A!\u00168ji\")1K\u0002a\u0001)\u0006AQ\r\\3nK:$8\u000f\u0005\u0002<+&\u0011a\u000b\u0010\u0002\u0005\u0019>tw-\u0001\u0004dC:\u001cW\r\u001c\u000b\u0002\u001f\"\u0012\u0001A\u0017\t\u00037zk\u0011\u0001\u0018\u0006\u0003;6\t!\"\u00198o_R\fG/[8o\u0013\tyFLA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public class ActorSubscription<T>
implements Subscription {
    private final ActorRef impl;
    private final Subscriber<? super T> subscriber;

    public final ActorRef impl() {
        return this.impl;
    }

    public final Subscriber<? super T> subscriber() {
        return this.subscriber;
    }

    @Override
    public void request(long elements) {
        RequestMore x$1 = new RequestMore(this, elements);
        ActorRef x$2 = this.impl().$bang$default$2(x$1);
        this.impl().$bang(x$1, x$2);
    }

    @Override
    public void cancel() {
        Cancel x$1 = new Cancel(this);
        ActorRef x$2 = this.impl().$bang$default$2(x$1);
        this.impl().$bang(x$1, x$2);
    }

    public ActorSubscription(ActorRef impl, Subscriber<? super T> subscriber) {
        this.impl = impl;
        this.subscriber = subscriber;
    }
}

