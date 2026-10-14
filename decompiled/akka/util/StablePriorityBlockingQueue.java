/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.PriorityQueueStabilizer;
import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00113AAB\u0004\u0001\u0019!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006\u0003\u0005,\u0001\t\u0005\t\u0015!\u0003-\u0011\u0015y\u0003\u0001\"\u00011\u0011\u001d!\u0004A1A\u0005\u0002UBaa\u0011\u0001!\u0002\u00131$aG*uC\ndW\r\u0015:j_JLG/\u001f\"m_\u000e\\\u0017N\\4Rk\u0016,XM\u0003\u0002\t\u0013\u0005!Q\u000f^5m\u0015\u0005Q\u0011\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\u000e/M\u0019\u0001AD\u0012\u0011\u0007=\u0019R#D\u0001\u0011\u0015\tA\u0011CC\u0001\u0013\u0003\u0011Q\u0017M^1\n\u0005Q\u0001\"!D!cgR\u0014\u0018m\u0019;Rk\u0016,X\r\u0005\u0002\u0017/1\u0001A!\u0002\r\u0001\u0005\u0004I\"!A#\u0012\u0005i\u0001\u0003CA\u000e\u001f\u001b\u0005a\"\"A\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}a\"a\u0002(pi\"Lgn\u001a\t\u00037\u0005J!A\t\u000f\u0003\r\u0005s\u0017PU3g!\r!S%F\u0007\u0002\u000f%\u0011ae\u0002\u0002\u0018!JLwN]5usF+X-^3Ti\u0006\u0014\u0017\u000e\\5{KJ\f\u0001bY1qC\u000eLG/\u001f\t\u00037%J!A\u000b\u000f\u0003\u0007%sG/A\u0002d[B\u00042aD\u0017\u0016\u0013\tq\u0003C\u0001\u0006D_6\u0004\u0018M]1u_J\fa\u0001P5oSRtDcA\u00193gA\u0019A\u0005A\u000b\t\u000b\u001d\u001a\u0001\u0019\u0001\u0015\t\u000b-\u001a\u0001\u0019\u0001\u0017\u0002\u0019\t\f7m[5oOF+X-^3\u0016\u0003Y\u00022a\u000e\u001e=\u001b\u0005A$BA\u001d\u0011\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003wa\u0012Q\u0003\u0015:j_JLG/\u001f\"m_\u000e\\\u0017N\\4Rk\u0016,X\rE\u0002>\u0001Vq!\u0001\n \n\u0005}:\u0011a\u0006)sS>\u0014\u0018\u000e^=Rk\u0016,Xm\u0015;bE&d\u0017N_3s\u0013\t\t%I\u0001\bXe\u0006\u0004\b/\u001a3FY\u0016lWM\u001c;\u000b\u0005}:\u0011!\u00042bG.LgnZ)vKV,\u0007\u0005")
public class StablePriorityBlockingQueue<E>
extends AbstractQueue<E>
implements PriorityQueueStabilizer<E> {
    private final PriorityBlockingQueue<PriorityQueueStabilizer.WrappedElement<E>> backingQueue;
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
    public PriorityBlockingQueue<PriorityQueueStabilizer.WrappedElement<E>> backingQueue() {
        return this.backingQueue;
    }

    public StablePriorityBlockingQueue(int capacity, Comparator<E> cmp) {
        PriorityQueueStabilizer.$init$(this);
        this.backingQueue = new PriorityBlockingQueue<E>(capacity, new PriorityQueueStabilizer.WrappedElementComparator<E>(cmp));
        Statics.releaseFence();
    }
}

