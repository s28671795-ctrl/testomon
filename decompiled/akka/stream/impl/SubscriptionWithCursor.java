/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.ResizableMultiReaderRingBuffer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00194\u0001BC\u0006\u0011\u0002\u0007\u0005q\"\u0005\u0005\u0006W\u0001!\t!\f\u0005\u0006i\u00011\t!\u000e\u0005\u0006\u0013\u0002!\tA\u0013\u0005\b\u001b\u0002\u0001\r\u0011\"\u0001O\u0011\u001d\u0011\u0006\u00011A\u0005\u0002MCqA\u0016\u0001A\u0002\u0013\u0005q\u000bC\u0004\\\u0001\u0001\u0007I\u0011\u0001/\t\u000fy\u0003\u0001\u0019!C\u0001?\"91\r\u0001a\u0001\n\u0003!'AF*vEN\u001c'/\u001b9uS>tw+\u001b;i\u0007V\u00148o\u001c:\u000b\u00051i\u0011\u0001B5na2T!AD\b\u0002\rM$(/Z1n\u0015\u0005\u0001\u0012\u0001B1lW\u0006,\"A\u0005!\u0014\t\u0001\u00192d\t\t\u0003)ei\u0011!\u0006\u0006\u0003-]\tA\u0001\\1oO*\t\u0001$\u0001\u0003kCZ\f\u0017B\u0001\u000e\u0016\u0005\u0019y%M[3diB\u0011A$I\u0007\u0002;)\u0011adH\u0001\u0010e\u0016\f7\r^5wKN$(/Z1ng*\t\u0001%A\u0002pe\u001eL!AI\u000f\u0003\u0019M+(m]2sSB$\u0018n\u001c8\u0011\u0005\u0011BcBA\u0013'\u001b\u0005Y\u0011BA\u0014\f\u0003y\u0011Vm]5{C\ndW-T;mi&\u0014V-\u00193feJKgn\u001a\"vM\u001a,'/\u0003\u0002*U\t11)\u001e:t_JT!aJ\u0006\u0002\r\u0011Jg.\u001b;%\u0007\u0001!\u0012A\f\t\u0003_Ij\u0011\u0001\r\u0006\u0002c\u0005)1oY1mC&\u00111\u0007\r\u0002\u0005+:LG/\u0001\u0006tk\n\u001c8M]5cKJ,\u0012A\u000e\u0019\u0003oq\u00022\u0001\b\u001d;\u0013\tITD\u0001\u0006Tk\n\u001c8M]5cKJ\u0004\"a\u000f\u001f\r\u0001\u0011IQHAA\u0001\u0002\u0003\u0015\tA\u0010\u0002\u0004?\u0012\n\u0014CA G!\tY\u0004\tB\u0003B\u0001\t\u0007!IA\u0001U#\t\u0019e\t\u0005\u00020\t&\u0011Q\t\r\u0002\b\u001d>$\b.\u001b8h!\tys)\u0003\u0002Ia\t\u0019\u0011I\\=\u0002\u0011\u0011L7\u000f]1uG\"$\"AL&\t\u000b1\u001b\u0001\u0019A \u0002\u000f\u0015dW-\\3oi\u00061\u0011m\u0019;jm\u0016,\u0012a\u0014\t\u0003_AK!!\u0015\u0019\u0003\u000f\t{w\u000e\\3b]\u0006Q\u0011m\u0019;jm\u0016|F%Z9\u0015\u00059\"\u0006bB+\u0006\u0003\u0003\u0005\raT\u0001\u0004q\u0012\n\u0014a\u0003;pi\u0006dG)Z7b]\u0012,\u0012\u0001\u0017\t\u0003_eK!A\u0017\u0019\u0003\t1{gnZ\u0001\u0010i>$\u0018\r\u001c#f[\u0006tGm\u0018\u0013fcR\u0011a&\u0018\u0005\b+\u001e\t\t\u00111\u0001Y\u0003\u0019\u0019WO]:peV\t\u0001\r\u0005\u00020C&\u0011!\r\r\u0002\u0004\u0013:$\u0018AC2veN|'o\u0018\u0013fcR\u0011a&\u001a\u0005\b+&\t\t\u00111\u0001a\u0001")
public interface SubscriptionWithCursor<T>
extends Subscription,
ResizableMultiReaderRingBuffer.Cursor {
    public Subscriber<? super T> subscriber();

    public static /* synthetic */ void dispatch$(SubscriptionWithCursor $this, Object element) {
        $this.dispatch(element);
    }

    default public void dispatch(T element) {
        ReactiveStreamsCompliance$.MODULE$.tryOnNext(this.subscriber(), element);
    }

    public boolean active();

    public void active_$eq(boolean var1);

    public long totalDemand();

    public void totalDemand_$eq(long var1);

    @Override
    public int cursor();

    @Override
    public void cursor_$eq(int var1);

    public static void $init$(SubscriptionWithCursor $this) {
        $this.active_$eq(true);
        $this.totalDemand_$eq(0L);
        $this.cursor_$eq(0);
    }
}

