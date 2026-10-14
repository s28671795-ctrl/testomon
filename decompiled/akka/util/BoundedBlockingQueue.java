/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import scala.MatchError;
import scala.Predef$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005]g\u0001\u0002\u0013&\u0001)B\u0001b\u0012\u0001\u0003\u0006\u0004%\t\u0001\u0013\u0005\t\u0019\u0002\u0011\t\u0011)A\u0005\u0013\"AQ\n\u0001BC\u0002\u0013%a\n\u0003\u0005S\u0001\t\u0005\t\u0015!\u0003P\u0011\u0015\u0019\u0006\u0001\"\u0001U\u0011\u001dI\u0006A1A\u0005\u0012iCa!\u0019\u0001!\u0002\u0013Y\u0006b\u00022\u0001\u0005\u0004%\tb\u0019\u0005\u0007O\u0002\u0001\u000b\u0011\u00023\t\u000f!\u0004!\u0019!C\tG\"1\u0011\u000e\u0001Q\u0001\n\u0011DQA\u001b\u0001\u0005\u0012-DQ\u0001\u001c\u0001\u0005\u00125DQA\u001c\u0001\u0005\u00125DQa\u001c\u0001\u0005\u0002ADQA\u001e\u0001\u0005\u0002]DQ\u0001\u001f\u0001\u0005\u0002eDQ\u0001\u001f\u0001\u0005\u0002yDq!!\u0006\u0001\t\u0003\t9\u0002\u0003\u0004\u0002\u0016\u0001!\ta\u001e\u0005\b\u0003;\u0001A\u0011IA\u0010\u0011\u001d\t\u0019\u0003\u0001C!\u0003KAq!!\u000b\u0001\t\u0003\nY\u0003C\u0004\u0002.\u0001!\t!a\f\t\u000f\u0005E\u0002\u0001\"\u0001\u00020!1\u00111\u0007\u0001\u0005\u0002]Dq!!\u000e\u0001\t\u0003\t9\u0004C\u0004\u00026\u0001!\t!a\u0015\t\u000f\u0005\u0015\u0004\u0001\"\u0011\u0002h!9\u0011q\u000f\u0001\u0005B\u0005e\u0004bBAD\u0001\u0011\u0005\u0013\u0011\u0012\u0005\b\u0003/\u0003A\u0011AAM\u0011\u001d\t\t\u000b\u0001C!\u0003GCq!a+\u0001\t\u0003\ni\u000bC\u0004\u0002\"\u0002!\t%a,\u0003)\t{WO\u001c3fI\ncwnY6j]\u001e\fV/Z;f\u0015\t1s%\u0001\u0003vi&d'\"\u0001\u0015\u0002\t\u0005\\7.Y\u0002\u0001+\tYSgE\u0002\u0001Y\u0005\u00032!L\u00194\u001b\u0005q#B\u0001\u00140\u0015\u0005\u0001\u0014\u0001\u00026bm\u0006L!A\r\u0018\u0003\u001b\u0005\u00137\u000f\u001e:bGR\fV/Z;f!\t!T\u0007\u0004\u0001\u0005\u000bY\u0002!\u0019A\u001c\u0003\u0003\u0015\u000b\"\u0001\u000f \u0011\u0005ebT\"\u0001\u001e\u000b\u0003m\nQa]2bY\u0006L!!\u0010\u001e\u0003\u000f9{G\u000f[5oOB\u0011\u0011hP\u0005\u0003\u0001j\u0012a!\u00118z%\u00164\u0007c\u0001\"Fg5\t1I\u0003\u0002E]\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005\u0019\u001b%!\u0004\"m_\u000e\\\u0017N\\4Rk\u0016,X-A\u0006nCb\u001c\u0015\r]1dSRLX#A%\u0011\u0005eR\u0015BA&;\u0005\rIe\u000e^\u0001\r[\u0006D8)\u00199bG&$\u0018\u0010I\u0001\bE\u0006\u001c7.\u001b8h+\u0005y\u0005cA\u0017Qg%\u0011\u0011K\f\u0002\u0006#V,W/Z\u0001\tE\u0006\u001c7.\u001b8hA\u00051A(\u001b8jiz\"2!V,Y!\r1\u0006aM\u0007\u0002K!)q)\u0002a\u0001\u0013\")Q*\u0002a\u0001\u001f\u0006!An\\2l+\u0005Y\u0006C\u0001/`\u001b\u0005i&B\u00010D\u0003\u0015awnY6t\u0013\t\u0001WLA\u0007SK\u0016tGO]1oi2{7m[\u0001\u0006Y>\u001c7\u000eI\u0001\t]>$X)\u001c9usV\tA\r\u0005\u0002]K&\u0011a-\u0018\u0002\n\u0007>tG-\u001b;j_:\f\u0011B\\8u\u000b6\u0004H/\u001f\u0011\u0002\u000f9|GOR;mY\u0006Aan\u001c;Gk2d\u0007%\u0001\u0006de\u0016\fG/\u001a'pG.$\u0012aW\u0001\u0018GJ,\u0017\r^3O_R,U\u000e\u001d;z\u0007>tG-\u001b;j_:$\u0012\u0001Z\u0001\u0017GJ,\u0017\r^3O_R4U\u000f\u001c7D_:$\u0017\u000e^5p]\u0006\u0019\u0001/\u001e;\u0015\u0005E$\bCA\u001ds\u0013\t\u0019(H\u0001\u0003V]&$\b\"B;\u0010\u0001\u0004\u0019\u0014!A3\u0002\tQ\f7.\u001a\u000b\u0002g\u0005)qN\u001a4feR\u0011!0 \t\u0003smL!\u0001 \u001e\u0003\u000f\t{w\u000e\\3b]\")Q/\u0005a\u0001gQ1!p`A\u0001\u0003\u0017AQ!\u001e\nA\u0002MBq!a\u0001\u0013\u0001\u0004\t)!A\u0004uS6,w.\u001e;\u0011\u0007e\n9!C\u0002\u0002\ni\u0012A\u0001T8oO\"9\u0011Q\u0002\nA\u0002\u0005=\u0011\u0001B;oSR\u00042AQA\t\u0013\r\t\u0019b\u0011\u0002\t)&lW-\u00168ji\u0006!\u0001o\u001c7m)\u0015\u0019\u0014\u0011DA\u000e\u0011\u001d\t\u0019a\u0005a\u0001\u0003\u000bAq!!\u0004\u0014\u0001\u0004\ty!\u0001\u0004sK6|g/\u001a\u000b\u0004u\u0006\u0005\u0002\"B;\u0016\u0001\u0004q\u0014\u0001C2p]R\f\u0017N\\:\u0015\u0007i\f9\u0003C\u0003v-\u0001\u0007a(A\u0003dY\u0016\f'\u000fF\u0001r\u0003E\u0011X-\\1j]&twmQ1qC\u000eLG/\u001f\u000b\u0002\u0013\u0006!1/\u001b>f\u0003\u0011\u0001X-Z6\u0002\u000f\u0011\u0014\u0018-\u001b8U_R\u0019\u0011*!\u000f\t\u000f\u0005m2\u00041\u0001\u0002>\u0005\t1\r\r\u0003\u0002@\u0005\u001d\u0003#B\u0017\u0002B\u0005\u0015\u0013bAA\"]\tQ1i\u001c7mK\u000e$\u0018n\u001c8\u0011\u0007Q\n9\u0005\u0002\u0007\u0002J\u0005e\u0012\u0011!A\u0001\u0006\u0003\tYEA\u0002`IE\n2aMA'!\rI\u0014qJ\u0005\u0004\u0003#R$aA!osR)\u0011*!\u0016\u0002b!9\u00111\b\u000fA\u0002\u0005]\u0003\u0007BA-\u0003;\u0002R!LA!\u00037\u00022\u0001NA/\t1\ty&!\u0016\u0002\u0002\u0003\u0005)\u0011AA&\u0005\ryFE\r\u0005\u0007\u0003Gb\u0002\u0019A%\u0002\u00175\f\u00070\u00127f[\u0016tGo]\u0001\fG>tG/Y5og\u0006cG\u000eF\u0002{\u0003SBq!a\u000f\u001e\u0001\u0004\tY\u0007\r\u0003\u0002n\u0005E\u0004#B\u0017\u0002B\u0005=\u0004c\u0001\u001b\u0002r\u0011a\u00111OA5\u0003\u0003\u0005\tQ!\u0001\u0002v\t\u0019q\fJ\u001a\u0012\u0007a\ni%A\u0005sK6|g/Z!mYR\u0019!0a\u001f\t\u000f\u0005mb\u00041\u0001\u0002~A\"\u0011qPAB!\u0015i\u0013\u0011IAA!\r!\u00141\u0011\u0003\r\u0003\u000b\u000bY(!A\u0001\u0002\u000b\u0005\u0011Q\u000f\u0002\u0004?\u0012\"\u0014!\u0003:fi\u0006Lg.\u00117m)\rQ\u00181\u0012\u0005\b\u0003wy\u0002\u0019AAGa\u0011\ty)a%\u0011\u000b5\n\t%!%\u0011\u0007Q\n\u0019\n\u0002\u0007\u0002\u0016\u0006-\u0015\u0011!A\u0001\u0006\u0003\t)HA\u0002`IU\n\u0001\"\u001b;fe\u0006$xN\u001d\u000b\u0003\u00037\u0003B!LAOg%\u0019\u0011q\u0014\u0018\u0003\u0011%#XM]1u_J\fq\u0001^8BeJ\f\u0017\u0010\u0006\u0002\u0002&B!\u0011(a*?\u0013\r\tIK\u000f\u0002\u0006\u0003J\u0014\u0018-_\u0001\bSN,U\u000e\u001d;z)\u0005QX\u0003BAY\u0003{#B!a-\u0002NB)\u0011(a*\u00026J1\u0011qWA^\u0003\u00034a!!/\u0001\u0001\u0005U&\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004c\u0001\u001b\u0002>\u00129\u0011qX\u0012C\u0002\u0005U$!\u0001-\u0011\t\u0005\r\u0017\u0011Z\u0007\u0003\u0003\u000bT1!a20\u0003\u0011a\u0017M\\4\n\t\u0005-\u0017Q\u0019\u0002\u0007\u001f\nTWm\u0019;\t\u000f\u0005=7\u00051\u0001\u0002R\u0006\t\u0011\rE\u0003:\u0003O\u000b\u0019NE\u0003\u0002V\u0006mfH\u0002\u0004\u0002:\u0002\u0001\u00111\u001b")
public class BoundedBlockingQueue<E>
extends AbstractQueue<E>
implements BlockingQueue<E> {
    private final int maxCapacity;
    private final Queue<E> akka$util$BoundedBlockingQueue$$backing;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    public int maxCapacity() {
        return this.maxCapacity;
    }

    public Queue<E> akka$util$BoundedBlockingQueue$$backing() {
        return this.akka$util$BoundedBlockingQueue$$backing;
    }

    public ReentrantLock lock() {
        return this.lock;
    }

    public Condition notEmpty() {
        return this.notEmpty;
    }

    public Condition notFull() {
        return this.notFull;
    }

    public ReentrantLock createLock() {
        return new ReentrantLock(false);
    }

    public Condition createNotEmptyCondition() {
        return this.lock().newCondition();
    }

    public Condition createNotFullCondition() {
        return this.lock().newCondition();
    }

    @Override
    public void put(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        this.lock().lockInterruptibly();
        try {
            this.putElement$1(e);
        }
        finally {
            this.lock().unlock();
        }
    }

    @Override
    public E take() {
        Object object;
        this.lock().lockInterruptibly();
        try {
            object = this.takeElement$1();
        }
        finally {
            this.lock().unlock();
        }
        return (E)object;
    }

    @Override
    public boolean offer(E e) {
        boolean bl;
        if (e == null) {
            throw new NullPointerException();
        }
        this.lock().lock();
        try {
            if (this.akka$util$BoundedBlockingQueue$$backing().size() == this.maxCapacity()) {
                bl = false;
            } else {
                Predef$.MODULE$.require(this.akka$util$BoundedBlockingQueue$$backing().offer(e));
                this.notEmpty().signal();
                bl = true;
            }
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public boolean offer(E e, long timeout2, TimeUnit unit) {
        boolean bl;
        if (e == null) {
            throw new NullPointerException();
        }
        this.lock().lockInterruptibly();
        try {
            bl = this.offerElement$1(unit.toNanos(timeout2), e);
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public E poll(long timeout2, TimeUnit unit) {
        Object object;
        this.lock().lockInterruptibly();
        try {
            object = this.pollElement$1(unit.toNanos(timeout2));
        }
        finally {
            this.lock().unlock();
        }
        return (E)object;
    }

    @Override
    public E poll() {
        E e;
        this.lock().lock();
        try {
            E e2 = this.akka$util$BoundedBlockingQueue$$backing().poll();
            if (e2 == null) {
                e = null;
            } else {
                this.notFull().signal();
                e = e2;
            }
        }
        finally {
            this.lock().unlock();
        }
        return e;
    }

    @Override
    public boolean remove(Object e) {
        boolean bl;
        if (e == null) {
            throw new NullPointerException();
        }
        this.lock().lock();
        try {
            if (this.akka$util$BoundedBlockingQueue$$backing().remove(e)) {
                this.notFull().signal();
                bl = true;
            } else {
                bl = false;
            }
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public boolean contains(Object e) {
        boolean bl;
        if (e == null) {
            throw new NullPointerException();
        }
        this.lock().lock();
        try {
            bl = this.akka$util$BoundedBlockingQueue$$backing().contains(e);
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public void clear() {
        this.lock().lock();
        try {
            this.akka$util$BoundedBlockingQueue$$backing().clear();
            this.notFull().signalAll();
        }
        finally {
            this.lock().unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        int n;
        this.lock().lock();
        try {
            n = this.maxCapacity() - this.akka$util$BoundedBlockingQueue$$backing().size();
        }
        finally {
            this.lock().unlock();
        }
        return n;
    }

    @Override
    public int size() {
        int n;
        this.lock().lock();
        try {
            n = this.akka$util$BoundedBlockingQueue$$backing().size();
        }
        finally {
            this.lock().unlock();
        }
        return n;
    }

    @Override
    public E peek() {
        E e;
        this.lock().lock();
        try {
            e = this.akka$util$BoundedBlockingQueue$$backing().peek();
        }
        finally {
            this.lock().unlock();
        }
        return e;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return this.drainTo(c, Integer.MAX_VALUE);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        void v0;
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalArgumentException();
        }
        if (c == this.akka$util$BoundedBlockingQueue$$backing()) {
            throw new IllegalArgumentException();
        }
        if (maxElements <= 0) {
            return 0;
        }
        this.lock().lock();
        try {
            void var3_3;
            int n = this.drainOne$1(BoundedBlockingQueue.drainOne$default$1$1(), maxElements, c);
            if (n > 0) {
                this.notFull().signalAll();
            }
            v0 = var3_3;
        }
        finally {
            this.lock().unlock();
        }
        return (int)v0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean bl;
        this.lock().lock();
        try {
            bl = this.akka$util$BoundedBlockingQueue$$backing().containsAll(c);
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean bl;
        this.lock().lock();
        try {
            if (this.akka$util$BoundedBlockingQueue$$backing().removeAll(c)) {
                int sz = this.akka$util$BoundedBlockingQueue$$backing().size();
                if (sz < this.maxCapacity()) {
                    this.notFull().signal();
                }
                if (sz > 0) {
                    this.notEmpty().signal();
                }
                bl = true;
            } else {
                bl = false;
            }
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean bl;
        this.lock().lock();
        try {
            if (this.akka$util$BoundedBlockingQueue$$backing().retainAll(c)) {
                int sz = this.akka$util$BoundedBlockingQueue$$backing().size();
                if (sz < this.maxCapacity()) {
                    this.notFull().signal();
                }
                if (sz > 0) {
                    this.notEmpty().signal();
                }
                bl = true;
            } else {
                bl = false;
            }
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator iterator2;
        this.lock().lock();
        try {
            Object[] elements = this.akka$util$BoundedBlockingQueue$$backing().toArray();
            iterator2 = new Iterator<E>(this, elements){
                private int at;
                private int last;
                private final /* synthetic */ BoundedBlockingQueue $outer;
                private final Object[] elements$1;

                public void forEachRemaining(Consumer<? super E> x$1) {
                    Iterator.super.forEachRemaining(x$1);
                }

                private int at() {
                    return this.at;
                }

                private void at_$eq(int x$1) {
                    this.at = x$1;
                }

                private int last() {
                    return this.last;
                }

                private void last_$eq(int x$1) {
                    this.last = x$1;
                }

                public boolean hasNext() {
                    return this.at() < this.elements$1.length;
                }

                public E next() {
                    if (this.at() >= this.elements$1.length) {
                        throw new NoSuchElementException();
                    }
                    this.last_$eq(this.at());
                    this.at_$eq(this.at() + 1);
                    return (E)this.elements$1[this.last()];
                }

                public void remove() {
                    if (this.last() < 0) {
                        throw new IllegalStateException();
                    }
                    Object target = this.elements$1[this.last()];
                    this.last_$eq(-1);
                    this.$outer.lock().lock();
                    try {
                        this.removeTarget$1(this.removeTarget$default$1$1(), target);
                    }
                    finally {
                        this.$outer.lock().unlock();
                    }
                }

                private final void removeTarget$1(Iterator i, Object target$1) {
                    block1: {
                        BoxedUnit boxedUnit;
                        while (i.hasNext()) {
                            if (i.next() != target$1) continue;
                            i.remove();
                            this.$outer.notFull().signal();
                            boxedUnit = BoxedUnit.UNIT;
                            break block1;
                        }
                        boxedUnit = BoxedUnit.UNIT;
                    }
                }

                private final Iterator removeTarget$default$1$1() {
                    return this.$outer.akka$util$BoundedBlockingQueue$$backing().iterator();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.elements$1 = elements$1;
                    this.at = 0;
                    this.last = -1;
                }
            };
        }
        finally {
            this.lock().unlock();
        }
        return iterator2;
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray;
        this.lock().lock();
        try {
            objectArray = this.akka$util$BoundedBlockingQueue$$backing().toArray();
        }
        finally {
            this.lock().unlock();
        }
        return objectArray;
    }

    @Override
    public boolean isEmpty() {
        boolean bl;
        this.lock().lock();
        try {
            bl = this.akka$util$BoundedBlockingQueue$$backing().isEmpty();
        }
        finally {
            this.lock().unlock();
        }
        return bl;
    }

    @Override
    public <X> Object[] toArray(Object[] a) {
        Object[] objectArray;
        this.lock().lock();
        try {
            objectArray = this.akka$util$BoundedBlockingQueue$$backing().toArray(a);
        }
        finally {
            this.lock().unlock();
        }
        return objectArray;
    }

    private final void putElement$1(Object e$1) {
        while (true) {
            if (this.akka$util$BoundedBlockingQueue$$backing().size() < this.maxCapacity()) break;
            this.notFull().await();
        }
        Predef$.MODULE$.require(this.akka$util$BoundedBlockingQueue$$backing().offer(e$1));
        this.notEmpty().signal();
    }

    private final Object takeElement$1() {
        while (true) {
            if (!this.akka$util$BoundedBlockingQueue$$backing().isEmpty()) {
                E e = this.akka$util$BoundedBlockingQueue$$backing().poll();
                Predef$.MODULE$.require(e != null);
                this.notFull().signal();
                return e;
            }
            this.notEmpty().await();
        }
    }

    private final boolean offerElement$1(long remainingNanos, Object e$2) {
        while (true) {
            if (this.akka$util$BoundedBlockingQueue$$backing().size() < this.maxCapacity()) {
                Predef$.MODULE$.require(this.akka$util$BoundedBlockingQueue$$backing().offer(e$2));
                this.notEmpty().signal();
                return true;
            }
            if (remainingNanos <= 0L) {
                return false;
            }
            remainingNanos = this.notFull().awaitNanos(remainingNanos);
        }
    }

    private final Object pollElement$1(long remainingNanos) {
        E e;
        while (true) {
            boolean bl = false;
            Object var6_3 = null;
            e = this.akka$util$BoundedBlockingQueue$$backing().poll();
            if (e == null) {
                bl = true;
                var6_3 = e;
                if (remainingNanos <= 0L) {
                    return null;
                }
            }
            if (!bl) break;
            remainingNanos = this.notEmpty().awaitNanos(remainingNanos);
        }
        this.notFull().signal();
        return e;
    }

    private final int drainOne$1(int n, int maxElements$1, Collection c$1) {
        while (n < maxElements$1) {
            E e = this.akka$util$BoundedBlockingQueue$$backing().poll();
            if (e == null) {
                return n;
            }
            c$1.add(e);
            ++n;
        }
        return n;
    }

    private static final int drainOne$default$1$1() {
        return 0;
    }

    public BoundedBlockingQueue(int maxCapacity, Queue<E> backing) {
        this.maxCapacity = maxCapacity;
        this.akka$util$BoundedBlockingQueue$$backing = backing;
        Queue<E> queue = backing;
        if (queue == null) {
            throw new IllegalArgumentException("Backing Queue may not be null");
        }
        if (queue instanceof BlockingQueue) {
            BlockingQueue blockingQueue = (BlockingQueue)queue;
            Predef$.MODULE$.require(maxCapacity > 0);
            Predef$.MODULE$.require(blockingQueue.size() == 0);
            Predef$.MODULE$.require(blockingQueue.remainingCapacity() >= maxCapacity);
        } else if (queue != null) {
            Queue<E> queue2 = queue;
            Predef$.MODULE$.require(queue2.size() == 0);
            Predef$.MODULE$.require(maxCapacity > 0);
        } else {
            throw new MatchError(queue);
        }
        this.lock = this.createLock();
        this.notEmpty = this.createNotEmptyCondition();
        this.notFull = this.createNotFullCondition();
    }
}

