/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Buffer;
import java.io.Serializable;
import java.util.Arrays;
import scala.Function1;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichLong;

@ScalaSignature(bytes="\u0006\u0005\u0005muA\u0002\u0016,\u0011\u0003y\u0013G\u0002\u00044W!\u0005q\u0006\u000e\u0005\u0006w\u0005!\t!\u0010\u0005\u0007}\u0005!\taL \u0007\u000bM\n\u0011\u0011E\"\t\u0011M#!Q1A\u0005\u0002QC\u0001\u0002\u0017\u0003\u0003\u0002\u0003\u0006I!\u0016\u0005\u0006w\u0011!\t!\u0017\u0005\u00069\u0012!\t%\u0018\u0005\bM\u0012\u0011\r\u0011\"\u0003h\u0011\u0019YG\u0001)A\u0005Q\"9A\u000e\u0002a\u0001\n#i\u0007bB9\u0005\u0001\u0004%\tB\u001d\u0005\u0007q\u0012\u0001\u000b\u0015\u00028\t\u000fe$\u0001\u0019!C\t[\"9!\u0010\u0002a\u0001\n#Y\bBB?\u0005A\u0003&a\u000eC\u0003\u007f\t\u0011\u0005A\u000b\u0003\u0004\u0000\t\u0011\u0005\u0011\u0011\u0001\u0005\b\u0003\u0013!A\u0011AA\u0001\u0011\u0019\tY\u0001\u0002C\u0001)\"9\u0011Q\u0002\u0003\u0005\u0002\u0005\u0005\u0001bBA\b\t\u0011\u0005\u0011\u0011\u0001\u0005\b\u0003#!A\u0011AA\n\u0011\u001d\tI\u0002\u0002D\t\u00037Aq!!\n\u0005\t\u0013\t9\u0003C\u0004\u00020\u0011!I!!\r\t\u000f\u0005UB\u0001\"\u0001\u00028!9\u0011\u0011\b\u0003\u0005\u0002\u0005]\u0002bBA\u001e\t\u0011\u0005\u0011Q\b\u0005\b\u0003\u007f!A\u0011AA\u001f\u0011\u001d\t\t\u0005\u0002C\u0001\u0003{1q!!\u0012\u0002\u0005=\n9\u0005\u0003\u0006\u0002R\u0001\u0012\t\u0011)A\u0005+\u0016Aaa\u000f\u0011\u0005\u0002\u0005M\u0003bBA\rA\u0011E\u0013\u0011\f\u0004\b\u0003?\n!aLA1\u0011)\t\t\u0006\nB\u0001B\u0003%Q+\u0002\u0005\u0007w\u0011\"\t!a\u001b\t\u0011\u0005EDE1A\u0005\nQCq!a\u001d%A\u0003%Q\u000bC\u0004\u0002\u001a\u0011\"\t&!\u001e\u0002\u001f\u0019K\u00070\u001a3TSj,')\u001e4gKJT!\u0001L\u0017\u0002\t%l\u0007\u000f\u001c\u0006\u0003]=\naa\u001d;sK\u0006l'\"\u0001\u0019\u0002\t\u0005\\7.\u0019\t\u0003e\u0005i\u0011a\u000b\u0002\u0010\r&DX\rZ*ju\u0016\u0014UO\u001a4feN\u0011\u0011!\u000e\t\u0003mej\u0011a\u000e\u0006\u0002q\u0005)1oY1mC&\u0011!h\u000e\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!M\u0001\u0006CB\u0004H._\u000b\u0004\u0001\u0006uDcA!\u0002\u0000A!!\tBA>\u001b\u0005\tQC\u0001#K'\r!Q'\u0012\t\u0004e\u0019C\u0015BA$,\u0005\u0019\u0011UO\u001a4feB\u0011\u0011J\u0013\u0007\u0001\t\u0015YEA1\u0001M\u0005\u0005!\u0016CA'Q!\t1d*\u0003\u0002Po\t9aj\u001c;iS:<\u0007C\u0001\u001cR\u0013\t\u0011vGA\u0002B]f\f\u0001bY1qC\u000eLG/_\u000b\u0002+B\u0011aGV\u0005\u0003/^\u00121!\u00138u\u0003%\u0019\u0017\r]1dSRL\b\u0005\u0006\u0002[7B\u0019!\t\u0002%\t\u000bM;\u0001\u0019A+\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A\u0018\t\u0003?\u0012l\u0011\u0001\u0019\u0006\u0003C\n\fA\u0001\\1oO*\t1-\u0001\u0003kCZ\f\u0017BA3a\u0005\u0019\u0019FO]5oO\u00061!-\u001e4gKJ,\u0012\u0001\u001b\t\u0004m%,\u0014B\u000168\u0005\u0015\t%O]1z\u0003\u001d\u0011WO\u001a4fe\u0002\nqA]3bI&#\u00070F\u0001o!\t1t.\u0003\u0002qo\t!Aj\u001c8h\u0003-\u0011X-\u00193JIb|F%Z9\u0015\u0005M4\bC\u0001\u001cu\u0013\t)xG\u0001\u0003V]&$\bbB<\r\u0003\u0003\u0005\rA\\\u0001\u0004q\u0012\n\u0014\u0001\u0003:fC\u0012LE\r\u001f\u0011\u0002\u0011]\u0014\u0018\u000e^3JIb\fAb\u001e:ji\u0016LE\r_0%KF$\"a\u001d?\t\u000f]|\u0011\u0011!a\u0001]\u0006IqO]5uK&#\u0007\u0010I\u0001\u0005kN,G-\u0001\u0004jg\u001a+H\u000e\\\u000b\u0003\u0003\u0007\u00012ANA\u0003\u0013\r\t9a\u000e\u0002\b\u0005>|G.Z1o\u0003\u001dqwN\u001c$vY2\f\u0011C]3nC&t\u0017N\\4DCB\f7-\u001b;z\u0003\u001dI7/R7qif\f\u0001B\\8o\u000b6\u0004H/_\u0001\bK:\fX/Z;f)\r\u0019\u0018Q\u0003\u0005\u0007\u0003/9\u0002\u0019\u0001%\u0002\t\u0015dW-\\\u0001\ti>|eMZ:fiR)Q+!\b\u0002\"!1\u0011q\u0004\rA\u00029\f1!\u001b3y\u0011\u001d\t\u0019\u0003\u0007a\u0001\u0003\u0007\t1\"\\1j]R,g.\u00198dK\u0006\u0019\u0001/\u001e;\u0015\u000fM\fI#a\u000b\u0002.!1\u0011qD\rA\u00029Da!a\u0006\u001a\u0001\u0004A\u0005bBA\u00123\u0001\u0007\u00111A\u0001\u0004O\u0016$Hc\u0001%\u00024!1\u0011q\u0004\u000eA\u00029\fA\u0001]3fWR\t\u0001*A\u0004eKF,X-^3\u0002\u000b\rdW-\u0019:\u0015\u0003M\f\u0001\u0002\u001a:pa\"+\u0017\rZ\u0001\tIJ|\u0007\u000fV1jY&\u001aA\u0001\t\u0013\u0003+5{G-\u001e7p\r&DX\rZ*ju\u0016\u0014UO\u001a4feV!\u0011\u0011JA('\r\u0001\u00131\n\t\u0005\u0005\u0012\ti\u0005E\u0002J\u0003\u001f\"Qa\u0013\u0011C\u00021\u000bQaX:ju\u0016$B!!\u0016\u0002XA!!\tIA'\u0011\u0019\t\tF\ta\u0001+R)Q+a\u0017\u0002^!1\u0011qD\u0012A\u00029Dq!a\t$\u0001\u0004\t\u0019AA\rQ_^,'o\u00144Uo>4\u0015\u000e_3e'&TXMQ;gM\u0016\u0014X\u0003BA2\u0003S\u001a2\u0001JA3!\u0011\u0011E!a\u001a\u0011\u0007%\u000bI\u0007B\u0003LI\t\u0007A\n\u0006\u0003\u0002n\u0005=\u0004\u0003\u0002\"%\u0003OBa!!\u0015'\u0001\u0004)\u0016\u0001B'bg.\fQ!T1tW\u0002\"R!VA<\u0003sBa!a\b*\u0001\u0004q\u0007bBA\u0012S\u0001\u0007\u00111\u0001\t\u0004\u0013\u0006uD!B&\u0004\u0005\u0004a\u0005BBAA\u0007\u0001\u0007Q+\u0001\u0003tSj,\u0007fA\u0002\u0002\u0006B!\u0011qQAG\u001b\t\tIIC\u0002\u0002\f>\n!\"\u00198o_R\fG/[8o\u0013\u0011\ty)!#\u0003#%sG/\u001a:oC2\u001cF/\u00192mK\u0006\u0003\u0018\u000eK\u0002\u0002\u0003'\u0003B!a\"\u0002\u0016&!\u0011qSAE\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0007\u0001\t\u0019\n")
@InternalApi
public final class FixedSizeBuffer {

    public static abstract class FixedSizeBuffer<T>
    implements Buffer<T> {
        private final int capacity;
        private final Object[] buffer;
        private long readIdx;
        private long writeIdx;

        @Override
        public int capacity() {
            return this.capacity;
        }

        public String toString() {
            return new StringBuilder(14).append("Buffer(").append(this.capacity()).append(", ").append(this.readIdx()).append(", ").append(this.writeIdx()).append(")(").append(((IterableOnceOps)new RichLong(Predef$.MODULE$.longWrapper(this.readIdx())).until(BoxesRunTime.boxToLong(this.writeIdx())).map((Function1<Object, Object> & Serializable)idx -> this.get(BoxesRunTime.unboxToLong(idx)))).mkString(", ")).append(")").toString();
        }

        private Object[] buffer() {
            return this.buffer;
        }

        public long readIdx() {
            return this.readIdx;
        }

        public void readIdx_$eq(long x$1) {
            this.readIdx = x$1;
        }

        public long writeIdx() {
            return this.writeIdx;
        }

        public void writeIdx_$eq(long x$1) {
            this.writeIdx = x$1;
        }

        @Override
        public int used() {
            return (int)(this.writeIdx() - this.readIdx());
        }

        @Override
        public boolean isFull() {
            return this.used() == this.capacity();
        }

        public boolean nonFull() {
            return this.used() < this.capacity();
        }

        public int remainingCapacity() {
            return this.capacity() - this.used();
        }

        @Override
        public boolean isEmpty() {
            return this.used() == 0;
        }

        @Override
        public boolean nonEmpty() {
            return this.used() != 0;
        }

        @Override
        public void enqueue(T elem) {
            this.put(this.writeIdx(), elem, false);
            this.writeIdx_$eq(this.writeIdx() + 1L);
        }

        public abstract int toOffset(long var1, boolean var3);

        private void put(long idx, T elem, boolean maintenance) {
            this.buffer()[this.toOffset((long)idx, (boolean)maintenance)] = elem;
        }

        private T get(long idx) {
            return (T)this.buffer()[this.toOffset(idx, false)];
        }

        @Override
        public T peek() {
            return this.get(this.readIdx());
        }

        @Override
        public T dequeue() {
            T result = this.get(this.readIdx());
            this.dropHead();
            return result;
        }

        @Override
        public void clear() {
            Arrays.fill(this.buffer(), null);
            this.readIdx_$eq(0L);
            this.writeIdx_$eq(0L);
        }

        @Override
        public void dropHead() {
            this.put(this.readIdx(), null, true);
            this.readIdx_$eq(this.readIdx() + 1L);
        }

        @Override
        public void dropTail() {
            this.writeIdx_$eq(this.writeIdx() - 1L);
            this.put(this.writeIdx(), null, false);
        }

        public FixedSizeBuffer(int capacity) {
            this.capacity = capacity;
            this.buffer = new Object[capacity];
            this.readIdx = 0L;
            this.writeIdx = 0L;
        }
    }

    public static final class ModuloFixedSizeBuffer<T>
    extends FixedSizeBuffer<T> {
        @Override
        public int toOffset(long idx, boolean maintenance) {
            block0: {
                if (!maintenance || this.readIdx() <= Integer.MAX_VALUE) break block0;
                int shift = Integer.MAX_VALUE - Integer.MAX_VALUE % this.capacity();
                this.readIdx_$eq(this.readIdx() - (long)shift);
                this.writeIdx_$eq(this.writeIdx() - (long)shift);
            }
            return (int)(idx % (long)this.capacity());
        }

        public ModuloFixedSizeBuffer(int _size) {
            super(_size);
        }
    }

    public static final class PowerOfTwoFixedSizeBuffer<T>
    extends FixedSizeBuffer<T> {
        private final int Mask = this.capacity() - 1;

        private int Mask() {
            return this.Mask;
        }

        @Override
        public int toOffset(long idx, boolean maintenance) {
            return (int)idx & this.Mask();
        }

        public PowerOfTwoFixedSizeBuffer(int _size) {
            super(_size);
        }
    }
}

