/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Buffer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00054Q!\u0005\n\u0003%aA\u0001\u0002\r\u0001\u0003\u0002\u0003\u0006I\u0001\t\u0005\tc\u0001\u0011\t\u0011)A\u0005A!A!\u0007\u0001B\u0001B\u0003%1\u0007C\u0003:\u0001\u0011\u0005!\bC\u0003@\u0001\u0011\u0005\u0001\tC\u0003E\u0001\u0011\u0005\u0001\tC\u0003F\u0001\u0011\u0005a\tC\u0003K\u0001\u0011\u0005a\tC\u0003L\u0001\u0011\u0005a\tC\u0003M\u0001\u0011\u0005Q\nC\u0003Q\u0001\u0011\u0005\u0011\u000bC\u0003S\u0001\u0011\u0005\u0011\u000bC\u0003T\u0001\u0011\u0005A\u000bC\u0003V\u0001\u0011\u0005A\u000bC\u0003W\u0001\u0011\u0005A\u000bC\u0003X\u0001\u0011%\u0001LA\u0007DQ\u0006Lg.\u001a3Ck\u001a4WM\u001d\u0006\u0003'Q\tA![7qY*\u0011QCF\u0001\u0007gR\u0014X-Y7\u000b\u0003]\tA!Y6lCV\u0011\u0011DJ\n\u0004\u0001i\u0001\u0003CA\u000e\u001f\u001b\u0005a\"\"A\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}a\"AB!osJ+g\rE\u0002\"E\u0011j\u0011AE\u0005\u0003GI\u0011aAQ;gM\u0016\u0014\bCA\u0013'\u0019\u0001!Qa\n\u0001C\u0002%\u0012\u0011\u0001V\u0002\u0001#\tQS\u0006\u0005\u0002\u001cW%\u0011A\u0006\b\u0002\b\u001d>$\b.\u001b8h!\tYb&\u0003\u000209\t\u0019\u0011I\\=\u0002\u0015!,\u0017\r\u001a\"vM\u001a,'/\u0001\u0006uC&d')\u001e4gKJ\fqb\u001c8F]F,X-^3U_\"+\u0017\r\u001a\t\u00057Q\"c'\u0003\u000269\tIa)\u001e8di&|g.\r\t\u00037]J!\u0001\u000f\u000f\u0003\tUs\u0017\u000e^\u0001\u0007y%t\u0017\u000e\u001e \u0015\tmbTH\u0010\t\u0004C\u0001!\u0003\"\u0002\u0019\u0005\u0001\u0004\u0001\u0003\"B\u0019\u0005\u0001\u0004\u0001\u0003\"\u0002\u001a\u0005\u0001\u0004\u0019\u0014\u0001C2ba\u0006\u001c\u0017\u000e^=\u0016\u0003\u0005\u0003\"a\u0007\"\n\u0005\rc\"aA%oi\u0006!Qo]3e\u0003\u0019I7OR;mYV\tq\t\u0005\u0002\u001c\u0011&\u0011\u0011\n\b\u0002\b\u0005>|G.Z1o\u0003\u001dI7/R7qif\f\u0001B\\8o\u000b6\u0004H/_\u0001\bK:\fX/Z;f)\t1d\nC\u0003P\u0015\u0001\u0007A%\u0001\u0003fY\u0016l\u0017a\u00023fcV,W/\u001a\u000b\u0002I\u0005!\u0001/Z3l\u0003\u0015\u0019G.Z1s)\u00051\u0014\u0001\u00033s_BDU-\u00193\u0002\u0011\u0011\u0014x\u000e\u001d+bS2\fQ\"\u001a8rk\u0016,X\rV8IK\u0006$GC\u0001\u001cZ\u0011\u0015y\u0005\u00031\u0001%Q\t\u00011\f\u0005\u0002]?6\tQL\u0003\u0002_-\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0001l&aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class ChainedBuffer<T>
implements Buffer<T> {
    private final Buffer<T> headBuffer;
    private final Buffer<T> tailBuffer;
    private final Function1<T, BoxedUnit> onEnqueueToHead;

    @Override
    public int capacity() {
        return this.headBuffer.capacity() + this.tailBuffer.capacity();
    }

    @Override
    public int used() {
        return this.headBuffer.used() + this.tailBuffer.used();
    }

    @Override
    public boolean isFull() {
        return this.tailBuffer.isFull();
    }

    @Override
    public boolean isEmpty() {
        return this.headBuffer.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return this.headBuffer.nonEmpty();
    }

    @Override
    public void enqueue(T elem) {
        if (this.headBuffer.isFull()) {
            this.tailBuffer.enqueue(elem);
            return;
        }
        this.enqueueToHead(elem);
    }

    @Override
    public T dequeue() {
        T ret;
        block0: {
            ret = this.headBuffer.dequeue();
            if (!this.tailBuffer.nonEmpty()) break block0;
            T toHead = this.tailBuffer.dequeue();
            this.enqueueToHead(toHead);
        }
        return ret;
    }

    @Override
    public T peek() {
        return this.headBuffer.peek();
    }

    @Override
    public void clear() {
        this.headBuffer.clear();
        this.tailBuffer.clear();
    }

    @Override
    public void dropHead() {
        this.dequeue();
    }

    @Override
    public void dropTail() {
        if (this.tailBuffer.nonEmpty()) {
            this.tailBuffer.dropTail();
            return;
        }
        this.headBuffer.dropTail();
    }

    private void enqueueToHead(T elem) {
        this.headBuffer.enqueue(elem);
        this.onEnqueueToHead.apply(elem);
    }

    public ChainedBuffer(Buffer<T> headBuffer, Buffer<T> tailBuffer, Function1<T, BoxedUnit> onEnqueueToHead) {
        this.headBuffer = headBuffer;
        this.tailBuffer = tailBuffer;
        this.onEnqueueToHead = onEnqueueToHead;
    }
}

