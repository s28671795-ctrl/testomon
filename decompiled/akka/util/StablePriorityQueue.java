/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.PriorityQueueStabilizer;
import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00053AAB\u0004\u0001\u0019!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006\u0003\u0005,\u0001\t\u0005\t\u0015!\u0003-\u0011\u0015y\u0003\u0001\"\u00011\u0011\u001d!\u0004A1A\u0005\u0002UBa\u0001\u0011\u0001!\u0002\u00131$aE*uC\ndW\r\u0015:j_JLG/_)vKV,'B\u0001\u0005\n\u0003\u0011)H/\u001b7\u000b\u0003)\tA!Y6lC\u000e\u0001QCA\u0007\u0018'\r\u0001ab\t\t\u0004\u001fM)R\"\u0001\t\u000b\u0005!\t\"\"\u0001\n\u0002\t)\fg/Y\u0005\u0003)A\u0011Q\"\u00112tiJ\f7\r^)vKV,\u0007C\u0001\f\u0018\u0019\u0001!Q\u0001\u0007\u0001C\u0002e\u0011\u0011!R\t\u00035\u0001\u0002\"a\u0007\u0010\u000e\u0003qQ\u0011!H\u0001\u0006g\u000e\fG.Y\u0005\u0003?q\u0011qAT8uQ&tw\r\u0005\u0002\u001cC%\u0011!\u0005\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0007\u0011*S#D\u0001\b\u0013\t1sAA\fQe&|'/\u001b;z#V,W/Z*uC\nLG.\u001b>fe\u0006A1-\u00199bG&$\u0018\u0010\u0005\u0002\u001cS%\u0011!\u0006\b\u0002\u0004\u0013:$\u0018aA2naB\u0019q\"L\u000b\n\u00059\u0002\"AC\"p[B\f'/\u0019;pe\u00061A(\u001b8jiz\"2!\r\u001a4!\r!\u0003!\u0006\u0005\u0006O\r\u0001\r\u0001\u000b\u0005\u0006W\r\u0001\r\u0001L\u0001\rE\u0006\u001c7.\u001b8h#V,W/Z\u000b\u0002mA\u0019qbN\u001d\n\u0005a\u0002\"!\u0004)sS>\u0014\u0018\u000e^=Rk\u0016,X\rE\u0002;{Uq!\u0001J\u001e\n\u0005q:\u0011a\u0006)sS>\u0014\u0018\u000e^=Rk\u0016,Xm\u0015;bE&d\u0017N_3s\u0013\tqtH\u0001\bXe\u0006\u0004\b/\u001a3FY\u0016lWM\u001c;\u000b\u0005q:\u0011!\u00042bG.LgnZ)vKV,\u0007\u0005")
public class StablePriorityQueue<E>
extends AbstractQueue<E>
implements PriorityQueueStabilizer<E> {
    private final PriorityQueue<PriorityQueueStabilizer.WrappedElement<E>> backingQueue;
    private AtomicLong seqNum;

    @Override
    public E peek() {
        return (E)PriorityQueueStabilizer.peek$(this);
    }

    @Override
    public int size() {
        return PriorityQueueStabilizer.size$(this);
    }

    @Override
    public boolean offer(E e) {
        return PriorityQueueStabilizer.offer$(this, e);
    }

    @Override
    public Iterator<E> iterator() {
        return PriorityQueueStabilizer.iterator$(this);
    }

    @Override
    public E poll() {
        return (E)PriorityQueueStabilizer.poll$(this);
    }

    @Override
    public AtomicLong seqNum() {
        return this.seqNum;
    }

    @Override
    public void akka$util$PriorityQueueStabilizer$_setter_$seqNum_$eq(AtomicLong x$1) {
        this.seqNum = x$1;
    }

    @Override
    public PriorityQueue<PriorityQueueStabilizer.WrappedElement<E>> backingQueue() {
        return this.backingQueue;
    }

    public StablePriorityQueue(int capacity, Comparator<E> cmp) {
        PriorityQueueStabilizer.$init$(this);
        this.backingQueue = new PriorityQueue<E>(capacity, new PriorityQueueStabilizer.WrappedElementComparator<E>(cmp));
        Statics.releaseFence();
    }
}

