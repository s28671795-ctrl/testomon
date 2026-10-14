/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.util.AbstractQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%baB\f\u0019!\u0003\r\t!\b\u0005\u0006i\u0001!\t!\u000e\u0005\bs\u0001\u0011\rQ\"\u0001;\u0011\u001d\u0001\u0006A1A\u0005\u0002iDq!a\u0002\u0001\t\u0003\nI\u0001C\u0004\u0002\f\u0001!\t%!\u0004\t\u000f\u0005=\u0001\u0001\"\u0011\u0002\u0012!9\u0011Q\u0004\u0001\u0005B\u0005}\u0001bBA\u0014\u0001\u0011\u0005\u0013\u0011B\u0004\u0006\u007faA\t\u0001\u0011\u0004\u0006/aA\t!\u0011\u0005\u0006\u0005*!\ta\u0011\u0004\u0005\t*\u0001Q\t\u0003\u0005H\u0019\t\u0015\r\u0011\"\u0001I\u0011!yEB!A!\u0002\u0013I\u0005\u0002\u0003)\r\u0005\u000b\u0007I\u0011A)\t\u0011Uc!\u0011!Q\u0001\nICQA\u0011\u0007\u0005\u0002Y3Aa\u0017\u0006\u00019\"A!N\u0005BC\u0002\u0013\u00051\u000e\u0003\u0005n%\t\u0005\t\u0015!\u0003m\u0011\u0015\u0011%\u0003\"\u0001o\u0011\u0015\t(\u0003\"\u0001s\u0005]\u0001&/[8sSRL\u0018+^3vKN#\u0018MY5mSj,'O\u0003\u0002\u001a5\u0005!Q\u000f^5m\u0015\u0005Y\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0002\u001fQM\u0011\u0001a\b\t\u0004A\u00112S\"A\u0011\u000b\u0005e\u0011#\"A\u0012\u0002\t)\fg/Y\u0005\u0003K\u0005\u0012Q\"\u00112tiJ\f7\r^)vKV,\u0007CA\u0014)\u0019\u0001!Q!\u000b\u0001C\u0002)\u0012\u0011!R\t\u0003WE\u0002\"\u0001L\u0018\u000e\u00035R\u0011AL\u0001\u0006g\u000e\fG.Y\u0005\u0003a5\u0012qAT8uQ&tw\r\u0005\u0002-e%\u00111'\f\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u00051\u0004C\u0001\u00178\u0013\tATF\u0001\u0003V]&$\u0018\u0001\u00042bG.LgnZ)vKV,W#A\u001e\u0011\u0007\u0001\"C\bE\u0002>\u0019\u0019r!AP\u0005\u000e\u0003a\tq\u0003\u0015:j_JLG/_)vKV,7\u000b^1cS2L'0\u001a:\u0011\u0005yR1C\u0001\u00062\u0003\u0019a\u0014N\\5u}Q\t\u0001I\u0001\bXe\u0006\u0004\b/\u001a3FY\u0016lWM\u001c;\u0016\u0005\u0019S5C\u0001\u00072\u0003\u001d)G.Z7f]R,\u0012!\u0013\t\u0003O)#Q!\u000b\u0007C\u0002-\u000b\"a\u000b'\u0011\u00051j\u0015B\u0001(.\u0005\r\te._\u0001\tK2,W.\u001a8uA\u000511/Z9Ok6,\u0012A\u0015\t\u0003YMK!\u0001V\u0017\u0003\t1{gnZ\u0001\bg\u0016\fh*^7!)\r9\u0016L\u0017\t\u000412IU\"\u0001\u0006\t\u000b\u001d\u000b\u0002\u0019A%\t\u000bA\u000b\u0002\u0019\u0001*\u00031]\u0013\u0018\r\u001d9fI\u0016cW-\\3oi\u000e{W\u000e]1sCR|'/\u0006\u0002^SN\u0019!C\u00183\u0011\u0005}\u0013W\"\u00011\u000b\u0005\u0005\u0014\u0013\u0001\u00027b]\u001eL!a\u00191\u0003\r=\u0013'.Z2u!\r\u0001SmZ\u0005\u0003M\u0006\u0012!bQ8na\u0006\u0014\u0018\r^8s!\rAF\u0002\u001b\t\u0003O%$Q!\u000b\nC\u0002-\u000b1aY7q+\u0005a\u0007c\u0001\u0011fQ\u0006!1-\u001c9!)\ty\u0007\u000fE\u0002Y%!DQA[\u000bA\u00021\fqaY8na\u0006\u0014X\rF\u0002tmb\u0004\"\u0001\f;\n\u0005Ul#aA%oi\")qO\u0006a\u0001O\u0006\u0011Q-\r\u0005\u0006sZ\u0001\raZ\u0001\u0003KJ*\u0012a\u001f\t\u0004y\u0006\rQ\"A?\u000b\u0005y|\u0018AB1u_6L7MC\u0002\u0002\u0002\u0005\n!bY8oGV\u0014(/\u001a8u\u0013\r\t)! \u0002\u000b\u0003R|W.[2M_:<\u0017\u0001\u00029fK.$\u0012AJ\u0001\u0005g&TX\rF\u0001t\u0003\u0015ygMZ3s)\u0011\t\u0019\"!\u0007\u0011\u00071\n)\"C\u0002\u0002\u00185\u0012qAQ8pY\u0016\fg\u000e\u0003\u0004\u0002\u001c\u0019\u0001\rAJ\u0001\u0002K\u0006A\u0011\u000e^3sCR|'\u000f\u0006\u0002\u0002\"A!\u0001%a\t'\u0013\r\t)#\t\u0002\t\u0013R,'/\u0019;pe\u0006!\u0001o\u001c7m\u0001")
public interface PriorityQueueStabilizer<E> {
    public void akka$util$PriorityQueueStabilizer$_setter_$seqNum_$eq(AtomicLong var1);

    public AbstractQueue<WrappedElement<E>> backingQueue();

    public AtomicLong seqNum();

    public static /* synthetic */ Object peek$(PriorityQueueStabilizer $this) {
        return $this.peek();
    }

    default public E peek() {
        WrappedElement wrappedElement = (WrappedElement)this.backingQueue().peek();
        if (wrappedElement == null) {
            return null;
        }
        return wrappedElement.element();
    }

    public static /* synthetic */ int size$(PriorityQueueStabilizer $this) {
        return $this.size();
    }

    default public int size() {
        return this.backingQueue().size();
    }

    public static /* synthetic */ boolean offer$(PriorityQueueStabilizer $this, Object e) {
        return $this.offer(e);
    }

    default public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        WrappedElement<E> wrappedElement = new WrappedElement<E>(e, this.seqNum().incrementAndGet());
        return this.backingQueue().offer(wrappedElement);
    }

    public static /* synthetic */ Iterator iterator$(PriorityQueueStabilizer $this) {
        return $this.iterator();
    }

    default public Iterator<E> iterator() {
        return new Iterator<E>(this){
            private final Iterator<WrappedElement<E>> backingIterator;

            public void forEachRemaining(Consumer<? super E> x$1) {
                Iterator.super.forEachRemaining(x$1);
            }

            public boolean hasNext() {
                return this.backingIterator.hasNext();
            }

            public E next() {
                return this.backingIterator.next().element();
            }

            public void remove() {
                this.backingIterator.remove();
            }
            {
                this.backingIterator = $outer.backingQueue().iterator();
            }
        };
    }

    public static /* synthetic */ Object poll$(PriorityQueueStabilizer $this) {
        return $this.poll();
    }

    default public E poll() {
        WrappedElement wrappedElement = (WrappedElement)this.backingQueue().poll();
        if (wrappedElement == null) {
            return null;
        }
        return wrappedElement.element();
    }

    public static void $init$(PriorityQueueStabilizer $this) {
        $this.akka$util$PriorityQueueStabilizer$_setter_$seqNum_$eq(new AtomicLong(0L));
    }

    public static class WrappedElement<E> {
        private final E element;
        private final long seqNum;

        public E element() {
            return this.element;
        }

        public long seqNum() {
            return this.seqNum;
        }

        public WrappedElement(E element, long seqNum) {
            this.element = element;
            this.seqNum = seqNum;
        }
    }

    public static class WrappedElementComparator<E>
    implements Comparator<WrappedElement<E>> {
        private final Comparator<E> cmp;

        @Override
        public Comparator<WrappedElement<E>> reversed() {
            return Comparator.super.reversed();
        }

        @Override
        public Comparator<WrappedElement<E>> thenComparing(Comparator<? super WrappedElement<E>> x$1) {
            return Comparator.super.thenComparing(x$1);
        }

        @Override
        public <U> Comparator<WrappedElement<E>> thenComparing(Function<? super WrappedElement<E>, ? extends U> x$1, Comparator<? super U> x$2) {
            return Comparator.super.thenComparing(x$1, x$2);
        }

        @Override
        public <U extends Comparable<? super U>> Comparator<WrappedElement<E>> thenComparing(Function<? super WrappedElement<E>, ? extends U> x$1) {
            return Comparator.super.thenComparing(x$1);
        }

        @Override
        public Comparator<WrappedElement<E>> thenComparingInt(ToIntFunction<? super WrappedElement<E>> x$1) {
            return Comparator.super.thenComparingInt(x$1);
        }

        @Override
        public Comparator<WrappedElement<E>> thenComparingLong(ToLongFunction<? super WrappedElement<E>> x$1) {
            return Comparator.super.thenComparingLong(x$1);
        }

        @Override
        public Comparator<WrappedElement<E>> thenComparingDouble(ToDoubleFunction<? super WrappedElement<E>> x$1) {
            return Comparator.super.thenComparingDouble(x$1);
        }

        public Comparator<E> cmp() {
            return this.cmp;
        }

        @Override
        public int compare(WrappedElement<E> e1, WrappedElement<E> e2) {
            int baseComparison = this.cmp().compare(e1.element(), e2.element());
            if (baseComparison != 0) {
                return baseComparison;
            }
            long diff = e1.seqNum() - e2.seqNum();
            return Long.signum(diff);
        }

        public WrappedElementComparator(Comparator<E> cmp) {
            this.cmp = cmp;
        }
    }
}

