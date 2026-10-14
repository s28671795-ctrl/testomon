/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import java.io.Serializable;
import java.util.LinkedList;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005e!\u0002\u001c8\u0005mj\u0004\u0002C+\u0001\u0005\u000b\u0007I\u0011\u0001,\t\u0011i\u0003!\u0011!Q\u0001\n]CQa\u0017\u0001\u0005\u0002qCQa\u0018\u0001\u0005\u0002YCQ\u0001\u0019\u0001\u0005\u0002\u0005DQ!\u001a\u0001\u0005\u0002\u0005DQA\u001a\u0001\u0005\u0002\u0005DQa\u001a\u0001\u0005\u0002!DQA\u001c\u0001\u0005\u0002=DQ\u0001\u001d\u0001\u0005\u0002=DQ!\u001d\u0001\u0005\u0002IDQa\u001d\u0001\u0005\u0002IDQ\u0001\u001e\u0001\u0005\u0002IDq!\u001e\u0001A\u0002\u0013%a\u000fC\u0004x\u0001\u0001\u0007I\u0011\u0002=\t\rm\u0004\u0001\u0015)\u0003F\u000f!\t9a\u000eE\u0001w\u0005%aa\u0002\u001c8\u0011\u0003Y\u00141\u0002\u0005\u00077J!\t!!\u0004\u0007\r\u0005=!CBA\t\u0011!)FC!b\u0001\n\u00032\u0006\u0002\u0003.\u0015\u0005\u0003\u0005\u000b\u0011B,\t\u0015\u0005mAC!A!\u0002\u0013\ti\u0002\u0003\u0004\\)\u0011\u0005\u00111\u0005\u0005\n\u0003[!\"\u0019!C\u0005\u0003_A\u0001\"a\u000e\u0015A\u0003%\u0011\u0011\u0007\u0005\t\u0003s!\u0002\u0019!C\u0005-\"I\u00111\b\u000bA\u0002\u0013%\u0011Q\b\u0005\b\u0003\u0003\"\u0002\u0015)\u0003X\u0011!\t\u0019\u0005\u0006a\u0001\n\u00131\u0006\"CA#)\u0001\u0007I\u0011BA$\u0011\u001d\tY\u0005\u0006Q!\n]CQa\u0018\u000b\u0005BYCQ\u0001\u0019\u000b\u0005B\u0005DQ!\u001a\u000b\u0005B\u0005DQA\u001a\u000b\u0005B\u0005Daa\u001a\u000b\u0005B\u00055\u0003B\u00028\u0015\t\u0003\n\t\u0006\u0003\u0004q)\u0011\u0005\u0013\u0011\u000b\u0005\u0006cR!\tE\u001d\u0005\u0006gR!\tE\u001d\u0005\u0006iR!\tE\u001d\u0004\b\u0003'\u0012\"aNA+\u0011!)6F!b\u0001\n\u00032\u0006\u0002\u0003.,\u0005\u0003\u0005\u000b\u0011B,\t\rm[C\u0011AA8\u0011\u0015y6\u0006\"\u0011W\u0011\u0015\u00017\u0006\"\u0011b\u0011\u001517\u0006\"\u0011b\u0011\u001997\u0006\"\u0011\u0002v!1an\u000bC!\u0003sBQa]\u0016\u0005BIDQ\u0001^\u0016\u0005BI\u0014QBQ8v]\u0012,GMQ;gM\u0016\u0014(B\u0001\u001d:\u0003\u0011IW\u000e\u001d7\u000b\u0005iZ\u0014AB:ue\u0016\fWNC\u0001=\u0003\u0011\t7n[1\u0016\u0005yZ5c\u0001\u0001@\u000bB\u0011\u0001iQ\u0007\u0002\u0003*\t!)A\u0003tG\u0006d\u0017-\u0003\u0002E\u0003\n1\u0011I\\=SK\u001a\u00042AR$J\u001b\u00059\u0014B\u0001%8\u0005\u0019\u0011UO\u001a4feB\u0011!j\u0013\u0007\u0001\t\u0015a\u0005A1\u0001O\u0005\u0005!6\u0001A\t\u0003\u001fJ\u0003\"\u0001\u0011)\n\u0005E\u000b%a\u0002(pi\"Lgn\u001a\t\u0003\u0001NK!\u0001V!\u0003\u0007\u0005s\u00170\u0001\u0005dCB\f7-\u001b;z+\u00059\u0006C\u0001!Y\u0013\tI\u0016IA\u0002J]R\f\u0011bY1qC\u000eLG/\u001f\u0011\u0002\rqJg.\u001b;?)\tif\fE\u0002G\u0001%CQ!V\u0002A\u0002]\u000bA!^:fI\u00061\u0011n\u001d$vY2,\u0012A\u0019\t\u0003\u0001\u000eL!\u0001Z!\u0003\u000f\t{w\u000e\\3b]\u00069\u0011n]#naRL\u0018\u0001\u00038p]\u0016k\u0007\u000f^=\u0002\u000f\u0015t\u0017/^3vKR\u0011\u0011\u000e\u001c\t\u0003\u0001*L!a[!\u0003\tUs\u0017\u000e\u001e\u0005\u0006[\"\u0001\r!S\u0001\u0005K2,W.A\u0004eKF,X-^3\u0015\u0003%\u000bA\u0001]3fW\u0006)1\r\\3beR\t\u0011.\u0001\u0005ee>\u0004\b*Z1e\u0003!!'o\u001c9UC&d\u0017!A9\u0016\u0003\u0015\u000bQ!]0%KF$\"![=\t\u000fi|\u0011\u0011!a\u0001\u000b\u0006\u0019\u0001\u0010J\u0019\u0002\u0005E\u0004\u0003F\u0001\u0001~!\rq\u00181A\u0007\u0002\u007f*\u0019\u0011\u0011A\u001e\u0002\u0015\u0005tgn\u001c;bi&|g.C\u0002\u0002\u0006}\u00141\"\u00138uKJt\u0017\r\\!qS\u0006i!i\\;oI\u0016$')\u001e4gKJ\u0004\"A\u0012\n\u0014\u0005IyDCAA\u0005\u0005)1\u0015\u000e_3e#V,W/Z\u000b\u0005\u0003'\tIb\u0005\u0003\u0015\u007f\u0005U\u0001\u0003\u0002$H\u0003/\u00012ASA\r\t\u0015aEC1\u0001O\u00031\u0019x/\u001b;dQ\n+hMZ3s!\u0019\u0001\u0015qDA\u000bS&\u0019\u0011\u0011E!\u0003\u0013\u0019+hn\u0019;j_:\fDCBA\u0013\u0003S\tY\u0003E\u0003\u0002(Q\t9\"D\u0001\u0013\u0011\u0015)\u0006\u00041\u0001X\u0011\u001d\tY\u0002\u0007a\u0001\u0003;\tQ!];fk\u0016,\"!!\r\u0011\t\u0001\u000b\u0019dP\u0005\u0004\u0003k\t%!B!se\u0006L\u0018AB9vKV,\u0007%\u0001\u0003iK\u0006$\u0017\u0001\u00035fC\u0012|F%Z9\u0015\u0007%\fy\u0004C\u0004{9\u0005\u0005\t\u0019A,\u0002\u000b!,\u0017\r\u001a\u0011\u0002\tQ\f\u0017\u000e\\\u0001\ti\u0006LGn\u0018\u0013fcR\u0019\u0011.!\u0013\t\u000fi|\u0012\u0011!a\u0001/\u0006)A/Y5mAQ\u0019\u0011.a\u0014\t\r5,\u0003\u0019AA\f)\t\t9B\u0001\u0007Es:\fW.[2Rk\u0016,X-\u0006\u0003\u0002X\u0005-4#B\u0016\u0002Z\u00055\u0004CBA.\u0003K\nI'\u0004\u0002\u0002^)!\u0011qLA1\u0003\u0011)H/\u001b7\u000b\u0005\u0005\r\u0014\u0001\u00026bm\u0006LA!a\u001a\u0002^\tQA*\u001b8lK\u0012d\u0015n\u001d;\u0011\u0007)\u000bY\u0007B\u0003MW\t\u0007a\n\u0005\u0003G\u000f\u0006%D\u0003BA9\u0003g\u0002R!a\n,\u0003SBQ!\u0016\u0018A\u0002]#2![A<\u0011\u0019i'\u00071\u0001\u0002jQ\u0011\u0011\u0011\u000e\u0015\u0003WuD#AE?)\u0005Ei\b")
@InternalApi
public final class BoundedBuffer<T>
implements Buffer<T> {
    private final int capacity;
    private Buffer<T> q;

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int used() {
        return this.q().used();
    }

    @Override
    public boolean isFull() {
        return this.q().isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.q().isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return this.q().nonEmpty();
    }

    @Override
    public void enqueue(T elem) {
        this.q().enqueue(elem);
    }

    @Override
    public T dequeue() {
        return this.q().dequeue();
    }

    @Override
    public T peek() {
        return this.q().peek();
    }

    @Override
    public void clear() {
        this.q().clear();
    }

    @Override
    public void dropHead() {
        this.q().dropHead();
    }

    @Override
    public void dropTail() {
        this.q().dropTail();
    }

    private Buffer<T> q() {
        return this.q;
    }

    private void q_$eq(Buffer<T> x$1) {
        this.q = x$1;
    }

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.q = new FixedQueue(capacity, (Function1<Buffer, Object> & Serializable)newBuffer -> {
            this.q_$eq(newBuffer);
            return BoxedUnit.UNIT;
        });
    }

    @InternalApi
    public static final class DynamicQueue<T>
    extends LinkedList<T>
    implements Buffer<T> {
        private final int capacity;

        @Override
        public int capacity() {
            return this.capacity;
        }

        @Override
        public int used() {
            return this.size();
        }

        @Override
        public boolean isFull() {
            return this.size() == this.capacity();
        }

        @Override
        public boolean nonEmpty() {
            return !this.isEmpty();
        }

        @Override
        public void enqueue(T elem) {
            this.add(elem);
        }

        @Override
        public T dequeue() {
            return (T)this.remove();
        }

        @Override
        public void dropHead() {
            this.remove();
        }

        @Override
        public void dropTail() {
            this.removeLast();
        }

        public DynamicQueue(int capacity) {
            this.capacity = capacity;
        }
    }

    private static final class FixedQueue<T>
    implements Buffer<T> {
        private final int capacity;
        private final Function1<Buffer<T>, BoxedUnit> switchBuffer;
        private final Object[] queue;
        private int head;
        private int tail;

        @Override
        public int capacity() {
            return this.capacity;
        }

        private Object[] queue() {
            return this.queue;
        }

        private int head() {
            return this.head;
        }

        private void head_$eq(int x$1) {
            this.head = x$1;
        }

        private int tail() {
            return this.tail;
        }

        private void tail_$eq(int x$1) {
            this.tail = x$1;
        }

        @Override
        public int used() {
            return this.tail() - this.head();
        }

        @Override
        public boolean isFull() {
            return this.used() == this.capacity();
        }

        @Override
        public boolean isEmpty() {
            return this.tail() == this.head();
        }

        @Override
        public boolean nonEmpty() {
            return this.tail() != this.head();
        }

        @Override
        public void enqueue(T elem) {
            if (this.tail() - this.head() == Buffer$.MODULE$.FixedQueueSize()) {
                DynamicQueue<T> queue = new DynamicQueue<T>(this.capacity());
                while (this.nonEmpty()) {
                    queue.enqueue(this.dequeue());
                }
                this.switchBuffer.apply(queue);
                queue.enqueue(elem);
                return;
            }
            this.queue()[this.tail() & Buffer$.MODULE$.FixedQueueMask()] = elem;
            this.tail_$eq(this.tail() + 1);
        }

        @Override
        public T dequeue() {
            int pos = this.head() & Buffer$.MODULE$.FixedQueueMask();
            Object ret = this.queue()[pos];
            this.queue()[pos] = null;
            this.head_$eq(this.head() + 1);
            return (T)ret;
        }

        @Override
        public T peek() {
            if (this.tail() == this.head()) {
                return null;
            }
            return (T)this.queue()[this.head() & Buffer$.MODULE$.FixedQueueMask()];
        }

        @Override
        public void clear() {
            while (this.nonEmpty()) {
                this.dequeue();
            }
        }

        @Override
        public void dropHead() {
            this.dequeue();
        }

        @Override
        public void dropTail() {
            this.tail_$eq(this.tail() - 1);
            this.queue()[this.tail() & Buffer$.MODULE$.FixedQueueMask()] = null;
        }

        public FixedQueue(int capacity, Function1<Buffer<T>, BoxedUnit> switchBuffer) {
            this.capacity = capacity;
            this.switchBuffer = switchBuffer;
            this.queue = new Object[Buffer$.MODULE$.FixedQueueSize()];
            this.head = 0;
            this.tail = 0;
        }
    }
}

